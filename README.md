# FlexDb: Flexible Database Handler

FlexDb stands for Flexible Database, is a **Java** project relationed on manage, control, stability, flexibility in operations using **MySQL**. The main point that the project want arrive is take to developer a more **flexible** and **readable** code in database operations.

* FlexDb current version: 0.1
* Java required version: 1.7

## Examples
This represents some examples from **flexibility** and **readability** provided by **FlexDb**.
```java
// Engineer.java
@Data(name = "test_db")
@Table(name = "engineers")
public class Engineer {

    public Engineer(int id, int age, String name, String address, String phoneNumber) {
    	this.id = id;
	this.name = name;
	this.age = age;
	this.address = address;
	this.phoneNumber = phoneNumber;
    }

    /**
     * This variable is marked with "ColumnAnchor" that is an annotation that is used to
     * represents that this field is main field from class. This annotation is indinpensable
     * on create an object that is handled by FlexDb. 
     */
    @Column(name = "id", nonNull = true, updatable = false, type = SqlType.INTEGER)
    @ColumnAnchor
    private int id;
	  
    @Column(name = "age", nonNull = true, type = SqlType.INTEGER)  
    private int age;  
  
    @Column(name = "name", nonNull = true, updatable = false, type = SqlType.VARCHAR, size = 64)  
    private String name;
  
    @Column(name = "address", nonNull = true, type = SqlType.VARCHAR, size = 128)  
    private String address;  
  
    @Column(name = "phone_number", nonNull = true, type = SqlType.VARCHAR, size = 64)  
    private String phoneNumber;
}

// Main.java
public final FlexDb db = FlexDbFactory.createSingleDatabase(	"localhost", "root", "123", 3306);
// An Engineer class to use like example, containing (id, name, age, address, phone number, etc.)
public final Engineer engineer = new Engineer(1, 25, "Foo", "Street Bar", "000-0000 00");

// @Note Insert the Engineer object into database, based on informations that is provided from
// Engineer class that provide informations about (database name, table name, column
// informations) and the object that contains the value from the fields
db.insert(engineer);

// @Note Insert, this method is few different from other "insert" method, this method use the
// Engineer class to get the infomrations about (database name, table name, column informations)
// and the remainder from arguments represents the values from columns in order
db.insert(Engineer.class, 1, 25, "Foo", "Street Bar", "000-0000 00");

// @Note Update makes the update from the all columns that is marked as "updatable=true"
db.update(engineer);

// @Note Update, this method is few different from other "update" method, this method use the
// Engineer class to get the informations about (database name, table name, column informations)
// to can check also the updatable variables, and remainder from arguments represents a 
// key-pair of arguments that the first represents the "column name" and the second the
// "column value" that is updated based on the "whereCheck" that represents the second argument
// argument-list, and the third argument represents the "column value" that is checked to find
// the row that contains this informations and update with the informations based on the
// key-pair values
db.update(Engineer.class, "id", 1, "phone_number", "000-000 01", "address", "Street Bar 02");

// @Note Delete makes the delete from row based on the informations provided from the object 
// and from ColumnAnchor, that is the main column from the class that manage the row
// informations in database
db.delete(engineer);

// @Note Delete, this method is few different from other "delete" method, this method use the
// Engineer class to get the informations about (database name, table name, column informations)
// and delete the row from table based  on the informations from argument-list that are
// the "columnCheck" that is name from column and "argCheck" that is value from column
db.delete(Engineer.class, "id", 1);
```
