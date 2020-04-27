function listUpBushoName() {
	$.ajax({
		typr : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			console.log(data.length);
			$('#addBushoName').append(
					'<option value="">選択してください</option>');
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				console.log(busho.bushoName);
				$('#addBushoName').append(
						'<option>' + busho.bushoName + '</option>');
			}
		},
		error : function() {
			alert("データの通信に失敗しました。")
		}
	});
}

function listUpPrefecture() {
	$.ajax({
		typr : 'GET',
		url : '/kisoTeichaku/GetPrefecture',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			console.log(data.length);
			$('#addShainPrefecture').append(
					'<option value="">選択してください</option>');
			for (var i = 0; i < data.length; i++) {
				var pref = data[i];
				console.log(pref.name);
				$('#addShainPrefecture').append(
						'<option>' + pref.name + '</option>');
			}
		},
		error : function() {
			alert("データの通信に失敗しました。")
		}
	});
}

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
	listUpBushoName();
	listUpPrefecture();
	$('#addButton').click(addShain);
});