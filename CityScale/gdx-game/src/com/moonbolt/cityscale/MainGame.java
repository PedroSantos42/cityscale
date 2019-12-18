package com.moonbolt.cityscale;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MainGame extends Game {
public MainGame(){};
	
	SpriteBatch batch;
	
	public LoadingScreen loadingmanager;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		loadingmanager = new LoadingScreen(this);
		this.Switch("SplashScreen");
	}
	
	public void Switch(String screenName) {
		loadingmanager.screenSwitch(screenName);
	}
	
	public void AtualizaElementos(MainGame mainGameAlt, String[] configAlt, GameControl controlAlt, String platformAlt) {
		loadingmanager.atualizaComponentes( mainGameAlt, configAlt, controlAlt, platformAlt );
	}

	@Override
	public void render()
	{   
	    super.render();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}
}
