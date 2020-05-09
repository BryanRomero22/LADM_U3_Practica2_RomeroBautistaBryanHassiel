package com.example.ladm_u3_practica2_romerobautistabryanhassiel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    var baseRemota = FirebaseFirestore.getInstance()
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extras = intent.extras
        id = extras!!.getString("id").toString()
        editText7.setText(extras.getString("nombre"))
        editText8.setText(extras.getString("domicilio"))
        editText9.setText(extras.getString("celular"))
        editText10.setText(extras.getString("producto"))
        editText11.setText(extras.getString("precio"))
        editText12.setText(extras.getString("cantidad"))
        switch2.isChecked = extras.getString("entregado")!!.toBoolean()

        button3.setOnClickListener {
            baseRemota.collection("restaurante")
                .document(id)
                .update("nombre", editText7.text.toString(), "domicilio", editText8.text.toString(),
                    "celular", editText9.text.toString(), "pedido.producto", editText10.text.toString(), "pedido.precio",
                    editText11.text.toString().toFloat(), "pedido.cantidad", editText12.text.toString().toInt(),
                    "pedido.entregado", switch2.isChecked)
                .addOnSuccessListener {
                    Toast.makeText(this, "SE HA ACTUALIZADO EL REGISTRO", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "ERROR AL ACTUALIZAR", Toast.LENGTH_LONG)
                        .show()
                }
        }

        button4.setOnClickListener {
            finish()
        }

    }
}
