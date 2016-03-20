/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import be.doubotis.http.exceptions.HTTPException;
import be.doubotis.mvc.core.annotations.ControllerPath;
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
    
    public ControllerScanner(ServletContext context, String file)
    {
        setup(context, file);
    }
    
    public static ControllerScanner fromFile(ServletContext context, String file)
    {
        try
        {
            InputStream is = context.getResourceAsStream(file);
            
            ControllerScanner cs = new ControllerScanner(context, file);
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
    
    private void setup(ServletContext context, String file)
    {
        try
        {
            InputStream is = context.getResourceAsStream(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            
            Element controllersEl = (Element)
                    doc.getElementsByTagName("controllers").item(0);
            String packageToLookup = controllersEl.getAttribute("location");
            
            Reflections reflections = new Reflections(packageToLookup);
            Set<Class<? extends Controller>> subTypes = 
                  reflections.getSubTypesOf(Controller.class);
            
            mControllers = new HashMap<String, Class<? extends Controller>>();
            Iterator<Class<? extends Controller>> it = subTypes.iterator();
            while (it.hasNext())
            {
                Class<? extends Controller> controller = it.next();
                
                ControllerPath annotation = (ControllerPath) 
                        controller.getAnnotation(ControllerPath.class);
                
                if (annotation == null)
                    continue;
                
                String path = annotation.path();
                
                mControllers.put(path, controller);
            }
            
            mErrorControllers = new HashMap<Integer, Class<? extends Controller>>();
            
            Element errorsEl = (Element)
                    doc.getElementsByTagName("errors").item(0);
            NodeList nl = errorsEl.getElementsByTagName("error");
            for (int i=0; i < nl.getLength(); i++)
            {
                Element el = (Element)nl.item(i);
                String codeAsString = el.getAttribute("code");
                int code = -1;
                try {
                    code = Integer.parseInt(codeAsString);
                } catch (NumberFormatException nfe) {
                    if (codeAsString.equals("*"))
                        code = -1;
                }
                String location = el.getAttribute("location");
                
                Class c = Class.forName(location);
                
                mErrorControllers.put(code, c);
            }
            
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
}
