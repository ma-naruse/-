function showShainInfo() {
	var inputShainId = $('#shainIdToPic').val();
	console.log(inputShainId);
	$.ajax({
				Type : 'GET',
				url : '/kisoTeichaku/ShainEditServlet',
				dataType : 'json',
				data : {
					shainId : inputShainId
				},
				success : function(data) {
					console.log(data);
					$('#shainInfo').html("");
					if (data[0] == undefined) {
						alert('該当する社員は存在しません');
					} else {
						for (var i = 0; i < data.length; i++) {
							var shain = data[i];
							$('#shainInfo')
									.append(
											'<div>社員ID:<span>'
													+ shain.shainId
													+ '</span><input type="hidden"value="'+shain.shainId+'"id="editShainId"></input><br>'
													+ '社員名<input type="text" value='
													+ shain.shainName
													+ ' id="editShainName"></input><br>'
													+ '年齢<input type="text" value='
													+ shain.shainAge
													+ ' id="editShainAge"></input><br>'
													+ '性別<input type="text" value='
													+ shain.shainSex
													+ ' id="editShainSex"></input><br>'
													+ '郵便番号<input type="text" value='
													+ shain.shainPostCd
													+ ' id="editShainPostCd"></input><br>'
													+ '居住都道府県<input type="text" value='
													+ shain.shainPrefecture
													+ ' id="editShainPrefecture"></input><br>'
													+ '住所<input type="text" value='
													+ shain.shainAddress
													+ ' id="editShainAddress"></input><br>'
													+ '所属部署<input type="text" value='
													+ shain.bushoName
													+ ' id="editBushoName"></input>'
													+ '</div>');
						}
					}
				},
				error : function() {
					alert('データの通信に失敗しました');
				},
			});
}

function editShainInfo(){
	var shainId = $('#editShainId').val();
	var shainName = $('#editShainName').val();
	var shainAge = $('#editShainAge').val();
	var shainSex = $('#editShainSex').val();
	var shainPostCd = $('#editShainPostCd').val();
	var shainPrefecture = $('#editShainPrefecture').val();
	var shainAddress = $('#editShainAddress').val();
	var bushoName = $('#editBushoName').val();
	console.log(shainId);
	console.log(shainName);
	console.log(shainSex);
	var requestQuery = {
			shainId : shainId,
			shainName: shainName,
			shainAge : shainAge,
			shainSex : shainSex,
			shainPostCd : shainPostCd,
			shainPrefecture : shainPrefecture,
			shainAddress : shainAddress,
			bushoName : bushoName
	}
	
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/ShainEditServlet',
		data : requestQuery,
		success : function(){
			alert('編集が完了しました。');
		},
		error:function(){
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {

	$('#showShainIdButton').click(showShainInfo);
	$('#editButton').click(editShainInfo);

});
