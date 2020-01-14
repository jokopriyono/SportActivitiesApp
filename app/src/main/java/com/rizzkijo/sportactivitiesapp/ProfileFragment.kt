package com.rizzkijo.sportactivitiesapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            customStylingPieChart(it)
            customStylingLineChart(it)
        }
    }

    private fun customStylingLineChart(context: Context) {
        val sampleLineDataSet = LineDataSet(DummyData.lineEntriesData(), "")
        sampleLineDataSet.setDrawIcons(false)
        sampleLineDataSet.enableDashedLine(10f, 5f, 0f)
        sampleLineDataSet.enableDashedHighlightLine(10f, 5f, 0f)
        sampleLineDataSet.color = Color.YELLOW
        sampleLineDataSet.setCircleColor(Color.WHITE)
        sampleLineDataSet.lineWidth = 1f
        sampleLineDataSet.circleRadius = 3f
        sampleLineDataSet.setDrawCircleHole(false)
        sampleLineDataSet.valueTextSize = 9f
        sampleLineDataSet.setDrawFilled(true)
        sampleLineDataSet.formLineWidth = 1f
        sampleLineDataSet.disableDashedLine()
        sampleLineDataSet.valueTextColor = Color.TRANSPARENT
        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.fade_white)
            sampleLineDataSet.fillDrawable = drawable
        } else {
            sampleLineDataSet.fillColor = Color.WHITE
        }

        val lineData = LineData(sampleLineDataSet)
        line_chart.data = lineData
        line_chart.xAxis.setDrawGridLines(false)
        line_chart.setTouchEnabled(false)
        line_chart.setPinchZoom(false)
        line_chart.description.isEnabled = false
        line_chart.xAxis.setDrawLabels(false)
        line_chart.axisRight.setDrawLabels(false)
        line_chart.axisLeft.textColor = Color.WHITE
        line_chart.axisLeft.typeface = ResourcesCompat.getFont(context, R.font.roboto)
    }

    private fun customStylingPieChart(context: Context) {
        val sampleDataSet = PieDataSet(DummyData.pieEntriesData(), "")
        val pieData = PieData(sampleDataSet)
        sampleDataSet.setColors(rgb("#0354b1"), rgb("#3f6571"), rgb("#ffaa01"), rgb("#ff3130"))
        sampleDataSet.setDrawValues(false)
        sampleDataSet.sliceSpace = 4f

        donut_chart.data = pieData
        donut_chart.animateXY(3000, 3000)
        donut_chart.setDrawEntryLabels(false)
        donut_chart.description.isEnabled = false
        donut_chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        donut_chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        donut_chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        donut_chart.legend.textSize = 14f
        donut_chart.legend.typeface = ResourcesCompat.getFont(context, R.font.roboto)
    }
}