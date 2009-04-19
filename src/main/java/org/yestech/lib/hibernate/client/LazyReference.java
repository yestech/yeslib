package org.yestech.lib.hibernate.client;

import java.io.Serializable;
import java.io.ObjectStreamException;

/**
 *
 *
 */
public class LazyReference implements Serializable
{
    private static final long serialVersionUID = 7799616869249915673L;

    private Serializable id;

    private int token;

    private String className;


    public LazyReference()
    {

    }

    public LazyReference(int token, String className, Serializable id)
    {
        this.token = token;
        this.className = className;
        this.id = id;
    }

    /* package */
    Object readResolve() throws ObjectStreamException,
            ClassNotFoundException
    {

        return Proxy.create(this);

    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public Serializable getId()
    {
        return id;
    }

    public void setId(Serializable id)
    {
        this.id = id;
    }

    public int getToken()
    {
        return token;
    }

    public void setToken(int token)
    {
        this.token = token;
    }

}
