<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$errors = array();

$uname = $_REQUEST["username"];
$num = $_REQUEST["ordNum"];
$rating = $_REQUEST["orderRate"];

// Validate Customer and Staff username and Restuarant
if(empty(trim($num))){
        array_push($errors, "Order number is required.");
        echo "Order Number is required.";
} elseif(!preg_match('/^[0-9]+$/', trim($num))){
        array_push($errors, "Order Number can only contain letters, numbers, and underscores.");
        echo "Order number can only contain letters, numbers, and underscores.";
}elseif($rating > 1){
        array_push($errors, "Rating is required.");
        echo"Rating is required.";
}else{
        // Prepare a select statement
        $sql = "SELECT * FROM tblORDER WHERE Cust_UName = ? AND Order_Num= ?";

        if($stmt = mysqli_prepare($link, $sql)){
 // Bind variables to the prepared statement as parameters
                mysqli_stmt_bind_param($stmt, "ss", $uname, $num);

                if(mysqli_stmt_execute($stmt)){
                        /* store result */
                        mysqli_stmt_store_result($stmt);
                        if(mysqli_stmt_num_rows($stmt) == 0){
                                array_push($errors, "Invalid.");
                                echo "cannot be found.";
                        }
                }
                mysqli_stmt_close($stmt);
        }
}

if((count($errors)) == 0)
{
    $sql = "UPDATE tblORDER SET Order_Rating = ? WHERE Order_Num = ?";
	mysqli_query($link,$sql);
	

        if($stmt = mysqli_prepare($link, $sql))
        {
                // Bind variables to the prepared statement as parameters

            mysqli_stmt_bind_param($stmt, "ss", $rating, $num);

                // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt))
            {
                echo "Update successful";
            }

                // Close statement
            mysqli_stmt_close($stmt);
        }
}


mysqli_close($link);
?>