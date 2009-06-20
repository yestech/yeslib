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
import org.apache.camel.Endpoint;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TerracottaMasterWorkerComponent extends DefaultComponent<DefaultExchange>
 implements ApplicationContextAware {

    @Override
    protected Endpoint<DefaultExchange> createEndpoint(String uri, String remaining, Map parameters) throws Exception {
        TerracottaMasterWorkerEndpoint endpoint = new TerracottaMasterWorkerEndpoint(uri, this);
        return endpoint;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
