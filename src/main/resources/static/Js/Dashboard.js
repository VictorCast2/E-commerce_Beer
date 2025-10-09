document.addEventListener("DOMContentLoaded", () => {

    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.content__header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

    //Menú desplegable del perfil 
    const subMenu = document.getElementById("SubMenu");
    const profileImage = document.getElementById("user__admin");

    if (subMenu && profileImage) {
        profileImage.addEventListener("click", function (e) {
            e.stopPropagation(); // Evita que el click cierre el menú inmediatamente
            subMenu.classList.toggle("open__menu");
        });

        // Cerrar menú al hacer clic fuera
        document.addEventListener("click", function (e) {
            if (!subMenu.contains(e.target) && !profileImage.contains(e.target)) {
                subMenu.classList.remove("open__menu");
            }
        });
    }

    //abrir la notificaciones del admin
    const notifIcon = document.getElementById("notifIcon");
    const notifMenu = document.getElementById("notifMenu");

    notifIcon.addEventListener("click", (e) => {
        e.stopPropagation();
        notifMenu.classList.toggle("open");
    });

    // cerrar al hacer clic fuera
    document.addEventListener("click", (e) => {
        if (!notifMenu.contains(e.target) && !notifIcon.contains(e.target)) {
            notifMenu.classList.remove("open");
        }
    });

    //incrementos en los datos
    function formatNumberCustom(num, hasDollar = false) {
        let str = num.toString();

        if (str.length > 6) {
            // Si es millón o más: coma en millón, punto en miles
            let millones = str.slice(0, str.length - 6);
            let miles = str.slice(str.length - 6, str.length - 3);
            let cientos = str.slice(str.length - 3);
            return (hasDollar ? "$" : "") + millones + "," + miles + "." + cientos;
        } else {
            // Menor al millón: coma normal
            return (hasDollar ? "$" : "") + parseInt(num).toLocaleString("en-US");
        }
    }

    function animateCounter(element, start, end, duration, hasDollar = false, suffix = "") {
        let startTimestamp = null;
        const step = (timestamp) => {
            if (!startTimestamp) startTimestamp = timestamp;
            const progress = Math.min((timestamp - startTimestamp) / duration, 1);

            // 🔹 Asegurar que siempre es un entero creciente
            const value = Math.floor(progress * (end - start) + start);

            element.textContent = formatNumberCustom(value, hasDollar) + suffix;

            if (progress < 1) {
                requestAnimationFrame(step);
            }
        };
        requestAnimationFrame(step);
    }

    // Animar todos los .ingresos__total
    document.querySelectorAll(".ingresos__total").forEach((el) => {
        const finalValue = parseInt(el.textContent.replace(/[^0-9]/g, ""));
        const hasDollar = el.textContent.includes("$");
        animateCounter(el, 0, finalValue, 2000, hasDollar);
    });

    // Animar los <strong> (30+ , 35+ etc.)
    document.querySelectorAll(".ingresos__text strong").forEach((el) => {
        const finalValue = parseInt(el.textContent.replace(/\D/g, ""));
        animateCounter(el, 0, finalValue, 1500, false, "+");
    });

    //grafica de ingresos
    const options = {
        chart: {
            type: 'area',
            height: 400,
            toolbar: {
                show: false
            }
        },
        series: [
            {
                name: "Ingresos total",
                data: [310000, 400000, 280000, 510000, 420000, 1000000]
            },
            {
                name: "Gastos total",
                data: [800000, 320000, 450000, 790000, 340000, 440000]
            }
        ],
        xaxis: {
            categories: ["Ene", "Feb", "Mar", "Abr", "May", "Jun"],
        },
        yaxis: {
            min: 200000,
            max: 1200000,
            tickAmount: 5,
            labels: {
                formatter: function (val) {
                    // Personalizamos SOLO los valores que nos interesan
                    switch (val) {
                        case 200000: return "20k";
                        case 300000: return "30k";
                        case 400000: return "40k";
                        case 600000: return "60k";
                        case 800000: return "80k";
                        case 1000000: return "100k";
                        case 1200000: return "120k";
                        default: return "";
                    }
                }
            }
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return "$" + val.toLocaleString();
                }
            }
        },
        stroke: {
            curve: 'smooth',
            width: 2
        },
        colors: ["#28a745", "#ffc107"],
        markers: {
            size: 5,
            hover: {
                size: 6.5,
                sizeOffset: 0
            }
        },
        dataLabels: {
            enabled: false
        },
        legend: {
            position: 'top'
        }
    };

    const chart = new ApexCharts(document.querySelector("#chart"), options);
    chart.render();

    // ventas totales
    var optionsVentasTotales = {
        chart: {
            type: 'donut',
            height: 400
        },
        series: [6000, 2000, 1000, 600],
        labels: [
            "Envíos $32,98 (2%)",
            "Reembolsos $11 (11%)",
            "Pedidos $14,87 (1%)",
            "Ingresos $3.271 (86%)"
        ],
        colors: ["#28a745", "#ffc107", "#dc3545", "#007bff"], // verde, amarillo, rojo, azul
        plotOptions: {
            pie: {
                donut: {
                    size: '85%',
                    labels: {
                        show: true,
                        name: {
                            show: true,
                            fontSize: '16px',
                            offsetY: -10,
                            fontFamily: 'Geist, sans-serif'
                        },
                        value: {
                            show: true,
                            fontSize: '24px',
                            fontWeight: 700,
                            fontFamily: 'Geist, sans-serif',
                            formatter: function (val) {
                                return val;
                            }
                        },
                        total: {
                            show: true,
                            label: 'Ventas totales',
                            fontSize: '18px',
                            color: '#000',
                            formatter: function (w) {
                                return w.globals.seriesTotals.reduce((a, b) => a + b, 0);
                            }
                        }
                    }
                }
            }
        },
        stroke: {
            show: false,
            width: 0
        },
        dataLabels: {
            enabled: false
        },
        tooltip: {
            theme: "light", // fondo blanco
            custom: function ({ series, seriesIndex, w }) {
                // Agarramos solo la primera palabra del label
                const labelCompleto = w.globals.labels[seriesIndex];
                const label = labelCompleto.split(" ")[0];
                const value = series[seriesIndex].toLocaleString("es-CO");
                return `
                <div style="padding:6px 10px; font-family:Geist, sans-serif; font-size:14px; font-weight:600; color:#000; background:#fff; border-radius:6px; box-shadow:0 2px 6px rgba(0,0,0,0.15);">
                    ${label}: ${value}
                </div>
            `;
            }
        },
        legend: {
            position: 'bottom',
            horizontalAlign: 'left',
            fontSize: '14px',
            fontWeight: 600,
            fontFamily: 'Geist, sans-serif',
            itemMargin: {
                vertical: 5
            },
            markers: {
                radius: 12
            },
            formatter: function (seriesName, opts) {
                return seriesName; // mantiene los textos como están
            }
        },
        responsive: [{
            breakpoint: 600,
            options: {
                legend: {
                    position: 'bottom',
                    horizontalAlign: 'center'
                }
            }
        }]
    };

    // renderizar gráfico
    var chartVentasTotales = new ApexCharts(document.querySelector("#ventasTotales"), optionsVentasTotales);
    chartVentasTotales.render();


    // Gráfico de columna apilada
    var optionsColum = {
        chart: {
            type: 'bar',
            height: 400,
            stacked: true,
            toolbar: {
                show: false
            },
            fontFamily: 'Geist'
        },
        series: [
            {
                name: 'Ventas',
                data: [300, 305, 195, 250, 400, 200, 55, 450, 155]
            },
            {
                name: 'Stock Disponible',
                data: [450, 250, 450, 350, 280, 70, 100, 500, 200]
            }
        ],
        xaxis: {
            categories: ['Vino', 'Whisky', 'Tequila', 'Aguardiente', 'Ron', 'Ginebra', 'Vodka', 'Cerveza', 'Mezcal'],
            labels: {
                style: {
                    fontFamily: 'Geist',
                }
            }
        },
        yaxis: {
            labels: {
                style: {
                    fontFamily: 'Geist, sans-serif',
                    fontSize: '14px'
                }
            }
        },
        legend: {
            position: 'top',
            labels: {
                colors: '#000',
                useSeriesColors: false,
                fontFamily: 'Geist, sans-serif'
            }
        },
        plotOptions: {
            bar: {
                borderRadius: 0,
                columnWidth: '45%'
            }
        },
        colors: ['#1E90FF', '#32CD32'],
        dataLabels: {
            enabled: false
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return val + " unidades";
                }
            },
            style: {
                fontFamily: 'Geist, sans-serif'
            }
        }
    };

    var chartColum = new ApexCharts(document.querySelector("#chart__Colum"), optionsColum);
    chartColum.render();

    // mapa de colombia
    // Crear mapa sin controles de zoom ni desplazamiento
    const map = L.map('map', {
        zoomControl: false,
        attributionControl: false,
        dragging: false,
        scrollWheelZoom: false,
        doubleClickZoom: false,
        boxZoom: false,
        keyboard: false,
        tap: false,
        zoomSnap: 0.1
    });

    // No usamos ningún fondo (sin gris)
    map.eachLayer(layer => map.removeLayer(layer));

    const URL_DEPTOS = 'https://raw.githubusercontent.com/santiblanko/colombia.geojson/master/depto.json';
    const URL_MUNIS = 'https://raw.githubusercontent.com/santiblanko/colombia.geojson/master/mpio.json';

    // Ciudades y ventas
    const ventasPorCiudad = {
        'Bogotá D.C.': { ventas: 1245, coords: [4.7110, -74.0721] },
        'Medellín': { ventas: 890, coords: [6.2442, -75.5812] },
        'Cali': { ventas: 730, coords: [3.4516, -76.5320] },
        'Barranquilla': { ventas: 420, coords: [10.9639, -74.7964] },
        'Cartagena': { ventas: 315, coords: [10.3910, -75.4794] }
    };

    // Estilo de los departamentos
    function styleDept(feature) {
        return {
            color: '#c27a00',
            weight: 1,
            fillColor: '#ffe9b5',
            fillOpacity: 0.7
        };
    }

    // Cargar departamentos
    fetch(URL_DEPTOS)
        .then(r => r.json())
        .then(deptosGeoJSON => {
            const deptosLayer = L.geoJSON(deptosGeoJSON, {
                style: styleDept,
                onEachFeature: (feature, layer) => {
                    const name = feature.properties.NOMBRE || 'Departamento';
                    layer.bindTooltip(name, { sticky: true });
                }
            }).addTo(map);

            // Centrar el mapa en Colombia
            const bounds = deptosLayer.getBounds();
            map.fitBounds(bounds, { padding: [0, 0] });

            // Hacer que se vea más grande (zoom manual)
            const center = bounds.getCenter();
            map.setView(center, 5.1); // puedes subirlo un poco más si quieres acercarlo

            // Fijar límites para que no se pueda mover
            map.setMaxBounds(bounds);

            // Agregar puntos de ventas (ciudades)
            Object.entries(ventasPorCiudad).forEach(([nombre, data]) => {
                const { ventas, coords } = data;
                const circle = L.circleMarker(coords, {
                    radius: 5, // tamaño pequeño/mediano
                    fillColor: '#1b6fb8',
                    color: '#0b3a66',
                    weight: 1,
                    opacity: 1,
                    fillOpacity: 0.9
                }).addTo(map);

                // Tooltip con el número de ventas
                circle.bindTooltip(
                    `<strong>${nombre}</strong><br>Ventas: ${ventas}`,
                    { direction: 'top', offset: [0, -6] }
                );
            });
        });

    // grafica ventas por horas y dias de la semana
    // Días de la semana
    const days = ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"];

    // Rangos horarios
    const timeRanges = ["12AM-2AM", "6AM-8AM", "12PM-2PM", "6PM-8PM"];

    // Licores posibles
    const licores = ["Whisky", "Ron", "Cerveza", "Vino", "Tequila", "Vodka"];

    // Colores asignados a cada licor (para tooltip)
    const colorLicores = {
        Whisky: "#d4a017",
        Ron: "#8b0000",
        Cerveza: "#f4c542",
        Vino: "#800080",
        Tequila: "#e68a00",
        Vodka: "#4db8ff"
    };

    // Generar datos simulados de ventas
    const ventasPorHoraYDia = {};
    days.forEach(day => {
        ventasPorHoraYDia[day] = {};
        timeRanges.forEach(range => {
            // Generar ventas por cada licor (número aleatorio)
            const detalleLicores = {};
            licores.forEach(licor => {
                detalleLicores[licor] = Math.floor(Math.random() * 50); // 0–50 ventas
            });

            // Calcular total general
            const total = Object.values(detalleLicores).reduce((a, b) => a + b, 0);

            ventasPorHoraYDia[day][range] = {
                detalle: detalleLicores,
                total: total
            };
        });
    });

    // Crear estructura para ApexCharts
    const seriesData = timeRanges.map(range => ({
        name: range,
        data: days.map(day => ventasPorHoraYDia[day][range].total)
    }));

    // Opciones del gráfico
    const optionsGroupedBar = {
        chart: {
            type: 'bar',
            height: 500,
            width: '100%',
            fontFamily: 'Geist',
            toolbar: { show: false }
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: '55%',
                endingShape: 'rounded'
            }
        },
        colors: ["#28a745", "#ffc107", "#dc3545", "#007bff"], // colores pastel
        series: seriesData,
        xaxis: {
            categories: days,
            labels: {
                rotate: -45,
                style: {
                    colors: '#444',
                    fontSize: '13px',
                    fontFamily: 'Geist'
                }
            }
        },
        legend: {
            position: 'top',
            horizontalAlign: 'center',
            labels: {
                colors: '#000',
                useSeriesColors: true,
                fontFamily: 'Geist, sans-serif'
            }
        },
        tooltip: {
            custom: function ({ series, seriesIndex, dataPointIndex, w }) {
                const range = w.config.series[seriesIndex].name;
                const day = w.config.xaxis.categories[dataPointIndex];
                const info = ventasPorHoraYDia[day][range];
                const detalle = info.detalle;

                // Crear tabla HTML estilizada
                let detalleHTML = "";
                Object.entries(detalle).forEach(([licor, cantidad]) => {
                    detalleHTML += `
                    <div style="
                        display: flex; 
                        justify-content: space-between; 
                        align-items: center; 
                        padding: 2px 0;
                        border-bottom: 1px solid #eee;
                    ">
                        <div style="display: flex; align-items: center; gap: 6px;">
                            <span style="
                                display: inline-block; 
                                width: 10px; 
                                height: 10px; 
                                border-radius: 50%; 
                                background-color: ${colorLicores[licor]};
                            "></span>
                            <span style="font-weight: 500;">${licor}</span>
                        </div>
                        <span style="color: #555;">${cantidad}</span>
                    </div>
                `;
                });

                return `
                <div style="
                    font-family: Geist, sans-serif;
                    background: #fff;
                    border-radius: 8px;
                    padding: 10px 12px;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.15);
                    min-width: 200px;
                ">
                    <div style="font-weight: 600; color: #333; margin-bottom: 6px;">
                        ${day} (${range})
                    </div>
                    <div style="font-size: 13px; color: #666; margin-bottom: 8px;">
                        Total: <b>${info.total}</b> licores vendidos
                    </div>
                    ${detalleHTML}
                </div>
            `;
            }
        },
        dataLabels: {
            enabled: false
        },
        grid: {
            borderColor: '#eee',
            row: {
                colors: ['#f9f9f9', 'transparent'],
                opacity: 0.5
            }
        }
    };

    // Renderizar gráfico
    const chartGroupedBar = new ApexCharts(document.querySelector("#heatmap"), optionsGroupedBar);
    chartGroupedBar.render();


    //Grafica de proyeccion
    // ====== Generador pseudoaleatorio determinista ======
    function seededRandom(seed) {
        var x = Math.sin(seed) * 10000;
        return x - Math.floor(x);
    }

    // ====== GENERADOR DE DATOS CON SUBPICOS EN TODAS LAS LÍNEAS (DET.) ======
    function generateYearWiseSalesWithContinuousSubPeaks(baseYear, count, yrange, seed = 12345) {
        var series = [];
        var lastValue = 1200; // Valor inicial en $

        for (var i = 0; i < count; i++) {
            var year = baseYear + i;

            // Número de puntos principales (picos grandes) por año
            var mainPoints = 2;
            var mainStart = lastValue;

            for (var mp = 0; mp < mainPoints; mp++) {
                // Pico grande determinista
                var mainChange = Math.floor(seededRandom(seed + year * 10 + mp) * 350 - 200);
                var mainValue = mainStart + mainChange;

                if (mainValue > yrange.max) mainValue = yrange.max - seededRandom(seed + year * 20 + mp) * 50;
                if (mainValue < yrange.min) mainValue = yrange.min + seededRandom(seed + year * 30 + mp) * 50;

                // Subpicos deterministas
                var subPeaks = 10; // cantidad de micro-picos por tramo
                for (var sp = 0; sp < subPeaks; sp++) {
                    var microChange = Math.floor(seededRandom(seed + year * 40 + mp * 10 + sp) * 20 - 10);
                    var y = mainStart + ((mainValue - mainStart) * (sp + 1) / subPeaks) + microChange;

                    series.push([year + (mp + sp / subPeaks) / mainPoints, y]);
                }

                mainStart = mainValue;
            }

            lastValue = mainStart;
        }

        return series;
    }

    // ====== DATOS ======
    var salesData = generateYearWiseSalesWithContinuousSubPeaks(2017, 9, { min: 1000, max: 2500 });

    // ====== OPCIONES DEL CHART ======
    var optionsProye = {
        series: [{
            name: 'Ventas Totales',
            data: salesData
        }],
        chart: {
            type: 'area',
            stacked: false,
            height: 400,
            zoom: { type: 'x', enabled: true, autoScaleYaxis: true },
            toolbar: { show: false },
            fontFamily: 'Geist, sans-serif'
        },
        dataLabels: { enabled: false },
        stroke: { curve: 'straight', width: 3 },
        markers: { size: 0 },
        fill: {
            type: 'gradient',
            gradient: {
                shade: 'light',
                type: 'vertical',
                shadeIntensity: 1,
                gradientToColors: ['#00BFFF'],
                inverseColors: false,
                opacityFrom: 0.6,
                opacityTo: 0,
                stops: [0, 100]
            }
        },
        yaxis: {
            labels: { formatter: val => `$${val.toFixed(0)}`, style: { fontFamily: 'Geist, sans-serif', fontSize: '14px', fontWeight: 400 } },
            title: { text: 'Ventas Totales', style: { fontFamily: 'Geist, sans-serif', fontSize: '13px', fontWeight: 700 } }
        },
        xaxis: {
            type: 'numeric',
            title: { text: 'Años', style: { fontFamily: 'Geist, sans-serif', fontSize: '14px', fontWeight: 700 } },
            labels: { formatter: val => Math.floor(val), style: { fontFamily: 'Geist, sans-serif', fontSize: '13px' } }
        },
        tooltip: { y: { formatter: val => `$${val.toFixed(0)}`, style: { fontFamily: 'Geist, sans-serif' } } }
    };

    // ====== RENDERIZAR ======
    var chartProye = new ApexCharts(document.querySelector("#chartVentas"), optionsProye);
    chartProye.render();

    //Predecir compras


});