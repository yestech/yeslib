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
package org.yestech.lib.io;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockFilterChain;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import static junit.framework.Assert.assertTrue;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class FileSystemFileDownloadFilterUnitTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterConfig config;
    private MockFilterChain chain;
    private FileSystemFileDownloadFilter filter;

    @Before
    public void setUp() throws ServletException {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockFilterConfig();
        chain = new MockFilterChain();
        filter = new FileSystemFileDownloadFilter();
        filter.init(config);
    }

    @Test
    public void testDefaultSettings() throws IOException, ServletException {
        String testFileName = "unittest.tst";
        File file = new File(System.getProperty("java.io.tmpdir"), testFileName);
        FileWriter writer = new FileWriter(file);
        String text = "testing download";
        writer.write(text);
        writer.flush();
        writer.close();
//        config.addInitParameter("deleteAfterDownload", "false");
//        config.addInitParameter("baseDirectory", "false");
        request.setParameter("file", testFileName);
        filter.doFilter(request, response, chain);
        assertEquals(text.length(), response.getContentLength());
        assertEquals(text, response.getContentAsString());
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testDeleteAfterDownload() throws IOException, ServletException {
        config.addInitParameter("deleteAfterDownload", "true");
        filter.init(config);
        String testFileName = "unittest.tst222";
        File file = new File(System.getProperty("java.io.tmpdir"), testFileName);
        FileWriter writer = new FileWriter(file);
        String text = "testing download with delete";
        writer.write(text);
        writer.flush();
        writer.close();
        request.setParameter("file", testFileName);
        filter.doFilter(request, response, chain);
        assertEquals(text.length(), response.getContentLength());
        assertEquals(text, response.getContentAsString());
        assertFalse(file.exists());
    }
}
