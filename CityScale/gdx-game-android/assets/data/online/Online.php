<?php

	#Variaveis Recebidas do Cliente
	$ldata = $_POST['ldata'];
	$lrequest = $_POST['lrequest'];
	$lversion = $_POST['lversion'];
	
	#Variaveis de Atualizacao
	$lnome = $_POST['lnome'];
	$lhp = $_POST['lhp'];
	$lmp = $_POST['lmp'];
	$lposX = $_POST['lposX'];
	$lposY = $_POST['lposY'];
	$lmap = $_POST['lmap'];
	$llevel = $_POST['llevel'];
	$lsetchar = $_POST['lsetchar'];
	$lhair = $_POST['lhair'];
	$lhat = $_POST['lhat'];
	$lweapon = $_POST['lweapon'];
	$lbattle = $_POST['lbattle'];
	$lside = $_POST['lside'];
	$lpos = $_POST['lpos'];
	$lskillOnline = $_POST['lskillOnline'];
	$laccount = $_POST['laccount'];
	$lparty = $_POST['lparty'];
	$lchat = $_POST['lchat'];
	
	#Variaveis de uso Global
	$lAll = '';
	$lnomeplayer = '';
	
	#Variaveis de Banco
	$servername = $_POST['lservername'];
	$username = $_POST['lusername'];
	$password = $_POST['lpassword'];
	$dbname = $_POST['ldbname'];

	#Comandos Uteis #
	#\n  (Quebra Linha)
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
    die("Resultado: - Falhou na Conexão com Banco -" . $conn->connect_error);
	return;
	}
	
	#Sincronizar
	if ($lrequest == "Sincronizar"){
		if($lversion != "a1"){
				echo nl2br($lversion);
				echo nl2br("Resultado: Versao Invalida");
				return;
		}
	
		//Recupera Chat
		$sql = "SELECT * FROM Chats ORDER BY ChatID DESC LIMIT 3";
		$result = $conn->query($sql);
		
		$lAll = '';
		if ($result->num_rows > 0) {
			// output data of each row
			while($row = $result->fetch_assoc()) {
				$lAll = $lAll . ("SYSTEMCHAT - :Nome=" . $row["Nome"] . 
								":Mensagem=" .  $row["Mensagem"]. ":\n");
			}

			echo nl2br($lAll);
		}
		
		//Verifica se já está ativo
		$sql = "SELECT * FROM Processos where ACCOUNT = '$ldata' ";
		$result = $conn->query($sql);
	
		if ($result->num_rows > 0) {
			// output data of each row
			//while($row = $result->fetch_assoc()) {
			//	$lnomeplayer = $row["NOME"];
			//}
			
			$sql = "UPDATE Processos set NOME = '$lnome',
										 HP = '$lhp',
										 MP = '$lmp',
										 POSX = '$lposX',
										 POSY = '$lposY',
										 MAP = '$lmap',
										 LEVEL = '$llevel',
										 SETCHAR = '$lsetchar',
										 HAIR = '$lhair',
										 HAT = '$lhat',
										 WEAPON = '$lweapon',
										 BATTLE = '$lbattle',
										 SIDE = '$lside',
										 POS = '$lpos',
										 SKILLONLINE = '$lskillOnline',
										 ACCOUNT = '$ldata',
										 PARTY = '$lparty' 
										 where ACCOUNT = '$ldata' ";
			echo nl2br("ready \n");
			echo nl2br($sql);
			
			$result = $conn->query($sql);
			
			if ($conn->query($sql) === TRUE) { echo nl2br("Resultado: \n - UPDATE com sucesso - \n"); } else { echo nl2br("Resultado: \n - Falhou na Atualizacao - \n") . $conn->error; }
			$lAll = '';
			$sql = "SELECT * FROM Processos";
			$result = $conn->query($sql);
			
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
											
						$lAll = $lAll . ("SYSTEMPLAYERS - SELECT :NOME=" . $row["NOME"]. 
							          ":HP=" .  $row["HP"]. 
									  ":MP=" . $row["MP"] . 
									  ":POSX=" . $row["POSX"] .
									  ":POSY=" . $row["POSY"] .
									  ":MAP=" . $row["MAP"] .
									  ":LEVEL=" . $row["LEVEL"] .
									  ":SETCHAR=" . $row["SETCHAR"] .
									  ":HAIR=" . $row["HAIR"] .
									  ":HAT=" . $row["HAT"] .
									  ":WEAPON=" . $row["WEAPON"] .
									  ":BATTLE=" . $row["BATTLE"] .
									  ":SIDE=" . $row["SIDE"] .
									  ":POS=" . $row["POS"] .
									  ":SKILLONLINE=" . $row["SKILLONLINE"] .
									  ":ACCOUNT=" . $row["ACCOUNT"] .
									  ":PARTY=" . $row["PARTY"] .
									  ": - \n");
					}
					echo nl2br ($lAll);					
					$lAll = '';
				}
				else{				
					echo nl2br ("Resultado: \n - Nao foi possivel Recuperar jogadores Online - \n");
				}
			} else {
			$lAll = '';
			$sql = 
			"INSERT INTO Processos (NOME, HP, MP, POSX, POSY, MAP, LEVEL, SETCHAR, HAIR, HAT, WEAPON, BATTLE, SIDE, POS, SKILLONLINE, ACCOUNT, PARTY)  VALUES 
			('$lnome', '$lhp', '$lmp', '$lposX', '$lposY', '$lmap', '$llevel', '$lsetchar','$lhair', '$lhat', '$lweapon', '$lbattle' ,'$lside', '$lpos', '$lskillOnline','$ldata', '$lparty') ";
			
			if ($conn->query($sql) === TRUE) {
				$result = $conn->query($sql);
			
				if ($result->num_rows > 0) {
					while($row = $result->fetch_assoc()) {
						
						$lAll = $lAll . ("SYSTEMPLAYERS - INSERT :NOME=" . $row["NOME"]. 
							          ":HP=" .  $row["HP"]. 
									  ":MP=" . $row["MP"] . 
									  ":POSX=" . $row["POSX"] .
									  ":POSY=" . $row["POSY"] .
									  ":MAP=" . $row["MAP"] .
									  ":LEVEL=" . $row["LEVEL"] .
									  ":SETCHAR=" . $row["SETCHAR"] .
									  ":HAIR=" . $row["HAIR"] .
									  ":HAT=" . $row["HAT"] .
									  ":WEAPON=" . $row["WEAPON"] .
									  ":BATTLE=" . $row["BATTLE"] .
									  ":SIDE=" . $row["SIDE"] .
									  ":POS=" . $row["POS"] .
									  ":SKILLONLINE=" . $row["SKILLONLINE"] .
									  ":ACCOUNT=" . $row["ACCOUNT"] .
									  ":PARTY=" . $row["PARTY"] .
									  ": - \n");
					}
					echo nl2br ($lAll);
					$lAll = '';
				}
				else{
					
					echo nl2br("Resultado: \n - Nao foi possivel Recuperar jogadores Online - \n");
				}
				
			} else {
				
				echo nl2br ("Nao foi possivel inserir jogador \n") . $conn->error;
			}		
		}
		
		$conn->close();
	}
	
	
	#Chat
	if ($lrequest == "Chat"){
		
		$lAll = '';
			$sql = "INSERT INTO Chats (Nome,Mensagem) 
						        VALUES ('$lnome','$lchat') ";
								
		if ($conn->query($sql) === TRUE) {	
		}
		else{
			echo nl2br ("Mensagem Invalida \n");
		}
		
		$conn->close();
	}
	
	##UPLOAD
	if ($lrequest == "Upload"){
		
		$arquivo = $laccount + '.txt';
		$file = fopen($arquivo, 'w');	
		if(fwrite($file, $ldata)){
			echo nl2br("Resultado: \n - Upload com Sucesso- \n");
		}
		fclose($file);
	}
	
	##DOWNLOAD
	if ($lrequest == "Download"){
		
		if (file_exists($laccount)) {
			$arquivo = fopen ($laccount,"r");
			while (!feof ($arquivo)) {
				$linha = fgets($arquivo,4096);
				 echo nl2br ($linha);
			}
			fclose ($arquivo);
		}
		else
		{
			echo nl2br("Arquivo Inexistente");
		}		
	}
?>
