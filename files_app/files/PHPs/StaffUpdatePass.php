<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Define variables and initialize with empty values

$errors = array();

$username = $_REQUEST["username"];
$password = $_REQUEST["password"];
$confirm_password = $_REQUEST["confirm_password"];

if(empty(trim($password))){
        array_push($errors, "Please enter a password.");
        echo "Please enter a password.";
} elseif(strlen(trim($password)) < 6){
        array_push($errors, "Password must have atleast 6 characters.");
        echo "Password must have atleast 6 characters.";
}elseif(empty(trim($confirm_password))){
        array_push($errors, "Please confirm password.");
        echo "Please confirm password.";
} elseif(trim($password) != trim($confirm_password)){
        array_push($errors, "Password did not match.");
        echo "Password did not match.";}


if((count($errors)) == 0)
{
        $sql = "UPDATE STAFF SET Staff_Password = ? WHERE Staff_UName = ?";
        if($stmt = mysqli_prepare($link, $sql))
        {
                // Bind variables to the prepared statement as parameters
                        
                mysqli_stmt_bind_param($stmt, "ss", $password, $username);

                // Attempt to execute the prepared statement
                if(mysqli_stmt_execute($stmt))
                {
                        echo "Update successful";
                }

                // Close statement
                mysqli_stmt_close($stmt);
        }

}
// Close connection
mysqli_close($link);
?>
