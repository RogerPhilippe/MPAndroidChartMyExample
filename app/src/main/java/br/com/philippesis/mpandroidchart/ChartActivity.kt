package br.com.philippesis.mpandroidchart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val method = intent.getStringExtra("method")
        val chartFragment = ChartFragment()
        val bundle = Bundle()
        bundle.putString("method", method)
        chartFragment.arguments = bundle

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, chartFragment).commit()

    }
}
