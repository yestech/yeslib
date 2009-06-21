/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.terracotta.message.pipe.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple {@link org.apache.camel.Producer} which just appends to a {@link java.util.Collection} the {@link org.apache.camel.Exchange} object.
 *
 * A component based on camel SEDA component.
 */
public class TerracottaCollectionProducer extends DefaultProducer implements AsyncProcessor {
    final private static Logger logger = LoggerFactory.getLogger(TerracottaCollectionProducer.class);
    private final Pipe<Object> pipe;

    public TerracottaCollectionProducer(Endpoint endpoint, Pipe<Object> pipe) {
        super(endpoint);
        this.pipe = pipe;
    }

    public void process(Exchange exchange) throws Exception {
        pipe.put(exchange.copy());
    }

    public boolean process(Exchange exchange, AsyncCallback callback) {
        try {
            pipe.put(exchange.copy());
            callback.done(true);
            return true;
        } catch (InterruptedException e) {
            logger.error("error putting exchange...", e);
            return false;
        }
    }
}