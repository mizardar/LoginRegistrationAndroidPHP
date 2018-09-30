<?php
ob_start();
session_start();

//set timezone
date_default_timezone_set('Asia/Calcutta');

//database credentials
define('DBHOST','localhost');
define('DBUSER','root');
define('DBPASS','root');
define('DBNAME','DevelopmentDB');

try {

	//create PDO connection
	$db = new PDO("mysql:host=".DBHOST.";charset=utf8mb4;dbname=".DBNAME, DBUSER, DBPASS);
    //$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_SILENT);//Suggested to uncomment on production websites
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);//Suggested to comment on production websites
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);

} catch(PDOException $e) {
	//show error
    //echo '<p class="bg-danger">'.$e->getMessage().'</p>';
    exit;
}

?>
