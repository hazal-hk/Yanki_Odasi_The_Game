package com.fragilemind.interfaces;

import com.fragilemind.model.Player;

// INTERFACE: Bir nesnenin "Kullanılabilir" (Usable) olduğunu belirtir.
public interface IUsable {
    // Interface metodları varsayılan olarak public'tir.
    void use(Player player);
}