$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var to = new Date();
    $('.to input').val(moment(to).format('YYYY-MM-DD'));
    to.setMonth(to.getMonth() - 11);
    $('.from input').val(moment(to).format('YYYY-MM-DD'));
    static('doanhthu', createRevenueChart);
    static('giasuvahocvien', createMemberChart);
    static('lop', createClassChart);
    static('giasutheohocphi', createByTuitionChart);
    static('theoquan', createByDistrictChart);
});

function createByTuitionChart(data) {
    var labels = data.map((value) => value[0]);
    var data0 = data.map((value) => value[1]);
    data = [labels, data0];
    pieChart1('tuition-chart', data)
}

function createByDistrictChart(data) {
    var labels = data.map((value) => value[0]);
    var data0 = data.map((value) => value[1]);
    var data1 = data.map((value) => value[2]);
    data = [labels, data0, data1];
    barChart('district-chart', data, ['Lớp', 'Gia sư']);
}

function createRevenueChart(data) {
    var labels = data.map((value) => value[0]);
    var data0 = data.map((value) => value[1]);
    data = [labels, data0];
    lineChart1('revenue-chart', data, ['Doanh thu'], ['Tháng', 'VND'])
}

function createMemberChart(data) {
    var labels = data.map((value) => value[0]);
    var data0 = data.map((value) => value[1]);
    var data1 = data.map((value) => value[2]);
    data = [labels, data0, data1];
    console.log(data);
    lineChart2('member-chart', data, ['Gia sư mới', 'Học viên mới'], ['Tháng', 'Số lượng'])
}

function createClassChart(data) {
    var labels = data.map((value) => value[0]);
    var data0 = data.map((value) => value[1]);
    var data1 = data.map((value) => value[2]);
    data = [labels, data0, data1];
    console.log(data);
    lineChart2('class-chart', data, ['Lớp mới', 'Lớp đã giao'], ['Tháng', 'Số lượng'])
}

function barChart(elementSelector, data, labeldata, labelxy) {
    try {
        //bar chart
        var ctx = document.getElementById(elementSelector);
        if (ctx) {
            ctx.height = 150;
            var myChart = new Chart(ctx, {
                type: 'bar',
                defaultFontFamily: 'Poppins',
                data: {
                    labels: data[0],
                    datasets: [
                        {
                            label: labeldata[0],
                            data: data[1],
                            borderColor: "rgba(0, 123, 255, 0.9)",
                            borderWidth: "0",
                            backgroundColor: "rgba(0, 123, 255, 0.5)",
                            fontFamily: "Poppins"
                        },
                        {
                            label: labeldata[1],
                            data: data[2],
                            borderColor: "rgba(0,0,0,0.09)",
                            borderWidth: "0",
                            backgroundColor: "rgba(0,0,0,0.07)",
                            fontFamily: "Poppins"
                        }
                    ]
                },
                options: {
                    legend: {
                        position: 'top',
                        labels: {
                            fontFamily: 'Poppins'
                        }

                    },
                    scales: {
                        xAxes: [{
                            ticks: {
                                fontFamily: "Poppins"

                            }
                        }],
                        yAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontFamily: "Poppins"
                            }
                        }]
                    }
                }
            });
        }


    } catch (error) {
        console.log(error);
    }
}
function singleBarChart(elementSelector, data, labeldata, labelxy) {
    try {

        // single bar chart
        var ctx = document.getElementById(elementSelector);
        if (ctx) {
            ctx.height = 150;
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data[0],
                    datasets: [
                        {
                            label: labeldata[0],
                            data: data[1],
                            borderColor: "rgba(0, 123, 255, 0.9)",
                            borderWidth: "0",
                            backgroundColor: "rgba(0, 123, 255, 0.5)"
                        }
                    ]
                },
                options: {
                    legend: {
                        position: 'top',
                        labels: {
                            fontFamily: 'Poppins'
                        }

                    },
                    scales: {
                        xAxes: [{
                            ticks: {
                                fontFamily: "Poppins"

                            }
                        }],
                        yAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontFamily: "Poppins"
                            }
                        }]
                    }
                }
            });
        }

    } catch (error) {
        console.log(error);
    }
}

