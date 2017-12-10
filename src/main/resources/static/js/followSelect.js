$(document).ready(function() {
	$(".isFollowed").change(function (){
		var uname = $('#hidden').val();
		var userId = $(this).parent().find(".userId").val();
	    if( $(this).is(':checked') ){
	    	$.ajax({
	            url: "/followUser",
	            type: "POST",
	            data: {username : uname, userId : userId},
	            success: function () {
	            		alert("Followed User");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	    }else{
	    	$.ajax({
	            url: "/unfollowUser",
	            type: "POST",
	            data: {username : uname, userId : userId},
	            success: function () {
	            		alert("Unfollowed User");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	    }
	});
	$('.followCheck').each(function(){
		var followCheck = $(this).val();
		if(followCheck == 'Y'){
			$(this).parent().find("#isFollowed").prop('checked',true);
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