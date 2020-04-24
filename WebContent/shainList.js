function showAllShain() {
	console.log("全社員表示");
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainSearchServlet',
		dataType : 'json',
		data : {
			shainId : '*'
		},
		success : function(data) {
			console.log("success");
			console.log(data);
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

// function shainSearch(){
// var inputBushoName = $('#inputBushoName').val();
// console.log(inputBushoName);
// var inputShainId = $('#inputShainId').val();
// console.log(inputShainId);
// var inputShainName = $('#inputShainName').val();
// console.log(inputShainName);
// $.ajax({
// Type:'GET',
// url : '/kisoTeichaku/ShainSearchServlet',
// dataType : 'json',
// data : {shainId:inputShainId},
// success:function(data){
// console.log(data);
// },
// error:function(){
// alert('データの通信に失敗しました');
// },
// });
// }

$(document).ready(function() {
	showAllShain();

	// $('#searchButton').click(shainSearch);
});