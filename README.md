# StarData
A Java SQL library to make working with databases a little easier  
[![](https://www.jitpack.io/v/StarDevelopmentLLC/StarData.svg)](https://www.jitpack.io/#StarDevelopmentLLC/StarData)  
## To use this Library
You must add JitPack as a repo, below is for Gradle  
```groovy
repositories {
    maven {
        url = 'https://www.jitpack.io'
    }
}
```  
Then to use this library as a dependency  
```goovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarData:{MODULE}:{VERSION}'
}
```  
Replace {MODULE} with your chosen MySQL Type or the api module to compile against it.  
Replace {VERSION} with the module version. Go here: https://www.jitpack.io/#StarDevelopmentLLC/StarData/ and click on "Get It" on the version you want, then you can select the "Subproject" to get a copy/paste link, doing this can also get you the Maven stuff for it.  

For some reason, the shading and relocation of the StarLib files didn't work as I had hoped, I will have to solve this later, but you will need to add StarLib as a dependency. The lastest version should work just fine. 
## Getting Started
To get started with using this Library, you need to choose what SQL Database you want to use. This library comes with default support for MySQL, PostgreSQL, H2 and SQLite.  
After choosing your Database Type, you need to make sure that it is a dependency. If you want to use anything from StarLib, you need to add that as a dependency as well. You do not need to add any of the required modules of StarData.  
For this demonstration, I am going to use MySQL. I have a MySQL 8 server set up on my computer already.

NOTE: ALL METHODS RUN SYNCHRONOUSLY, there are plans to add methods to run async, but that will be later and will require some more things to be done first.  

The first thing that you need is a DatabaseRegistry, more specifically a SQLDatabaseRegistry as that is the only database type supported at this time.  
There are two ways to get an instance of this, the first way is to instantiate it using the constructor and passing in a java.util.logging.Logger instance. This is useful if you are using this in an environment where you have it, or want to create your own.  
```java
SQLDatabaseRegistry registry = new SQLDatabaseRegistry(logger);
```  
However, if you are lazy or don't care about the semantics of the Logger, you can use the static method in StarSQL to get one using a default logger implementation.  
```java
SQLDatabaseRegistry registry = StarSQL.createDatabaseRegistry();
```
After we have a Registry instance, we can move forward to creating a code representation of our Database.  

The first step in this is to create an instance of `SQLProperties` that is based on your chosen Database Type.  
These classes are using a Builder-Style pattern for ease of use.  
For example, since I chose MySQL, I am going to use the `MySQLProperties` class.  
```java
MySQLProperties properties = new MySQLProperties().setHost("HOST").setDatabaseName("DATABASE").setUsername("USERNAME").setPassword("PASSWORD");
```
These are not the actual values and you should replace them with your own details depending on the type of database you chose.  

Now we can create an instance of our Database  
```java
SQLDatabase database = new MySQLDatabase(registry.getLogger(), properties);
```

Now we can start creating classes based on how we want tables to be set up.  
I am just going to create one class for now and then we can work our way through the steps.  
```java
public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    private Person() {}
  
    //Standard Getters and Setters for the fields, as well as IntelliJ IDEA generated toString() method.
}
```
This is going to be our base example class for this and this is setup in a way where nothing from the library is in place, however this will technically work still.  
The `private long id` field will be automatically detected as the Primary Key and set to Auto-Increment. This library is entirely based on having this type of field.  
You can use longs and ints for this field and it will automatically be detected as such. However there are Annotations you can use if you so desire, more on these later.  
What this will do is create a table named `person` in the database with columns called `id`, `firstname`, `lastname` and `age` of appropriate types based on mapping of Java Types to SQL Types.  
The No-Args constructor is **critical** as without it, the library can't create new instances of our class.  
The no-args constructor can be any access modifier.  
Now we need to tell the library about this class, and we do that by registering it with the database.  
```java
database.registerClass(Person.class);
```
And in order to get the library to actually create things, we must register the database with the Registry and call the setup method.
```java
registry.register(database);
registry.setup();
```
This does have a checked Exception, for this tutorial, I am just passing it on to the parent method, which in this case is just the main method.  
For reference, this is what my main method looks like right now   
```java
public static void main(String[] args) throws Exception {
    SQLDatabaseRegistry registry = StarSQL.createDatabaseRegistry();
    MySQLProperties properties = new MySQLProperties().setHost("HOST").setDatabaseName("DATABASE").setUsername("USERNAME").setPassword("PASSWORD");
    SQLDatabase database = new MySQLDatabase(registry.getLogger(), properties);
    database.registerClass(Person.class);
    registry.register(database);
    registry.setup();
}
```
You must call the setup method after you register everything, otherwise it will not work. I will probably change this in a future update to dynamically register after the setup method has been called.  
Running this will get the following in phpMyAdmin, which I am using for easy viewing and manipulation of databases.  
<img width="711" alt="Screenshot 2023-11-01 000606" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/9f71fa41-012d-4b09-9f59-cd34f9776987">  
<img width="656" alt="Screenshot 2023-11-01 000751" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/3a85defe-022a-45af-aff9-ccd7d1dcfa65">  

Now, there isn't anything there, and we really don't want to use that name for the table, in this case, I want to name this table, `people`.  
We do have to drop this table, which we can do via phpMyAdmin or via the console. I haven't gotten to doing table modifications yet, and it will probably be a while before I get to it.  

In order to customize the name of the Table, we can use the `@Name` annotation from `com.stardevllc.stardata.api.annotations`. Below is what the class signature will look like with this change.  
```java
@Name("people")
public class Person {
    //The rest of the class remained the same
}
```
The best thing about this is that we don't need to use that name for everything as the library operates using the Class instance much like the `registerClass` method did. So if you change the name of the database, you don't need to update your code anywhere else.  
Doing this change will just have the table name be different, nothing else changed.  

Now I am going to create a method called `populateDatabase` to just make it easier to manipulate the logic of this test. This method will contain logic to create a Person and then save it to the database.  
```java
Person person = new Person(firstName, lastName, age);
```
The `firstName`, `lastName` and `age` arguments are being generated using helper methods and are out of the scope of this tutorial.  
Then to save the person to the database all we do is the following  
```java
database.save(person);
```
The `save` and `saveSilent` methods will take in any object and will try to find a table for it.  
`save` will pass the `Exception` to the caller and `saveSilent` will just silence the exception and not even print it out.  

I am generating 10 random people for this demonstration, the database now looks like this if I browse the table.  
<img width="182" alt="Screenshot 2023-11-01 002527" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/acb15ba8-3643-4152-9e3a-b8ac41122dc9">  
You can probably guess what I am doing for the random name generation now :)  

So now that we know how to model data and save it to the database, how do we get it back?  
It's pretty simple...  
```java
List<Person> people = database.get(Person.class);
```
Note: This will always return a List, and it will never be null, you will get an Exception before you get a null List.  
If nothing matches, it will return an empty list, if only one matches, then it will return a list with only one entry in it.  
This `get` method returns all database entries that are registered under that table. This is just doing a `select * from {table}` query.  
There are two other `get` methods...
The first one is just `Database.get(Class<?> clazz, String columnName, Object columnValue)` which lets you get an entry that matches a column name and column value, the most common use case is to get an entry based on the ID.
```java
List<Person> people = database.get(Person.class, "id", 1);
```
Again, this still returns a NotNull List, check against the size and get the first index if you know for sure.  
```java
if (people.size() == 1) {
    Person person = people.get(0);
}
```
This is the main reason why this returns a List over any other collection, because you can get it based on the index of the List.  
The other one is pretty much the same thing, but you are passing in an array of Strings and an array of Objects. The Strings represent the columns and the Objects represent the values. Both must be the same length, otherwise an error will be thrown. The order of the arrays does matter. I don't have a good demonstration of this method with this set up, but I have used it, and it will probably be rare to use this last one.  
For now, we will go back to the first one, with just the single argument.  
We will loop through them, and print them out to the console to verify that everything looks OK.  
```java
List<Person> people = database.get(Person.class);
for (Person person : people) {
    System.out.println(person);
}
```
<img width="369" alt="Screenshot 2023-11-01 003916" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/58ef5eb8-2e5c-40dc-bf1f-8ede254bd6ff">  

Pretty good so far, but it's kind of boring and this library can do more.  

You can customize the tables with the following annotations (That don't require further explaination): `@AutoIncrement`, `@DBNotNull` (Named this way to prevent conflicts with other annotations), `@Name`, `@Unique` and `@PrimaryKey` (This one you really don't need if you are using an int or long with the name "id". You can also use the `@ID` annotation on an int or long that is not named "id" and have the same reuslts.  
The `@Order(int)` annotation allows you to specify the column order in the table. This exists because Java Reflection does not guaruntee that the order of the fields is the same as the declaration order. The lower the number, the higher the column is on the list. So if we want, we can make the following modification to our class...  
```java
@Order(1) private long id;
@Order(2) private String firstName;
@Order(3) private String lastName;
@Order(4) private int age;
```
And it will ensure that the columns are in that order when it creates the table. For clarity's sake, I will be redoing the database every time we make a change to the structure to ensure that there isn't much of a problem.  

Now, let's talk about some of the rules of the fields of a class that define how things work.  
There are default implementations of a class called `TypeHandler` for 8 primitive types, String and UUID. I will not go too far in dept on this as I will be creating a Wiki down the road, but these are the building blocks of the columns, much like they are the building blocks of Java Classes.  
Fields can be any access modifier (`public`, `protected`, `package-private` and `private`)
Fields that have the `@Ignored` annotation are ignored by the library.  
Fields that have the `transient` modifier or fields that are both `static` and `final` are also ignored by the library.  
Final fields are not ignored as they can be changed with Reflection.  
Fields that are an `ObservableValue` or any of the child classes from StarLib are also ignored unless they have a `@Codec` annotation and type assigned to them. This will be covered in the future.  
Collections and Maps are ignored unless they are linked with a Foreign Key (Covered further down), or have a `@Codec` attached to them.  
Fields that do not have a `@Codec` or `TypeHandler` or are not covered by the `Foreign Key` system will also be ignored. `TypeHandler`s will be covered in the future, or you can take a look at the `com.stardevllc.stardata.sql.objects.typehandlers.impl` package and `DatabaseRegistry.addTypeHandler()` and see if you can figure it out before I can make a tutorial for it.  

Now, with that out of the way, lets add another class and table!  
```java
@Name("transactions")
public class Transaction {
    @Order(1) private long id;
    @Order(2) private int amount;
    @Order(3) private long timestamp;
    @Order(4) private long personid;

    //Public cosntructor that takes in an int amount and long timestamp and assigns them to the fields
    //Private No-Args constructor
    //Standard Getters and Setters and an IntelliJ IDEA generated toString() method
}
```  
Note: Using an int for the amount because it looks nicer in the database and print-outs.  
And Database Registration  
```java
database.registerClass(Transaction.class);
```
I did this registration after the Person registration, it will be important in the near future.  
In the Person class, I added the following...  
```java
private List<Transaction> transactions = new ArrayList<>();

public List<Transaction> getTransactions() {
    return transactions;
}
    
public void addTransaction(Transaction transaction) {
    transaction.setPersonid(this.getId());
    this.transactions.add(transaction);
}
```  
In my populate method, I will be generating 5 transactions per person and saving them.  
```java
for (int i = 0; i < 10; i++) {
    Person person = new Person(firstName, lastName, age);
    database.save(person);
            
    for (int t = 0; t < 5; t++) {
        Transaction transaction = new Transaction(amount, time);
        person.addTransaction(transaction);
        database.save(transaction);
    }
}
```
Notice how I am saving the person before I am doing things with the transactions? That is because in the basic form it is, there is no syncing going on as the transactions field in the Person class is ignored due to being a collection and it having nothing else to link it to other things.  
For loading this data, the code is like this  
```java
List<Person> people = database.get(Person.class);
for (Person person : people) {
    List<Transaction> transactions = database.get(Transaction.class, "personid", person.getId());
    person.getTransactions().addAll(transactions);
    system.out.println(person);
}
```
Table List  
<img width="722" alt="Screenshot 2023-11-01 011614" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/4829d09e-3763-4c47-894e-87a9ce4fecd8">
The People table didn't change due to the transactions field being ignored  
The transactons table with the first 10 rows (total of 50)  
<img width="198" alt="Screenshot 2023-11-01 011714" src="https://github.com/StarDevelopmentLLC/StarData/assets/9711989/7cfce0df-3520-4e0c-b89d-bbf72a331f51">  
And here is Gist of the console output from printing the person (It is too big to fit)  
https://gist.github.com/Firestar311/fb85ea9052afb185b85ffe368faeeee4  

In additon to this, to finish our structure for this tutorial, here is the last class and changes  
```java
@Name("addresses")
public class Address {
    @Order(1) private long id;
    @Order(2) private int streetNumber;
    @Order(3) private String city;
    @Order(4) private String state;
    @Order(5) private long personid;

    //Public constructor that takes in an int streetNumber, String city and String state and assigns them to the fields.
    //Private No-Args constructor
    //Standard Getters and Setters and an IntelliJ IDEA generated toString() method
}
```
Changes to Person  
```java
@Ignored private Address address;

public Address getAddress() {
    return address;
}

public void setAddress(Address address) {
    address.setPersonid(this.getId());
    this.address = address;
}
```
The address field has to be ignored for now until we get further along. 
And Database registration  
```java
database.registerClass(Address.class);
```  
This is added after the person is saved and before the transactions are generated in the population logic  
```java
Address address = new Address(streetNumber, city, state);
person.setAddress(address);
database.save(address);
```

And this is added after the addAll call on transactions  
```java
Address address = database.get(Address.class, "personid", person.getId()).get(0);
person.setAddress(address);
```
Yes, I should be doing a size check, but I am not for the sake of the tutorial.  
So, why did I do all of this repetition? Because repetition is good for learning. And this helped me to test the library as well before I released it.  

By now, you can see where this can get tedious and still not super easy to manage the stuff, better than using SQL directly, but still pretty cumbersome.  
For example, while writing this, I forgot some of the statements above and got errors, so I am not immune to making these mistakes. But this can be solved with a recently implemented feature, `Foreign Keys`.  

These allow you to link tables together using the Primary Key in a parent table to link to a column in a child table.  
We can do this for this example using the Person class (People table) as our parent table and the Address and Transaction classes as the child tables.  
Note: This library supports more than 1 parent table, its just that for this example, only one parent table makes any sense.  

The primary thing to keep in mind with foriegn keys in this library is that you need to have a field in the child class that is the same type as the primary key field in the parent class. The name doesn't matter as we will be using two annotations to link them together.  
In our example, both the Transaction and Address classes have a field named `personid`, this is the field that we will be using with foriegn keys. This was intentional as I made this example first, then worked my way backwards for the tutorial.  

First, lets add the foreign key to the address as this will be easier to demonstrate.  
All you need to do is add the `@ForeignKey` annotation and specify the class of the parent table you want to link it to. The library will then find the Primary Key column automatically.  
```java
@ForeignKey(Person.class)
@Order(5) private long personid;
```
We aren't done yet though, as if we want to save and load automatically into a field in the Person class, we need a `@ForeignKeyStorage` on the parent (Person) class.  
We can also remove that `@Ignored` annotation from the address field.  
```java
@ForeignKeyStorage(clazz = Address.class, field = "personid")
private Address address;
```
The `clazz` parameter is the class instance of the child class with teh foreign key.  
The `field` parameter is the field name that has the `@ForeignKey` annotation with the class link.  
I did it this way to support Generics, Collections and Maps as those type arguments are annoying to get at run-time and child classes can have multiple Foreign Keys, and it would be a lot of work for the library to have to search for it. This way, the programmer can specify it and make it clear when looking at either class to know what is going on.  
Now, we don't have to set the personid field in the Address anymore as the library will do that automatically.  
This is what the saving looks like (I changed the setAddress method to no longer set the personid and I made the address a constructor parameter)  
```java
Address address = new Address(streetNumber, city, state);
Person person = new Person(firstName, lastName, age, address);
database.save(person);
```
Now the `database.save` will do the hard work of making sure that the address is saved when the Person is saved.  
Loading the person means we don't have to call the `Database.get()` method on the Address anymore as it will be loaded automatically due to that `@ForeignKeyStorage` annotation.  
Here is the console output of one person just so you can see it. I omitted the transactions part of it.  
```txt
Person{id=1, firstName='ervpyu', lastName='jpisrx', age=7, address=Address{id=1, streetNumber=523, city='dtsd', state='iq', personid=1}, transactions=[{OMITTED}]}
```  
And yes, this is what it is in the database as well.  

So now what about that list of Transactions? Well, we can do the same thing.  
Change to the Transaction Class  
```java
@ForeignKey(Person.class)
@Order(4) private long personid;
```
Change to the Person Class  
```java
@ForeignKeyStorage(clazz = Transaction.class, field = "personid")
private List<Transaction> transactions = new ArrayList<>();
```
The real main difference is that you have to make sure that the collection field is not null.  
So now I can remove the `transaction.setPersonid()` call in the `addTransaction` method on the Person and even add a constructor parameter to take in an initial set of transactions.  
While I am at it, to make it easier to read, I wll change the amount of generated transactions to 2 per person.  
So now the generation loop looks like  
```java
for (int i = 0; i < 10; i++) {
    Address address = new Address(streetNumber, city, state);

    List<Transaction> transactions = new ArrayList<>();
    for (int t = 0; t < 2; t++) {
        transactions.add(new Transaction(amount, time));
    }
            
    Person person = new Person(generateString(6), generateString(6), generateAge(), address, transactions);
    database.save(person);
}
```
Loading has gone back to a single line  
```java
List<Person> people = database.get(Person.class);
```
And here is proof using a single console output  
```txt
Person{id=1, firstName='yfizcg', lastName='aumizv', age=67, address=Address{id=1, streetNumber=145, city='uyhr', state='bc', personid=1}, transactions=[Transaction{id=1, amount=870, timestamp=76738, personid=1}, Transaction{id=2, amount=940, timestamp=2942, personid=1}]}
```

So using foreign keys makes the hassle of manually loading data stored in other classes much cleaner and easier. 

Now this has all been about saving and loading data, what about updating and deleting data?  

For updating data, you just call the `save` method again after making your changes and as long as you didnt change the Primary Key, it should update just fine.  
Deleting data is just calling one of the `delete` methods. One of these takes in a class and an id and the other takes in an object. 

MORE COMING
