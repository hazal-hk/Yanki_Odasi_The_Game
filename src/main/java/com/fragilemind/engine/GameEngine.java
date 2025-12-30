package com.fragilemind.engine;

import java.util.List;
import java.util.Scanner;

import com.fragilemind.model.Choice;
import com.fragilemind.model.Player;
import com.fragilemind.model.Scene;

public class GameEngine {
    private Scanner scanner;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
    }

    // Oyun Döngüsü (Game Loop)
    public void playSceneLoop(Scene startingScene, Player player) {
        Scene currentScene = startingScene;

        // Sahne null olmadığı sürece oyun devam eder
        while (currentScene != null) {
            
            // 1. Sahne Başlığını Yazdır
            System.out.println("\n################################################");
            System.out.println("LOCATION: " + currentScene.getName());
            System.out.println("################################################");

            // 2. Sahne Metnini Yazdır (POLYMORPHISM BURADA!)
            // Engine, sahnenin RealityScene mi DreamScene mi olduğunu bilmez.
            // Sadece metodunu çağırır. Nesne kendi davranışını sergiler.
            System.out.println(currentScene.getDynamicDescription());

            // 3. Seçenekleri Al
            List<Choice> options = currentScene.getChoices();
            
            // Eğer seçenek yoksa oyun biter (veya bir son sahnesidir)
            if (options.isEmpty()) {
                System.out.println("\n>> [END OF THIS PATH]");
                break;
            }

            // 4. Seçenekleri Listele
            System.out.println("\n--- CHOICES ---");
            for (int i = 0; i < options.size(); i++) {
                // Seçeneğin metnini al (Kilitliyse [LOCKED] yazar)
                System.out.println((i + 1) + ". " + options.get(i).getDisplayText(player));
            }

            // 5. Kullanıcı Girdisi Al
            System.out.print("\n> Make your choice (1-" + options.size() + "): ");
            
            try {
                String input = scanner.nextLine();
                int selection = Integer.parseInt(input) - 1;

                if (selection >= 0 && selection < options.size()) {
                    Choice selectedChoice = options.get(selection);

                    // Kilit Kontrolü
                    if (selectedChoice.isLocked(player)) {
                        System.out.println("\n>> LOCKED! Your mind refuses to take this path.");
                        System.out.println(">> (Press Enter to try again)");
                        scanner.nextLine(); 
                    } else {
                        // Geçerli seçim: Etkiyi uygula ve sahneyi değiştir
                        selectedChoice.executeEffect(player);
                        currentScene = selectedChoice.getNextScene();
                    }
                } else {
                    System.out.println(">> Invalid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Please enter a valid number.");
            }
        }
    }
}