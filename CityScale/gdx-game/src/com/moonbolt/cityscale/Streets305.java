package com.moonbolt.cityscale;

import java.io.IOException;
import java.util.ArrayList;

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

public class Streets305 implements Screen, ApplicationListener, InputProcessor, TextInputListener  {
    
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
	private int menuBlock = 1;
	private int intcount = 0;
	private int menuItemTab = 1;
	private int SaveTime = 100;
	private int HPRegenTime = 0;
	private int deathCount = 0;
	private int baloonQuestNum = 0;
	private int questStep = 0;
	private int objectNum = 0;
	private int skillSelected = 0;
	private int countParty = 0;
	private String playerManualAttack = "no";
	private String playerAutoAttack = "no";
	private String state = "Front";
	private String walk = "Stop";
	private String shopName = "";
	private String questName = "";
	private String configMsg = "";
	private String[] logItens;
	 

	//mob
	private float mobX;
	private float mobY;
	
	//Game States
	private boolean mainState = true;
	private boolean menuState = false;
	private boolean questState = false;
	private boolean areaSkillState = false;
	private boolean selectAreaSkillState = false;
	private boolean deadState = false;
	private boolean chatState = false;
	private boolean partyState =  false;
	private boolean shopState = false;
	private boolean movement = false;
	private boolean discart = false;
	private boolean description = false;
	private boolean onlineState = false;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraCoordsX = 0;
    private float cameraCoordsY = 0;
    
    //Controller
    private final IntSet downKeys = new IntSet(20);
	
	//Data
	private Player activePlayer;
	private String[] cameraSettings;
	
	//Sprite player
	private Sprite spr_player;
	private Sprite spr_hair;
	private Sprite spr_weapon;
	
	//Background
	private Texture tex_background;
	private Sprite spr_background;
	private Sprite spr_master;
	
	private ArrayList<Sprite> lstMobs;
	private ArrayList<String> lstNomes;
	private ArrayList<Damage> lstDamage;
	private ArrayList<Skill> lstSkills;
	private ArrayList<String> lstItens;
	private ArrayList<String> lstItensSub;
	private ArrayList<String> lstChats;
	private ArrayList<Buffs> lstBuffs;
	private ArrayList<Sprite> lstNpcs;
	private ArrayList<Player> lstInfoOnline;
	private String[] splitNomes;
	private float nomeX;
	private float nomeY;
	
	//fonts
	private BitmapFont font_master;
	
	//Interface
	private Sprite spr_Interface;
	
	//Sprite Item
	private Sprite spr_item;

	//teste
	private Sprite spr_teste;
	private Sprite spr_teste2;
	private Sprite spr_teste3;
	private Texture tex_teste;
	
	public Streets305(MainGame gameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
		this.game = gameAlt;
		this.config = configAlt;
		this.gameControl = controlAlt;
		this.platform = platformAlt;
		cameraSettings = new String[3];
		
		//mobs Sprites
		lstMobs = new ArrayList<Sprite>();
		
		//Data Recover
		charNumActive = gameControl.RecoverActiveChar();
		activePlayer = gameControl.SetActiveCharacter(charNumActive);
		
		//Camera and Inputs
		cameraSettings = gameControl.CameraSettings("Streets305");
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(200,200,camera);
		viewport.apply();
		Gdx.input.setInputProcessor(this);
		
		//background
		tex_background = new Texture(Gdx.files.internal("data/maps/streets305.jpg"));
		spr_background = new Sprite(tex_background);
		
		//Controls
		if(platform.equals("PC")) { entryType = "PC"; }
		if(platform.equals("Mobile")) { entryType = "Mobile"; }
		
		//Itens
		logItens = new String[120];
		lstItens = new ArrayList<String>();
		lstItensSub = new ArrayList<String>();
		
		//Chats
		lstChats = new ArrayList<String>();
		
		//Npcs
		lstNpcs = new ArrayList<Sprite>();
		
		//Online
		lstInfoOnline = new ArrayList<Player>();
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f,0.10f);
		font_master.setUseIntegerPositions(false);
		
		//Load Mobs
		gameControl.CarregaMonstrosMapa("Streets305");
		
		//Teste dot
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_teste = new Sprite(tex_teste);
		spr_teste.setSize(1,1);	
		spr_teste2 = new Sprite(tex_teste);
		spr_teste2.setSize(1,1);	
		spr_teste3 = new Sprite(tex_teste);
		spr_teste3.setSize(1,1);	
		
		//interface
		spr_Interface = new Sprite(tex_teste);		
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
		
