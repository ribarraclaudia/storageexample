package com.pruebacurso1.storageexample

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import  android.widget.Toast

//Almacenamiento Interno
import  java.io.File
import  java.io.FileOutputStream

//Almacenamiento Externo
import  android.os.Environment
import  java.io.BufferedReader
import  java.io.FileInputStream
import java.io.IOException
import  java.io.InputStreamReader

//

class MainActivity : AppCompatActivity() {
    //almacenamiento interno
    //val nombreArchivoAI = "miarchivoAI.txt"
    //val datosAI = "Contenido del archivo en almacenamiento interno."

    //Almacenamiento Externo
    //val nombreArchivoAE = "miarchivoAE.txt"
    //val datosAE = "Contenido del archivo en almacenamiento externo"

    //Almacenamiento Cache
    //val clave = "Clave"
    //val valor = "Mi valor de Cache"

    //Almacenamiento SQLite
    val databaseHelper = DatabaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Amacenamiento Interno
        //escribirDatosAlmacenamientoInterno(nombreArchivoAI, datosAI)

        //Almacenamiento Externo
        //escribirDatosAlmacenamientoExterno(nombreArchivoAE, datosAE)

        //Almacenamiento Cache
        //escribirDatosAlmacenamientoCache(this, clave, valor)

        //Almacenamiento SQLite
        databaseHelper.addContact("claudia", "4")
        databaseHelper.addContact("oscar", "5")
        databaseHelper.addContact("daniel", "3")
    }

    override fun onResume() {
        super.onResume()

        //Almacenamiento Interno
        //val contenido = leerDatosAlmacenamientoInterno(nombreArchivoAI)
        //Toast.makeText(this, contenido, Toast.LENGTH_LONG).show()

        //Almacenamiento Externo
        //val contenido = leerDatosAlmacenamientoExterno(nombreArchivoAE)
        //Toast.makeText(this, contenido, Toast.LENGTH_LONG).show()

        //Almacenamiento Cache
        //val contenido = leerDatosAlmacenamientoCache(this, clave)
        //Toast.makeText(this, contenido, Toast.LENGTH_LONG).show()

        //Almacenamiento SQLite
        val contenido = databaseHelper.getAllContacts()
        for (contact in contenido) {
            Toast.makeText(
                this,
                "ID: ${contact.id}, Name: ${contact.name}, Phone: ${contact.phone}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun escribirDatosAlmacenamientoInterno(nombreArchivo: String, datos: String) {
        val archivo = File(this.filesDir, nombreArchivo)
        archivo.writeText(datos)
    }

    fun leerDatosAlmacenamientoInterno(nombreArchivo: String):String {
        val archivo = File(this.filesDir, nombreArchivo)
        return archivo.readText()
    }

    private fun escribirDatosAlmacenamientoExterno(nombreArchivo: String, datos: String) {
        val estado = isExternalSrorageWritable()
        if (estado) {
            val directorio = getExternalFilesDir(null)
            val archivo = File(directorio, nombreArchivo)
            try {
                FileOutputStream(archivo).use {
                    it.write(datos.toByteArray())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

//    private fun leerDatosAlmacenamientoExterno(nombreArchivo: String): String {
//        val estado = isExternalStorageReadable()
//        if (estado) {
//            val directorio = getExternalFilesDir(null)
//            val archivo = File(directorio, nombreArchivoAE)
//            val fileInputStream = FileInputStream(archivo)
//            val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
//            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
//            val stringBuilder: StringBuilder = StringBuilder()
//            var text: String? = null
//            while ({ text = bufferedReader.readLine(); text }() != null) {
//                stringBuilder.append(text)
//            }
//            fileInputStream.close()
//            return stringBuilder.toString()
//        }
//        return ""
//    }

    private fun isExternalSrorageWritable(): Boolean{
        val estado = Environment.getExternalStorageState()
        return  Environment.MEDIA_MOUNTED == estado
    }

    private  fun isExternalStorageReadable():Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado || Environment.MEDIA_MOUNTED_READ_ONLY == estado
    }

    fun escribirDatosAlmacenamientoCache(context: Context, clave: String, valor: String){
        val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(clave, valor)
        editor.apply()
    }

    fun leerDatosAlmacenamientoCache(context: Context,clave: String): String?{
        val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
        return  sharedPreferences.getString(clave, null)
    }
}


