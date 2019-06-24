var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: ["Baixas Civis", "Baixas Militares"],
        datasets: [{
            data: [15000000, 45000000],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'],
            borderWidth: 1
        }]
    },

    options: {

        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true,
                    display:false,

                }
            }]
        }

    }

});
var ctx = document.getElementById("myChart1");
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ["Ussr", "Reino Unido","França", "Japão", "Alemanha","China"],
        datasets: [{
            data: [8800000, 383600, 217600, 2120000, 5533000, 3750000],
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(255, 159, 64, 0.2)' ],
            borderWidth: 1
        }]
    },

    options: {

        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true,
                    display:false,

                }
            }]
        }

    }

});
var ctx = document.getElementById("myChart2");
var myChart = new Chart(ctx, {
    type: 'polarArea',
    data: {
        labels: ["Ussr", "Reino Unido","França", "Japão", "Alemanha","China"],
        datasets: [{
            data: [18184000, 67200, 350000, 953000, 3000000, 13191000],
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(255, 159, 64, 0.2)' ],
            borderWidth: 1
        }]
    },

    options: {

        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true,
                    display:false,

                }
            }]
        }

    }

});
