package br.com.philippesis.mpandroidchart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBarChart = findViewById<Button>(R.id.BtnBarChart)
        val btnPieChart = findViewById<Button>(R.id.BtnPieChart)

        btnBarChart.setOnClickListener { openBarChart(it) }
        btnPieChart.setOnClickListener { openPieChart(it) }


    }

    fun openBarChart(view: View) {
        startActivity(Intent(this,
            ChartActivity::class.java).putExtra("method", "bars"))
    }

    fun openPieChart(view: View) {
        startActivity(Intent(this,
            ChartActivity::class.java).putExtra("method", "pie"))
    }

}
