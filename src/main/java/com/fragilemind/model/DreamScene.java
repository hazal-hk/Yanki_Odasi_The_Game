package com.fragilemind.model;

public class DreamScene extends Scene {
    
    // Rüya sahnelerine özel bir değişken
    private int distortionLevel; 

    public DreamScene(String id, String name, String description, int distortionLevel) {
        super(id, name, description);
        this.distortionLevel = distortionLevel;
    }

    // OVERRIDE: Metni bozarak geri döndürür.
    @Override
    public String getDynamicDescription() {
        String originalText = super.getDescription();
        
        // Eğer bozulma seviyesi yüksekse metni manipüle et
        if (distortionLevel > 50) {
            return ">>> NIGHTMARE SEQUENCE <<<\n" + 
                   originalText.replace(" ", "... ")
                               .toUpperCase() + 
                   "\n(Voices are whispering your name...)";
        }
        
        return originalText + "\n[Reality feels thin here...]";
    }
}