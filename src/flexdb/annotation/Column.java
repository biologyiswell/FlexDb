/*
 * MIT License
 *
 * Copyright (c) 2018 biologyiswell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package flexdb.annotation;

import flexdb.util.SqlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates to field that it represents a column which is interpreted by project to handle operations
 * about database on methods that create and check table, insert, update or delete, among other methods that can be add
 * and that have totally relation with this annotation
 *
 * @author biologyiswell (18/05/2018 18:57)
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * Indicates the name of column
     * @return the name of column
     * @since 0.1
     */
    String name();

    /**
     * Indicates the size of data type, if necessary, because has data types that not need set size because already set
     * in MySQL
     * @return the size of data type which this column has
     * @since 0.1
     */
    int size() default 0;

    /**
     * Indicates the data type value from this column
     * @return the data type value from this column
     * @since 0.1
     */
    SqlType type() default SqlType.UNKNOWN;

    /**
     * Indicates that this column is updatable, Flexible Database has methods that do update table rows, which these do
     * checks about updatable fields that is provide by this value from annotation
     * @return if the column is updatable
     * @since 0.1
     */
    boolean updatable() default true;

    /**
     * Indicates if the column is non null which represents that the column must be a default value, that is set by
     * default value from data type
     * @return if the column is non null
     * @since 0.1
     */
    boolean nonNull() default false;
}