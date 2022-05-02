package mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD.ASIGNACION
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD.INVENTARIO
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.databinding.FragmentListaAsignacionBinding
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.databinding.FragmentListaInventarioBinding
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.recyclers.RecyclerAsignacion
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.recyclers.RecyclerInventario
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.tipoDato.Asignacion
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.tipoDato.Inventario

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [listaAsignacion.newInstance] factory method to
 * create an instance of this fragment.
 */
class listaAsignacion : Fragment(), RecyclerAsignacion.onClickListener, SearchView.OnQueryTextListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var _binding: FragmentListaAsignacionBinding
    private val b get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaAsignacionBinding.inflate(inflater, container, false)
        return b.root
    }
    lateinit var listaAsignacion:MutableList<Asignacion>
    var listaAsignacionOriginal:MutableList<Asignacion>? = null
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        setupRecyclerView()
        b.search.setOnQueryTextListener(this)
    }
    private fun setupRecyclerView(){
        b.listaAsignacion.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        b.listaAsignacion.addItemDecoration(
            DividerItemDecoration(activity?.applicationContext!!,
                DividerItemDecoration.VERTICAL)
        )
        val i = ASIGNACION(activity?.applicationContext!!)
        listaAsignacion = i.getAll()
        listaAsignacionOriginal = i.getAll()
        b.listaAsignacion.adapter = RecyclerAsignacion(activity?.applicationContext!!,listaAsignacion,this)

    }

    private fun RecargarRecycler(){
        b.listaAsignacion.adapter = RecyclerAsignacion(activity?.applicationContext!!,listaAsignacion,this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listaAsignacion.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listaAsignacion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(
        codigoBarras: String,
        nombre: String,
        area: String,
        fecha: String,
        itemView: View,
        position: Int
    ): Boolean {
        val menu = PopupMenu(context,itemView)
        val builder = AlertDialog.Builder(context)
        val info = AlertDialog.Builder(context)
        menu.menu.add("Editar")
        menu.menu.add("Ver")
        menu.menu.add("Desasignar")
        menu.setOnMenuItemClickListener {
            when(it.title){
                "Desasignar" -> {
                    builder.setTitle("Borrar")
                    builder.setMessage("¿Esta seguro de borrar la asignacion?")
                    builder.setPositiveButton("Si"){_,_ ->
                        val i = ASIGNACION(activity?.applicationContext!!)
                        i.eliminarbarras(codigoBarras)
                        listaAsignacion.removeAt(position)
                        RecargarRecycler()
                    }
                    builder.setNegativeButton("No"){_,_ ->
                        Toast.makeText(context,"No se borro",Toast.LENGTH_SHORT).show()
                    }
                    builder.show()
                }
                "Editar" -> {
                }
                "Ver" -> {
                    val i = ASIGNACION(activity?.applicationContext!!)
                    val inventario = i.getOneByBarras(codigoBarras)
                    info.setTitle("Informacion")
                    info.setMessage("Codigo de barras: ${inventario.CodigoBarra}\n" +
                            "Nombre: ${inventario.empleado}\n" +
                            "Area: ${inventario.area}\n" +
                            "Fecha: ${inventario.fecha}\n")
                    info.show()
                }
            }
            true
        }
        menu.show()
        return true
    }

    fun filtrado(txtBuscar: String) {
        val longitud = txtBuscar.length
        if(longitud == 0){
            listaAsignacion.clear()
            listaAsignacion.addAll(listaAsignacionOriginal!!)
        }else{
            listaAsignacion.clear()
            listaAsignacion.addAll(listaAsignacionOriginal!!)
            val collecion = listaAsignacion.filter {
                it.CodigoBarra.lowercase().contains(txtBuscar.lowercase()).or(
                    it.area.lowercase().contains(txtBuscar.lowercase())
                ).or(it.empleado.lowercase().contains(txtBuscar.lowercase())).or(
                    it.fecha.lowercase().contains(txtBuscar.lowercase())
                )
            }
            listaAsignacion.clear()
            listaAsignacion.addAll(collecion)
        }
        RecargarRecycler()
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null) {
            filtrado(p0)
        }
        return false
    }
}