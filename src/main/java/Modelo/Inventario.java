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
    private MongoCollection<Document> db_articulos;

    public Inventario(MongoDatabase db)
    {
        db_articulos = db.getCollection("articulos");
        cargarArticulos();
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

    public void agregarArticulo(Articulo articulo){
        articulos.add(articulo);
        Document doc_articulo = new Document();
        doc_articulo.append("codigoArticulo",articulo.getCodigoArticulo())
                .append("descripcion",articulo.getDescripcion())
                .append("almacen",new Document("codigoAlmacen",articulo.getAlmacen().getCodigoAlmacen())
                .append("balanceActual",articulo.getAlmacen().getBalanceActual()));

        db_articulos.insertOne(doc_articulo);
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

}
