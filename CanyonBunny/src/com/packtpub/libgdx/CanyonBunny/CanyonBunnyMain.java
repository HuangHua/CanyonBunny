package com.packtpub.libgdx.CanyonBunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.CanyonBunny.Game.Assets;
import com.packtpub.libgdx.CanyonBunny.Screens.MenuScreen;

public class CanyonBunnyMain extends Game {
	private static final String TAG = CanyonBunnyMain.class.getName();
	
	@Override
	public void create() {		
		// Set LigGdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}
