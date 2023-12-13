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
    private lateinit var tvComplexion: TextView
    private lateinit var rgSlider : RangeSlider
    private lateinit var rgSliderComplexion : RangeSlider
    private lateinit var butMinusPeso : AppCompatButton
    private lateinit var butPlusPeso : AppCompatButton
    private lateinit var butMinusEdad : AppCompatButton
    private lateinit var butPlusEdad : AppCompatButton
    private lateinit var butCalcular : AppCompatButton

    private var ALTURA_POR_DEFECTO = 160
    private val MINIMO_EDAD = 0
    private val MINIMO_PESO = 0
    private val MAXIMO_EDAD = 100
    private val MAXIMO_PESO = 200
    private val COMPLEXION_MINIMO = 0
    private val COMPLEXION_DELGADO = 1
    private val COMPLEXION_NORMAL = 2
    private val COMPLEXION_ATLETICO = 3
    private val COMPLEXION_CORPULENTO = 4
    private val FACTORGENERO_HOMBRE = 0.9
    private val FACTORGENERO_MUJER = 1.0
    private val TX_DELGADO = "DELGADO"
    private val TX_NORMAL = "NORMAL"
    private val TX_ATLETICO = "ATLETICO"
    private val TX_CORPULENO = "CORCUPLENTO"
    private val FACTORCORP_DELG = 1.03;
    private val FACTORCORP_NORM = 1.00;
    private val FACTORCORP_ATLE = 0.97;
    private val FACTORCORP_CORP = 0.94;
    private val STR_HOMBRE = "Hombre";
    private val STR_MUJER = "Mujer";


    private var hombre = true
    private var peso = 0
    private var edad = 0
    private var altura = 0
    private var factorGenero = 0.0
    private var factorEdad = 0.0
    private var factorCorpulencia = 0.0
    private var complexionValor = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imcactivity)
        initButtons()
        initFunctions()
        initVariables()

    }

    private fun initVariables(){
        altura = ALTURA_POR_DEFECTO
        peso = tvPesoNum.text.toString().toInt()
        edad = tvEdadNum.text.toString().toInt()
    }

    private fun initButtons(){
        cvHombre = findViewById<CardView>(R.id.cvHombre)
        cvMujer = findViewById<CardView>(R.id.cvMujer)
        tvAlturaNum = findViewById<TextView>(R.id.tvAlturaNum)
        tvAlturaNum.text = "$ALTURA_POR_DEFECTO cm."
        tvEdadNum = findViewById<TextView>(R.id.tvEdadNum)
        tvPesoNum = findViewById<TextView>(R.id.tvPesoNum)
        tvComplexion = findViewById<TextView>(R.id.tvComplexion)
        tvComplexion.text = TX_DELGADO
        rgSlider = findViewById<RangeSlider>(R.id.rgSlider)
        rgSliderComplexion = findViewById<RangeSlider>(R.id.rgSliderComplexion)
        butMinusPeso = findViewById<AppCompatButton>(R.id.butMinusPeso)
        butPlusPeso = findViewById<AppCompatButton>(R.id.butPlusPeso)
        butMinusEdad = findViewById<AppCompatButton>(R.id.butMinusEdad)
        butPlusEdad = findViewById<AppCompatButton>(R.id.butPlusEdad)
        butCalcular = findViewById<AppCompatButton>(R.id.butCalcular)
    }

    private fun calculoFactorGenero(){
        if (hombre) {
            factorGenero = FACTORGENERO_HOMBRE
        } else {
            factorGenero = FACTORGENERO_MUJER
        }
    }

    private fun calculoFactorEdad(){
        when {
            edad in 0..15 -> factorEdad = 1.1
            edad in 16 .. 30 -> factorEdad = 1.0
            edad in 31..60 -> factorEdad = 0.95
            edad > 60 -> factorEdad = 1.05
        }
    }

    private fun calculoFactorCorpulento(){
        when {
            (complexionValor >= COMPLEXION_MINIMO) && (complexionValor <= COMPLEXION_DELGADO) -> factorCorpulencia = FACTORCORP_DELG
            (complexionValor > COMPLEXION_DELGADO) && (complexionValor <= COMPLEXION_NORMAL) -> factorCorpulencia = FACTORCORP_NORM
            (complexionValor > COMPLEXION_NORMAL) && (complexionValor <= COMPLEXION_ATLETICO) -> factorCorpulencia = FACTORCORP_ATLE
            (complexionValor > COMPLEXION_ATLETICO) && (complexionValor <= COMPLEXION_CORPULENTO) -> factorCorpulencia = FACTORCORP_CORP
        }
    }
    private fun calculoIMC(){
        val intent = Intent(this, ResultIMCActivity::class.java)
        var alturametro = altura / 100
        var imc = 0.0;
        val df = DecimalFormat("#.##")

        calculoFactorGenero()
        calculoFactorEdad()
        calculoFactorCorpulento()

        imc = (peso.toDouble() / (alturametro*alturametro)) * factorGenero * factorCorpulencia * factorEdad
        intent.putExtra("EXTRA_IMC", imc)
        if (hombre){
            intent.putExtra("GENERO",STR_HOMBRE)
        } else {
            intent.putExtra("GENERO", STR_MUJER)
        }

        intent.putExtra("EDAD","$edad años")
        intent.putExtra("ALTURA","$altura cm")
        intent.putExtra("PESO_NUM",peso)
        intent.putExtra("PESO","$peso kg")

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
           // val df = DecimalFormat("#.##")
           // altura = df.format(value).toDouble().toInt()
            altura = value.toInt();
            tvAlturaNum.text = "$altura cm"
        }

        rgSliderComplexion.addOnChangeListener { _, value, _ ->
            complexionValor = value.toDouble();
            when {
                (complexionValor >= COMPLEXION_MINIMO) && (complexionValor <= COMPLEXION_DELGADO) -> tvComplexion.text =
                    TX_DELGADO
                (complexionValor > COMPLEXION_DELGADO) && (complexionValor <= COMPLEXION_NORMAL) -> tvComplexion.text =
                    TX_NORMAL
                (complexionValor > COMPLEXION_NORMAL) && (complexionValor <= COMPLEXION_ATLETICO) -> tvComplexion.text =
                    TX_ATLETICO
                (complexionValor > COMPLEXION_ATLETICO) && (complexionValor <= COMPLEXION_CORPULENTO) -> tvComplexion.text =
                    TX_CORPULENO
            }
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
            if (altura!=0){
                calculoIMC()
            }
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