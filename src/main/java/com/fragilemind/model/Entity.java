package com.fragilemind.model;

import com.fragilemind.interfaces.IInspectable;

// ABSTRACT CLASS: Bu sınıftan doğrudan nesne üretilemez (new Entity() diyemezsin).
// Ancak Player, Scene ve Item buradan türeyebilir.
public abstract class Entity implements IInspectable {
    
    // ENCAPSULATION: Değişkenler private (dışarıdan gizli).
    private String id;
    private String name;
    private String description;

    // CONSTRUCTOR
    public Entity(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // GETTERS & SETTERS (Veriye kontrollü erişim)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Abstract sınıflar, interface metodlarını implement etmek zorunda değildir.
    // Alt sınıflara bırakabilirler. Ama biz burada varsayılan bir davranış yazalım.
    @Override
    public void inspect() {
        System.out.println(">> Inspecting: " + this.name);
        System.out.println(">> " + this.description);
    }
}