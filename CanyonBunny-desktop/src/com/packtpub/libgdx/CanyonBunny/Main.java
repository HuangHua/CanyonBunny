package com.packtpub.libgdx.CanyonBunny;

import java.io.File;
import java.nio.file.FileSystem;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class Main {
	
	private static boolean rebuildAltas = false;
	private static boolean drawDebugOutline = false;
	
	private static void deleteFile(String filename) {
		File file = new File(filename);
		if(file.isFile() && file.exists())
			file.delete();
	}
	public static void main(String[] args) {
		if(rebuildAltas) {
			// delete the packed file first if exists
			deleteFile("../CanyonBunny-android/assets/images/canyonBunny.pack");
			deleteFile("../CanyonBunny-android/assets/images/canyonBunny.png");
			// Pack the images
			Settings settings = new Settings();
			settings.maxHeight = 1024;
			settings.maxWidth = 1024;
			settings.filterMin = TextureFilter.Linear;
			settings.filterMag = TextureFilter.Linear;
			settings.duplicatePadding = true;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images",
					"../CanyonBunny-android/assets/images", "canyonbunny.pack");
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CanyonBunny";
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new CanyonBunnyMain(), cfg);
	}
}
