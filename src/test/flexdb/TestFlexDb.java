package test.flexdb;

import flexdb.FlexDb;
import flexdb.FlexDbFactory;
import flexdb.annotation.Column;
import flexdb.annotation.ColumnAnchor;
import flexdb.annotation.Data;
import flexdb.annotation.Table;
import flexdb.util.SqlType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a test class which test the functions relationed with flexible databse
 *
 * @author biologyiswell (31/05/2018 10:44)
 * @since 0.1
 */
public class TestFlexDb {

    private static final String HOST = "localhost";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final int PORT = 3306;

    private static final String DB_NAME = "test_db2";
    private static final String ENGINEER_TABLE_NAME = "engineers2";

    public static void main(String[] args) throws Exception {
        // @Note Hot the machine
        hotMachine();

        testInsertArgumentsInitialization();
        testDeleteArgumentListInitialization();
    }

    private static void hotMachine() {
        for (int i = 0; i < 100000; i++) {
            double d = (i * 3200d) / 98747.812d * 1.4948247d;
            double h = d * 3298318.3289d;
            h *= Math.cos(i) * 4000.3219321879d;
            h *= 3890382190d * 32381.312839218d;

            d *= 3219.92139012d * 32891d;
            d *= 34289432.4932849302d;

            final String test = "312321321-312312-3213-123-21-3-21-312-4-124-25-43-5642-t-432-5324-5-324-34-25-34-52";

            for (int j = 0; j < 5; j++) {
                test.split("-");
                test.split("32");
                test.split("5");

                double a = (Math.cos(j) * Math.tan(j)) * (Math.cos(j) * 180.0d) / 5d;

                h *= a;
                d *= h;
                d *= a;
            }
        }
    }

    // @Note Test methods

    // @Note The time from this test is based
    // 1. Test: Test from the delete row from Engineer from the able
    //  1.1. Cold: (62ms)
    //  1.2. Hot: (47ms)
    // 2. Test: Test the delete row from Engineer from the table with the non existing row in the table
    //  1.1. Cold: (32ms)
    //  1.2. Hot: (15ms - 31ms)
    private static void testDeleteArgumentListInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Delete the Engineer informations in table, based on the informations that is provide by the Engineer
        // class, and the column name and column value that is provide by the argument-list
        db.delete(Engineer.class, "id", 0);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from this test is based
    // 1. Test: Test from the delete row from Engineer from the able
    //  1.1. Cold: (62ms)
    //  1.2. Hot: (47ms)
    // 2. Test: Test the delete row from Engineer from the table with the non existing row in the table
    //  1.1. Cold: (32ms)
    //  1.2. Hot: (15ms - 31ms)
    private static void testDeleteObjectInitialization() throws Exception {
        final Engineer engineer = new Engineer(0, "Test Engineer Name", "Street Test 1", "000-000 0002");
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Delete the Engineer object, based on the Column Anchor, which contains the column name that is provide
        // by the object class, and the column anchor value that is provide by object
        // @Note !! This method to delete objects is more safely about other delete methods, because this method select
        // the automatically the column anchor, about other delete method can be select a non column anchor method !!
        db.delete(engineer);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from this test is based
    // 1. Test: Test from the update informations from Engineer into the table
    //  1.1. Cold: (78ms - 93ms)
    //  1.2. Hot: (63ms)
    private static void testUpdateArgumentsInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Update the Engineer argument-list into the database, this method get the class to can get the database
        // name, table name and column informations, and the argument-list contains the row check column name with the
        // column value with the updatable column names with updatable column values
        db.update(Engineer.class, "id", 0, "phone_number", "000-000 00002");

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");

    }

