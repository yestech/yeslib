/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.ibatis;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class YesNoBooleanTypeHandler implements TypeHandlerCallback {
    private static final String YES = "Y";
    private static final String NO = "N";

    public Object getResult(ResultGetter getter)
            throws SQLException {
        String s = getter.getString();
        if (YES.equalsIgnoreCase(s)) {
            return new Boolean(true);
        } else if (NO.equalsIgnoreCase(s)) {
            return new Boolean(false);
        } else {
            return s;
        }
    }

    public void setParameter(ParameterSetter setter, Object parameter)
            throws SQLException {
        boolean b = ((Boolean) parameter).booleanValue();
        if (b) {
            setter.setString(YES);
        } else {
            setter.setString(NO);
        }
    }

    public Object valueOf(String s) {
        if (YES.equalsIgnoreCase(s)) {
            return new Boolean(true);
        } else if (NO.equalsIgnoreCase(s)) {
            return new Boolean(false);
        } else {
            return s;
        }
    }
}
