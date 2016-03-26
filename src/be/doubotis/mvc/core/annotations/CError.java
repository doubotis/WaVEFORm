/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation that serves as marker for controllers. A controller marked
 * by this annotation will be available for use when errors occurs.
 * Set the <code>errorCode</code> property to a valid HTTP status code. If
 * you want to handle many error codes for one controller, just make the
 * HTTP status codes separated by a coma. If you want to handle all error codes
 * with a single controller, just put an asterisk (*).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CError {
    
    String errorCode();
}
