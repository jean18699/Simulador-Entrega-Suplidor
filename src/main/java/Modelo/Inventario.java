package Modelo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Inventario {

    private List<Articulo> articulos;
    private List<ArticuloSuplidor> articulosSuplidores;
    private MongoCollection<Document> db_articulos;
    private MongoCollection<Document> db_articulosSuplidores;

    public Inventario(MongoDatabase db)
    {
        db_articulos = db.getCollection("articulos");
        db_articulosSuplidores = db.getCollection("articuloSuplidor");
        cargarArticulos();
        cargarArticulosSuplidores();
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

    //Funcion para realizar un movimiento. Tambien actualiza la base de datos
    public void realizarMovimiento(String codigoArticulo, long codigoAlmacen, String tipoMovimiento, long cantidad)
    {
        Almacen articulo;

        for(Articulo art : articulos)
        {
            if(art.getCodigoArticulo().equalsIgnoreCase(codigoArticulo) && art.getAlmacen().getCodigoAlmacen() == codigoAlmacen)
            {
                articulo = art.getAlmacen();

                if(tipoMovimiento.equalsIgnoreCase("ENTRADA"))
                {
                    articulo.setBalanceActual(articulo.getBalanceActual() + cantidad);
                    actualizarCantidadArticuloBD(art.getCodigoArticulo(),articulo.getCodigoAlmacen(),articulo.getBalanceActual());
                }else
                {
                    articulo.setBalanceActual(Math.max(0,articulo.getBalanceActual() - cantidad));
                    actualizarCantidadArticuloBD(art.getCodigoArticulo(),articulo.getCodigoAlmacen(),articulo.getBalanceActual());
                }
            }
        }
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
