<?php
//include config
require('config.php');

//process login form if submitted
if(isset($_POST['username'])&& isset($_POST['password'])){

	if (!isset($_POST['username'])) $error[] = "Please fill out all fields";
	if (!isset($_POST['password'])) $error[] = "Please fill out all fields";

	$username = $_POST['username'];
	$password = $_POST['password'];


	//update users record set the active column to Yes where the memberID and active value match the ones provided in the array
	$stmt = $db->prepare("SELECT * from  students  WHERE Username = :Username AND UserPassword = :UserPassword");
	$stmt->execute(array(
		':Username' => $username,
		':UserPassword' => $password
	));
	$row = $stmt->fetch(PDO::FETCH_ASSOC);

	if(!empty($row['ID'])){
		$response["Error"] = FALSE;
		$response["ID"] =  $row["ID"];
		$response["FirstName"] =  $row["FirstName"];
		$response["LastName"] =  $row["LastName"];
		$response["Username"] =  $row["Username"];
		$response["Institute"] =  $row["Institute"];
		echo json_encode($response); 
		//exit;
	} else {

		$response["Error"] = TRUE;
		$response["Message"] =  "Invalid credentials";
		echo json_encode($response);
		//exit;
		//echo "Your account could not be activated."; 
	}




}else{
		$response["Error"] = FALSE;
		$response["Message"] =  "Invalid credentials";
		echo json_encode($response);
		
}//end if submit

//define page title
//$title = 'Login';

//include header template
//require('layout/header.php'); 
?>