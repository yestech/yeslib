/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Original Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.xml;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author $Author: $
 * @version $Revision: $
 */
public class XmlUtilsUnitTest {

    @Before
    public void setUp() {
        // Add your code here
    }

    @After
    public void tearDown() {
        // Add your code here
    }

    @Test
    public void testToJSon() {
        TestObject test = new TestObject();
        test.setParam1("This is a String");
        test.setParam2(100);
        test.setParam3(500000000);
        String json = XmlUtils.toJSon(test);
        assertNotNull(json);
        assertEquals(
                "{\"org.yestech.lib.xml.XmlUtilsUnitTest_-TestObject\":{\"param1\":\"This is a String\",\"param2\":100,\"param3\":500000000}}",
                json);
    }

    @Test
    public void testNullXml() {
        assertEquals("", XmlUtils.fromXml(null));
    }

    @Test
    public void testEmptyXml() {
        assertEquals("", XmlUtils.fromXml(""));
    }

    @Test
    public void testXmlLargeObject() {
        ArrayList<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
        int rows = 30;
        for (int i = 0; i < rows; i++) {
            Map<String, Object> row = new HashMap<String, Object>();
            row.put(i + "[1]", i + "[1]: " + Math.random());
            row.put(i + "[2]", i + "[2]: " + Math.random());
            row.put(i + "[3]", i + "[3]: " + Math.random());
            report.add(row);
        }
        String xmlText = XmlUtils.toXml(report);
        assertTrue(StringUtils.isNotBlank(xmlText));
        ArrayList<Map<String, Object>> tempReport = XmlUtils.fromXml(xmlText);
        assertEquals(rows, tempReport.size());

    }

    @Test
    public void testXmlString() {
        String t = "testing";
        String xml = XmlUtils.toXml(t);
        assertEquals("<string>testing</string>", xml);
    }

    @Test
    public void testToXmlUsingJaxb() {
        TestObject test = new TestObject();
        test.setParam1("This is a String");
        test.setParam2(100);
        test.setParam3(500000000);
        String xml = XmlUtils.toXmlJaxb(test);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testObject><param1>This is a String</param1><param2>100</param2><param3>500000000</param3></testObject>", xml);
    }

    @Test
    public void testFromXmlUsingJaxb() {
        TestObject test = new TestObject();
        test.setParam1("This is a String");
        test.setParam2(100);
        test.setParam3(500000000);
        TestObject result = XmlUtils.fromXmlJaxb(TestObject.class,"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testObject><param1>This is a String</param1><param2>100</param2><param3>500000000</param3></testObject>");
        assertEquals(test, result);
    }

    @Test
    public void testFromJSon() {
        String jsonText = "{\"org.yestech.lib.xml.XmlUtilsUnitTest_-TestObject\":{\"param1\":\"This is a String\",\"param2\":100,\"param3\":500000000}}";
        TestObject test = new TestObject();
        test.setParam1("This is a String");
        test.setParam2(100);
        test.setParam3(500000000);
        TestObject json = (TestObject) XmlUtils.fromJSon(jsonText);
        assertNotNull(json);
        assertEquals(test, json);
    }

    @Test
    public void testIgnoreInvalidFlieds() {
        TestObject expected = new TestObject();
        String xml = "<org.yestech.lib.xml.XmlUtilsUnitTest_-TestObject>\n" +
                "  <param2>0</param2>\n" +
                "  <param3>0</param3>\n" +
                "  <invalid>0</invalid>\n" +
                "</org.yestech.lib.xml.XmlUtilsUnitTest_-TestObject>";
        TestObject to = XmlUtils.fromXml(xml);
        assertNotNull(to);
        assertEquals(expected, to);
    }

    @XmlRootElement
    private static class TestObject {
        private String param1;
        private int param2;
        private long param3;

        public TestObject() {
            super();
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public int getParam2() {
            return param2;
        }

        public void setParam2(int param2) {
            this.param2 = param2;
        }

        public long getParam3() {
            return param3;
        }

        public void setParam3(long param3) {
            this.param3 = param3;
        }

        /*
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((this.param1 == null) ? 0 : this.param1.hashCode());
            result = prime * result + this.param2;
            result = prime * result
                    + (int) (this.param3 ^ (this.param3 >>> 32));
            return result;
        }

        /*
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final TestObject other = (TestObject) obj;
            if (this.param1 == null) {
                if (other.param1 != null)
                    return false;
            } else if (!this.param1.equals(other.param1))
                return false;
            if (this.param2 != other.param2)
                return false;
            if (this.param3 != other.param3)
                return false;
            return true;
        }

    }
}
