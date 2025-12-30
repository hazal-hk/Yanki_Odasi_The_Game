package com.fragilemind.model;

import com.fragilemind.interfaces.IUsable;

// INHERITANCE: Entity sınıfının tüm özelliklerini alır.
// IMPLEMENTS: IUsable arayüzündeki kurallara uymak zorundadır.
public class Item extends Entity implements IUsable {

    // Constructor: Üst sınıfın (Entity) constructor'ını çağırmak zorunda.
    public Item(String id, String name, String description) {
        super(id, name, description); // 'super' anahtar kelimesi ebeveyni işaret eder.
    }

    // POLYMORPHISM: Interface'den gelen metodu kendine göre dolduruyor (Override).
    @Override
    public void use(Player player) {
        System.out.println(">> You used: " + getName());
        // Varsayılan eşya davranışı. Özel eşyalar bunu da override edebilir.
    }
}