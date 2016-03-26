/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import be.doubotis.http.exceptions.HTTPException;
import be.doubotis.mvc.core.annotations.CError;
import be.doubotis.mvc.core.annotations.CPath;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Christophe
 */
public class ControllerScanner {
    
    private ServletContext mContext;
    private HashMap<String, Class<? extends Controller>> mControllers;
    private HashMap<Integer, Class<? extends Controller>> mErrorControllers;
    
    public ControllerScanner(ServletContext context)
    {
        setup(context);
    }
    
    public static ControllerScanner fromContext(ServletContext context)
    {
        try
        {
            ControllerScanner cs = new ControllerScanner(context);
            return cs;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public Controller buildControllerForURI(String uri)
    {
        try
        {
            
            Class<? extends Controller> controllerClass = 
                    mControllers.get(uri);
            
            Object o = controllerClass.newInstance();
            return (Controller)o;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public Controller buildControllerForException(HTTPException e)
    {
        try
        {
            
            Class<? extends Controller> controllerClass = 
                    mErrorControllers.get(e.getHTTPStatus());
            
            if (controllerClass == null)
            {
                // Has a "all" page?
                controllerClass = mErrorControllers.get(-1);
            }
            
            Object o = controllerClass.newInstance();
            return (Controller)o;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    
    private void setup(ServletContext context)
    {
        try
        {     
            Reflections reflections = new Reflections("");
            Set<Class<? extends Controller>> subTypes = 
                  reflections.getSubTypesOf(Controller.class);
            
            // Prepare the list of controllers.
            mControllers = new HashMap<String, Class<? extends Controller>>();
            mErrorControllers = new HashMap<Integer, Class<? extends Controller>>();
            
            // Loop inside the controllers.
            Iterator<Class<? extends Controller>> it = subTypes.iterator();
            while (it.hasNext())
            {
                Class<? extends Controller> controller = it.next();
                
                CPath pathAnnot = (CPath) 
                        controller.getAnnotation(CPath.class);
                
                if (pathAnnot != null)
                {
                    // This controller can be accessed by normal path.
                    String path = pathAnnot.path();
                    mControllers.put(path, controller);
                }
                else
                {
                    CError errAnnot = (CError) 
                        controller.getAnnotation(CError.class);
                    
                    if (errAnnot != null)
                    {
                        // This controller can be accessed by error code.
                        String result = errAnnot.errorCode();
                        String[] errorCodes = result.split(",");
                        for (int i=0; i < errorCodes.length; i++)
                        {
                            int code = -1;
                            try {
                                code = Integer.parseInt(errorCodes[i]);
                            } catch (NumberFormatException nfe) {
                                if (errorCodes[i].equals("*"))
                                    code = -1;
                            }
                            mErrorControllers.put(code, controller);
                        }
                    }
                } 
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
}
