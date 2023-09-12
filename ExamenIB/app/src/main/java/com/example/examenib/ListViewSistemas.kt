package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class ListViewSistemas : AppCompatActivity() {
    val arreglo = SistemaSolarDATA.getSistemas()
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_sistemas)

        val listView = findViewById<ListView>(R.id.ss_list_view)
        val adaptor = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptor
        adaptor.notifyDataSetChanged()

    }
}