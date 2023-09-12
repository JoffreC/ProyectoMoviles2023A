package com.example.sistemasolarcrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup

class CrearSistemaSolar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sistema_solar)
        val botonCrear = findViewById<Button>(R.id.btn_crear)
        botonCrear.setOnClickListener {
            val nombreSS = findViewById<EditText>(R.id.input_nombre_ss).text.toString()
            val extension = findViewById<EditText>(R.id.input_extension).text.toString().toDouble()
            val radG = findViewById<RadioGroup>(R.id.radioGroup)
            var estrC = ' '
            if(radG.checkedRadioButtonId == R.id.rb_es_a){
                estrC = 'G'
            }else if(radG.checkedRadioButtonId == R.id.rb_es_m){
                estrC = 'O'
            }else if(radG.checkedRadioButtonId == R.id.rb_en_r){
                estrC = 'M'
            }
            var arrPl = ArrayList<Planeta>()
            val s1 = SistemaSolarDATA.createSistemaSolar(nombreSS,0,arrPl,extension,estrC)
        }
        //irActividad()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
        // this.startActivity()
    }
}