
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
    $stmt= sqlsrv_query($conn, $tsql);
	
	if ($stmt) {  
		echo "true"; 
	} else {  
		echo "false"; 
	}  
	  
	/* Free statement and connection resources. */  
	sqlsrv_close($conn); 
	sqlsrv_free_stmt($stmt);  
	
?>  