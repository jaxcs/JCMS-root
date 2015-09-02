function initializeLogin(){
	$("#userName").val(window.localStorage.getItem("jcmsDefaultUsername"));
	$("#host").val(window.localStorage.getItem("jcmsDefaultHostname"));
	$('#https').prop('checked', (window.localStorage.getItem("jcmsUseSecure") == 'true'));
	$('#rememberMe').prop('checked', (window.localStorage.getItem("jcmsRememberMe") == 'true'));
}

function loginAttempt(){
	var hostName = $("#host").val();
	var userName = $("#userName").val();
	var secure = $("#https").is(":checked");
	var remember = $("#rememberMe").is(":checked");
	if(secure){
		document.cookie='jcmsSecure=' + secure + '; path=/;';
	}
	else{
		document.cookie='jcmsSecure=' + secure + '; path=/;';
	}
	
	var url;
	if(secure){
		url = 'https://' + hostName + ':8443/jcms/login/login';
	}
	else{
		url = 'http://' + hostName + ':8080/jcms/login/login';
	}
	$.ajax({
		type: 'POST',
		url: url,
		data: $("#loginForm").serialize(),
		success: function (data) {
			//login failed
			if(data == 'false'){
				alert("Incorrect Username and Password. Please try again.");
			}
			//login successful
			else{
				if($("#rememberMe").is(":checked")){
					window.localStorage.setItem("jcmsDefaultUsername", userName);
					window.localStorage.setItem("jcmsDefaultHostname", hostName);
					window.localStorage.setItem("jcmsUseSecure", secure);
					window.localStorage.setItem("jcmsRememberMe", remember);
				}
				else{
					window.localStorage.removeItem("jcmsDefaultUsername");
					window.localStorage.removeItem("jcmsDefaultHostname");
					window.localStorage.removeItem("jcmsUseSecure");
					window.localStorage.removeItem("jcmsRememberMe");						
				}
				document.cookie='jcmsHostName='+hostName+'; path=/;';
				document.cookie='jcmsUserName='+userName+'; path=/;';
				document.cookie='jcmsUserPW='+data+'; path=/;';
				window.location.href = "./home.html";
			}
		},
		error: function (xhr, status, error) {
			alert("Error: Could not connect to server, please check your hostname and try again.");
		}
	});
}