package com.voidvvv.game.manager.behaviors;

import com.voidvvv.game.plugin.Plugin;

public interface BehaviorPlugin extends Plugin {
    void execute(Behavior behavior);
}
