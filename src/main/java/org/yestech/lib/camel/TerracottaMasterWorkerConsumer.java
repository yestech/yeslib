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

import org.apache.camel.impl.*;
import org.apache.camel.*;

import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TerracottaMasterWorkerConsumer extends DefaultConsumer {

    public TerracottaMasterWorkerConsumer(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }
    
}