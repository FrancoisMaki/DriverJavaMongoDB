package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Connection to your MongoDB Atlas
        String connectionString = "mongodb+srv://<USER>:<PASSWORD>@cluster0.grs5rca.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString))) {
            MongoDatabase database = mongoClient.getDatabase("<DATABASE>"); // replace with your database name
            MongoCollection<Document> collection = database.getCollection("<COLLECTION>"); // replace with your collection name

            // Read .json file and convert it into a document
            String jsonContent = Files.lines(Paths.get("src\\main\\java\\org\\example\\personajes_marvel_subir.json")).collect(Collectors.joining());
            Document doc = Document.parse(jsonContent);

            // Get the results array from the document
            List<Document> results = (List<Document>) doc.get("results");

            // Insert each document in the results array into the collection
            for (Document result : results) {
                collection.insertOne(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}