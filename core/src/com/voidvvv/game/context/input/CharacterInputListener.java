package com.voidvvv.game.context.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.voidvvv.game.base.VCharacter;

public class CharacterInputListener extends InputListener {
    protected VCharacter character;

    public VCharacter getCharacter() {
        return character;
    }

    public void setCharacter(VCharacter character) {
        this.character = character;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (character == null) {
            return false;
        }
        boolean result = tryMove(keycode);
        if (!result) {
            // try attack();
            result = attack(keycode);
        }
        return result;
    }

    private boolean attack(int keycode) {
        if (keycode == InputActionMapping.ACTION_ATTACK) {

            return true;
        }
        return false;
    }

    private boolean tryMove(int keycode) {
        if (keycode == InputActionMapping.MOVE_UP) {
            character.baseMove.y = 20;
            return true;
        } else if (keycode == InputActionMapping.MOVE_DOWN) {
            character.baseMove.y = -20;
            return true;
        }
        if (keycode == InputActionMapping.MOVE_RIGHT) {
            character.baseMove.x = 20;
            return true;
        } else if (keycode == InputActionMapping.MOVE_LEFT) {
            character.baseMove.x = -20;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (character == null) {
            return false;
        }
        boolean result = false;
        if (keycode == InputActionMapping.MOVE_UP && character.baseMove.y > 0) {
            character.baseMove.y = 0;
            result = true;
        } else if (keycode == InputActionMapping.MOVE_DOWN && character.baseMove.y < 0) {
            character.baseMove.y = 0;
            result = true;
        }
        if (keycode == InputActionMapping.MOVE_RIGHT && character.baseMove.x > 0) {
            character.baseMove.x = 0;
            result = true;
        } else if (keycode == InputActionMapping.MOVE_LEFT && character.baseMove.x < 0) {
            character.baseMove.x = 0;
            result = true;
        }
        return result;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        float stageX = event.getStageX();
        float stageY = event.getStageY();
        Pinpoint pinpoint = character.getWorld().getStage().getPinpoint();
        pinpoint.begin(stageX,stageY);
        return true;
    }
}
