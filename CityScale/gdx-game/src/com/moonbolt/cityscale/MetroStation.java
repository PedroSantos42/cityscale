package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.*;

public class MetroStation implements Screen, ApplicationListener, InputProcessor, TextInputListener {

	////MAINLY///
	private MainGame game;
	private String[] config;
	private GameControl gameControl;
	private String platform;
	private String entryType = "";
	private int charNumActive = 0;
	
	//Primitives
	private float playerX = 0;
	private float playerY = 0;
	private String state = "Front";
	private String walk = "Stop";
	private String breakwalk = "";
	private int movFrameMetro = -300;
	private int movFrameMetro2 = 600;
	private int metrobackword = 0;
	private int metroTVShow = 0;
	private int SaveTime = 100;
	
	//Game States
	private boolean mainState = true;
	private boolean menuState = false;
	private boolean deadState = false;
	private boolean movement = false;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);
	
	//data
	private Player activePlayer;
	private String[] cameraSettings;
	
	//NPCS
	private ArrayList<Sprite> lstNpcs;
	
	//sprite player
	private Sprite spr_player;
	private Sprite spr_hair;
	
	//Background
	private Texture tex_background;
	private Texture tex_backgroundupward;
	private Sprite spr_background;
	private Sprite spr_backgroundupward;
	private Sprite spr_master;
	
	//fonts
	private BitmapFont font_master;
	
	//Interface
	private Sprite spr_Interface;
	
	//Elements
	private Texture tex_Metro;
	private Sprite spr_Metro;
	private Sprite spr_Metro2;
	
	//teste
	private Sprite spr_teste;
	private Sprite spr_teste2;
	private Sprite spr_teste3;
	private Texture tex_teste;
	
	public MetroStation(MainGame gameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
		this.game = gameAlt;
		this.config = configAlt;
		this.gameControl = controlAlt;
		this.platform = platformAlt;
		cameraSettings = new String[3];
		
		//Teste dot
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_teste = new Sprite(tex_teste);
		spr_teste.setSize(1,1);	
		spr_teste2 = new Sprite(tex_teste);
		spr_teste2.setSize(1,1);	
		spr_teste3 = new Sprite(tex_teste);
		spr_teste3.setSize(1,1);	
		
		if(platform.equals("PC")) { entryType = "PC"; }
		if(platform.equals("Mobile")) { entryType = "Mobile"; }
		
		//Data Recover
		charNumActive = gameControl.RecoverActiveChar();
		activePlayer = gameControl.SetActiveCharacter(charNumActive);
		
		//Automatic Save Data
		SaveTime--;
		if(SaveTime <= 0) { 
			SaveTime = 100; 
			gameControl.WriteDataCharacterActive();
			gameControl.SaveData(); 
		}
		
		//Camera and Inputs
		cameraSettings = gameControl.CameraSettings("MetroStation");
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(200,200,camera);
		viewport.apply();
		Gdx.input.setInputProcessor(this);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f,0.10f);
		font_master.setUseIntegerPositions(false);
		
		//background
		tex_background = new Texture(Gdx.files.internal("data/maps/trainstation_downward.jpg"));
		spr_background = new Sprite(tex_background);
		
		tex_backgroundupward = new Texture(Gdx.files.internal("data/maps/trainstation_upward.png")); 
		spr_backgroundupward = new Sprite(tex_backgroundupward);
		
		//interface
		spr_Interface = new Sprite(tex_teste);
		
		//Npcs
		lstNpcs = new ArrayList<Sprite>();
		
		//Elements
		tex_Metro = new Texture(Gdx.files.internal("data/assets/metro.png"));
		spr_Metro = new Sprite(tex_Metro);
		spr_Metro2 = new Sprite(tex_Metro);
		spr_master = new Sprite(tex_Metro);
	}
	
	@Override
	public void render(float p1) {
		//Main Methods

		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Positioning
		playerX = Float.parseFloat(activePlayer.PX_A);
		playerY = Float.parseFloat(activePlayer.PY_A);
		
		//Camera Ajustments
		cameraCoordsX = playerX;
		cameraCoordsY = playerY;
			
		if(playerX <= 50) { cameraCoordsX = 50; }
		if(playerX >= 150) { cameraCoordsX = 150; }
		if(playerY <= 5) { cameraCoordsY = 5; }
		if(playerY >= 5) { cameraCoordsY = 5; }
		
		camera.position.set(cameraCoordsX,10,0);
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
			
		if(mainState) {
			//Set Background
			spr_background.setPosition(-50f,-90);
			spr_background.setSize(300,200);
			spr_backgroundupward.setPosition(-50f,-90);
			spr_backgroundupward.setSize(300,200);
			
			spr_backgroundupward.draw(game.batch);
			ScenarioObjects("metrobackword");
			ScenarioObjects("Metro");
			ScenarioObjects("Metro2");
			spr_background.draw(game.batch);
			ScenarioObjects("TVShow");
			
			
			gameControl.AtualizaCameraX(cameraCoordsX);
			gameControl.AtualizaCameraY(cameraCoordsY);
			
			//Set Player
			if(activePlayer.Sex_A.equals("M")) {
				spr_player = gameControl.MovChar(activePlayer.Set_A, state,walk, "", playerX, playerY,0);
				spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, state,walk, playerX, playerY);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(activePlayer.Sex_A.equals("F")) {
				spr_player = gameControl.MovChar(activePlayer.Set_A, state,walk, "", playerX, playerY,0);
				spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, state,walk, playerX, playerY);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			
			//Place Interface			
			spr_Interface = gameControl.InterfaceMetroStation("Backanalog", ""); spr_Interface.draw(game.batch);  //Analog
			if(walk.equals("Stop")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Stop"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Right")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Right"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Left")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Left"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Front")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Front"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Back")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Back"); spr_Interface.draw(game.batch);  }
			font_master.getData().setScale(0.10f,0.13f);
			//font_master.draw(game.batch,String.valueOf("X:" + Math.round(playerX)),cameraCoordsX - 98,cameraCoordsY + 66);
			//font_master.draw(game.batch,String.valueOf("Y:" + Math.round(playerY)),cameraCoordsX - 88,cameraCoordsY + 66);	
		}
		
		//Recupera��o de HP
		gameControl.RegenerateHPTiming();
		
		ExibirNpcs();
		
		//Colision
		CheckColision();
		
		//Automatic Save Data
		SaveTime--;
		if(SaveTime <= 0) { 
			SaveTime = 100; 
			gameControl.WriteDataCharacterActive();
			gameControl.SaveData(); 
		}
		
		game.batch.end();
	}
	
	public void ExibirNpcs(){
		lstNpcs = gameControl.ExibeNPCs("MetroStation");
		for(int x = 0; x < lstNpcs.size(); x++){
			spr_master = lstNpcs.get(x);
			spr_master.draw(game.batch);
		}
	}
	
	private void ScenarioObjects(String item) {
		
		if(item.equals("Metro")){
			movFrameMetro = movFrameMetro + 2;
			spr_Metro.setSize(120, 60);
			spr_Metro.setPosition(movFrameMetro, 50);
			spr_Metro.draw(game.batch);
			if(movFrameMetro > 500) { movFrameMetro = -300; }
		}
		if(item.equals("Metro2")){
			movFrameMetro2 = movFrameMetro2 - 2;
			spr_Metro2.setSize(120, 60);
			spr_Metro2.setPosition(movFrameMetro2, 50);
			spr_Metro2.draw(game.batch);
			if(movFrameMetro2 < -300) { movFrameMetro2 = 600; }
		}
		if(item.equals("metrobackword")) {
			metrobackword = metrobackword + 1;
			if(metrobackword > 0 && metrobackword <= 30) { spr_master = gameControl.LoadObject("metrobackword1");  }
			if(metrobackword > 30 && metrobackword <= 60) { spr_master = gameControl.LoadObject("metrobackword2");  }
			if(metrobackword > 60 && metrobackword <= 90) { spr_master = gameControl.LoadObject("metrobackword3");  }
			if(metrobackword > 90) { metrobackword = 0;}
			
			spr_master.setSize(35, 10);
			spr_master.setPosition(12,90);
			spr_master.draw(game.batch);
			
			spr_master.setSize(35, 10);
			spr_master.setPosition(120,90);
			spr_master.draw(game.batch);
			
			spr_master.setSize(35, 10);
			spr_master.setPosition(220,90);
			spr_master.draw(game.batch);			
		}
		
		if(item.equals("TVShow")) {
			metroTVShow = metroTVShow + 1;
			if(metroTVShow >= 0 && metroTVShow <= 60) {
				spr_master = gameControl.LoadObject("metroTV1");
			}
			if(metroTVShow > 60 && metroTVShow <= 120) {
				spr_master = gameControl.LoadObject("metroTV2");		
			}
			if(metroTVShow > 120 && metroTVShow <= 180) {
				spr_master = gameControl.LoadObject("metroTV1");
			}
			if(metroTVShow > 180 && metroTVShow <= 240) {
				spr_master = gameControl.LoadObject("metroTV2");
			}
			if(metroTVShow > 240 && metroTVShow <= 300) {
				spr_master = gameControl.LoadObject("metroTV3");		
			}
			if(metroTVShow > 300) { metroTVShow = 1; spr_master = gameControl.LoadObject("metroTV1"); }
			
			spr_master.setSize(35, 30);
			spr_master.setPosition(82.2f,38);
			spr_master.draw(game.batch);
			
			spr_master.setSize(35, 30);
			spr_master.setPosition(-45.5f,38);
			spr_master.draw(game.batch);
		}
	}

	private void CheckColision() {
		
		if(playerX < 1) {		
			breakwalk = "Left";
			state = "Right";
			return;
		}
		
		if(playerY < -102) {		
			breakwalk = "Front";
			state = "Back";
			return;
		}
		
		if(playerX > 1 && playerX < 22 && playerY > 35) {
			breakwalk = "Back";
			state = "Front";
			return;
		}
		
		if(playerX > 30 && playerX < 236 && playerY > -42) {
			breakwalk = "Back";
			state = "Front";
			return;
		}
		
		if(playerY > -40 && playerY < 25 && playerX > 19) {
			breakwalk = "Right";
			state = "Left";
			return;
		}
		
		//Change Screen
		if(playerX >= 217 && playerY < -51) {
			gameControl.AtualizaMapa("Streets305");
			gameControl.WriteDataCharacterActive();
			game.loadingmanager.screenSwitch("Streets305");
		}
		
		breakwalk = "";
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		movement = true;
		downKeys.add(keycode);
        if (downKeys.size >= 2){
            onMultipleKeysDown(keycode);
        }
        if(downKeys.size == 1) {
        	if (((keycode == Input.Keys.A || keycode == Input.Keys.LEFT)) && !breakwalk.equals("Left")) {		
        			state = "Left"; walk = "Walk";       		    			      		
            }
    		
    		if ((keycode == Input.Keys.W || keycode == Input.Keys.UP) && !breakwalk.equals("Up")) { 						
    				state = "Back"; walk = "Walk";
            }
    		
    		if ((keycode == Input.Keys.S || keycode == Input.Keys.DOWN) && !breakwalk.equals("Down")) { 			
    				state = "Front";  walk = "Walk";		 			
            }
    		
    		if ((keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) && !breakwalk.equals("Right")) {
    				state = "Right"; walk = "Walk";  				  			
            } 		
        }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		downKeys.remove(keycode);
		movement = false;
		walk = "Stop";
		return false;
	}

	private void onMultipleKeysDown (int mostRecentKeycode){
		//Para casos de multiplas teclas, recupera a ultima tecla apertada e adiciona a anterior
	    if ((downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)) && !breakwalk.equals("Left")){
	        if (downKeys.size == 2 && (((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)) && !breakwalk.equals("Front")){
	        	state = "Left-Front";
        		walk = "Walk";  	
	        }
	    }
	    if ((downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)) && !breakwalk.equals("Left")){
	        if ((downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)) && !breakwalk.equals("Back")){
	        	state = "Left-Back";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
	    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
	    		state = "Right-Back";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
	    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
	    		state = "Right-Front";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
	        	state = "Back-Right";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
	        	state = "Back-Left";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
	        	state = "Front-Right";
	        	walk = "Walk";		
	        }
	    }
	    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
	        	state = "Front-Left";
	        	walk = "Walk";		
	        }
	    }
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {	
		Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));		
		//Morte Invalida Comandos
		if(deadState) { return false; }	
		movement = true;	
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		movement = false;
		walk = "Stop";
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(movement == true && !menuState){
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
				
			if(coordsTouch.x > (cameraCoordsX - 58) && coordsTouch.x < (cameraCoordsX - 39) && coordsTouch.y > (cameraCoordsY -67) && coordsTouch.y < (cameraCoordsY -43)) {
				if(!breakwalk.equals("Right")) { state = "Right"; walk = "Walk"; }		
			}
			
			if(coordsTouch.x > (cameraCoordsX - 77) && coordsTouch.x < (cameraCoordsX - 58) && coordsTouch.y > (cameraCoordsY -67) && coordsTouch.y < (cameraCoordsY -43)) {
				if(!breakwalk.equals("Right")) { state = "Left"; walk = "Walk"; }				
			}
			
			if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -75) && coordsTouch.y < (cameraCoordsY -56)) {
				if(!breakwalk.equals("Right")) { state = "Front"; walk = "Walk"; }	
			}
			
			if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -56) && coordsTouch.y < (cameraCoordsY -37)) {
				if(!breakwalk.equals("Right")) { state = "Back"; walk = "Walk"; }
			}
		}
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void input(String text) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width,height);	
		camera.position.set(cameraCoordsX,10,0);
		//camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub	
	}

}
