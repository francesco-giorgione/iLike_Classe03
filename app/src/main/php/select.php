<?php
    $serverName = "ilike.database.windows.net"; // update me
    $connectionOptions = array(
        "Database" => "", // update me
        "Uid" => "", // update me
        "PWD" => "" // update me
    );
   

    //Establishes the connection
    $conn = sqlsrv_connect($serverName, $connectionOptions);
	$tsql = $_POST['query'];
    $getResults= sqlsrv_query($conn, $tsql);


    if ($getResults != FALSE) {
		$output = array();
		$i = 0;

    	while ($row = sqlsrv_fetch_array($getResults, SQLSRV_FETCH_ASSOC)) {
			$output[$i] = $row;
			$i = $i + 1;
    	}  
    }
	
    print(json_encode($output));
	sqlsrv_close($conn);
?>