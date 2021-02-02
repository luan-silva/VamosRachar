package com.example.vamosrachar

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener
{
    var value: Double = 0.0
    var numberOfPeople: Int = 0
    lateinit var peopleEditText: EditText
    lateinit var valueEditText: EditText
    lateinit var finalValueText: TextView
    lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        valueEditText = findViewById(R.id.inputTextValue)
        peopleEditText = findViewById(R.id.inputTextPeople)
        finalValueText = findViewById(R.id.finalValue)

        valueEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (valueEditText.text.toString() != "" &&
                    valueEditText.text.toString() != "0"
                ) {
                    value = valueEditText.text.toString().toDouble()
                }
                if (peopleEditText.text.toString() != "" &&
                    peopleEditText.text.toString() != "0" &&
                    valueEditText.text.toString() != "" &&
                    valueEditText.text.toString() != "0"
                ) {
                    value = valueEditText.text.toString().toDouble()
                    calculateFinalValue(value, numberOfPeople)
                }
            }
        })

        peopleEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (peopleEditText.text.toString() != "" &&
                    peopleEditText.text.toString() != "0"
                ) {
                    numberOfPeople = peopleEditText.text.toString().toInt()
                }
                if (valueEditText.text.toString() != "" &&
                    valueEditText.text.toString() != "0" &&
                    peopleEditText.text.toString() != "" &&
                    peopleEditText.text.toString() != "0"

                ) {
                    numberOfPeople = peopleEditText.text.toString().toInt()
                    calculateFinalValue(value, numberOfPeople)
                }
            }
        })

        shareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = finalValueText.text.toString()
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Quantia a pagar:")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Enviar para"))
        }
        tts = TextToSpeech(this, this)
        ttsButton.setOnClickListener {
            tts.language = Locale.getDefault()
            var valor = finalValueText.text.toString().split(',')
            tts.speak(valor[0] + " reais e" + valor[1] + " centavos", TextToSpeech.QUEUE_ADD, null, "")
        }
    }


    fun calculateFinalValue(value: Double, numberOfPeople: Int) {
        var finalValue = value / numberOfPeople
        finalValueText.text = String.format("%.2f", finalValue)

//            String.format(
//            "%s",
//            BigDecimal(finalValue).setScale(2).toString()
//        )
    }

    override fun onInit(status: Int) {
        if(status != TextToSpeech.ERROR) {
            tts.language = Locale.UK;
        }
    }

}