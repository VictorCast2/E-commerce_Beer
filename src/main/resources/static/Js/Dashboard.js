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
                data: [300, 105, 95, 50, 70, 40, 55, 280, 25]
            },
            {
                name: 'Stock Disponible',
                data: [450, 220, 180, 150, 90, 70, 100, 300, 40]
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
                columnWidth: '40%'
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

    // Top 5 Productos Más Vendidos
    var optionsPie = {
        series: [150, 120, 90, 80, 70],
        chart: {
            type: 'pie',
            height: 400,
            fontFamily: 'Geist'
        },
        labels: ['Corona (30%)', 'Vino Lambrusco (24%)', 'Johnnie Walker (18%)', 'Don Julio (16%)', 'Ron Medellín (12%)'],
        colors: ['#8B5E34', '#C18C5D', '#E3B778', '#F3DFA2', '#FCF6BD'],
        legend: {
            position: 'bottom',
            horizontalAlign: 'left',
            floating: false,
            fontFamily: 'Geist',
            fontSize: '14px',
            fontWeight: 500,
            labels: {
                colors: '#000'
            },
            markers: {
                width: 12,
                height: 12,
                radius: 12
            },
            itemMargin: {
                horizontal: 0,
                vertical: 5
            },
            formatter: function (seriesName) {
                return seriesName;
            },
            width: 170 // ajusta el ancho para forzar que estén en columna
        },
        stroke: {
            show: false,
            width: 0
        },
        dataLabels: {
            enabled: false
        },
        tooltip: {
            enabled: true,
            y: {
                formatter: function (val) {
                    return val + " ventas";
                }
            }
        }
    };

    var chartPie = new ApexCharts(document.querySelector("#chart__Top5"), optionsPie);
    chartPie.render();

    // Gráfica Heatmap
    const days = ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"];

    const data = days.map(day => ({
        name: day,
        data: Array.from({ length: 12 }, (_, i) => ({
            x: `${i * 2}:00 - ${(i * 2) + 2}:00`, // Rango de horas
            y: Math.floor(Math.random() * 100) // Simula ventas aleatorias
        }))
    }));

    const optionsheatmap = {
        chart: {
            type: 'heatmap',
            height: 450,
            toolbar: { show: false }
        },
        plotOptions: {
            heatmap: {
                shadeIntensity: 0.5,
                colorScale: {
                    ranges: [
                        { from: 0, to: 20, color: '#F4E1D2', name: 'Bajas' },
                        { from: 21, to: 50, color: '#F9C784', name: 'Medias' },
                        { from: 51, to: 100, color: '#E26A2C', name: 'Altas' }
                    ]
                }
            }
        },
        dataLabels: { enabled: false },
        xaxis: {
            labels: {
                rotate: -45,
                style: {
                    colors: '#444',
                    fontSize: '12px',
                    fontFamily: 'Geist'
                }
            }
        },
        yaxis: {
            labels: {
                style: {
                    colors: '#444',
                    fontSize: '14px',
                    fontWeight: 500,
                    fontFamily: 'Geist'
                }
            }
        },
        tooltip: {
            y: {
                formatter: val => `${val} ventas`
            }
        }
    };

    const chartheatmap = new ApexCharts(document.querySelector("#heatmap"), {
        ...optionsheatmap,
        series: data
    });
    chartheatmap.render();

});