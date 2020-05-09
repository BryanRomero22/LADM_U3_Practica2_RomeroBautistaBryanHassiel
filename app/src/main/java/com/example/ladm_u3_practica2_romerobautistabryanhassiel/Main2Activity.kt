package com.example.ladm_u3_practica2_romerobautistabryanhassiel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    var baseRemota = FirebaseFirestore.getInstance()
    var dataLista = ArrayList<String>()
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        baseRemota.collection("restaurante")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    Toast.makeText(this, "ERROR NO SE PUEDE ACCEDER A CONSULTA", Toast.LENGTH_LONG)
                        .show()
                    return@addSnapshotListener
                }
                dataLista.clear()
                listaID.clear()
                for(document in querySnapshot!!){
                    var cadena = "Nombre: " + document.getString("nombre") +
                            "\nDomicilio: " + document.getString("domicilio") +
                            "\nCelular: " + document.getString("celular") +
                            "\nProducto: " + document.get("pedido.producto") +
                            "\nCantidad: " + document.get("pedido.cantidad") +
                            "\nPrecio: " + document.get("pedido.precio") +
                            "\nEntregado: " + document.get("pedido.entregado")
                    dataLista.add(cadena)
                    listaID.add(document.id)
                }
                if(dataLista.size == 0){
                    dataLista.add("NO HAY DATA")
                }
                var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataLista)
                lista.adapter = adapter
            }

        lista.setOnItemClickListener { parent, view, position, id ->
            if(listaID.size == 0){
                return@setOnItemClickListener
            }

            eliminaOActualizar(position)
        }
    }

    fun eliminaOActualizar(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("¿Qué deseas hacer con\n ${dataLista[position]}?")
            .setPositiveButton("Eliminar"){d, w ->
                baseRemota.collection("restaurante")
                    .document(listaID[position])
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "SE ELIMINO EL REGISTRO", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "NO SE PUDO ELIMINAR", Toast.LENGTH_LONG)
                            .show()
                    }
            }
            .setNegativeButton("Actualizar"){d, w ->
                actualizar(listaID[position])
            }
            .setNeutralButton("Cancelar"){d, w -> }
            .show()
    }

    fun actualizar(id: String) {
        baseRemota.collection("restaurante")
            .document(id)
            .get()
            .addOnSuccessListener {
                var v = Intent(this, Main3Activity::class.java)
                v.putExtra("id", id)
                v.putExtra("nombre", it.getString("nombre"))
                v.putExtra("domicilio", it.getString("domicilio"))
                v.putExtra("celular", it.getString("celular"))
                v.putExtra("producto", it.get("pedido.producto").toString())
                v.putExtra("precio", it.get("pedido.precio").toString())
                v.putExtra("cantidad", it.get("pedido.cantidad").toString())
                v.putExtra("entregado", it.get("pedido.entregado").toString())
                startActivity(v)
            }
            .addOnFailureListener {
                Toast.makeText(this, "ERROR! NO HAY CONEXION DE RED", Toast.LENGTH_LONG)
                    .show()
            }
    }
}
