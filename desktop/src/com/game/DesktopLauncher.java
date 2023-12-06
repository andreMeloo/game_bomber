package com.game;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.controller.GameManager;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(primaryMode);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Bomber");
		new Lwjgl3Application(new GameManager(), config);
	}
}
