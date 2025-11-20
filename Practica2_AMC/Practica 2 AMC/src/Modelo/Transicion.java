/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class Transicion {

    private int estadoinicial;
    private char simbolo;
    private int estadofinal;
    private ArrayList<Integer> estadosfinales;

    public Transicion(int estadoini, char cambio, int estadofin) { //TRANSICION AFD
        this.estadoinicial = estadoini;
        this.simbolo = cambio;
        this.estadofinal = estadofin;
    }

    public Transicion(int estadoini, char cambio, ArrayList<Integer> estadosfin) { //TRANSICION AFND
        this.estadoinicial = estadoini;
        this.simbolo = cambio;
        this.estadosfinales = estadosfin;
    }

    public Transicion(int estadoini, ArrayList<Integer> estadosfin) { //TRANSICION λ AFND
        this.estadoinicial = estadoini;
        this.simbolo = 'λ';
        this.estadosfinales = estadosfin;
    }

    public int getEstadoinicial() {
        return estadoinicial;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getEstadofinal() {
        return estadofinal;
    }

    public ArrayList<Integer> getEstadosFinales() {
        return estadosfinales;
    }

}
