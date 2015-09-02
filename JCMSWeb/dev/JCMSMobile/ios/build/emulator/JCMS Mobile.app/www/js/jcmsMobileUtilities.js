function getHost() {
    var name = "jcmsHostName=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
        	return c.substring(name.length,c.length);
        }
    }
    return "";
}

function getUser() {
    var name = "jcmsUserName=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
        	return c.substring(name.length,c.length);
        }
    }
    return "";
}

function getEncrypedPW() {
    var name = "jcmsUserPW=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
        	return c.substring(name.length,c.length);
        }
    }
    return "";
}

function getSecure() {
    var name = "jcmsSecure=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
        	if(c.substring(name.length,c.length) == "true"){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
    }
    return false;
}