function showBushoInfo() {
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];
	console.log(parameter);
	$.ajax({
		Type : 'GET',
		url : '/kisoTeichaku/BushoEditServlet',
		dataType : 'json',
		data : {
			bushoId : parameter
		},
		success : function(data) {
			console.log(data);
			$('#bushoInfo').html("");
			if (data[0] == undefined) {
				alert('該当する部署は存在しません');
			} else {
				for (var i = 0; i < data.length; i++) {
					var busho = data[i];
					$('#bushoInfo').append(
							'<div>部署ID:<span>' + busho.bushoId
									+ '</span><input type="hidden"value="'
									+ busho.bushoId
									+ '"id="editBushoId"></input><br>'
									+ '部署名<input type="text" value='
									+ busho.bushoName
									+ ' id="editBushoName"></input><br>'
									+ '</div>');
				}
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}
function editBushoInfo() {
	var bushoId = $('#editBushoId').val();
	var bushoName = $('#editBushoName').val();
	var requestQuery = {
		bushoId : bushoId,
		bushoName : bushoName
	}

	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/BushoEditServlet',
		data : requestQuery,
		success : function() {
			alert('編集が完了しました。');
			location.href = './bushoList.html'
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function cancel() {
	alert('入力をキャンセルしました。');
	location.href = './bushoList.html';
}

$(document).ready(function() {
	showBushoInfo();
	$('#editButton').click(editBushoInfo);
	$('#cancelButton').click(cancel);
});
