function login() {
	var inputLoginId = $('#loginId').val();
	console.log(inputLoginId);
	var inputPassword = $('#loginPass').val();
	console.log(inputPassword);
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/LoginServlet',
		dataType : 'json',
		data : {
			loginId : inputLoginId,
			password : inputPassword
		},
		success : function(data) {
			console.log(data);
			if (data.role != null) {
				location.href = "/kisoTeichaku/shainList.html";
			} else {
				alert('IDもしくはパスワードが間違っています');
			}
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});

}

function logout() {
	$.ajax({
		type : 'POST',
		url : '/kisoTeichaku/LoginServlet',
		dataType : 'json',
		data : {
			loginRequest : 'logout'
		},
		success : function() {
			console.log('ログアウトしました。');
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {
	$('#loginBtn').click(login);
	$('#logoutBtn').click(logout);

	$('#managerLoginBtn').click(managerLogin);
	$('#memberLoginBtn').click(memberLogin);
});

function managerLogin() {
	$.ajax({
		url : '/kisoTeichaku/LoginServlet',
		type : 'POST',
		data : {
			loginId : '0001',
			password : '0001'
		},
		success : function() {
			console.log('マネージャーログイン');
			location.href = "/kisoTeichaku/shainList.html";
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}

function memberLogin() {
	$.ajax({
		url : '/kisoTeichaku/LoginServlet',
		type : 'POST',
		data : {
			loginId : '0002',
			password : '0002'
		},
		success : function() {
			console.log('メンバーログイン');
			location.href = "/kisoTeichaku/shainList.html";
		},
		error : function() {
			alert('データの通信に失敗しました。');
		}
	});
}