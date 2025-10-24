package com.regresion.linear.controller

import com.regresion.linear.model.RegressionRequest
import com.regresion.linear.service.LinearRegressionService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
class RegressionController(
    private val regressionService: LinearRegressionService
) {

    @GetMapping("/")
    fun showForm(model: Model): String {
        model.addAttribute("regressionRequest", RegressionRequest())
        return "index"
    }

    @PostMapping("/calculate")
    @ResponseBody
    fun calculateRegression(
        @Valid @ModelAttribute regressionRequest: RegressionRequest,
        bindingResult: BindingResult
    ): Map<String, Any> {

        if (bindingResult.hasErrors()) {
            return mapOf("success" to false, "error" to "Por favor ingrese datos válidos")
        }

        return try {
            val points = regressionService.parseDataPoints(regressionRequest.dataPoints)
            if (points.size < 2)
                return mapOf("success" to false, "error" to "Se necesitan al menos 2 puntos de datos")
            val result = regressionService.calculateLinearRegression(points)
            mapOf("success" to true, "result" to result)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to (e.message ?: "Error al calcular la regresión"))
        }
    }
}
