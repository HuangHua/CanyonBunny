package com.packtpub.libgdx.CanyonBunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;
import com.packtpub.libgdx.CanyonBunny.Game.Assets;
import com.packtpub.libgdx.CanyonBunny.Screens.DirectedGame;
import com.packtpub.libgdx.CanyonBunny.Screens.MenuScreen;
import com.packtpub.libgdx.CanyonBunny.Screens.Transitions.ScreenTransition;
import com.packtpub.libgdx.CanyonBunny.Screens.Transitions.ScreenTransitionSlice;
import com.packtpub.libgdx.CanyonBunny.Util.AudioManager;
import com.packtpub.libgdx.CanyonBunny.Util.GamePreferences;

public class CanyonBunnyMain extends DirectedGame {
	private static final String TAG = CanyonBunnyMain.class.getName();
	
	@Override
	public void create() {		
		// Set LigGdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Load preferences for audio settings and start playing music
        GamePreferences.instance.load();
        AudioManager.instance.play(Assets.instance.music.song01);
		// Start game at menu screen
		ScreenTransition transition = ScreenTransitionSlice.init(2,
				ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
		setScreen(new MenuScreen(this), transition);
	}
}
