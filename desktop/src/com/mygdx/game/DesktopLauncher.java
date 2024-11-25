package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.box2d.testt.Box2DExample;
import com.box2d.testt.BoxTest;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.test.imgui.ImGuiRender;
import com.voidvvv.game.ActGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("act_game_2");
		new Lwjgl3Application(new ImGuiRender(ActGame.gameInstance()), config);


	}
}
