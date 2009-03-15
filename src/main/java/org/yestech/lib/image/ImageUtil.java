package org.yestech.lib.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Image Manipulation Utility Methods
 *
 */
public final class ImageUtil {
    final private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    final private static String SLASH = "/";

    private ImageUtil() {
    }

    public static void crop(String format, InputStream in, OutputStream out,
                            int x, int y, int width, int height) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(in);
        BufferedImage subImage = bufferedImage.getSubimage(x, y, width, height);
        ImageIO.write(subImage, format, out);
    }

    public static void resizeAndSaveToDisk(File file, String outImageFileName,
                                           int height, int width, int imageQuality) throws IOException {
        byte imageAsBytes[] = FileUtils.readFileToByteArray(file);
        resizeAndSaveToDisk(imageAsBytes, outImageFileName, height, width, imageQuality);
    }

    public static void resizeAndSaveToDisk(String inputFileFQN, String outImageFileName,
                                           int height, int width, int imageQuality) throws IOException {
        resizeAndSaveToDisk(new File(inputFileFQN), outImageFileName, height, width, imageQuality);
    }

    public static void resizeAndSaveToDisk(byte[] bytes, String outImageFileName,
                                           int height, int width, int imageQuality) {

        if (logger.isDebugEnabled()) {
            logger.debug("--- resizeAndSaveToDisk() ---");
            logger.debug("  - outImageFileName: " + outImageFileName);
            logger.debug("  - height: " + height);
            logger.debug("  - width: " + width);
            logger.debug("  - imageQuality: " + imageQuality);
        }

        BufferedOutputStream out = null;
        try {

            Image image = Toolkit.getDefaultToolkit().createImage(bytes);
            MediaTracker mediaTracker = new MediaTracker(new Container());
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForID(0);

            // determine thumbnail size from WIDTH and HEIGHT
            int thumbWidth = width * 2;
            int thumbHeight = height * 2;
            double thumbRatio = (double) thumbWidth / (double) thumbHeight;
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double imageRatio = (double) imageWidth / (double) imageHeight;

            if (thumbRatio < imageRatio) {
                thumbHeight = (int) (thumbWidth / imageRatio);
            } else {
                thumbWidth = (int) (thumbHeight * imageRatio);
            }

            // draw original image to thumbnail image object and
            // scale it to the new size on-the-fly
            BufferedImage thumbImage = new BufferedImage(thumbWidth,
                    thumbHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = thumbImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (logger.isDebugEnabled()) {
                logger.debug("  image being saved as:");
                logger.debug("  - height: " + thumbHeight);
                logger.debug("  - width: " + thumbWidth);
            }
            graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

            // fixes problem on Unix deployments
            if (outImageFileName.indexOf("//") != 0) {
                logger.debug("  double quotes found in path");
            } else {
                logger.debug("  No double quotes found in path");
            }
            outImageFileName = StringUtils.replace(outImageFileName, "//", SLASH);

            // save thumbnail image to OUTFILE
            out = new BufferedOutputStream(
                    new FileOutputStream(outImageFileName));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
            int quality = imageQuality;
            quality = Math.max(0, Math.min(quality, 100));
            //param.setQuality((float)quality / 100.0f, false);
            param.setQuality((float) 1.0, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(thumbImage);

        } catch (Exception e) {
            logger.error("*** " + e.getClass().getName() + " occurred ***", e);
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * Utility method that converts Image object to BufferedImage object
     *
     * @param image    Image object
     * @param useAlpha boolean: TRUE if Alpha blending desired. FALSE otherwise.
     * @return BufferedImage
     */
    public static BufferedImage convertImageToBufferedImage(Image image, boolean useAlpha) {
        BufferedImage bi = null;

        //For JPG without alpha-blending, use TYPE_INT_RGB, else use TYPE_INT_ARGB:
        if (useAlpha) {
            bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        } else {
            bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        }

        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose(); //Be nice and clean up after ourselves
        return bi;
    }

    /**
     * Wrapper for method getByteArrayFromFile( File imageFile ) that takes String for the image filename instead of a File object of the image itself.
     *
     * @param inputImageFileName String
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getByteArrayFromFile(String inputImageFileName) throws IOException {
        return FileUtils.readFileToByteArray(new File(inputImageFileName));
    }
}
