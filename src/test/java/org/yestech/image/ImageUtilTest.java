package org.yestech.image;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static junit.framework.Assert.assertTrue;

/**
 *
 *
 */
public class ImageUtilTest
{
    private static final Logger log = LoggerFactory.getLogger(ImageUtilTest.class);

    @Test
    public void testCrop() throws IOException
    {

        InputStream in = getClass().getResourceAsStream("/aj.jpg");
        File tempFile = File.createTempFile("ImageUtil", "test");

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));

        ImageUtil.crop("jpg", in, out, 15, 20, 10, 10);

        in.close();
        out.close();


        if (!Boolean.getBoolean("ImageUtil.dontDelete")) {
            assertTrue(tempFile.delete());
        }
        else {
            log.info("File was not deleted it can be found at "+tempFile.getPath());
        }
    }
}
