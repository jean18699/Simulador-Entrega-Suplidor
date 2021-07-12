import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    public static void main(String[] args)
    {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase db = mongoClient.getDatabase("inventario");
        MongoCollection<Document> articulos = db.getCollection("articulos");


    }

}
