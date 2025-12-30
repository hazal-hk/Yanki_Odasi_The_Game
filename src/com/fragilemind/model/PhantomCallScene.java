package com.fragilemind.model;

public class PhantomCallScene extends DreamScene {

    public PhantomCallScene() {
        super("scene_phantom_call", "THE PHONE CALL", "", 100);
    }

    @Override
    public String getDynamicDescription() {
        return """
               [SOUND] Brrr... Brrr... The phone vibrates against the desk wood.
               It's an Unknown Number.
               """;
    }

    // Bu sahne, oyuncunun geçmiş kararına göre seçeneklerini dinamik oluşturur!
    // Bu metodu Scene sınıfına veya GameEngine'e entegre etmek için 
    // StoryBuilder içinde özel bir yapı kuracağız.
    // (Not: Basitlik adına bu mantığı StoryBuilder içinde Condition ile de yapabiliriz,
    // ama burada özel bir sınıf olduğunu göstermek istedim.)
}