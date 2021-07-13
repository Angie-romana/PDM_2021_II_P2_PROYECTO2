package hn.edu.ujcv.pdm_2021_ii_p2_proyecto2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var palabra: HashMap<Int,String> = hashMapOf()
    var numero=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJugar.setOnClickListener { datos() }
    }

    private fun datos() {

       palabra.put(numero++,"BANGTANÜ| Mi grupo favorito :)")
        palabra.put(numero++,"JEON JUNGKOOK| la pista es que es jungkook lol")
        palabra.put(numero++,"MÚSICA|Es lo que se pone para bailar")
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("palabras",palabra)

        startActivity(intent)

    }
}