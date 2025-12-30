package com.fragilemind.plugin;

import com.fragilemind.model.Player;
import com.fragilemind.model.Scene;

// INTERFACE: Tüm eklentiler (DLC'ler) bu sözleşmeye uymalıdır.
public interface IGamePlugin {
    String getName();
    
    // Eklenti yüklendiğinde çalışacak metot
    void onEnable();
    
    // Eklentinin başlangıç sahnesini döndürür
    Scene getStartingScene();
    
    // Eklenti bittiğinde ana oyuncuya etkilerini uygular (Item verme, Stat değiştirme)
    void applyAftermath(Player player);
}