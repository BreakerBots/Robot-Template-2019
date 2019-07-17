//Tuner.js

// -- Tuner UI -- \\
const outputRefreshRateMs = 50;
const chartMaxLengthMs = 10000;

function clearTuner() {
	document.querySelector("#tuner-container > #inputBox").innerHTML = "";
	clearChart();
}
var chartIndex = 0;
var chart, chartConfig;
function clearChart() {
	chartConfig = {
		type: 'line', data: { labels: [], datasets: [] },
		options: {
			responsive: true,
			scales: {
				xAxes: [{ display: true, scaleLabel: { display: true, labelString: 'Time' } }],
				yAxes: [{ display: true, scaleLabel: { display: true, labelString: 'Value' } }]
			},
			cubicInterpolationMode: 'monotone',
			elements: { point: { radius: 0 } },
			animation: { duration: 50 }
		}
	};
	chart = new Chart(document.querySelector("#tunerChart").getContext('2d'), chartConfig);
} clearChart();
function initTunerOutput(names, values) {
	const colors = ['red', 'gold', 'green', 'blue', 'purple', 'orange', 'yellow', 'black', 'grey', 'pink'];
	for (var t = 0; t < names.length; t++) {
		var nd = {
			label: names[t],
			backgroundColor: colors[t],
			borderColor: colors[t],
			data: [],
			fill: false
		};

		for (var i = 0; i < chartConfig.data.labels.length; i++) {
			nd.data.push(values[t]);
		}

		chartConfig.data.datasets.push(nd);
		chart.update();
	}
}
function updateTunerOutput(values) {
	var rm = chartConfig.data.labels.length-1 > (chartMaxLengthMs / outputRefreshRateMs);
	chartConfig.data.labels.push(chartIndex.toFixed(2));
	if (rm) chartConfig.data.labels.shift();
	chartConfig.data.datasets.forEach(function (dataset, i) {
		dataset.data.push(values[i]);
		if (rm) dataset.data.shift();
	});
	chart.update();
}
const tunerInputElementUI = `
<div class="mdc-text-field mdc-text-field--outlined" data-mdc-auto-init="MDCTextField">
	<input oninput="updateTunerInput(this)" id="tf-outlined" class="mdc-text-field__input">
	<div class="mdc-notched-outline">
		<div class="mdc-notched-outline__leading"></div>
		<div class="mdc-notched-outline__notch">
			<label for="tf-outlined" class="mdc-floating-label">NAME</label>
		</div>
		<div class="mdc-notched-outline__trailing"></div>
	</div>
</div>
`;
function addTunerInputElement(name, value) {
	var element = document.createElement("div");
	element.innerHTML = tunerInputElementUI.replace("NAME", name);
	element.className = "tunerInputElement";
	document.querySelector("#tuner-container > #inputBox").appendChild(element);
	mdc.autoInit(element);
	element.firstElementChild.MDCTextField.value = value;
}
function updateTunerInput(element) {
	var name = element.parentNode.querySelector(".mdc-floating-label").innerText;
	var value = element.parentNode.MDCTextField.value;
	tunerSet(name, value);
}

// -- Webapp Communication -- \\
function tunerSet(name, value) {
	var xhr = new XMLHttpRequest();
	xhr.timeout = 200;

	//Send Request
	xhr.open("POST", "/tuner/set", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send(JSON.stringify({
		name: name,
		value: value
	}));
}

function tunerUpdate() {
	chartIndex += outputRefreshRateMs/1000.0;
	var xhr = new XMLHttpRequest();
	xhr.timeout = 200;
	xhr.onreadystatechange = function () {
		if (this.readyState != 4) return;

		//Data Recieved
		if (this.status == 200) {
			var data = JSON.parse(this.responseText);
			var values = [];
			for (var i = 0; i < Object.keys(data).length; i++) {
				values.push(data[Object.keys(data)[i]]);
			}
			updateTunerOutput(values);
		}
	};
	//Send Request
	xhr.open("POST", "/tuner/get", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send("");
}
setInterval(tunerUpdate, outputRefreshRateMs);

function tunerInit() {
	var xhr = new XMLHttpRequest();
	xhr.timeout = 2000;
	xhr.onreadystatechange = function () {
		if (this.readyState != 4) return;

		//Data Recieved
		if (this.status == 200) {
			var data = JSON.parse(this.responseText);

			clearTuner();
			var outputNames = [];
			var outputValues = [];

			for (var i = 0; i < Object.keys(data).length; i++) {
				var name = Object.keys(data)[i];
				var value = data[Object.keys(data)[i]];
				//input
				if (value[0]) {
					addTunerInputElement(name, value[1]);
				}
				//output
				else {
					outputNames.push(name);
					outputValues.push(value[1]);
				}
			}

			initTunerOutput(outputNames, outputValues);
		}
	};
	//Send Request
	xhr.open("POST", "/tuner/init", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send("");
}
tunerInit();