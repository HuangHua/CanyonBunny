package com.packtpub.libgdx.CanyonBunny.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.CanyonBunny.Util.Constants;

public class WorldRenderer implements Disposable 
{
	private static final String TAG = WorldRenderer.class.getName();
	private WorldController worldController;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		init();
	}
	
	private void init()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	public void render()
	{
		renderTestObjects();
	}
	
	private void renderTestObjects() {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite sprite : worldController.testSprites) {
			sprite.draw(batch);
		}
		batch.end();
	}

	public void resize(int width, int height)
	{
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height) * width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
