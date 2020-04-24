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
			for (var i = 0; i < data.length; i++) {
				var shain = data[i];
				$('#shainList').append(
						"<tr><td>" + shain.shainId + "</td><td>"
								+ shain.shainName + "</td></tr>");
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function shainSearch() {
	var inputBushoName = $('#inputBushoName').val();
	console.log(inputBushoName);
	var inputShainId = $('#inputShainId').val();
	console.log(inputShainId);
	var inputShainName = $('#inputShainName').val();
	console.log(inputShainName);
	$.ajax({
		Type : 'GET',
		url : '/kisoTeichaku/ShainSearchServlet',
		dataType : 'json',
		data : {
			shainId : inputShainId,
			shainName : inputShainName,
			bushoName : inputBushoName
		},
		success : function(data) {
			console.log(data);
			$('#shainList').html("");
			if(data[0] == undefined){
				$('#shainList').html("該当する社員はいませんでした。");
			}else{
				for (var i = 0; i < data.length; i++) {
					var shain = data[i];
					$('#shainList').append("<tr><td>" + shain.shainId + "</td><td>"
											+ shain.shainName + "</td><td>"+shain.bushoName+"</td></tr>");
				}
			}	
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

$(document).ready(function() {
	showAllShain();

	$('#searchButton').click(shainSearch);
});