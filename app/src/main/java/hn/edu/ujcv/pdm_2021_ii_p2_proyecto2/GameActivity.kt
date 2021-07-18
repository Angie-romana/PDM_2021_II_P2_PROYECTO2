package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import android.media.MediaPlayer
import androidx.appcompat.app.AlertDialog


class GameActivity : AppCompatActivity() {
    var palabra:HashMap<Int,String> = hashMapOf()
    var intentos=6
    var contarPalabra=0
    var palabraAdivinar=""
    var datosGuiones = ArrayList<String>()
    var datosLetras = ArrayList<String>()
    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        incializar()
        btnValidar.setOnClickListener { validar() }
        presentarPalabraEnGuiones()
        btnRecargar.setOnClickListener { recargar() }
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.musica1)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
       /* } else{ mMediaPlayer!!.start()

    }*/}



    }


    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun regresarMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        mMediaPlayer = MediaPlayer.create(this, R.raw.sonidodokidoki)
        mMediaPlayer!!.isLooping = true
        mMediaPlayer!!.start()
    }

    private fun volverAJugar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea volver a jugar?")
        builder.setMessage("¿Está seguro que desea volver al menú principal?")
        builder.setPositiveButton("Jugar de nuevo",{ dialogInterface: DialogInterface, i: Int ->
            recargar()
        })
        builder.setNegativeButton("Regresar al menu",{ dialogInterface: DialogInterface, i: Int ->
            regresarMenu()
        })
        builder.show()
    }

    private fun ganaste(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ganaste!")
        builder.setMessage("¿Está seguro que desea volver al menú principal?")
        builder.setPositiveButton("Jugar de nuevo",{ dialogInterface: DialogInterface, i: Int ->
            recargar()
        })
        builder.setNegativeButton("Regresar al menu",{ dialogInterface: DialogInterface, i: Int ->
            regresarMenu()
        })
        builder.show()
    }

    private fun recargar(){
        intentos=6
        btnValidar.isEnabled=true
        presentarPalabraEnGuiones()
        cambiarImagen(intentos)
    }

    private fun incializar() {
        var intent = intent
        palabra = intent.getSerializableExtra("palabras") as HashMap<Int, String>
    }

    private fun cambiarImagen(intento:Int){
        when (intento) {
            6 -> {
                fondogame.setBackgroundResource(R.drawable.fondo)
                txvImagenAhorcado.setBackgroundResource(R.drawable.intento0)
            }
            5 -> txvImagenAhorcado.setBackgroundResource(R.drawable.intento1)
            4 -> txvImagenAhorcado.setBackgroundResource(R.drawable.intento2)
            3 -> txvImagenAhorcado.setBackgroundResource(R.drawable.intento3)
            2 -> txvImagenAhorcado.setBackgroundResource(R.drawable.intento4)
            1 -> txvImagenAhorcado.setBackgroundResource(R.drawable.intento5)
            0 -> {
                fondogame.setBackgroundResource(R.drawable.fondo2)
                txvImagenAhorcado.setBackgroundResource(R.drawable.intento6)
                volverAJugar()
            }
        }

    }


    private fun validar() {

        if(txtIngresarLetra.text.isEmpty()){
            txtIngresarLetra.error = "No puede dejar en blanco"
            return
        }

        if (intentos==1){
            btnValidar.isEnabled=false
        }
        var guiones =""
        var contarincidencias=0
        for (i in 0..contarPalabra-1){

            var letra=datosLetras.get(i)
            var guionLetra=datosGuiones.get(i)
            var letraIngresada=txtIngresarLetra.text.toString().toUpperCase()

            if(letra.equals(letraIngresada)){
                datosGuiones.removeAt(i)
                datosGuiones.add(i,letraIngresada)
                contarincidencias++
                mMediaPlayer = MediaPlayer.create(this, R.raw.efecto1)
                mMediaPlayer!!.start()


            }
            guiones=guiones+datosGuiones.get(i)
        }
        if (contarincidencias==0){
            intentos=intentos-1
            cambiarImagen(intentos)
            Toast.makeText(this, "Esta incorrecta la letra tiene $intentos intentos", Toast.LENGTH_SHORT).show()
        }


        txtIngresarLetra.setText("")
        txvPalabra.setText(guiones)

        if(txvPalabra.text.toString().equals(palabraAdivinar)){
            ganaste()
        }

    }


    fun presentarPalabraEnGuiones(){
        var palabraJuego:String
        var pista:String
        var numeroRandom= (1..(palabra.size)).random()
        var guiones=""
        var letrasSeparadas=""
        datosGuiones = ArrayList<String>()
        datosLetras = ArrayList<String>()




       for (palabras in palabra){
            var lista= palabras.toString().split("|","=")
            palabraJuego=lista[1]
            pista=lista[2]

           var datosDelHash=palabra.get(numeroRandom)


           if (datosDelHash != null) {
               if(datosDelHash.contains(palabraJuego)){
                    contarPalabra= palabraJuego.length
                   palabraAdivinar=palabraJuego

            for(i in 0..contarPalabra-1){
                var letra=palabraJuego.get(i).toString()
                datosLetras.add(i,letra)
                datosGuiones.add(i,"-")

              if(letra.equals(" ")){
                  datosGuiones.add(i," ")

                }

            }
                   for(i in 0..contarPalabra-1){
                      guiones=guiones+datosGuiones.get(i)
                       letrasSeparadas=letrasSeparadas+datosLetras.get(i)

                   }
                   txvPalabra.setText(guiones)
                   txvPista.setText("PISTA: "+pista)
               }
           }


        }
    }
}


