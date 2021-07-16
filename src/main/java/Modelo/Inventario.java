package Modelo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Inventario {

    private List<Articulo> articulos;
    private List<ArticuloSuplidor> articulosSuplidores;
    private List<Movimiento> movimientosInventario;
    private List<OrdenCompra> ordenesCompra;
    private MongoCollection<Document> db_articulos;
    private MongoCollection<Document> db_articulosSuplidores;
    private MongoCollection<Document> db_movimientoInventario;
    private MongoCollection<Document> db_ordenCompra;

    public Inventario(MongoDatabase db)
    {
        db_articulos = db.getCollection("articulos");
        db_articulosSuplidores = db.getCollection("articuloSuplidor");
        db_movimientoInventario = db.getCollection("movimientoInventario");
        db_ordenCompra = db.getCollection("ordenCompra");
        ordenesCompra = new ArrayList<>();
        cargarArticulos();
        cargarArticulosSuplidores();
        cargarMovimientosInventario();
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

    public void nuevaOrden(OrdenCompra orden){

        LocalDate fechaActual = LocalDate.now();
        orden.setFechaOrden(fechaActual);
        ordenesCompra.add(orden);

        Document doc_orden = new Document();
        List<DBObject> arrayArticulos = new ArrayList<>();


        doc_orden.append("codigoOrden",orden.getCodigoOrden())
                .append("codigoSuplidor",orden.getCodigoSuplidor())
                .append("fechaOrden",fechaActual)
                .append("fechaRequerida",orden.getFechaRequerida());

        List<Document> articulosOrdenados = new ArrayList<>();

        for(ArticuloOrdenado articulo : orden.getArticulos())
        {
            articulosOrdenados.add(new Document("codigoArticulo",articulo.getCodigoArticulo())
                    .append("cantidadOrdenada",articulo.getCantidadOrdenada())
                    .append("precioCompra",articulo.getPrecioCompra()));
        }


        doc_orden.append("articulos",articulosOrdenados);
        db_ordenCompra.insertOne(doc_orden);
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
                db_movimientoInventario.insertOne(doc_movimiento);
                break;
            }
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
        System.out.println(Math.ceil(cantidadConsumida / contador));
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
}
