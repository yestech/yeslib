package org.yestech.lib.i18n;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 *
 *
 */
public class PostalCodeUtilTest
{
    @Test
    public void testIsValid()
    {

        CountryEnum countryEnum = CountryEnum.UNITED_STATES;
        assertTrue(PostalCodeUtil.isValid("98903", countryEnum));
        assertTrue(PostalCodeUtil.isValid("32349-2333", countryEnum));
        assertFalse(PostalCodeUtil.isValid("sdf243", countryEnum));
        assertFalse(PostalCodeUtil.isValid(null, countryEnum));
        assertFalse(PostalCodeUtil.isValid("", countryEnum));
        assertFalse(PostalCodeUtil.isValid("12345", CountryEnum.AFGHANISTAN));

    }
}
