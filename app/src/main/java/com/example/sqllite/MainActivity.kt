package com.example.sqllite

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
class MainActivity : AppCompatActivity() {

    private lateinit var etnCodigo:EditText
    private lateinit var etDescripcion:EditText
    private lateinit var etnPrecio:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etnCodigo = findViewById(R.id.etn_Codigo)
        etDescripcion = findViewById(R.id.et_Descripcion)
        etnPrecio = findViewById(R.id.etn_Precio)
    }
    fun registrar(vista: View){
        //se crea la Conexion a las Base de Datos.

        val admin = AdminSQLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString()

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            codigo.toInt()
            precio.toDouble()

            val registro = ContentValues()
            registro.put("Codigo",codigo)
            registro.put("Descripcion",descripcion)
            registro.put("Precio",precio)

            baseDeDatos.insert("articulos",null, registro)
            baseDeDatos.close()
            etnPrecio.setText("")
            etDescripcion.setText("")
            etnCodigo.setText("")

            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show()
        }

    }


    fun buscar(vista:View){
        //se crea la Conexion a las Base de Datos.

        val admin = AdminSQLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()

        if (!codigo.isEmpty()){
            //verificar si es el codigo del Producto
            val fila = baseDeDatos.rawQuery("select Descripcion, Precio from articulos where Codigo=${codigo}",null)
            if (fila.moveToFirst()){
                etDescripcion.setText(fila.getString(0))
                etnPrecio.setText(fila.getString(1))
                baseDeDatos.close()
            }else{
                Toast.makeText( this,"No Existe el Articulo", Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }
        }else{
            Toast.makeText(this, "Debes Ingresar Un Codigo", Toast.LENGTH_SHORT).show()
        }

    }

    fun modificar(vista:View){
        //se crea la Conexion a las Base de Datos.

        val admin = AdminSQLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString()

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            codigo.toInt()
            precio.toDouble()

            val registro = ContentValues()
            registro.put("Codigo",codigo)
            registro.put("Descripcion",descripcion)
            registro.put("Precio",precio)

            val cantidad:Int = baseDeDatos.update("articulos",registro,"Codigo=${codigo}",null)
            baseDeDatos.close()

            //verifica si la consulta trae valor

            if (cantidad==1){
                Toast.makeText(this, "Articulo modificado Correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No Existe el Articulo ", Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }

            etnPrecio.setText("")
            etDescripcion.setText("")
            etnCodigo.setText("")

            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show()
        }

    }


    fun eliminar(vista:View){
        //se crea la Conexion a las Base de Datos.

        val admin = AdminSQLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()

        if (!codigo.isEmpty()){
            //verificar si es el codigo del Producto
            val cantidad:Int = baseDeDatos.delete("articulos","Codigo=${codigo}",null)
            baseDeDatos.close()
            etnPrecio.setText("")
            etDescripcion.setText("")
            etnCodigo.setText("")

            if (cantidad==1){
                Toast.makeText(this, "Articulo Eliminado Correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No Existe el Articulo ", Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }

        }else{
            Toast.makeText( this,"Debes Ingresar Un Codigo", Toast.LENGTH_SHORT).show()
            baseDeDatos.close()
        }

    }

    fun siguiente(vista:View){
        val ventana:Intent = Intent(applicationContext,SharedPerferencesApp::class.java)
        startActivity(ventana)
    }
}