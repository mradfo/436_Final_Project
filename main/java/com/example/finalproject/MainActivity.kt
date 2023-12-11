package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var circuit_types = arrayOf<String?>("Series", "Parallel")
    private lateinit var adView : AdView

    companion object {
        lateinit var circuitCalculator: CircuitCalculator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circuitCalculator = CircuitCalculator(this) // Pass context to CircuitCalculator

        var spinner: Spinner = findViewById<Spinner>(R.id.circuit_type)
        spinner.onItemSelectedListener = this

        var ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.spinner_layout, circuit_types)
        ad.setDropDownViewResource(R.layout.spinner_layout)

        spinner.adapter = ad

        createAd( )
    }

    fun startSolving(v: View) {
        var numResistorsText: EditText = findViewById<EditText>(R.id.num_resistors)
        circuitCalculator.setNumResistors(numResistorsText.text.toString().toInt())
        circuitCalculator.generateResistorValues()
        circuitCalculator.calculateTotalResistance()
        var intent: Intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, 0)
    }

    fun createAd( ) {
        adView = AdView( this )
        var adSize : AdSize = AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT )
        adView.setAdSize( adSize )

        var adUnitId : String = // "ca-app-pub-3940256099942544/1033173712"
            "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        var builder : AdRequest.Builder = AdRequest.Builder( )
        var request : AdRequest = builder.build()

        // add adView to linear layout
        var layout : LinearLayout = findViewById( R.id.ad_view )
        layout.addView( adView )

        // load the ad
        adView.loadAd( request )
    }

    override fun onDestroy() {
        if( adView != null )
            adView.destroy()
        super.onDestroy()
        circuitCalculator.savePersistentData()
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        circuitCalculator.setType(circuit_types[p2]!!)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}