/**
 * approve the pharamcy
 */
function showLoader() {

	document.getElementById('loader').style.display = 'block';


	window.onload = function() {
		setTimeout(function() {
			var msgBox = document.getElementById('globalMessages');
			if (msgBox) {
				msgBox.style.display = 'none';
			}
			document.getElementById('loader').style.display = 'none';
		}, 10000); // Hide loader and message after 10 seconds
	};
}
