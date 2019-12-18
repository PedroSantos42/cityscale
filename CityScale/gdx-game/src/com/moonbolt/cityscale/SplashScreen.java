package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SplashScreen implements Screen, ApplicationListener, InputProcessor {
	private MainGame game;
	private LoadingScreen screen;
	private OrthographicCamera camera;
    private Viewport viewport;
	private Sprite spr_Logo;
	private Texture tex_logo;
	private int fadeInCount;
	private int fadeOutCount;
	private boolean interpolation;
	private int countEffect;
	
	public SplashScreen(MainGame game, LoadingScreen screen){
		this.screen = screen;
		this.game = game;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		fadeInCount = 1;
		fadeOutCount = 1;
		interpolation = false;
		countEffect = 0;
		tex_logo = new Texture(Gdx.files.internal("data/assets/logo.png"));
		spr_Logo = new Sprite(tex_logo);
		spr_Logo.setPosition(35,34);
		spr_Logo.setSize(30,40);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		
		if(countEffect < 250 && interpolation == false){
			spr_Logo.setAlpha(fadeInCount);
			fadeInCount -= 1;
			countEffect += 1;
		}
		if(countEffect >= 250 && interpolation == false){
			interpolation = true;
			countEffect = 0;
		}
		
		if(countEffect < 250 && interpolation == true){
			spr_Logo.setAlpha(fadeOutCount);
			fadeOutCount += 1;
		    countEffect += 1;
		}
		
		if(countEffect >= 250 && interpolation == true){
			this.screen.screenSwitch("TitleScreen");
			dispose();
		}
		
		game.batch.begin();
		spr_Logo.draw(game.batch);
		game.batch.end();
		
	}
	
	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void hide()
	{
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
		game.dispose();
		tex_logo.dispose();
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
	public boolean keyDown(int p1)
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
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

			if(coordsTouch.x >= 0 && coordsTouch.x <= 100 && coordsTouch.y >= 0 && coordsTouch.y <= 100){
			    this.screen.screenSwitch("TitleScreen");
				dispose();
			}
		return false;
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
