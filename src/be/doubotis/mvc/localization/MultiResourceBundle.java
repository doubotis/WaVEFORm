/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.localization;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import sun.misc.CompoundEnumeration;

/** A ResourceBundle that contains many resource bundles.
 */
public class MultiResourceBundle extends ResourceBundle {
    
    public ArrayList<ResourceBundle> mResourceBundles = null;
    
    public MultiResourceBundle(ResourceBundle... args)
    {
        mResourceBundles = new ArrayList<ResourceBundle>();
        
        for (int i=0; i < args.length; i++)
        {
            mResourceBundles.add(args[i]);
        }
    }

    @Override
    protected Object handleGetObject(String key) {
        
        Object o = null;
        for (ResourceBundle rb : mResourceBundles)
        {
            o = rb.getObject(key);
            
            if (o != null)
                return o;
        }
        
        return null;
    }
    
    /** Get the amount of ResourceBundle stored into it. */
    public int getResourceBundlesCount()
    {
        return mResourceBundles.size();
    }
    
    /** Get the {@link ResourceBundle} stored at the specified index. */
    public ResourceBundle getResourceBundle(int index)
    {
        return mResourceBundles.get(index);
    }

    @Override
    public Enumeration<String> getKeys() {
        
        Enumeration<String>[] enums = 
                (Enumeration<String>[]) Array.newInstance(Enumeration.class, 
                        mResourceBundles.size());
        
        for (int i=0; i < mResourceBundles.size(); i++)
            enums[i] = mResourceBundles.get(i).getKeys();
        
        CompoundEnumeration<String> enumeration = 
                new CompoundEnumeration<String>(enums);
        
        return enumeration;
    }
    
}
