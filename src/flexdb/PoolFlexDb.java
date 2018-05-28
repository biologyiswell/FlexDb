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
 * PoolFlexDb, class,
 * This represents the class that is the pool flexible database, that represents the database that contains a pool of
 * connections that is recommneded to make a high operations
 *
 * @author biologyiswell (26/05/2018 23:59)
 * @since 0.1
 */
public class PoolFlexDb extends AbstractFlexDb {

    /**
     * Max Connection Pool, variable,
     * This variable must be declared to set a maximum of connections can be create on create the PoolFlexDb object
     * @since 0.1
     */
    private static final int MAX_CONNECTION_POOL = 128;

    /**
     * Connections, variable,
     * This represents the connections array that contains the connections that is used on operations about flexible
     * database
     * @since 0.1
     */
    private Connection[] connections;

    /**
     * Connection Index, variable,
     * This represents the connection index that represents the connection that is used to make the operation
     * @since 0.1
     */
    private int connectionIndex;

    /**
     * PoolFlexDb, constructor,
     * This constructor need be five arguments in argument-list, that represents the host, username, password, port
     * and the connection size
     *
     * @param host the host
     * @param username the username
     * @param password the password
     * @param port the port, that if the port is equals -1, this represents the default port that is 3306
     * @param connectionSize the connection size from pool of connections
     * @throws SQLException
     */
    public PoolFlexDb(final String host, final String username, final String password, final int port, final int connectionSize) throws SQLException {
        super(host, username, password, port);

        // @Note Check if the connection size from argument-list is bigger than MAX_CONNECTION_POOL
        if (connectionSize > MAX_CONNECTION_POOL) {
            throw new IllegalArgumentException("connection size can not be bigger than maximum connection pool that is (" + MAX_CONNECTION_POOL + ")");
        }

        // @Note Initialize the connection array
        this.connections = new Connection[connectionSize];
    }

    /**
     * Connection, method,
     * This method checks if the connections is open, if not, open it, otherwise create the connection, and the method
     * returns the connection object
     *
     * @return connection object
     * @throws SQLException
     * @since 0.1
     */
    @Override
    public Connection connection() throws SQLException {
        // @Note Check if the connection index is equals length from the connection array minus 1, then this represents
        // that represents that the connection index set to the 0
        if (this.connectionIndex == this.connections.length - 1) {
            this.connectionIndex = 0;
        }

        // @Note Check if the connection from the current index is closed then, open it
        if (this.connections[this.connectionIndex].isClosed()) {
            this.connections[this.connectionIndex] = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
        }

        // @Note Get the current connection handled, and increase the index from connection index
        return this.connections[this.connectionIndex++];
    }
}
