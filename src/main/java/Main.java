import Modelo.Almacen;
import Modelo.Articulo;
import Modelo.ArticuloSuplidor;
import Modelo.Inventario;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.info.Info;
import org.bson.Document;

public class Main {

    public static void main(String[] args)
    {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase db = mongoClient.getDatabase("inventario");


        Javalin app = Javalin.create(config ->{
           // config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.enableCorsForAllOrigins();
            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
        }).start(7000);

        //AREA DE PRUEBAS=============================================================//
        
        Inventario inv = new Inventario(db);
        inv.agregarArticulo(new Articulo("MON-001",1,50,"Monitor"));
        inv.agregarArticulo(new Articulo("MON-002",1,50,"Monitor"));
        inv.agregarArticulo(new Articulo("MON-002",2,50,"Monitor"));

         //inv.realizarMovimiento("MON-001",1,"SALIDA",30);

        //alm.agregarArticulo(art1);
        //alm.realizarMovimiento("salida",2,50);

       // alm.realizarMovimiento("Salida",1,3);
        //System.out.println(alm.getArticulos().get(0).getCantidad());

        inv.agregarArticuloSuplidor(new ArticuloSuplidor(50, "a", (long) 10.2, 85));


    }

    private static OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("My Application");
        return new OpenApiOptions(applicationInfo).path("/openapi").swagger(new SwaggerOptions("/openapi-ui"));
    }

}
