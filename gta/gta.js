// Data file: globalTempAnomalies.csv
// Data from https://www.ncdc.noaa.gov/monitoring-references/faq/anomalies.php
//   https://www.ncdc.noaa.gov/cag/time-series/global/globe/land_ocean/p12/12/1880-2017.csv
// Global Land and Ocean Temperature Anomalies
//
// Code for this project: https://github.com/bethrobson/CCC
//
// COPYRIGHT 2018 WickedlySmart, LLC
//

window.onload = function() {

	// if you're getting the data via XHR
/*
	$.getJSON("gta.json", function(data) {
		createGraphic(data);
	});
*/
	// if you're getting the data via JS it's already in the global variable data
	createGraphic(data);
}

function createGraphic(data) {
	let colors = ["#D53E4F", "#F46D43", "#FDAE61", "#FEE08B", "#F6FAAA", "#E6F598", "#ABDDA4", "#66C2A5", "#7BA5C7", "#A17BC7"], // hottest to coolest
	$dataTable = $("table#dataTable");

	// build the table
	for (let i = 0; i < 28; i++) {
		let cell = "<th>" + (1990+i) + "</th>";
		for (let j = 1; j <= 12; j++) {
			cell = cell + "<td id=\"" + (1990+i) + "_" + j + "\"> </td>";
		}
		let row = "<tr>" + cell + "</tr>";
		$dataTable.append(row);
	}

	// make the legend
	for (let k = 0; k < colors.length; k++) {
		$("ul#colorList").append("<li style=\"background-color: " + colors[k] + "\"> </li>");
	}

	// i is index = color to choose
	// i+1 is ranking of month in top 10
	data.forEach(function(monthData) {
		monthData.forEach(function(yearData, i) {
			let id = yearData.year + "_" + yearData.month;
			let color = colors[i];
			let $td = $("table#dataTable td#" + id);
			$td.text(i+1); 
			$td.on("mouseover", function(e) {
				$(e.target).text(yearData.value);
			});
			$td.on("mouseout", function(e) {
				$(e.target).text(i+1);
			});
			$td.css("background-color", color);
		});
	});
}
