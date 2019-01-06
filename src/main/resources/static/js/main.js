$(document).ready(function() {
	$('#consoleTestBtn').on('click', function(event) {
		console.log($('#myInput').val());
	});

	$('#testBtn').on('click', function(event) {
        event.preventDefault();
        console.log('111' + 2);
		$.get("/register/showRegistrationForm").done(function() {
		    alert("AAA");
		});
	});

    $('.myTableRow').on('click', function(event) {
                            // event.preventDefault();
                            console.log($(this).attr('entryIndex'));
                    	});

});