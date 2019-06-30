<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
class DbOperation
{	private $con;
	
	function __construct()
	{
		require_once dirname (__FILE__) . '/DbConnect.php';
		$db = new DbConnect();
		$this ->con = $db->connect();
	}
	
	//adding a record to database
	public function AddUser($name, $Email, $Password){
		if($stmt =$this->con->prepare("SELECT Userid FROM Users WHERE name = ? OR Email = ?")){
 		$stmt->bind_param('ss', $name, $Email);
 		$stmt->execute();
 		$stmt->store_result();

 		if($stmt->num_rows > 0){
 		$response['error'] = true;
 		$response['message'] = 'Username/email already in use';
		 $stmt->close();

 		}else{

 		$stmt= $this->con->prepare("INSERT INTO Users (name, Email, Password) VALUES (?, ?, ?)");
		$Passwordmd5=md5($Password);
		$stmt->bind_param('sss',$name, $Email, $Passwordmd5);
		$stmt->execute();
		$response['error']=false;
		$response['message']='Registration successfull';

	}}
        else{$error = $this->con->errno . ' ' . $this->con->error;
    echo $error; 
 }echo json_encode($response);}

	public function Login($name, $Password){
		$Password=md5($Password);
		if($stmt=$this->con->prepare("SELECT Userid FROM Users WHERE name = ? AND Password = ?")){
		$stmt->bind_param('ss',$name, $Password);
		$stmt->execute();
		$stmt->store_result();

 		if($stmt->num_rows > 0){
 		$stmt->bind_result($ID);
 		$stmt->fetch();
 		$response['error']=false;
 		$response['message']='Login successfull';
 		$response['ID']=$ID;
 		
		 

	}else{

 		$response['error'] = true;
 		$response['message'] = 'User not found';
 $stmt->close();

	}
	}
echo json_encode($response);}


public function FetchJobs($Userid, $Jobtype, $Todaydate){
		$Data=new DateTime($Todaydate);
		$Result = $Data->format('Y-m-d');
		$stmt=$this->con->prepare("SELECT Jobname, Jobtype, Jobdate, Jobfrom, Jobto, Jobid FROM Jobs WHERE Jobid NOT IN(SELECT Jobid FROM Userjobs WHERE Userid=?)AND Jobtype = ? AND MONTH(Jobfrom)=MONTH(?)AND DAY(Jobfrom)<=DAY(?) AND DAY(Jobto)>=DAY(?)");
$stmt->bind_param('sssss',$Userid, $Jobtype, $Result , $Result, $Result);
$stmt->execute();
$stmt->store_result();
           if($stmt->num_rows>0){
		$stmt->bind_result($Jobname, $Jobtype, $Jobdate, $Jobfrom, $Jobto, $Jobid);
		$Jobs =array();
			while($stmt->fetch()){
			$temp =array(); 
			$temp['Jobid'] =$Jobid; 
			$temp['Jobname']=$Jobname;
			$temp['Jobdate']=$Jobdate;
			$temp['Jobtype']=$Jobtype;
			$temp['Jobfrom']=$Jobfrom;
			$temp['Jobto']=$Jobto;
			array_push($Jobs, $temp);}
			$response['error']=false;
			$response['message']='Jobs fetched successfully';
			$response['Jobs']=$Jobs;}
else{
$response['error']=true;
$response['message']='No jobs found';}

	echo json_encode($response);}

public function SendUserJobs($Userid, $Jobid, $Jobfrom, $Jobto, $Jobname, $Jobtype){
	$stmt= $this->con->prepare("INSERT INTO Userjobs (Jobid, Jobname, Jobfrom, Jobto, Jobtype, Userid) VALUES (?, ?, ?, ?, ?, ?)");
	$stmt->bind_param('ssssss',$Jobid, $Jobname, $Jobfrom, $Jobto, $Jobtype, $Userid);
	$stmt->execute();
$response['error']=false;
$response['message']='Job inserted successfully';
echo json_encode($response);
}
public function FetchCalendarJobs($Userid, $Jobtype){
	$stmt = $this->con->prepare("SELECT Jobid, Jobname, Jobfrom, Jobto FROM Userjobs WHERE Userid=? AND Jobtype=?");
	$stmt->bind_param('ss',$Userid, $Jobtype);
	$stmt->execute();
        $stmt->store_result();
	if($stmt->num_rows > 0){
		$stmt->bind_result($Jobid, $Jobname, $Jobfrom, $Jobto);
		while($stmt->fetch()){
		$Jobs=array();
		$temp['Jobid']=$Jobid;
		$temp['Jobname']=$Jobname;
		$temp['Jobfrom']=$Jobfrom;
		$temp['Jobto']=$Jobto;
		array_push($Jobs, $temp);}
		$response['error']=false;
			$response['message']='Calendar Jobs fetched successfully';
			$response['Caljobs']=$Jobs;}
		else{
			$response['error']=true;
			$response['message']='Could not fetch calendar jobs';}
  echo json_encode($response);
	
	}
	}