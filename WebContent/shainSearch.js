function showAllShain() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainAllListupServlet',
		dataType : 'json',
		success : function(data) {
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
	var inputSearchBushoName = $('#inputSearchBushoName').val();
	console.log(inputSearchBushoName);
	var inputSearchShainId = $('#inputSearchShainId').val();
	console.log(inputSearchShainId);
	var inputSearchShainName = $('#inputSearchShainName').val();
	console.log(inputSearchShainName);
	$.ajax({
		Type : 'GET',
		url : '/kisoTeichaku/ShainSearchServlet',
		dataType : 'json',
		data : {
			shainId : inputSearchShainId,
			shainName : inputSearchShainName,
			bushoName : inputSearchBushoName
		},
		success : function(data) {
			console.log(data);
			$('#shainList').html("");
			if(data[0] == undefined){
				$('#shainList').html("該当する社員はいませんでした。");
			}else{
				$('#shainList').append("<tr><th>社員ID</th><th>社員名</th><th>所属部署</th></tr>");
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