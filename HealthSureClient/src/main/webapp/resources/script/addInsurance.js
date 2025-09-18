/**

 Copyright © 2025 Infinite Computer Solution. All rights reserved.
 
 
 Description: This is javscript file for addInsurance.jsp file

*/


function selectOneMenu() {
	if (document.getElementById("companyForm:planType").value == 'FAMILY') {
		document.getElementById("individulMember").style.display = 'none';
		document.getElementById("memberDetail").style.display = 'block';
	} else if (document.getElementById("companyForm:planType").value == 'INDIVIDUAL') {
		document.getElementById("memberDetail").style.display = 'none';
		document.getElementById("individulMember").style.display = 'block';
	} else {
		document.getElementById("memberDetail").style.display = 'none';
		document.getElementById("individulMember").style.display = 'none';
	}
}

window.onload = function() {
	selectOneMenu();
	setTimeout(function() {
		var msg = document.getElementById('globalMsg');
		if (msg) {
			msg.style.display = 'none';
		}
	}, 5000);
};

window.addEventListener('DOMContentLoaded', function() {
	var inputs = document.querySelectorAll('input.date-input');
	for (var i = 0; i < inputs.length; i++) {
		inputs[i].setAttribute('type', 'date');
	}
});
