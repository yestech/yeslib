package org.yestech.lib.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;

/**
 *
 *
 */
public class JodaDateTimeConverter implements ConverterMatcher, SingleValueConverter
{

    private final DateTimeFormatter dateTimeFormatter;

    public JodaDateTimeConverter(DateTimeFormatter dateTimeFormatter)
    {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter()
    {
        return dateTimeFormatter;
    }

    @Override
    public boolean canConvert(Class type)
    {
        return DateTime.class.isAssignableFrom(type);
    }

    @Override
    public String toString(Object obj)
    {
        DateTime dt = (DateTime) obj;
        return dt.toString(dateTimeFormatter);

    }

    @Override
    public Object fromString(String str)
    {
        return dateTimeFormatter.parseDateTime(str);
    }
}
