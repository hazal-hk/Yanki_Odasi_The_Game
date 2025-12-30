package com.fragilemind.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Player bir Entity'dir (İsmi ve ID'si vardır).
public class Player extends Entity {
    
    // ENCAPSULATION: Private fields
    private int sanity;      // Mental Clarity (0-100)
    private int conscience;  // Moral Compass (0-100)
    
    // COMPOSITION: Oyuncu, bir eşya listesine "sahiptir".
    private List<Item> inventory;

    // YENİ: Hikaye kararlarını tutan hafıza
    private Map<String, Boolean> storyFlags;

    // YENİ: Olay günlüğü (Action Log)
    private StringBuilder actionLog;

    public Player(String name) {
        // ID otomatik "player_01" olsun, Açıklaması sabit.
        super("player_01", name, "The Architect. Struggling with reality.");
        
        // Başlangıç Değerleri
        this.sanity = 60;      
        this.conscience = 50;  
        this.inventory = new ArrayList<>(); // Listeyi başlatıyoruz
        this.storyFlags = new HashMap<>();
        this.actionLog = new StringBuilder(); // Başlat
    }

    // YENİ METOT: Terminal yerine buraya yazacağız
    public void log(String message) {
        // Hem terminale yaz (debug için) hem de hafızaya al (arayüz için)
        System.out.println(message); 
        this.actionLog.append(message).append("\n");
    }

    // YENİ METOT: Biriken logları al ve temizle
    public String flushLog() {
        String logs = actionLog.toString();
        actionLog.setLength(0); // Temizle
        return logs;
    }

    public void setFlag(String key, boolean value) {
        storyFlags.put(key, value);
        log("\t>>> [MEMORY FORMED]: " + key); // Burayı da log yaptık
    }

    // Bir olayın olup olmadığını kontrol et
    public boolean hasFlag(String key) {
        return storyFlags.getOrDefault(key, false);
    }

    // İş Mantığı Metodu (Setter yerine bunu kullanıyoruz)
    public void updateStats(int sanityChange, int conscienceChange) {
        // Math.max ve Math.min ile değerlerin 0-100 dışına çıkmasını engelliyoruz.
        this.sanity = Math.max(0, Math.min(100, this.sanity + sanityChange));
        this.conscience = Math.max(0, Math.min(100, this.conscience + conscienceChange));

        // Feedback to Console
        System.out.println("\n\t>>> [SYSTEM UPDATE] <<<");
        if(sanityChange != 0) {
            System.out.println("\tSanity: " + (sanityChange > 0 ? "+" : "") + sanityChange);
        }
        if(conscienceChange != 0) {
            System.out.println("\tConscience: " + (conscienceChange > 0 ? "+" : "") + conscienceChange);
        }
        System.out.println("\t[STATUS] Sanity: %" + sanity + " | Conscience: %" + conscience + "\n");
    }

    // Envanter Yönetimi
    public void addItem(Item item) {
        inventory.add(item);
        log("\t[INVENTORY] Added: " + item.getName()); // Burayı da log yaptık
    }
    // Stream API kullanımı (Modern Java) - Listede arama yapar
    public boolean hasItem(String itemName) {
        return inventory.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    // Getters
    public int getSanity() { return sanity; }
    public int getConscience() { return conscience; }
}