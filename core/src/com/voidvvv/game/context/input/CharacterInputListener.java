package com.voidvvv.game.context.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.manager.SystemNotifyMessageManager;

public class CharacterInputListener extends InputListener {
    protected VCharacter character;

    SystemNotifyMessageManager systemNotifyMessageManager;

    public CharacterInputListener() {
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
    }

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
        result |= attack(keycode);
        result |= skill(keycode);
        return result;
    }

    private boolean skill(int keycode) {
        boolean result = false;
        if (keycode == InputActionMapping.SKILL_Q) {
            result = true;
            systemNotifyMessageManager.pushMessage("Q skill");
        }
        if (keycode == InputActionMapping.SKILL_R) {
            result = true;

            systemNotifyMessageManager.pushMessage("R skill");
        }
        if (keycode == InputActionMapping.SKILL_W) {
            result = true;

            systemNotifyMessageManager.pushMessage("W skill");
        }
        if (keycode == InputActionMapping.SKILL_E) {
            result = true;

            systemNotifyMessageManager.pushMessage("E skill");
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
        if (keycode == InputActionMapping.MOVE_JUMP) {
            this.character.jump();
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
        if (button == Input.Buttons.LEFT)
            leftEvent(event, x, y, pointer);
        return true;
    }

    private void leftEvent(InputEvent event, float x, float y, int pointer) {
        float stageX = event.getStageX();
        float stageY = event.getStageY();
        Pinpoint pinpoint = character.getWorld().getStage().getPinpoint();
        pinpoint.begin(stageX, stageY);
        character.findPath(stageX, stageY);
    }
}
