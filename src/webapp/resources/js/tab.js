// Tab.js

const trajectoryTab = document.querySelector("#tab-trajectory");
const tunerTab = document.querySelector("#tab-tuner");
const tabBar = document.querySelector(".mdc-tab-bar");

function updateTab() {
	if (location.hash != "#trajectory" && location.hash != "#tuner")
		location.hash = "#trajectory"

	if (location.hash == "#trajectory") {
		trajectoryTab.classList.add("active");
		tunerTab.classList.remove("active");
		tabBar.MDCTabBar.activateTab(0);
	}
	else if (location.hash == "#tuner") {
		tunerTab.classList.add("active");
		trajectoryTab.classList.remove("active");
		tabBar.MDCTabBar.activateTab(1);
	}
	else throw "Unknown error has occured."
}

window.addEventListener("hashchange", updateTab);
updateTab();