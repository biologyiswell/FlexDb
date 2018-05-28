package flexdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author biologyiswell (18/05/2018 17:44)
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Data {

    /**
     * Name, string,
     * This represents the name of database
     * @since 0.1
     */
    String name();
}
