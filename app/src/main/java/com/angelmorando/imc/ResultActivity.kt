package com.angelmorando.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val resultado:String = intent.extras?.getString("EXTRA_IMC").orEmpty()
        tvResultado.text = resultado

    }
}