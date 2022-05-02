package mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD

import android.content.ContentValues
import android.content.Context
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.baseDatos
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.tipoDato.Asignacion

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
    //getAll
    fun getAll(): ArrayList<Asignacion> {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).readableDatabase
        val columnas = arrayOf("IDASIGNACION","NOM_EMPLEADO", "AREA_TRABAJO", "FECHA", "CODIGOBARRAS")
        val cursor = tabla.query("ASIGNACION", columnas, null, null, null, null, null)
        val lista = ArrayList<Asignacion>()
        if (cursor.moveToFirst()) {
            do {
                val asignacion = Asignacion(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4))
                lista.add(asignacion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        tabla.close()
        return lista
    }
    //getOne by barras
    fun getOneByBarras(barras:String): Asignacion {
        val tabla = baseDatos(p, "EquiposComputo", null, 1).readableDatabase
        val columnas = arrayOf("IDASIGNACION","NOM_EMPLEADO", "AREA_TRABAJO", "FECHA", "CODIGOBARRAS")
        val cursor = tabla.query("ASIGNACION", columnas, "CODIGOBARRAS = ?", arrayOf(barras), null, null, null)
        val asignacion = Asignacion(0,"","","","")
        if (cursor.moveToFirst()) {
            asignacion.id = cursor.getInt(0)
            asignacion.empleado = cursor.getString(1)
            asignacion.area = cursor.getString(2)
            asignacion.fecha = cursor.getString(3)
            asignacion.CodigoBarra = cursor.getString(4)
        }
        cursor.close()
        tabla.close()
        return asignacion
    }
}