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

import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;

import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class BigIntegerTypeHandler implements TypeHandlerCallback {

    public Object getResult(ResultGetter getter) throws SQLException {

        Object o = getter.getObject();
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            return bd.toBigInteger();
        }
        else if (o instanceof String) {
            return new BigInteger((String) o);
        }
        else {
            return o;
        }
    }

    public void setParameter(ParameterSetter setter, Object parameter)
            throws SQLException {
        if (parameter != null && parameter instanceof BigInteger) {
            BigInteger i = (BigInteger) parameter;
            setter.setBigDecimal(new BigDecimal(i));
        }
        else {
            setter.setNull(Types.BIGINT);
        }
    }

    public Object valueOf(String s) {
        return s;
    }
}
