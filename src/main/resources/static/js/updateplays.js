$(document).ready(function() {
		
	$(".trackPlayed").click(function(){
		
		var source =  $('#source').val();
		var user =  $('#user').val();
		var trackId =  $(this).parent().parent().find('#trackid').val();
		var tracktitle =  $(this).parent().parent().find('#tracktitle').val();
		var playlistId = $('#playlistId').val(); 
		
		 $.ajax({
	            url: "/insertPlayData",
	            type: "POST",
	            data: {source : source, user:user, trackId:trackId, playlistId:playlistId},
	            success: function (response) {
	            	if(!response.trim() == '') 
	            		alert('Song: ' +tracktitle+ ' \nPlayed from REST Endpoint URL: \n' +response);
	            	else
	            		alert("Error occurred. Please try again");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
});
