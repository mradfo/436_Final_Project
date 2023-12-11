package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.AnalogClock


class TimerActivity: AppCompatActivity() {
    private lateinit var enterAnswer : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var gl: GridLayout = GridLayout(this)
        setContentView(gl)

        for (i in 0 .. MainActivity.circuitCalculator.getNumResistors() - 1) {
            var tv: TextView = TextView(this)
            tv.text = "R" + i.toString() + ":"
            tv.gravity = Gravity.START
            tv.textSize = 25.0f
            tv.setPadding(10,10,10,10)
            var rowSpec: GridLayout.Spec = GridLayout.spec(i)
            var colSpec: GridLayout.Spec = GridLayout.spec(0)
            var spec: GridLayout.LayoutParams = GridLayout.LayoutParams(rowSpec, colSpec)
            tv.layoutParams = spec

            gl.addView(tv)

            var tv2: TextView = TextView(this)
            tv2.text =  MainActivity.circuitCalculator.getResistorValues()[i].toString() + " Ω"
            tv2.gravity = Gravity.START
            tv2.textSize = 25.0f
            tv2.setPadding(10,10,10,10)
            rowSpec = GridLayout.spec(i)
            colSpec = GridLayout.spec(1)
            spec = GridLayout.LayoutParams(rowSpec, colSpec)
            tv2.layoutParams = spec

            gl.addView(tv2)
        }

        var resultText: TextView = TextView(this)
        resultText.text = "Answer: "
        resultText.textSize = 25.0f
        resultText.setPadding(10,10,10,10)
        var rowSpec: GridLayout.Spec = GridLayout.spec(MainActivity.circuitCalculator.getNumResistors())
        var colSpec: GridLayout.Spec = GridLayout.spec(0)
        var spec: GridLayout.LayoutParams = GridLayout.LayoutParams(rowSpec, colSpec)
        resultText.layoutParams = spec
        gl.addView(resultText)

        enterAnswer = EditText(this)
        enterAnswer.textSize = 25.0f
        enterAnswer.setPadding(10,10,10,10)
        enterAnswer.hint = "Enter answer in Ohms"
        rowSpec = GridLayout.spec(MainActivity.circuitCalculator.getNumResistors())
        colSpec = GridLayout.spec(1)
        spec = GridLayout.LayoutParams(rowSpec, colSpec)
        enterAnswer.layoutParams = spec
        gl.addView(enterAnswer)

        // Analog clock
        val analogClock = AnalogClock(this)
        rowSpec = GridLayout.spec(MainActivity.circuitCalculator.getNumResistors() + 1)
        colSpec = GridLayout.spec(0, 2) // Span 2 columns for full width
        spec = GridLayout.LayoutParams(rowSpec, colSpec)
        analogClock.layoutParams = spec
        gl.addView(analogClock)


        var backButton: Button = Button(this)
        backButton.text = "Go back"
        backButton.textSize = 25f
        backButton.id = BACK_BUTTON
        backButton.setPadding(10,10,10,10)
        rowSpec = GridLayout.spec(MainActivity.circuitCalculator.getNumResistors() + 2, GridLayout.BOTTOM)
        colSpec = GridLayout.spec(0)
        spec = GridLayout.LayoutParams(rowSpec, colSpec)
        backButton.layoutParams = spec
        var onClickListener : OnClickListener = onClickHandler()
        backButton.setOnClickListener(onClickListener)
        gl.addView(backButton)

        var forwardButton: Button = Button(this)
        forwardButton.text = "See solution"
        forwardButton.textSize = 25f
        forwardButton.id = FORWARD_BUTTON
        forwardButton.setPadding(10,10,10,10)
        rowSpec = GridLayout.spec(MainActivity.circuitCalculator.getNumResistors() + 2, GridLayout.BOTTOM)
        colSpec = GridLayout.spec(1)
        spec = GridLayout.LayoutParams(rowSpec, colSpec)
        forwardButton.layoutParams = spec
        forwardButton.setOnClickListener(onClickListener)
        gl.addView(forwardButton)
    }


    inner class onClickHandler: OnClickListener {
        override fun onClick(v: View?) {
            if (v!!.id == BACK_BUTTON) {
                finish()
                overridePendingTransition(R.anim.fade_out, 0)
            } else if (v!!.id == FORWARD_BUTTON){
                var intent : Intent = Intent(this@TimerActivity, AnswerActivity::class.java)
                if (!enterAnswer.text.toString().isEmpty()) {
                    answer = enterAnswer.text.toString().toDouble()
                }
                finish()
                overridePendingTransition(R.anim.fade_in, 0)
                startActivity(intent)
            }
        }


    }
    // functions for switching views on button clicks go here

    companion object {
        var BACK_BUTTON : Int = 1
        var FORWARD_BUTTON : Int = 2
        var answer : Double = 0.0
    }
}