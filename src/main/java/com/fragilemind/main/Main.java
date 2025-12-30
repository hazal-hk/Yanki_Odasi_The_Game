package com.fragilemind.main;

import java.util.Scanner;

import com.fragilemind.content.StoryBuilder;
import com.fragilemind.engine.GameEngine;
import com.fragilemind.model.Player;
import com.fragilemind.plugin.PluginManager;
import com.fragilemind.plugin.dlc.TangledThreadsDLC;

public class Main {
    public static void main(String[] args) {
        // 1. OYUN BİLEŞENLERİNİ HAZIRLA (Initialization)
        Scanner scanner = new Scanner(System.in);
        GameEngine engine = new GameEngine();
        PluginManager pluginManager = new PluginManager();
        
        // Oyuncuyu Oluştur
        Player player = new Player("Rowan");

        // 2. ARAYÜZ (UI - Console)
        System.out.println("#############################################");
        System.out.println("#       FRAGILE MIND: ECHO CHAMBER          #");
        System.out.println("#############################################");
        System.out.println("\nLoading core systems...");
        
        // 3. DLC KONTROLÜ (Plugin Injection)
        // Normalde burası bir klasörü tarayıp otomatik bulur, biz elle ekliyoruz.
        pluginManager.registerPlugin(new TangledThreadsDLC());

        // Kullanıcıya DLC sorma
        if (!pluginManager.getPlugins().isEmpty()) {
            var dlc = pluginManager.getPlugins().get(0);
            System.out.println("\n>> [SYSTEM ALERT] Fragmented Memory Found: " + dlc.getName());
            System.out.print(">> Do you want to relive this memory first? (y/n): ");
            
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("y")) {
                System.out.println("\n>> SYNCHRONIZING WITH PAST TIMELINE...");
                
                // DLC Sahnesini Oynat
                engine.playSceneLoop(dlc.getStartingScene(), player);
                
                // Sonuçları Ana Oyuncuya Uygula
                dlc.applyAftermath(player);
                
                System.out.println("\n>> MEMORY SEQUENCE ENDED. RETURNING TO PRESENT...");
            }
        }

        // 4. ANA OYUNU BAŞLAT
        System.out.println("\n>> INITIALIZING ACT 1: ELOWEN BAY...");
        // StoryBuilder'dan ana hikayeyi al ve motora ver
        engine.playSceneLoop(StoryBuilder.buildMainStory(), player);

        // 5. KAPANIŞ
        System.out.println("\n#############################################");
        System.out.println("#             TO BE CONTINUED...            #");
        System.out.println("#############################################");
        
        scanner.close();
    }
}