/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.message.pipe.Pipe;

import java.util.concurrent.ExecutorService;
import org.apache.camel.util.concurrent.ExecutorServiceHelper;

/**
 *
 * A {@link org.apache.camel.Consumer} which uses a Terracotta {@link Pipe} to pass the {@link org.apache.camel.Exchange}.
 */
public class TerracottaConsumer extends ServiceSupport implements Consumer, Runnable {
    final private static Logger logger = LoggerFactory.getLogger(TerracottaConsumer.class);

    private TerracottaEndpoint endpoint;
    private Processor processor;
    private ExecutorService executor;

    public TerracottaConsumer(TerracottaEndpoint endpoint, Processor processor) {
        this.endpoint = endpoint;
        this.processor = processor;
    }

    @Override
    public String toString() {
        return "TerracottaConsumer: " + endpoint.getEndpointUri();
    }

    public void run() {
        Pipe<Object> queue = endpoint.getPipe();
        while (queue != null && isRunAllowed()) {
            final Exchange exchange = new DefaultExchange(endpoint.getCamelContext());
            try {
                final Object pipeMessage = queue.take();
                DefaultMessage message = new DefaultMessage();
                message.setBody(pipeMessage);
                exchange.setIn(message);
            } catch (InterruptedException e) {
                logger.debug("Wait interrupted, are we stopping? " + (isStopping() || isStopped()));
                continue;
            }
            if (exchange != null) {
                if (isRunAllowed()) {
                    try {
                        processor.process(exchange);
                    } catch (Exception e) {
                        logger.error("TerracottaConsumer pipe caught: " + e, e);
                    }
                } else {
                    logger.warn("This consumer is stopped during polling an exchange, so putting it back on the TerracottaConsumer pipe: " + exchange);
                    try {
                        queue.put(exchange);
                    } catch (InterruptedException e) {
                        logger.debug("Sleep interrupted, are we stopping? " + (isStopping() || isStopped()));
                    }
                }
            }
        }
    }

    protected void doStart() throws Exception {
        int poolSize = endpoint.getConcurrentConsumers();
        executor = ExecutorServiceHelper.newFixedThreadPool(poolSize, endpoint.getEndpointUri(), true);
        for (int i = 0; i < poolSize; i++) {
            executor.execute(this);
        }
        endpoint.onStarted(this);
    }

    protected void doStop() throws Exception {
        endpoint.onStopped(this);

        executor.shutdownNow();
        executor = null;
    }

}