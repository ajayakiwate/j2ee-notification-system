/**
 * 
 */
var regex_emailid = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/
	
var regex_mobile_number = /^[2-9]\d{9}$/;

$(document).ready(function() {
	
	$('.navbar-nav>li>a').on('click', function(){
		$('.navbar-collapse').collapse('hide');
	});
	
	$("#home").click(function(){
		$("#login").click();
	});
	
	

	//Handle Login Form 
	$('#form1').on('submit', function(e){
		e.preventDefault();
		
        var emailid= document.forms["form1"]["emailid"].value;
		var password= document.forms["form1"]["password"].value;
		
		var message="";
		
		if(!emailid.match(regex_emailid)){
			message+="Please enter Valid Email-ID \n";
		}
		
		if(message.length == 0){
			
			$.ajax({ 
				async: true,
				type: "POST",
				url: 'VerifyLogin',
				data: $(this).serialize(),// or fData
				success: function(response){
					if(response['success']){
						
					}
					$('#form1')[0].reset();
					$("#myModal1").modal("hide");
					
					//calling login link click event
					$("#login").click();
			   	}
		   	});

		}
		else{
			alert(message);
		}
		
	});
	
	
	//After clicking Login Link
	$('#login').click(function(){
		//console.log("User View Clicked");
		$.ajax({ 
			async: true,
			type: "POST",
			url: 'VerifyUser',
			success: function(response){
				alert(JSON.stringify(response));
				var jsonData = response;
				if(jsonData.success){
					alert("Login Success");		

					$("#login").parent().addClass("d-none");
					$("#logout").parent().removeClass("d-none");
					
					if(jsonData['message']['admin']){
						$("#CM").parent().removeClass("d-none");
					}
					
					$.ajax({ 
						async: true,
						type: "POST",
						url: "M_Data.jsp",
						success: function(response1){
							//console.log(response1);
							$("#main").html(response1);
					   }
				   	});
					   
				}
				else{
					$("#myModal1").modal("show");
				}
			}
		});	
	});
	
	
	//After Clicking Logout
	$('#logout').click(function(){
		//console.log("User View Clicked");
		$.ajax({ 
			async: true,
			type: "POST",
			url: 'Logout',
			success: function(response1){
				alert(response1);
				$("#logout").parent().addClass("d-none");
				$("#login").parent().removeClass("d-none");
				$("#CM").parent().addClass("d-none");
				
				$("#main").html("");
				
				//calling login link click event
				$("#login").click();
		   }
	   	});
	   
	});
	
	//Handle Create Message
	$('#formCreateMessage').on('submit', function(e){
		e.preventDefault();
	
		$.ajax({ 
			async: true,
			type: "POST",
			url: 'CreateMessage',
			data: $(this).serialize(),// or fData
			success: function(response){
				
				if(response['success']){
					alert("New Message Created");
				}
				else{
					alert("Unsuccessfull");
				}
				
				$('#formCreateMessage')[0].reset();
				$("#modalCreateMessage").modal("hide");
				
				$.ajax({ 
					async: true,
					type: "POST",
					url: "M_Data.jsp",
					success: function(response1){
						//console.log(response1);
						$("#main").html(response1);
				   }
				});
				
		   	}
	   	});

	});
	
	var regex_letters = /^[A-Za-z]+$/;
	//min 8 max 15 one special character one digit one uppercase and one lowercase
	//var regex_password=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
	//var regex_emailid = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/; 
	var regex_emailid = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/
	
	var regex_mobile_number = /^[2-9]\d{9}$/;
	
	
	$('#idFormCreateUser').on('submit', function(e){
		e.preventDefault();
		
        var firstname= document.forms["createUserForm"]["firstname"].value;
		var lastname= document.forms["createUserForm"]["lastname"].value;
		var emailid= document.forms["createUserForm"]["emailid"].value;
		var mobile_number= document.forms["createUserForm"]["mobilenumber"].value;
		var dob= document.forms["createUserForm"]["dob"].value;
		var password1 = document.forms["createUserForm"]["password1"].value;
		var password2= document.forms["createUserForm"]["password2"].value;
		
		var message="";
		
		if(!firstname.match(regex_letters)){
			message+="Please enter only letters in first name \n";
		}
		
		if(!lastname.match(regex_letters)){
			message+="Please enter only letters in last name \n";
		}
		
		if(!emailid.match(regex_emailid)){
			message+="Please enter Valid Email-ID \n";
		}
		
		if(!mobile_number.match(regex_mobile_number)){
			message+="Please enter valid mobile number\n";
		}
		
		if(password1 != password2){
			message+="Password 1 and password 2 don't match\n";
		}
		
		if(message.length == 0){
			$.ajax({ 
				async: true,
				type: "POST",
				url: "CreateUser",
				data: $(this).serialize(),// or fData
				success: function(response){
					$("#modalCreateUser").modal("hide");
					console.log(response);
					$('#idFormCreateUser')[0].reset();
					$("#myModal1").modal("show");
					
					if(response['success']){
						alert("successfully created User");
					}
					else{
						alert("Error EmailID Already Exist");
					}													
			   }
		   });
		}
		else{
			alert(message);
		}
		
	});

	
});