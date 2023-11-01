package com.stardevllc.stardata.mysql;

import com.stardevllc.stardata.api.interfaces.SQLDatabase;
import com.stardevllc.stardata.sql.StarSQL;
import com.stardevllc.stardata.sql.objects.SQLDatabaseRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public final class StarMySQL {
    public static void main(String[] args) throws Exception {
        SQLDatabaseRegistry databaseRegistry = StarSQL.createDatabaseRegistry();
        SQLDatabase database = new MySQLDatabase(databaseRegistry.getLogger(), new MySQLProperties().setHost("localhost").setDatabaseName("test").setUsername("root").setPassword("niles3408"));
        database.registerClass(Person.class);
        database.registerClass(Address.class);
        database.registerClass(Transaction.class);
        databaseRegistry.register(database);
        databaseRegistry.setup();
        
        String[] letters = "abcdefghijklmnopqrstuvwxyz".split("");
        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            String firstName = "", lastName = "", city = "", state = "";
            for (int n = 0; n < 6; n++) {
                firstName += letters[random.nextInt(letters.length)];
                lastName += letters[random.nextInt(letters.length)];
            }
            
            for (int c = 0; c < 4; c++) {
                city += letters[random.nextInt(letters.length)];
            }
            
            for (int s = 0; s < 2; s++) {
                state += letters[random.nextInt(letters.length)];
            }
            
            int age = random.nextInt(16, 101);
            Address address = new Address(random.nextInt(100, 999), city, state);
            
            List<Transaction> transactions = new ArrayList<>();

            for (int t = 0; t < 2; t++) {
                Transaction transaction = new Transaction(random.nextInt(1, 1000), System.currentTimeMillis());
                transactions.add(transaction);
            }
            
            Person person = new Person(firstName, lastName, age, address, transactions);
            database.save(person);
        }

        List<Person> people = database.get(Person.class);
        for (Person person : people) {
            System.out.println(person);
        }
    }
}
