/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.ibatis;

import com.ibatis.sqlmap.engine.cache.CacheModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.Properties;

/*
 *
 * Author:  Greg Crow
 * Last Modified Date: $DateTime: $
 */
public class EhCacheControllerUnitTest {
    Properties props;
    CacheModel model;
    @Before
    public void setup()
    {
        props = new Properties();
        model = new CacheModel();
        model.setId("testcache");
        model.setReadOnly(false);
    }

    /**
     * This test will test setting the controllers cache config from the classpath. It then validates
     * that its correct by puttin an object in the cache then getting the object back out and asserting
     * the expected results.
     *
     */
    @Test
    public void testSetPropertiesInitCacheFromClasspath()
    {
        String classpathConfig = "/cache/ehcache.xml";
        EhCacheController controller = new EhCacheController();
        props.setProperty("configFile", classpathConfig);
        controller.setProperties(props);
        controller.putObject(model, "key1", "value1");

        Object value = controller.getObject(model, "key1");
        assertEquals("value1", value.toString());
        controller.flush(model);
    }

    /**
     * This test will test setting the controllers cache config from the file system. It then validates that
     * the configuration is correct by placing an object in the cache then then pulling it back out and asserting
     * that the value is correct.
     */
    @Test
    public void testSetPropertiesInitCacheFromFile() {
        File f = new File("src/test/resources/cache/ehcache.xml");
        EhCacheController controller = new EhCacheController();
        props.setProperty("configFile", f.getAbsolutePath());
        controller.setProperties(props);
        controller.putObject(model, "key2", "value2");

        Object value = controller.getObject(model, "key2");
        assertEquals("value2", value.toString());
        controller.flush(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertiesNullProps() {
        EhCacheController controller = new EhCacheController();
        controller.setProperties(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertiesWithNoConfigFile() {
        Properties myProps = new Properties();

        EhCacheController controller = new EhCacheController();
        controller.setProperties(myProps);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPropertiesWithEmptyConfigFileProp() {
        Properties myProps = new Properties();
        myProps.setProperty("configFile","");
        EhCacheController controller = new EhCacheController();
        controller.setProperties(myProps);
    }
}
