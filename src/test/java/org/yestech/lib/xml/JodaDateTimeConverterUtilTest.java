package org.yestech.lib.xml;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.DateTime;

/**
 *
 *
 */
public class JodaDateTimeConverterUtilTest
{
    @Test
    public void testCanConvert()

    {
        JodaDateTimeConverter converter = new JodaDateTimeConverter(DateTimeFormat.fullDateTime());
        assertTrue(converter.canConvert(DateTime.class));
    }

    @Test
    public void testToString()
    {
        DateTimeFormatter formatter = DateTimeFormat.fullDateTime();
        JodaDateTimeConverter converter = new JodaDateTimeConverter(formatter);
        DateTime dt = new DateTime(8238923L);
        String result = converter.toString(dt);
        assertEquals(dt.toString(formatter), result);
    }

    @Test
    public void testFromString()
    {
        DateTimeFormatter formatter = DateTimeFormat.fullDate();
        DateTime dt = new DateTime(8238923L);
        String s = dt.toString(formatter);

        JodaDateTimeConverter converter = new JodaDateTimeConverter(formatter);
        DateTime result = (DateTime) converter.fromString(s);
        assertEquals(dt.getYear(), result.getYear());
        assertEquals(dt.getMonthOfYear(), result.getMonthOfYear());
        assertEquals(dt.getDayOfYear(), result.getDayOfYear());
    }
}
