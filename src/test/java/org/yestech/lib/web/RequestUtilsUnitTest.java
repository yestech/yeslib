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
package org.yestech.lib.web;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class RequestUtilsUnitTest {
    private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        request = new MockHttpServletRequest();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testResolveUserIpAddressWithMultipleHeaders() {
        request.addHeader("X-Forwarded-For", "127.0.0.1, 192.168.0.2, 10.16.0.1");
        String ip = RequestUtils.resolveUserIpAddress(request);
        assertEquals("10.16.0.1", ip);
    }
}
