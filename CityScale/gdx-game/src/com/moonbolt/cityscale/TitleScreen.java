package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TitleScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
////MAINLY///
	private MainGame game;
	private String[] config;
	private GameControl gameControl;
	private String platform;
	
	//Primitives
	private boolean check = false;
	private String text = "";
	private float posXmetro = -100;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

	//Audio
	private Music sound_select;
	
	//Sprites
	private Sprite spr_master;
	
	//Background
	private Texture tex_sky;
	private Texture tex_background;
	private Texture tex_metro;
	
	private Sprite spr_sky;
	private Sprite spr_background;
	private Sprite spr_metro;
	
	//fonts
	private BitmapFont font_master;
	
	//Barra Acesso
	private Texture tex_BarraAcesso;
	
	private Sprite spr_BarraAcesso;
	
	//teste
	Sprite spr_teste;
	Sprite spr_teste2;
	Sprite spr_teste3;
	Texture tex_teste;
	
	public TitleScreen(MainGame gameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
		this.game = gameAlt;
		this.config = configAlt;
		this.gameControl = controlAlt;
		this.platform = platformAlt;
		
		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//Audio 
		sound_select = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/sound/optionselected.mp3"));
			
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		//Sprites
		tex_sky = new Texture(Gdx.files.internal("data/assets/skytitlescreen.png"));
		spr_sky = new Sprite(tex_sky);
		spr_sky.setPosition(0, 70);
		spr_sky.setSize(100,30);
		
		tex_BarraAcesso = new Texture(Gdx.files.internal("data/interface/charactercreator/barralogin.png"));
		spr_BarraAcesso = new Sprite(tex_BarraAcesso);
		
		tex_background = new Texture(Gdx.files.internal("data/assets/titlescreen.png"));
		spr_background = new Sprite(tex_background);
		spr_background.setPosition(0,0);
		spr_background.setSize(100,100);
		
		tex_metro = new Texture(Gdx.files.internal("data/assets/metro.png"));
		spr_metro = new Sprite(tex_metro);
		spr_metro.setPosition(-100,76);
		spr_metro.setSize(60,20);
		
		
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
		posXmetro = posXmetro + 0.8f;	
		if(posXmetro > 200) { posXmetro = -100;}
		spr_metro.setPosition(posXmetro, 76);
		
		//Render Graphics
		spr_sky.draw(game.batch);
		spr_metro.draw(game.batch);
		spr_background.draw(game.batch);
		
		spr_BarraAcesso.setPosition(72, 2);
		spr_BarraAcesso.setSize(30, 35);
		spr_BarraAcesso.draw(game.batch);
		
		//Check option Select
		if(check == true){		
		    game.AtualizaElementos(game, config, gameControl, platform);
		    game.Switch("CharacterSelect");			
		}
		
		spr_teste.setPosition(72, 14);
		spr_teste2.setPosition(99, 2);
		//spr_teste.draw(game.batch);
		//spr_teste2.draw(game.batch);	
		game.batch.end();
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
				
		//Criar nova Conta
		if((coordsTouch.x >= 72 && coordsTouch.x <= 99) && (coordsTouch.y >= 28 && coordsTouch.y <= 35)){
			gameControl.CreateNewData();
			check = true;
		}
		//Recuperar do Backup
		if((coordsTouch.x >= 72 && coordsTouch.x <= 99) && (coordsTouch.y >= 2 && coordsTouch.y <= 14)){
			Gdx.input.getTextInput(this,"Digite o código","",""); 
		}	
			
		
		return false;
	}
	
	@Override
	public void input(String input){
		text = input;		
		gameControl.OperacaoOnline("Download", text);
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
