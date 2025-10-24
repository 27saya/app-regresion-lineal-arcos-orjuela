package com.regresion.linear.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegressionResult(
    val slope: Double,
    val intercept: Double,
    val equation: String,
    val dataPoints: List<DataPoint>,
    val regressionLine: List<DataPoint>,
    @JsonProperty("rSquared")
    val rSquared: Double
)
