// Tab.js

const tunerTab = document.querySelector("#tab-tuner");
const tabBar = document.querySelector(".mdc-tab-bar");

function updateTab() {
	if (location.hash != "#tuner")
		location.hash = "#tuner"

	if (location.hash == "#tuner") {
		tunerTab.classList.add("active");
		tabBar.MDCTabBar.activateTab(0);
	}
}

window.addEventListener("hashchange", updateTab);
updateTab();