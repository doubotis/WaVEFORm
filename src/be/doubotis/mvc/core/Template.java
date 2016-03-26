/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/** An object that serves as a messenger between your JSPs and your
 * controllers.
 */
public final class Template {
    
    private HashMap<String, Object> mObjects;
    private String mDisplayedPage = null;
    private Locale mLocale = null;
    private ResourceBundle mResourceBundle = null;
    
    public Template()
    {
        mObjects = new HashMap<String, Object>();
    }
    
    /** Assign a variable to be available in JSP by using <code>${key}</code>.
     * @param key The key that refers to the variable.
     * @param o The object variable you want to make it available.
     */
    public void assign(String key, Object o)
    {
        mObjects.put(key, o);
    }
    
    /** Sets the JSP page that must be displayed. */
    public void display(String jspPage)
    {
        mDisplayedPage = jspPage;
    }
    
    /** Get the list of assignations set by calling {@link Template#assign}. */
    public HashMap<String, Object> getAssignations()
    {
        return mObjects;
    }
    
    /** Get the JSP page that must be displayed. */
    public String getDisplayPage()
    {
        return mDisplayedPage;
    }
    
    /** Tells to the system what locale and which {@link ResourceBundle}
     * to use for internationalization. */
    public void localize(Locale locale, ResourceBundle resourceBundle)
    {
        mLocale = locale;
        mResourceBundle = resourceBundle;
    }
    
    /** Get the locale used for internationalization. */
    public Locale getLocale()
    {
        return mLocale;
    }
    
    /** Get the {@link ResourceBundle} that will be used for 
     * internationalization. */
    public ResourceBundle getResourceBundle()
    {
        return mResourceBundle;
    }
    
}