		// Main Font
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f,0.10f);
		font_master.setUseIntegerPositions(false);
		
		//Automatic Save Data
		SaveTime--;
		if(SaveTime <= 0) { 
		SaveTime = 100; 
		gameControl.WriteDataCharacterActive();
		gameControl.SaveData(); 
		}
		
		if(playerX <= 50) { cameraCoordsX = 50; }
		if(playerX >= 350) { cameraCoordsX = 350; }
		if(playerY <= -310) { cameraCoordsY = -310; }
		if(playerY >= 5) { cameraCoordsY = 5; }
		
		camera.position.set(cameraCoordsX,cameraCoordsY + 30,0);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
			
		if(mainState) {
			//Set Background
			spr_background.setPosition(-50f,-380);
			spr_background.setSize(500,520);
			spr_background.draw(game.batch);
			
			
			gameControl.AtualizaCameraX(cameraCoordsX);
			gameControl.AtualizaCameraY(cameraCoordsY);
			
			
			
			
			//Set Player
			if(gameControl.RecuperaPosition() == 3 || gameControl.RecuperaPosition() == 4) {
				spr_player = gameControl.MovChar(activePlayer.Set_A, state,walk, "", playerX, playerY,0);
				spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, state,walk, playerX, playerY);
				spr_weapon = gameControl.ShowWeapon(state, walk,  "", playerX, playerY);
				
				if(spr_weapon != null) { spr_weapon.draw(game.batch); }
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
				
			}
			else {
				spr_player = gameControl.MovChar(activePlayer.Set_A, state,walk, "", playerX, playerY,0);
				spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, state,walk, playerX, playerY);
				spr_weapon = gameControl.ShowWeapon(state, walk,  "", playerX, playerY);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
				if(spr_weapon != null) { spr_weapon.draw(game.batch); }
			}
			
			//Place Interface			
			spr_Interface = gameControl.InterfaceStreets305("PlayerTag", ""); spr_Interface.draw(game.batch);  //Player Tag
			spr_Interface = gameControl.InterfaceStreets305("Portrait", activePlayer.Hair_A); spr_Interface.draw(game.batch);  //Portrair
			spr_Interface = gameControl.InterfaceStreets305("Hotcrossbar", ""); spr_Interface.draw(game.batch);  //Hotbar
			spr_Interface = gameControl.InterfaceStreets305("Backanalog", ""); spr_Interface.draw(game.batch);  //Analog
			spr_Interface = gameControl.InterfaceStreets305("flagStreet305", ""); spr_Interface.draw(game.batch);  //Flag
			spr_Interface = gameControl.InterfaceStreets305("hotkey", ""); spr_Interface.draw(game.batch);  //Hotkey
			if(walk.equals("Stop")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Stop"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Right")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Right"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Left")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Left"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Front")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Front"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Back")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Back"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Left-Front")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Left-Front"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Left-Back")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Left-Back"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Right-Back")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Right-Back"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Right-Front")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Right-Front"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Back-Right")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Back-Right"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Back-Left")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Back-Left"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Front-Right")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Front-Right"); spr_Interface.draw(game.batch);  }
			if(walk.equals("Walk") && state.equals("Front-Left")) { spr_Interface = gameControl.InterfaceStreets305("Analog","Front-Left"); spr_Interface.draw(game.batch);  }
			
			font_master.getData().setScale(0.10f,0.13f);
			font_master.draw(game.batch,String.valueOf("X:" + Math.round(playerX)),cameraCoordsX - 98,cameraCoordsY + 92);
			font_master.draw(game.batch,String.valueOf("Y:" + Math.round(playerY)),cameraCoordsX - 90,cameraCoordsY + 92);			
			font_master.draw(game.batch,String.valueOf(activePlayer.Name_A),cameraCoordsX - 90,cameraCoordsY + 127);
			font_master.draw(game.batch,String.valueOf(activePlayer.HP_A),cameraCoordsX - 82,cameraCoordsY + 120);	
			font_master.draw(game.batch,String.valueOf(activePlayer.MP_A),cameraCoordsX - 82,cameraCoordsY + 112.5f);	
			font_master.draw(game.batch,String.valueOf(activePlayer.Level_A),cameraCoordsX - 66,cameraCoordsY + 121);
			font_master.draw(game.batch,String.valueOf(activePlayer.Exp_A),cameraCoordsX - 68,cameraCoordsY + 113);
			font_master.draw(game.batch,String.valueOf(activePlayer.Stamina_A),cameraCoordsX - 62,cameraCoordsY + 102);
			font_master.draw(game.batch,String.valueOf(gameControl.VerificaCooldown()),cameraCoordsX + 76,cameraCoordsY - 30); // playerAttackCooldown
			if(playerAutoAttack.equals("yes")) { spr_Interface = gameControl.InterfaceStreets305("autoATK", "ON"); spr_Interface.draw(game.batch);} else { spr_Interface = gameControl.InterfaceStreets305("autoATK", "OFF"); spr_Interface.draw(game.batch);}
			spr_Interface = gameControl.InterfaceStreets305("AtkBtn", ""); spr_Interface.draw(game.batch);  //ATK
			spr_Interface = gameControl.InterfaceStreets305("ActionBtn", ""); spr_Interface.draw(game.batch);  //ATK
			
			//Skills de classes
			if(activePlayer.Job_A.equals("Novice")) {
				spr_Interface = gameControl.InterfaceStreets305("tripleAttackbtn", ""); spr_Interface.draw(game.batch);  //ATK
			}
			if(activePlayer.Job_A.equals("Swordman")) {
				spr_Interface = gameControl.InterfaceStreets305("flyswordbtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("ravenbladebtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("healthboostbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("protectbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("ironshieldbtn", ""); spr_Interface.draw(game.batch); 
			}
			
			if(activePlayer.Job_A.equals("Magician")) {
				spr_Interface = gameControl.InterfaceStreets305("icecrystalbtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("fireballbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("thundercloudbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("rockboundbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("soulclashbtn", ""); spr_Interface.draw(game.batch); 
			}
			if(activePlayer.Job_A.equals("Thief")) {
				spr_Interface = gameControl.InterfaceStreets305("invisibilitybtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("doublehitbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("dashkickbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("poisonhitbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("stealbtn", ""); spr_Interface.draw(game.batch); 
			}
			
			if(activePlayer.Job_A.equals("Gunner")) {
				spr_Interface = gameControl.InterfaceStreets305("bulletrainbtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("lockshotbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("precisionbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("fastshotbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("minebtn", ""); spr_Interface.draw(game.batch); 
			}
			
			if(activePlayer.Job_A.equals("Medic")) {
				spr_Interface = gameControl.InterfaceStreets305("healbtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("atkboostbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("defboostbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("regenbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("holyprismbtn", ""); spr_Interface.draw(game.batch); 
			}
			
			if(activePlayer.Job_A.equals("Beater")) {
				spr_Interface = gameControl.InterfaceStreets305("rageboundbtn", ""); spr_Interface.draw(game.batch);  //ATK
				spr_Interface = gameControl.InterfaceStreets305("hammercrashbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("impoundbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("overpowerbtn", ""); spr_Interface.draw(game.batch); 
				spr_Interface = gameControl.InterfaceStreets305("berserkbtn", ""); spr_Interface.draw(game.batch); 
			}
			
			//Se for skill AoE  exibir aviso
			spr_Interface = gameControl.InterfaceStreets305("ActionBtn", ""); spr_Interface.draw(game.batch);  //ATK
			
			//ExibeNPC
			ExibirNpcs();
			
			//Chats
			ExibeChats();
			
			//Exibe Quests
			ExibeQuests();
			
			//Exibe Skills
			ExibeSkills();
					
			//Verify Autoattack
			gameControl.VerificaAttack(playerAutoAttack,playerManualAttack);
			
			//Show Monsters
			lstMobs = gameControl.ExibeMonstros(playerX, playerY);
			for(intcount = 0; intcount < lstMobs.size(); intcount++){				
				lstMobs.get(intcount).draw(game.batch);
				mobX = lstMobs.get(intcount).getX() + (lstMobs.get(intcount).getWidth() / 2);
				mobY = lstMobs.get(intcount).getY() + (lstMobs.get(intcount).getHeight() / 2);
			}
			lstNomes = gameControl.ExibeNomesMonstros();
			for(intcount = 0; intcount < lstNomes.size(); intcount++){				
				splitNomes = lstNomes.get(intcount).split("/");
				nomeX = Float.parseFloat(splitNomes[3]);
				nomeY = Float.parseFloat(splitNomes[4]);
				font_master.getData().setScale(0.12f,0.15f);
				font_master.draw(game.batch,splitNomes[0] + " HP:" + splitNomes[1] + "/" + splitNomes[2],nomeX,nomeY);
			}
			
			//Check Damage
			lstDamage = gameControl.ExibeDanos();
			for(intcount = 0; intcount < lstDamage.size(); intcount++){		
			    if(lstDamage.get(intcount).Color.equals("Yellow")){font_master.setColor(Color.YELLOW);}
				if(lstDamage.get(intcount).Color.equals("Red")){font_master.setColor(Color.RED);}
				font_master.getData().setScale(0.45f,0.48f);		
				font_master.draw(game.batch,lstDamage.get(intcount).dano,lstDamage.get(intcount).areaX,lstDamage.get(intcount).areaY);
			}
			
			//Check Buffs
			lstBuffs = gameControl.RetornaBuffs();
			
			//HP Regeneration
			HPRegenTime++;
			if(HPRegenTime > 1000) {
				gameControl.RegenerateHPTiming();
				HPRegenTime = 0;
			}
			
			// Buffs and Debuffs
			//gameControl.VerificaBuffsDebuffs();
			
			// Monsters Respawn
			gameControl.RespawnMonstros();
			
			//Loots
			if(gameControl.ExibirMsgItem()) {
				spr_master = gameControl.InterfaceStreets305("lootbox", "");
				spr_master.draw(game.batch);
				spr_master = gameControl.ItemDropImage("Refrigerante");
				spr_master.setPosition(cameraCoordsX - 42,cameraCoordsY + 75);
				spr_master.draw(game.batch);
				font_master.getData().setScale(0.12f,0.18f);
				font_master.draw(game.batch,"Obtido:" + gameControl.itemDrop(),cameraCoordsX - 28,cameraCoordsY + 93); // 
			}
			
			//Objects
		    //ScenarioObjects("textovershop");
			
			// Show Shops
			if(shopState) {
				spr_master = gameControl.InterfaceStreets305("SodaMachine", "");
				spr_master.draw(game.batch);
				font_master.getData().setScale(0.25f,0.28f);
				font_master.setColor(Color.YELLOW);
				font_master.draw(game.batch,activePlayer.Money_A,cameraCoordsX + 27,cameraCoordsY - 5); // playerAttackCooldown
			}
						
			// Menu Section
			if(menuState) {		
				if(menuBlock == 1) {
					spr_master = gameControl.ItensMenu("Menu", "Main");
					spr_master.draw(game.batch);
				}
				if(menuBlock == 2) {
					font_master.setColor(Color.WHITE);
					font_master.getData().setScale(0.13f,0.16f);
					font_master.setUseIntegerPositions(false);
					
					spr_master = gameControl.ItensMenu("Menu", "Status");
					spr_master.draw(game.batch);
					font_master.draw(game.batch,String.valueOf(activePlayer.Strengh_A),cameraCoordsX + 10,cameraCoordsY + 56);
					font_master.draw(game.batch,String.valueOf(activePlayer.Vitality_A),cameraCoordsX + 10,cameraCoordsY + 44);
					font_master.draw(game.batch,String.valueOf(activePlayer.Mind_A),cameraCoordsX + 10,cameraCoordsY + 30);
					font_master.draw(game.batch,String.valueOf(activePlayer.Dextery_A),cameraCoordsX + 10,cameraCoordsY + 15);
					font_master.draw(game.batch,String.valueOf(activePlayer.Lucky_A),cameraCoordsX + 10,cameraCoordsY + 2);
					font_master.draw(game.batch,String.valueOf(activePlayer.Agility_A),cameraCoordsX + 10,cameraCoordsY - 14);
					font_master.draw(game.batch,String.valueOf(activePlayer.Resistence_A),cameraCoordsX + 10,cameraCoordsY - 27);
					font_master.draw(game.batch,String.valueOf(activePlayer.StatusPoint_A),cameraCoordsX - 25,cameraCoordsY - 32);
					font_master.draw(game.batch,String.valueOf(activePlayer.Job_A),cameraCoordsX - 44,cameraCoordsY + 50);
					
					spr_player = gameControl.MovChar(activePlayer.Set_A, "Front","Stop", "Menu", cameraCoordsX - 43, cameraCoordsY - 10,1);
					spr_hair = gameControl.ReturnHairs(activePlayer.Hair_A, "Menu","Stop", cameraCoordsX - 43, cameraCoordsY - 10);
						
					spr_player.draw(game.batch);
					spr_hair.draw(game.batch); 
				}	
				
				if(menuBlock == 3) {
					spr_master = gameControl.ItensMenu("Menu", "Itens");
					spr_master.draw(game.batch);
					ExibeItensMochila();
				}
				
				if(menuBlock == 4) {
					spr_master = gameControl.ItensMenu("Menu", "Skills");
					spr_master.draw(game.batch);
					
					if(activePlayer.Job_A.equals("Novice")) {
						spr_Interface = gameControl.InterfaceStreets305("tripleAttackbtnMenu", ""); spr_Interface.draw(game.batch); 
						font_master.getData().setScale(0.15f,0.18f);
						font_master.draw(game.batch,"Ataque Triplo",cameraCoordsX - 44,cameraCoordsY + 37);
						font_master.draw(game.batch,"Causa 3x o Atk comum",cameraCoordsX - 44,cameraCoordsY + 25);
					}
					
				}
				
				if(menuBlock == 5) {
					spr_master = gameControl.ItensMenu("Menu", "Social");
					spr_master.draw(game.batch);
				}
				
				if(menuBlock == 7) {
					spr_master = gameControl.ItensMenu("Menu", "Batalha");
					spr_master.draw(game.batch);
				}
				
				if(menuBlock == 8) {
					spr_master = gameControl.ItensMenu("Menu", "Config");
					spr_master.draw(game.batch);					
					font_master.draw(game.batch,configMsg,cameraCoordsX,cameraCoordsY); 
				}
			}
			
			
			
			//Death
			if(activePlayer.HP_A.equals("0")){ deadState = true; }
			if(deadState){
				deathCount++;	
				activePlayer.HP_A = "1";
				lstMobs.clear();
				playerAutoAttack = "no";
				spr_Interface = gameControl.InterfaceStreets305("deathmsg", ""); 
				spr_Interface.draw(game.batch);  //msg
				if(deathCount > 300){
					gameControl.AtualizaMapa("MetroStation");
					gameControl.WriteDataCharacterActive();
					game.loadingmanager.screenSwitch("MetroStation");
				}
			}
		}
		
		if(onlineState) {
			TrataOnline();
			ExibeParty();
		}

		//Tests
		//spr_teste.setPosition(cameraCoordsX + 90, cameraCoordsY - 5); 
		//spr_teste2.setPosition(cameraCoordsX + 99, cameraCoordsY - 20);
		//spr_teste.draw(game.batch);
		//spr_teste2.draw(game.batch);
		//font_master.draw(game.batch,String.valueOf(playerX),cameraCoordsX,cameraCoordsY);
		//font_master.draw(game.batch,String.valueOf(playerY),cameraCoordsX + 30,cameraCoordsY);		
		
		game.batch.end();
	}
	
	private void TrataOnline() {
		
			//lstInfoOnline.clear();
			lstInfoOnline = gameControl.InfoPlayerOnline();
			for(int i = 0; i < lstInfoOnline.size(); i ++) {
				
				if(lstInfoOnline.get(i).Map_A.equals("Streets305")) {
				spr_master = gameControl.MovChar(lstInfoOnline.get(i).Set_A,lstInfoOnline.get(i).Side_A,"","",Float.parseFloat(lstInfoOnline.get(i).PX_A),
																								  Float.parseFloat(lstInfoOnline.get(i).PY_A),
																								  Integer.parseInt(lstInfoOnline.get(i).Position_A));
				spr_master.draw(game.batch);
				
				spr_master = gameControl.ReturnHairs(lstInfoOnline.get(i).Hair_A,lstInfoOnline.get(i).Side_A,"",Float.parseFloat(lstInfoOnline.get(i).PX_A),
																												Float.parseFloat(lstInfoOnline.get(i).PY_A));
				spr_master.draw(game.batch);
				
				font_master.draw(game.batch,lstInfoOnline.get(i).Name_A,Float.parseFloat(lstInfoOnline.get(i).PX_A) + 3,Float.parseFloat(lstInfoOnline.get(i).PY_A) + 10);
				}
			}			
	}
	
	private void ExibeParty() {
		lstInfoOnline = gameControl.InfoPlayerOnline();
		
		countParty = 0;
		for(int i = 0; i < lstInfoOnline.size(); i++) {				
			String partylst =  lstInfoOnline.get(i).Party_A;
			String characterParty = activePlayer.Party_A;
			if(lstInfoOnline.get(i).Party_A.equals(activePlayer.Party_A)) {
				countParty++;
				
				font_master.getData().setScale(0.09f,0.11f);
				if(countParty > 3) { return;}
				
				if(countParty == 1) {
					spr_master = gameControl.InterfaceStreets305("PartyTag1","");
					spr_master.draw(game.batch);
					
					spr_master = gameControl.ReturnHairs(lstInfoOnline.get(i).Hair_A,"Front","",cameraCoordsX - 101,cameraCoordsY + 26);																								
					spr_master.draw(game.batch);
					
					font_master.draw(game.batch,lstInfoOnline.get(i).Name_A,cameraCoordsX - 90,cameraCoordsY +85);
					font_master.draw(game.batch,lstInfoOnline.get(i).HP_A,cameraCoordsX - 80,cameraCoordsY +79);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 80,cameraCoordsY +73);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 69,cameraCoordsY +80);
					font_master.draw(game.batch,lstInfoOnline.get(i).Map_A,cameraCoordsX - 80,cameraCoordsY +66);	
				}
				
				if(countParty == 2) {
					spr_master = gameControl.InterfaceStreets305("PartyTag2","");
					spr_master.draw(game.batch);

					spr_master = gameControl.ReturnHairs(lstInfoOnline.get(i).Hair_A,"Front","",cameraCoordsX - 101,cameraCoordsY - 1);
					spr_master.draw(game.batch);
					
					font_master.draw(game.batch,lstInfoOnline.get(i).Name_A,cameraCoordsX - 90,cameraCoordsY +57);
					font_master.draw(game.batch,lstInfoOnline.get(i).HP_A,cameraCoordsX - 80,cameraCoordsY +52);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 80,cameraCoordsY +46);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 69,cameraCoordsY +52);
					font_master.draw(game.batch,lstInfoOnline.get(i).Map_A,cameraCoordsX - 80,cameraCoordsY +38);
				}
				
				if(countParty == 3) {
					spr_master = gameControl.InterfaceStreets305("PartyTag3","");
					spr_master.draw(game.batch);

					spr_master = gameControl.ReturnHairs(lstInfoOnline.get(i).Hair_A,"Front","",cameraCoordsX - 101,cameraCoordsY - 30);
					spr_master.draw(game.batch);
					
					font_master.draw(game.batch,lstInfoOnline.get(i).Name_A,cameraCoordsX - 90,cameraCoordsY +29);
					font_master.draw(game.batch,lstInfoOnline.get(i).HP_A,cameraCoordsX - 80,cameraCoordsY +24);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 80,cameraCoordsY +18);
					font_master.draw(game.batch,lstInfoOnline.get(i).MP_A,cameraCoordsX - 69,cameraCoordsY +24);			
					font_master.draw(game.batch,lstInfoOnline.get(i).Map_A,cameraCoordsX - 80,cameraCoordsY +10);
				}
				
				font_master.getData().setScale(0.12f,0.14f);
			}
		}
		
	}
	
	public void ExibirNpcs(){
		lstNpcs = gameControl.ExibeNPCs("Streets305");
		for(int x = 0; x < lstNpcs.size(); x++){
			spr_master = lstNpcs.get(x);
			spr_master.draw(game.batch);
		}
	}
	
	private void ExibeChats() {	
		
		try {
		lstChats = gameControl.CarregaChats();
		
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.16f,0.23f);
		font_master.setUseIntegerPositions(false);
		font_master.draw(game.batch,"________Chat________",cameraCoordsX - 30,cameraCoordsY - 30);
		
		for(int i = 0; i < 3; i++) {
			if(i == 0) {
			font_master.draw(game.batch,lstChats.get(i),cameraCoordsX - 30,cameraCoordsY - 40);
			}
			if(i == 1) {
			font_master.draw(game.batch,lstChats.get(i),cameraCoordsX - 30,cameraCoordsY - 50);
			}
			if(i == 2) {
			font_master.draw(game.batch,lstChats.get(i),cameraCoordsX - 30,cameraCoordsY - 60);
			}
		}
		
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.10f,0.13f);
		font_master.setUseIntegerPositions(false);
		}
		
		catch(Exception ex) {
			
		}
	}
	
	public void ExibeItensMochila() {
		//Para Itens Comuns
		if(!activePlayer.Itens_A.equals("")) {
			logItens = activePlayer.Itens_A.split("-");
			intcount = 0;
			for(int i = 0; i < activePlayer.Itens_A.length(); i++){
				if(activePlayer.Itens_A.charAt(i) == '-'){
					intcount++;
				}
			}
			for(int i = 0; i <= intcount; i++) {
				if(!logItens[i].equals("[None]")){			   
				   if(menuItemTab == 1 && (i >= 0 && i <= 11)){
				     spr_item = gameControl.ItemImage(logItens[i], i, cameraCoordsX, cameraCoordsY + 30);
				     spr_item.draw(game.batch);
				     font_master.draw(game.batch,String.valueOf("x" + gameControl.ItemQuantidade()),spr_item.getX() + 10,spr_item.getY() + 8);		
				   }
				   if(menuItemTab == 2 && (i >= 12 && i <= 23)){
					     spr_item = gameControl.ItemImage(logItens[i], i, cameraCoordsX, cameraCoordsY + 30);
					     spr_item.draw(game.batch);
					     font_master.draw(game.batch,String.valueOf("x" + gameControl.ItemQuantidade()),spr_item.getX() + 10,spr_item.getY() + 8);		
				   }
				   if(menuItemTab == 3 && (i >= 24 && i <= 35)){
					     spr_item = gameControl.ItemImage(logItens[i], i, cameraCoordsX, cameraCoordsY + 30);
					     spr_item.draw(game.batch);
					     font_master.draw(game.batch,String.valueOf("x" + gameControl.ItemQuantidade()),spr_item.getX() + 10,spr_item.getY() + 8);		
				   }
				   if(menuItemTab == 4 && (i >= 36 && i <= 47)){
					     spr_item = gameControl.ItemImage(logItens[i], i, cameraCoordsX, cameraCoordsY + 30);
					     spr_item.draw(game.batch);
					     font_master.draw(game.batch,String.valueOf("x" + gameControl.ItemQuantidade()),spr_item.getX() + 10,spr_item.getY() + 8);		
				   }
				   if(menuItemTab == 5 && (i >= 48 && i <= 59)){
					     spr_item = gameControl.ItemImage(logItens[i], i, cameraCoordsX, cameraCoordsY + 30);
					     spr_item.draw(game.batch);
					     font_master.draw(game.batch,String.valueOf("x" + gameControl.ItemQuantidade()),spr_item.getX() + 10,spr_item.getY() + 8);		
				   }
				   
				}
			}
		}
		//Para Itens Equipados
		spr_item = gameControl.ItemEquipped("weapon", cameraCoordsX, cameraCoordsY);
		spr_item.draw(game.batch);
		spr_item = gameControl.ItemEquipped("set", cameraCoordsX, cameraCoordsY);
		spr_item.draw(game.batch);
		spr_item = gameControl.ItemEquipped("hat", cameraCoordsX, cameraCoordsY);
		if(spr_item != null) { spr_item.draw(game.batch); }
		
		
	}
	
	private void ExibeSkills() {
		
		gameControl.CalculaCooldown();
		font_master.getData().setScale(0.12f,0.15f);
		font_master.draw(game.batch,"Atraso:" + String.valueOf(gameControl.delayinfo()),cameraCoordsX + 85,cameraCoordsY - 26);
		lstSkills = gameControl.RetornaSkills();
		
		for(int i = 0; i < lstSkills.size(); i++) {
			int timer = 0;
			int framesk = 0;
			
			timer = lstSkills.get(i).timer;
			framesk = lstSkills.get(i).countFrameEffect;
			
			spr_master = gameControl.ImageSkill(lstSkills.get(i));
			spr_master.setSize(lstSkills.get(i).width, lstSkills.get(i).height);
			spr_master.setPosition(lstSkills.get(i).posX, lstSkills.get(i).posY);
			spr_master.draw(game.batch);
			timer--;
			
			if(timer == 50 || timer == 40 || timer == 30 || timer == 20 || timer == 10) { 
				framesk++; 
			}
			if(timer <= 0) { 
				gameControl.RemoveSkill(i); 
			}
			else {
				lstSkills.get(i).timer = timer;
				lstSkills.get(i).countFrameEffect = framesk;
			}			
		}
	}
	
	// World Settings - BEGIN //
	private void VerificaAction() {
		
		if( (playerX >= 75 && playerX <= 102) && ( playerY >= -84 && playerY <= -70) ) {
			shopState = true;
			shopName = "SodaMachine";
		}
		
		
		if( (playerX >= 44 && playerX <= 74) && (playerY >= -108 && playerY <= -80) ) {		
			activePlayer.Job_A = "Swordman";
			
		}
		
		//if( (playerX >= 44 && playerX <= 74) && (playerY >= -108 && playerY <= -80) ) {
		//	questState = true;
		//	questName = "Um pedido gentil";
		//}
	}
	
	private void ExibeQuests() {
		//Exibe Texto Quest//
		if(questState) {
			questStep = gameControl.IniciaQuest("Um pedido gentil");
			
			if(questStep == 0) { questState = false; }			
			spr_Interface = gameControl.InterfaceStreets305("questBar", ""); spr_Interface.draw(game.batch);
			font_master.draw(game.batch,String.valueOf(activePlayer.Job_A),cameraCoordsX - 44,cameraCoordsY + 50);
			font_master.draw(game.batch,String.valueOf(activePlayer.Job_A),cameraCoordsX - 44,cameraCoordsY + 50);
		}	
		//Balao de Quest
		spr_Interface = gameControl.InterfaceStreets305("avisoMissao", ""); spr_Interface.draw(game.batch);
		
	}
	
	
	
	// World Settings - END //
	private void ItemSelecionado(int numItemSelecionado) {
		gameControl.VerificaItemSelecionado(menuItemTab, numItemSelecionado);
	}
	
	@Override
	public void input(String text) {
		
		if(chatState) {		
			//Para cancelamento
			if(text.equals("")) { chatState = false; return; }
			
			if(onlineState) { try {
				gameControl.GerenciamentoOnline("Chat", text);
				chatState = false;
				return;
			} catch (IOException e) {
				e.printStackTrace();
				chatState = false;
			} return; }
			
			gameControl.InsereChat(text);
			chatState = false;
		}
		
		if(partyState) {
			//
			if(text.equals("")) { partyState = false; return; }
			try {
				gameControl.GerenciamentoOnline("Party",text);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			partyState = false;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		movement = false;
		walk = "Stop";
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(movement == true && !menuState && !deadState && !shopState && !questState){
			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));
				
			if(coordsTouch.x > (cameraCoordsX - 58) && coordsTouch.x < (cameraCoordsX - 39) && coordsTouch.y > (cameraCoordsY -37) && coordsTouch.y < (cameraCoordsY -13)) {
				state = "Right";
				walk = "Walk";		
			}
			
			if(coordsTouch.x > (cameraCoordsX - 77) && coordsTouch.x < (cameraCoordsX - 58) && coordsTouch.y > (cameraCoordsY -37) && coordsTouch.y < (cameraCoordsY -13)) {
				state = "Left";
				walk = "Walk";		
			}
			
			if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -45) && coordsTouch.y < (cameraCoordsY -26)) {
				state = "Front";
				walk = "Walk";
			}
			
			if(coordsTouch.x > (cameraCoordsX - 68) && coordsTouch.x < (cameraCoordsX - 49) && coordsTouch.y > (cameraCoordsY -26) && coordsTouch.y < (cameraCoordsY -7)) {
				state = "Back";
				walk = "Walk";
			}
		}
		
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		movement = true;
		downKeys.add(keycode);
        if (downKeys.size >= 2 && !menuState && !deadState && !shopState && !questState){
            onMultipleKeysDown(keycode);
        }
        if(downKeys.size == 1 && !menuState && !deadState && !shopState && !questState) {
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
		
		//Morte Invalida Comandos
		if(deadState) { movement = false; return false; }
		
		if(selectAreaSkillState) {
            gameControl.SetaSkillArea(skillSelected,coordsTouch.x,coordsTouch.y);
            selectAreaSkillState = false;
		}
		
		if(mainState) {
			if(!menuState && !questState && !deadState && !shopState) {
				//Menu
				if((coordsTouch.x >= cameraCoordsX - 86 && coordsTouch.x <= cameraCoordsX -73) && (coordsTouch.y >= cameraCoordsY + 65 && coordsTouch.y <= cameraCoordsY + 125)){
					menuState = true;
					return false;
				}
				//Auto Attack 
				if((coordsTouch.x >= cameraCoordsX + 73 && coordsTouch.x <= cameraCoordsX + 80) && (coordsTouch.y >= cameraCoordsY - 79 && coordsTouch.y <= cameraCoordsY - 33)){
					if(playerAutoAttack.equals("no")) { playerAutoAttack = "yes"; } else { playerAutoAttack = "no"; }
					return false;
				}
				//Manual Attack 
				if((coordsTouch.x >= cameraCoordsX + 44 && coordsTouch.x <= cameraCoordsX + 54) && (coordsTouch.y >= cameraCoordsY - 99 && coordsTouch.y <= cameraCoordsY - 53)){
					gameControl.VerificaAttack("","yes");
					return false;
				}
				//Action
				if((coordsTouch.x >= cameraCoordsX + 91 && coordsTouch.x <= cameraCoordsX + 98) && (coordsTouch.y >= cameraCoordsY - 50 && coordsTouch.y <= cameraCoordsY - 34)){
					VerificaAction();
					return false;
				}
				//Chat
				if((coordsTouch.x >= cameraCoordsX - 100 && coordsTouch.x <= cameraCoordsX - 87) && (coordsTouch.y >= cameraCoordsY + 65 && coordsTouch.y <= cameraCoordsY + 105)){
					chatState = true;
					Gdx.input.getTextInput(this,"Digite a Mensagem","","");  
					return false;
				}
				//Skill 1
				if((coordsTouch.x >= cameraCoordsX + 55 && coordsTouch.x <= cameraCoordsX + 62) && (coordsTouch.y >= cameraCoordsY - 70 && coordsTouch.y <= cameraCoordsY - 53)){
					skillSelected = 1;
					areaSkillState = gameControl.VerificaRangedSkill(skillSelected);
					if(!areaSkillState){
						gameControl.SetaSkillSolo(skillSelected);
					}
				    else {
						selectAreaSkillState = true;
					}
					return false;
				}
				
				//Hotkey
				if((coordsTouch.x >= cameraCoordsX + 90 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY - 20 && coordsTouch.y <= cameraCoordsY - 5)){
					ItemSelecionado(0);
					return false;
				}
			}
		}
		
		
		
		if(menuState) {
			if(menuBlock == 1) { // Main
				//Para Status
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY + 70 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 2;
					return false;
				}
				//Para Itens
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY + 52 && coordsTouch.y <= cameraCoordsY + 70)){
					menuBlock = 3;
					return false;
				}
				//Para Batalha
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY + 32 && coordsTouch.y <= cameraCoordsY + 51)){
					menuBlock = 7;
					return false;
				}
				//Para Habilidades
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY + 14 && coordsTouch.y <= cameraCoordsY + 32)){
					menuBlock = 4;
					return false;
				}
				//Para Social
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY - 3 && coordsTouch.y <= cameraCoordsY + 13)){
					menuBlock = 5;
					return false;
				}
				
				//Para Config
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY - 20 && coordsTouch.y <= cameraCoordsY - 4)){
					menuBlock = 8;
					return false;
				}
				//Para Sair
				if((coordsTouch.x >= cameraCoordsX + 85 && coordsTouch.x <= cameraCoordsX + 99) && (coordsTouch.y >= cameraCoordsY - 30 && coordsTouch.y <= cameraCoordsY - 21)){
					menuState = false;
					return false;
				}
			}
			
			
			
			if(menuBlock == 2) { //Status
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
				//Aumenta Forï¿½a
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY + 48 && coordsTouch.y <= cameraCoordsY + 62)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Strengh_A);
						intcount++; 
						activePlayer.Strengh_A = String.valueOf(intcount);  
						return false;
					}
				}
				//Aumenta Vitalidade
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY + 33 && coordsTouch.y <= cameraCoordsY + 47)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Vitality_A);
						intcount++; 
						activePlayer.Vitality_A = String.valueOf(intcount); 
						return false;
					}
				}
				//Aumenta Sabedoria
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY + 21 && coordsTouch.y <= cameraCoordsY + 32)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Mind_A);
						intcount++; 
						activePlayer.Mind_A = String.valueOf(intcount);  
						return false;
					}
				}
				//Aumenta Dextery
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY + 15 && coordsTouch.y <= cameraCoordsY + 22)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Dextery_A);
						intcount++; 
						activePlayer.Dextery_A = String.valueOf(intcount); 
						return false;
					}
				}
				//Aumenta Lucky
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY - 9 && coordsTouch.y <= cameraCoordsY + 16)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Lucky_A);
						intcount++; 
						activePlayer.Lucky_A = String.valueOf(intcount);  
						return false;
					}
				}			
				//Aumenta Lucky
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY - 24 && coordsTouch.y <= cameraCoordsY - 10)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Agility_A);
						intcount++; 
						activePlayer.Agility_A = String.valueOf(intcount);  
						return false;
					}
				}
				//Aumenta Resistencia
				if((coordsTouch.x >= cameraCoordsX + 17 && coordsTouch.x <= cameraCoordsX + 24) && (coordsTouch.y >= cameraCoordsY - 25 && coordsTouch.y <= cameraCoordsY - 38)){
					intcount = Integer.parseInt(activePlayer.StatusPoint_A);
					if(intcount >= 1) { 
						intcount--; 
						activePlayer.StatusPoint_A = String.valueOf(intcount); 
						intcount = Integer.parseInt(activePlayer.Resistence_A);
						intcount++; 
						activePlayer.Resistence_A = String.valueOf(intcount);  
						return false;
					}
				}
			}  //Fim menu Status
			
			
			if(menuBlock == 3) {//Itens
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
				
				//Item 1
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 46) && (coordsTouch.y >= cameraCoordsY + 30 && coordsTouch.y <= cameraCoordsY + 54)){
					if(menuItemTab == 1) { ItemSelecionado(0);}
					if(menuItemTab == 2) { ItemSelecionado(12);}
					if(menuItemTab == 3) { ItemSelecionado(24);}
					if(menuItemTab == 4) { ItemSelecionado(36);}
					if(menuItemTab == 5) { ItemSelecionado(48);}
					return false;
				}
				
				//Item 2
				if((coordsTouch.x >= cameraCoordsX - 45 && coordsTouch.x <= cameraCoordsX - 32) && (coordsTouch.y >= cameraCoordsY + 30 && coordsTouch.y <= cameraCoordsY + 54)){
					if(menuItemTab == 1) { ItemSelecionado(1);}
					if(menuItemTab == 2) { ItemSelecionado(13);}
					if(menuItemTab == 3) { ItemSelecionado(25);}
					if(menuItemTab == 4) { ItemSelecionado(37);}
					if(menuItemTab == 5) { ItemSelecionado(49);}
					return false;
				}
				
				//Item 3
				if((coordsTouch.x >= cameraCoordsX - 31 && coordsTouch.x <= cameraCoordsX - 18) && (coordsTouch.y >= cameraCoordsY + 30 && coordsTouch.y <= cameraCoordsY + 54)){
					if(menuItemTab == 1) { ItemSelecionado(2);}
					if(menuItemTab == 2) { ItemSelecionado(14);}
					if(menuItemTab == 3) { ItemSelecionado(26);}
					if(menuItemTab == 4) { ItemSelecionado(38);}
					if(menuItemTab == 5) { ItemSelecionado(50);}
					return false;
				}
				
				//Item 4
				if((coordsTouch.x >= cameraCoordsX - 17 && coordsTouch.x <= cameraCoordsX - 4) && (coordsTouch.y >= cameraCoordsY + 30 && coordsTouch.y <= cameraCoordsY + 54)){
					if(menuItemTab == 1) { ItemSelecionado(3);}
					if(menuItemTab == 2) { ItemSelecionado(15);}
					if(menuItemTab == 3) { ItemSelecionado(27);}
					if(menuItemTab == 4) { ItemSelecionado(39);}
					if(menuItemTab == 5) { ItemSelecionado(51);}
					return false;
				}
				
				//Item 5
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 46) && (coordsTouch.y >= cameraCoordsY + 4 && coordsTouch.y <= cameraCoordsY + 29)){
					if(menuItemTab == 1) { ItemSelecionado(4);}
					if(menuItemTab == 2) { ItemSelecionado(16);}
					if(menuItemTab == 3) { ItemSelecionado(28);}
					if(menuItemTab == 4) { ItemSelecionado(40);}
					if(menuItemTab == 5) { ItemSelecionado(52);}
					return false;
				}
				
				//Item 6
				if((coordsTouch.x >= cameraCoordsX - 45 && coordsTouch.x <= cameraCoordsX - 32) && (coordsTouch.y >= cameraCoordsY + 4 && coordsTouch.y <= cameraCoordsY + 29)){
					if(menuItemTab == 1) { ItemSelecionado(5);}
					if(menuItemTab == 2) { ItemSelecionado(17);}
					if(menuItemTab == 3) { ItemSelecionado(29);}
					if(menuItemTab == 4) { ItemSelecionado(41);}
					if(menuItemTab == 5) { ItemSelecionado(53);}
					return false;
				}
				
				//Item 7
				if((coordsTouch.x >= cameraCoordsX - 31 && coordsTouch.x <= cameraCoordsX - 18) && (coordsTouch.y >= cameraCoordsY + 4 && coordsTouch.y <= cameraCoordsY + 29)){
					if(menuItemTab == 1) { ItemSelecionado(6);}
					if(menuItemTab == 2) { ItemSelecionado(18);}
					if(menuItemTab == 3) { ItemSelecionado(30);}
					if(menuItemTab == 4) { ItemSelecionado(42);}
					if(menuItemTab == 5) { ItemSelecionado(54);}
					return false;
				}
				
				//Item 8
				if((coordsTouch.x >= cameraCoordsX - 17 && coordsTouch.x <= cameraCoordsX - 4) && (coordsTouch.y >= cameraCoordsY + 4 && coordsTouch.y <= cameraCoordsY + 29)){
					if(menuItemTab == 1) { ItemSelecionado(7);}
					if(menuItemTab == 2) { ItemSelecionado(19);}
					if(menuItemTab == 3) { ItemSelecionado(31);}
					if(menuItemTab == 4) { ItemSelecionado(43);}
					if(menuItemTab == 5) { ItemSelecionado(55);}
					return false;
				}
				
				//Item 9
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 46) && (coordsTouch.y >= cameraCoordsY - 22 && coordsTouch.y <= cameraCoordsY + 3)){
					if(menuItemTab == 1) { ItemSelecionado(8);}
					if(menuItemTab == 2) { ItemSelecionado(20);}
					if(menuItemTab == 3) { ItemSelecionado(32);}
					if(menuItemTab == 4) { ItemSelecionado(44);}
					if(menuItemTab == 5) { ItemSelecionado(56);}
					return false;
				}
				
				//Item 10
				if((coordsTouch.x >= cameraCoordsX - 45 && coordsTouch.x <= cameraCoordsX - 32) && (coordsTouch.y >= cameraCoordsY - 22 && coordsTouch.y <= cameraCoordsY + 3)){
					if(menuItemTab == 1) { ItemSelecionado(9);}
					if(menuItemTab == 2) { ItemSelecionado(21);}
					if(menuItemTab == 3) { ItemSelecionado(33);}
					if(menuItemTab == 4) { ItemSelecionado(45);}
					if(menuItemTab == 5) { ItemSelecionado(57);}
					return false;
				}
				
				//Item 11
				if((coordsTouch.x >= cameraCoordsX - 31 && coordsTouch.x <= cameraCoordsX - 18) && (coordsTouch.y >= cameraCoordsY - 22 && coordsTouch.y <= cameraCoordsY + 3)){
					if(menuItemTab == 1) { ItemSelecionado(10);}
					if(menuItemTab == 2) { ItemSelecionado(22);}
					if(menuItemTab == 3) { ItemSelecionado(34);}
					if(menuItemTab == 4) { ItemSelecionado(46);}
					if(menuItemTab == 5) { ItemSelecionado(58);}
					return false;
				}
				
				//Item 12
				if((coordsTouch.x >= cameraCoordsX - 17 && coordsTouch.x <= cameraCoordsX - 4) && (coordsTouch.y >= cameraCoordsY - 22 && coordsTouch.y <= cameraCoordsY + 3)){
					if(menuItemTab == 1) { ItemSelecionado(11);}
					if(menuItemTab == 2) { ItemSelecionado(23);}
					if(menuItemTab == 3) { ItemSelecionado(35);}
					if(menuItemTab == 4) { ItemSelecionado(47);}
					if(menuItemTab == 5) { ItemSelecionado(59);}
					return false;
				}
				
				//Mudar Aba 1
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 54) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					menuItemTab = 1;
					return false;
				}
				//Mudar Aba 2
				if((coordsTouch.x >= cameraCoordsX - 53 && coordsTouch.x <= cameraCoordsX - 48) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					menuItemTab = 2;
					return false;
				}
				//Mudar Aba 3
				if((coordsTouch.x >= cameraCoordsX - 47 && coordsTouch.x <= cameraCoordsX - 41) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					menuItemTab = 3;
					return false;
				}
				//Mudar Aba 4
				if((coordsTouch.x >= cameraCoordsX - 40 && coordsTouch.x <= cameraCoordsX - 35) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					menuItemTab = 4;
					return false;
				}
				//Mudar Aba 5
				if((coordsTouch.x >= cameraCoordsX - 34 && coordsTouch.x <= cameraCoordsX - 28) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					menuItemTab = 5;
					return false;
				}
				
				//Descartar
				if((coordsTouch.x >= cameraCoordsX - 25 && coordsTouch.x <= cameraCoordsX) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					
					return false;
				}
				
				//Descricao
				if((coordsTouch.x >= cameraCoordsX + 4 && coordsTouch.x <= cameraCoordsX + 30) && (coordsTouch.y >= cameraCoordsY - 38 && coordsTouch.y <= cameraCoordsY - 25)){
					
					return false;
				}
							
			}
			
			if(menuBlock == 4) {  // Habilidades
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
			}
			
			if(menuBlock == 5) {  // Social
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
				
				//Criar Grupo
				if((coordsTouch.x >= cameraCoordsX - 56 && coordsTouch.x <= cameraCoordsX - 27) && (coordsTouch.y >= cameraCoordsY - 15 && coordsTouch.y <= cameraCoordsY - 2)){				
					partyState = true;
					Gdx.input.getTextInput(this,"Digite o Nome do Grupo","",""); 
					return false;
				}
			}
			
			if(menuBlock == 7) { // Battle
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
				
				//Botão para Cidade
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 20) && (coordsTouch.y >= cameraCoordsY + 32 && coordsTouch.y <= cameraCoordsY + 53)){
					activePlayer.PX_A = "11";
					activePlayer.PY_A = "12";
					gameControl.WriteDataCharacterActive();
					game.loadingmanager.screenSwitch("MetroStation");
					return false;
				}
				//Botão para Foresta
				if((coordsTouch.x >= cameraCoordsX - 59 && coordsTouch.x <= cameraCoordsX - 20) && (coordsTouch.y >= cameraCoordsY + 6 && coordsTouch.y <= cameraCoordsY + 28)){
					activePlayer.PX_A = "84";
					activePlayer.PY_A = "73";
					gameControl.WriteDataCharacterActive();
					game.loadingmanager.screenSwitch("ForestArea");
					return false;
				}			
			}
			
			if(menuBlock == 8) {
				//Voltar
				if((coordsTouch.x >= cameraCoordsX + 56 && coordsTouch.x <= cameraCoordsX + 67) && (coordsTouch.y >= cameraCoordsY + 62 && coordsTouch.y <= cameraCoordsY + 88)){
					menuBlock = 1;
					return false;
				}
				//Trocar Personagem
				if((coordsTouch.x >= cameraCoordsX + 18 && coordsTouch.x <= cameraCoordsX + 66) && (coordsTouch.y >= cameraCoordsY + 33 && coordsTouch.y <= cameraCoordsY + 45)){
					gameControl.WriteDataCharacterActive();
					game.loadingmanager.screenSwitch("CharacterSelect");
					return false;
				}
				//Fazer Upload
				if((coordsTouch.x >= cameraCoordsX + 18 && coordsTouch.x <= cameraCoordsX + 66) && (coordsTouch.y >= cameraCoordsY + 15 && coordsTouch.y <= cameraCoordsY + 30)){
					try {
						gameControl.GerenciamentoOnline("Upload", activePlayer.Account);
						configMsg = "Upload feito com sucesso";
					} catch (IOException e) {
						configMsg = "Falha ao tentar fazer upload";
						e.printStackTrace();
					}
					return false;
				}
				
				//Som Ligado
				if((coordsTouch.x >= cameraCoordsX - 38 && coordsTouch.x <= cameraCoordsX - 13) && (coordsTouch.y >= cameraCoordsY + 36 && coordsTouch.y <= cameraCoordsY + 44)){
					//TODO
					return false;
				}
				//Som Desligado
				if((coordsTouch.x >= cameraCoordsX - 11 && coordsTouch.x <= cameraCoordsX + 13) && (coordsTouch.y >= cameraCoordsY + 36 && coordsTouch.y <= cameraCoordsY + 44)){
					//TODO
					return false;
				}
				//Online Ligado
				if((coordsTouch.x >= cameraCoordsX - 38 && coordsTouch.x <= cameraCoordsX - 13) && (coordsTouch.y >= cameraCoordsY + 22 && coordsTouch.y <= cameraCoordsY + 31)){
					configMsg = "Online Ligado";
					onlineState = true;
					gameControl.OperacaoOnline("Sincronizar", "");
					return false;
				}
				//Online Desligado
				if((coordsTouch.x >= cameraCoordsX - 11 && coordsTouch.x <= cameraCoordsX + 13) && (coordsTouch.y >= cameraCoordsY + 22 && coordsTouch.y <= cameraCoordsY + 31)){
					configMsg = "Online Desligado";
					onlineState = false;
					gameControl.OperacaoOnline("Desligar", "");
					return false;
				}
				//Alcance Ligado
				if((coordsTouch.x >= cameraCoordsX - 38 && coordsTouch.x <= cameraCoordsX - 13) && (coordsTouch.y >= cameraCoordsY + 8 && coordsTouch.y <= cameraCoordsY + 19)){
					//TODO
					return false;
				}
				//Alcance Desligado
				if((coordsTouch.x >= cameraCoordsX - 11 && coordsTouch.x <= cameraCoordsX + 13) && (coordsTouch.y >= cameraCoordsY + 8 && coordsTouch.y <= cameraCoordsY + 19)){
					//TODO
					return false;
				}
				//Controle por Toque
				if((coordsTouch.x >= cameraCoordsX - 55 && coordsTouch.x <= cameraCoordsX - 31) && (coordsTouch.y >= cameraCoordsY - 30 && coordsTouch.y <= cameraCoordsY - 7)){
					//TODO
					return false;
				}
				//Controle por Analogico
				if((coordsTouch.x >= cameraCoordsX - 28 && coordsTouch.x <= cameraCoordsX - 2) && (coordsTouch.y >= cameraCoordsY - 7 && coordsTouch.y <= cameraCoordsY - 30)){
					//
					return false;
				}
				
			}
		}
		
		if(shopState) {
			//Fechar
			if((coordsTouch.x >= cameraCoordsX + 78 && coordsTouch.x <= cameraCoordsX + 89) && (coordsTouch.y >= cameraCoordsY + 100 && coordsTouch.y <= cameraCoordsY + 115)) {
				shopState = false;
				shopName = "";
			}
			
			//ShopItem 1
			if((coordsTouch.x >= cameraCoordsX + 4 && coordsTouch.x <= cameraCoordsX + 18) && (coordsTouch.y >= cameraCoordsY + 68 && coordsTouch.y <= cameraCoordsY + 89)) {
				gameControl.VerificaItemCompra(shopName,1);
			}
			//ShopItem 2
			if((coordsTouch.x >= cameraCoordsX + 26 && coordsTouch.x <= cameraCoordsX + 40) && (coordsTouch.y >= cameraCoordsY + 68 && coordsTouch.y <= cameraCoordsY + 89)) {
				gameControl.VerificaItemCompra(shopName,2);
			}
			//ShopItem 3
			if((coordsTouch.x >= cameraCoordsX + 49 && coordsTouch.x <= cameraCoordsX + 63) && (coordsTouch.y >= cameraCoordsY + 68 && coordsTouch.y <= cameraCoordsY + 89)) {
				gameControl.VerificaItemCompra(shopName,3);
			}
			//ShopItem 4
			if((coordsTouch.x >= cameraCoordsX + 71 && coordsTouch.x <= cameraCoordsX + 85) && (coordsTouch.y >= cameraCoordsY + 68 && coordsTouch.y <= cameraCoordsY + 89)) {
				gameControl.VerificaItemCompra(shopName,4);
			}
			//ShopItem 5
			if((coordsTouch.x >= cameraCoordsX + 4 && coordsTouch.x <= cameraCoordsX + 18) && (coordsTouch.y >= cameraCoordsY + 39 && coordsTouch.y <= cameraCoordsY + 59)) {
				gameControl.VerificaItemCompra(shopName,5);
			}
			//ShopItem 6
			if((coordsTouch.x >= cameraCoordsX + 26 && coordsTouch.x <= cameraCoordsX + 40) && (coordsTouch.y >= cameraCoordsY + 39 && coordsTouch.y <= cameraCoordsY + 59)) {
				gameControl.VerificaItemCompra(shopName,6);
			}
			//ShopItem 7
			if((coordsTouch.x >= cameraCoordsX + 49 && coordsTouch.x <= cameraCoordsX + 63) && (coordsTouch.y >= cameraCoordsY + 39 && coordsTouch.y <= cameraCoordsY + 59)) {
				gameControl.VerificaItemCompra(shopName,7);
			}
			//ShopItem 8
			if((coordsTouch.x >= cameraCoordsX + 71 && coordsTouch.x <= cameraCoordsX + 85) && (coordsTouch.y >= cameraCoordsY + 39 && coordsTouch.y <= cameraCoordsY + 59)) {
				gameControl.VerificaItemCompra(shopName,8);
			}
			//ShopItem 9
			if((coordsTouch.x >= cameraCoordsX + 4 && coordsTouch.x <= cameraCoordsX + 18) && (coordsTouch.y >= cameraCoordsY + 10 && coordsTouch.y <= cameraCoordsY + 31)) {
				gameControl.VerificaItemCompra(shopName,9);
			}
			//ShopItem 10
			if((coordsTouch.x >= cameraCoordsX + 26 && coordsTouch.x <= cameraCoordsX + 40) && (coordsTouch.y >= cameraCoordsY + 10 && coordsTouch.y <= cameraCoordsY + 31)) {
				gameControl.VerificaItemCompra(shopName,10);
			}
			//ShopItem 11
			if((coordsTouch.x >= cameraCoordsX + 49 && coordsTouch.x <= cameraCoordsX + 63) && (coordsTouch.y >= cameraCoordsY + 10 && coordsTouch.y <= cameraCoordsY + 31)) {
				gameControl.VerificaItemCompra(shopName,11);
			}
			//ShopItem 12
			if((coordsTouch.x >= cameraCoordsX + 71 && coordsTouch.x <= cameraCoordsX + 85) && (coordsTouch.y >= cameraCoordsY + 10 && coordsTouch.y <= cameraCoordsY + 31)) {
				gameControl.VerificaItemCompra(shopName,12);
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
		camera.position.set(cameraCoordsX,cameraCoordsY,0);
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
	
	

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

}
