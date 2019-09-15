package br.com.philippesis.mpandroidchart


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import java.text.DecimalFormat

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

        val days = arrayOf("JAN", "FEV", "MAR", "ABR", "MAI", "JUN")

        val json = "[{\"id\":\"1\", \"year\":\"0\", \"growth_rate\":\"1320.20\"},{\"id\":\"2\", \"year\":\"1\", \"growth_rate\":\"1502.7\"},{\"id\":\"3\", \"year\":\"2\", \"growth_rate\":\"1109.8\"},{\"id\":\"4\", \"year\":\"3\", \"growth_rate\":\"2005.77\"},{\"id\":\"5\", \"year\":\"4\", \"growth_rate\":\"1221.99\"},{\"id\":\"6\", \"year\":\"5\", \"growth_rate\":\"1500.7\"}]"


        val list = Gson().fromJson(json, Array<Growth>::class.java).asList()

        if (method == "bars") {

            val mutableList = mutableListOf<BarEntry>()

            list.forEach { item -> mutableList.add(BarEntry(item.year, item.growth_rate)) }

            val barDataSet = BarDataSet(mutableList, "Meses")
            barDataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

            val barData = BarData(barDataSet)
            barData.barWidth = 0.9f

            mBarChart?.visibility = View.VISIBLE
            mBarChart?.animateY(2000)
            mBarChart?.data = barData
            mBarChart?.setFitBars(true)
            mBarChart?.setPinchZoom(false)
            mBarChart?.setDrawValueAboveBar(true)
            mBarChart?.setScaleEnabled(false)
            mBarChart?.setTouchEnabled(false)

            val description = Description()
            description.text = "Saídas 1º Semestre"
            mBarChart?.description = description
            mBarChart?.invalidate()
            mBarChart?.xAxis?.valueFormatter = MyXAxisFormatter(days)
            mBarChart?.axisLeft?.valueFormatter = MyYAxisFormatter()
            mBarChart?.axisRight?.isEnabled = false

        }
        else if (method == "pie") {

            val mutableList = mutableListOf<PieEntry>()

            list.forEach { item -> mutableList.add(PieEntry(item.growth_rate, days[item.year.toInt()])) }

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

    class MyXAxisFormatter(private val days: Array<String>) : ValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days[value.toInt()]
        }


    }

    class MyYAxisFormatter: ValueFormatter() {

        private val format = DecimalFormat("###,##0.00")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return "R$${format.format(value)}"
        }
    }



}
