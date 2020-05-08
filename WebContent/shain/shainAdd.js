function countShain() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainAllListupServlet',
		dataType : 'json',
		success : function(data) {
			var ret = ('000' + (data.shainList.length + 1)).slice(-4);
			$('#nextShainId').html(ret);
			$('#addShainId').val(ret);
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function listUpBushoName() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			$('#addBushoName').append('<option value="">選択してください</option>');
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				$('#addBushoName').append('<option>' + busho.bushoName + '</option>');
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
			$('#addShainPrefecture').append('<option value="">選択してください</option>');
			for (var i = 0; i < data.length; i++) {
				var pref = data[i];
				$('#addShainPrefecture').append('<option>' + pref.name + '</option>');
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
			location.href = './shainList.html';
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function cancel() {
	alert('入力をキャンセルしました。');
	location.href = './shainList.html';
}

$(document).ready(function() {
	countShain();
	listUpBushoName();
	listUpPrefecture();
	$('#addButton').click(addShain);
	$('#cancelButton').click(cancel);
});