$(document).ready(function() {
	$(".rating").change(function (){
	    alert($(this).val());
	    var uname = $('#hidden').val();
	    $.ajax({
	            url: "/rateTrack",
	            type: "POST",
	            data: {username : uname},
	            success: function () {
	                alert("Success");
	            },
	            error: function () {
	                alert("Error");
	            }
	        });
	});
});
