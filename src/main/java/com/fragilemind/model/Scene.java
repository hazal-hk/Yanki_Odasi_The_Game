package com.fragilemind.model;

import java.util.ArrayList;
import java.util.List;

// ABSTRACT CLASS: Scene tek başına var olamaz, türü olmalıdır.
// EXTENDS: Scene bir Entity'dir.
public abstract class Scene extends Entity {
    
    // PROTECTED: Sadece bu sınıf ve onu miras alan alt sınıflar (Reality/Dream) erişebilir.
    protected List<Choice> choices;

    public Scene(String id, String name, String description) {
        super(id, name, description);
        this.choices = new ArrayList<>();
    }

    public void addChoice(Choice c) {
        choices.add(c);
    }

    public List<Choice> getChoices() {
        return choices;
    }

    // ABSTRACT METHOD: Alt sınıflar bu metodu kendine göre doldurmak ZORUNDADIR.
    // Polimorfizm burada gerçekleşir.
    public abstract String getDynamicDescription();
}