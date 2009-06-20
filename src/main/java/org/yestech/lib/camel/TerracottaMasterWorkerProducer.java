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
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.*;

import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TerracottaMasterWorkerProducer extends DefaultProducer {

    public TerracottaMasterWorkerProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}