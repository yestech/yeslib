/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */

/*

   MD5Crypt.java

   Created: 3 November 1999
   Release: $Name:  $
   Version: $Revision: 1.8 $
   Last Mod Date: $Date: 2004/08/25 01:01:14 $
   Java Port By: Jonathan Abbey, jonabbey@arlut.utexas.edu
   Original C Version:
   ----------------------------------------------------------------------------
   "THE BEER-WARE LICENSE" (Revision 42):
   <phk@login.dknet.dk> wrote this file.  As long as you retain this notice you
   can do whatever you want with this stuff. If we meet some day, and you think
   this stuff is worth it, you can buy me a beer in return.   Poul-Henning Kamp
   ----------------------------------------------------------------------------

   -----------------------------------------------------------------------

   Ganymede Directory Management System

   Copyright (C) 1996, 1997, 1998, 1999, 2000, 2001, 2002
   The University of Texas at Austin.

   Contact information

   Web site: http://www.arlut.utexas.edu/gash2
   Author Email: ganymede_author@arlut.utexas.edu
   Email mailing list: ganymede@arlut.utexas.edu

   US Mail:

   Computer Science Division
   Applied Research Laboratories
   The University of Texas at Austin
   PO Box 8029, Austin TX 78713-8029

   Telephone: (512) 835-3200

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
   02111-1307, USA

*/
/*------------------------------------------------------------------------------
                                                                           class
                                                                        MD5Crypt

------------------------------------------------------------------------------*/

package org.yestech.lib.crypto;

import java.util.Random;

/**
 * <p>This class defines a method,
 * {@link MD5Crypt#crypt(String, String) crypt()}, which
 * takes a password and a salt string and generates an OpenBSD/FreeBSD/Linux-compatible
 * md5-encoded password entry.</p>
 * <p/>
 * <p>Created: 3 November 1999</p>
 * <p>Release: $Name:  $</p>
 * <p>Version: $Revision: 1.8 $</p>
 * <p>Last Mod Date: $Date: 2004/08/25 01:01:14 $</p>
 * <p>Java Code By: Jonathan Abbey, jonabbey@arlut.utexas.edu</p>
 * <p>Original C Version:<pre>
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <phk@login.dknet.dk> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Poul-Henning Kamp
 * ----------------------------------------------------------------------------
 * </pre></p>
 */

class MD5Crypt {

    static private final String to64(long v, int size) {
        StringBuffer result = new StringBuffer();

        while (--size >= 0) {
            result.append(ICryptoConstants.ITOA64.charAt((int) (v & 0x3f)));
            v >>>= 6;
        }

        return result.toString();
    }

