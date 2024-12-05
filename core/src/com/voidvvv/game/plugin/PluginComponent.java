package com.voidvvv.game.plugin;

import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;
import java.util.List;

public class PluginComponent {
    private List<Plugin> initList = new ArrayList<Plugin>();
    private List<Plugin> plugins = new ArrayList<Plugin>();
    private List<Plugin> trashBin = new ArrayList<Plugin>();

    public void addPlugin(Plugin plugin) {
        if (plugin != null) {
            initList.add(plugin);
        }
    }

    public void removePlugin(Plugin plugin) {
        if (plugin != null) {
            trashBin.add(plugin);
        }
    }

    public void update (float delta) {
        for (Plugin plugin : plugins) {
            plugin.update(delta);
        }
        if (!initList.isEmpty()) {
            for (Plugin plugin : initList) {
                plugin.start();
            }
            plugins.addAll(initList);
            initList.clear();
        }
        if (!trashBin.isEmpty()) {
            for (Plugin plugin : trashBin) {
                Pools.free(plugin);
            }
            plugins.removeAll(trashBin);
            trashBin.clear();
        }
    }

}
