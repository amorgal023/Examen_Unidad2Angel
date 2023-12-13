package com.angelmorando.imc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.google.android.material.slider.RangeSlider

class ResultIMCActivity : AppCompatActivity() {
    private lateinit var tvGenero: TextView
    private lateinit var tvEdad: TextView
    private lateinit var tvAltura: TextView
    private lateinit var tvPeso: TextView
    private lateinit var tvPesoGrande: TextView
    private lateinit var tvResultado: TextView
    private lateinit var tvMensaje1: TextView
    private lateinit var btnResultado: AppCompatButton

    private var resultado = 0.0
    private var edad = ""
    private var pesoStr = ""
    private var pesoNum = 0;
    private var altura = ""
    private var genero = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultimc)
        initButtons()
        initFunctions()
        initVariables()


    }

    private fun initButtons() {
        tvGenero = findViewById<TextView>(R.id.tvGenero)
        tvEdad = findViewById<TextView>(R.id.tvEdad)
        tvAltura = findViewById<TextView>(R.id.tvAltura)
        tvPeso = findViewById<TextView>(R.id.tvPeso)
        tvPesoGrande = findViewById<TextView>(R.id.tvPesoGrande)
        tvMensaje1 = findViewById<TextView>(R.id.tvMensaje1)
        tvResultado = findViewById<TextView>(R.id.tvResultado)
        btnResultado = findViewById<AppCompatButton>(R.id.btnResultado)

    }

    private fun initFunctions() {
        resultado = intent.extras?.getDouble("EXTRA_IMC")!!
        genero = intent.extras?.getString("GENERO").orEmpty()
        altura = intent.extras?.getString("ALTURA").orEmpty()
        pesoStr = intent.extras?.getString("PESO").orEmpty()
        pesoNum = intent.extras?.getInt("PESO_NUM")!!
        edad = intent.extras?.getString("EDAD").orEmpty()

        tvAltura.text = altura
        tvPeso.text = pesoStr
        tvEdad.text = edad
        tvGenero.text = genero
        tvResultado.text = resultado.toString()

        mensajePeso();

    }

    private fun initVariables() {
        btnResultado.setOnClickListener {
            val intent = Intent(this, IMCActivity::class.java)
            startActivity(intent)
        }

    }

    private fun mensajePeso() {
        var mensaje = "";

        when {
            resultado >= 0 && resultado <= 18.5 -> {
                if (genero == "Hombre") {
                    mensaje = "Estas muy delgado \n. Tienes que ganar peso"
                    tvMensaje1.setTextColor(getColor(R.color.tr1Hombre))
                    tvResultado.setTextColor(getColor(R.color.tr1Hombre))
                    tvPeso.setTextColor(getColor(R.color.tr1Hombre))
                } else {
                    mensaje = "Estas muy delgada \n. Tienes que ganar peso"
                    tvMensaje1.setTextColor(getColor(R.color.tr1Mujer))
                    tvResultado.setTextColor(getColor(R.color.tr1Mujer))
                    tvPeso.setTextColor(getColor(R.color.tr1Mujer))
                }

                tvPesoGrande.text = "Peso bajo."

            }

            resultado > 18.5 && resultado < 25 -> {
                if (genero == "Hombre") {
                    mensaje = "Estas muy bien tío!! \n Sigue así!!"
                    tvMensaje1.setTextColor(getColor(R.color.tr2Hombre))
                    tvResultado.setTextColor(getColor(R.color.tr2Hombre))
                    tvPeso.setTextColor(getColor(R.color.tr2Hombre))
                } else {
                    mensaje = "Estas muy bien tía!! \n Sigue así!!"
                    tvMensaje1.setTextColor(getColor(R.color.tr2Mujer))
                    tvResultado.setTextColor(getColor(R.color.tr2Mujer))
                    tvPeso.setTextColor(getColor(R.color.tr2Mujer))
                }
                tvPesoGrande.text = "Peso normal"

            }

            resultado >= 25 && resultado <= 30 -> {
                if (genero == "Hombre") {
                    mensaje = "Estas rellenito \n Cuidate!!"
                    tvResultado.setTextColor(getColor(R.color.tr3Hombre))
                    tvPeso.setTextColor(getColor(R.color.tr3Hombre))
                    tvMensaje1.setTextColor(getColor(R.color.tr3Hombre))
                } else {
                    mensaje = "Estas muy bien tía!! \n Sigue así!!"
                    tvMensaje1.setTextColor(getColor(R.color.tr3Mujer))
                    tvResultado.setTextColor(getColor(R.color.tr3Mujer))
                    tvPeso.setTextColor(getColor(R.color.tr3Mujer))
                }

                tvPesoGrande.text = "Sobrepeso"
            }

            resultado >= 30 -> {
                if (genero == "Hombre") {
                    mensaje = "Estas gordito \n Tienes que adelgazar!!"
                    tvResultado.setTextColor(getColor(R.color.tr4Hombre))
                    tvPeso.setTextColor(getColor(R.color.tr4Hombre))
                    tvMensaje1.setTextColor(getColor(R.color.tr4Hombre))
                } else {
                    mensaje = "Estas gordita \n Tienes que adelgazar!!"
                    tvResultado.setTextColor(getColor(R.color.tr4Mujer))
                    tvPeso.setTextColor(getColor(R.color.tr4Mujer))
                    tvMensaje1.setTextColor(getColor(R.color.tr4Mujer))
                }

                tvPesoGrande.text = "Obesidad"
            }

        }

        tvMensaje1.text = mensaje;


    }
}
