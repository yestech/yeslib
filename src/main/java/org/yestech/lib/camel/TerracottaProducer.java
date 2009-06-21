/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.camel;

import java.util.concurrent.BlockingQueue;

import org.apache.camel.Exchange;
import org.apache.camel.component.seda.CollectionProducer;
import org.apache.camel.component.seda.SedaEndpoint;
import org.terracotta.message.pipe.Pipe;

/**
 * @version $Revision: 1.1 $
 */
public class TerracottaProducer extends TerracottaCollectionProducer {
    private TerracottaEndpoint endpoint;

    public TerracottaProducer(TerracottaEndpoint endpoint, Pipe<Object> queue) {
        super(endpoint, queue);
        this.endpoint = endpoint;
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