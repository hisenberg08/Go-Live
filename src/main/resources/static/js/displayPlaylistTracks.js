$(document).ready(function() {
	
	var username =  $('#user').val();
	var playlistowner = $('#owner').val();
	
	if(playlistowner!= "you"){
		$(".deleteColumn").remove();
		console.log("in here");
	}
	
	$("button").click(function(){
		 var playlistId = $('#playlistId').val();
		 var user = $('#user').val();
		 var trackid = $(this).parent().siblings('#trackid').val();
		 $(this).attr('disabled',true);
		 $.ajax({
	            url: "/deleteTrackFromplaylist",
	            type: "POST",
	            data: {playlistId : playlistId,trackid : trackid,user : user},
	            success: function () {
	            	alert("track deleted from playlist sucessfully");
	                $(this).attr('disabled',true);
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
		 
	});
});
