<?php

class Dbconnect
{
	//database link
	private $con;
	
	//Class constructor
	function __construct()
	{
		
	}
	
	//conn to db
	function connect()
	{
		//incl db constants
		include_once dirname(__FILE__) . '/Constants.php';
		
		//conn to mysql db
		
		$this->con = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
		
		//Check if error during conn
		if(mysqli_connect_errno()){
			echo "Failed to connect to MySQL: " . mysqli_connect_error();
			return null;
			
		}
		
		//if no error return conn link
	return $this->con;

}
}