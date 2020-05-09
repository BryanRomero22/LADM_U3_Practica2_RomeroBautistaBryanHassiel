package com.example.ladm_u3_practica2_romerobautistabryanhassiel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var baseRemota = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            agregar()
        }

        button2.setOnClickListener {
            var otraV = Intent(this, Main2Activity::class.java)
            startActivity(otraV)
        }
    }

    fun agregar() {
        var data = hashMapOf(
            "nombre" to editText.text.toString(),
            "domicilio" to editText2.text.toString(),
            "celular" to editText3.text.toString(),
            "pedido" to hashMapOf(
                "producto" to editText4.text.toString(),
                "precio" to editText5.text.toString().toFloat(),
                "cantidad" to editText6.text.toString().toInt(),
                "entregado" to switch1.isChecked
            )
        )

        baseRemota.collection("restaurante")
            .add(data as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "SE AGREGO REGISTRO", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG)
                    .show()
            }

        editText.setText("")
        editText2.setText("")
        editText3.setText("")
        editText4.setText("")
        editText5.setText("")
        editText6.setText("")
        switch1.isChecked = false
    }
}
