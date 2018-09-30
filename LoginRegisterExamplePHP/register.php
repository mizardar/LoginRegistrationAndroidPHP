<?php
//include config
require('config.php');

//process login form if submitted
if(isset($_POST['firstName'])&& isset($_POST['lastName'])&& isset($_POST['institute'])&&
	isset($_POST['username'])&& isset($_POST['password'])){

		if (!isset($_POST['firstName'])) $error[] = "Please fill out all fields";
		if (!isset($_POST['lastName'])) $error[] = "Please fill out all fields";
		if (!isset($_POST['institute'])) $error[] = "Please fill out all fields";
		if (!isset($_POST['username'])) $error[] = "Please fill out all fields";
		if (!isset($_POST['password'])) $error[] = "Please fill out all fields";
		
		$firstName = $_POST['firstName'];
		$lastName = $_POST['lastName'];
		$institute = $_POST['institute'];
		$username = $_POST['username'];
		$password = $_POST['password'];


		//update users record set the active column to Yes where the memberID and active value match the ones provided in the array
		$stmt = $db->prepare("SELECT * from  students  WHERE Username = :Username");
		$stmt->execute(array(
			':Username' => $username
		));
		$row = $stmt->fetch(PDO::FETCH_ASSOC);

		if(!empty($row['ID'])){
			$response["Error"] = TRUE;
			$response["Message"] =  "User already exist";
			echo json_encode($response); 
			//exit;
		} else {
			

			$stmt = $db->prepare("INSERT INTO students (Username, Userpassword, FirstName, LastName,Institute) 
								VALUES (:Username, :Userpassword, :FirstName, :LastName,:Institute)");
			$stmt->execute(array(
				':Username'  => $username,
				':Userpassword' => $password,
				':FirstName' => $firstName,
				':LastName' => $lastName,
				':Institute' => $institute
			));
			$row = $stmt->fetch(PDO::FETCH_ASSOC);

			$response["Error"] = FALSE;
			$response["Message"] =  "Registration successfull";
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