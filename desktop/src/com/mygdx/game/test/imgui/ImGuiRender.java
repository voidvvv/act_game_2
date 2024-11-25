package com.mygdx.game.test.imgui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.mygdx.game.test.imgui.actor.ProtagnizeAttr;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class ImGuiRender implements ApplicationListener {
    Game game;
    UIRender uiRender;

    private static ImGuiImplGlfw imGuiGlfw;
    private static ImGuiImplGl3 imGuiGl3;
    static long windowHandle = -1;
    private static InputProcessor tmpProcessor;

    public ImGuiRender(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        this.game.create();
        uiRender = new ProtagnizeAttr();
        initImGui();
    }

    public static void initImGui() {
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGl3 = new ImGuiImplGl3();
        windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.getFonts().addFontFromFileTTF("C:\\Windows\\Fonts\\simsun.ttc", 20.0f, ImGui.getIO().getFonts().getGlyphRangesChineseSimplifiedCommon());
        io.getFonts().build();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable); // 这个设置可以让imgui的panel放置在window外部

        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 130");
    }

    public static void startImGui() {
        if (tmpProcessor != null) { // Restore the input processor after ImGui caught all inputs, see #end()
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }
        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public static void endImGui() {

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        // If ImGui wants to capture the input, disable libGDX's input processor
        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
//
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupCurrentContext = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupCurrentContext);
        }

//        GLFW.glfwSwapBuffers(windowHandle);
//        GLFW.glfwPollEvents();
    }

    public static void disposeImGui() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();
    }

    @Override
    public void resize(int width, int height) {
        this.game.resize(width, height);
    }

    @Override
    public void render() {
        this.game.render();
        startImGui();
        if (uiRender != null) {
            uiRender.render();
        }
        endImGui();
    }

    @Override
    public void pause() {
        this.game.pause();
    }

    @Override
    public void resume() {
        this.game.resume();
    }

    @Override
    public void dispose() {
        this.game.dispose();

        disposeImGui();
    }
}
