/*
 * Copyright (c) 2015 The Jackson Laboratory
 * 
 * This is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by  
 * the Free Software Foundation, either version 3 of the License, or  
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
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