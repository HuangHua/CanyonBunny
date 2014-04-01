package com.packtpub.libgdx.CanyonBunny.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.CanyonBunny.Game.WorldController;
import com.packtpub.libgdx.CanyonBunny.Game.WorldRenderer;

public class GameScreen extends AbstractGameScreen {
	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float deltaTime) {
		// Do not update game world when paused.
		if(!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(deltaTime);
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
	public void show() {
		// Initialize controller and renderer
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }

}
