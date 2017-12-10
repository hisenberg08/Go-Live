$(document).ready(function() {
	$(".isLiked").change(function (){
		var uname = $('#hidden').val();
		var artistId = $(this).parent().find(".artistId").val();
	    if( $(this).is(':checked') ){
	    	$.ajax({
	            url: "/addLikeArtist",
	            type: "POST",
	            data: {username : uname, artistId : artistId},
	            success: function () {
	            		alert("Liked artist");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	    }else{
	    	$.ajax({
	            url: "/removeLikeArtist",
	            type: "POST",
	            data: {username : uname, artistId : artistId},
	            success: function () {
	            		alert("Removed like for Artist");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	    }
	});
	$('.likedCheck').each(function(){
		var likedCheck = $(this).val();
		if(likedCheck == 'Y'){
			$(this).parent().find("#isLiked").prop('checked',true);
		}
	});
	$(":checkbox").parent().click(function(evt) {
	    if (evt.target.type !== 'checkbox') {
	        var $checkbox = $(":checkbox", this);
	        $checkbox.attr('checked', !$checkbox.attr('checked'));
	        $checkbox.change();
	    }
	});
});	