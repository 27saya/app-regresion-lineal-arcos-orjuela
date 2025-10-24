package com.regresion.linear.model

import jakarta.validation.constraints.NotBlank

data class RegressionRequest(
    @field:NotBlank(message = "Los datos no pueden estar vacíos")
    val dataPoints: String = ""
)

