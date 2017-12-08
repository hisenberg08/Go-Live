$(document).ready(function() {
	$(".rating").change(function (){
	    var rate = $(this).val();
	    var uname = $('#hidden').val();
	    var trackId = $(this).closest('tr').find("#trackIdtest1").val();
	    $.ajax({
	            url: "/rateTrack",
	            type: "POST",
	            data: {username : uname, rating : rate, trackId : trackId},
	            success: function () {
	                alert("Rating successful");
	            },
	            error: function () {
	                alert("Error occurred. Please try again");
	            }
	        });
	});
});
