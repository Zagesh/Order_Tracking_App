<?php $username = "s2305631"; $password = "s2305631"; $database = "d2305631"; $link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Define variables and initialize with empty values

$errors = array();

$name = $_REQUEST["username"];
$Onum = $_REQUEST["number"];
$status = $_REQUEST["update"];

// Validate Customer and Staff username and Restuarant
if(empty(trim($Onum))){
        array_push($errors, "Order number is required.");
        echo "Order Number is required.";
} elseif(!preg_match('/^[0-9]+$/', trim($Onum))){
        array_push($errors, "Order Number can only contain letters, numbers, and underscores.");
        echo "Order number can only contain letters, numbers, and underscores.";
}elseif(empty(trim($status))){
        array_push($errors, "Status is required.");
        echo"Status is required.";
}else{
        // Prepare a select statement
        $sql = "SELECT * FROM tblORDER WHERE Staff_UName = ? Order_Num= ?";

        if($stmt = mysqli_prepare($link, $sql)){
 // Bind variables to the prepared statement as parameters
                mysqli_stmt_bind_param($stmt, "ss", $name, $Onum);

                if(mysqli_stmt_execute($stmt)){
                        /* store result */
                        mysqli_stmt_store_result($stmt);
                        if(mysqli_stmt_num_rows($stmt) == 0){
                                array_push($errors, "Invalid.");
                                echo " cannot be found.";
                        }
                }
                mysqli_stmt_close($stmt);
        }
}

if((count($errors)) == 0)
{
        $sql = "UPDATE tblORDER SET Order_Status =? WHERE Order_Num = ?";
	mysqli_query($link,$sql);
	

       	if($stmt = mysqli_prepare($link, $sql))
        {
                // Bind variables to the prepared statement as parameters

                mysqli_stmt_bind_param($stmt, "ss", $status, $Onum);

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
