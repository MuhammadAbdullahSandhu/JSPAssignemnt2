<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login And Registration</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>



<div class="main">
<div class="box">
<form action="LoginServlet" method="post" onsubmit="return validate()">
	<table id="table">
	<tr><th colspan=2 style="font-size:40px">Login</th></tr>
    <tr><td>Username :</td><td><input type="text" name="username" required></td></tr>
	<tr><td>Password :</td><td><input type="password" name="password" id="pass" required></td></tr>
    <tr><td colspan=2><input type="submit" class="btn"value="login" ></td></tr>
    </table>
</form>
<span id="error_msg"></span>
<p>${error}</p>

<form action="RegiostrationServlet" method="post">
<table id="table">
<tr><th colspan=2 style="font-size:40px">Register</th></tr>
<tr><td>First name :</td><td><input type="text" name="firstname" required></td></tr>
<tr><td>Last name :</td><td><input type="text" name="lastname" required></td></tr>
<tr><td>Username :</td><td><input type="text" name="username" required></td></tr>
<tr><td>Password :</td><td><input type="password" name="password" id="password2" onkeyup="checkPass();" required></td></tr>
<tr><td>Confirm password :</td><td><input type="password" name="confirm" id="confirm2" onkeyup="checkPass();" required></td></tr>
<tr><td colspan=2><span id='message'></span></td></tr>
<tr><td colspan=2><input type="submit" class="btn"value="Sign up" id='btn' ></td></tr>
</table>
</form>
</div>
<div class="calert" id="calert">
<div id="msg" class="msg"><p id="message"></p></div>
</div>
</div>
</body>
<script>
function validate() {
    var username = document.getElementById("username").value
    var password = document.getElementById("password").value
    //const re_email = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(email === "") {
        document.getElementById("error_msg").innerHTML = "Please enter email."
        result = false;
    } else if(password === "") {
        document.getElementById("error_msg").innerHTML = "Please enter password."
        result = false
    } else {
        document.getElementById("error_msg").innerHTML = "Username is not valid."
    }
    return result
}
</script>
<script type="text/javascript">
function checkPass()
{
    //Store the password field objects into variables ...
    var password = document.getElementById('password2');
    var confirm  = document.getElementById('confirm2');
    //Store the Confirmation Message Object ...
    var message = document.getElementById('confirm-message2');
    //Set the colors we will be using ...
    var good_color = "#66cc66";
    var bad_color  = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(password.value == confirm.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        confirm.style.backgroundColor = good_color;
        message.style.color           = good_color;
        message.innerHTML             = 'Passwords Match!';
        document.getElementById('btn').disabled = false;
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        confirm.style.backgroundColor = bad_color;
        message.style.color           = bad_color;
        message.innerHTML             = 'Passwords Do Not Match!';
        document.getElementById('btn').disabled = true;
    }
}  
</script>
</html>