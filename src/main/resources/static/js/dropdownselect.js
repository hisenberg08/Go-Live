$(document).ready(function() {
	$(".rating").change(function (){
	    var rate = $(this).val();
	    var uname = $('#hidden').val();
	    var trackId = $(this).closest('tr').find("#trackIdtest1").val();
	    $.ajax({
	            url: "/rateTrack",
	            type: "POST",
	            data: {username : uname, rating : rate, trackId : trackId},
	            success: function (res) {
	            	if(res == 2){
	            		alert("Please select a rating");	
	            	}else{
	            		alert("Rating successful");
	            	}
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
	$(".addTrack").change(function (){
	    var playListId = $(this).val();
	    var trackId = $(this).closest('tr').find("#trackIdtest1").val();
	    $.ajax({
	            url: "/insertTrack",
	            type: "POST",
	            data: {playListId : playListId, trackId : trackId},
	            success: function (res) {
	            	if (res==1){
	                alert("Track added to the Selected Playlist");
	            	}else if(res==2){
	            		alert("Please select a Playlist");
	            	}
	            	else{
	            		alert("Track already exists in this Playlist");
	            	}
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
});
