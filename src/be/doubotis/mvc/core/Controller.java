/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import be.doubotis.http.exceptions.HTTPException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Represents basically a "web page".
 * You must override this to create your webpages. Each controller is another
 * web page. To tell the system which controller to use, you must set
 * a {@link CPath} annotation to tell the controller to be created when the
 * specified path in the annotation is covered. You can also set a 
 * {@link CError} annotation to tell the controller to be displayed when
 * an error of the specified error code went.
 */
public abstract class Controller
{
    private HttpServletRequest mRequest;
    private HttpServletResponse mResponse;
    private ServletControllerContext mContext;
    
    public final void setParams(ServletControllerContext context,
            HttpServletRequest request, HttpServletResponse response)
    {
        mContext = context;
        mRequest = request;
        mResponse = response;
    }
    
    /** Get the {@link HttpServletRequest} of the Controller Servlet. */
    public final HttpServletRequest getRequest()
    {
        return mRequest;
    }
    
    /** Get the {@link HttpServletResponse} from the Controller Servlet. */
    public final HttpServletResponse getResponse()
    {
        return mResponse;
    }
    
    /** Get the context of the Controller Servlet, allowing you to know what
     * happened while building the controller.
     * @return A context, allowing you to get the throwed exception if
     * something fails, and more.
     */
    public final ServletControllerContext getContext()
    {
        return mContext;
    }
    
    /** You must override this method and prepares here all the data that
     * will be displayed on the JSP.
     * @param tpl A {@link Template} object that you must use to tell to
     * the controller what to display on the JSP.<br/><br/>
     * For instance:<br/>
     * <code>tpl.assign("myVariable",maVariable);</code><br/>
     * By setting this, inside the JSP, you will be able to use
     * <code>${myVariable}</code> to get the variable instance.
     */
    public abstract void build(Template tpl);
    
}
