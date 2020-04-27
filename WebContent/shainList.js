function showAllShain() {
	console.log('全社員表示');
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			$('#shainList').append('<tr><th>社員ID</th><th>社員名</th></tr>')
			for (var i = 0; i < data.length; i++) {
				var shain = data[i];
				$('#shainList').append(
						'<tr><td>' + shain.shainId + '</td><td>'
								+ shain.shainName + '</td><td>'
								+ '<button value="' + shain.shainId
								+ '"onclick="jumpToEditPage(this)">編集</button>'
								+ '</td><td>' + '<button value="'
								+ shain.shainId
								+ '" onclick="deleteShain(this)">削除</button>'
								+ '</td></tr>');
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function jumpToEditPage(button) {
	var query = $(button).val();
	location.href = "./shainEdit.html?=" + query;
}

function deleteShain(button) {
	var q = $(button).val();
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/ShainDeleteServlet',
		data : {
			shainId : q
		},
		success : function() {
			alert('削除が完了しました');
			$('#shainList').html('');
			showAllShain();
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {
	showAllShain();
});