package com.fragilemind.controller;

import com.fragilemind.content.StoryBuilder;
import com.fragilemind.model.Choice;
import com.fragilemind.model.Player;
import com.fragilemind.model.Scene;
import com.fragilemind.plugin.PluginManager;
import com.fragilemind.plugin.dlc.TangledThreadsDLC;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class GameController {

    @FXML private Label sanityLabel;
    @FXML private Label conscienceLabel;
    @FXML private TextArea storyArea;
    @FXML private VBox choicesBox;

    private Player player;
    private Scene currentScene;
    private PluginManager pluginManager;

    @FXML
    public void initialize() {
        this.player = new Player("Rowan");
        this.pluginManager = new PluginManager();
        pluginManager.registerPlugin(new TangledThreadsDLC());

        showMainMenu();
    }

    private void showMainMenu() {
        sanityLabel.setText("");
        conscienceLabel.setText("");
        
        storyArea.setText(
            "\n\n\n" +
            "        FRAGILE MIND: ECHO CHAMBER\n" +
            "        ==========================\n\n" +
            "        \"The mind is something that is built.\n" +
            "         But every building can be destroyed.\"\n\n\n" +
            "        Please select a memory sequence..."
        );

        choicesBox.getChildren().clear();
        choicesBox.setAlignment(Pos.CENTER);

        // BUTONLAR
        Button btnMain = new Button("START: ELOWEN BAY (MAIN GAME)");
        btnMain.getStyleClass().add("choice-button");
        btnMain.setOnAction(e -> {
            this.currentScene = StoryBuilder.buildMainStory();
            updateUI_ShowScene(); // Direkt sahneyi göster
        });

        choicesBox.getChildren().add(btnMain);

        if (!pluginManager.getPlugins().isEmpty()) {
            var dlc = pluginManager.getPlugins().get(0);
            Button btnDLC = new Button("MEMORY: " + dlc.getName().toUpperCase());
            btnDLC.getStyleClass().add("choice-button");
            btnDLC.setStyle("-fx-border-color: #ff2e63; -fx-text-fill: #ff2e63;"); 
            btnDLC.setOnAction(e -> {
                this.currentScene = dlc.getStartingScene();
                updateUI_ShowScene();
            });
            choicesBox.getChildren().add(btnDLC);
        }
        
        Button btnExit = new Button("EXIT");
        btnExit.getStyleClass().add("choice-button");
        btnExit.setOnAction(e -> System.exit(0));
        choicesBox.getChildren().add(btnExit);
    }

    // --- 1. AŞAMA: SAHNEYİ GÖSTER ---
    private void updateUI_ShowScene() {
        refreshStats();

        // Oyun bitti mi kontrolü
        if (currentScene == null) {
            showEndScreen();
            return;
        }

        // Sahne metnini yaz
        storyArea.setText(currentScene.getDynamicDescription());

        // Seçenekleri oluştur
        choicesBox.getChildren().clear();
        
        if (currentScene.getChoices().isEmpty()) {
            storyArea.appendText("\n\n>>> THE END.");
            return;
        }

        for (Choice choice : currentScene.getChoices()) {
            Button btn = new Button(choice.getDisplayText(player));
            btn.getStyleClass().add("choice-button");

            if (choice.isLocked(player)) {
                btn.setDisable(true);
            } else {
                // TIKLAYINCA -> TRANSITION EKRANINA GİT
                btn.setOnAction(e -> handleChoiceSelection(choice));
            }
            choicesBox.getChildren().add(btn);
        }
    }

    // --- 2. AŞAMA: SONUCU GÖSTER (ARA EKRAN) ---
    private void handleChoiceSelection(Choice choice) {
        // 1. Etkiyi uygula (Loglar oluşsun)
        choice.executeEffect(player);
        
        // 2. Logları al
        String logs = player.flushLog();
        
        // 3. Sonraki sahneyi hafızaya al (Henüz geçme!)
        Scene nextScene = choice.getNextScene();

        // EĞER LOG YOKSA (Metinsiz geçişse) direkt diğer sahneye atla
        if (logs.trim().isEmpty()) {
            this.currentScene = nextScene;
            updateUI_ShowScene();
            return;
        }

        // EĞER LOG VARSA -> Ekrana bas ve "DEVAM ET" butonu koy
        refreshStats();
        storyArea.setText(logs); // Sadece sonucu göster
        
        choicesBox.getChildren().clear();
        
        Button continueBtn = new Button("CONTINUE >>");
        continueBtn.getStyleClass().add("choice-button");
        continueBtn.setStyle("-fx-border-color: #00adb5; -fx-text-fill: #00adb5;"); // Farklı renk
        
        continueBtn.setOnAction(e -> {
            // "Devam Et"e basınca asıl sahne değişimini yap
            this.currentScene = nextScene;
            updateUI_ShowScene();
        });
        
        choicesBox.getChildren().add(continueBtn);
    }

    // Yardımcı Metotlar
    private void refreshStats() {
        sanityLabel.setText("SANITY: " + player.getSanity() + "%");
        conscienceLabel.setText("CONSCIENCE: " + player.getConscience() + "%");
    }

    private void showEndScreen() {
        storyArea.setText("\n\n>>> THE STORY IS OVER. <<<");
        choicesBox.getChildren().clear();
        
        Button menuBtn = new Button("Return to Main Menu");
        menuBtn.getStyleClass().add("choice-button");
        menuBtn.setOnAction(e -> {
            this.player = new Player("Rowan");
            showMainMenu();
        });
        choicesBox.getChildren().add(menuBtn);
    }
}