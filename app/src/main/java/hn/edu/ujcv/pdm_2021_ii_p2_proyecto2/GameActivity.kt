package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import android.media.MediaPlayer
import android.view.View


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
        btnRecargar.setOnClickListener { regresarMenu() }
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
    }



    private fun incializar() {
        var intent = intent
        palabra = intent.getSerializableExtra("palabras") as HashMap<Int, String>


    }


    private fun validar() {
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
            Toast.makeText(this, "Esta incorrecta la letra tiene [$intentos] intentos", Toast.LENGTH_SHORT).show()
        }


        txtIngresarLetra.setText("")
        txvPalabra.setText(guiones)


    }


    fun presentarPalabraEnGuiones(){
        var palabraJuego:String
        var pista:String
        var numeroRandom= (1..3).random()
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


