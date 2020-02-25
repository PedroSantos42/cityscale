package com.moonbolt.cityscale;

import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen{
	ArrayList<Screen> screens;
	private MainGame game;
	private String config[];
	private GameControl control;
	private String platform;
	
	public LoadingScreen(MainGame game){
		this.game = game;
		screens = new ArrayList<Screen>();
		control = new GameControl();
		platform = "Mobile";
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("SplashScreen")){	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("TitleScreen")){	
			TitleScreen titleScreen = new TitleScreen(game, config, control, platform);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("CharacterSelect")) {
			CharacterSelect characterScreen = new CharacterSelect(game, config, control,platform);
			game.setScreen(characterScreen);
		}
		
		if(tipo.equals("MetroStation")) {
			MetroStation metrostationScreen = new MetroStation(game, config, control,platform);
			game.setScreen(metrostationScreen);
		}
		
		if(tipo.equals("Streets305")) {
			Streets305 streets305Screen = new Streets305(game, config, control,platform);
			game.setScreen(streets305Screen);
		}
		
		if(tipo.equals("ForestArea")) {
			ForestArea forestScreen = new ForestArea(game, config, control,platform);
			game.setScreen(forestScreen);
		}
	}
	
	public void atualizaComponentes(MainGame maingameAlt, String[] configAlt, GameControl controlAlt, String platformAlt){
		this.game = maingameAlt;
		this.config = configAlt;
		this.control = controlAlt;
		this.platform = platformAlt;
	}

	@Override
	public void show()
	{
		// TODO: Implement this method
	}

	@Override
	public void render(float p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void resize(int p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
	}
}
