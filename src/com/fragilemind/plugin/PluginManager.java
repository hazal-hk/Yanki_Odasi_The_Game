package com.fragilemind.plugin;

import java.util.ArrayList;
import java.util.List;

// MANAGER: Eklentileri yöneten sınıf.
public class PluginManager {
    // COMPOSITION: Manager, bir plugin listesine sahiptir.
    private List<IGamePlugin> plugins;

    public PluginManager() {
        this.plugins = new ArrayList<>();
    }

    public void registerPlugin(IGamePlugin plugin) {
        this.plugins.add(plugin);
        plugin.onEnable(); // Eklentiyi aktifleştir
    }

    public List<IGamePlugin> getPlugins() {
        return plugins;
    }
}