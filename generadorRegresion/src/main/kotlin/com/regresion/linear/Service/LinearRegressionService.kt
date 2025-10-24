package com.regresion.linear.service

import com.regresion.linear.model.DataPoint
import com.regresion.linear.model.RegressionResult
import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
class LinearRegressionService {

    fun calculateLinearRegression(dataPoints: List<DataPoint>): RegressionResult {
        if (dataPoints.size < 2)
            throw IllegalArgumentException("Se necesitan al menos 2 puntos de datos")

        val n = dataPoints.size.toDouble()
        val sumX = dataPoints.sumOf { it.x }
        val sumY = dataPoints.sumOf { it.y }
        val sumXY = dataPoints.sumOf { it.x * it.y }
        val sumX2 = dataPoints.sumOf { it.x.pow(2) }

        val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
        val intercept = (sumY - slope * sumX) / n

        val yMean = sumY / n
        val ssTot = dataPoints.sumOf { (it.y - yMean).pow(2) }
        val ssRes = dataPoints.sumOf { (it.y - (slope * it.x + intercept)).pow(2) }
        val rSquared = 1 - (ssRes / ssTot)

        val minX = dataPoints.minOf { it.x }
        val maxX = dataPoints.maxOf { it.x }
        val regressionLine = listOf(
            DataPoint(minX, slope * minX + intercept),
            DataPoint(maxX, slope * maxX + intercept)
        )

        val equation = String.format("y = %.4fx + %.4f", slope, intercept)

        return RegressionResult(slope, intercept, equation, dataPoints, regressionLine, rSquared)
    }

    fun parseDataPoints(input: String): List<DataPoint> =
        input.trim()
            .split(";", "\n", "\r\n").filter { it.isNotBlank() }
            .map { line ->
                val parts = line.trim().split(",", " ", "\t").filter { it.isNotBlank() }
                if (parts.size != 2)
                    throw IllegalArgumentException("Formato inválido. Use: x,y o x y")
                try {
                    DataPoint(parts[0].toDouble(), parts[1].toDouble())
                } catch (_: NumberFormatException) {
                    throw IllegalArgumentException("Los valores deben ser números válidos")
                }
            }
}
