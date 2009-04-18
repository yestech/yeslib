package org.yestech.lib.hibernate.client;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 *
 *
 */
public class CollectionReference implements Serializable
{
    private static final long serialVersionUID = 183925776415464990L;
    private Serializable id;
    private String role;

    public Serializable getId()
    {
        return id;
    }

    public void setId(Serializable id)
    {
        this.id = id;
    }

    public String getRole()
    {

        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    Object readResolve() throws ObjectStreamException
    {
        return Proxy.create(this);
    }

}
