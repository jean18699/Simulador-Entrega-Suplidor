package Modelo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Inventario {

    private List<Articulo> articulos;
    private List<ArticuloSuplidor> articulosSuplidores;
    private List<ArticuloOrdenado> articuloOrdenados;
    private List<Movimiento> movimientosInventario;
    private List<OrdenCompra> ordenesCompra;
    private List<ConsumoDiario> listaConsumos;
    private MongoCollection<Document> db_articulos;
    private MongoCollection<Document> db_articulosSuplidores;
    private MongoCollection<Document> db_movimientoInventario;
    private MongoCollection<Document> db_ordenCompra;
    private MongoCollection<Document> db_consumoDiario;

    public Inventario(MongoDatabase db)
    {
        db_articulos = db.getCollection("articulos");
        db_articulosSuplidores = db.getCollection("articuloSuplidor");
        db_movimientoInventario = db.getCollection("movimientoInventario");
        db_consumoDiario = db.getCollection("consumoDiario");
        db_ordenCompra = db.getCollection("ordenCompra");
        ordenesCompra = new ArrayList<>();
        cargarArticulos();
        cargarArticulosSuplidores();
        cargarMovimientosInventario();
        cargarConsumosDiarios();
    }

    private void cargarArticulos()
    {
        articulos = new ArrayList<>();
        Articulo articulo;

        List<Document> parametrosAggregate = new ArrayList<>();

        parametrosAggregate.add(new Document("$project", new Document("_id",0).append("codigoArticulo","$codigoArticulo")
                .append("descripcion","$descripcion")
                .append("almacen","$almacen")
        ));

        List<Document> cursorArticulos = db_articulos.aggregate(parametrosAggregate).into(new ArrayList<>());
        Document almacenArticulo;
        for(Document doc : cursorArticulos) {
            almacenArticulo = (Document) doc.get("almacen");

            articulo = new Articulo(
                    doc.get("codigoArticulo").toString(),
                    Long.parseLong(almacenArticulo.get("codigoAlmacen").toString()),
                    Long.parseLong(almacenArticulo.get("balanceActual").toString()),
                    doc.get("descripcion").toString()
            );
            articulos.add(articulo);
        }
    }

    private void cargarMovimientosInventario()
    {
        movimientosInventario = new ArrayList<>();
        Movimiento movimiento;

        List<Document> parametrosAggregate = new ArrayList<>();

        parametrosAggregate.add(new Document("$project", new Document("_id",0)
                .append("codigoMovimiento", "$codigoMovimiento")
                .append("codigoAlmacen","$codigoAlmacen")
                .append("tipoMovimiento", "$tipoMovimiento")
                .append("codigoArticulo", "$codigoArticulo")
                .append("cantidad", "$cantidad")
        ));

        List<Document> cursorMovimientos = db_movimientoInventario.aggregate(parametrosAggregate).into(new ArrayList<>());

        for(Document doc : cursorMovimientos) {

            movimiento = new Movimiento(
                    Long.parseLong(doc.get("codigoMovimiento").toString()),
                    doc.get("codigoArticulo").toString(),
                    Long.parseLong(doc.get("codigoAlmacen").toString()),
                    doc.get("tipoMovimiento").toString(),
                    Long.parseLong(doc.get("cantidad").toString())

            );
            movimientosInventario.add(movimiento);
        }

    }

    private void cargarConsumosDiarios()
    {
        listaConsumos = new ArrayList<>();
        ConsumoDiario consumo;

        List<Document> parametrosAggregate = new ArrayList<>();

        parametrosAggregate.add(new Document("$project", new Document("_id",0)
                .append("codigoArticulo", "$codigoArticulo")
                .append("promedioConsumo", "$promedioConsumo")
        ));

        List<Document> cursorMovimientos = db_consumoDiario.aggregate(parametrosAggregate).into(new ArrayList<>());

        for(Document doc : cursorMovimientos) {
            consumo = new ConsumoDiario(doc.get("codigoArticulo").toString(), Long.parseLong(doc.get("promedioConsumo").toString()));
            listaConsumos.add(consumo);
        }
    }

    private void cargarArticulosSuplidores()
    {
        articulosSuplidores = new ArrayList<>();
        ArticuloSuplidor articuloSuplidor;

        List<Document> parametrosAggregate = new ArrayList<>();

        parametrosAggregate.add(new Document("$project", new Document("_id",0)
            .append("codigoSuplidor", "$codigoSuplidor")
            .append("codigoArticulo", "$codigoArticulo")
            .append("tiempoEntrega", "$tiempoEntrega")
            .append("precioCompra", "$precioCompra")
        ));

        List<Document> cursorArticulosSuplidores = db_articulosSuplidores.aggregate(parametrosAggregate).into(new ArrayList<>());
        // Document almacenArticuloSuplidor;
        for(Document doc : cursorArticulosSuplidores) {
            // almacenArticuloSuplidor = (Document) doc.get("almacen");

            articuloSuplidor = new ArticuloSuplidor(
                    Long.parseLong(doc.get("codigoSuplidor").toString()),
                    doc.get("codigoArticulo").toString(),
                    Long.parseLong(doc.get("tiempoEntrega").toString()),
                    Float.parseFloat(doc.get("precioCompra").toString())
            );
            articulosSuplidores.add(articuloSuplidor);
        }
    }


    public void agregarArticulo(Articulo articulo){
        articulos.add(articulo);
        Document doc_articulo = new Document();
        doc_articulo.append("codigoArticulo",articulo.getCodigoArticulo())
                .append("descripcion",articulo.getDescripcion())
                .append("almacen",new Document("codigoAlmacen",articulo.getAlmacen().getCodigoAlmacen())
                .append("balanceActual",articulo.getAlmacen().getBalanceActual()));

        db_articulos.insertOne(doc_articulo);
    }

    private void agregarConsumoDiario(ConsumoDiario consumoDiario)
    {
        System.out.println(consumoDiario.getPromedioConsumo());
        listaConsumos.add(consumoDiario);
        Document doc_consumoDiario = new Document();
        doc_consumoDiario
                .append("codigoArticulo", consumoDiario.getCodigoArticulo())
                .append("promedioConsumo", consumoDiario.getPromedioConsumo());

        db_consumoDiario.insertOne(doc_consumoDiario);
    }

    public void agregarArticuloSuplidor(ArticuloSuplidor articuloSuplidor) {
        articulosSuplidores.add(articuloSuplidor);
        Document doc_articuloSuplidor = new Document();
        doc_articuloSuplidor
                .append("codigoSuplidor", articuloSuplidor.getCodigoSuplidor())
                .append("codigoArticulo", articuloSuplidor.getCodigoArticulo())
                .append("tiempoEntrega", articuloSuplidor.getTiempoEntrega())
                .append("precioCompra", articuloSuplidor.getPrecioCompra());

        db_articulosSuplidores.insertOne(doc_articuloSuplidor);
    }

    //Funcion para realizar un movimiento. Tambien actualiza la base de datos
    public long realizarMovimiento(String codigoArticulo, long codigoAlmacen, String tipoMovimiento, long cantidad)
    {
        Almacen almacenArticulo = null;
        Movimiento movimiento = new Movimiento(codigoArticulo,codigoAlmacen,tipoMovimiento,cantidad);

        Document doc_movimiento = new Document();
        for(Articulo art : articulos)
        {
            if(art.getCodigoArticulo().equalsIgnoreCase(codigoArticulo) && art.getAlmacen().getCodigoAlmacen() == codigoAlmacen)
            {
                almacenArticulo = art.getAlmacen();
                doc_movimiento
                        .append("codigoMovimiento", movimiento.getCodigoMovimiento())
                        .append("codigoAlmacen",codigoAlmacen)
                        .append("tipoMovimiento", tipoMovimiento)
                        .append("codigoArticulo", codigoArticulo)
                        .append("cantidad", cantidad);

                if(tipoMovimiento.equalsIgnoreCase("ENTRADA"))
                {
                    almacenArticulo.setBalanceActual(almacenArticulo.getBalanceActual() + cantidad);
                }else
                {
                    almacenArticulo.setBalanceActual(Math.max(0,almacenArticulo.getBalanceActual() - cantidad));
                }
                actualizarCantidadArticuloBD(art.getCodigoArticulo(),almacenArticulo.getCodigoAlmacen(),almacenArticulo.getBalanceActual());
                movimientosInventario.add(movimiento);
                db_movimientoInventario.insertOne(doc_movimiento);
                break;
            }
        }

        if(tipoMovimiento.equalsIgnoreCase("SALIDA"))
        {
            for(int i = 0; i < listaConsumos.size(); i++)
            {
                if(listaConsumos.get(i).getCodigoArticulo().equalsIgnoreCase(codigoArticulo))
                {
                    listaConsumos.get(i).setPromedioConsumo(getPromedioConsumoArticulo(codigoArticulo, codigoAlmacen));
                    actualizarConsumoDiarioBD(codigoArticulo,getPromedioConsumoArticulo(codigoArticulo, codigoAlmacen));
                    return getPromedioConsumoArticulo(codigoArticulo, codigoAlmacen);
                }
            }
            agregarConsumoDiario(new ConsumoDiario(codigoArticulo,getPromedioConsumoArticulo(codigoArticulo, codigoAlmacen)));
        }




        return getPromedioConsumoArticulo(codigoArticulo, codigoAlmacen);
    }

    private long getPromedioConsumoArticulo(String codigoArticulo, long codigoAlmacen)
    {
        long contador = 0;
        long cantidadConsumida = 0;

        for(int i = 0; i < movimientosInventario.size();i++)
        {
            if(movimientosInventario.get(i).getCodigoArticulo().equalsIgnoreCase(codigoArticulo))
            {

                if(movimientosInventario.get(i).getCodigoAlmacen() == codigoAlmacen)
                {
                    if(movimientosInventario.get(i).getTipoMovimiento().equalsIgnoreCase("SALIDA"))
                    {

                        cantidadConsumida += movimientosInventario.get(i).getCantidad();
                        contador += 1;
                    }
                }
            }
        }

        if(contador == 0)
        {
            contador = 1;
        }

        return (long) Math.ceil(cantidadConsumida / contador);
    }

    private void actualizarCantidadArticuloBD(String codigoArticulo, long codigoAlmacen, long cantidad)
    {
        Bson[] filtros = {eq("codigoArticulo",codigoArticulo),eq("almacen.codigoAlmacen",codigoAlmacen)};
        BasicDBObject doc = new BasicDBObject();
        BasicDBObject update = new BasicDBObject();

        doc.put("almacen.balanceActual",cantidad);
        update.put("$set",doc);
        db_articulos.updateOne(and(filtros),update);
    }

    private void actualizarConsumoDiarioBD(String codigoArticulo, long promedioConsumo)
    {
        Bson[] filtros = {eq("codigoArticulo",codigoArticulo)};
        BasicDBObject doc = new BasicDBObject();
        BasicDBObject update = new BasicDBObject();

        doc.put("promedioConsumo",promedioConsumo);
        update.put("$set",doc);
        db_consumoDiario.updateOne(and(filtros),update);
    }

    public void crearOrden(OrdenCompra orden){

        LocalDate fechaActual = LocalDate.now();
        orden.setFechaOrden(fechaActual);
        ordenesCompra.add(orden);

        Document doc_orden = new Document();

        doc_orden.append("codigoOrden",orden.getCodigoOrden())
                .append("codigoSuplidor",orden.getCodigoSuplidor())
                .append("fechaOrden",orden.getFechaOrden())
                .append("articulo", new Document(
                        "codigoArticulo",orden.getArticuloOrdenado().getCodigoArticulo())
                        .append("cantidadOrdenada",orden.getArticuloOrdenado().getCantidadOrdenada())
                        .append("precioCompra",orden.getArticuloOrdenado().getPrecioCompra()
                ));

        ordenesCompra.add(orden);
        db_ordenCompra.insertOne(doc_orden);
    }


    public void genOrd(List<ArticuloOrdenado> articuloOrdenados)
    {

    }

    public ArticuloOrdenado generarOrdenCompra(ArticuloOrdenado articuloOrdenado, LocalDate fechaRequerida)
    {
        long consumoDiario = 0;
        LocalDate fechaActual = LocalDate.now();
        long cantidadDias = ChronoUnit.DAYS.between(fechaActual, fechaRequerida);
        long balanceInventario = 0;
        List<Document> parametrosAggregate = new ArrayList<>();

        for(int i = 0; i < listaConsumos.size(); i++)
        {
            if(articuloOrdenado.getCodigoArticulo().equalsIgnoreCase(listaConsumos.get(i).getCodigoArticulo()))
            {
                consumoDiario = listaConsumos.get(i).getPromedioConsumo();

            }
        }

        for(int i = 0; i < articulos.size(); i++)
        {
            if(articuloOrdenado.getCodigoArticulo().equalsIgnoreCase(articulos.get(i).getCodigoArticulo())
            && articuloOrdenado.getCodigoAlmacen() == articulos.get(i).getAlmacen().getCodigoAlmacen())
            {
                balanceInventario = articulos.get(i).getAlmacen().getBalanceActual();
                break;
            }
        }

        long totalATener = (consumoDiario*cantidadDias)+ articuloOrdenado.getCantidadOrdenada();
        long cantidadAPedir = Math.max(0,totalATener - balanceInventario);
        long totalConsumidoFechaRequerida = consumoDiario*cantidadDias;


        /***
         * db.articuloSuplidor.aggregate(
         *     {$match: {codigoArticulo: "TEC-001"}},
         *     {$sort: {tiempoEntrega: 1, precioCompra: 1}},
         *     {$limit: 1}
         *     )
         */
        parametrosAggregate.add(new Document("$match", new Document("codigoArticulo", articuloOrdenado.getCodigoArticulo())));
        parametrosAggregate.add(new Document("$sort", new Document("tiempoEntrega", 1).append("precioCompra",1)));
        parametrosAggregate.add(new Document("$limit", 1));
        
        ArticuloSuplidor suplidor = null;

        List<Document> cursorArticulosSuplidores = db_articulosSuplidores.aggregate(parametrosAggregate).into(new ArrayList<>());

        for(Document doc : cursorArticulosSuplidores) {
            suplidor = new ArticuloSuplidor(
                    Long.parseLong(doc.get("codigoSuplidor").toString()),
                    doc.get("codigoArticulo").toString(),
                    Long.parseLong(doc.get("tiempoEntrega").toString()),
                    Float.parseFloat(doc.get("precioCompra").toString())
            );
        }
        articuloOrdenado.setPrecioCompra(suplidor.getPrecioCompra());
        cantidadDias = suplidor.getTiempoEntrega();
        LocalDate fechaOrden = LocalDate.now();

        

        //OrdenCompra orden = new OrdenCompra(suplidor.getCodigoSuplidor(),fechaOrden.plusDays(cantidadDias),articuloOrdenado);
       // crearOrden(orden);


       /* System.out.println("Dias para Entregar: " + cantidadDias);
        System.out.println("Balance Inventario: "+ balanceInventario);
        System.out.println("Consumo Diario: "+ consumoDiario);
        System.out.println("Total Consumido hasta el "+fechaOrden.plusDays(cantidadDias)+": "+ totalConsumidoFechaRequerida);
        System.out.println("Requerido para el "+ fechaOrden.plusDays(cantidadDias)+": "+articuloOrdenado.getCantidadOrdenada());
        System.out.println("Total a Tener para el "+ fechaOrden.plusDays(cantidadDias)+": "+totalATener);
        System.out.println("Pedir: "+ cantidadAPedir);
        System.out.println("Orden al suplidor #"+suplidor.getCodigoSuplidor()+" el cual tarda "+suplidor.getTiempoEntrega()+" dias en entregar");
        //System.out.println("Fecha Requerida: " + fechaOrden.plusDays(cantidadDias));*/
    }


    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<ArticuloSuplidor> getArticulosSuplidores() {
        return articulosSuplidores;
    }

    public void setArticulosSuplidores(List<ArticuloSuplidor> articulosSuplidores) {
        this.articulosSuplidores = articulosSuplidores;
    }

    public MongoCollection<Document> getDb_articulos() {
        return db_articulos;
    }

    public void setDb_articulos(MongoCollection<Document> db_articulos) {
        this.db_articulos = db_articulos;
    }

    public MongoCollection<Document> getDb_articulosSuplidores() {
        return db_articulosSuplidores;
    }

    public void setDb_articulosSuplidores(MongoCollection<Document> db_articulosSuplidores) {
        this.db_articulosSuplidores = db_articulosSuplidores;
    }

    public List<Movimiento> getMovimientosInventario() {
        return movimientosInventario;
    }

    public void setMovimientosInventario(List<Movimiento> movimientosInventario) {
        this.movimientosInventario = movimientosInventario;
    }

    public List<OrdenCompra> getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(List<OrdenCompra> ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }

    public List<ConsumoDiario> getListaConsumos() {
        return listaConsumos;
    }

    public void setListaConsumos(List<ConsumoDiario> listaConsumos) {
        this.listaConsumos = listaConsumos;
    }
}
