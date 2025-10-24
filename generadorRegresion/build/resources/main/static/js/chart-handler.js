let chart = null;

document.getElementById("regressionForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const dataPoints = document.getElementById("dataPoints").value.trim();

    if (!dataPoints) {
        showError("Por favor ingrese puntos de datos.");
        return;
    }

    clearError();

    const formData = new FormData();
    formData.append("dataPoints", dataPoints);

    try {
        const response = await fetch("/calculate", {
            method: "POST",
            body: formData
        });

        const data = await response.json();

        if (data.success) {
            displayResults(data.result);
        } else {
            showError(data.error);
        }
    } catch (err) {
        showError("Error al conectarse con el servidor.");
    }
});

document.getElementById("clearBtn").addEventListener("click", () => {
    document.getElementById("dataPoints").value = "";
    document.getElementById("resultsSection").classList.add("hidden");
    clearError();

    if (chart) {
        chart.destroy();
        chart = null;
    }
});

document.getElementById("loadExampleBtn").addEventListener("click", () => {
    const example = "1,2\n2,4\n3,6\n4,8\n5,10";
    document.getElementById("dataPoints").value = example;
    clearError();
});

function displayResults(result) {
    document.getElementById("resultsSection").classList.remove("hidden");

    document.getElementById("equation").textContent = result.equation;
    document.getElementById("slope").textContent = result.slope.toFixed(4);
    document.getElementById("intercept").textContent = result.intercept.toFixed(4);
    document.getElementById("rSquared").textContent = result.rsquared.toFixed(4);

    const interpretation = generateInterpretation(result);
    document.getElementById("interpretation").textContent = interpretation;

    drawChart(result);
}

function drawChart(result) {
    const ctx = document.getElementById("regressionChart").getContext("2d");

    if (chart) {
        chart.destroy();
    }
    console.log("Dibujando gráfico:", result.dataPoints, result.regressionLine);

    chart = new Chart(ctx, {
        type: "scatter",
        data: {
            datasets: [
                {
                    label: "Puntos de datos",
                    data: result.dataPoints,
                    backgroundColor: "rgba(255,255,255,0.8)",
                    borderColor: "#fff"
                },
                {
                    label: "Línea de regresión",
                    data: result.regressionLine,
                    type: "line",
                    borderColor: "rgba(200,200,200,1)",
                    borderWidth: 2,
                    fill: false
                }
            ]
        },
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: "X",
                        color: "#ccc"
                    },
                    ticks: { color: "#999" }
                },
                y: {
                    title: {
                        display: true,
                        text: "Y",
                        color: "#ccc"
                    },
                    ticks: { color: "#999" }
                }
            },
            plugins: {
                legend: {
                    labels: { color: "#fff" }
                }
            }
        }
    });
}

function generateInterpretation(result) {
    const { slope, rsquared } = result;
    const trend = slope > 0 ? "creciente" : "decreciente";
    const quality = rsquared >= 0.9 ? "muy fuerte" : rsquared >= 0.7 ? "moderada" : "débil";
    return `La relación es ${trend} y la calidad del ajuste es ${quality} (R²=${rsquared.toFixed(4)}).`;
}

function showError(msg) {
    document.getElementById("errorMessage").textContent = msg;
}

function clearError() {
    document.getElementById("errorMessage").textContent = "";
}

