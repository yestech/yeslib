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

import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class InternetAddress {

    public static InetAddress createIp4Address(String address) {
        String[] tempOctects = StringUtils.split(address, ".");
        if (tempOctects.length == 4) {
            byte[] octets = new byte[4];
            for (int i = 0; i < tempOctects.length; i++) {
                String tempOctect = tempOctects[i];
                octets[i] = Byte.valueOf(tempOctect);                
            }
            try {
                return InetAddress.getByAddress(octets);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("not a valid ip4 address");
        }
    }
}
