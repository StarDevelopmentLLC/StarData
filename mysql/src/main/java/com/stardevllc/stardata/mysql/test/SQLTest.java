package com.stardevllc.stardata.mysql.test;

import com.stardevllc.stardata.api.interfaces.SQLDatabase;
import com.stardevllc.stardata.mysql.MySQLDatabase;
import com.stardevllc.stardata.mysql.MySQLProperties;
import com.stardevllc.stardata.sql.StarSQL;
import com.stardevllc.stardata.sql.objects.SQLDatabaseRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SQLTest {

    private static final String[] letters = "abcdefghijklmnopqrstuvwxyz".split("");
    private static final Random random = new Random();
    
    public static void main(String[] args) throws Exception {
        SQLDatabaseRegistry registry = StarSQL.createDatabaseRegistry();
        MySQLProperties properties = new MySQLProperties().setHost("localhost").setDatabaseName("test").setUsername("root").setPassword("niles3408");
        SQLDatabase database = new MySQLDatabase(registry.getLogger(), properties);
        database.registerClass(Address.class);
        database.registerClass(Person.class);
        database.registerClass(Transaction.class);
        registry.register(database);
        registry.setup();

        //populateDatabase(database);
        
        Address address = new Address(123, "Foo", "Bar");
        Person person1 = new Person("John", "Smith", 32, address);
        person1.addTransaction(new Transaction(100, 123456789));
        person1.addTransaction(new Transaction(400, 987654321));
        database.save(person1);
        
        Person person2 = new Person("James", "Smith", 5, address);
        database.save(person2);

        System.out.println(person1);
        System.out.println(person2);

//        Person person = database.get(Person.class).get(0);
//        Address address = person.getAddress();
//        List<Transaction> transactions = person.getTransactions();
//        Transaction transaction1 = transactions.get(0);
//        Transaction transaction2 = transactions.get(1);
//        
//        database.delete(transaction1);
        
//        address.setState("Baz");
//        database.save(address);
    }
    
    private static void populateDatabase(SQLDatabase database) throws Exception {
        for (int i = 0; i < 10; i++) {
            Address address = new Address(random.nextInt(100, 1000), generateString(4), generateString(2));

            List<Transaction> transactions = new ArrayList<>();
            for (int t = 0; t < 2; t++) {
                transactions.add(new Transaction(random.nextInt(1, 1001), random.nextLong(1, 100000)));
            }
            
            Person person = new Person(generateString(6), generateString(6), generateAge(), address, transactions);
            database.save(person);
        }
    }
    
    private static int generateAge() {
        return random.nextInt(1, 101);
    }
    
    private static String generateString(int amount) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < amount; n++) {
            sb.append(letters[random.nextInt(letters.length)]);
        }
        return sb.toString();
    }
}