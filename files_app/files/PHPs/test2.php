<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$uName = $_REQUEST["uName"];
$passw = $_REQUEST["passw"];

$uName = mysqli_real_escape_string($link, $uName);
$passw = mysqli_real_escape_string($link, $passw);

/* Select queries return a resultset */

$query = mysqli_prepare($link, "SELECT * from STAFF where Staff_UName=? and Staff_Password=?");
$query->bind_param("ss", $uName, $passw);
$query->execute();
$result = $query->get_result();

$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
$count = mysqli_num_rows($result);

if($count == 1){
    echo "Login successful";
}
else{
    echo "Login failed. Invalid username or password";
}


mysqli_close($link);
?>