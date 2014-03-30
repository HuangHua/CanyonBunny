package com.packtpub.libgdx.CanyonBunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.CanyonBunny.Game.Assets;
import com.packtpub.libgdx.CanyonBunny.Game.WorldController;
import com.packtpub.libgdx.CanyonBunny.Game.WorldRenderer;

public class CanyonBunnyMain implements ApplicationListener {
	private static final String TAG = CanyonBunnyMain.class.getName();
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	@Override
	public void create() {		
		// Set LigGdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		paused = false;
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

	@Override
	public void render() {		
		if(!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		
		// Set clear screen color to: CornFlower blue
		Gdx.gl.glClearColor(0x64/255.f, 0x95/255.f, 0xed/255.f, 0xff/255.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		paused = false;
	}
}
