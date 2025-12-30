package com.fragilemind.interfaces;

// INTERFACE: Bir nesnenin "incelenebilir" olduğunu belirtir.
// OOP Prensibi: Abstraction (Soyutlama)
public interface IInspectable {
    void inspect(); // Gövdesiz metot. Uygulayan sınıf doldurmak ZORUNDA.
}