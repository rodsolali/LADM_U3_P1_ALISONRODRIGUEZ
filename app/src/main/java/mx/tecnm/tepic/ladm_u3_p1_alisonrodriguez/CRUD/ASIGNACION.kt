package mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD

import android.content.ContentValues
import android.content.Context
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.baseDatos

class ASIGNACION(p: Context) {
    val p= p
    var nombreEmpleado = ""
    var areaTrabajo =""
    var Fecha = "2022-01-01"
    var CodigoBarras = ""

    //add
    fun insertar(): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val dato = ContentValues()
        dato.put("NOM_EMPLEADO", nombreEmpleado)
        dato.put("AREA_TRABAJO", areaTrabajo)
        dato.put("FECHA", Fecha)
        dato.put("CODIGOBARRAS", CodigoBarras)
        val respuesta = tabla.insert("ASIGNACION", null, dato)
        tabla.close()
        return respuesta.toInt() != -1
    }
    //update
    fun actualizar(id:String): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val dato = ContentValues()
        dato.put("NOM_EMPLEADO", nombreEmpleado)
        dato.put("AREA_TRABAJO", areaTrabajo)
        dato.put("FECHA", Fecha)
        dato.put("CODIGOBARRAS", CodigoBarras)
        val respuesta = tabla.update("ASIGNACION", dato, "IDASIGNACION = ?", arrayOf(id))
        tabla.close()
        return respuesta.toInt() != -1
    }
    //delete
    fun eliminarbarras(barras:String): Boolean {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).writableDatabase
        val respuesta = tabla.delete("ASIGNACION", "CODIGOBARRAS= ?", arrayOf(barras))
        tabla.close()
        return respuesta != -1
    }
    //get
    fun buscar(id:String): ArrayList<String> {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).readableDatabase
        val columnas = arrayOf("NOM_EMPLEADO", "AREA_TRABAJO", "FECHA", "CODIGOBARRAS")
        val cursor = tabla.query("ASIGNACION", columnas, "IDASIGNACION = ?", arrayOf(id), null, null, null)
        val lista = ArrayList<String>()
        if (cursor.moveToFirst()) {
            lista.add(cursor.getString(0))
            lista.add(cursor.getString(1))
            lista.add(cursor.getString(2))
            lista.add(cursor.getString(3))
        }
        cursor.close()
        tabla.close()
        return lista
    }
}