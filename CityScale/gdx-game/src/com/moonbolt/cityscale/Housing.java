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

public class Housing implements Screen, ApplicationListener, InputProcessor, TextInputListener {

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
	private boolean playerAutoAttack = false;
	private String state = "Front";
	private String walk = "Stop";
	
	//Game States
	private boolean mainState = true;
	private boolean menuState = false;
	private boolean editState = false;
	private boolean movement = false;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;
    private float movMapX = 0;
    private float movMapY = 0;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);
	
	//data
	private Player activePlayer;
	private String[] cameraSettings;
	
	//sprite player
	private Sprite spr_top;
	private Sprite spr_bottom;
	private Sprite spr_shoes;
	private Sprite spr_hair;
	
	//sprite online people
	private Sprite spr_topOnline;
	private Sprite spr_bottomOnline;
	private Sprite spr_shoesOnline;
	private Sprite spr_hairOnline;
	
	//Background
	private Texture tex_background;
	private Texture tex_backgroundupward;
	private Sprite spr_background;
	private Sprite spr_backgroundupward;
	
	//fonts
	private BitmapFont font_master;
	
	//Interface
	private Sprite spr_Interface;
	
	//Elements
	private Sprite spr_master;
	
	//teste
	private Sprite spr_teste;
	private Sprite spr_teste2;
	private Sprite spr_teste3;
	private Texture tex_teste;
	
	public Housing(MainGame gameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
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
		tex_background = new Texture(Gdx.files.internal("data/maps/trainstation_downward.png"));
		spr_background = new Sprite(tex_background);
		
		tex_backgroundupward = new Texture(Gdx.files.internal("data/maps/trainstation_upward.png")); 
		spr_backgroundupward = new Sprite(tex_backgroundupward);
		
		//interface
		spr_Interface = new Sprite(tex_teste);
		
		//Elements
		spr_master = new Sprite(tex_teste);
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
		//camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
			
		if(mainState) {
			//Set Background
			spr_background.setPosition(-50f,-90);
			spr_background.setSize(300,200);
			
			spr_background.draw(game.batch);
			
			gameControl.AtualizaCameraX(cameraCoordsX);
			gameControl.AtualizaCameraY(cameraCoordsY);
			
			//Set Player
			if(activePlayer.Sex_A.equals("M")) {
				spr_top = gameControl.MovChar(activePlayer.Top_A, state,walk, "Top", playerX, playerY,0);
				spr_bottom = gameControl.MovChar(activePlayer.Bottom_A, state,walk, "Bottom", playerX, playerY,0);
				spr_shoes = gameControl.MovChar(activePlayer.Shoes_A, state,walk, "Shoes", playerX, playerY,0);
				spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, state,walk, playerX, playerY);
				
				spr_shoes.draw(game.batch);
				spr_bottom.draw(game.batch);
				spr_top.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			
			//Place Interface			
			spr_Interface = gameControl.InterfaceMetroStation("PlayerTag", ""); spr_Interface.draw(game.batch);  //Player Tag
			spr_Interface = gameControl.InterfaceMetroStation("Portrait", activePlayer.Hair_A); spr_Interface.draw(game.batch);  //Portrair
			//spr_Interface = gameControl.InterfaceMetroStation("Hotcrossbar", ""); spr_Interface.draw(game.batch);  //Hotbar
			spr_Interface = gameControl.InterfaceMetroStation("Backanalog", ""); spr_Interface.draw(game.batch);  //Analog
			if(walk.equals("Stop")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Stop"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Right")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Right"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Left")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Left"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Front")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Front"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Back")) { spr_Interface = gameControl.InterfaceMetroStation("Analog","Back"); spr_Interface.draw(game.batch);  }
			font_master.getData().setScale(0.10f,0.13f);
			font_master.draw(game.batch,String.valueOf("X:" + Math.round(playerX)),cameraCoordsX - 98,cameraCoordsY + 66);
			font_master.draw(game.batch,String.valueOf("Y:" + Math.round(playerY)),cameraCoordsX - 88,cameraCoordsY + 66);	
			
			font_master.draw(game.batch,String.valueOf(activePlayer.Name_A),cameraCoordsX - 88,cameraCoordsY + 100);
			font_master.draw(game.batch,String.valueOf(activePlayer.HP_A),cameraCoordsX - 81,cameraCoordsY + 93);	
			font_master.draw(game.batch,String.valueOf(activePlayer.MP_A),cameraCoordsX - 80.5f,cameraCoordsY + 85.5f);	
			font_master.draw(game.batch,String.valueOf(activePlayer.Level_A),cameraCoordsX - 64,cameraCoordsY + 93);
			font_master.draw(game.batch,String.valueOf(activePlayer.Exp_A),cameraCoordsX - 66,cameraCoordsY + 85.5f);
			font_master.draw(game.batch,String.valueOf(activePlayer.Stamina_A),cameraCoordsX - 60,cameraCoordsY + 75);
		}
		
		spr_teste.setPosition(cameraCoordsX -17,cameraCoordsY + 22);
		spr_teste2.setPosition(cameraCoordsX -17,cameraCoordsY);
		spr_teste3.setPosition(cameraCoordsX -17,cameraCoordsY - 16);
		spr_teste.draw(game.batch);
		spr_teste2.draw(game.batch);
		spr_teste3.draw(game.batch);
		game.batch.end();
	}
	
	private void ScenarioObjects(String item) {
		
		
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
	public boolean keyDown(int keycode) {
		movement = true;
		downKeys.add(keycode);
        if (downKeys.size >= 2){
            onMultipleKeysDown(keycode);
        }
        if(downKeys.size == 1) {
        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
        		state = "Left";
        		walk = "Walk";    		
            }
    		
    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
    			state = "Back";
    			walk = "Walk";
            }
    		
    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
    			state = "Front";
    			walk = "Walk";	
            }
    		
    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
    			state = "Right";
    			walk = "Walk";	   			
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

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void onMultipleKeysDown (int mostRecentKeycode){
	    //Para casos de multiplas teclas, recupera a ultima tecla apertada e adiciona a anterior
	    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
	        	state = "Left-Front";
        		walk = "Walk";  	
	        }
	    }
	    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
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
		if(movement == true){
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
			
			//playerX - 21,playerY - 52
			
			if(coordsTouch.x > ((cameraCoordsX - 66) + 7)){
				state = "Right";
				walk = "Walk";		
			}
			if(coordsTouch.x < ((cameraCoordsX - 66) - 7)){
				state = "Left";
				walk = "Walk";			
			}
			if(coordsTouch.y < ((cameraCoordsY - 45) - 7)){
				state = "Front";
				walk = "Walk";	
			}
			if(coordsTouch.y > ((cameraCoordsY - 45) + 7)){
				state = "Back";
				walk = "Walk";
			}
		}
		
		return false;
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