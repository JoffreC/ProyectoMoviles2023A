package com.example.examenibcrud

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class ListViewSS : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<ISistemaSolar> = arrayListOf()
    var idItemSeleccionado = 0
    private lateinit var adaptor: ArrayAdapter<ISistemaSolar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_ss)
        val listView = findViewById<ListView>(R.id.lv_sistemas)
        mostrarSSs()
        adaptor = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptor
        adaptor.notifyDataSetChanged()
        val botonAnadir = findViewById<Button>(R.id.btn_anadir)
        botonAnadir.setOnClickListener {
            mostrarFormSS()
        }
        registerForContextMenu(listView)
    }
    fun mostrarSSs(){
        val db = Firebase.firestore
        val ss = db.collection("sistemas_solares")
        limpiarArreglo()
        adaptor.notifyDataSetChanged()
        ss
            .get()
            .addOnSuccessListener {
                for (sistemaSolar in it){
                    sistemaSolar.id
                    agregarAArreglo(sistemaSolar)
                }
                adaptor.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }
    fun agregarAArreglo(sistemaSolar: QueryDocumentSnapshot){
        val nuevoSS = ISistemaSolar(
            sistemaSolar.id as String?,
            sistemaSolar.data.get("nombre") as String?,
            sistemaSolar.data.get("numero_planetas") as Int?,
            sistemaSolar.data.get("extension") as Double?,
            sistemaSolar.data.get("estrella") as String?,
        )
        arreglo.add(nuevoSS)
    }
    fun limpiarArreglo(){
        arreglo.clear()
    }
    fun mostrarFormSS() {
        val db = Firebase.firestore
        val ss = db.collection("sistemas_solares")
        val inflater = LayoutInflater.from(this)
        val formularioView = inflater.inflate(R.layout.form_ss, null)
        val dialogBuilder = MaterialAlertDialogBuilder(this)
            .setView(formularioView)
            .setTitle("Formulario Creación Sistema solar")
            .setPositiveButton("Crear") { _, _ ->
                val nombreSS = formularioView.findViewById<EditText>(R.id.input_n_ss).text.toString()
                val extension = formularioView.findViewById<EditText>(R.id.input_e).text.toString().toDouble()
                val radG = formularioView.findViewById<RadioGroup>(R.id.radioG)
                var estrC = ""
                if(radG.checkedRadioButtonId == R.id.rb_ea){
                    estrC = "G"
                }else if(radG.checkedRadioButtonId == R.id.rb_em){
                    estrC = "O"
                }else if(radG.checkedRadioButtonId == R.id.rb_er){
                    estrC = "M"
                }
                var arrPl = ArrayList<IPlaneta>()
                var numPl = 0
                val data = hashMapOf(
                    "nombre" to nombreSS,
                    "numero_planetas" to numPl,
                    "extension" to extension,
                    "estrella" to estrC,
                )
                ss.document(Date().time.toString()).set(data)
                arreglo.clear()
                mostrarSSs()
                adaptor.notifyDataSetChanged()
                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
        dialogBuilder.create().show()
    }
    fun mostrarFormSSA() {
        val db = Firebase.firestore
        val ss = db.collection("sistemas_solares")
        val inflater = LayoutInflater.from(this)
        val formularioView = inflater.inflate(R.layout.form_ss, null)
        val s = arreglo[idItemSeleccionado]
        val nombreSS = s.getNombre()
        val extension = s.getExtension().toString()
        val rB = s.getEstrellaCentral()
        val i_n = formularioView.findViewById<EditText>(R.id.input_n_ss)
        i_n.setText(nombreSS)
        val i_e = formularioView.findViewById<EditText>(R.id.input_e)
        i_e.setText(extension)
        val rbEa = formularioView.findViewById<RadioButton>(R.id.rb_ea)
        val rbEm = formularioView.findViewById<RadioButton>(R.id.rb_em)
        val rbEr = formularioView.findViewById<RadioButton>(R.id.rb_er)
        if(rB == "G"){
            rbEa.isChecked = true
        }else if(rB == "O"){
            rbEm.isChecked = true
        }else if(rB == "M"){
            rbEr.isChecked = true
        }
        val dialogBuilder = MaterialAlertDialogBuilder(this)
            .setView(formularioView)
            .setTitle("Formulario Actualización Sistema solar")
            .setPositiveButton("Actualizar") { _, _ ->
                val radG = formularioView.findViewById<RadioGroup>(R.id.radioG)
                var estrC = ""
                if(radG.checkedRadioButtonId == R.id.rb_ea){
                    estrC = "G"
                }else if(radG.checkedRadioButtonId == R.id.rb_em){
                    estrC = "O"
                }else if(radG.checkedRadioButtonId == R.id.rb_er){
                    estrC = "M"
                }
                val data = hashMapOf(
                    "nombre" to i_n.text.toString(),
                    "numero_planetas" to s.getNumeroPlanetas(),
                    "extension" to i_e.text.toString().toDouble(),
                    "estrella" to estrC,
                )
                if(s.getId() != null){
                    ss.document(s.getId()!!).set(data)
                }
                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
                arreglo.clear()
                mostrarSSs()
                adaptor.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
        dialogBuilder.create().show()
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                mostrarFormSSA()
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogo()
                return true
            }
            R.id.mi_ver_pl ->{
                val intent = Intent(this, ListViewPl::class.java)
                intent.putExtra("nombreSS", arreglo[idItemSeleccionado].getNombre())
                intent.putExtra("idItem", arreglo[idItemSeleccionado].getId())
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener{
                dialog, which ->
                val db = Firebase.firestore
                val referenciaSS = db.collection("sistemas_solares")
                arreglo[idItemSeleccionado].getId()?.let {
                    referenciaSS
                        .document(it)
                        .delete()
                        .addOnSuccessListener {  }
                        .addOnFailureListener {  }
                }
                arreglo.clear()
                mostrarSSs()
                adaptor.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ss, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSeleccionado = info.position
    }
    override fun onResume() {
        super.onResume()
        arreglo.clear()
        mostrarSSs()
        adaptor.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()
        arreglo.clear()
        mostrarSSs()
        adaptor.notifyDataSetChanged()
    }
}