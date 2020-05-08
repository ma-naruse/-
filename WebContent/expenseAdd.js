function countExpense() {
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ExpenseListUpServlet',
		dataType : 'json',
		success : function(data) {
			var ret = ('000' + (data.expenseList.length + 1)).slice(-4);
			$('#nextExpenseId').html(ret);
			$('#addExpenseId').val(ret);
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function addExpense() {
	var expenseId = $('#addExpenseId').val();
	var itemName = $('#addExpenseItemName').val();
	var price = $('#addExpensePrice').val();
	var requestQuery = {
		expenseId : expenseId,
		itemName : itemName,
		price : price,
		status : '未承認',
	}
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/ExpenseAddServlet',
		data : requestQuery,
		success : function() {
			alert('追加が完了しました。');
			location.href = './expenseList.html';
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function cancel() {
	location.href = './expenseList.html';
}

$(document).ready(function() {
	countExpense();
	$('#addButton').click(addExpense);
	$('#cancelButton').click(cancel);
});