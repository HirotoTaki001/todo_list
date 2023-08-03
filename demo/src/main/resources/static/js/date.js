var today = new Date();
today.setDate(today.getDate());
function dateFormat(today, format){
	format = format.replace("YYYY", today.getFullYear());
	format = format.replace("MM", ("0"+(today.getMonth() + 1)).slice(-2));
 	format = format.replace("DD", ("0"+ today.getDate()).slice(-2));
 	return format;
}
const data = dateFormat(today,'YYYY-MM-DD');
const field = document.getElementById("days");
const hz = document.getElementById("hzdays");
field.value = data;
hz.value = data;