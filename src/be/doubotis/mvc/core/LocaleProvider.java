/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Christophe
 */
public class LocaleProvider
{
    private Locale mLocale;
    private ResourceBundle mResourceBundle;

    public LocaleProvider(Locale locale, ResourceBundle resBundle)
    {
        mLocale = locale;
        mResourceBundle = resBundle;
    }

    public String translate(String id)
    {
        return mResourceBundle.getString(id);
    }
    
}
