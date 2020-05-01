/**
 * 
 */
function check() {
	var inputLoginId = $('#loginId').val();
	console.log(inputLoginId);
	var inputPassword = $('#loginPass').val();
	console.log(inputPassword);
	if (inputLoginId == "manager" && inputPassword == "manager") {
		$.ajax({
			type : 'get',
			url : '/kisoTeichaku/LoginServlet',
			dataType : 'json',
			data : {
				loginRequest : 'login'
			},
			success : function(data) {
				location.href = "/kisoTeichaku/shainList.html";
			},
			error : function() {
				alert('データの通信に失敗しました。');
			}
		});
	} else {
		alert('IDもしくはパスワードが間違っています');

	}
}

$(document).ready(function() {
	$('#loginBtn').click(check);
});