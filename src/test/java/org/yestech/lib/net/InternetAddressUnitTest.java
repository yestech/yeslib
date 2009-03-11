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
package org.yestech.lib.net;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class InternetAddressUnitTest {
    @Test
    public void testValidCreateIp4Address() throws UnknownHostException {
        InetAddress address = InternetAddress.createIp4Address("127.0.0.1");
        assertEquals(InetAddress.getByName("localhost"), address);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInValidCreateIp4Address() {
        InternetAddress.createIp4Address("127.0.0");
    }
}
