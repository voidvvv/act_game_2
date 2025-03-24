package com.voidvvv.game.plugin;

import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginComponent {
    private Set<Plugin> initList = new HashSet<Plugin>();
    private Set<Plugin> plugins = new HashSet<Plugin>();
    private Set<Plugin> trashBin = new HashSet<>();

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
        if (!trashBin.isEmpty()) {
            for (Plugin plugin : trashBin) {
                Pools.free(plugin);
            }
            plugins.removeAll(trashBin);
            trashBin.clear();
        }
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

    }

    public void reset () {
        for (Plugin plugin : plugins) {
            Pools.free(plugin);
        }
        plugins.clear();
        for (Plugin plugin : initList) {
            Pools.free(plugin);
        }
        initList.clear();
        for (Plugin plugin : trashBin) {
            Pools.free(plugin);
        }
        trashBin.clear();
    }

}
