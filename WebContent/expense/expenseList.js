function showAllExpense() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ExpenseListUpServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			if (data == null) {
				$('#expenseList').append("ログインしてください");
			} else {
				$('#expenseList').html('');
				$('#expenseList').append(
						'<tr><th>経費ID</th><th>記入者</th><th>項目名</th><th>金額</th><th>日付</th><th>状態</th><th>備考</th><th>更新日</th><th>更新者</th></tr>')
				var loginId = data.loginId;
				console.log(loginId);
				var role = data.role;
				console.log(role);
				for (var i = 0; i < data.expenseList.length; i++) {
					var expense = data.expenseList[i];
					console.log(expense);
					var tableStr = '';
					tableStr += '<tr>';
					if (role == 'manager' || expense.inputUserId == loginId) {
						tableStr += '<td>' + expense.expenseId + '</td>';
						tableStr += '<td>' + expense.inputUserName + '</td>';
						tableStr += '<td>' + expense.itemName + '</td>';
						tableStr += '<td>' + expense.price + '</td>';
						tableStr += '<td>' + expense.date + '</td>';
						tableStr += '<td>' + expense.status + '</td>';
						if (expense.description == null) {
							tableStr += '<td>' + "" + '</td>';
						} else {
							tableStr += '<td>' + expense.description + '</td>';
						}
						if (expense.updateDate == null) {
							tableStr += '<td>' + "" + '</td>';
						} else {
							tableStr += '<td>' + expense.updateDate + '</td>';
						}
						if (expense.updateUser == null) {
							tableStr += '<td>' + "" + '</td>';
						} else {
							tableStr += '<td>' + expense.updateUser + '</td>';
						}
						if (role == 'manager') {
							tableStr += '<td><button value="' + expense.expenseId + '"onclick="approve(this)">承認</button></td>';
							tableStr += '<td><button value="' + expense.expenseId + '"onclick="reject(this)">却下</button></td>';
						}
					}
					tableStr += '</tr>';
					$('#expenseList').append(tableStr);
				}
				var str = '';
				if (role == 'manager') {
					str += '却下理由<input type="text" value="" id="reasonOfRejection"></input><br>';
				}
				str += '<button onclick="jumpToAddPage()">新規申請</button><br>';
				str += '<button onclick="logout()">ログアウト</button>';
				$('#button').html('');
				$('#button').append(str);
			}

		},
		error : function() {
			alert('データの通信に失敗しました');
		}
	});
}

function approve(button) {
	var query = $(button).val();
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/ExpenseExamServlet',
		dataType : 'json',
		data : {
			expenseId : query,
			status : '承認済'
		},
		success : function(data) {
			alert('承認されました');
			showAllExpense();
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function reject(button) {
	var query = $(button).val();
	if ($('#reasonOfRejection').val() == "") {
		alert('却下理由は必須です。');
	} else {
		var reason = $('#reasonOfRejection').val();
		$.ajax({
			type : 'POST',
			url : '/kisoTeichaku/ExpenseExamServlet',
			dataType : 'json',
			data : {
				expenseId : query,
				status : '却下',
				reason : reason
			},
			success : function(data) {
				alert('却下しました');
				showAllExpense();
			},
			error : function() {
				alert('データの通信に失敗しました。');
			}
		});
	}
}

function jumpToAddPage() {
	location.href = './expenseAdd.html';
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