function showAllShain() {
	console.log('全社員表示');
	$.ajax({
		type : 'GET',
		url : '/kisoTeichaku/ShainAllListupServlet',
		dataType : 'json',
		success : function(data) {
			console.log(data);
			$('#shainList').append('<tr><th>社員ID</th><th>社員名</th></tr>');
			for (var i = 0; i < data.length; i++) {
				var shain = data[i];
				$('#shainList').append(
						'<tr><td =id="shainId'+i+'">' + shain.shainId + '</td><td id="shain'+i+'">'
								+ shain.shainName + '</td></tr>');
			}
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function searchShain() {
	var inputSearchBushoName = $('#inputSearchBushoName').val();
	console.log(inputSearchBushoName);
	var inputSearchShainId = $('#inputSearchShainId').val();
	console.log(inputSearchShainId);
	var inputSearchShainName = $('#inputSearchShainName').val();
	console.log(inputSearchShainName);
	$.ajax({
		Type : 'GET',
		url : '/kisoTeichaku/ShainSearchServlet',
		dataType : 'json',
		data : {
			shainId : inputSearchShainId,
			shainName : inputSearchShainName,
			bushoName : inputSearchBushoName
		},
		success : function(data) {
			console.log(data);
			$('#shainList').html("");
			if(data[0] == undefined){
				$('#shainList').html('該当する社員はいませんでした。');
			}else{
				$('#shainList').append('<tr><th>社員ID</th><th>社員名</th><th>所属部署</th></tr>');
				for (var i = 0; i < data.length; i++) {
					var shain = data[i];
					$('#shainList').append('<tr><td>' + shain.shainId + '</td><td>'
											+ shain.shainName + '</td><td>'+shain.bushoName+'</td></tr>');
				}
			}	
		},
		error : function() {
			alert('データの通信に失敗しました');
		},
	});
}

function deleteShain(){
	var deleteShainId = $('#deleteShainId').val();
	console.log(deleteShainId);
	
	$.ajax({
		type:'POST',
		url:'/kisoTeichaku/ShainDeleteServlet',
		data:{shainId : deleteShainId},
		success:function(){
			alert('削除が完了しました');
			$('#shainList').html('');
			showAllShain();
		},
		error:function(){
			alert('データの通信に失敗しました。');
		}
	});
}

$(document).ready(function() {
	showAllShain();

	$('#searchButton').click(searchShain);
	$('#deleteButton').click(deleteShain);
	
});