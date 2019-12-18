package com.moonbolt.cityscale;

import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

import com.badlogic.gdx.files.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Online {
	
	
	private ArrayList<String> chatServer;
	
	//Dados para Backup
	private String text = "";
	private String situation = "";
	private int num1;
	private int num2;
	private float pos;
	private FileHandle fileBackup;
	private Monster[] lstMobs = {};
	private String[] chatLog = {};
	private Player[] lstPlayer = {};

    public Online() {}

	public void GerenciamentoOnline(String operation, Player plData) throws IOException {
		
		   //Metodo Online (PHP)
		   try {
			 // Construct data
			 String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
			 data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode(operation, "UTF-8");
			 data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode("cityscale.mysql.uhserver.com", "UTF-8");
			 data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode("citymaster", "UTF-8");
		     data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode("Xenobl@de07", "UTF-8");
			 data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode("cityscale", "UTF-8");	 
			 
			 // Send data
			 URL url = new URL("http://moonbolt.online/Conector/Online.php");
			 URLConnection conn = url.openConnection();
			 conn.setDoOutput(true);
			 OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			 wr.write(data);
		     wr.flush();
			 		        
			 // Get the response
			 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        String line;
			        text = "retry";
			        while ((line = rd.readLine()) != null) {
			        	text = line;   
			        	if (text.contains("Logado")) {            	
			            	   		
			            }		            
		    		}	        
			        wr.close();
			        rd.close();
			    } 
				
				catch (Exception e) { situation = "retry";}
	}
		
    //////////////////////////////////////////////////////////
	
	public Monster[] RetornaMobs() {
		return lstMobs;
	}
	
	public ArrayList<String> RetornaChatServidor() {	
		return chatServer;
	}
	
	public Player[] ReturnPlayers(){
		return lstPlayer;
	}
}
