import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    public static void main(String[] args)
    {
        MongoClient mongoClient = new MongoClient("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("Ventas");
        MongoCollection<Document> ordenes = db.getCollection("Ordenes");

        System.out.println(ordenes.count());

    }

}
