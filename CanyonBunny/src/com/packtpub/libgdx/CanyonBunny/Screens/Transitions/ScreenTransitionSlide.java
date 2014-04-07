package com.packtpub.libgdx.CanyonBunny.Screens.Transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class ScreenTransitionSlide implements ScreenTransition {

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	private static final ScreenTransitionSlide instance = new ScreenTransitionSlide();
	private float duration;
	private int direction;
	private boolean slideOut;
	private Interpolation easing;
	
	public static ScreenTransitionSlide init(float duration, int direction
			, boolean slideOut, Interpolation easing) {
		instance.duration = duration;
		instance.direction = direction;
		instance.slideOut = slideOut;
		instance.easing = easing;
		return instance;
	}
	
	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public void render(SpriteBatch batch, Texture curScreen,
			Texture nextScreen, float alpha) {
		float w = curScreen.getWidth();
		float h = curScreen.getHeight();
		float x = 0; 
		float y = 0;
		if(easing != null)
			alpha = easing.apply(alpha);
		// calculate position offset
		switch(direction) {
		case LEFT:
			x = -w * alpha;
			if(!slideOut)
				x += w;
			break;
		case RIGHT:
			x = w * alpha;
			if(!slideOut)
				x -= w;
			break;
		case UP:
			y = h * alpha;
			if(!slideOut)
				y -= h;
			break;
		case DOWN:
			y = -h * alpha;
			if(!slideOut)
				y += h;
			break;
		}
		// draw order depends on slide type ('in' or 'out')
		Texture textBottom = slideOut ? nextScreen : curScreen;
		Texture textTop = slideOut ? curScreen : nextScreen;
		// finally, draw both screens
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(textBottom, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0,
				textBottom.getWidth(), textBottom.getHeight(), false, true);
		batch.draw(textTop, x, y, 0, 0, w, h, 1, 1, 0, 0, 0,
				textTop.getWidth(), textTop.getHeight(), false, true);
		batch.end();
	}

}
