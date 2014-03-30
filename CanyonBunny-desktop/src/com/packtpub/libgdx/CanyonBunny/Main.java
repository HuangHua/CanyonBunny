package com.packtpub.libgdx.CanyonBunny;

import java.io.File;
import java.nio.file.FileSystem;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class Main {
	
	private static boolean rebuildAltas = true;
	private static boolean drawDebugOutline = true;
	
	private static void deleteFile(String filename) {
		File file = new File(filename);
		if(file.isFile() && file.exists())
			file.delete();
	}
	public static void main(String[] args) {
		if(rebuildAltas) {
			// delete the packed file first if exists
			deleteFile("../CanyonBunny/images/canyonBunny.pack");
			deleteFile("../CanyonBunny/images/canyonBunny.png");
			// Pack the images
			Settings settings = new Settings();
			settings.maxHeight = 1024;
			settings.maxWidth = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images",
					"../CanyonBunny/images", "canyonbunny.pack");
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CanyonBunny";
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new CanyonBunnyMain(), cfg);
	}
}
