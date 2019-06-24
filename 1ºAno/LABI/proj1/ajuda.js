function ajuda()  //Botão de ajuda para exlicar o que fazer(Invasão à Polónia)
{
  alert("Seleciona duas bandeiras");
};

//Funções para as bandeiras (Invasão à Polónia)
function Click()
{
  document.getElementById("China").src = "Img/wrong.png";
}
function Click1()
{
  document.getElementById("EUA").src="Img/wrong.png";
}
function Click2()
{
  document.getElementById("UK").src = "Img/correct.png";
}
function Click3()
{
  document.getElementById("France").src = "Img/correct.png";
}

//Botão de Resposta (Invasão à Polónia)
$(document).ready(function()
{
  $(".btn-success" ).popover({title: "Resposta", content: "Os Estados Unidos apenas declaram guerra aos alemães em 1941 (11 Dezembro). Os Chineses declararam dois dias antes, ou seja, a 9 de Dezembro de 1941. No dia 3 de Setembro de 1939, a França e o Reino Unido declaram guerra à Alemanha.", placement: "bottom"});
});

//Gráfico de mortes da invasão da Polónia (Invasão à Polónia)
function Grafic() {
$("#grafico-linhas").highcharts({
  title: {
    text: "Número de Mortes na invasão da Polónia",
  },
  xAxis: {
    categories: ["Alemães", "Soviéticos", "Polacos"]
  },
  series: [{
    name: "Mortos",
    data: [16*1000 , 5*1000  , 66*1000]
    }]
  });
};




//Botões + e Setas (A Guerra de Inverno)
function Mais()
{
  document.getElementById("divMais").innerHTML = "Inverno rigoroso com condições atmosféricas muito duras.";
}
function Mais1()
{
  document.getElementById("divMais1").innerHTML = "Grande resistência por parte dos finlandeses."
}
function Seta()
{
  document.getElementById("divSeta").innerHTML = "Grandes perdas na frente de combate soviética."
}
function Mais2()
{
  document.getElementById("divMais2").innerHTML = "Enormes perdas no lado soviético."
}



//Mapa para a invasão da Noruega e Dinamarca (Invasões à Noruega e Dinamarca)
var map = new L.Map("MapNor", {center: [59.911491,10.757933],zoom: 3.5});
var osmUrl="http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
var osmAttrib="Map data OpenStreetMap contributors";
var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});
map.addLayer(osm);
var marnorte = L.polygon(
[ [59.911491, 10.757933], [55.676098, 12.568337],
[52.520008, 13.404954], [51.509865, -0.118092], [57.149651, -2.099075] ],
{ color: "red" } );
marnorte.addTo(map);
var icon = L.icon({ iconUrl: "Img/country.png" });
var pontos = [
L.marker([ 59.911491, 10.757933], {icon: icon}).bindPopup("Oslo"),
L.marker([52.520008, 13.404954], {icon: icon}).bindPopup("Berlim"),
L.marker([51.509865, -0.118092], {icon: icon}).bindPopup("Londres")
];
for(i in pontos) {
pontos[i].addTo(map);
}



//Imagens do Mapa de França (A Guerra de Mentira/Invasão à França)
function Fr1()
{
  document.getElementById("Fr1").src = "Img/fr2.png";
}
function Fr2()
{
  document.getElementById("Fr1").src = "Img/fr1.png"
}



//Gráfico Inglaterra (Nlitz)
google.charts.load("current", {packages:['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ["Group", "Baixas", { role: "style" } ],
        ["Civis Mortos", 43*1000, "#ff4d4d"],
        ["Civis Feridos", 139*1000, "#ff1a1a"],
      ]);

      var view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "string",
                         role: "annotation" },
                       2]);

      var options = {
        title: "Baixas causadas pelo Blitz",
        width: 600,
        height: 400,
        bar: {groupWidth: "95%"},
        legend: { position: "none" },
      };
      var chart = new google.visualization.ColumnChart(document.getElementById("Blt"));
      chart.draw(view, options);
  }



  //Botões Dia D(Dia D)

  function DiaD1()
  {
    document.getElementById("D1").innerHTML = "Umas horas mais tarde, deu-se o maior desembarque alguma vez visto por toda a costa francesa."
  }



  //Imagens das Bombas (Bombas Atómicas)
  function Bomb1()
  {
    document.getElementById("LittleBoy").src = "Img/bomb.gif";
  }
  function Bomb2()
  {
    document.getElementById("FatMan").src="Img/bomb.gif"
  }
