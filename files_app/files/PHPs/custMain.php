<?php
$username = "s2305631";
$password = "s2305631";
$database = "d2305631";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);

$uName = $_REQUEST["uName"];
$output=array();

/* Select queries return a resultset */

$query = mysqli_prepare($link, "SELECT Order_Num, Order_Time, Order_Rest, Order_Status, Order_Rating FROM tblORDER WHERE Cust_UName = ? ORDER BY Order_Num DESC");
$query->bind_param("s", $uName);
$query->execute();
$result = $query->get_result();

while ($row=$result->fetch_assoc()){
    $output[]=$row;
}


mysqli_close($link);
echo json_encode($output);
?>