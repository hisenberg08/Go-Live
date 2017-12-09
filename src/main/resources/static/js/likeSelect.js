$(document).ready(function() {
	$(".likeCheckBox").change(function (){
	    var isLiked = $(this).val();
	    var uname = $('#hidden').val();
	    var artistId = $('.artistId').val();
//	    alert('isLiked ='+isLiked);
//	    alert('uname ='+uname);
//	    alert('artistId ='+artistId);
	    /*$.ajax({
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
	        });*/
	});
	$('.likedCheck').each(function(){
//		var checkbox = $(this).parent().find("#isLiked").val();
		var likedCheck = $(this).val();
		if(likedCheck == 'Y'){
			$(this).parent().find("#isLiked").prop('checked',true);
		}
	});
});	