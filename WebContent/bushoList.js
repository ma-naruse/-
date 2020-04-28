function showAllBusho(){
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/BushoAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			console.log(data.length);
			$('#bushoList').html('');
			$('#bushoList').append('<tr><th>部署ID</th><th>部署名</th></tr>');
			for (var i = 0; i < data.length; i++) {
				var busho = data[i];
				$('#bushoList').append('<tr><td>' + busho.bushoId + '</td><td>'+ busho.bushoName + '</td><td><button value="' + busho.bushoId + '"onclick="editBusho(this)">編集</button></td><td><button value="' + busho.bushoId + '" onclick="deleteBusho(this)">削除</button></td></tr>');
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function editBusho(button){
	var query = $(button).val();
	location.href = "./bushoEdit.html?="+query;
}

function deleteBusho(button){
	if (window.confirm('削除してよろしいですか？')) { // 確認ダイアログを表示
		var query = button.value;
		console.log(query);
		$.ajax({
			type:'GET',
			url:'/kisoTeichaku/BushoDeleteServlet',
			data : {
				bushoId : query
			},
			success:function(){
				alert('削除が完了しました。');
				showAllBusho();
			},
			error:function(){
				alert('削除できませんでした。');
			}
		});
		return true; // 「OK」時は送信を実行
	} else { // 「キャンセル」時の処理
		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止
	}
}

function bushoAdd(){
	var addbushoId = $('#inputAddBushoId').val();
	var addbushoName = $('#inputAddBushoName').val();
	var requestQuery = {
			bushoId : addbushoId,
			bushoName : addbushoName
	};
	$.ajax({
		type:'GET',
		url:'/kisoTeichaku/BushoAddServlet',
		data:requestQuery,
		success:function(){
			alert("登録に成功しました。");
			$('#bushoList').html('');
			showAllBusho();
		},
		error(){
			alert('データの通信に失敗しました');
		}
	});
}

$(document).ready(function() {
	showAllBusho();
	$('#bushoAddButton').click(bushoAdd);
});