package com.fragilemind.interfaces;

import com.fragilemind.model.Player;
import java.util.function.Predicate;

// FUNCTIONAL INTERFACE: Bir seçimin kilitli olup olmadığını kontrol eder.
@FunctionalInterface
public interface Condition extends Predicate<Player> {
    // Java'nın Predicate arayüzünden 'test(Player p)' metodunu miras alır.
}