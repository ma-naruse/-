/*function shainSearch() {
	var inputBushoName = $('#inputBushoName').val();
	console.log(inputBushoName);
	var inputShainId = $('#inputShainId').val();
	console.log(inputShainId);
	var inputShainName = $('#inputShainName').val();
	console.log(inputShainName);
	$
			.ajax({
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
					$('#shainInfo').html("");
					if (data[0] == undefined) {
						$('#shainInfo').html("該当する社員はいませんでした。");
					} else {
						$('#shainInfo')
								.append(
										"<tr><th>社員ID</th><th>社員名</th><th>年齢</th><th>性別</th><th>郵便番号</th><th>都道府県</th><th>住所</th><th>所属部署</th></tr>");
						for (var i = 0; i < data.length; i++) {
							var shain = data[i];
							$('#shainInfo').append(
									"<tr><td>" + shain.shainId + "</td><td>"
											+ shain.shainName + "</td><td>"
											+ shain.shainAge + "</td><td>"
											+ shain.shainSex + "</td><td>"
											+ shain.shainPostCd + "</td><td>"
											+ shain.shainPrefecture
											+ "</td><td>" + shain.shainAddress
											+ "</td><td>" + shain.bushoName
											+ "</td></tr>");
						}
					}
				},
				error : function() {
					alert('データの通信に失敗しました');
				},
			});
}*/

function shainShow() {
	var inputEditId = $('#editShainId').val();
	console.log(inputShainId);
	$
			.ajax({
				Type : 'GET',
				url : '/kisoTeichaku/ShainEditServlet',
				dataType : 'json',
				data : {
					shainId : inputEditId
				},
				success : function(data) {
					console.log(data);
					$('#shainInfo').html("");
					if (data[0] == undefined) {
						alert('該当する社員は存在しません');
					} else {
						$('#shainInfo')
								.append(
										"<tr><th>社員ID</th><th>社員名</th><th>年齢</th><th>性別</th><th>郵便番号</th><th>都道府県</th><th>住所</th><th>所属部署</th></tr>");
						for (var i = 0; i < data.length; i++) {
							var shain = data[i];
							$('#shainInfo').append(
									"<tr><td>" + shain.shainId + "</td><td>"
											+ shain.shainName + "</td><td>"
											+ shain.shainAge + "</td><td>"
											+ shain.shainSex + "</td><td>"
											+ shain.shainPostCd + "</td><td>"
											+ shain.shainPrefecture
											+ "</td><td>" + shain.shainAddress
											+ "</td><td>" + shain.bushoName
											+ "</td></tr>");
						}
					}
				},
				error : function() {
					alert('データの通信に失敗しました');
				},
			});
}

$(document).ready(function() {

	// $('#searchButton').click(shainSearch);
	$('#editShainIdButton').click(shainShow);

});