    static private final void clearbits(byte bits[]) {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }
    }

    /**
     * convert an encoded unsigned byte value into a int
     * with the unsigned value.
     */

    static private final int bytes2u(byte inp) {
        return (int) inp & 0xff;
    }

    /**
     * <p>This method actually generates a OpenBSD/FreeBSD/Linux PAM compatible
     * md5-encoded password hash from a plaintext password and a
     * salt.</p>
     * <p/>
     * <p>The resulting string will be in the form '$1$&lt;salt&gt;$&lt;hashed mess&gt;</p>
     *
     * @param password Plaintext password
     * @return An OpenBSD/FreeBSD/Linux-compatible md5-hashed password field.
     */

    static public final String crypt(String password) {
        StringBuffer salt = new StringBuffer();
        Random randgen = new Random();

        /* -- */

        while (salt.length() < 8) {
            int index = (int) (randgen.nextFloat() * ICryptoConstants.SALTCHARS.length());
            salt.append(ICryptoConstants.SALTCHARS.substring(index, index + 1));
        }

        return MD5Crypt.crypt(password, salt.toString());
    }

    /**
     * <p>This method actually generates a OpenBSD/FreeBSD/Linux PAM compatible
     * md5-encoded password hash from a plaintext password and a
     * salt.</p>
     * <p/>
     * <p>The resulting string will be in the form '$1$&lt;salt&gt;$&lt;hashed mess&gt;</p>
     *
     * @param password Plaintext password
     * @param salt     A short string to use to randomize md5.  May start with $1$, which
     *                 will be ignored.  It is explicitly permitted to pass a pre-existing
     *                 MD5Crypt'ed password entry as the salt.  crypt() will strip the salt
     *                 chars out properly.
     * @return An OpenBSD/FreeBSD/Linux-compatible md5-hashed password field.
     */

    static public final String crypt(String password, String salt) {
        return MD5Crypt.crypt(password, salt, "$1$");
    }

    /**
     * <p>This method generates an Apache MD5 compatible
     * md5-encoded password hash from a plaintext password and a
     * salt.</p>
     * <p/>
     * <p>The resulting string will be in the form '$apr1$&lt;salt&gt;$&lt;hashed mess&gt;</p>
     *
     * @param password Plaintext password
     * @return An Apache-compatible md5-hashed password string.
     */

    static public final String apacheCrypt(String password) {
        StringBuffer salt = new StringBuffer();
        Random randgen = new Random();

        /* -- */

        while (salt.length() < 8) {
            int index = (int) (randgen.nextFloat() * ICryptoConstants.SALTCHARS.length());
            salt.append(ICryptoConstants.SALTCHARS.substring(index, index + 1));
        }

        return MD5Crypt.apacheCrypt(password, salt.toString());
    }

    /**
     * <p>This method actually generates an Apache MD5 compatible
     * md5-encoded password hash from a plaintext password and a
     * salt.</p>
     * <p/>
     * <p>The resulting string will be in the form '$apr1$&lt;salt&gt;$&lt;hashed mess&gt;</p>
     *
     * @param password Plaintext password
     * @param salt     A short string to use to randomize md5.  May start with $apr1$, which
     *                 will be ignored.  It is explicitly permitted to pass a pre-existing
     *                 MD5Crypt'ed password entry as the salt.  crypt() will strip the salt
     *                 chars out properly.
     * @return An Apache-compatible md5-hashed password string.
     */

    static public final String apacheCrypt(String password, String salt) {
        return MD5Crypt.crypt(password, salt, "$apr1$");
    }

    /**
     * <p>This method actually generates md5-encoded password hash from
     * a plaintext password, a salt, and a magic string.</p>
     * <p/>
     * <p>There are two magic strings that make sense to use here.. '$1$' is the
     * magic string used by the FreeBSD/Linux/OpenBSD MD5Crypt algorithm, and
     * '$apr1$' is the magic string used by the Apache MD5Crypt algorithm.</p>
     * <p/>
     * <p>The resulting string will be in the form '&lt;magic&gt;&lt;salt&gt;$&lt;hashed mess&gt;</p>
     *
     * @param password Plaintext password @param salt A short string to
     *                 use to randomize md5.  May start with the magic string, which
     *                 will be ignored.  It is explicitly permitted to pass a
     *                 pre-existing MD5Crypt'ed password entry as the salt.  crypt()
     *                 will strip the salt chars out properly.
     * @return An md5-hashed password string.
     */

    static public final String crypt(String password, String salt, String magic) {
        /* This string is magic for this algorithm.  Having it this way,
* we can get get better later on */

        byte finalState[];
        MD5 ctx, ctx1;
        long l;

        /* -- */

        /* Refine the Salt first */

        /* If it starts with the magic string, then skip that */

        if (salt.startsWith(magic)) {
            salt = salt.substring(magic.length());
        }

        /* It stops at the first '$', max 8 chars */

        if (salt.indexOf('$') != -1) {
            salt = salt.substring(0, salt.indexOf('$'));
        }

        if (salt.length() > 8) {
            salt = salt.substring(0, 8);
        }

        ctx = new MD5();

        ctx.Update(password);    // The password first, since that is what is most unknown
        ctx.Update(magic);    // Then our magic string
        ctx.Update(salt);    // Then the raw salt

        /* Then just as many characters of the MD5(pw,salt,pw) */

        ctx1 = new MD5();
        ctx1.Update(password);
        ctx1.Update(salt);
        ctx1.Update(password);
        finalState = ctx1.Final();

        for (int pl = password.length(); pl > 0; pl -= 16) {
            ctx.Update(finalState, pl > 16 ? 16 : pl);
        }

        /*
        the original code claimed that finalState was being cleared
        to keep dangerous bits out of memory, but doing this is also
        required in order to get the right output.
        */

        clearbits(finalState);

        /* Then something really weird... */

        for (int i = password.length(); i != 0; i >>>= 1) {
            if ((i & 1) != 0) {
                ctx.Update(finalState, 1);
            } else {
                ctx.Update(password.getBytes(), 1);
            }
        }

        finalState = ctx.Final();

        /*
        * and now, just to make sure things don't run too fast
        * On a 60 Mhz Pentium this takes 34 msec, so you would
        * need 30 seconds to build a 1000 entry dictionary...
        *
        * (The above timings from the C version)
        */

        for (int i = 0; i < 1000; i++) {
            ctx1 = new MD5();

            if ((i & 1) != 0) {
                ctx1.Update(password);
            } else {
                ctx1.Update(finalState, 16);
            }

            if ((i % 3) != 0) {
                ctx1.Update(salt);
            }

            if ((i % 7) != 0) {
                ctx1.Update(password);
            }

            if ((i & 1) != 0) {
                ctx1.Update(finalState, 16);
            } else {
                ctx1.Update(password);
            }

            finalState = ctx1.Final();
        }

        /* Now make the output string */

        StringBuffer result = new StringBuffer();

        result.append(magic);
        result.append(salt);
        result.append("$");

        l = (bytes2u(finalState[0]) << 16) | (bytes2u(finalState[6]) << 8) | bytes2u(finalState[12]);
        result.append(to64(l, 4));

        l = (bytes2u(finalState[1]) << 16) | (bytes2u(finalState[7]) << 8) | bytes2u(finalState[13]);
        result.append(to64(l, 4));

        l = (bytes2u(finalState[2]) << 16) | (bytes2u(finalState[8]) << 8) | bytes2u(finalState[14]);
        result.append(to64(l, 4));

        l = (bytes2u(finalState[3]) << 16) | (bytes2u(finalState[9]) << 8) | bytes2u(finalState[15]);
        result.append(to64(l, 4));

        l = (bytes2u(finalState[4]) << 16) | (bytes2u(finalState[10]) << 8) | bytes2u(finalState[5]);
        result.append(to64(l, 4));

        l = bytes2u(finalState[11]);
        result.append(to64(l, 2));

        /* Don't leave anything around in vm they could use. */
        clearbits(finalState);

        return result.toString();
    }

    /**
     * Implementation of RSA's MD5 hash generator
     *
     * @author Santeri Paavolainen <sjpaavol@cc.helsinki.fi>
     * @version $Revision: 1.1 $
     */

    private static class MD5 {
        /**
         * MD5 state
         */
        MD5State state;

        /**
         * If Final() has been called, finals is set to the current finals
         * state. Any Update() causes this to be set to null.
         */
        MD5State finals;

        /**
         * Padding for Final()
         */
        static byte padding[] = {
                (byte) 0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        /**
         * Initialize MD5 internal state (object can be reused just by
         * calling Init() after every Final()
         */
        public synchronized void Init() {
            state = new MD5State();
            finals = null;
        }

        /**
         * Class constructor
         */
        public MD5() {
            this.Init();
        }

        /**
         * Initialize class, and update hash with ob.toString()
         *
         * @param ob Object, ob.toString() is used to update hash
         *           after initialization
         */
        public MD5(Object ob) {
            this();
            Update(ob.toString());
        }

        public String debugDump() {
            return asHex();
        }

        private int rotate_left(int x, int n) {
            return (x << n) | (x >>> (32 - n));
        }

        /* I wonder how many loops and hoops you'll have to go through to
    get unsigned add for longs in java */

        private int uadd(int a, int b) {
            long aa, bb;
            aa = ((long) a) & 0xffffffffL;
            bb = ((long) b) & 0xffffffffL;

            aa += bb;

            return (int) (aa & 0xffffffffL);
        }

        private int uadd(int a, int b, int c) {
            return uadd(uadd(a, b), c);
        }

        private int uadd(int a, int b, int c, int d) {
            return uadd(uadd(a, b, c), d);
        }

        private int FF(int a, int b, int c, int d, int x, int s, int ac) {
            a = uadd(a, ((b & c) | (~b & d)), x, ac);
            return uadd(rotate_left(a, s), b);
        }

        private int GG(int a, int b, int c, int d, int x, int s, int ac) {
            a = uadd(a, ((b & d) | (c & ~d)), x, ac);
            return uadd(rotate_left(a, s), b);
        }

        private int HH(int a, int b, int c, int d, int x, int s, int ac) {
            a = uadd(a, (b ^ c ^ d), x, ac);
            return uadd(rotate_left(a, s), b);
        }

        private int II(int a, int b, int c, int d, int x, int s, int ac) {
            a = uadd(a, (c ^ (b | ~d)), x, ac);
            return uadd(rotate_left(a, s), b);
        }

        private int[] Decode(byte buffer[], int len, int shift) {
            int out[];
            int i, j;

            out = new int[16];

            for (i = j = 0; j < len; i++, j += 4) {
                out[i] = ((int) (buffer[j + shift] & 0xff)) |
                        (((int) (buffer[j + 1 + shift] & 0xff)) << 8) |
                        (((int) (buffer[j + 2 + shift] & 0xff)) << 16) |
                        (((int) (buffer[j + 3 + shift] & 0xff)) << 24);
            }

            return out;
        }

        private void Transform(MD5State state, byte buffer[], int shift) {
            int
                    a = state.state[0],
                    b = state.state[1],
                    c = state.state[2],
                    d = state.state[3],
                    x[];

            x = Decode(buffer, 64, shift);

            /* Round 1 */
            a = FF(a, b, c, d, x[0], 7, 0xd76aa478); /* 1 */
            d = FF(d, a, b, c, x[1], 12, 0xe8c7b756); /* 2 */
            c = FF(c, d, a, b, x[2], 17, 0x242070db); /* 3 */
            b = FF(b, c, d, a, x[3], 22, 0xc1bdceee); /* 4 */
            a = FF(a, b, c, d, x[4], 7, 0xf57c0faf); /* 5 */
            d = FF(d, a, b, c, x[5], 12, 0x4787c62a); /* 6 */
            c = FF(c, d, a, b, x[6], 17, 0xa8304613); /* 7 */
            b = FF(b, c, d, a, x[7], 22, 0xfd469501); /* 8 */
            a = FF(a, b, c, d, x[8], 7, 0x698098d8); /* 9 */
            d = FF(d, a, b, c, x[9], 12, 0x8b44f7af); /* 10 */
            c = FF(c, d, a, b, x[10], 17, 0xffff5bb1); /* 11 */
            b = FF(b, c, d, a, x[11], 22, 0x895cd7be); /* 12 */
            a = FF(a, b, c, d, x[12], 7, 0x6b901122); /* 13 */
            d = FF(d, a, b, c, x[13], 12, 0xfd987193); /* 14 */
            c = FF(c, d, a, b, x[14], 17, 0xa679438e); /* 15 */
            b = FF(b, c, d, a, x[15], 22, 0x49b40821); /* 16 */

            /* Round 2 */
            a = GG(a, b, c, d, x[1], 5, 0xf61e2562); /* 17 */
            d = GG(d, a, b, c, x[6], 9, 0xc040b340); /* 18 */
            c = GG(c, d, a, b, x[11], 14, 0x265e5a51); /* 19 */
            b = GG(b, c, d, a, x[0], 20, 0xe9b6c7aa); /* 20 */
            a = GG(a, b, c, d, x[5], 5, 0xd62f105d); /* 21 */
            d = GG(d, a, b, c, x[10], 9, 0x2441453); /* 22 */
            c = GG(c, d, a, b, x[15], 14, 0xd8a1e681); /* 23 */
            b = GG(b, c, d, a, x[4], 20, 0xe7d3fbc8); /* 24 */
            a = GG(a, b, c, d, x[9], 5, 0x21e1cde6); /* 25 */
            d = GG(d, a, b, c, x[14], 9, 0xc33707d6); /* 26 */
            c = GG(c, d, a, b, x[3], 14, 0xf4d50d87); /* 27 */
            b = GG(b, c, d, a, x[8], 20, 0x455a14ed); /* 28 */
            a = GG(a, b, c, d, x[13], 5, 0xa9e3e905); /* 29 */
            d = GG(d, a, b, c, x[2], 9, 0xfcefa3f8); /* 30 */
            c = GG(c, d, a, b, x[7], 14, 0x676f02d9); /* 31 */
            b = GG(b, c, d, a, x[12], 20, 0x8d2a4c8a); /* 32 */

            /* Round 3 */
            a = HH(a, b, c, d, x[5], 4, 0xfffa3942); /* 33 */
            d = HH(d, a, b, c, x[8], 11, 0x8771f681); /* 34 */
            c = HH(c, d, a, b, x[11], 16, 0x6d9d6122); /* 35 */
            b = HH(b, c, d, a, x[14], 23, 0xfde5380c); /* 36 */
            a = HH(a, b, c, d, x[1], 4, 0xa4beea44); /* 37 */
            d = HH(d, a, b, c, x[4], 11, 0x4bdecfa9); /* 38 */
            c = HH(c, d, a, b, x[7], 16, 0xf6bb4b60); /* 39 */
            b = HH(b, c, d, a, x[10], 23, 0xbebfbc70); /* 40 */
            a = HH(a, b, c, d, x[13], 4, 0x289b7ec6); /* 41 */
            d = HH(d, a, b, c, x[0], 11, 0xeaa127fa); /* 42 */
            c = HH(c, d, a, b, x[3], 16, 0xd4ef3085); /* 43 */
            b = HH(b, c, d, a, x[6], 23, 0x4881d05); /* 44 */
            a = HH(a, b, c, d, x[9], 4, 0xd9d4d039); /* 45 */
            d = HH(d, a, b, c, x[12], 11, 0xe6db99e5); /* 46 */
            c = HH(c, d, a, b, x[15], 16, 0x1fa27cf8); /* 47 */
            b = HH(b, c, d, a, x[2], 23, 0xc4ac5665); /* 48 */

            /* Round 4 */
            a = II(a, b, c, d, x[0], 6, 0xf4292244); /* 49 */
            d = II(d, a, b, c, x[7], 10, 0x432aff97); /* 50 */
            c = II(c, d, a, b, x[14], 15, 0xab9423a7); /* 51 */
            b = II(b, c, d, a, x[5], 21, 0xfc93a039); /* 52 */
            a = II(a, b, c, d, x[12], 6, 0x655b59c3); /* 53 */
            d = II(d, a, b, c, x[3], 10, 0x8f0ccc92); /* 54 */
            c = II(c, d, a, b, x[10], 15, 0xffeff47d); /* 55 */
            b = II(b, c, d, a, x[1], 21, 0x85845dd1); /* 56 */
            a = II(a, b, c, d, x[8], 6, 0x6fa87e4f); /* 57 */
            d = II(d, a, b, c, x[15], 10, 0xfe2ce6e0); /* 58 */
            c = II(c, d, a, b, x[6], 15, 0xa3014314); /* 59 */
            b = II(b, c, d, a, x[13], 21, 0x4e0811a1); /* 60 */
            a = II(a, b, c, d, x[4], 6, 0xf7537e82); /* 61 */
            d = II(d, a, b, c, x[11], 10, 0xbd3af235); /* 62 */
            c = II(c, d, a, b, x[2], 15, 0x2ad7d2bb); /* 63 */
            b = II(b, c, d, a, x[9], 21, 0xeb86d391); /* 64 */

            state.state[0] += a;
            state.state[1] += b;
            state.state[2] += c;
            state.state[3] += d;
        }

        /**
         * Updates hash with the bytebuffer given (using at maximum length bytes from
         * that buffer)
         *
         * @param state  Which state is updated
         * @param buffer Array of bytes to be hashed
         * @param offset Offset to buffer array
         * @param length Use at maximum `length' bytes (absolute
         *               maximum is buffer.length)
         */
        public void Update(MD5State state, byte buffer[], int offset, int length) {
            int index, partlen, i, start;

            finals = null;

            /* Length can be told to be shorter, but not inter */
            if ((length - offset) > buffer.length)
                length = buffer.length - offset;

            /* compute number of bytes mod 64 */
            index = (int) (state.count[0] >>> 3) & 0x3f;

            if ((state.count[0] += (length << 3)) <
                    (length << 3))
                state.count[1]++;

            state.count[1] += length >>> 29;

            partlen = 64 - index;

            if (length >= partlen) {
                for (i = 0; i < partlen; i++)
                    state.buffer[i + index] = buffer[i + offset];

                Transform(state, state.buffer, 0);

                for (i = partlen; (i + 63) < length; i += 64)
                    Transform(state, buffer, i);

                index = 0;
            } else
                i = 0;

            /* buffer remaining input */
            if (i < length) {
                start = i;
                for (; i < length; i++)
                    state.buffer[index + i - start] = buffer[i + offset];
            }
        }

        /*
        * Update()s for other datatypes than byte[] also. Update(byte[], int)
        * is only the main driver.
        */

        /**
         * Plain update, updates this object
         */

        public void Update(byte buffer[], int offset, int length) {
            Update(this.state, buffer, offset, length);
        }

        public void Update(byte buffer[], int length) {
            Update(this.state, buffer, 0, length);
        }

        /**
         * Updates hash with given array of bytes
         *
         * @param buffer Array of bytes to use for updating the hash
         */
        public void Update(byte buffer[]) {
            Update(buffer, 0, buffer.length);
        }

        /**
         * Updates hash with a single byte
         *
         * @param b Single byte to update the hash
         */
        public void Update(byte b) {
            byte buffer[] = new byte[1];
            buffer[0] = b;

            Update(buffer, 1);
        }

        /**
         * Update buffer with given string.
         *
         * @param s String to be update to hash (is used as
         *          s.getBytes())
         */
        public void Update(String s) {
            byte chars[];

            chars = s.getBytes();

            Update(chars, chars.length);
        }

        private byte[] Encode(int input[], int len) {
            int i, j;
            byte out[];

            out = new byte[len];

            for (i = j = 0; j < len; i++, j += 4) {
                out[j] = (byte) (input[i] & 0xff);
                out[j + 1] = (byte) ((input[i] >>> 8) & 0xff);
                out[j + 2] = (byte) ((input[i] >>> 16) & 0xff);
                out[j + 3] = (byte) ((input[i] >>> 24) & 0xff);
            }

            return out;
        }

        /**
         * Returns array of bytes (16 bytes) representing hash as of the
         * current state of this object. Note: getting a hash does not
         * invalidate the hash object, it only creates a copy of the real
         * state which is finalized.
         *
         * @return Array of 16 bytes, the hash of all updated bytes
         */
        public synchronized byte[] Final() {
            byte bits[];
            int index, padlen;
            MD5State fin;

            if (finals == null) {
                fin = new MD5State(state);

                bits = Encode(fin.count, 8);

                index = (int) ((fin.count[0] >>> 3) & 0x3f);
                padlen = (index < 56) ? (56 - index) : (120 - index);

                Update(fin, padding, 0, padlen);
                 /**/
                Update(fin, bits, 0, 8);

                /* Update() sets finalds to null */
                finals = fin;
            }

            return Encode(finals.state, 16);
        }

        /**
         * Turns array of bytes into string representing each byte as
         * unsigned hex number.
         *
         * @param hash Array of bytes to convert to hex-string
         * @return Generated hex string
         */
        public static String asHex(byte hash[]) {
            StringBuffer buf = new StringBuffer(hash.length * 2);
            int i;

            for (i = 0; i < hash.length; i++) {
                if (((int) hash[i] & 0xff) < 0x10)
                    buf.append("0");

                buf.append(Long.toString((int) hash[i] & 0xff, 16));
            }

            return buf.toString();
        }

        /**
         * Returns 32-character hex representation of this objects hash
         *
         * @return String of this object's hash
         */
        public String asHex() {
            return asHex(this.Final());
        }

        /**
         * One-stop md5 string encrypting.
         */

        public static String md5crypt(String input) {
            MD5 md5 = new MD5();
            md5.Init();
            md5.Update(input);
            return md5.asHex();
        }
    }

    /**
     * @author Artie Copeland
     * @version $Revision: $
     */
    static class MD5State {
        /**
         * 128-byte state
         */
        int state[];

        /**
         * 64-bit character count (could be true Java long?)
         */
        int count[];

        /**
         * 64-byte buffer (512 bits) for storing to-be-hashed characters
         */
        byte buffer[];

        public MD5State() {
            buffer = new byte[64];
            count = new int[2];
            state = new int[4];

            state[0] = 0x67452301;
            state[1] = 0xefcdab89;
            state[2] = 0x98badcfe;
            state[3] = 0x10325476;

            count[0] = count[1] = 0;
        }

        /**
         * Create this State as a copy of another state
         */
        MD5State(MD5State from) {
            this();

            int i;

            for (i = 0; i < buffer.length; i++)
                this.buffer[i] = from.buffer[i];

            for (i = 0; i < state.length; i++)
                this.state[i] = from.state[i];

            for (i = 0; i < count.length; i++)
                this.count[i] = from.count[i];
        }
    }
}
