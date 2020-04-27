function addShain() {
	var shainId = $('#addShainId').val();
	var shainName = $('#addShainName').val();
	var shainAge = $('#addShainAge').val();
	var shainSex = $('#addShainSex').val();
	var shainPostCd = $('#addShainPostCd').val();
	var shainPrefecture = $('#addShainPrefecture').val();
	var shainAddress = $('#addShainAddress').val();
	var bushoName = $('#addBushoName').val();
	console.log(shainId);
	console.log(shainName);
	console.log(shainSex);
	var requestQuery = {
		shainId : shainId,
		shainName : shainName,
		shainAge : shainAge,
		shainSex : shainSex,
		shainPostCd : shainPostCd,
		shainPrefecture : shainPrefecture,
		shainAddress : shainAddress,
		bushoName : bushoName
	}
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/ShainAddServlet',
		data : requestQuery,
		success : function() {
			alert('追加が完了しました。');
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {
	
	$('#addButton').click(addShain);
});