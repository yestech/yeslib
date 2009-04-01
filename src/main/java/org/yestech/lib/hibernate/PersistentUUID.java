/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Hibernate;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PersistentUUID implements EnhancedUserType {

    public final static PersistentUUID INSTANCE = new PersistentUUID();

    private static final int[] SQL_TYPES = new int[]
            {
                    Types.VARCHAR,
            };

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return UUID.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        UUID uidx = (UUID) x;
        UUID uidy = (UUID) y;

        return uidx.equals(uidy);
    }

    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }

    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object object) throws HibernateException, SQLException {
        return nullSafeGet(resultSet, strings[0]);

    }

    public Object nullSafeGet(ResultSet resultSet, String string) throws SQLException {
        Object uuid = Hibernate.STRING.nullSafeGet(resultSet, string);
        if (uuid == null) {
            return null;
        }

        return UUID.fromString((String)uuid);
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            Hibernate.STRING.nullSafeSet(preparedStatement, null, index);
        } else {
            Hibernate.STRING.nullSafeSet(preparedStatement, value.toString(), index);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        return UUID.fromString((String)value);
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public String objectToSQLString(Object object) {
        throw new UnsupportedOperationException();
    }

    public String toXMLString(Object object) {
        return object.toString();
    }

    public Object fromXMLString(String string) {
        return UUID.fromString(string);
    }

}
