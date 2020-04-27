function showAllBusho(){
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			console.log(data.length);
			$('#bushoList').append('<tr><th>部署ID</th><th>部署名</th></tr>');
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				$('#bushoList').append('<tr><td>' + busho.bushoId + '</td><td>'+ busho.bushoName + '</td></tr>');
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function bushoAdd(){
	var addbushoId = $('#inputAddBushoId').val();
	var addbushoName = $('#inputAddBushoName').val();
	var requestQuery = {
			bushoId : addbushoId,
			bushoName : addbushoName
	};
	$.ajax({
		type:'GET',
		url:'/kisoTeichaku/BushoAddServlet',
		data:requestQuery,
		success:function(){
			alert("登録に成功しました。");
			$('#bushoList').html('');
			showAllBusho();
		},
		error(){
			alert('データの通信に失敗しました');
		}
	});
}

$(document).ready(function() {
	showAllBusho();
	
	$('#bushoAddButton').click(bushoAdd);
});