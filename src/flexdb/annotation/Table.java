package flexdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author biologyiswell (18/05/2018 18:46)
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * Name, string,
     * This represents the name from the table
     * @since 0.1
     */
    String name();
}
