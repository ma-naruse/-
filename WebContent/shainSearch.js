function listUpBushoName() {
	$.ajax({
		typr : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			console.log(data.length);
			$('#inputSearchBushoName').append(
					'<option value="">選択してください</option>');
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				console.log(busho.bushoName);
				$('#inputSearchBushoName').append(
						'<option>' + busho.bushoName + '</option>');
			}
		},
		error : function() {
			alert("データの通信に失敗しました。")
		}
	});
}

function searchShain() {
	var inputSearchBushoName = $('#inputSearchBushoName').val();
	console.log(inputSearchBushoName);
	var inputSearchShainId = $('#inputSearchShainId').val();
	console.log(inputSearchShainId);
	var inputSearchShainName = $('#inputSearchShainName').val();
	console.log(inputSearchShainName);
	var rq =  {
			shainId : inputSearchShainId,
			shainName : inputSearchShainName,
			bushoName : inputSearchBushoName
		};
	console.log(rq);
	$
			.ajax({
				Type : 'GET',
				url : '/kisoTeichaku/ShainSearchServlet',
				dataType : 'json',
				data : rq,
				success : function(data) {
					console.log(data);
					$('#shainInfo').html("");
					if (data[0] == undefined) {
						$('#shainInfo').html('該当する社員はいませんでした。');
					} else {
						$('#shainInfo')
								.append(
										'<tr><th>社員ID</th><th>社員名</th><th>年齢</th><th>性別</th><th>郵便番号</th><th>都道府県</th><th>住所</th><th>所属部署</th></tr>');
						for (var i = 0; i < data.length; i++) {
							var shain = data[i];
							$('#shainInfo').append(
									'<tr><td>' + shain.shainId + '</td><td>'
											+ shain.shainName + '</td><td>'
											+ shain.shainAge + '</td><td>'
											+ shain.shainSex + '</td><td>'
											+ shain.shainPostCd + '</td><td>'
											+ shain.shainPrefecture
											+ '</td><td>' + shain.shainAddress
											+ '</td><td>' + shain.bushoName
											+ '</td></tr>');
						}
					}
				},
				error : function() {
					alert('データの通信に失敗しました');
				},
			});

}
$(document).ready(function() {
	listUpBushoName();
	$('#searchButton').click(searchShain);
});