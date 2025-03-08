package com.example.opendatajabar.ui

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.common.utils.DataUtils.getGroupBarChartData
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.opendatajabar.viewmodel.HealthViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.GroupBar
import com.example.opendatajabar.data.HealthEntity
import kotlin.math.absoluteValue

@Composable
fun DataScreen(
    navController: NavHostController,
    viewModel: HealthViewModel = hiltViewModel()
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabTitles = listOf("📈 Line", "🥧 Pie", "📊 Bar")

    val healthData = viewModel.healthList.collectAsLazyPagingItems()
    val items = healthData.itemSnapshotList.items

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xFFEEEEEE),
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color.Gray,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // Animasi transisi saat ganti chart
        Crossfade(targetState = selectedTabIndex, label = "ChartTransition") { tabIndex ->
            when (tabIndex) {
                0 -> LineChartScreen(viewModel)
                1 -> PieChartScreen(viewModel)
                2 -> BarChartScreen(viewModel)
            }
        }
    }
}

@Composable
fun LineChartScreen(viewModel: HealthViewModel) {
    val healthData = viewModel.healthList.collectAsLazyPagingItems()
    val steps = 5

    if (healthData.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Menampilkan loading jika data kosong
        }
        return
    }

    val pointsData = healthData.itemSnapshotList.items.mapIndexed { index, healthEntity ->
        Point(index.toFloat(), healthEntity.jumlahPenderita.toFloat())
    }

    val cityLabels = healthData.itemSnapshotList.items.map { it.namaKabupatenKota }

    val xAxisData = AxisData.Builder()
        .axisStepSize(200.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i ->
            cityLabels.getOrNull(i)?.replace(" ", "\n") ?: ""
        } // Ganti spasi dengan newline agar wrap
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()


    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 250000 / steps
            (i * yScale).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false),
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.tertiary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = MaterialTheme.colorScheme.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun PieChartScreen(viewModel: HealthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val healthData = viewModel.healthList.collectAsLazyPagingItems()
    val healthList = healthData.itemSnapshotList.items

    if (healthList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val total = healthList.sumOf { it.jumlahPenderita.toDouble() }.toFloat()
    if (total == 0f) return

    val pieSlices = healthList.map { healthEntity ->
        PieChartData.Slice(
            label = healthEntity.namaKabupatenKota,
            value = healthEntity.jumlahPenderita.toFloat(), // FIX: Gunakan jumlah langsung, bukan persen
            color = Color(0xFF58BDFF + healthEntity.namaKabupatenKota.hashCode() % 0xFFFFFF) // FIX: Warna lebih aman
        )
    }

    val pieChartData = PieChartData(
        slices = pieSlices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        strokeWidth = 40f, // FIX: Kurangi stroke width
        labelColor = Color.Black,
        activeSliceAlpha = .9f,
        isEllipsizeEnabled = true,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.MIDDLE,
        labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
        isAnimationEnable = true,
        showSliceLabels = true,
        chartPadding = 25,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Legends(
            legendsConfig = DataUtils.getLegendsConfigFromPieChartData(
                pieChartData,
                3
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        PieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            pieChartData,
            pieChartConfig
        ) { slice ->
            Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun BarChartScreen(viewModel: HealthViewModel) {
    val healthData = viewModel.healthList.collectAsLazyPagingItems()

    if (healthData.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Mengelompokkan data berdasarkan tahun
    val groupedData = healthData.itemSnapshotList.items
        .groupBy { it.tahun }
        .mapValues { (_, list) -> list.sumOf { it.jumlahPenderita } }
        .toSortedMap() // Urutkan berdasarkan tahun

    val barSize = groupedData.size
    val colorPaletteList = DataUtils.getColorPaletteList(barSize)

    val groupBarList: List<GroupBar> = groupedData.entries.toList().mapIndexed { index, (tahun, jumlah) ->
        GroupBar(
            barList = listOf(
                BarData(
                    point = Point(index.toFloat(), jumlah.toFloat()),
                    color = colorPaletteList.getOrElse(index) { Color.Gray }, // Ambil warna unik per tahun
                    gradientColorList = listOf(
                        colorPaletteList.getOrElse(index) { Color.Gray },
                        colorPaletteList.getOrElse((index + 1) % colorPaletteList.size) { Color.Gray }
                    ),
                )
            ),
            label = tahun.toString()
        )
    }


    val groupBarPlotData = BarPlotData(
        groupBarList = groupBarList,
        barColorPaletteList = colorPaletteList
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(groupBarList.size - 1)
        .bottomPadding(10.dp)
        .labelData { index -> groupedData.keys.elementAtOrNull(index)?.toString() ?: "" }
        .build()

    val maxPenderita = groupBarList.maxOfOrNull { it.barList.first().point.y } ?: 0f
    val yStepSize = 5

    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index ->
            val scale = (maxPenderita / yStepSize).toInt()
            (index * scale).toString()
        }
        .build()


    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )

    Column {
        GroupBarChart(
            modifier = Modifier
                .height(400.dp),
            groupBarChartData = groupBarChartData
        )
    }
}
