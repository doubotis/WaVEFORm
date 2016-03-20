/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core.annotations;

import be.doubotis.mvc.core.LocaleProvider;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Christophe
 */
public class TranslationTag extends SimpleTagSupport {
    private String id;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            PageContext pc = (PageContext)getJspContext();
            ServletContext sc = pc.getRequest().getServletContext();
            Locale locale = (Locale)sc.getAttribute("lang");
            ResourceBundle rb = (ResourceBundle)sc.getAttribute("resourceBundle");
            
            LocaleProvider provider = new LocaleProvider(locale, rb);
            String result = provider.translate(id);
            if (result == null)
                result = "???";
            out.print(result);
            
            
        } catch (java.io.IOException ex) {
            throw new JspException("Error in TranslationTag tag", ex);
        }
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
