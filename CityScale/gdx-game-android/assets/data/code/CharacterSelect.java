package com.moonbolt.cityscale;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.Input.TextInputListener;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	////MAINLY///
	private MainGame game;
	private String[] config;
	private GameControl gameControl;
	private String platform;
	
	//Primitives
	private boolean check = false;
	private String nome = "";
	private String sex = "M";
	private String hair = "hair1";
	private String aviso = "";
	private float posY = 0;
	private float posWindowY = 47;
	private float posCorrimao = 50;
	private boolean selectOption = true;
	private boolean deleteCharacter = false;
	private boolean createCharacter = false;
	private boolean selectedCharacter = false;
	private boolean loadingHairs = true;
	private boolean loadCreatedCharacter = false;
	private int countWindow = 1;
	private int hairCount = 0;
	private int flipCreate = 1;
	private String[] charData;
	private int showNumberCharacter = 0;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

	//Audio
	private Music sound_select;

	//fonts
	private BitmapFont font_master;

	//Sprites
	//Title
	private Texture tex_Title;
	private Sprite spr_Title;
	
	private Texture tex_TitleDelete;
	private Sprite spr_TitleDelete;
	
	private Texture tex_Voltar;
	private Sprite spr_Voltar;
	
	private Texture tex_BtnCriar;
	private Texture tex_BtnDeletar;
	private Texture tex_MenuCriacao;
	private Texture tex_Window1;
	private Texture tex_Window2;
	private Texture tex_Window3;
	private Texture tex_Corrimao;
	private Texture tex_LuzClara;
	private Texture tex_LuzEscura;
	private Texture tex_checkM;
	private Texture tex_checkF;
	
	private Sprite spr_BtnCriar;
	private Sprite spr_BtnDeletar;
	
	private Sprite spr_MenuCriacao;
	
	private Sprite spr_Window;
	private Sprite spr_Window2;
	
	private Texture tex_TableInfo;
	private Sprite spr_TableInfo;
	
	private Sprite spr_corrimao;
	private Sprite spr_LuzClara;
	private Sprite spr_LuzEscura;
	
	private Sprite spr_master;
	private Sprite spr_checkM;
	private Sprite spr_checkF;
	
	//hairs
	private Sprite spr_hair1;
	private Sprite spr_hair2;
	private Sprite spr_hair3;
	private Sprite spr_hair4;
	private Sprite spr_hair5;
	private Sprite spr_hair6;
	private Sprite spr_hair7;
	private Sprite spr_hair8;
	private Sprite spr_hair9;
	private Sprite spr_hair10;
	private Sprite spr_hair11;
	
	//body
	private Sprite spr_player;
	private Sprite spr_hair;
	private Sprite spr_hat;
	
	//Background
	private Texture tex_background;
	private Sprite spr_background;
	
	//teste
	private Sprite spr_teste;
	private Sprite spr_teste2;
	private Texture tex_teste;
	
	
	public CharacterSelect(MainGame gameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
		this.game = gameAlt;
		this.config = configAlt;
		this.gameControl = controlAlt;
		this.platform = platformAlt;
		
		charData = new String[50];
		
		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.07f,0.10f);
		font_master.setUseIntegerPositions(false);
		
		tex_background = new Texture(Gdx.files.internal("data/assets/characterSelect.png"));
		spr_background = new Sprite(tex_background);
		spr_background.setPosition(0,0);
		spr_background.setSize(100,100); 
		
		tex_Title = new Texture(Gdx.files.internal("data/interface/charactercreator/InfoSelecionePersonagem.png"));
		spr_Title = new Sprite(tex_Title);
		spr_Title.setPosition(0,0);
		spr_Title.setSize(30,10);
		
		tex_TitleDelete = new Texture(Gdx.files.internal("data/interface/charactercreator/InfoDeletarPersonagem.png"));
		spr_TitleDelete = new Sprite(tex_TitleDelete);
		spr_TitleDelete.setPosition(0,0);
		spr_TitleDelete.setSize(30,10);
		
		tex_BtnCriar = new Texture(Gdx.files.internal("data/interface/charactercreator/btnCriarNovo.png"));
		spr_BtnCriar = new Sprite(tex_BtnCriar);
		spr_BtnCriar.setPosition(0,0);
		spr_BtnCriar.setSize(30,10);
		
		tex_Voltar = new Texture(Gdx.files.internal("data/interface/charactercreator/btnVoltar.png"));
		spr_Voltar = new Sprite(tex_Voltar);
		spr_Voltar.setPosition(0,0);
		spr_Voltar.setSize(30,10);
			
		tex_BtnDeletar = new Texture(Gdx.files.internal("data/interface/charactercreator/btnDeletar.png"));
		spr_BtnDeletar = new Sprite(tex_BtnDeletar);
		spr_BtnDeletar.setPosition(0,0);
		spr_BtnDeletar.setSize(30,10);
		
		tex_MenuCriacao = new Texture(Gdx.files.internal("data/interface/charactercreator/criandopersonagem.png"));
		spr_MenuCriacao = new Sprite(tex_MenuCriacao);
		spr_MenuCriacao.setPosition(0,0);
		spr_MenuCriacao.setSize(30,10);
		
		tex_TableInfo = new Texture(Gdx.files.internal("data/interface/charactercreator/infochar.png"));
		spr_TableInfo = new Sprite(tex_TableInfo);
		spr_TableInfo.setPosition(0,0);
		spr_TableInfo.setSize(30,10);
		
		tex_checkM = new Texture(Gdx.files.internal("data/assets/check.png"));
		spr_checkM = new Sprite(tex_checkM);
		spr_checkM.setPosition(0,0);
		spr_checkM.setSize(10,10);
		
		tex_checkF = new Texture(Gdx.files.internal("data/assets/check.png"));
		spr_checkF = new Sprite(tex_checkF);
		spr_checkF.setPosition(0,0);
		spr_checkF.setSize(10,10);
		
		tex_Window1 = new Texture(Gdx.files.internal("data/assets/window1.png"));
		tex_Window2 = new Texture(Gdx.files.internal("data/assets/window2.png"));
		tex_Window3 = new Texture(Gdx.files.internal("data/assets/window3.png"));
		spr_Window = new Sprite(tex_Window1);
		spr_Window.setPosition(0,0);
		spr_Window.setSize(30,10);
		spr_Window2 = new Sprite(tex_Window1);
		spr_Window2.setPosition(0,0);
		spr_Window2.setSize(30,10);
		
		tex_Corrimao = new Texture(Gdx.files.internal("data/assets/barratrain.png"));
		tex_LuzClara = new Texture(Gdx.files.internal("data/assets/luzclara.png"));
		tex_LuzEscura = new Texture(Gdx.files.internal("data/assets/luzescura.png"));
		
		spr_corrimao = new Sprite(tex_Corrimao);
		spr_corrimao.setPosition(0,0);
		spr_corrimao.setSize(30,10);
		
		spr_LuzClara = new Sprite(tex_LuzClara);
		spr_LuzClara.setPosition(10,15);
		spr_LuzClara.setSize(30,23);
		
		spr_LuzEscura = new Sprite(tex_LuzEscura);
		spr_LuzEscura.setPosition(0,0);
		spr_LuzEscura.setSize(30,10);
		
		
		//Teste dot
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_teste = new Sprite(tex_teste);
		spr_teste.setSize(1,1);	
		spr_teste2 = new Sprite(tex_teste);
		spr_teste2.setSize(1,1);
		
	}
	
	@Override
	public void render(float p1)
	{	
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		//Animate Metro
		AnimationMetro();
		
		spr_background.draw(game.batch);
		spr_Window.draw(game.batch);
		spr_Window2.draw(game.batch);
		
		if(selectedCharacter) {
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(10,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(37,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(67,7);
			spr_LuzClara.draw(game.batch);
			
			spr_Title.setPosition(5, 85);
			spr_Title.setSize(40, 10);
			spr_Title.draw(game.batch);
			
			PlaceSelectedCharacter();	
			
			spr_TableInfo.setPosition(65, 70);
			spr_TableInfo.setSize(30, 25);
			spr_TableInfo.draw(game.batch);
			
			if(showNumberCharacter == 1) {
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.05f,0.08f);
				font_master.setUseIntegerPositions(false);
				
				font_master.draw(game.batch,charData[20],70,88.5f);
				font_master.draw(game.batch,charData[22],71,84.5f);
				font_master.draw(game.batch,charData[21],70,80.5f);
				font_master.draw(game.batch,charData[23],70,76.5f);			
			}
			if(showNumberCharacter == 2) {
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.10f);
				font_master.setUseIntegerPositions(false);
				
				font_master.draw(game.batch,charData[30],70,88.5f);
				font_master.draw(game.batch,charData[31],71,84.5f);
				font_master.draw(game.batch,charData[32],70,80.5f);
				font_master.draw(game.batch,charData[33],70,76.5f);			
			}
			if(showNumberCharacter == 3) {
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.07f,0.10f);
				font_master.setUseIntegerPositions(false);
				
				font_master.draw(game.batch,charData[40],70,88.5f);
				font_master.draw(game.batch,charData[42],71,84.5f);
				font_master.draw(game.batch,charData[41],70,80.5f);
				font_master.draw(game.batch,charData[43],70,76.5f);			
			}
			
			spr_Voltar.setPosition(-1, 67);
			spr_Voltar.setSize(10, 15);
			spr_Voltar.draw(game.batch);
			
			font_master.setColor(Color.RED);
		}
		
		if(selectOption) {
					
			spr_corrimao.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(10,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(37,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(67,7);
			spr_LuzClara.draw(game.batch);
			
			spr_Title.setPosition(5, 85);
			spr_Title.setSize(40, 10);
			spr_Title.draw(game.batch);
			
			spr_BtnCriar.setPosition(90, 40);
			spr_BtnCriar.setSize(10, 15);
			spr_BtnCriar.draw(game.batch);
			
			spr_BtnDeletar.setPosition(90, 25);
			spr_BtnDeletar.setSize(10, 15);
			spr_BtnDeletar.draw(game.batch);
			
			
			PlaceCreatedCharacters();
			
		}
		
		if(createCharacter) {
			
			spr_MenuCriacao.setSize(90, 80);
			spr_MenuCriacao.setPosition(5, 10);
			spr_MenuCriacao.draw(game.batch);
			PlaceHairs();
			PlaceCharacterCreateScreen();
			font_master.setColor(Color.RED);
			font_master.getData().setScale(0.09f,0.13f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch,nome,48,81);
			font_master.draw(game.batch,aviso,68,80);
					
			if(sex.equals("M")) { spr_checkM.setSize(2,3);  spr_checkM.setPosition(46,67.5f); spr_checkM.draw(game.batch); }
			if(sex.equals("F")) { spr_checkF.setSize(2,3);  spr_checkF.setPosition(58,67.5f); spr_checkF.draw(game.batch); }
		}
		
		if(deleteCharacter) {
			
			spr_Voltar.setPosition(-1, 67);
			spr_Voltar.setSize(10, 15);
			spr_Voltar.draw(game.batch);
			
			spr_corrimao.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(10,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(37,7);
			spr_LuzClara.draw(game.batch);
			
			spr_LuzClara.setSize(22,55);
			spr_LuzClara.setPosition(67,7);
			spr_LuzClara.draw(game.batch);
			
			spr_TitleDelete.setSize(40, 10);
			spr_TitleDelete.setPosition(5, 85);
			spr_TitleDelete.draw(game.batch);
			
			PlaceCreatedCharacters();
		}
		
		
		
		//Check option Select
		if(check == true){		
		    game.AtualizaElementos(game, config, gameControl, platform);	    		
		}

		//spr_teste.setPosition(17, 45);
		//spr_teste2.setPosition(26, 45);
		//spr_teste.draw(game.batch);
		//spr_teste2.draw(game.batch);	
		game.batch.end();
	}
	
	private void PlaceCreatedCharacters() {
		
		if(!loadCreatedCharacter) { 
			gameControl.LoadData(); 
			loadCreatedCharacter = true; 
		}
		
		charData = gameControl.LoadShowCharSelectScreen();
		
		if(!charData[0].equals("none")){		
			if(charData[45].equals("M")) {
				spr_player = gameControl.MovChar(charData[3], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[12], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13f, 10);
				spr_hair.setPosition(18, 32.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[45].equals("F")) {
				spr_player = gameControl.MovChar(charData[3], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[12], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13f, 10f);
				spr_hair.setPosition(17.8f, 30.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
		
		if(!charData[1].equals("none")){
			if(charData[46].equals("M")) {
				spr_player = gameControl.MovChar(charData[6], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[13], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(39f, 10);
				spr_hair.setPosition(44, 32.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[46].equals("F")) {
				spr_player = gameControl.MovChar(charData[6], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[13], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(40, 10f);
				spr_hair.setPosition(44.8f, 30.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
		
		if(!charData[2].equals("none")){
			if(charData[47].equals("M")) {
				spr_player = gameControl.MovChar(charData[9], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[14], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(68, 10);
				spr_hair.setPosition(73, 32.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[47].equals("F")) {
				spr_player = gameControl.MovChar(charData[9], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[14], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(68.5f, 10f);
				spr_hair.setPosition(73.3f, 30.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
	}
	
	private void PlaceSelectedCharacter() {
		
		charData = gameControl.LoadShowCharSelectScreen();
		
		
		if(showNumberCharacter == 1){
			if(charData[45].equals("M")) {
				spr_player = gameControl.MovChar(charData[3], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[12], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13f, 10);
				spr_hair.setPosition(18, 32.5f);

				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[45].equals("F")) {
				spr_player = gameControl.MovChar(charData[3], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[12], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);

				spr_player.setPosition(13f, 10f);
				spr_hair.setPosition(17.8f, 30.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
		
		if(showNumberCharacter == 2){
			if(charData[46].equals("M")) {
				spr_player = gameControl.MovChar(charData[6], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[13], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(39f, 10);
				spr_hair.setPosition(44, 32.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[46].equals("F")) {
				spr_player = gameControl.MovChar(charData[6], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[13], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);

				spr_player.setPosition(40, 10f);
				spr_hair.setPosition(44.8f, 30.5f);

				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
		
		if(showNumberCharacter == 3){
			if(charData[47].equals("M")) {
				spr_player = gameControl.MovChar(charData[9], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[14], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(68, 10);
				spr_hair.setPosition(73, 32.5f);
				
				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
			if(charData[47].equals("F")) {
				spr_player = gameControl.MovChar(charData[9], "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(charData[14], "Front","Stop",0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);

				spr_player.setPosition(68.5f, 10f);
				spr_hair.setPosition(73.3f, 30.5f);

				spr_player.draw(game.batch);
				spr_hair.draw(game.batch);
			}
		}
	}
	
	private void PlaceHairs() {
		
		//spr_master
		if(loadingHairs && sex.equals("M")) {
			spr_hair1 = gameControl.ReturnHairs("hair1", "Front","Stop", 0,0);
			spr_hair1.setPosition(41.5f, 44.8f);
			spr_hair1.setSize(8, 12);
			
			spr_hair2 = gameControl.ReturnHairs("hair2", "Front","Stop", 0,0);
			spr_hair2.setPosition(48.3f, 44.8f);
			spr_hair2.setSize(8, 10);
			
			spr_hair3 = gameControl.ReturnHairs("hair3", "Front","Stop", 0,0);
			spr_hair3.setPosition(54.6f, 44.8f);
			spr_hair3.setSize(8, 10);
			
			spr_hair4 = gameControl.ReturnHairs("hair4", "Front","Stop", 0,0);
			spr_hair4.setPosition(61.4f, 44.8f);
			spr_hair4.setSize(8, 10);
			
			spr_hair5 = gameControl.ReturnHairs("hair5", "Front","Stop", 0,0);
			spr_hair5.setPosition(68.1f, 44.8f);
			spr_hair5.setSize(8, 10);
			
			spr_hair6 = gameControl.ReturnHairs("hair6", "Front","Stop", 0,0);
			spr_hair6.setPosition(74.5f, 44.8f);
			spr_hair6.setSize(8, 10);
			
			spr_hair7 = gameControl.ReturnHairs("hair7", "Front","Stop", 0,0);
			spr_hair7.setPosition(81.5f, 44.8f);
			spr_hair7.setSize(8, 10);
			
			spr_hair8 = gameControl.ReturnHairs("hair8", "Front","Stop", 0,0);
			spr_hair8.setPosition(41.5f, 32.8f);
			spr_hair8.setSize(8, 10);
			
			spr_hair9 = gameControl.ReturnHairs("hair9", "Front","Stop", 0,0);
			spr_hair9.setPosition(48.3f, 32.8f);
			spr_hair9.setSize(8, 10);
			
			spr_hair10 = gameControl.ReturnHairs("hair10", "Front","Stop", 0,0);
			spr_hair10.setPosition(54.6f, 32.8f);
			spr_hair10.setSize(8, 10);
			
			spr_hair11 = gameControl.ReturnHairs("hair11", "Front","Stop", 0,0);
			spr_hair11.setPosition(61.4f, 32.8f);
			spr_hair11.setSize(8, 10);
			
			loadingHairs = false;
			hairCount = 5;
		}
		if(loadingHairs && sex.equals("F")) {
			spr_hair1 = gameControl.ReturnHairs("hair1_f", "Front","Stop", 0,0);
			spr_hair1.setPosition(41.5f, 44.8f);
			spr_hair1.setSize(8, 12);
			
			spr_hair2 = gameControl.ReturnHairs("hair2_f", "Front","Stop", 0,0);
			spr_hair2.setPosition(48.3f, 44.8f);
			spr_hair2.setSize(8, 10);
			
			spr_hair3 = gameControl.ReturnHairs("hair3_f", "Front","Stop", 0,0);
			spr_hair3.setPosition(54.6f, 44.8f);
			spr_hair3.setSize(8, 10);
			
			spr_hair4 = gameControl.ReturnHairs("hair4_f", "Front","Stop", 0,0);
			spr_hair4.setPosition(61.4f, 44.8f);
			spr_hair4.setSize(8, 10);
			
			spr_hair5 = gameControl.ReturnHairs("hair5_f", "Front","Stop", 0,0);
			spr_hair5.setPosition(68.1f, 44.8f);
			spr_hair5.setSize(8, 10);
			
			spr_hair6 = gameControl.ReturnHairs("hair6_f", "Front","Stop", 0,0);
			spr_hair6.setPosition(74.5f, 44.8f);
			spr_hair6.setSize(8, 10);
			
			spr_hair7 = gameControl.ReturnHairs("hair7_f", "Front","Stop", 0,0);
			spr_hair7.setPosition(81.5f, 44.8f);
			spr_hair7.setSize(8, 10);
			
			spr_hair8 = gameControl.ReturnHairs("hair8_f", "Front","Stop", 0,0);
			spr_hair8.setPosition(41.5f, 32.8f);
			spr_hair8.setSize(8, 10);
			
			spr_hair9 = gameControl.ReturnHairs("hair9_f", "Front","Stop", 0,0);
			spr_hair9.setPosition(48.3f, 32.8f);
			spr_hair9.setSize(8, 10);
			
			spr_hair10 = gameControl.ReturnHairs("hair10_f", "Front","Stop", 0,0);
			spr_hair10.setPosition(54.6f, 32.8f);
			spr_hair10.setSize(8, 10);
			
			spr_hair11 = gameControl.ReturnHairs("hair11_f", "Front","Stop", 0,0);
			spr_hair11.setPosition(61.4f, 32.8f);
			spr_hair11.setSize(8, 10);
			
			loadingHairs = false;
			hairCount = 5;
		}
				
		if(!loadingHairs) {
			hairCount++;
			if(hairCount > 1) { spr_hair1.draw(game.batch); }
			if(hairCount > 10) { spr_hair2.draw(game.batch); }
			if(hairCount > 20) { spr_hair3.draw(game.batch); }
			if(hairCount > 30) { spr_hair4.draw(game.batch); }
			if(hairCount > 40) { spr_hair5.draw(game.batch); }
			if(hairCount > 50) { spr_hair6.draw(game.batch); }
			if(hairCount > 60) { spr_hair7.draw(game.batch); }
			if(hairCount > 70) { spr_hair8.draw(game.batch); }
			if(hairCount > 80) { spr_hair9.draw(game.batch); }
			if(hairCount > 90) { spr_hair10.draw(game.batch); }
			if(hairCount > 100) { spr_hair11.draw(game.batch); }
			
			if(hairCount > 100) { hairCount = 120; }
		}
	}
	
	private void PlaceCharacterCreateScreen() {
		
		if(sex.equals("M")) {			
			if(flipCreate >= 0 && flipCreate <= 20) {				
				spr_player = gameControl.MovChar("basic_set_male", "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13, 31);
				spr_hair.setPosition(18, 53f);
			}
			if(flipCreate >= 20 && flipCreate <= 40) {
				spr_player = gameControl.MovChar("basic_set_male", "Front","Stop", "", 0,0,2);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13.5f, 31);
				spr_hair.setPosition(18, 52.9f);
			}
			if(flipCreate >= 40 && flipCreate <= 60) {
				spr_player = gameControl.MovChar("basic_set_male", "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13, 31);
				spr_hair.setPosition(18, 53f);
			}
			if(flipCreate >= 60 && flipCreate <= 80) {
				spr_player = gameControl.MovChar("basic_set_male", "Front","Stop", "", 0,0,3);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(18, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(13.5f, 31);
				spr_hair.setPosition(18, 53.2f);
			}
			
			spr_player.draw(game.batch);
			spr_hair.draw(game.batch);
			
			flipCreate++;
			
			if(flipCreate >= 80) { flipCreate = 0;}
		}	
		
		//flipCreate = 41;
		if(sex.equals("F")) {			
			if(flipCreate >= 0 && flipCreate <= 10) {
				spr_player = gameControl.MovChar("basic_set_female", "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(19, 26);
				spr_hair.setSize(8, 12);
			
				spr_player.setPosition(12.6f, 31.8f);
				spr_hair.setPosition(18, 52.5f);
			}
			if(flipCreate >= 10 && flipCreate <= 20) {
				spr_player = gameControl.MovChar("basic_set_female", "Front","Stop", "", 0,0,2);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(19, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(12.6f, 31.8f);
				spr_hair.setPosition(18, 52.5f);
			}
			if(flipCreate >= 30 && flipCreate <= 40) {
				spr_player = gameControl.MovChar("basic_set_female", "Front","Stop", "", 0,0,1);
				spr_hair = gameControl.ReturnHairs(hair, "Front", "Stop", 0,0);
				
				spr_player.setSize(19, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(12.6f, 31.8f);
				spr_hair.setPosition(18, 52.5f);
			}
			if(flipCreate >= 40 && flipCreate <= 50) {
				spr_player = gameControl.MovChar("basic_set_female", "Front","Stop", "", 0,0,3);
				spr_hair = gameControl.ReturnHairs(hair, "Front","Stop", 0,0);
				
				spr_player.setSize(19, 26);
				spr_hair.setSize(8, 12);
				
				spr_player.setPosition(12.6f, 31.8f);
				spr_hair.setPosition(18, 52.5f);
			}
			
			spr_player.draw(game.batch);
			spr_hair.draw(game.batch);
			
			flipCreate++;
			
			if(flipCreate >= 60) { flipCreate = 0;}
		}
	}
	
	private void AnimationMetro() {
		
		if(posY > 1) { posY = 0; posWindowY = 47; posCorrimao = 50; }
		if(posY <= 1) { posY = posY + 0.01F; posWindowY = posWindowY + 0.01F; posCorrimao = posCorrimao + 0.01F;}
		
		spr_background.setPosition(0,posY);
		spr_background.setSize(100,100);
		
		spr_Window.setPosition(14, posWindowY);
		spr_Window.setSize(22,22);
		spr_Window2.setPosition(66, posWindowY);
		spr_Window2.setSize(22,22);
		
		spr_corrimao.setPosition(0,posCorrimao);
		spr_corrimao.setSize(100, 10);
		
		if(countWindow >= 14 && countWindow < 21) {
			countWindow++;
			spr_Window.setTexture(tex_Window1);
			spr_Window2.setTexture(tex_Window1);
		}
		if(countWindow >= 7 && countWindow < 14) {
			countWindow++;
			spr_Window.setTexture(tex_Window3);
			spr_Window2.setTexture(tex_Window3);
		}
		if(countWindow >= 1 && countWindow < 7) {
			countWindow++;
			spr_Window.setTexture(tex_Window2);
			spr_Window2.setTexture(tex_Window2);
		}
		if(countWindow >= 21) { countWindow = 1;}
	}

	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}	

	//TOUCH RESPONSE	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		String[] charconfig = new String[3];
		//Criar novo Char
		if(selectOption){	
			//Create New
			if((coordsTouch.x >= 90 && coordsTouch.x <= 99) && (coordsTouch.y >= 40 && coordsTouch.y <= 53)){
				createCharacter = true;
				selectOption = false;
				deleteCharacter = false;
				selectedCharacter = false;
				sex = "M";
				hair = "hair1";
				loadingHairs = true;
			}	
			//Delete Char
			if((coordsTouch.x >= 90 && coordsTouch.x <= 99) && (coordsTouch.y >= 25 && coordsTouch.y <= 38)){
				deleteCharacter = true;
				selectOption = false;
				createCharacter = false;
				selectedCharacter = false;
			}
			
			//Char 1 Selected 
			if((coordsTouch.x >= 10 && coordsTouch.x <= 32) && (coordsTouch.y >= 10 && coordsTouch.y <= 50)){
				if(gameControl.CheckExistCharacter(1)) {
					selectedCharacter = true;
					selectOption = false;
					deleteCharacter = false;
					createCharacter = false;
					showNumberCharacter = 1;
				}		
			}
			//Char 2 Selected
			if((coordsTouch.x >= 35 && coordsTouch.x <= 60) && (coordsTouch.y >= 10 && coordsTouch.y <= 20)){
				if(gameControl.CheckExistCharacter(2)) {
					selectedCharacter = true;
					selectOption = false;
					deleteCharacter = false;
					createCharacter = false;
					showNumberCharacter = 2;
				}
			}
			//Char 3 Selected
			if((coordsTouch.x >= 63 && coordsTouch.x <= 89) && (coordsTouch.y >= 10 && coordsTouch.y <= 20)){
				if(gameControl.CheckExistCharacter(3)) {
					selectedCharacter = true;
					selectOption = false;
					deleteCharacter = false;
					createCharacter = false;
					showNumberCharacter = 3;
				}
			}
		}
		
		if(selectedCharacter) {
			//Voltar
			if((coordsTouch.x >= 0 && coordsTouch.x <= 8) && (coordsTouch.y >= 67 && coordsTouch.y <= 80)){
				selectOption = true;
				createCharacter = false;
				deleteCharacter = false;
				selectedCharacter = false;
				
			}
			
			//Acessar
			if((coordsTouch.x >= 87 && coordsTouch.x <= 98) && (coordsTouch.y >= 69 && coordsTouch.y <= 75)){
				game.AtualizaElementos(game, config, gameControl, platform);	    
				if(showNumberCharacter == 1) { gameControl.SetNumberChar(1); game.loadingmanager.screenSwitch(charData[23]); }
				if(showNumberCharacter == 2) { gameControl.SetNumberChar(2); game.loadingmanager.screenSwitch(charData[33]); }
				if(showNumberCharacter == 3) { gameControl.SetNumberChar(3); game.loadingmanager.screenSwitch(charData[43]); }
			}
		}
		
		if(createCharacter){	
			
			//Continuar
			if((coordsTouch.x >= 81 && coordsTouch.x <= 93) && (coordsTouch.y >= 13 && coordsTouch.y <= 20)){
				if(nome.equals("none")){ aviso = "Insira um nome"; return false;}
				if(nome.equals("")) { aviso = "Insira um nome"; return false; }
				if(nome.contains(".")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("-")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains(";")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("'")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("~")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains(":")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("?")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("!")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("-")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("*")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("=")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("@")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("#")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("$")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("%")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("&")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("(")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains(")")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("=")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("/")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("\\")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains("<")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.contains(">")) { aviso = "Nome com caracters especiais"; return false; }
				if(nome.length() > 10) { aviso = "Ate 10 letras"; return false; }
				if(sex.equals("")) { aviso = "Escolha o sexo"; return false; }
				
				charconfig[0] = nome;
				charconfig[1] = hair;
				charconfig[2] = sex;
				
				gameControl.CreateNewCharacter(charconfig);
				loadCreatedCharacter = false;
				createCharacter = false;
				selectOption = true;
				
			}	
			
			//Voltar
			if((coordsTouch.x >= 5 && coordsTouch.x <= 15) && (coordsTouch.y >= 10 && coordsTouch.y <= 20)){
				selectOption = true;
				createCharacter = false;
				sex = "M";
				loadingHairs = true;
			}	
			
			//Nome
			if((coordsTouch.x >= 47.2f && coordsTouch.x <= 66.4f) && (coordsTouch.y >= 75 && coordsTouch.y <= 82)){
				Gdx.input.getTextInput(this,"Digite o nome do personagem","","");
			}
			
			
			//Masculino
			if((coordsTouch.x >= 46 && coordsTouch.x <= 65) && (coordsTouch.y >= 56.2 && coordsTouch.y <= 72)){
				sex = "M";
				hair = "hair1";
				loadingHairs = true;
				
			}
			
			//Feminino
			if((coordsTouch.x >= 58.2f && coordsTouch.x <= 69) && (coordsTouch.y >= 65 && coordsTouch.y <= 72)){
				sex = "F";
				hair = "hair1_f";
				loadingHairs = true;
			}
			
			//Cabelo 1
			if((coordsTouch.x >= 42 && coordsTouch.x <= 47) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair1"; }
				if(sex.equals("F")) { hair = "hair1_f"; }
			}
			//Cabelo 2
			if((coordsTouch.x >= 49 && coordsTouch.x <= 54) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair2"; }
				if(sex.equals("F")) { hair = "hair2_f"; }
			}
			//Cabelo 3
			if((coordsTouch.x >= 55.5f && coordsTouch.x <= 60.5f) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair3"; }
				if(sex.equals("F")) { hair = "hair3_f"; }
			}
			//Cabelo 4
			if((coordsTouch.x >= 62 && coordsTouch.x <= 67) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair4"; }
				if(sex.equals("F")) { hair = "hair4_f"; }
			}
			//Cabelo 5
			if((coordsTouch.x >= 69 && coordsTouch.x <= 74) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair5"; }
				if(sex.equals("F")) { hair = "hair5_f"; }
			}
			//Cabelo 6
			if((coordsTouch.x >= 76 && coordsTouch.x <= 81) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair6"; }
				if(sex.equals("F")) { hair = "hair6_f"; }
			}
			//Cabelo 7
			if((coordsTouch.x >= 82 && coordsTouch.x <= 87) && (coordsTouch.y >= 44 && coordsTouch.y <= 53)){
				if(sex.equals("M")) { hair = "hair7"; }
				if(sex.equals("F")) { hair = "hair7_f"; }
			}
			//Cabelo 8
			if((coordsTouch.x >= 42 && coordsTouch.x <= 47) && (coordsTouch.y >= 32 && coordsTouch.y <= 41)){
				if(sex.equals("M")) { hair = "hair8"; }
				if(sex.equals("F")) { hair = "hair8_f"; }
			}
			//Cabelo 9
			if((coordsTouch.x >= 42 && coordsTouch.x <= 47) && (coordsTouch.y >= 32 && coordsTouch.y <= 41)){
				if(sex.equals("M")) { hair = "hair8"; }
				if(sex.equals("F")) { hair = "hair8_f"; }
			}
			//Cabelo 10
			if((coordsTouch.x >= 49 && coordsTouch.x <= 54) && (coordsTouch.y >= 32 && coordsTouch.y <= 41)){
				if(sex.equals("M")) { hair = "hair9"; }
				if(sex.equals("F")) { hair = "hair9_f"; }
			}
			//Cabelo 11
			if((coordsTouch.x >= 56 && coordsTouch.x <= 61) && (coordsTouch.y >= 32 && coordsTouch.y <= 41)){
				if(sex.equals("M")) { hair = "hair10"; }
				if(sex.equals("F")) { hair = "hair10_f"; }
			}
			//Cabelo 11
			if((coordsTouch.x >= 62 && coordsTouch.x <= 67) && (coordsTouch.y >= 32 && coordsTouch.y <= 41)){
				if(sex.equals("M")) { hair = "hair11"; }
				if(sex.equals("F")) { hair = "hair11_f"; }
			}		
		}
		
		if(deleteCharacter) {
			//Char 1 10 | 32
			if((coordsTouch.x >= 10 && coordsTouch.x <= 32) && (coordsTouch.y >= 10 && coordsTouch.y <= 50)){
				gameControl.DeleteCharacter(1);
				loadCreatedCharacter = false;
			}
			//Char 2 35 | 60
			if((coordsTouch.x >= 35 && coordsTouch.x <= 60) && (coordsTouch.y >= 10 && coordsTouch.y <= 20)){
				gameControl.DeleteCharacter(2);
				loadCreatedCharacter = false;
			}
			//Char 3 63 | 89
			if((coordsTouch.x >= 63 && coordsTouch.x <= 89) && (coordsTouch.y >= 10 && coordsTouch.y <= 20)){
				gameControl.DeleteCharacter(3);
				loadCreatedCharacter = false;
			}
			//Voltar
			if((coordsTouch.x >= 0 && coordsTouch.x <= 8) && (coordsTouch.y >= 67 && coordsTouch.y <= 80)){
				selectOption = true;
				createCharacter = false;
				deleteCharacter = false;
				selectedCharacter = false;
			}
		}
		return false;
	}

	@Override
	public void input(String input){
		nome = input;
	}
	
	@Override
	public void canceled(){
	}

	@Override
	public void create()
	{
		// TODO: Implement this method
	}

	@Override
	public void render()
	{
		// TODO: Implement this method
	}

	@Override
	public void hide()
	{
		// TODO: Implement this method
	}

	@Override
	public void show()
	{
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		gameControl = null;
		sound_select.dispose();
		camera = null;
		viewport = null;
		game.dispose();
	}

	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyDown(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean mouseMoved(int p1, int p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean scrolled(int p1)
	{
		// TODO: Implement this method
		return false;
	}
}
