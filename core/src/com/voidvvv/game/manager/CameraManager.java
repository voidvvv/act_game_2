package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager {
    private OrthographicCamera mainCamera;
    private OrthographicCamera screenCamera;
    public CameraManager() {}

    public void init () {
        mainCamera = new OrthographicCamera();
        screenCamera = new OrthographicCamera();
    }

    public OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    public OrthographicCamera getScreenCamera() {
        return screenCamera;
    }
}
