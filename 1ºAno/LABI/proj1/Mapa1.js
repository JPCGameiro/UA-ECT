//Mapa Inglaterra (Biltz)
var map = new L.Map("England", {center: [53.509865, -0.118092],zoom: 5.25});
var osmUrl="http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
var osmAttrib="Map data OpenStreetMap contributors";
var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});
map.addLayer(osm);
var icon = L.icon({ iconUrl: "Img/plan.png" });
var pontos = [
L.marker([ 52.489471, -1.898575], {icon: icon}).bindPopup("Birmingham"),
L.marker([53.38297, -1.4659], {icon: icon}).bindPopup("Sheffield"),
L.marker([50.909698, -1.404351], {icon: icon}).bindPopup("Southampton"),
L.marker([53.41058, -2.97794], {icon: icon}).bindPopup("Liverpool"),
L.marker([53.483959, -2.244644], {icon: icon}).bindPopup("Manchester"),
L.marker([55.86515, -4.25763], {icon: icon}).bindPopup("Glasgow"),
L.marker([51.509865, -0.118092], {icon: icon}).bindPopup("Londres")
];
for(i in pontos) {
pontos[i].addTo(map);
}
