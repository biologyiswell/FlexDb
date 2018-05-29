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
package flexdb;

/**
 * FlexDbType, enum,
 * This represents the enum that contains the types from Flexible Database
 *
 * @author biologyiswell (18/05/2018 17:35)
 * @since 0.1
 */
public enum FlexDbType {

    /**
     * Indicates the single flexible database which represents that the database only contains a single connection, this
     * database type is recommended to database that make low quantity of operations by time
     * @since 0.1
     */
    SINGLE,

    /**
     * Indicates the pooled flexible database which represents that the database has a pre-determined size from pool
     * of connections, this database type is recommended to database that make big quantity of operations by time
     * @since 0.1
     */
    POOLED
}
