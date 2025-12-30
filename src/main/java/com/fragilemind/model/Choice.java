package com.fragilemind.model;

import com.fragilemind.interfaces.Condition;
import com.fragilemind.interfaces.Effect;

public class Choice {
    private String text;
    private Scene nextScene;
    
    // FUNCTIONAL INTERFACES: Davranışı nesne olarak saklıyoruz.
    private Condition condition; // Kilit mantığı (Predicate)
    private Effect effect;       // Sonuç mantığı (Consumer)
    private String lockReason;   // Kilitliyse neden?

    // 1. Constructor: Kilitli/Şartlı Seçimler İçin
    public Choice(String text, Scene nextScene, Condition condition, String lockReason, Effect effect) {
        this.text = text;
        this.nextScene = nextScene;
        this.condition = condition;
        this.lockReason = lockReason;
        this.effect = effect;
    }

    // 2. Constructor Overloading: Herhangi bir şartı olmayan basit seçimler için
    public Choice(String text, Scene nextScene, Effect effect) {
        // Varsayılan kilit: (p -> true) yani her zaman açık.
        this(text, nextScene, p -> true, "", effect);
    }

    // Mantık Metotları
    public boolean isLocked(Player p) {
        // condition.test(p) -> Interface metodunu çalıştırır.
        return !condition.test(p);
    }

    public void executeEffect(Player p) {
        if (effect != null) {
            effect.accept(p); // Interface metodunu çalıştırır.
        }
    }

    // UI için metin düzenleme
    public String getDisplayText(Player p) {
        if (isLocked(p)) {
            return "[LOCKED - " + lockReason + "] " + text;
        }
        return text;
    }

    public Scene getNextScene() { return nextScene; }
}