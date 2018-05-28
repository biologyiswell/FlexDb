package flexdb;

import flexdb.annotation.Data;
import flexdb.annotation.Table;

import java.sql.*;

/**
 * AbstractFlexDb, class,
 * This represents the abstract flexible database type, that handle the operations about database
 *
 * @author biologyiswell (18/05/2018 17:39)
 * @since 0.1
 */
public abstract class AbstractFlexDb extends FlexDb {

    protected String host;
    protected String username;
    protected String password;
    protected int port;

    /**
     * AbstractFlexDb, constructor,
     * This constructor need four arguments that represents the host, username, password and port, that if the port is
     * equals -1, the port is set by default that is 3306
     *
     * @param host the host
     * @param username the username
     * @param password the password
     * @param port the port, if the port is equals -1, the port is set by default that is 3306
     * @throws SQLException
     */
    public AbstractFlexDb(final String host, final String username, final String password, final int port) throws SQLException {
        if (host == null) throw new NullPointerException("host");
        if (username == null) throw new NullPointerException("username");
        if (password == null) throw new NullPointerException("password");

        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    /**
     * AbstractFlexDb, constructor,
     * This constructor need one argument that represents the connection that handle the all methods from this class
     *
     * @param connection the connection
     */
    public AbstractFlexDb(final Connection connection) {
        if (connection == null) throw new NullPointerException("connection");
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
    public abstract Connection connection() throws SQLException;

    /**
     * Operation, method,
     * This method execute a statement
     *
     * @param operation the statement that will be executed
     * @since 0.1
     */
    @Override
    protected void checkOperation(final String operation) {
        // @Note Not remove "super.checkOperation(operation)"
        super.checkOperation(operation);

        // @Note This statement execute the operation
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operations, method,
     * This method execute a array of operations in a statement
     *
     * @param operations the array of operations
     * @since 0.1
     */
    @Override
    protected void checkOperations(final String... operations) {
        // @Note Not remove "super.checkOperations(operations)"
        super.checkOperations(operations);

        // @Note This statement execute the operations
        try (final Connection connection = this.connection(); final Statement statement = connection.createStatement()) {
            // @Note Disable the auto-commit to make the operations without need commit each one
            // @Note !! When change the configurations about the connection is important that the selected connection
            // make a variable instead of use the "connection" method, because the pooled flexible database make the
            // configuration about a other connection instead of the current connection that create the statement
            connection.setAutoCommit(false);

            // @Note Add the operations that contains in the operations array into a statement in a batch
            for (final String operation : operations) {
                statement.addBatch(operation);
            }

            // @Note Execute the batch operations
            statement.executeBatch();

            // @Note Commit the changes about the operations
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Insert, method,
     * This method check if the insertion from the statement is correctly, and make the execute about the statement
     * to the storage
     *
     * @param object the object that contains the field values to insert the values to the columns
     * @param klass the class that contains the data annotation, table annotation and columns annotations
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @since 0.1
     */
    @Override
    protected void checkInsert(final Object object, final Class<?> klass, final Data data, final Table table) {
        // @Note Not remove "super.checkInsert(object, klass, data, table)"
        super.checkInsert(object, klass, data, table);

        // @Note This statement executes the operation that insert the values into the columns in a row
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createInsertStatement(object, klass, data, table));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Insert With Arguments, method,
     * This method check if the insertion statement is correctly, this statement is created by arguments that is
     * represented by "args" in the argument-list, not need an object to get the field values
     *
     * @param klass the klass that contains the data annotation, table annotation and columns annotations that contains
     *              the informations about the column
     * @param data the data annotation
     * @param table the table annotation
     * @param args the arguments that represents the values from the columns
     */
    @Override
    protected void checkInsertWithArguments(final Class<?> klass, final Data data, final Table table, final Object... args) {
        // @Note Not remove "super.checkInsert(klass, data, table, args)"
        super.checkInsertWithArguments(klass, data, table, args);

        // @Note This statement executes the operation that inserts the values from object to columns from table in row
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createInsertStatementWithArguments(klass, data, table, args));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Update, method,
     * This method check the if the update statement is correctly, and make the execute about the update statement
     *
     * @param object the object that contains the field values to update values to the columns
     * @param klass the class that contains the data annotation, table annotation and columns annotations
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @since 0.1
     */
    @Override
    protected void checkUpdate(final Object object, final Class<?> klass, final Data data, final Table table) {
        // @Note Not remove "super.checkUpdate(object, klass, data, table)"
        super.checkUpdate(object, klass, data, table);

        // @Note This statement makes the execute from the update about the object
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createUpdateStatement(object, klass, data, table));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Update, method,
     * This method check if the update statement is correctly, and make the execute about the update statement, this
     * method make the update statement about the values without the object
     *
     * @param klass the klass that contains the data annotation, table annotation and column annotation that contains
     *              the informations about the table
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @param whereCheck the where check column that represents the column that will be check to make the update about
     *                   the update values
     * @param argCheck the argument check that will be make the check with the where check column
     * @param args the arguments that will be updated to the row from table
     */
    @Override
    protected void checkUpdate(final Class<?> klass, final Data data, final Table table, final String whereCheck, final Object argCheck, final Object... args) {
        // @Note Not remove "super.checkUpdate(klass, data, table, whereCheck, argCheck, args)"
        super.checkUpdate(klass, data, table, whereCheck, argCheck, args);

        // @Note This statement executes the update statement to database
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createUpdateStatement(klass, data, table, whereCheck, argCheck, args));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Check Delete, method,
     * This method makes the check about the DELETE statement
     *
     * @param object the object that contains the field values
     * @param klass the class from object that contains, data anotation, table annotation and column annotation
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     */
    @Override
    protected void checkDelete(final Object object, final Class<?> klass, final Data data, final Table table) {
        // @Note Not remove "super.checkDelete(object, klass, data, table)"
        super.checkDelete(object, klass, data, table);

        // @Note This statement makes the DELETE statement from the object
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createDeleteStatement(object, klass, data, table));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Delete, method,
     * Thismethod makes the check about the DELETE statement
     *
     * @param klass the class from object that contains, data annotation, table annotation and column annotation
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @param columnCheck the column name that will be check
     * @param argCheck the argument value that will be check in the column
     */
    protected void checkDelete(final Class<?> klass, final Data data, final Table table, final String columnCheck, final Object argCheck) {
        // @Note Not remove "super.checkDelete(klass, data, table, columnCheck, argCheck)"
        super.checkDelete(klass, data, table, columnCheck, argCheck);

        // @Note This statement makes the execute from the DELETE statement
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createDeleteStatement(data, table, columnCheck, argCheck));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Table, method,
     * This method check the table name exists in database, otherwise if not exists creat the table
     *
     * @param klass the klass that represents where the table annotation is present
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name that will be create
     * @since 0.1
     */
    @Override
    protected void checkTable(final Class<?> klass, final Data data, final Table table) {
        // @Note Not remove "super.checkTable(klass, data, table)"
        super.checkTable(klass, data, table);

        // @Note This statement executes the operation about create table in database if not exists, the method
        // "createTableStatement" makes the parse from class from argument list "klass", that the parse read the
        // fields that contains the "Column" annotation that contains the informations about a column
        try (final Statement statement = this.connection().createStatement()) {
            final ResultSet resultSet;
            try {
                resultSet = this.connection().createStatement().executeQuery("SELECT * FROM " + data.name() + "." + table.name() + " LIMIT 1");

                // @Note This method "createAlterStatement" makes that if has not modifications about the current
                // columns from table and database columns from table, this method returns the "", that represents
                // an empty string
                final String alterStatement = this.createAlterStatement(klass, data, table, resultSet.getMetaData());

                // @Note This condition makes the check if the alter statement is not empty
                if (!alterStatement.isEmpty()) {
                    statement.executeUpdate(alterStatement);
                }
            } catch (SQLException e) {
                e.printStackTrace();

                // @Todo (19/05/2018 11:43): Make handle from errors about error code

                // @Note When the try-catch block catch an error, the error that is catch is that the SELECT statement
                // thrown an error, this represents that the query fails because the table not exists
                statement.executeUpdate(this.createTableStatement(klass, data, table));
            }

            // @Note If the result set "next()" method is true then, the execute statement represents an alter
            // statement if contains something, otherwise execute the table statement
            // statement.executeUpdate(resultSet.next() ? this.createAlterStatement(klass, data, table, resultSet.getMetaData()) : this.createTableStatement(klass, data, table));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Database, method,
     * This method check the database name exists in database, otherwise if not exists create the database
     *
     * @param klass the klass that represents where the data annotation is present
     * @param data the data annotation that contains the name from database that will be created
     * @since 0.1
     */
    @Override
    protected void checkDatabase(final Class<?> klass, final Data data) {
        // @Note Not remove "super.checkDatabase(klass, data)"
        super.checkDatabase(klass, data);

        // @Note This statement executes the operation about create database if not exists
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + data.name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
