<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Define variables and initialize with empty values

$errors = array();

$username = $_REQUEST["username"];
$fname = $_REQUEST["fullname"];
$phonenum = $_REQUEST["phonenumber"];
$password = $_REQUEST["password"];
$confirm_password = $_REQUEST["confirm_password"];


// Validate username
if(empty(trim($username))){
        array_push($errors, "Username is required.");
        echo "Username is required.";
} elseif(!preg_match('/^[a-zA-Z0-9_]+$/', trim($username))){
        array_push($errors, "Username can only contain letters, numbers, and underscores.");
        echo "Username can only contain letters, numbers, and underscores.";
}elseif(empty(trim($fname))){
        array_push($errors, "Please enter your name.");
        echo "Please enter your name.";
}elseif(empty(trim($phonenum))){
        array_push($errors, "Please enter a phone number.");
        echo "Please enter a phone number.";
}elseif(strlen($phonenum)<10){
        array_push($errors, "Incorrect phone number.");
        echo "Incorrect phone number.";
}elseif(empty(trim($password))){
        array_push($errors, "Please enter a password.");
        echo "Please enter a password.";
} elseif(strlen(trim($password)) < 6){
        array_push($errors, "Password must have atleast 6 characters.");
}elseif(empty(trim($confirm_password))){
        array_push($errors, "Please confirm password.");
        echo "Please confirm password.";
} elseif(trim($password) != trim($confirm_password)){
        array_push($errors, "Password did not match.");
        echo "Password did not match.";

} else{
        // Prepare a select statement
        $sql = "SELECT * FROM STAFF WHERE Staff_UName= ?";

        if($stmt = mysqli_prepare($link, $sql)){
 		// Bind variables to the prepared statement as parameters
        	mysqli_stmt_bind_param($stmt, "s", $username);

        	if(mysqli_stmt_execute($stmt)){
                	/* store result */
                	mysqli_stmt_store_result($stmt);
                	if(mysqli_stmt_num_rows($stmt) == 1){
                        	array_push($errors, "This username is already used.");
                        	echo "This username is already used.";
			}
                }else{
                        echo "Oops! Something went wrong. Please try again later.";
                }
                mysqli_stmt_close($stmt);
        }
}

if((count($errors)) == 0)
{
        $sql = "INSERT INTO STAFF(Staff_UName,Staff_FName,Staff_Password,Staff_PhoneNo) VALUES (?, ?, ?, ?)";
        if($stmt = mysqli_prepare($link, $sql))
        {
                // Bind variables to the prepared statement as parameters

                mysqli_stmt_bind_param($stmt, "ssss", $username, $fname, $password, $phonenum);

                // Attempt to execute the prepared statement
                if(mysqli_stmt_execute($stmt))
                {
                        echo "Registration successful";
                }

                // Close statement
                mysqli_stmt_close($stmt);
        }
}
// Close connection
mysqli_close($link);
?>
