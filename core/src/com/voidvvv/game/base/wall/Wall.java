package com.voidvvv.game.base.wall;

import com.voidvvv.game.base.test.VObstacle;

public class Wall extends VObstacle {
    @Override
    public void init() {
        super.init();
        this.setVisible(false);
    }
}
