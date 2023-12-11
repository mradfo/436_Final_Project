package com.example.finalproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode

class AnswerActivity: AppCompatActivity() {
    private lateinit var yourText: TextView
    private lateinit var correctText: TextView
    private lateinit var resultText: TextView
    private lateinit var statsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        yourText = findViewById(R.id.your_text)
        correctText = findViewById(R.id.correct_text)
        resultText = findViewById(R.id.result_text)
        statsText = findViewById(R.id.stats_data)

        yourText.text = "Your answer is " + TimerActivity.answer.toString() + " Ω"
        correctText.text = "The correct answer is " + MainActivity.circuitCalculator.getTotalResistance().toString() + " Ω"
        if (MainActivity.circuitCalculator.checkAnswer(TimerActivity.answer)){
            resultText.text = "Your answer is correct!"
        } else {
            resultText.text = "Your answer is wrong. Try another problem"
        }

        statsText.text = "Your total score is " + ((MainActivity.circuitCalculator.getNumCorrect().toDouble() / MainActivity.circuitCalculator.getTotalAsked().toDouble()) * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() + "%"
    }

    fun restart(v : View) {
        finish()
        overridePendingTransition(R.anim.fade_out, 0)
    }

    fun sendEmail(v : View) {
        var email: String = findViewById<EditText?>(R.id.email_et).text.toString()
        var body: String = "Hi, \n Hope your studying is going well. You have answered " + MainActivity.circuitCalculator.getTotalAsked().toString() + " circuit questions and have gotten " + MainActivity.circuitCalculator.getNumCorrect().toString() + " correct. \n Best, \n Circuit Calculator"

        val intent = Intent(Intent.ACTION_SEND)
        intent.setData(Uri.parse("mailto:"))
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Circuit Calculator Report")
        intent.putExtra(Intent.EXTRA_TEXT, body)

        startActivity(Intent.createChooser(intent, "Sending email"))
        finish()
    }
}