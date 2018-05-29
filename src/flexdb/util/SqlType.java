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
package flexdb.util;

/**
 * All data types provided by MySQL is added in this enumeration, this has a main function to the project which is
 * provide the data type specifications from MySQL, where one example is to can set column data types among other
 * functions
 *
 * @author biologyiswell (18/05/2018 18:55)
 * @since 0.1
 */
public enum SqlType {

    /**
     * Indicates a bit value which has range from 0 to 64
     * @since 0.1
     */
    BIT,

    /**
     * Indicates a boolean value which is "true" or "false". The default value is "false"
     * @since 0.1
     */
    BOOLEAN,

    /**
     * Indicates a tiny integer in MySQL which is equivalent to byte, and has signed range from -128 to 127, and unsigned
     * range from 0 to 255, the unsigned value from this data type affect the Java data type set and it is changed to
     * short data type
     * @since 0.1
     */
    TINYINT,

    /**
     * Indicates a small integer in MySQL which is equivalent to short, and has signed range from -32768 to 32767, and
     * unsigned range from 0 to 65535, the unsigned value from this data type affect Java data type set and it is
     * changed to int data type
     * @since 0.1
     */
    SMALLINT,

    /**
     * Indicates a medium integer in MySQl which is equivalent to int (because Java not has a data type that contains
     * this range), and has signed range from -8388608 to 8338607, and unsigned range from 0 to 16777215, the unsigned
     * value from this data type not affect the Java data type
     * @since 0.1
     */
    MEDIUMINT,

    /**
     * Indicates an integer in MySQL which is equivalent to int, and has signed range from -2147483648 to 2147483647,
     * and unsigned range from 0 to 4294967295, the unsigned value from this data type affect Java data type set and it
     * is changed to long data type
     * @since 0.1
     */
    INTEGER,

    /**
     * Indicates a large integer in MySQL which is equivalent to long, and has signed range from -9223372036854775808 to
     * 9223372036854775807, and unsigned has from 0 to 18446744073709551615, the unsigned value from this data type
     * affect Java data type set and it is changed to (probably) to BigInteger class
     * @since 0.1
     */
    BIGINT,

    /**
     * Indicates a floating-point number in MySQL which is equivalent to float, and has signed range from
     * -3.402823466E+38 to 1.175494351E-38, and unsigned disallow negative values
     * @since 0.1
     */
    FLOAT,

    /**
     * Indicates a double floating-point number in MySQL which is equivalent to double, and has signed range from
     * 2.2250738585072014E-308 to 1.7976931348623157E+308, and unsigned disallow negative values
     * @since 0.1
     */
    DOUBLE,

    /**
     * Indicates a "exact" fixed-point number which is equivalent (probably) a BigDecimal class, and has range with 0
     * decimal digits by default where maximum is 30, and mantissa has range 10 digits by default where maximum is 65
     * @since 0.1
     */
    DECIMAL,

    /**
     * Indicates a date which is equivalent to Date class, and default format is "YYYY-MM-DD", and range is from
     * "1000-01-01" to "9999-31-12"
     * @since 0.1
     */
    DATE,

    /**
     * Indicates a datetime that make a combination about date format with time format which is equivalent to Date
     * class, and default format is "YYYY-MM-DD HH:MM:SS", and range is from "1000-01-01 00:00:00" to "9999-31-12
     * 23:59:59"
     * @since 0.1
     */
    DATETIME,

    /**
     * Indicates a timestamp that make a combination about date format, time format and timestamp format from location,
     * which is equivalent a Date class, and range is from "1000-01-01 00:00:00 UTC" to "9999-31-12 23:59:59 UTC"
     * @since 0.1
     */
    TIMESTAMP,

    /**
     * Indicates a time which is equivalent a String, and range is from "-838:59:59" to "838:59:59"
     * @since 0.1
     */
    TIME,

    /**
     * Indicates a fixed-length string that is always right-padded with spaces to the specified length when stored,
     * which is equivalent to char, this data type has the optional size set, that by default the size from the
     * char in column is 0, otherwise the maximum length is from 0 to 255
     * @since 0.1
     */
    CHAR,

    /**
     * Indicates a variable-length char which is equivalent to String, this data type has optional size set, that by default
     * is 1, otherwise the maximum length is from 0 to 65535. This data type affect on the Java data type set, because
     * the VARCHAR characters that contains in the char array by the MySQL has the format "1-byte" by char, while the
     * String from Java has format "2-byte"
     * @since 0.1
     */
    VARCHAR,

    /**
     * Indicates a variable-length string which is equivalent to String, this data type has optional size set, that by
     * default is 1, otherwise the maximum length is from 0 to 65535, which default charset is UTF-8. Both in Java or
     * MySQL each char use the "2-byte" format
     * @since 0.1
     */
    TEXT,

    /**
     * Indicates a variable-length string which is equivalent to String, this data type has optional size set, that by
     * default is 1, otherwise the maximum length is from 0 to 4294967295, which default charset is UTF-8. Both in Java or
     * MySQL each char use the "2-byte" format
     * @since 0.1
     */
    LONGTEXT,

    TINYBLOB, // @Todo (29/05/2018 19:02) Create document
    BLOB, // @Todo (29/05/2018 19:02) Create document
    MEDIUMBLOB, // @Todo (29/05/2018 19:02) Create document
    LONGBLOB, // @Todo (29/05/2018 19:02) Create document

    BINARY, // @Todo (29/05/2018 19:02) Create document
    VARBINARY, // @Todo (29/05/2018 19:02) Create document

    ENUM, // @Todo (29/05/2018 19:02) Create document
    SET, // @Todo (29/05/2018 19:02) Create document

    /**
     * Indicates an unknown data type, which is equivalent a void, but this data is not provided by the MySQL, this
     * data type is implemented to defines the default data type from the column data types
     * @since 0.1
     */
    UNKNOWN
}
