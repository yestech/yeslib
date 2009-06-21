/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import org.apache.camel.*;
import org.apache.camel.impl.ServiceSupport;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.converter.AsyncProcessorTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.message.pipe.Pipe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * A Consumer for the SEDA component.
 *
 * @version $Revision: 751655 $
 */
public class TerracottaConsumer extends ServiceSupport implements Consumer, Runnable {
    final private static Logger logger = LoggerFactory.getLogger(TerracottaConsumer.class);

    private TerracottaEndpoint endpoint;
    private AsyncProcessor processor;
    private ExecutorService executor;

    public TerracottaConsumer(TerracottaEndpoint endpoint, Processor processor) {
        this.endpoint = endpoint;
        this.processor = AsyncProcessorTypeConverter.convert(processor);
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
//                final Object pipeMessage = queue.poll(1000, TimeUnit.MILLISECONDS);
                final Object pipeMessage = queue.take();
                System.out.println("TerracottaConsumer take: " + pipeMessage);
                DefaultMessage message = new DefaultMessage();
                message.setBody(pipeMessage);
                System.out.println("TerracottaConsumer message: " + message);
                exchange.setIn(message);
                System.out.println("TerracottaConsumer exchange: " + exchange);
            } catch (InterruptedException e) {
                logger.debug("Sleep interrupted, are we stopping? " + (isStopping() || isStopped()));
                continue;
            }
            if (exchange != null) {
                System.out.println("TerracottaConsumer exchange process: " + exchange);
                if (isRunAllowed()) {
                    try {
                        processor.process(exchange, new AsyncCallback() {
                            public void done(boolean sync) {
                            }
                        });
                    } catch (Exception e) {
                        logger.error("TerracottaConsumer pipe caught: " + e, e);
                        e.printStackTrace();
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
        int concurrentConsumers = endpoint.getConcurrentConsumers();
        executor = Executors.newFixedThreadPool(concurrentConsumers, new ThreadFactory() {

            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, getThreadName(endpoint.getEndpointUri()));
                thread.setDaemon(true);
                return thread;
            }
        });
        for (int i = 0; i < concurrentConsumers; i++) {
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