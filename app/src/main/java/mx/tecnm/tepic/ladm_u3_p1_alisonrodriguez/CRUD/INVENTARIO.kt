package mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD

import android.content.ContentValues
import android.content.Context
import android.util.Log
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.baseDatos
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.tipoDato.Inventario

class INVENTARIO(p: Context) {
    val p = p
    var codigoBarras = ""
    var tipoEquipo = ""
    var Caracteristicas = ""
    var FechaCompra = "2022-01-01"


    //add inventario
    fun insertar(): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val dato = ContentValues()
        dato.put("codigoBarras", codigoBarras)
        dato.put("tipoEquipo", tipoEquipo)
        dato.put("Caracteristicas", Caracteristicas)
        dato.put("FechaCompra", FechaCompra)
        val respuesta = tabla.insert("INVENTARIO", null, dato)
        tabla.close()
        return respuesta.toInt() != -1
    }
    //get inventario tipoDato Inventario if has relation with ASIGNACION then asignacion is true
    fun getInventario():MutableList<Inventario> {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).readableDatabase
        val listaInventario = mutableListOf<Inventario>()

        val cursor = tabla.rawQuery("SELECT * FROM INVENTARIO i LEFT JOIN ASIGNACION a ON i.CODIGOBARRAS = a.CODIGOBARRAS WHERE a.CODIGOBARRAS is null", null)
        //then if has relation with ASIGNACION then asignacion is true
        if (cursor.moveToFirst()) {
            do {
                listaInventario.add(Inventario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), false))
            } while (cursor.moveToNext())
        }
        cursor.close()
        //inner join with ASIGNACION
        val cursor2 = tabla.rawQuery("SELECT * FROM INVENTARIO i INNER JOIN ASIGNACION a ON i.CODIGOBARRAS = a.CODIGOBARRAS ", null)
        if (cursor2.moveToFirst()) {
            do {
                listaInventario.add(Inventario(cursor2.getString(0), cursor2.getString(1), cursor2.getString(2), cursor2.getString(3), true))
            } while (cursor2.moveToNext())
        }
        cursor2.close()
        tabla.close()
        return listaInventario
    }

    fun getInventario(codigoBarras: String): Inventario {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).readableDatabase
        val cursor = tabla.rawQuery("SELECT * FROM INVENTARIO WHERE CODIGOBARRAS = '$codigoBarras'", null)
        var inventario = Inventario("", "", "", "", false)
        if (cursor.moveToFirst()) {
            do {
                inventario = Inventario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), false)
            } while (cursor.moveToNext())
        }
        cursor.close()
        tabla.close()
        return inventario
    }

    //update inventario
    fun actualizar(cbActualizar:String): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val dato = ContentValues()
        dato.put("codigoBarras", codigoBarras)
        dato.put("tipoEquipo", tipoEquipo)
        dato.put("Caracteristicas", Caracteristicas)
        dato.put("FechaCompra", FechaCompra)
        val respuesta = tabla.update("INVENTARIO", dato, "CODIGOBARRAS=?", arrayOf(cbActualizar))
        tabla.close()
        return respuesta != 0 && respuesta != -1
    }
    //delete inventario
    fun eliminar(cbEliminar:String): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val respuesta = tabla.delete("INVENTARIO", "CODIGOBARRAS=?", arrayOf(cbEliminar))
        tabla.close()
        return respuesta != 0 && respuesta != -1
    }



}