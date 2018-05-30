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

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database Factory class which make creations from flexible database more safely
 *
 * @author biologyiswell (18/05/2018 17:51)
 * @since 0.1
 */
public class FlexDbFactory {

    /**
     * Creates an instance of pooled flexible database, to handle operations about database, which this database has a
     * fixed size of connections
     *
     * @param host the host name which is used to connect to the MySQL Storage
     * @param username the username which is used to authenticate the username credential
     * @param password the password which is used to authenticate the password credential
     * @param port the port which is used to connect to the MySQL Storage, if the port is equals -1, the default port
     *             is set that is 3306
     * @param connectionSize this represents the pre-determined from pool of connections
     * @return Pooled Flexible Database object
     * @throws SQLException this exception is thrown if Connection construct fails
     * @since 0.1
     */
    public static FlexDb newPooledDatabase(final String host, final String username, final String password, final int port, int connectionSize) throws SQLException {
        return new PoolFlexDb(host, username, password, port, connectionSize);
    }

    /**
     * Creates an instance of single flexible database, to handle operations about database, which this database has a
     * single connection
     *
     * @see #newSingleDatabase(String, String, String, int)
     * @param connection the connection that is used by the single flexible database to make operations
     * @return FlexDb object with single connection
     * @since 0.1
     */
    public static FlexDb newSingleDatabase(final Connection connection) {
        return new SingleFlexDb(connection);
    }

    /**
     * Creates an instance of single flexible database, to handle operations about database, which this database has a
     * single connection
     *
     * @param host the host
     * @param username the username
     * @param password the password
     * @param port the port, if the port is equals -1 this represents the default port that is 3306
     * @return FlexDb object with single connection
     * @since 0.1
     */
    public static FlexDb newSingleDatabase(final String host, final String username, final String password, final int port) throws SQLException {
        return new SingleFlexDb(host, username, password, port);
    }
}
