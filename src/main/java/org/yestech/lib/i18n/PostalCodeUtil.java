package org.yestech.lib.i18n;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 *
 */
public final class PostalCodeUtil
{

    private PostalCodeUtil() {
    }


    private static final Pattern US_LONG;
    private static final Pattern US_SHORT;

    static {
        US_SHORT= Pattern.compile("[0-9]{5}");
        US_LONG = Pattern.compile("[0-9]{5}\\-[0-9]{4}");
    }

    public static boolean isValid(String code, CountryEnum countryEnum) {
        //noinspection SimplifiableIfStatement
        if (countryEnum == CountryEnum.UNITED_STATES) {
            return isValidUS(code);
        }
        return false;
    }

    public static boolean isValidUS(String code) {
        Matcher matcher = US_SHORT.matcher(code);
        if (matcher.matches()) {
            return true;
        }

        matcher = US_LONG.matcher(code);

        return matcher.matches();
    }

}
