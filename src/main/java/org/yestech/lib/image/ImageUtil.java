package org.yestech.lib.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 *
 */
public final class ImageUtil
{
    private ImageUtil() {
    }

    public static void crop (String format, InputStream in, OutputStream out,
                             int x, int y, int width, int height) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(in);
        BufferedImage subImage = bufferedImage.getSubimage(x, y, width, height);
        ImageIO.write(subImage, format, out);
    }


}
