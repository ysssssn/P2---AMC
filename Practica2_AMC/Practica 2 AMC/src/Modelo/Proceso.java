/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

/**
 *
 * @author USER
 */
public interface Proceso {

    public abstract boolean esFinal(int estado); //true si estado es un estado final

    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida

    @Override
    public abstract String toString(); //muestra las transiciones y estados finales
}
