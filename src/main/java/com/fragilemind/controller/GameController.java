package com.fragilemind.controller;

import com.fragilemind.content.StoryBuilder;
import com.fragilemind.model.Choice;
import com.fragilemind.model.Player;
import com.fragilemind.model.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class GameController {

    // FXML dosyasındaki fx:id'ler ile eşleşmeli
    @FXML private Label sanityLabel;
    @FXML private Label conscienceLabel;
    @FXML private TextArea storyArea;
    @FXML private VBox choicesBox;

    private Player player;
    private Scene currentScene;

    // JavaFX yüklendiğinde otomatik çalışır
    @FXML
    public void initialize() {
        // Modeli Başlat
        this.player = new Player("Rowan");
        this.currentScene = StoryBuilder.buildMainStory();

        // İlk Sahneyi Ekrana Bas
        updateUI();
    }

    private void updateUI() {
        // 1. İstatistikleri Güncelle
        sanityLabel.setText("SANITY: " + player.getSanity() + "%");
        conscienceLabel.setText("CONSCIENCE: " + player.getConscience() + "%");

        // 2. Metni Güncelle (Polimorfizm burada çalışır)
        storyArea.setText(currentScene.getDynamicDescription());

        // 3. Butonları Temizle ve Yeniden Oluştur
        choicesBox.getChildren().clear();

        if (currentScene.getChoices().isEmpty()) {
            storyArea.appendText("\n\n>>> THE END <<<");
            return;
        }

        for (Choice choice : currentScene.getChoices()) {
            Button btn = new Button(choice.getDisplayText(player));
            btn.getStyleClass().add("choice-button"); // CSS sınıfını ekle

            // Kilit Kontrolü
            if (choice.isLocked(player)) {
                btn.setDisable(true);
            } else {
                // Tıklama Olayı (Event Handler)
                btn.setOnAction(e -> {
                    choice.executeEffect(player);      // Etkiyi uygula
                    currentScene = choice.getNextScene(); // Sahneyi ilerlet
                    updateUI();                           // Ekranı yenile
                });
            }
            choicesBox.getChildren().add(btn);
        }
    }
}