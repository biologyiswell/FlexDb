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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is a single database provided by flexible database factoryw hich creates a database handler that contains a
 * single connection which do operations about database and is recommended to database which do low quantity of
 * operations by time
 *
 * @author biologyiswell (26/05/2018 23:43)
 * @since 0.1
 */
public class SingleFlexDb extends FlexDb {

    /**
     * This is the connection object which is used to handle the operations from flexible databases, and its handle by
     * connection method
     * @since 0.1
     */
    private Connection connection;

    /**
     * Creates an instance from Single Flexible Database which this database is recommended to make a low quantity
     * operations about database by time
     *
     * @param host the host name which is used to connect to the MySQL Storage
     * @param username the username which is used to authenticate the username credential
     * @param password the password which is used to authenticate the password credential
     * @param port the port which is used to connect to the MySQL Storage, if the port is equals -1, the default port
     *             is set that is 3306
     * @throws SQLException this exception is thrown if Connection construct fails
     */
    SingleFlexDb(final String host, final String username, final String password, final int port) throws SQLException { // package-private
        super(host, username, password, port);
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
    }

    /**
     * SingleFlexDb, constructor,
     * This constructor need one argument that represents the connection that handle the all methods from this class
     *
     * @param connection the connection
     */
    public SingleFlexDb(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Returns the single connection that the database provides
     * @return the single connection
     * @since 0.1
     */
    @Override
    public Connection connection() {
        return this.connection;
    }
}
