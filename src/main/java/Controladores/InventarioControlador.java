package Controladores;

import Modelo.Articulo;
import Modelo.ArticuloOrdenado;
import Modelo.Inventario;
import Modelo.OrdenCompra;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventarioControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();
    Inventario inventario;
    OrdenCompra ordenCompra;
    List<Integer> idArticulo;
    // Para conversión de fecha de freemarker
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventarioControlador(Javalin app, Inventario inventario){
        this.app = app;
        this.inventario = inventario;
        modelo.put("listaConsumos",inventario.getListaConsumos());
        modelo.put("listaArticulos",inventario.getArticulos());
        idArticulo = new ArrayList<>();
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

    }

    public void aplicarRutas() throws NumberFormatException{

        app.get("/",ctx -> {

            inventario.actualizarInventario(LocalDate.now());
            ctx.render("templates/Inventario.html",modelo);

        });

        app.post("/realizarMovimiento",ctx -> {

            inventario.realizarMovimiento(ctx.formParam("codigoArticulo"),Long.parseLong(ctx.formParam("codigoAlmacen"))
            ,ctx.formParam("tipoMovimiento"),Long.parseLong(ctx.formParam("cantidad")));


            ctx.redirect("/");
        });

        app.get("/ordenCompra",ctx -> {
            Map<String, List<ArticuloOrdenado>> articulosOrdenados = new HashMap<String, List<ArticuloOrdenado>>() {{
                ArrayList<ArticuloOrdenado> arregloArticulosOrdenados = new ArrayList<ArticuloOrdenado>();
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                put("articulosOrdenados", arregloArticulosOrdenados);
            }};

            // for (int i=0;i<5; i++) {
            //     testModel.get("myCustomStrings").add("myCustomString " + i); 
            // }
            ctx.render("templates/OrdenCompra.html",articulosOrdenados);

        });

        app.get("/listadoOrdenes",ctx -> {
            Map<String, List<ArticuloOrdenado>> articulosOrdenados = new HashMap<String, List<ArticuloOrdenado>>() {{
                ArrayList<ArticuloOrdenado> arregloArticulosOrdenados = new ArrayList<ArticuloOrdenado>();
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                arregloArticulosOrdenados.add(new ArticuloOrdenado());
                put("articulosOrdenados", arregloArticulosOrdenados);
            }};

            // for (int i=0;i<5; i++) {
            //     testModel.get("myCustomStrings").add("myCustomString " + i); 
            // }
            ctx.render("templates/ListadoOrdenes.html",articulosOrdenados);

        });


        app.get("/crearPedido", ctx -> {
           ctx.redirect("/crearPedido/1");
        });

        app.post("/modificarPedido", ctx -> {
            ctx.redirect("/crearPedido/"+ctx.formParam("cantidadArticulos"));
        });

        app.get("/crearPedido/:cantidad", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            idArticulo = new ArrayList<>();

            for(int i = 1; i <= Integer.parseInt(ctx.pathParam("cantidad")); i++)
            {
                idArticulo.add(i);
            }
            modelo.put("cantidadArticulos",idArticulo);
            modelo.put("cantidadActual",ctx.pathParam("cantidad"));
            ctx.render("templates/CrearOrden.html",modelo);
        });

        app.post("/realizarPedido", ctx -> {

            LocalDate fechaOrden = LocalDate.parse(ctx.formParam("fechaOrden"));

           for(int i = 1; i <= idArticulo.size(); i++)
           {
               System.out.println();
               if(inventario.getArticuloByCodigo_Almacen(ctx.formParam("codigoArticulo"+i), Long.parseLong(ctx.formParam("codigoAlmacen"+i))) != null)
               {
                   ArticuloOrdenado articuloOrdenado = new ArticuloOrdenado(ctx.formParam("codigoArticulo"+i),Long.parseLong(ctx.formParam("cantidadOrdenada"+i)),
                           Long.parseLong(ctx.formParam("codigoAlmacen"+i)));

                   inventario.generarOrdenCompra(articuloOrdenado,fechaOrden);
               }
           }

           ctx.redirect("/crearPedido/"+1);
        });


        app.post("/realizarOrdenCompra", ctx -> {

            // System.out.println(ctx.formParam("${ordenCompra.codigoOrden}=[3]"));

            List attributes = new ArrayList();

            Set<String> keys = ctx.formParamMap().keySet();


            for (String key : keys) {
                attributes.add(ctx.formParamMap().get(key).get(0));
            }

            // List array = new ArrayList(ctx.formParamMap().values());

            String fechaRequeridaString = (String) attributes.get(0);

            LocalDate fechaRequerida = LocalDate.parse(fechaRequeridaString, dateFormatter);

            attributes.remove(0);

            List<Articulo> articulos = new ArrayList<Articulo>();



            // for (int i = 0; i < attributes.size(); i+=3) {
            //     articulos.add(new Articulo())
            // }

            
            // System.out.println(attributes);
            // System.out.println(fechaRequerida);
            

            
            // ordenCompra.realizarOrdenCompra(ctx.formParam("codigoArticuloOrden"), Long.parseLong(ctx.formParam("codigoAlmacen")));
        });



    }

}
