import Controladores.InventarioControlador;
import Modelo.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("inventario");

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/bootstrap-5.0.1-dist"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.enableCorsForAllOrigins();
            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
        }).start(7000);

        Inventario inv = new Inventario(db);
        new InventarioControlador(app,inv).aplicarRutas();

        //AREA DE PRUEBAS=============================================================//
        //ArrayList<ArticuloOrdenado> ordenados = new ArrayList<>();
        //ordenados.add(new ArticuloOrdenado("MOU-001",3,10));
        //ordenados.add(new ArticuloOrdenado("MOU-002",2,50));


       // Inventario inv = new Inventario(db);
        //inv.nuevaOrden(new OrdenCompra(1,date,ordenados));


        //GENERADORES AUTOMATICOS TERMINADOS (EJECUTAR 1 SOLA VEZ!!!!! SI DESEAS OTRO RESULTADO BORRA LOS DATOS ANTERIORES EN MONGO Y EJECUTA ESTO DE NUEVO)
       // generarArticuloSuplidor(inv, 10);
        //generarArticulos(inv);


       // System.out.println(inv.realizarMovimiento("MOU-001",2,"SALIDA",10));
        //System.out.println(inv.getPromedioConsumoArticulo("MOU-001",2));




        // inv.agregarArticulo(new Articulo("MON-001",1,50,"Monitor"));
        //   inv.agregarArticulo(new Articulo("MON-002",1,50,"Monitor"));
        // inv.agregarArticulo(new Articulo("MON-002",2,50,"Monitor"));



        //alm.agregarArticulo(art1);
        //alm.realizarMovimiento("salida",2,50);

        // alm.realizarMovimiento("Salida",1,3);
        //System.out.println(alm.getArticulos().get(0).getCantidad());


        //System.out.println(inv.getArticulosSuplidores().get(0).getTiempoEntrega());

    }

    private static OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("My Application");
        return new OpenApiOptions(applicationInfo).path("/openapi").swagger(new SwaggerOptions("/openapi-ui"));
    }

    private static void generarArticuloSuplidor(Inventario inv, int cantidadSuplidores) {
        Random random = new Random();

        int maxPrecio = 500;
        int minPrecio = 100;

        ArrayList<String> tipoProducto = new ArrayList<>();
        tipoProducto.add("MOU");
        tipoProducto.add("MON");
        tipoProducto.add("TEC");

        for (int i = 1; i <= cantidadSuplidores; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= random.nextInt((3 - 0) + 1) + 0; k++) {
                    long entrega = random.nextInt((15 - 3) + 1) + 3;
                    float precio = random.nextInt((maxPrecio - minPrecio) + 1) + minPrecio;

                    inv.agregarArticuloSuplidor(new ArticuloSuplidor(
                            i, tipoProducto.get(j - 1) + "-00" + k, entrega, precio));
                }


            }
        }
    }

    private static void generarArticulos(Inventario inv) {
        Random random = new Random();

        int maxBalance = 100;
        int minBalance = 10;

        ArrayList<String> tipoProducto = new ArrayList<>();
        tipoProducto.add("MOU");
        tipoProducto.add("MON");
        tipoProducto.add("TEC");

        for (int i = 1; i <= 3; i++) {

            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= random.nextInt((3 - 0) + 1) + 0; k++) {


                    long balanceInicial = random.nextInt((maxBalance - minBalance) + 1) + minBalance;

                    if (tipoProducto.get(j - 1).equalsIgnoreCase("MOU")) {
                        inv.agregarArticulo(new Articulo(tipoProducto.get(j - 1) + "-00" + i, k, balanceInicial, "Mouse"));
                    } else if (tipoProducto.get(j - 1).equalsIgnoreCase("MON")) {
                        inv.agregarArticulo(new Articulo(tipoProducto.get(j - 1) + "-00" + i, k, balanceInicial, "Monitor"));
                    } else if (tipoProducto.get(j - 1).equalsIgnoreCase("TEC")) {
                        inv.agregarArticulo(new Articulo(tipoProducto.get(j - 1) + "-00" + i, k, balanceInicial, "Teclado"));
                    }

                }
            }

        }

    }
}

