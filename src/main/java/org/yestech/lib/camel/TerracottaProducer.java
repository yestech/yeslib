/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.message.pipe.Pipe;

/**
 * 
 * A {@link org.apache.camel.Producer} which uses a Terracotta {@link Pipe} to pass the {@link org.apache.camel.Exchange}.
 *
 */
public class TerracottaProducer extends DefaultProducer {
    final private static Logger logger = LoggerFactory.getLogger(TerracottaProducer.class);
    private final Pipe<Object> queue;
    private TerracottaEndpoint endpoint;

    public TerracottaProducer(TerracottaEndpoint endpoint, Pipe<Object> queue) {
        super(endpoint);
        this.endpoint = endpoint;
        this.queue = queue;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        queue.put(exchange.copy());
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        endpoint.onStarted(this);
    }

    @Override
    protected void doStop() throws Exception {
        endpoint.onStopped(this);
        super.doStop();
    }
}