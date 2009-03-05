/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.io;

import java.io.InputStream;
import java.net.URL;


/**
 * This class will find any file located in the users classpath.
 *<br>
 *
 * @author Arthur Copeland
 *
 */
final public class FileLocator {

    //--------------------------------------------------------------------------
    // S T A T I C   V A R I A B L E S
    //--------------------------------------------------------------------------
    /**
     * Holds the default filename -> extension delimeter
     */
    private final static String FILE_EXTENSION_DELIMETER = ".";

    /**
     * Default Ctor. Creates a new file locator
     */
    private FileLocator() {
        super();
    }

    /**
     * Locates a properties file in the classes classpath and returns the URL
     * associated with the file.
     *
     * @param file the file to locate
     * @return the URL associated with the file or NULL if file not found
     */
    public URL locatePropertiesFile(String file) {
        return locateFile(file, "properties");
    }

    /**
     * Locates an xml file in the classes classpath and returns the URL
     * associated with the file.
     *
     * @param file the file to locate
     * @return the URL associated with the file or NULL if file not found
     */
    public URL locateXMLFile(String file) {
        return locateFile(file, "xml");
    }

    /**
     * Locates a file in the classes classpath and returns the URL
     * associated with the file.  If the file doesn't have an extension then the
     * extension parameter <b>MUST</b> be NULL.
     *
     * @param file the file to locate
     * @param extension the extension of the file to locate
     * @return the URL associated with the file or NULL if file not found
     */
    public URL locateFile(String file, String extension) {
        return getClassLoader().getResource(getFileName(file,extension));
    }

    /**
     * Locates a file in the classes classpath and returns the URL
     * associated with the file.
     *
     * @param file the file to locate
     * @return the URL associated with the file or NULL if file not found
     */
    public URL locateFile(String file) {
        return locateFile(file, null);
    }

    /**
     * Locates a properties file in the classes classpath and returns the
     * InputStream associated with the file.
     *
     * @param file the file to locate
     * @return the InputStream associated with the file or NULL if file not
     * found
     */
    public InputStream locatePropertiesFileAsStream(String file) {
        return locateFileAsStream(file, "properties");
    }

    /**
     * Locates an xml file in the classes classpath and returns the InputStream
     * associated with the file.
     *
     * @param file the file to locate
     * @return the InputStream associated with the file or NULL if file not
     * found
     */
    public InputStream locateXMLFileAsStream(String file) {
        return locateFileAsStream(file, "xml");
    }

    /**
     * Locates a file in the classes classpath and returns the
     * InputStream associated with the file.
     *
     * @param file the file to locate
     * @param extension the extension of the file to locate
     * @return the InputStream associated with the file or NULL if file not
     * found
     */
    public InputStream locateFileAsStream(String file,
                                                 String extension) {
        return getClassLoader().getResourceAsStream(getFileName(file,
                                                                extension));
    }

    /**
     * Locates a file in the classes classpath and returns the
     * InputStream associated with the file.
     *
     * @param file the file to locate
     * @return the InputStream associated with the file or NULL if file not
     * found
     */
    public InputStream locateFileAsStream(String file) {
        return locateFileAsStream(file, null);
    }

    //--------------------------------------------------------------------------
    // F A C T O R Y   M E T H O D S
    //--------------------------------------------------------------------------
    /**
     * Creates a new FileLocator
     *
     * @return new FileLocator
     */
    public static FileLocator getFile() {
        return new FileLocator();
    }

    //--------------------------------------------------------------------------
    // I N T E R N A L   M E T H O D S
    //--------------------------------------------------------------------------
    /**
     * Return the formated name of the file to find
     *
     * @param file FileName of the file to find
     * @param extension extension to the file name
     * @return the properly formatted filename to locate
     */
    private String getFileName(String file, String extension) {
        String fileName = file;
        if (extension != null &&
            !extension.startsWith(FILE_EXTENSION_DELIMETER)) {
            fileName += FILE_EXTENSION_DELIMETER + extension;
        }
        return fileName;
    }

    /**
     * Retrieves the ClassLoader to use when trying to locate the resource.
     */
    private ClassLoader getClassLoader() {
        ClassLoader cl = getClass().getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        return cl;
    }

}