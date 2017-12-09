$(document).ready(function() {
		
	$(".deleteColumn").click(function(){
		
		var playlistId = $(this).parent().find('#playlistid').val();
		var username =  $('#user').val();
		$(this).find('#hidebtn').attr('disabled',true);
		 $.ajax({
	            url: "/deletePlaylist",
	            type: "POST",
	            data: {playlistId : playlistId},
	            success: function (response) {
	            	 alert('playlist deleted');
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
});
