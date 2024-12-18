package com.voidvvv.game.player;

import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.context.input.InputActionMapping;
import lombok.Getter;
import lombok.Setter;

public class BasePlayer implements Player {
    @Getter
    @Setter
    VCharacter character;


    @Override
    public void onE() {
        if (character != null) {
            character.setFrameSkill(InputActionMapping.SKILL_E);
        }
    }

    @Override
    public void onMove(float x, float y) {
        if (character != null) {
            character.baseMove.x = x;
            character.baseMove.y = y;
        }
    }

    @Override
    public void onQ() {
        if (character != null) {
            character.setFrameSkill(InputActionMapping.SKILL_Q);
        }
    }
}
