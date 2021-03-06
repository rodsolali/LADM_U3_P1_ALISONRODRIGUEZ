package mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.material.datepicker.MaterialDatePicker
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.CRUD.INVENTARIO
import mx.tecnm.tepic.ladm_u3_p1_alisonrodriguez.databinding.FragmentAgregarInventarioBinding
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregarInventario.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregarInventario : Fragment() {
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

    private lateinit var _binding: FragmentAgregarInventarioBinding
    private val b get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgregarInventarioBinding.inflate(inflater, container, false)
        return b.root
    }
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        val builder : MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Selecciona la fecha de vencimiento")
        val picker: MaterialDatePicker<*> = builder.build()
        var calendar: Calendar
        var format : SimpleDateFormat
        var formattedDate = "2022-01-01"

        b.btnFecha.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!,picker.toString())
        }

        picker.addOnPositiveButtonClickListener {
            b.txtFechaCompra.setText(picker.headerText)
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.setTimeInMillis(it as Long)
            format = SimpleDateFormat("yyyy-MM-dd")
            calendar.add(Calendar.DATE, 1)
            formattedDate = format.format(calendar.getTime())
        }
        b.btnInsertar.setOnClickListener {
            val inventario = INVENTARIO(activity?.applicationContext!!)
            inventario.Caracteristicas = b.txtCaracteristicas.text.toString()
            inventario.FechaCompra = b.txtFechaCompra.text.toString()
            inventario.codigoBarras = b.txtCodigobarra.text.toString()
            inventario.tipoEquipo = b.txtTipoEquipo.text.toString()
            if(inventario.insertar()){
                Toast.makeText(activity?.applicationContext!!,"Equipo insertado",Toast.LENGTH_SHORT).show()
                b.txtCaracteristicas.setText("")
                b.txtFechaCompra.setText("")
                b.txtCodigobarra.setText("")
                b.txtTipoEquipo.setText("")
            }
            else{
                Toast.makeText(activity?.applicationContext!!,"Error al insertar",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment agregarInventario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregarInventario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}