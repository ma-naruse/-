function showAllExpense() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ExpenseListUpServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			if (data == null) {
				$('#expenseList').append("ログインしてください。");
			} else {
				$('#expenseList').append('<tr><th>経費ID</th><th>記入者</th><th>項目名</th><th>金額</th><th>日付</th><th>状態</th><th>更新日</th><th>更新者</th></tr>')
				var loginId = data.loginId;
				console.log(loginId);
				var role = data.role;
				console.log(role);
				for (var i = 0; i < data.expenseList.length; i++) {
					var expense = data.expenseList[i];
					console.log(expense);
					var tableStr = '';
					tableStr += '<tr>';
					tableStr += '<td>' + expense.expenseId + '</td>';
					tableStr += '<td>' + expense.inputUserName + '</td>';
					tableStr += '<td>' + expense.itemName + '</td>';
					tableStr += '<td>' + expense.price + '</td>';
					tableStr += '<td>' + expense.date + '</td>';
					tableStr += '<td>' + expense.status + '</td>';
					tableStr += '<td>' + expense.updateDate + '</td>';
					tableStr += '<td>' + expense.updateUser + '</td>';
					if (data.role = 'manager' || loginId == expense.inputUseId) {
						tableStr += '<td><button value="' + expense.expenseId + '"onclick="jumpToEditPage(this)">編集</button></td>';
					}
					if (data.role == 'manager') {
						tableStr += '<td><button value="' + expense.expenseId + '"onclick="deleteExpense(this)">削除</button></td>';
					}
					tableStr += '</tr>';
					$('#expenseList').append(tableStr);
				}
				var str = '';
				str += '<button onclick="jumpToAddPage()">新規申請</button><br>';
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
	location.href = "./expenseEdit.html?=" + query;
}

function jumpToAddPage() {
	location.href = '/kisoTeichaku/expenseAdd.html';
}

function deleteExpense(button) {
	if (window.confirm('削除してよろしいですか？')) {
		var q = $(button).val();
		$.ajax({
			type : 'POST',
			url : '/kisoTeichaku/ExpenseDeleteServlet',
			data : {
				expenseId : q
			},
			success : function() {
				alert('削除が完了しました');
				$('#expenseList').html('');
				showAllExpense();
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
	showAllExpense();

});