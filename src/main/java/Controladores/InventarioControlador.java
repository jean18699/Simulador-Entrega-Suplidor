package Controladores;

import Modelo.Articulo;
import Modelo.Inventario;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.util.HashMap;
import java.util.Map;

public class InventarioControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();
    Inventario inventario;

    public InventarioControlador(Javalin app, Inventario inventario){
        this.app = app;
        this.inventario = inventario;
        modelo.put("listaArticulos",inventario.getArticulos());
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

    }

    public void aplicarRutas() throws NumberFormatException{

        app.get("/",ctx -> {

            ctx.render("templates/Inventario.html",modelo);

        });

        app.post("/realizarMovimiento",ctx -> {
            System.out.println(ctx.formParamMap());
            inventario.realizarMovimiento(ctx.formParam("codigoArticulo"),Long.parseLong(ctx.formParam("codigoAlmacen"))
            ,ctx.formParam("tipoMovimiento"),Long.parseLong(ctx.formParam("cantidad")));

            ctx.redirect("/");
        });


    }

}
