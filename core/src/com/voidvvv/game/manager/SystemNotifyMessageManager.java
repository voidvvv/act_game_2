package com.voidvvv.game.manager;

import com.badlogic.gdx.utils.Pool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class SystemNotifyMessageManager {
    SystemNotifyMessagePool pool = new SystemNotifyMessagePool(100);

    public final Deque<SystemNotifyMessage> messages = new ArrayDeque<SystemNotifyMessage>();

    public float maxTime = 5f;

    public int maxSize = 10;

    public void init () {

    }

    public void pushMessage (String meg) {
        SystemNotifyMessage systemNotifyMessage = pool.newObject();
        systemNotifyMessage.setMessage(meg);
        systemNotifyMessage.time = maxTime;
        messages.push(systemNotifyMessage);
    }

    public void update (float delta) {
        while (messages.size() > 10) {
            messages.pop();
        }
        Iterator<SystemNotifyMessage> iterator = messages.iterator();
        while (iterator.hasNext()) {
            SystemNotifyMessage systemNotifyMessage = iterator.next();
            systemNotifyMessage.update(delta);
            if (systemNotifyMessage.time <= 0f) {
                iterator.remove();
                pool.free(systemNotifyMessage);
            }
        }
    }


    class SystemNotifyMessagePool extends Pool<SystemNotifyMessage> {
        public SystemNotifyMessagePool(int maxSize) {
            super(maxSize);
        }

        @Override
        protected SystemNotifyMessage newObject() {
            return new SystemNotifyMessage();
        }
    }

}