    // @Note The time from this test is based
    // 1. Test: Test from the update informations from Engineer into the table
    //  1.1. Cold: (78ms - 93ms)
    //  1.2. Hot: (63ms)
    private static void testUpdateObjectInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);
        final Engineer engineer = new Engineer(0, "Test Engineer Name", "Street Test 1", "000-000 0001");

        long start = System.currentTimeMillis();

        // @Note Update the Engineer object into the database, this method use the object that contains the class which
        // contains the database name, table name and column informations, and the object which contains the field
        // values, and this method makes the check from the row that contains the informations about the column anchor
        // that is defined in the class and that can be get by the object class
        db.update(engineer);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from this test is based
    // 1. Test: Test that insert the Engineer informations into the table
    //  1.1. Cold: (64ms - 74ms)
    //  1.2. Hot: (43ms - 54ms)
    private static void testInsertArgumentsInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Insert an Engineer infomrations into Engineer table, this method use the "Engineer.class" to can get
        // the database name, table name and column informations and the remainder from argument-list from method
        // represents the values from each column in order
        db.insert(Engineer.class, 0, "Test Engineer Name", "Street Test 1", "000-000 0000");

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from this test is based
    // 1. Test: Test that insert the Engineer object into table
    //  1.1. Cold: 131ms
    //  1.2. Hot: (67ms - 98ms)
    private static void testInsertObjectInitialization() throws Exception {
        // @Note Create an Engineer instance with randomized informations
        final Engineer engineer = Engineer.createEngineer();

        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Insert the Engineer object into the database, this method use the object that contains the class which
        // contains the database name, table name and column informations, and the object which contains the field
        // values
        db.insert(engineer);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from this test is based
    // 1. Test: Test that create the database into MySQL Storasge
    //  1.1. Cold: 61ms
    //  1.2. Hot: Can not be tested, but is estimated between (10ms - 25ms)
    // 2. Test: Test that make if the database exists
    //  2.1. Cold: (21ms - 29ms)
    //  2.2. Hot: 1ms
    private static void testDatabaseInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Check the database state to create, this method will do two tests that represents the time from the
        // method to create the database, and the time from method to check if the database exists
        // 1. Test - Time taken from method to create the database
        // 2. Test - Time taken from method to check if the database exists, this test represents that the database
        // exists on the MySQL Storage
        db.database(Engineer.class);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note The time from the test is based
    // 1. Test: Test that create the table into the database
    //  1.1. Cold: 406ms
    //  1.2. Hot: Can not be tested, but is estimated between (100ms - 150ms)
    // 2. Test: Test that make if the table exists
    //  2.1. Cold: (28ms - 31ms)
    //  2.2. Hot: 1ms
    private static void testTableInitialization() throws Exception {
        // @Note Creates the single flexible database type connection
        final FlexDb db = FlexDbFactory.newSingleDatabase(HOST, USERNAME, PASSWORD, PORT);

        long start = System.currentTimeMillis();

        // @Note Check the table state to create, this method will do two tests that represents the time from the method
        // to create the table, and the time from the method to check if the table exists
        // 1. Test - Time taken from method to create the table
        // 2. Test - Time taken from method to check if the table exists, this test represents that thet able exists on
        // the MySQL Storage
        db.table(Engineer.class);

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms.");
    }

    // @Note Test classes

    @Data(name = DB_NAME)
    @Table(name = ENGINEER_TABLE_NAME)
    public static class Engineer {

        // @Note This represents the id from the Engineer and also represents the column anchor from the table
        @Column(name = "id", type = SqlType.INTEGER, updatable = false, nonNull = true)
        @ColumnAnchor
        private final int id;

        // @Note This represents the name from the Engineer
        @Column(name = "name", type = SqlType.TEXT, size = 32, nonNull = true)
        private final String name;

        // @Note This represents the address from Engineer
        @Column(name = "address", type = SqlType.TEXT, size = 32, nonNull = true)
        private final String address;

        // @Note This represents the phone number from Engineer
        @Column(name = "phone_number", type = SqlType.TEXT, size = 32, nonNull = true)
        private final String phoneNumber;

        public Engineer(final int id, final String name, final String address, final String phoneNumber) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }

        /**
         * Creates an Engineer instance, with randomized informations
         * @return the Engineer object
         * @since 0.1.5
         */
        public static Engineer createEngineer() {
            final int id;
            final String name;
            final String address;
            final String phoneNumber;

            final String charactersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            final StringBuilder sb = new StringBuilder("");

            // @Note Id constructor
            id = ThreadLocalRandom.current().nextInt();

            // @Note Name constructor
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(5, 10); i++) {
                // @Note Append character from StringBuilder from a randomized char index
                sb.append(charactersAndNumbers.charAt(ThreadLocalRandom.current().nextInt(0, charactersAndNumbers.length() - 1)));
            }

            name = sb.toString();
            sb.delete(0, sb.length() - 1);

            // @Note Address constructor
            sb.append("Street ");
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(10, 20); i++) {
                // @Note Append character from StringBuilder from a randomized char index
                sb.append(charactersAndNumbers.charAt(ThreadLocalRandom.current().nextInt(0, charactersAndNumbers.length() - 1)));
            }
            address = sb.toString();
            sb.delete(0, sb.length() - 1);

            // @Note Phone Number constructor
            sb.append("000-0000 ");
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(5, 10); i++) {
                // @Note Append character from StringBuilder from a randomized char index
                sb.append(charactersAndNumbers.charAt(ThreadLocalRandom.current().nextInt(0, charactersAndNumbers.length() - 1)));
            }
            phoneNumber = sb.toString();
            sb.delete(0, sb.length() - 1);

            return new Engineer(id, name, address, phoneNumber);
        }
    }
}
