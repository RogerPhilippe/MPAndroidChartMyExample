package br.com.philippesis.mpandroidchart


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 */
class ChartFragment : Fragment() {

    private var mBarChart: BarChart? = null
    private var mPieChart: PieChart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_chart, container, false)

        this.mBarChart = root.findViewById(R.id.barChart) as BarChart
        this.mPieChart = root.findViewById(R.id.pieChart) as PieChart

        arguments?.getString("method")?.let { this.getGrowthChart(it) }

        return root
    }

    private fun getGrowthChart(method: String) {

        val json = "[{\"id\":\"1\", \"year\":\"2010\", \"growth_rate\":\"13.2\"},{\"id\":\"2\", \"year\":\"2011\", \"growth_rate\":\"15.7\"},{\"id\":\"3\", \"year\":\"2012\", \"growth_rate\":\"19.8\"},{\"id\":\"4\", \"year\":\"2013\", \"growth_rate\":\"25.7\"},{\"id\":\"5\", \"year\":\"2014\", \"growth_rate\":\"31.9\"},{\"id\":\"6\", \"year\":\"2015\", \"growth_rate\":\"35.7\"},{\"id\":\"7\", \"year\":\"2016\", \"growth_rate\":\"41.2\"},{\"id\":\"8\", \"year\":\"2017\", \"growth_rate\":\"55.7\"}]"


        val list = Gson().fromJson(json, Array<Growth>::class.java).asList()

        if (method == "bars") {

            val mutableList = mutableListOf<BarEntry>()

            list.forEach { item -> mutableList.add(BarEntry(item.year, item.growth_rate)) }

            val barDataSet = BarDataSet(mutableList, "Growth")
            barDataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

            val barData = BarData(barDataSet)
            barData.barWidth = 0.9f

            mBarChart?.visibility = View.VISIBLE
            mBarChart?.animateY(5000)
            mBarChart?.data = barData
            mBarChart?.setFitBars(true)

            val description = Description()
            description.text = "Descrição do Gráfico"
            mBarChart?.description = description
            mBarChart?.invalidate()

        }
        else if (method == "pie") {

            val mutableList = mutableListOf<PieEntry>()

            list.forEach { item -> mutableList.add(PieEntry(item.growth_rate, item.year.toString())) }

            val pieDataSet = PieDataSet(mutableList, "Growth")
            pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

            val pieData = PieData(pieDataSet)

            mPieChart?.visibility = View.VISIBLE
            mPieChart?.data = pieData

            val description = Description()
            description.text = "Descrição do Gráfico"
            mPieChart?.description = description
            mPieChart?.invalidate()

        }

    }


}
