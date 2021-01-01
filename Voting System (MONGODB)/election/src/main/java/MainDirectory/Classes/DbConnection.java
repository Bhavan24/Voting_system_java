package MainDirectory.Classes;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import javafx.scene.control.Alert;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * This Class is for MongoDB Connection
 * This is a singleton connection class
 * All MongoDB methods are inside this class
 */
public class DbConnection {
    private static MongoDatabase database = null;

    private DbConnection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("election");
    }

    public static MongoDatabase getDbConnection() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        if (database == null) {
            new DbConnection();
        }
        return database;
    }

    // Method to create unique fields in database
    public static void createUniqueIndex(String field, String collectionName) {
        MongoDatabase db = DbConnection.getDbConnection();
        MongoCollection<Document> collection = db.getCollection(collectionName);
        Document index = new Document(field, 1);
        collection.createIndex(index, new IndexOptions().unique(true));
    }

    // Method to add candidates to database
    public static void addCandidates(int id, String name, String party) {
        MongoDatabase database = getDbConnection();
        createUniqueIndex("id", "candidate");
        createUniqueIndex("id", "result");

        MongoCollection<Document> collection = database.getCollection("candidate");
        Document document = new Document();
        document.put("id", id);
        document.put("name", name);
        document.put("party", party);
        collection.insertOne(document);

        MongoCollection<Document> collection2 = database.getCollection("result");
        Document document2 = new Document();
        document2.put("id", id);
        document2.put("name", name);
        document2.put("votes", 0);
        collection2.insertOne(document2);
    }

    // Method to delete candidate from the database
    public static void deleteCandidate(int id) {
        MongoDatabase database = getDbConnection();

        MongoCollection<Document> collection = database.getCollection("candidate");
        collection.deleteOne(new Document("id", id));

        MongoCollection<Document> collection2 = database.getCollection("result");
        collection2.deleteOne(new Document("id", id));
    }

    // Method to edit candidate in the database
    public static void editCandidate(int id, String name, String party) {
        MongoDatabase database = getDbConnection();

        MongoCollection<Document> collection = database.getCollection("candidate");
        collection.updateOne(eq("id", id), new Document("$set", new Document("name", name)));
        collection.updateOne(eq("id", id), new Document("$set", new Document("party", party)));

        MongoCollection<Document> collection2 = database.getCollection("result");
        collection2.updateOne(eq("id", id), new Document("$set", new Document("name", name)));
    }

    // Method to add voter to database
    public static void addVoter(int voter_id, String first_name, String last_name,
                                String address, String gender, String dob) {
        MongoDatabase database = getDbConnection();

        MongoCollection<Document> collection = database.getCollection("voter");
        Document document = new Document();
        document.put("voter_id", voter_id);
        document.put("first_name", first_name);
        document.put("last_name", last_name);
        document.put("address", address);
        document.put("gender", gender);
        document.put("dob", dob);
        collection.insertOne(document);
    }

    // Method to delete voter from the database
    public static void deleteVoter(int voter_id) {
        MongoDatabase database = getDbConnection();
        MongoCollection<Document> collection = database.getCollection("voter");
        collection.deleteOne(new Document("voter_id", voter_id));
    }

    // Method to edit voter in the database
    public static void editVoter(int voter_id, String first_name,
                                 String last_name, String address,
                                 String gender, String dob) {
        MongoDatabase database = getDbConnection();

        MongoCollection<Document> collection = database.getCollection("voter");
        collection.updateOne(eq("voter_id", voter_id),
                new Document("$set", new Document("first_name", first_name)));
        collection.updateOne(eq("voter_id", voter_id),
                new Document("$set", new Document("last_name", last_name)));
        collection.updateOne(eq("voter_id", voter_id),
                new Document("$set", new Document("address", address)));
        collection.updateOne(eq("voter_id", voter_id),
                new Document("$set", new Document("gender", gender)));
        collection.updateOne(eq("voter_id", voter_id),
                new Document("$set", new Document("dob", dob)));
    }

    // Method to add vote to the candidate (incrementing 1)
    public static void addVote(String name) {
        MongoDatabase database = getDbConnection();
        MongoCollection<Document> collection = database.getCollection("result");
        collection.updateOne(eq("name", name), new Document("$inc", new Document("votes", 1)));
    }

    // Method to reset votes = 0 when program ends
    public static void resetVotes() {
        MongoDatabase database = getDbConnection();
        MongoCollection<Document> collection = database.getCollection("result");
        collection.updateMany(new Document(), new Document("$set", new Document("votes", 0)));
    }

    // Method to check candidates & voters != 0
    public static boolean dataExists() {
        MongoDatabase database = getDbConnection();

        MongoCollection<Document> collection = database.getCollection("candidate");
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> it = findIterable.iterator();

        MongoCollection<Document> collection2 = database.getCollection("voter");
        FindIterable<Document> findIterable2 = collection2.find();
        MongoCursor<Document> it2 = findIterable2.iterator();

        return it.hasNext() && it2.hasNext();
    }

    // This method checks username & password (returns boolean)
    public static boolean validateAdmin(String username, String password) {
        boolean b = true;

        MongoDatabase db = getDbConnection();
        MongoCollection<Document> collection = db.getCollection("admin");
        Document document = collection.find(and(eq("username", username),
                eq("password", password))).first();

        if (document == null) {
            b = false;
        }
        return b;
    }

    // This method checks voter_id (returns boolean)
    public static boolean validateVoter(int voter_id) {
        boolean b = true;

        MongoDatabase db = getDbConnection();
        MongoCollection<Document> collection = db.getCollection("voter");
        Document document = collection.find(eq("voter_id", voter_id)).first();

        if (document == null) {
            b = false;
        }
        return b;
    }

    // This method is to find winner of the election
    public static String findWinner() {
        String winner;
        MongoDatabase database = getDbConnection();
        MongoCollection<Document> collection = database.getCollection("result");
        // sorting the votes & finding the maximum votes
        FindIterable<Document> findIterable = collection.find().sort(eq("votes", -1)).limit(1);
        MongoCursor<Document> it = findIterable.iterator();
        Document document = it.next();
        winner = document.getString("name");
        return winner;
    }

    // This method imports a csv file to database using file path
    public static void importVoters(String path) {
        try {
            MongoDatabase db = DbConnection.getDbConnection();
            MongoCollection<Document> collection = db.getCollection("voter");

            List<Document> list = new ArrayList<>(); // list to store the data
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            // reading all lines in csv file
            while ((line = reader.readLine()) != null) {
                // csv file -> array -> variables -> document
                String[] item = line.split(",");
                String voter_id = item[0];
                String first_name = item[1];
                String last_name = item[2];
                String address = item[3];
                String gender = item[4];
                String dob = item[5];

                Document document = new Document();
                int voter_id_int = Integer.parseInt(voter_id);
                document.put("voter_id", voter_id_int);
                document.put("first_name", first_name);
                document.put("last_name", last_name);
                document.put("address", address);
                document.put("gender", gender);
                document.put("dob", dob);

                list.add(document);
            }
            collection.insertMany(list);
            createUniqueIndex("voter_id", "voter");
        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}