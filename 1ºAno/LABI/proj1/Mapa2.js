//Mapa do Dia D (Dia D)
var map = new L.Map("D", {center: [48.8798704,0.1712529],zoom: 6});
var osmUrl="http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
var osmAttrib="Map data OpenStreetMap contributors";
var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});
map.addLayer(osm);
var pontos = [
L.marker([ 48.8798704, 0.1712529],).bindPopup("Normandia, Local do desembarque")
];
for(i in pontos) {
pontos[i].addTo(map);
}
