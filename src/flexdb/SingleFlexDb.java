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
 * SingleFlexDb, class,
 * This represents the class that is the flexible database single type, that contains a single connection that is
 * recommended to use in low operations
 *
 * @author biologyiswell (26/05/2018 23:43)
 * @since 0.1
 */
public class SingleFlexDb extends AbstractFlexDb {

    /**
     * Connection, variable,
     * This variable represents the connection object that handle the operations about database
     * @since 0.1
     */
    private Connection connection;

    // @Todo (29/05/2018 19:20) Create document
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
        super(connection);
        this.connection = connection;
    }

    /**
     * Connection, method,
     * This method checks if the connections is open, if not, open it, otherwise create the connection, and the method
     * returns the connection object
     *
     * @return connection object
     * @since 0.1
     */
    @Override
    public Connection connection() {
        return this.connection;
    }
}
