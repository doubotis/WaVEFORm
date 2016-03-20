/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Christophe
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
    
    public void assign(String key, Object o)
    {
        mObjects.put(key, o);
    }
    
    public void display(String jspPage)
    {
        mDisplayedPage = jspPage;
    }
    
    public HashMap<String, Object> getAssignations()
    {
        return mObjects;
    }
    
    public String getDisplayPage()
    {
        return mDisplayedPage;
    }
    
    public void localize(Locale locale, ResourceBundle resourceBundle)
    {
        mLocale = locale;
        mResourceBundle = resourceBundle;
    }
    
    public Locale getLocale()
    {
        return mLocale;
    }
    
    public ResourceBundle getResourceBundle()
    {
        return mResourceBundle;
    }
    
}
