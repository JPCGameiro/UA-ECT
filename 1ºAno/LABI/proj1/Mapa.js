//Mapa Linha Maginot (A Guerra de Mentira/Invasão à França)
var map = new L.Map("LinhaM", {center: [48.864716, 2.3490],zoom: 5});
var osmUrl="http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
var osmAttrib="Map data OpenStreetMap contributors";
var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});
map.addLayer(osm);
var linha = L.polyline(
[ [51.088126, 2.546262], [49.498712, 5.870077],
[48.935921, 8.148038], [46.109000, 5.953365] ],
{ color: "red" } );
linha.addTo(map)



//Gráfico da Operação Barbarossa (Operação Barbarossa)
google.charts.load("current", {packages:['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ["Group", "Baixas", { role: "style" } ],
        ["Alemães Mortos", 186 *1000, "#ff4d4d"],
        ["Alemães Feridos", 655*1000, "#ff1a1a"],
        ["Soviéticos Mortos", 802*1000, "#80bfff"],
        ["Soviéticos Feridos", 3* 1000000, "#3399ff"],
        ["Mortos de Outros Países", 128*1000, "#009900"]
      ]);

      var view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "string",
                         role: "annotation" },
                       2]);

      var options = {
        title: "Baixas causadas pela operação Barbarossa",
        width: 600,
        height: 400,
        bar: {groupWidth: "95%"},
        legend: { position: "none" },
      };
      var chart = new google.visualization.ColumnChart(document.getElementById("Barbarossa"));
      chart.draw(view, options);
  }
