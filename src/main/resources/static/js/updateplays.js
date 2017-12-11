$(document).ready(function() {
		
	$(".trackPlayed").click(function(){
		
		var source =  $('#source').val();
		var user =  $('#user').val();
		var trackId =  $(this).parent().parent().find('#trackid').val();
		var playlistId = $('#playlistId').val(); 
		
		 $.ajax({
	            url: "/insertPlayData",
	            type: "POST",
	            data: {source : source, user:user, trackId:trackId, playlistId:playlistId},
	            success: function (response) {
	            	if(response == 1) 
	            		alert('song played');
	            	else
	            		alert("Error occurred. Please try again");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
});
