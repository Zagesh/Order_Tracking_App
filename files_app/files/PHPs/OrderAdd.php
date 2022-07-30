<?php $username = "s2305631"; $password = "s2305631"; $database = "d2305631"; $link = mysqli_connect("127.0.0.1", $username, $password, $database);

// Define variables and initialize with empty values

$errors = array();
$Restuarant=$_REQUEST["Restuarant"];
$Customer = $_REQUEST["Cust_name"];
$Staff = $_REQUEST["Staff_name"];
$DateTime = date('d-m-y h:i:s');

// Validate Customer and Staff username and Restuarant
if(empty(trim($Customer))){
        array_push($errors, "Customer Username is required.");
        echo "Customer Username is required.";
} elseif(!preg_match('/^[a-zA-Z0-9_]+$/', trim($Customer))){
        array_push($errors, "Customer Username can only contain letters, numbers, and underscores.");
        echo "Customer Username can only contain letters, numbers, and underscores.";
}elseif(empty(trim($Staff))){
        array_push($errors, "Staff Username is required.");
        echo "Staff Username is required.";
} elseif(!preg_match('/^[a-zA-Z0-9_]+$/', trim($Staff))){
        array_push($errors, " Staff Username can only contain letters, numbers, and underscores.");
        echo "Staff Username can only contain letters, numbers, and underscores.";
}elseif(empty(trim($Restuarant))){
        array_push($errors, "Restuarant name is required.");
        echo "Restuarant is required.";
}

$NumErr=count($errors);
if($NumErr == 0)
{
        $sql = "INSERT INTO tblORDER(Order_Time,Order_Rest,Cust_UName,Staff_UName) VALUES (?, ?, ?, ?)";
        if($stmt = mysqli_prepare($link, $sql))
        {
                // Bind variables to the prepared statement as parameters

                mysqli_stmt_bind_param($stmt, "ssss" ,$DateTime,$Restuarant, $Customer, $Staff);

                // Attempt to execute the prepared statement
                if(mysqli_stmt_execute($stmt))
                {
                        echo "Order successful";
                }

                // Close statement
                mysqli_stmt_close($stmt);
        }
}

// Close connection
mysqli_close($link);
?>





