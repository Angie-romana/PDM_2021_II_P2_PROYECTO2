package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {
    var palabra:HashMap<Int,String> = hashMapOf()
    var numero=0
    var intentos=0
    var palabraAdivinarGuiones=""
    var contarPalabra=0
    var palabraAdivinar=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        incializar()
        btnValidar.setOnClickListener { validar() }
        presentarPalabra()
        btnRecargar.setOnClickListener { presentarPalabra() }

    }

    private fun incializar() {
        var intent = intent
        palabra = intent.getSerializableExtra("palabras") as HashMap<Int, String>


    }


    private fun validar() {
        if (intentos==6){
            btnValidar.isEnabled=false
        }
        var guiones =""


        for (i in 0..contarPalabra-1){
            var letra=palabraAdivinar.get(i).toString()
            if (txtIngresarLetra.text.toString().equals(letra)){
                var letraTxt= txtIngresarLetra.text.toString()

             // guiones=guiones+txtIngresarLetra.text.toString()
                //palabraAdivinarGuiones = palabraAdivinarGuiones + letraTxt


            }else {
                guiones=guiones+"-"
                  // palabraAdivinarGuiones = palabraAdivinarGuiones + "-"
               // intentos=intentos-1
              //  Toast.makeText(this,"La letra es ERRONEA solo le quedan $intentos intentos",Toast.LENGTH_LONG).show()

            }


        }
        txvPalabra.setText(guiones)


    }


    fun presentarPalabra(){
        var palabraJuego:String
        var pista:String
        var numeroRandom= (1..3).random()
        var guiones=""

      //  txvPalabra.setText(numeroRandom.toString())


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

              if(letra.equals(" ")){
                    guiones=guiones+" "
                }else {
                    guiones = guiones + "-"
                }
            }

                   txvPalabra.setText(guiones)
                   txvPista.setText("PISTA: "+pista)
               }
           }


        }
    }
}


