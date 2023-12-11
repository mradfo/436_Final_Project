package com.example.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.math.RoundingMode
import kotlin.math.round
import kotlin.random.Random

class CircuitCalculator {
    private var numResistors: Int = 2
    private lateinit var type: String
    private var resistorValues: Array<Double> = emptyArray()
    private var totalResistance: Double = 0.0
    private var numCorrect: Int = 0
    private var totalAsked: Int = 0

    private val sharedPreferences: SharedPreferences

    companion object {
        private const val NUM_CORRECT_KEY = "numCorrect"
        private const val TOTAL_ASKED_KEY = "totalAsked"
    }

    constructor(context : Context){
        numResistors = 2
        type = "Series"
        sharedPreferences = context.getSharedPreferences("CircuitCalculatorPrefs", Context.MODE_PRIVATE)
        numCorrect = sharedPreferences.getInt(NUM_CORRECT_KEY, 0)
        totalAsked = sharedPreferences.getInt(TOTAL_ASKED_KEY, 0)

    }

    fun savePersistentData() {
        with(sharedPreferences.edit()) {
            putInt(NUM_CORRECT_KEY, numCorrect)
            putInt(TOTAL_ASKED_KEY, totalAsked)
            apply()
        }
    }

    fun getNumCorrect(): Int {
        return numCorrect
    }

    fun getTotalAsked() : Int {
        return totalAsked
    }

    fun getTotalResistance() : Double {
        return totalResistance
    }

    fun getNumResistors() : Int {
        return numResistors
    }

    fun setNumResistors(numResistors : Int){
        if (numResistors >= 1){
            this.numResistors = numResistors
        }
    }

    fun getType() : String {
        return type
    }

    fun setType(type : String) {
        if (type == "Series" || type == "Parallel"){
            this.type = type
        }
    }

    fun getResistorValues() : Array<Double> {
        return resistorValues
    }

    fun generateResistorValues(){
        var rand: Random = Random
        resistorValues = emptyArray()
        for (i in 1..numResistors){
            var num: Double = rand.nextDouble(0.0, 500.0)
            resistorValues += num.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        }
    }

    fun calculateTotalResistance() : Double {
        var totalResistance : Double = 0.0
        if (type == "Series") {
            for (i in 0 .. numResistors - 1){
                totalResistance += resistorValues[i]
            }
        } else if (type == "Parallel") {
            for (i in 0 .. numResistors - 1){
                totalResistance += 1 / resistorValues[i]
            }
            totalResistance = 1/totalResistance
        }
        // round to 2 decimal places
        this.totalResistance = totalResistance.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        return totalResistance.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    fun checkAnswer(answer: Double): Boolean {
        var roundedAnswer: Double = answer.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        if (roundedAnswer == totalResistance) {
            numCorrect++
            totalAsked++
            savePersistentData()
            return true
        } else {
            totalAsked++
            savePersistentData()
            return false
        }
    }
}