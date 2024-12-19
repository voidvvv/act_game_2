package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.NormalDetector;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Set;

public class SeekEnemy extends LeafTask<Slime> {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public Status execute() {
        Slime slime = getObject();
        if (slime.getNormalDetector() == null) {
            return Status.FAILED;
        }
        // find all current character contact with this actor
        NormalDetector normalDetector = slime.getNormalDetector();
        Set<VCharacter> characters = normalDetector.getCharacters();
        if (characters.isEmpty()) {
            return Status.FAILED;
        }
        normalDetector.setTarget(characters.iterator().next());
        return Status.SUCCEEDED;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        SeekEnemy other = ReflectUtil.cast(task, SeekEnemy.class);
        if (other == null) {
            return task;
        }
        return task;
    }
}
