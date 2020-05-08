function showShainInfo() {
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];
	console.log(parameter);
	$.ajax({
		Type : 'GET',
		url : '/kisoTeichaku/ShainEditServlet',
		dataType : 'json',
		data : {
			shainId : parameter
		},
		success : function(data) {
			console.log(data);
			$('#shainInfo').html("");
			if (data[0] == undefined) {
				alert('該当する社員は存在しません');
			} else {
				for (var i = 0; i < data.length; i++) {
					var shain = data[i];
					$('#shainInfo').append(
							'<div>社員ID:<span>' + shain.shainId + '</span><input type="hidden"value="' + shain.shainId
									+ '"id="editShainId"></input><br>' + '社員名<input type="text" value=' + shain.shainName
									+ ' id="editShainName"></input><br>' + '年齢<input type="text" value=' + shain.shainAge
									+ ' id="editShainAge"></input><br>' + '性別<input type="text" value=' + shain.shainSex
									+ ' id="editShainSex"></input><br>' + '郵便番号<input type="text" value=' + shain.shainPostCd
									+ ' id="editShainPostCd"></input><br>' + '居住都道府県<select id="editShainPrefecture"><option>'
									+ shain.shainPrefecture + '</option></select><br>' + '住所<input type="text" value=' + shain.shainAddress
									+ ' id="editShainAddress"></input><br>' + '所属部署<select id="editBushoName"><option>' + shain.bushoName
									+ '</option>' + '</div>');
				}
			}
			listUpPrefecture();
			listUpBushoName();
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}
function listUpBushoName() {
	$.ajax({
		typr : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				$('#editBushoName').append('<option>' + busho.bushoName + '</option>');
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
			for (var i = 0; i < data.length; i++) {
				var pref = data[i];
				$('#editShainPrefecture').append('<option>' + pref.name + '</option>');
			}
		},
		error : function() {
			alert("データの通信に失敗しました。")
		}
	});
}

function editShainInfo() {
	console.log("sousinndekita");
	var shainId = $('#editShainId').val();
	var shainName = $('#editShainName').val();
	var shainAge = $('#editShainAge').val();
	var shainSex = $('#editShainSex').val();
	var shainPostCd = $('#editShainPostCd').val();
	var shainPrefecture = $('#editShainPrefecture').val();
	var shainAddress = $('#editShainAddress').val();
	var bushoName = $('#editBushoName').val();
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
		url : '/kisoTeichaku/ShainEditServlet',
		data : requestQuery,
		success : function() {
			alert('編集が完了しました。');
			location.href = './shainList.html'
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function cancel() {
	location.href = './shainList.html';
}

$(document).ready(function() {
	showShainInfo();
	$('#editButton').click(editShainInfo);
	$('#cancelButton').click(cancel);
});
