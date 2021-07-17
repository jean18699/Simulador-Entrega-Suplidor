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

public class InventarioControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();
    Inventario inventario;
    OrdenCompra ordenCompra;
    // Para conversión de fecha de freemarker
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventarioControlador(Javalin app, Inventario inventario){
        this.app = app;
        this.inventario = inventario;
        modelo.put("listaConsumos",inventario.getListaConsumos());
        modelo.put("listaArticulos",inventario.getArticulos());
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

    }

    public void aplicarRutas() throws NumberFormatException{

        app.get("/",ctx -> {

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

        app.post("/realizarOrdenCompra", ctx -> {

            // System.out.println(ctx.formParam("${ordenCompra.codigoOrden}=[3]"));
            System.out.println(ctx.formParamMap());
            System.out.println(ctx.formParam("${articulosOrdenados[0]}"));
            System.out.println(ctx.formParam("${articulosOrdenados}"));
            

            // LocalDate fechaRequerida = LocalDate.parse(ctx.formParam("fechaOrden"), dateFormatter);
            // ordenCompra.realizarOrdenCompra(ctx.formParam("codigoArticuloOrden"), Long.parseLong(ctx.formParam("codigoAlmacen")));
        });



    }

}
