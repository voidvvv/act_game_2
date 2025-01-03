package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.box2d.testt.Box2DExample;
import com.box2d.testt.BoxTest;
import com.mygdx.game.test.imgui.ImGuiRender;
import com.voidvvv.game.ActGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
@SpringBootApplication(scanBasePackages = "com.voidvvv.game")
public class DesktopLauncher {
	public static void main (String[] arg) {
		SpringApplication.run(DesktopLauncher.class, arg);
	}

	@Bean
	public Lwjgl3Application app(ActGame actGame) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("act_game_2");
		config.useVsync(true);
		return new Lwjgl3Application(new ImGuiRender(actGame), config);
	}
}
