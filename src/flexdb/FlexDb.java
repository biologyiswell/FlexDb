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

import flexdb.annotation.Column;
import flexdb.annotation.ColumnAnchor;
import flexdb.annotation.Data;
import flexdb.annotation.Table;
import flexdb.util.SqlType;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FlexDb, class,
 * This represents the FlexDb class that represents the main class that handles the methods about operations in database
 *
 * @author biologyiswell (18/05/2018 17:24)
 * @version 0.1.5
 * @since 0.1
 */
public abstract class FlexDb {

    /**
     * Host, username, password and port variables that is used to make the connection with MySQL Storage
     * @since 0.1
     */
    protected String host;
    protected String username;
    protected String password;
    protected int port;

    /**
     * Creates an instance of flexible database which this database handler hass not a flexible database type to handle,
     * this constructor is created to database types that construct the database handler with a different constructor
     * argument-list
     *
     * @since 0.1.5
     */
    FlexDb() { // package-private
    }

    /**
     * Creates an instance of flexible database which this database handler has not a flexible database type to handle
     *
     * @param host the host name which is used to connect to the MySQL Storage
     * @param username the username which is used to authenticate the username credential
     * @param password the password which is used to authenticate the password credential
     * @param port the port which is used to connect to the MySQL Storage, if the port is equals -1, the default port
     *             is set that is 3306
     * @since 0.1
     */
    FlexDb(final String host, final String username, final String password, final int port) { // package-private
        if (host == null) throw new NullPointerException("host");
        if (username == null) throw new NullPointerException("username");
        if (password == null) throw new NullPointerException("password");

        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port == -1 ? 3306 : port;
    }

    // Abstract Methods

    /**
     * Returns the connection based on flexible database type
     *
     * @return the connection
     * @throws SQLException this exception is thrown if method relationed with connection fails
     * @since 0.1
     */
    public abstract Connection connection() throws SQLException;

    // Methods

    /**
     * Check the database state, if the database not exists in MySQL Storage, it is created
     *
     * @param klass the class that contains the database annotation to can get database name and make the check
     * @since 0.1
     */
    public final void database(final Class<?> klass) {
        // @Note Check if the "klass" is null
        if (klass == null) throw new NullPointerException("klass");

        // @Note This method make the check database
        this.checkDatabase(klass, klass.getAnnotation(Data.class));
    }

