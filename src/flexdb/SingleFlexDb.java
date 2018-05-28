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

    /**
     * SingleFlexDb, constructor,
     * This constructor need four arguments that represents the host, username, password and port, that if the port is
     * equals -1, the port is set by default that is 3306
     *
     * @param host the host
     * @param username the username
     * @param password the password
     * @param port the port, if the port is equals -1, the port is set by default that is 3306
     * @throws SQLException
     */
    public SingleFlexDb(final String host, final String username, final String password, final int port) throws SQLException {
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
