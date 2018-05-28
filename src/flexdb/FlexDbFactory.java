package flexdb;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * FlexDbFactory, class,
 * This represents the class that create the types of flexible databases
 *
 * @author biologyiswell (18/05/2018 17:51)
 * @since 0.1
 */
public class FlexDbFactory {

    /**
     * New Pooled Database, method,
     * This method creates an instance from pooled flexible database type, that is the database that contains a pool of
     * connections
     *
     * @param host the host
     * @param username the username
     * @param password the password
     * @param port the port, if the port is equals -1 this represents the default port that is 3306
     * @param poolSize the size of connections that is create to pool of connection
     * @return FlexDb object with pool of connections
     * @since 0.1
     */
    public static FlexDb newPooledDatabase(final String host, final String username, final String password, final int port,  int poolSize) throws SQLException {
        return new PoolFlexDb(host, username, password, port, poolSize);
    }

    /**
     * New Single Database, method,
     * This method creates an instance from single flexible database type, that is the database that contains a single
     * connection
     *
     * @param connection the connection that is used by the single flexible database to make operations
     * @return FlexDb object with single connection
     * @since 0.1
     */
    public static FlexDb newSingleDatabase(final Connection connection) {
        return new SingleFlexDb(connection);
    }

    /**
     * New Single Database, method,
     * This method creates an instance from single flexible database type, that is the database that contains a single
     * connection, this method need four arguments in argument-list that is host, username, password and port that
     * represents the informations from a connection
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
