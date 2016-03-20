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

/** Classe représentant une page JSP standard.
 * Elle peut définir les caractéristiques d'une JSP afin de compléter les pagelets Header et Footer par exemple.
 * Gère également l'accès aux pages et les permissions qui en découlent.
 * @author Christophe
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
    
    public final HttpServletRequest getRequest()
    {
        return mRequest;
    }
    
    public final HttpServletResponse getResponse()
    {
        return mResponse;
    }
    
    public final ServletControllerContext getContext()
    {
        return mContext;
    }
    
    public abstract void build(Template tpl);
    
}