    /**
     * Check the table state, if the table not exists in MySQL Storage, it is created, this method do the build from the
     * all columns from table in database
     *
     * @param klass the class that contains the database annotation, table annotation and column annotation which
     *              provide to methods informations about class that are database name, table name and column names
     *              with informations about (column name, column type, among others)
     * @since 0.1
     */
    public final void table(final Class<?> klass) {
        // @Note Check if the "klass" is null
        if (klass == null) throw new NullPointerException("klass");

        // @Note This method make the check table
        this.checkTable(klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class));
    }

    /**
     * Insert, method,
     * This method insert an object to the database, with this object the class is get to can the method get the
     * informations about database name, table name and the columns to can insert the object
     *
     * @param object the object that contains the database name, table name and columns informations
     * @since 0.1
     */
    public final void insert(final Object object) {
        // @Note Check if the object is null
        if (object == null) throw new NullPointerException("object");

        final Class<?> klass = object.getClass();
        this.checkInsert(object, klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class));
    }

    /**
     * Insert, method,
     * This method insert an object to the database, without need the objects to get the values from the object, this
     * method use the arguments that is represented by "objects" from argument-list
     *
     * @param klass the klass that contains the data annotation, table annotation and columns annotations
     * @param objects the objects that represents the value from the columns from table
     */
    public final void insert(final Class<?> klass, final Object... objects) {
        if (klass == null) throw new NullPointerException("klass");
        if (objects == null) throw new NullPointerException("objects");
        if (objects.length == 0) throw new IllegalArgumentException("objects is empty");

        // @Note Make the check insert about the arguments
        this.checkInsertWithArguments(klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class), objects);
    }

    /**
     * Update, method,
     * This method makes the update from the object in the database, that with this object can get the class that
     * contains the informations about data, table and column annotations and from field values
     *
     * @param object the object that will be update to the database, that contains the data, table and column
     *               annotations and the field values
     */
    public final void update(final Object object) {
        // @Note This condition makes the check if the object is null
        if (object == null) throw new NullPointerException("object");

        final Class<?> klass = object.getClass();
        this.checkUpdate(object, klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class));
    }

    /**
     * Update, method,
     * This method makes the update from the object in the database about single informations about the database table
     * without need the object to make the update
     *
     * @param klass the klass that contains the data annotation, table annotation and column annotation that contains
     *              the informations from the table
     * @param whereCheck the where check column that will be check
     * @param argCheck the argument check column that will be check value with the where check column
     * @param args the arguments tthat represents a pair of arguments that the first argument represents the column name
     *             that will be update, and the second argument that represents the value that will be update
     */
    public final void update(final Class<?> klass, final String whereCheck, final Object argCheck, final Object... args) {
        // @Note Make the check from the null values
        if (klass == null) throw new NullPointerException("klass");
        if (whereCheck == null) throw new NullPointerException("whereCheck");
        if (argCheck == null) throw new NullPointerException("argCheck");
        if (args == null) throw new NullPointerException("args");

        this.checkUpdate(klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class), whereCheck, argCheck, args);
    }

    /**
     * Delete, method,
     * This method makes the delete from the object from the row that contains this object, this method create the
     * DELETE statement to delete this object, that contains the data annotation, table annotation and column annotation
     * that contains the informations about the table
     *
     * @param object the object that will be delete from database table
     */
    public final void delete(final Object object) {
        // @Note This condition makes the check if the object is null
        if (object == null) throw new NullPointerException("object");

        final Class<?> klass = object.getClass();
        this.checkDelete(object, klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class));
    }

    /**
     * Delete, method,
     * This method makes the delete from the class that represents a table and by values that represents the column
     * check and the argument check that will be checked to delete the row
     *
     * @param klass the class that contains the data annotation and table annotation
     * @param columnCheck the column name that will be check
     * @param argCheck the argument value that will be check
     */
    public final void delete(final Class<?> klass, final String columnCheck, final Object argCheck) {
        if (klass == null) throw new NullPointerException("klass");
        if (columnCheck == null) throw new NullPointerException("columnCheck");
        if (argCheck == null) throw new NullPointerException("argCheck");

        this.checkDelete(klass, klass.getAnnotation(Data.class), klass.getAnnotation(Table.class), columnCheck, argCheck);
    }

    /**
     * Operation, method,
     * This method execute a statement
     *
     * @param operation the statement that will be executed
     * @since 0.1
     */
    public final void operation(final String operation) {
        if (operation == null) throw new NullPointerException("operation");

        this.checkOperation(operation);
    }

    /**
     * Operations, method,
     * This method execute a array of operations in a statement
     *
     * @param operations the array of operations
     * @since 0.1
     */
    public final void operations(final String... operations) {
        if (operations == null) throw new NullPointerException("operations");

        this.checkOperations(operations);
    }

    // Internal Methods

    /**
     * Check Operation, method,
     * This method check the operation
     *
     * @param operation the operation that will be checked
     */
    protected void checkOperation(final String operation) {
        // @Note This statement execute the operation
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Operations, method,
     * This method check the operations
     *
     * @param operations the operations that will be check
     */
    protected void checkOperations(final String... operations) {
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
     * Check Delete, method,
     * This method makes the check about the DELETE statement
     *
     * @param object the object that contains the field values
     * @param klass the class from object that contains, data annotation, table annotation and column annotation
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     */
    protected void checkDelete(final Object object, final Class<?> klass, final Data data, final Table table) {
        this.checkDatabaseAndTable(klass, data, table);

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
        this.checkDatabaseAndTable(klass, data, table);

        // @Note This statement makes the execute from the DELETE statement
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createDeleteStatement(data, table, columnCheck, argCheck));
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
    protected void checkUpdate(final Object object, final Class<?> klass, final Data data, final Table table) {
        this.checkDatabaseAndTable(klass, data, table);

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
    protected void checkUpdate(final Class<?> klass, final Data data, final Table table, final String whereCheck, final Object argCheck, final Object... args) {
        this.checkDatabaseAndTable(klass, data, table);

        // @Note This statement executes the update statement to database
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createUpdateStatement(klass, data, table, whereCheck, argCheck, args));
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
    protected void checkInsert(final Object object, final Class<?> klass, final Data data, final Table table) {
        this.checkDatabaseAndTable(klass, data, table);

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
    protected void checkInsertWithArguments(final Class<?> klass, final Data data, final Table table, final Object... args) {
        this.checkDatabaseAndTable(klass, data, table);

        // @Note This statement executes the operation that inserts the values from object to columns from table in row
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate(this.createInsertStatementWithArguments(klass, data, table, args));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check Table, method,
     * This method check the table name exists in database, otherwise if not exists create the table
     *
     * @param klass the klass that represents where the table annotation is present
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name that will be create
     * @since 0.1
     */
    protected void checkTable(final Class<?> klass, final Data data, final Table table) {
        this.checkDatabaseAndTable(klass, data, table);

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
            // @Note Make the check about the error that occurs when create the table into the database
            // e.printStackTrace();
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
    protected void checkDatabase(final Class<?> klass, final Data data) {
        // @Note Check if the "data" annotation is null, this represents that the class not contains the data annotation
        // then can not be initialize the database
        if (data == null) throw new NullPointerException("Class \"" + klass.getSimpleName() + "\" not represents a database because not contains the annotation \"" + Data.class.getName() + "\".");

        // @Note Check if the database name is empty
        if (data.name().isEmpty()) throw new RuntimeException("Class \"" + klass.getSimpleName() + "\" has \"Data\" annotation with empty name.");

        // @Note This statement executes the operation about create database if not exists
        try (final Statement statement = this.connection().createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + data.name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create Statement Methods

    /**
     * Create Table Statement, method,
     * This represents a "internal method", then this method not make checks about the "klass" is null or other thing,
     * this class only make the process to create the table statement to check if exists
     *
     * @param klass the klass that represents where the table annotation is present, and where the fields from classes
     *             contains the column annotation that contains the infomrations to create the table statement
     * @paam table the table annotation that contains the table name
     * @return table string statement
     */
    protected String createTableStatement(final Class<?> klass, final Data data, final Table table) {
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(data.name()).append('.').append(table.name());
        sb.append(" ("); // Start the process to put the columns that contains in the table
        for (final Field field : klass.getDeclaredFields()) {
            final Column column = field.getAnnotation(Column.class);

            // @Note This condition checks if the column is null then, the process can be continued because the
            // continuous off process represented the parse from the infomration from fields that contains the column
            // annotation
            if (column == null) continue;

            // @Note This condition check if the name from the column is empty
            if (column.name().isEmpty()) throw new RuntimeException("Class \"" + klass.getSimpleName() + "\" that represents a table, the column from field \"" + field.getName() + "\" has empty name.");

            // @Note This condition check if the SqlType from column is UNKNOWN
            if (column.type() == SqlType.UNKNOWN) throw new RuntimeException("Class \"" + klass.getSimpleName() + "\" that represents a table, the column from field \"" + field.getName() + "\" has unknown type.");

            sb.append(column.name()).append(' ').append(column.type().name());
            // @Note This condition check if the column size is bigger than 0, that represents that the type has a
            // custom size
            if (column.size() > 0) {
                sb.append("(").append(column.size()).append(")");
            }

            // About the type to put on the each check condition about the column, the sb.append(" "), that is put
            // before from append the column information is because to separate the last information from the current
            // information
            //      -biologyiswell, 18 May 2018

            // @Note This condition checks if the column is non null, then like this add to the statement the "NOT NULL"
            // statement
            if (column.nonNull()) {
                sb.append(" ").append("NOT NULL");
            }

            // @Note Put the string separator, to can separete the columns
            sb.append(", ");
        }
        // @Note This condition makes the check about the length from the StringBuilder, without
        // this condition to remove the last separator from the last for-each loop the statement
        // is wrong
        //      -biologyiswell, 18 May 2018
        if (sb.length() > 0) {
            // @Note This method make the delete from the last separator that represents ", ",
            // that is added by the last for-each loop
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(")"); // End the process to put the columns that contains in the table
        return sb.toString();
    }

    /**
     * Create Alter Statement, method,
     * This method get the current column names from class and get the current column names from table, and compare the
     * columns, if current column name from class has not database contains class is that the column will be remove,
     * if the database column name has not current column class, the current column class is will be add on the database
     *
     * @param klass the class that contains the informations about the data, table and columns
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @param metadata the metadata, that represents a metadata from a ResultSet, that is used to get the column names
     *                 from database
     * @return string ALTER statement
     * @since 0.1
     */
    protected String createAlterStatement(final Class<?> klass, final Data data, final Table table, final ResultSetMetaData metadata) {
        int modCount = 0;

        final Map<String, Column> currentColumnNames = new HashMap<>();
        final List<String> databaseColumnNames = new ArrayList<>();

        // @Note For-each from the all declared fields from the class, this for-each loop is used to cache the all
        // current column names and the column informations
        for (final Field field : klass.getDeclaredFields()) {
            final Column column = field.getAnnotation(Column.class);
            if (column == null) continue;

            // @Note Add the current column names to list
            currentColumnNames.put(column.name(), column);
        }

        // @Note Get the column names that contains in the database table
        try {
            // @Note For-loop about the column counts that will get the columns name to add the column name into the
            // database columns names list
            for (int i = 0; i < metadata.getColumnCount(); i++) {
                databaseColumnNames.add(metadata.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Object[] columnsNamesArray = currentColumnNames.keySet().toArray();
        final Object[] columnsColumnsArray = currentColumnNames.values().toArray();
        final Object[] databaseColumnsNamesArray = databaseColumnNames.toArray();

        // @Note This represents the string builder that constructs the ALTER statement
        final StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(data.name()).append('.').append(table.name());
        sb.append(" "); // Start the process of ALTER table statement

        final int lengthProcess = Math.max(columnsNamesArray.length, databaseColumnsNamesArray.length);

        // @Note For-each loop that make the comparison about the current column names with database column names
        for (int i = 0; i < lengthProcess; i++) {
            if (i < columnsNamesArray.length) {
                final String currentColumnName = (String) columnsNamesArray[i];

                // @Note This condition checks if the table column is not contains in the current table, this
                // represents that this column will be removed from the columns from table
                if (!databaseColumnNames.contains(currentColumnName)) {
                    // @Note The "column.name()", represents the currentColumnName
                    final Column column = (Column) columnsColumnsArray[i];

                    sb.append("ADD COLUMN ");
                    sb.append(column.name()).append(' ').append(column.type().name());

                    // @Note This condition check if the column size is bigger than 0, that represents that the type has a
                    // custom size
                    if (column.size() > 0) {
                        sb.append("(").append(column.size()).append(")");
                    }

                    // About the type to put on the each check condition about the column, the sb.append(" "), that is put
                    // before from append the column information is because to separate the last information from the current
                    // information
                    //      -biologyiswell, 18 May 2018

                    // @Note This condition checks if the column is non null, then like this add to the statement the "NOT NULL"
                    // statement
                    if (column.nonNull()) {
                        sb.append(" ").append("NOT NULL");
                    }

                    // @Note Put the string separator, to can separate the columns
                    sb.append(", ");

                    modCount++;
                    continue;
                }
            }

            if (i < databaseColumnsNamesArray.length) {
                final String databaseColumnName = (String) databaseColumnsNamesArray[i];

                // @Note This condition checks if the table column is not contains in the current table, this
                // represents that this column will be add
                if (!currentColumnNames.containsKey(databaseColumnName)) {
                    // @Note The database column name will be drop, because this column not contains in the current
                    // column that is get by the current class
                    sb.append("DROP COLUMN ").append(databaseColumnName);

                    // @Note Put the string separator, to can separate the columns
                    sb.append(", ");

                    modCount++;
//                    continue;
                }
            }
        }
        // @Note This condition makes the check about the length from the StringBuilder, without
        // this condition to remove the last separator from the last for-each loop the statement
        // is wrong
        //      -biologyiswell, 18 May 2018
        if (sb.length() > 0) {
            // @Note This method make the delete from the last separator that represents ", ",
            // that is added by the last for-each loop
            sb.delete(sb.length() - 2, sb.length());
        }

        return modCount == 0 ? "" : sb.toString();
    }

    /**
     * Check Insert, method,
     * This method make the insert statement about the object, with the object can be get the database name, table name,
     * column informations, and field values that contains in the object, with this create the insert statement
     *
     * @param object the object that is relative to create the insert statement, and that contains the informations,
     *               about database name, table name, columns informations and field values that contains in the object
     */
    protected String createInsertStatement(final Object object, final Class<?> klass, final Data data, final Table table) {
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(data.name()).append('.').append(table.name()).append(" VALUES ");
        sb.append("("); // Start the process to insert the values
        for (final Field field : klass.getDeclaredFields()) {
            final Column column = field.getAnnotation(Column.class);

            // @Note This condition makes the check about the column if is null
            if (column == null) continue;

            // @Note Here not need check if the column has a empty name, if type is unknown, because the column is
            // already tested on initialize the database and tables method to check then, this method not need check
            // the column because will cause a redundancy
            //      -biologyiswell, 18 May 2018

            try {
                // @Note Make that the field can be accessible, otherwise can be causes an error
                field.setAccessible(true);

                // @Note This represents the value that will be parsed
                final Object value = field.get(object);

                // @Note This condition check if the object value is a String or a Character, these objects types
                // are that the String and the Character should be between quotation marks
                if (value instanceof String || value instanceof Character) {
                    sb.append("'").append(value).append("'");
                } else {
                    sb.append(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // @Note Append the string separator between values
            sb.append(", ");
        }
        // @Note This condition makes the check about the length from the StringBuilder, without
        // this condition to remove the last separator from the last for-each loop the statement
        // is wrong
        //      -biologyiswell, 18 May 2018
        if (sb.length() > 0) {
            // @Note This method make the delete from the last separator that represents ", ",
            // that is added by the last for-each loop
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(")"); // End the process to insert the values
        return sb.toString();
    }

    /**
     * Create Insert Statement With Arguments, method,
     * This method create an insert statement without need object to get the field values, this need the arguments that
     * is represented by "args" in the argument-list
     *
     * @param klass the klass that contains the data annotation, table annotation and columns that contains the storage
     *              informations about the columns
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @param args the arguments that is insert like value to column from table
     * @return an insert statement
     */
    protected String createInsertStatementWithArguments(final Class<?> klass, final Data data, final Table table, final Object... args) {
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(data.name()).append('.').append(table.name()).append(" VALUES ");
        sb.append("("); // Start the process to insert the values
        for (final Object arg : args) {
            // @Note Check if the argument that contains in the arguments varargs is null
            if (arg == null) throw new NullPointerException("argument (object arguments can not be null)");

            // @Note This condition check if the object value is a String or a Character, these objects types
            // are that the String and the Character should be between quotation marks
            if (arg instanceof String || arg instanceof Character) {
                sb.append("'").append(arg).append("'");
            } else {
                sb.append(arg);
            }

            sb.append(", ");
        }
        // @Note This condition makes the check about the length from the StringBuilder, without
        // this condition to remove the last separator from the last for-each loop the statement
        // is wrong
        //      -biologyiswell, 18 May 2018
        if (sb.length() > 0) {
            // @Note This method make the delete from the last separator that represents ", ",
            // that is added by the last for-each loop
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(")"); // End the process to insert the values
        return sb.toString();
    }

    /**
     * Create Update Statement, method,
     * This method create the update statement based on the field values from the object that contains the field values,
     * and the class that contains the data annotation, table annotation and column annotations that contains the
     * informations about the database
     *
     * @param object the object that contains the field values that will be update to database
     * @param klass the class that contains the data annotation, table annotation and columns annotations
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @return update statement
     * @since 0.1
     */
    protected String createUpdateStatement(final Object object, final Class<?> klass, final Data data, final Table table) {
        final StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(data.name()).append('.').append(table.name()).append(" SET");
        sb.append(" "); // @Note Start the process of input updatable columns

        Field anchorField = null;

        try {
            // @Note For-each loop about the declared fields from class
            for (final Field field : klass.getDeclaredFields()) {
                final Column column = field.getAnnotation(Column.class);
                final ColumnAnchor anchor = field.getAnnotation(ColumnAnchor.class);

                // @Note This condtiion makes the check if the field represents a column
                if (column == null) continue;

                // @Note This condition makes the check if the column is updatable
                if (!column.updatable() && anchor == null) continue;

                // @Note This conditions makes the check about the anchor field, and check if the current anchor is
                // different from null, but if a anchor is different from null and anchor field is different from null
                // this represents an error because the class only contains a one column anchor
                if (anchor != null) {
                    if (anchorField == null) {
                        anchorField = field;
                    } else {
                        throw new IllegalStateException("A one column can has the anchor, in the Class \"" + klass.getSimpleName() + "\" the Columns \"" + anchorField.getName() + ", " + field.getName() + "\" has anchors.");
                    }

                    // @Note The process musut be continue because the anchor field not entry on the update list
                    continue;
                }

                field.setAccessible(true);
                final Object value = field.get(object);
                // @Note Append the column name with the updatable value
                sb.append(column.name()).append(" = ");

                if (value instanceof String || value instanceof Character) {
                    sb.append("'").append(value).append("'");
                } else {
                    sb.append(value);
                }

                // @Note Append the separator
                sb.append(", ");
            }

            // @Note This condition makes the check about the length from the StringBuilder, without
            // this condition to remove the last separator from the last for-each loop the statement
            // is wrong
            //      -biologyiswell, 18 May 2018
            if (sb.length() > 0) {
                // @Note This method make the delete from the last separator that represents ", ",
                // that is added by the last for-each loop
                sb.delete(sb.length() - 2, sb.length());
            }

            anchorField.setAccessible(true);

            // @Note Append the statement condition WHERE to localize the row that contains the anchor field that
            // contains the column annotation that will be get the column name and insert the value from field to can
            // check the condition to make the update from the statement
            sb.append(" WHERE ").append(anchorField.getAnnotation(Column.class).name()).append(" = ").append(anchorField.get(object));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Create Update Statement, method,
     * This method create the update statement about the class that contains the data annotation, table annotation and
     * the columns annotations, and the updatable is check by the column where check that contains the value where check
     * with the update values from columns and values
     *
     * @param klass the klass that contains the data annotation, table annotation and columns annotations
     * @param data the data annotation that contains the database name
     * @since table the table annotation that contains the table name
     * @param columnWhereCheck the column that will be checked
     * @param valueWhereCheck the value from the column that will be checked
     * @param columnsAndValues the columns with the values that will be update in the columns in storage
     * @return update statement
     * @since 0.1
     */
    protected String createUpdateStatement(final Class<?> klass, final Data data, final Table table, final String columnWhereCheck, final Object valueWhereCheck, final Object... columnsAndValues) {
        final StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(data.name()).append('.').append(table.name()).append(" SET");
        sb.append(" "); // @Note Start the process to input the values that will be update

        // @Note The for-loop increase the index by 2 indexes because the columnsAndValues array represents a pair array
        // that represents the column name and the column value that represents the column that will be update with the
        // new column value
        for (int i = 0; i < columnsAndValues.length; i += 2) {
            // @Note This condition makes the check about the column name if is a string
            if (!(columnsAndValues[i] instanceof String)) {
                throw new IllegalArgumentException("The columns and values from argument-list has not a string");
            }

            final String column = (String) columnsAndValues[i];
            final Object columnValue = columnsAndValues[i + 1];

            // @Note Append the column name
            sb.append(column).append(" = ");
            if (columnValue instanceof String || columnValue instanceof Character) {
                sb.append("'").append(columnValue).append("'");
            } else {
                sb.append(columnValue);
            }
            // @Note Appends the separator between the update statements
            sb.append(", ");
        }


        // @Note This condition makes the check about the length from the StringBuilder, without
        // this condition to remove the last separator from the last for-each loop the statement
        // is wrong
        //      -biologyiswell, 18 May 2018
        if (sb.length() > 0) {
            // @Note This method make the delete from the last separator that represents ", ",
            // that is added by the last for-each loop
            sb.delete(sb.length() - 2, sb.length());
        }

        // @Note Append the WHERE statement that will be check the column name with the column value
        sb.append(" WHERE ").append(columnWhereCheck).append(" = ").append(valueWhereCheck);
        return sb.toString();
    }

    /**
     * Create Delete Statement, method,
     * This method makes the delete statement about the object, like this, this represents an internal method then, this
     * method need be four arguments, that represents the object, class object, class data annotation, class table
     * annotation
     *
     * @param object the object represents the class that contains the field values
     * @param klass the class represents the arguments that contains the data annotation, table annotation and column
     *              annotations that get the informations about the table
     * @param data the data annotation that contains the database name
     * @param table the table annotation that contains the table name
     * @return delete statement
     */
    protected String createDeleteStatement(final Object object, final Class<?> klass, final Data data, final Table table) {
        final StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(data.name()).append('.').append(table.name()).append(" WHERE ");

        boolean hasAnchor = false;

        try {
            // @Note For-each loop from the all declared fields from the class
            for (final Field field : klass.getDeclaredFields()) {
                final Column column = field.getAnnotation(Column.class);
                final ColumnAnchor anchor = field.getAnnotation(ColumnAnchor.class);

                // @Note This condition makes the check about the column if is null then, the column anchor can not be a
                // column anchor if the field is not a column
                if (column == null) continue;

                // @Note This condition makes the check if the anchor is null
                if (anchor == null) continue;

                field.setAccessible(true);

                final Object value = field.get(object);
                sb.append(field.getName()).append(" = ");

                // @Note This condition makes the check about the string and character that represents that the value
                // need be into "'", that represents the string or character in statement
                if (value instanceof String || value instanceof Character) {
                    sb.append("'").append(value).append("'");
                } else {
                    sb.append(value);
                }

                hasAnchor = true;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!hasAnchor) {
            throw new IllegalArgumentException("Class \"" + klass.getSimpleName() + "\" has not a Column Anchor, to create the DELETE statement.");
        }

        return sb.toString();
    }

    /**
     * Create Delete Statement, method,
     * This method create the DELETE statement that is make by the class that represents the table that contains the
     * data annotation and table annotation, column check and column value that will be check
     *
     * @param data the data annotation that contains the database name
     * @param table the table anotation that contains the table name
     * @param columnCheck the column check that represents the column name that will be check
     * @param columnValue the column value that will be check in the column check
     * @return delete statement
     */
    protected String createDeleteStatement(final Data data, final Table table, final String columnCheck, final Object columnValue) {
        return "DELETE FROM " + data.name() + "." + table.name() + " WHERE " + columnCheck + " = " + columnValue;
    }

    /**
     * Checks the database annotation and table annotation if both from this annotations has the necessary requirements
     * that the flexible database provides to the class
     *
     * @param klass the class where the database annotation and table annotation is
     * @param data the data annotation that contains the database name which will be checked
     * @param table the table annotation that contains the table name which will be checked
     * @since 0.1
     */
    protected void checkDatabaseAndTable(final Class<?> klass, final Data data, final Table table) {
        // @Note Check if the "data" annotation is null, this represents that the class not contains the data annotation
        // then can not be initialize the database
        if (data == null) throw new NullPointerException("Class \"" + klass.getSimpleName() + "\" not represents a database because not contains the annotation \"" + Data.class.getName() + "\".");

        // @Note Check if the database name is empty
        if (data.name().isEmpty()) throw new RuntimeException("Class \"" + klass.getSimpleName() + "\" has \"Data\" annotation with empty name.");

        // @Note Check if the "table" annotation is null, this represents that the class not contains the table
        // annotation then can not be initialize the table
        if (table == null) throw new NullPointerException("Class \"" + klass.getSimpleName() + "\" not represents a table because not contains the annotation \"" + Table.class.getName() + "\"");

        // @Note Check if the table name is empty
        if (table.name().isEmpty()) throw new RuntimeException("Class \"" + klass.getSimpleName() + "\" has \"Table\" annotation with empty name.");
    }
}
