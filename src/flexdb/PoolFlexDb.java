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
 * This is a pooled database provided by flexible database factory which creates a database handler that contains a pool
 * of connections with a pre-determined size which do operations about database and is recommended to database which
 * make a big quantity of operations by time
 *
 * @author biologyiswell (26/05/2018 23:59)
 * @since 0.1
 */
public class PoolFlexDb extends AbstractFlexDb {

    /**
     * Is the maximum pre-determined size that a pool of connection can have
     * @since 0.1
     */
    private static final int MAX_CONNECTION_POOL = 128;

    /**
     * Is the array of connection that has the pre-determined size defined by the constructor instatiation (by default)
     * @since 0.1
     */
    private Connection[] connections;

    /**
     * This indicates the index from the connection that will be used on the next connection call
     * @since 0.1
     */
    private int connectionIndex;

    /**
     * Creates an instance from Pooled Flexible Database which this database is recommended to make big quantity of
     * operations about database by time
     *
     * @param host the host name which is used to connect to the MySQL Storage
     * @param username the username which is used to authenticate the username credential
     * @param password the password which is used to authenticate the password credential
     * @param port the port which is used to connect to the MySQL Storage, if the port is equals -1, the default port
     *             is set that is 3306
     * @param connectionSize this represents the pre-determined from pool of connections
     * @throws SQLException this exception is thrown if Connection construct fails
     */
    PoolFlexDb(final String host, final String username, final String password, final int port, final int connectionSize) throws SQLException { // package-private
        super(host, username, password, port);

        // @Note Check if the connection size from argument-list is bigger than MAX_CONNECTION_POOL
        if (connectionSize > MAX_CONNECTION_POOL) {
            throw new IllegalArgumentException("connection size can not be bigger than maximum connection pool that is (" + MAX_CONNECTION_POOL + ")");
        }

        // @Note Initialize the connection array
        this.connections = new Connection[connectionSize];
    }

    /**
     * Gets the next connection indicates by connection index that is handled, which the connection that is get is
     * provided by connection array pre-determined by the constructor instantiation (by default), which this make the
     * check about if the connection is closed, in this case open the connection, the connection index is handle by the
     * increase from this index based on the call of connection methods
     *
     * @return the connection indicates by connection index
     * @throws SQLException this exception is thrown if method call from Connection method fails
     * @since 0.1
     */
    @Override
    public Connection connection() throws SQLException {
        // @Note Check if the connection index is equals length from the connection array minus 1, then this represents
        // that represents that the connection index set to the 0
        if (this.connectionIndex == this.connections.length - 1) {
            this.connectionIndex = 0;
        }

        // @Note Check if the connection from the current index is null or closed then, open it
        if (this.connections[this.connectionIndex] == null || this.connections[this.connectionIndex].isClosed()) {
            this.connections[this.connectionIndex] = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
        }

        // @Note Get the current connection handled, and increase the index from connection index
        return this.connections[this.connectionIndex++];
    }
}
