package com.fragilemind.interfaces;

import com.fragilemind.model.Player;
import java.util.function.Consumer;

// FUNCTIONAL INTERFACE: Bir seçimin sonucunu oyuncuya uygular.
@FunctionalInterface
public interface Effect extends Consumer<Player> {
    // Java'nın Consumer arayüzünden 'accept(Player p)' metodunu miras alır.
}