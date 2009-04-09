package org.yestech.lib.hibernate;

/**
 *
 *
 */
public class BaseHibernateDao<T>
{

    protected YesHibernateOperations template;

    public void setTemplate(YesHibernateOperations template)
    {
        this.template = template;
    }

    protected YesHibernateOperations getTemplate()
    {
        return template;
    }

    public void save(T object)
    {

        object.getClass().
        if (object.getIdentifier() == null)
        {

            
        }
        


    }
}
