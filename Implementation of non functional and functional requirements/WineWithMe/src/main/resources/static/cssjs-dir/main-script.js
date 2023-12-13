var number = [[${number}]];
console.log(number)
var wineries=JSON.parse(/*[[${list0f}]]*/ '[]');
var customIcon = L.icon({
    iconUrl: '/img/marker.png',
    iconSize: [40, 45],
    iconAnchor: [20, 45],
    popupAnchor: [0, -30]
});


const map = L.map('map').setView([51.505, -0.09], 13);
map.locate({setView: true, maxZoom: 20});
const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

// map.on('locationfound', function (e) {
//     //var marker = L.marker(e.latlng).addTo(map);
//     map.flyTo(e.latlng, 10);
// });
wineries.forEach(function (wine) {

    var location = wine.location;
    latlnt = {"Lat": location.split("  ")[0], "Lng": location.split("  ")[1]}

    var marker = L.marker([location.split("  ")[0], location.split("  ")[1]], {icon: customIcon}).bindPopup(wine.name);

    //marker.on("click", onMarkerClick);
    marker.id=wine.id
    marker.addTo(map)
});



// function onMarkerClick(e) {
//     $.ajax({
//         type: "POST",
//         url: "/showinfo",
//         dataType: "html",
//         success: function(success) {
//             if (success) {
//                 console.log(success)
//                 window.location.href = "http://localhost:8080/mainpage?id="+e.target.id;
//
//             }
//         },
//         fail: function(xhr, status, error) {
//             console.log(error);
//         }
//     });
// }
