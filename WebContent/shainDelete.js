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

$(document).ready(function(){
	$('#deleteButton').click(deleteShain);
});