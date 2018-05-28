package flexdb.annotation;

import flexdb.util.SqlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author biologyiswell (18/05/2018 18:57)
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * Name, string,
     * This string represents the name from the column
     *
     * @return the name from column
     * @since 0.1
     */
    String name();

    /**
     * Size, integer,
     * If the column type is a TEXT or a VARCHAR by example, the size need be set to set the size from the TEXT or a
     * VARCHAR
     *
     * @return the size from the column type
     * @since 0.1
     */
    int size() default 0;

    SqlType type() default SqlType.UNKNOWN;

    boolean updatable() default true;

    boolean nonNull() default false;
}