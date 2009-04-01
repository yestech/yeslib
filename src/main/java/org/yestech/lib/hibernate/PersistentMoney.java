/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.yestech.lib.currency.Money;

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
public class PersistentMoney implements UserType {

    public final static PersistentMoney INSTANCE = new PersistentMoney();

    public PersistentMoney() {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private static final int[] SQL_TYPES = new int[]
            {
                    Types.NUMERIC, Types.VARCHAR
            };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return Money.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        Money uidx = (Money) x;
        Money uidy = (Money) y;

        return uidx.equals(uidy);
    }

    @Override
    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object object) throws HibernateException, SQLException {
        throw new UnsupportedOperationException("not yet implemented");
//        Object uuid = Hibernate.STRING.nullSafeGet(resultSet, string);
//        if (uuid == null) {
//            return null;
//        }
//
//        return UUID.fromString((String) uuid);

    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        Money existing = (Money) value;
        return new Money(existing.getAmount(),existing.getLocale());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}