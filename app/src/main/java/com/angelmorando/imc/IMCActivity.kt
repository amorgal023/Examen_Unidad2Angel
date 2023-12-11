package com.angelmorando.imc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class IMCActivity : AppCompatActivity() {
    private lateinit var cvHombre : CardView
    private lateinit var cvMujer : CardView
    private lateinit var tvAlturaNum : TextView
    private lateinit var tvEdadNum : TextView
    private lateinit var tvPesoNum : TextView
    private lateinit var rgSlider : RangeSlider
    private lateinit var butMinusPeso : AppCompatButton
    private lateinit var butPlusPeso : AppCompatButton
    private lateinit var butMinusEdad : AppCompatButton
    private lateinit var butPlusEdad : AppCompatButton
    private lateinit var butCalcular : AppCompatButton

    private var hombre = true
    private var peso = 0
    private var edad = 0
    private var altura = 0

    private var ALTURA_POR_DEFECTO = 130
    private val MINIMO_EDAD = 0
    private val MINIMO_PESO = 0
    private val MAXIMO_EDAD = 100
    private val MAXIMO_PESO = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imcactivity)
       initBotones()
        initFunctions()
        initVariables()

    }

    private fun initVariables(){
        altura = ALTURA_POR_DEFECTO
        peso = tvPesoNum.text.toString().toInt()
        edad = tvEdadNum.text.toString().toInt()
    }

    private fun initBotones(){
        cvHombre = findViewById<CardView>(R.id.cvHombre)
        cvMujer = findViewById<CardView>(R.id.cvMujer)
        tvAlturaNum = findViewById<TextView>(R.id.tvAlturaNum)
        tvEdadNum = findViewById<TextView>(R.id.tvEdadNum)
        tvPesoNum = findViewById<TextView>(R.id.tvPesoNum)
        rgSlider = findViewById<RangeSlider>(R.id.rgSlider)
        butMinusPeso = findViewById<AppCompatButton>(R.id.butMinusPeso)
        butPlusPeso = findViewById<AppCompatButton>(R.id.butPlusPeso)
        butMinusEdad = findViewById<AppCompatButton>(R.id.butMinusEdad)
        butPlusEdad = findViewById<AppCompatButton>(R.id.butPlusEdad)
        butCalcular = findViewById<AppCompatButton>(R.id.butCalcular)
    }

    private fun calculoIMC(){
        val intent = Intent(this, ResultActivity::class.java)
        var imc = peso.toDouble() / (altura * altura) * 10000
        Log.i("IMC", imc.toString())
        intent.putExtra("EXTRA_IMC",imc.toString())
        startActivity(intent)
    }

    private fun initFunctions(){
        cvHombre.setOnClickListener {
            hombrePulsado()
        }
        cvMujer.setOnClickListener{
            mujerPulsada()
        }
        rgSlider.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            altura = df.format(value).toDouble().toInt()
            tvAlturaNum.text = "$altura cm"
        }
        butMinusEdad.setOnClickListener{
            restarEdad()
        }
        butPlusEdad.setOnClickListener{
            sumarEdad()
        }
        butMinusPeso.setOnClickListener{
            restarPeso()
        }
        butPlusPeso.setOnClickListener{
            sumarPeso()
        }
        butCalcular.setOnClickListener{
            calculoIMC()
        }
    }

    private fun restarEdad(){
        if (edad > MINIMO_EDAD){
            edad--
            tvEdadNum.text = edad.toString()
        }
    }

    private fun sumarEdad(){
        if (edad < MAXIMO_EDAD){
            edad++
            tvEdadNum.text = edad.toString()
        }
    }

    private fun restarPeso(){
        if (peso > MINIMO_PESO){
            peso--
            tvPesoNum.text = peso.toString()
        }
    }

    private fun sumarPeso(){
        if (peso < MAXIMO_PESO){
            peso++
            tvPesoNum.text = peso.toString()
        }
    }
    private fun hombrePulsado() {
        cvHombre.setCardBackgroundColor(getColor(R.color.lightGrey))
        cvMujer.setCardBackgroundColor(getColor(R.color.Grey))
        hombre = true
    }
    private fun mujerPulsada() {
        cvHombre.setCardBackgroundColor(getColor(R.color.Grey))
        cvMujer.setCardBackgroundColor(getColor(R.color.lightGrey))
        hombre = false
    }
}