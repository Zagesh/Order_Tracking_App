<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);


// Define variables and initialize with empty values
{
$username = $_REQUEST["name"];
$sql = "SELECT AVG_RATING(AVG(Order_Rating)) as 'Your average rating' from tblORDER where Staff_UName ='$username'";
$result = mysqli_query($link, $sql);
while ($row=$result->fetch_assoc()){
 $output[]=$row;
}
echo json_encode($output);

	        
}
// Close connection
mysqli_close($link);

?>
