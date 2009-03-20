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

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang.BooleanUtils.toBoolean;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.defaultString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.getProperty;

/**
 * A simple Filter that will download a file from the file system.  It currently doesnt try to be smart about setting the
 * http content type it does set the content length.
 * <br/>
 * Init parameters
 * <ul>
 * <li>deleteAfterDownload - whether to delete the file after successful download (default : false)</li>
 * <li>baseDirectory - base directory where to find the files (default : java.io.tmpdir system property) </li>
 * </ul>
 * The request parameter must be: file
 *  
 * @author Artie Copeland
 * @version $Revision: $
 */
public class FileSystemFileDownloadFilter implements Filter {
    final private static Logger logger = LoggerFactory.getLogger(FileSystemFileDownloadFilter.class);
    
    private boolean deleteAfterDownload;
    private File baseDirectory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        deleteAfterDownload = toBoolean(defaultString(filterConfig.getInitParameter("deleteAfterDownload"), "false"),
                "true", "false");
        String tempBaseDir = defaultString(filterConfig.getInitParameter("baseDirectory"), getProperty("java.io.tmpdir"));
        baseDirectory = new File(tempBaseDir);
    }

    public boolean isDeleteAfterDownload() {
        return deleteAfterDownload;
    }

    public void setDeleteAfterDownload(boolean deleteAfterDownload) {
        this.deleteAfterDownload = deleteAfterDownload;
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String fileName = request.getParameter("file");
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = response.getOutputStream();
        if (StringUtils.isNotBlank(fileName)) {
            try {

                File downloadFile = new File(baseDirectory, fileName);
                response.setContentLength((int) downloadFile.length());
                inputStream = openInputStream(downloadFile);
                copy(inputStream, outputStream);
                outputStream.flush();
                if (deleteAfterDownload) {
                    downloadFile.delete();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            finally {
                closeQuietly(inputStream);
                closeQuietly(outputStream);
            }


        }
    }

    @Override
    public void destroy() {
    }
}
