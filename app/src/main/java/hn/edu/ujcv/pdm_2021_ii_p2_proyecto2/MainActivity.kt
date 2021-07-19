package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var palabra: HashMap<Int,String> = hashMapOf()
    var numero=1
    var mMediaPlayer: MediaPlayer? = null
    var mMediaPlayer2: MediaPlayer? = null
    var palabrasNuevas = false;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializar()
        btnCrearPalabra.setOnClickListener { irCrear() }
        btnJugar.setOnClickListener { datos() }
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.sonidodokidoki)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        }
    }


    private fun inicializar(){
        var intent = intent
        try{
            palabra = intent.getSerializableExtra("palabrasnuevas") as HashMap<Int, String>
            palabrasNuevas = intent.getSerializableExtra("hayPalabraNueva") as Boolean
            return
        }catch (e:Exception){

        }
        palabra.put(numero++,"MICHAEL JACKSON|Cantante Estado Unidense, que múrio a sus 50 años")
        palabra.put(numero++,"MCDONALDS|Restaurante de comida rapida con 2 arcos amarillos")
        palabra.put(numero++,"MANGA|Lo dibuja el japonés, lo hace el ladrón y la tienes por duplicado en tu camisa y pantalón")
    }


    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun datos() {
        mMediaPlayer2 = MediaPlayer.create(this, R.raw.sonidoboton)
        mMediaPlayer2!!.start()
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("palabras",palabra)
        startActivity(intent)
    }

    private fun irCrear() {
        mMediaPlayer2 = MediaPlayer.create(this, R.raw.sonidoboton)
        mMediaPlayer2!!.start()
        val intent = Intent(this,AgregarPalabraActivity::class.java)
        intent.putExtra("palabra", palabra)
        startActivity(intent)
    }
}