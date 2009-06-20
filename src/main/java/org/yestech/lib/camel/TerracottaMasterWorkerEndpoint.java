/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.camel;

import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.*;

import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TerracottaMasterWorkerEndpoint extends DefaultEndpoint {

    public TerracottaMasterWorkerEndpoint(String endpointUri, Component component) {
        super(endpointUri, component);
    }

    public TerracottaMasterWorkerEndpoint(String endpointUri, CamelContext camelContext) {
        super(endpointUri, camelContext);
    }

    public TerracottaMasterWorkerEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new TerracottaMasterWorkerProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new TerracottaMasterWorkerConsumer(this, processor);
    }
}