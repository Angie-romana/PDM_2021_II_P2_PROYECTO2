package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_palabra.*

class AgregarPalabraActivity : AppCompatActivity() {
    var palabra: HashMap<Int, String> = hashMapOf()
    var numero = 0
    var palabrasNuevas = false;
    var mMediaPlayer2: MediaPlayer? = null
    var mMediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_palabra)
        inicializar()
//        llenarDatos()
        inicializarCreacion()
        btnAgregar.setOnClickListener { crearPalabra() }
        //btnEnviar.setOnClickListener { enviarDatos() }
        btnRegresar.setOnClickListener { regresar() }
        playSound(this)


    }
    fun playSound(view: AgregarPalabraActivity) {
        if (mMediaPlayer2 == null) {
            mMediaPlayer2 = MediaPlayer.create(this, R.raw.musicapalabra)
            // mMediaPlayer2!!.isLooping = true
            mMediaPlayer2!!.start()
        } else mMediaPlayer2!!.start()
    }
    fun pauseSound(view: AgregarPalabraActivity) {
        if (mMediaPlayer2 != null && mMediaPlayer2!!.isPlaying) mMediaPlayer2!!.pause()
    }
    fun stopSound(view: AgregarPalabraActivity) {
        if (mMediaPlayer2 != null) {
            mMediaPlayer2!!.stop()
            mMediaPlayer2!!.release()
            mMediaPlayer2 = null
        }
    }
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer2 != null) {
            mMediaPlayer2!!.release()
            mMediaPlayer2 = null
        }
    }

    private fun inicializar(){
        var intent = intent
        palabra = intent.getSerializableExtra("palabra") as HashMap<Int, String>
        numero = palabra.size
    }
    private fun inicializarCreacion() {
        btnAgregar.isEnabled = true
    }

    private fun crearPalabra() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.sonidoboton)
        mMediaPlayer!!.start()
        val dato = StringBuilder()
        if (!datosVacios()) {
            return
        }
        numero += 1
        dato.append(txtPalabra.text.toString().trim().uppercase()).append("|")
        dato.append(txtPista.text.toString().trim()).append("|")
        palabra.put(numero, dato.toString())
        btnAgregar.isEnabled = true
        if (datosVacios() == true) {
            Toast.makeText(this, "Se ha agregado exitosamente", Toast.LENGTH_SHORT).show()
            limpiar()
        }
        palabrasNuevas = true
    }

    private fun limpiar(){
        txtPalabra.setText("")
        txtPista.setText("")
    }

    private fun datosVacios(): Boolean {
        if (txtPalabra.text.toString().isEmpty()) {
            txtPalabra.error = "Debe ingresar la palabra"
            return false
        }
        if(txtPista.text.toString().isEmpty())
        {
            txtPista.error = "Debe ingresar la pista de la pista"
            return false
        }
        return true
    }
    private fun regresar() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.sonidoboton)
        mMediaPlayer!!.start()
        pauseSound(this)
        stopSound(this)
        onStop()
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("palabrasnuevas",palabra)
            intent.putExtra("hayPalabraNueva",palabrasNuevas)
            startActivity(intent)
    }

   private fun enviarDatos() {
        val intent = Intent(this,GameActivity::class.java)
        intent.putExtra("palabras",palabra)
        startActivity(intent)
    }

    private fun llenarDatos() {
        var intent = intent
        var palabras: String
        var pista : String
        for(valor in palabra)
        {
            val lista = valor.toString().split("|").toTypedArray()
            palabras = lista[1].toString()
            pista    = lista[2].toString()

        }
    }
}


