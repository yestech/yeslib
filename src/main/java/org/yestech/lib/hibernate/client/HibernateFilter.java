package org.yestech.lib.hibernate.client;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 *
 */
public class HibernateFilter implements Filter
{

    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException
    {

        try
        {
            ClientSession.execute(
                    new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                chain.doFilter(request, response);
                            }
                            catch (RuntimeException re)
                            {
                                throw re;
                            }
                            catch (Exception e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
        }
        catch (RuntimeException re)
        {
            throw re;
        }
        catch (Exception e)
        {
            throw new ServletException(e);
        }
    }

    public void destroy()
    {
    }

    public void init(FilterConfig cfg) throws ServletException
    {

        ServletContext context = cfg.getServletContext();
        WebApplicationContext appContext =
                (WebApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ClientSession.init(appContext);
    }
}
