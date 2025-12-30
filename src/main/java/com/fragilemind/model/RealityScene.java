package com.fragilemind.model;

// CONCRETE CLASS: Somut sınıf.
public class RealityScene extends Scene {

    public RealityScene(String id, String name, String description) {
        super(id, name, description);
    }

    // OVERRIDE: Ata sınıftaki abstract metodu dolduruyoruz.
    @Override
    public String getDynamicDescription() {
        // Gerçeklikte metin neyse odur. Değişiklik yok.
        return super.getDescription();
    }
}