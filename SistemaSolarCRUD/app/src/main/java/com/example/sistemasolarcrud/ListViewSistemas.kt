package com.example.sistemasolarcrud

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup

class ListViewSistemas : AppCompatActivity() {
    val arreglo = SistemaSolarDATA.getSistemas()
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_sistemas)
        val listView = findViewById<ListView>(R.id.ss_list_view)
        var adaptor = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptor
        adaptor.notifyDataSetChanged()
        val botonAnadir = findViewById<Button>(R.id.btn_anadir)
        botonAnadir.setOnClickListener {
            mostrarFormularioSS()
            adaptor.notifyDataSetChanged()
        }

    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
        // this.startActivity()
    }
    private fun mostrarFormularioSS() {
        val inflater = LayoutInflater.from(this)
        val formularioView = inflater.inflate(R.layout.activity_crear_sistema_solar, null)
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
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Formulario Creación Sistema solar")
            .setView(formularioView)
            .setPositiveButton("Crear") { _, _ ->
                var arrPl = ArrayList<Planeta>()
                SistemaSolarDATA.createSistemaSolar(nombreSS,0,arrPl,extension,estrC)
                mostrarDialogElementoCreado()
            }
            .setNegativeButton("Cancelar", null)
        dialogBuilder.create().show()
    }
    private fun mostrarDialogElementoCreado() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Éxito")
            .setMessage("Elemento creado exitosamente")
            .setPositiveButton("Aceptar", null)

        dialogBuilder.create().show()
    }
}