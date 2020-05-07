function showAllShain() {
	console.log('全社員表示');
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			if (data == null) {
				$('#shainList').append("ログインしてください。");
			} else {
				$('#shainList').append('<tr><th>社員ID</th><th>社員名</th><th>部署名</th></tr>')
				var loginId = data.loginId;
				console.log(loginId);
				sessionStorage.setItem('loginId', loginId);
				var role = data.role;
				console.log(role);
				sessionStorage.setItem('role', role);
				for (var i = 0; i < data.shainList.length; i++) {
					var shain = data.shainList[i];
					console.log(shain);
					var tableStr = '';
					tableStr += '<tr>';
					tableStr += '<td>' + shain.shainId + '</td>';
					tableStr += '<td>' + shain.shainName + '</td>';
					tableStr += '<td>' + shain.bushoName + '</td>';
					if (role == 'manager' || loginId == shain.shainId) {
						tableStr += '<td><button value="' + shain.shainId + '"onclick="jumpToEditPage(this)">編集</button></td>';
					}
					if (role == 'manager') {
						tableStr += '<td><button value="' + shain.shainId + '" onclick="deleteShain(this)">削除</button></td>';
					}
					tableStr += '</tr>';
					$('#shainList').append(tableStr);
				}
				var str = '';
				str += '<button onclick="jumpToAddPage()">新規追加</button><br>';
				str += '<button onclick="window.open().location.href=\'/kisoTeichaku/shainSearch.html\'">検索</button><br>';
				str += '<button onclick="logout()">ログアウト</button>'
				$('#button').append(str);
			}

		},
		error : function() {
			alert('データの通信に失敗しました');
		}
	});
}

function jumpToEditPage(button) {
	var query = $(button).val();
	location.href = "./shainEdit.html?=" + query;
}

function jumpToAddPage() {
	location.href = '/kisoTeichaku/shainAdd.html';
}

function deleteShain(button) {
	if (window.confirm('削除してよろしいですか？')) {
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
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

function logout() {
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/LoginServlet',
		dataType : 'json',
		data : {
			loginRequest : 'logout'
		},
		success : function(data) {
			alert('ログアウトしました。');
			location.href = '/kisoTeichaku/index.html';
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {
	showAllShain();
});