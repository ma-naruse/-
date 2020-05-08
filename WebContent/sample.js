/**
 * 
 */
function dateFunction() {
	// 日付オブジェクトを作成する
	var dd = new Date();
	// 「年」を取得する
	var YYYY = dd.getFullYear();
	// 「月」を取得する
	var MM = dd.getMonth() + 1;
	// 「日」を取得する
	var DD = dd.getDate();
	var date = YYYY + '' + MM + '' + DD;
	console.log(date);
}

$(document).ready(function() {
	dateFunction();
});