function pieChart1(elementSelector, data) {
    try {

        //pie chart
        var ctx = document.getElementById(elementSelector);
        if (ctx) {
            ctx.height = 150;
            var myChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    datasets: [{
                        data: data[1],
                        backgroundColor: [
                            "rgba(0, 123, 255,0.9)",
                            "rgba(0, 123, 255,0.7)",
                            "rgba(0, 123, 255,0.5)",
                            "rgba(0,0,0,0.07)"
                        ],
                        hoverBackgroundColor: [
                            "rgba(0, 123, 255,0.9)",
                            "rgba(0, 123, 255,0.7)",
                            "rgba(0, 123, 255,0.5)",
                            "rgba(0,0,0,0.07)"
                        ]

                    }],
                    labels: data[0]
                },
                options: {
                    legend: {
                        position: 'top',
                        labels: {
                            fontFamily: 'Poppins'
                        }

                    },
                    responsive: true
                }
            });
        }


    } catch (error) {
        console.log(error);
    }
}

function lineChart1(elementSelector, data, labeldata, labelxy) {
    try {
        //Sales chart
        var ctx = document.getElementById(elementSelector);
        if (ctx) {
            ctx.height = 150;
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: data[0],
                    type: 'line',
                    defaultFontFamily: 'Poppins',
                    datasets: [{
                        data: data[1],
                        label: labeldata[0],
                        backgroundColor: 'rgba(0,103,255,.15)',
                        borderColor: 'rgba(0,103,255,0.5)',
                        borderWidth: 3.5,
                        pointStyle: 'circle',
                        pointRadius: 5,
                        pointBorderColor: 'transparent',
                        pointBackgroundColor: 'rgba(0,103,255,0.5)',
                    },]
                },
                options: {
                    responsive: true,
                    tooltips: {
                        mode: 'index',
                        titleFontSize: 12,
                        titleFontColor: '#000',
                        bodyFontColor: '#000',
                        backgroundColor: '#fff',
                        titleFontFamily: 'Poppins',
                        bodyFontFamily: 'Poppins',
                        cornerRadius: 3,
                        intersect: false,
                    },
                    legend: {
                        display: false,
                        position: 'top',
                        labels: {
                            usePointStyle: true,
                            fontFamily: 'Poppins',
                        },


                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            scaleLabel: {
                                display: false,
                                labelString: labelxy[0]
                            },
                            ticks: {
                                fontFamily: "Poppins"
                            }
                        }],
                        yAxes: [{
                            display: true,
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            scaleLabel: {
                                display: true,
                                labelString: labelxy[1],
                                fontFamily: "Poppins"
                            },
                            ticks: {
                                fontFamily: "Poppins"
                            }
                        }]
                    },
                    title: {
                        display: false,
                    }
                }
            });
        }
    } catch (error) {
        console.log(error);
    }
}

function lineChart2(elementSelector, data, labeldata, labelxy) {
    try {
        //Sales chart
        var ctx = document.getElementById(elementSelector);
        if (ctx) {
            ctx.height = 150;
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: data[0],
                    type: 'line',
                    defaultFontFamily: 'Poppins',
                    datasets: [{
                        label: labeldata[0],
                        data: data[1],
                        backgroundColor: 'transparent',
                        borderColor: 'rgba(220,53,69,0.75)',
                        borderWidth: 3,
                        pointStyle: 'circle',
                        pointRadius: 5,
                        pointBorderColor: 'transparent',
                        pointBackgroundColor: 'rgba(220,53,69,0.75)',
                    }, {
                        label: labeldata[1],
                        data: data[2],
                        backgroundColor: 'transparent',
                        borderColor: 'rgba(40,167,69,0.75)',
                        borderWidth: 3,
                        pointStyle: 'circle',
                        pointRadius: 5,
                        pointBorderColor: 'transparent',
                        pointBackgroundColor: 'rgba(40,167,69,0.75)',
                    }]
                },
                options: {
                    responsive: true,
                    tooltips: {
                        mode: 'index',
                        titleFontSize: 12,
                        titleFontColor: '#000',
                        bodyFontColor: '#000',
                        backgroundColor: '#fff',
                        titleFontFamily: 'Poppins',
                        bodyFontFamily: 'Poppins',
                        cornerRadius: 3,
                        intersect: false,
                    },
                    legend: {
                        display: false,
                        labels: {
                            usePointStyle: true,
                            fontFamily: 'Poppins',
                        },
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            scaleLabel: {
                                display: false,
                                labelString: labelxy[0]
                            },
                            ticks: {
                                fontFamily: "Poppins"
                            }
                        }],
                        yAxes: [{
                            display: true,
                            gridLines: {
                                display: false,
                                drawBorder: false
                            },
                            scaleLabel: {
                                display: true,
                                labelString: labelxy[1],
                                fontFamily: "Poppins"

                            },
                            ticks: {
                                fontFamily: "Poppins"
                            }
                        }]
                    },
                    title: {
                        display: false,
                        text: 'Normal Legend'
                    }
                }
            });
        }
    } catch (error) {
        console.log(error);
    }
}