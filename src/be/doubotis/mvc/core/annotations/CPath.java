
package be.doubotis.mvc.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation that serves as marker for controllers. A controller marked
 * by this annotation will be available for use when path matches the pattern.
 * Set the <code>path</code> property to a valid String pattern.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CPath {

    String path();
}
