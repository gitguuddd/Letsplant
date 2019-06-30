<?php 
error_reporting(E_ALL);
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
 //adding dboperation file 
 require_once 'DbOperation.php';
 
 //response array 
 $response = array(); 
 
 //if a get parameter named op is set we will consider it as an api call 
 if(isset($_GET['op'])){
 
 //switching the get op value 
 switch($_GET['op']){
 
 //if it is add user
 //that means we will add an user
 case 'AddUser':
 if(isset($_REQUEST['name']) && isset($_REQUEST['Email']) && isset($_REQUEST['Password'])){
 $db = new DbOperation(); 
 $db->AddUser($_REQUEST['name'], $_REQUEST['Email'], $_REQUEST['Password']);
 
 }
 else{
 	$response['error']=true;
 	$response['message']='Fields cannot be empty';

 }
 break; 
 case 'Login':
 if(isset($_REQUEST['name']) && isset($_REQUEST['Password'])){
 	$db=new DbOperation();
 	$db->Login($_REQUEST['name'], $_REQUEST['Password']);
 	
 	} else{
 	$response['error']=true;
 	$response['message']='Fields cannot be empty';

 }
 break;
 case 'FetchJobs':if(isset($_REQUEST['Userid']) && isset($_REQUEST['Jobtype']) && isset($_REQUEST['Todaydate'])){
	$db=new DbOperation();
	$db->FetchJobs($_REQUEST['Userid'], $_REQUEST['Jobtype'], $_REQUEST['Todaydate']);}
			else{
			    $response['error']=true;
			    $response['message']='Could not fetch jobs';
			}
 break;
 case 'SendUserJobs':if(isset($_REQUEST['Userid']) && isset($_REQUEST['Jobid']) && isset($_REQUEST['Jobfrom']) && isset($_REQUEST['Jobto']) && isset($_REQUEST['Jobname']) && isset($_REQUEST['Jobtype'])){
	 $db=new DbOperation();
	 $db->SendUserJobs($_REQUEST['Userid'], $_REQUEST['Jobid'], $_REQUEST['Jobfrom'], $_REQUEST['Jobto'], $_REQUEST['Jobname'], $_REQUEST['Jobtype']);}
			else{
			$response['error']=true;
			$response['message']='Could not insert job';}
 break;
 case 'FetchCalendarJobs':if(isset($_REQUEST['Userid']) && isset($_REQUEST['Jobtype'])){
	$db=new DbOperation();
	$db->FetchCalendarJobs($_REQUEST['Userid'], $_REQUEST['Jobtype']);}
			else{
			$response['error']=true;
			$response['message']='Jobs could not be fetched';}
 break;
	 
 default:
 $response['error'] = true;
 $response['message'] = 'No operation to perform';
 
 }
 
 
 }else{
 $response['error'] = false; 
 $response['message'] = 'no op code given';
 }
 
 //displaying the data in json 
 echo json_encode($response);