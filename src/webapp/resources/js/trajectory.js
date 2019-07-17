// Trajectory.js

var waypoints = [
	{ x: 0, y: 0, a: 0 },
	{ x: 10, y: 0, a: 0 },
	{ x: 18.5, y: 2.5, a: 0 }
];

// -- Trajectory UI -- \\
const waypointUI = `
<div class="mdc-text-field mdc-text-field--outlined" data-mdc-auto-init="MDCTextField">
	<input oninput="refreshTrajectory()" type="number" step="0.01" id="tf-outlined" class="mdc-text-field__input">
	<div class="mdc-notched-outline">
		<div class="mdc-notched-outline__leading"></div>
		<div class="mdc-notched-outline__notch">
			<label for="tf-outlined" class="mdc-floating-label">x</label>
		</div>
		<div class="mdc-notched-outline__trailing"></div>
	</div>
</div>
<div class="mdc-text-field mdc-text-field--outlined" data-mdc-auto-init="MDCTextField">
	<input oninput="refreshTrajectory()" type="number" step="0.01" id="tf-outlined" class="mdc-text-field__input">
	<div class="mdc-notched-outline">
		<div class="mdc-notched-outline__leading"></div>
		<div class="mdc-notched-outline__notch">
			<label for="tf-outlined" class="mdc-floating-label">y</label>
		</div>
		<div class="mdc-notched-outline__trailing"></div>
	</div>
</div>
<div class="mdc-text-field mdc-text-field--outlined" data-mdc-auto-init="MDCTextField">
	<input oninput="refreshTrajectory()" type="number" step="0.01" id="tf-outlined" class="mdc-text-field__input">
	<div class="mdc-notched-outline">
		<div class="mdc-notched-outline__leading"></div>
		<div class="mdc-notched-outline__notch">
			<label for="tf-outlined" class="mdc-floating-label">heading</label>
		</div>
		<div class="mdc-notched-outline__trailing"></div>
	</div>
</div>
<button onclick="removeWaypoint(this.parentNode)" style="color: #e7880a !important; border-color: #e7880a !important" class="mdc-button mdc-button--outlined" data-mdc-auto-init="MDCRipple">
	<span class="mdc-button__label">-</span>
</button>
`;
document.querySelectorAll(".waypointInput .mdc-notched-outline__notch").forEach(function (notch) {
	notch.style.width = notch.dataset.sw + 'px';
});
function addWaypoint() {
	var element = document.createElement("div");
	element.innerHTML = waypointUI;
	element.className = "waypointInput";

	document.querySelector("#waypointContainer").insertBefore(element, document.querySelector("#waypointAdd"));

	mdc.autoInit(element);
}

function removeWaypoint(node) {
	node.remove();
}

function updateWaypoints() {
	waypoints = [];
	document.querySelectorAll("#waypointContainer > .waypointInput").forEach(function (section) {
		var inputs = section.querySelectorAll("input");
		var x = inputs[0].value;
		var y = inputs[1].value;
		var a = inputs[2].value;
		if (x != "" && y != "" && a != "")
			waypoints.push({ x: parseFloat(x), y: parseFloat(y), a: parseFloat(a) });
	});
}

var refreshQueLength = 0;
var refreshLastCall = new Date();
function refreshTrajectory() {
	if (new Date().getTime() > refreshLastCall.getTime() + 500) {
		updateWaypoints();
		if (waypoints.length > 1)
			drawTrajectory(waypoints, 0, 10);
		else {
			var canvas = document.querySelector("#field");
			var ctx = canvas.getContext("2d");
			ctx.drawImage(field, 0, 0);
		}
		refreshLastCall = new Date();
		refreshQueLength--;
	}
	else {
		refreshQueLength++;
		setTimeout(refreshTrajectory, 500);
	}
}

function viewCode() {

}

// -- Trajectory Drawing (Field Canvas) -- \\
var field = new Image();
field.src = "resources/images/field.png";
field.onload = function () {
	refreshTrajectory();
}
function drawTrajectory(waypoints, startX, startY) {
	const robotWidth = 28 / 12.0;

	//Setup Canvas
	var canvas = document.querySelector("#field");
	var ctx = canvas.getContext("2d");

	//Setup Request
	var data = JSON.stringify(waypoints);
	var xhr = new XMLHttpRequest();
	xhr.timeout = 20000;

	xhr.onreadystatechange = function () {
		if (this.readyState != 4) return;

		//Data Recieved
		if (this.status == 200) {
			var data = JSON.parse(this.responseText);

			//Draw
			ctx.drawImage(field, 0, 0);
			drawLine(6, robotWidth / 2, _red);
			drawLine(6, 0, _yellow);
			drawLine(6, -robotWidth / 2, _red);

			function drawLine(lineWidth, offset, color) {
				ctx.lineWidth = lineWidth;//fieldToCanvas(lineWidth, 0).x;
				ctx.strokeStyle = color;
				ctx.beginPath();

				var lastX, lastY;
				data.forEach(function (segment, i) {
					var x = segment.x + startX;
					var y = segment.y + startY;
					var heading = segment.heading;
					var velocity = segment.velocity;

					var p = offsetPointByAngle(x, y, heading * (Math.PI / 180), offset);
					x = p.x;
					y = p.y;

					if (i == 0) {
						lastX = x;
						lastY = y;
					}

					var lastPoint = fieldToCanvas(lastX, lastY);
					var point = fieldToCanvas(x, y);
					ctx.moveTo(lastPoint.x, lastPoint.y);
					ctx.lineTo(point.x, point.y);

					lastX = x;
					lastY = y;
				});
				ctx.closePath();
				ctx.stroke();
			}
		}
	};

	//Send Request
	xhr.open("POST", "/trajectory", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send(data);

	//Field (feet) -> Canvas (pixels)
	const fieldWidth = 650.25 / 12.0 / 2.0;
	const fieldHeight = 322 / 12.0;
	const canvasWidth = 1768 / 2.0;
	const canvasHeight = 876;
	function fieldToCanvas(x, y) {
		return {
			x: x * (canvasWidth / fieldWidth),
			y: y * (canvasHeight / fieldHeight)
		};
	}
	function offsetPointByAngle(x, y, theta, offset) {
		return {
			x: x + (Math.sin(theta) * offset),
			y: y - (Math.cos(theta) * offset)
		};
	}
}