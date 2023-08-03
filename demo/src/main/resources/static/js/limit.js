var muki = document.getElementById('prop');
var lop = document.getElementById('loop');
var lim = document.getElementById('lims');
var dt = document.getElementById('days');

function notlimit() {
	if (muki.checked) {
		dt.disabled = true;
	} else {
		dt.disabled = false;
	}
}

function setDate() {
	var today = new Date();
	today.setDate(today.getDate() + 1);
	function dateFormat(today, format) {
		format = format.replace("YYYY", today.getFullYear());
		format = format.replace("MM", ("0" + (today.getMonth() + 1)).slice(-2));
		format = format.replace("DD", ("0" + today.getDate()).slice(-2));
		return format;
	}
	const data = dateFormat(today, 'YYYY-MM-DD');
	const field = document.getElementById("setday");
	field.value = data;
}

function changesel_muki() {
	lop.checked = false;
	lim.checked = false;
}

function changesel_lop() {
	muki.checked = false;
	lim.checked = false;
}

function changesel_lim() {
	lop.checked = false;
	muki.checked = false;
	dt.disabled = false;
}