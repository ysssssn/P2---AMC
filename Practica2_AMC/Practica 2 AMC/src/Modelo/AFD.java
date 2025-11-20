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
public class AFD implements Proceso {

    private ArrayList<Integer> estados; //indica cuales son los estados
    private int estadoinicial;
    private ArrayList<Integer> estadosFinales; //indica cuales son los estados Finales
    private ArrayList<Transicion> transiciones; //indica la lista de transiciones del AFD

    private int estadoPasoPaso;
    private String solucionString;

    public AFD() {
        estados = new ArrayList<>();
        estadoinicial = 0;
        estadosFinales = new ArrayList<>();
        transiciones = new ArrayList<>();
        solucionString = "";
    }

    public int transicion(int estado, char simbolo) {
        int estadocambio = -1, i = 0;

        solucionString += "\nESTADO INICIAL: " + estado;
        
        solucionString += "\nSIMBOLO: " + simbolo;

        while (i < transiciones.size()) {
            if (estado == transiciones.get(i).getEstadoinicial() && simbolo == transiciones.get(i).getSimbolo()) {
                estadocambio = transiciones.get(i).getEstadofinal();
                break;
            }
            i++;
        }

        solucionString += "\nESTADO CAMBIO: " + estadocambio + "\n";

        return estadocambio;
    }
    
    @Override
    public boolean esFinal(int estado) {
        int i = 0;
        while (i < estadosFinales.size()) {
            if (estado == estadosFinales.get(i)) {
                solucionString += "\nCADENA ACEPTADA\n\n";
                return true;
            }
            i++;
        }

        solucionString += "\nCADENA RECHAZADA\n\n";
        return false;
    }

    @Override
    public boolean reconocer(String cadena) {
        solucionString = "";
        
        char[] simbolo = cadena.toCharArray();
        int estado = estadoinicial;
        for (int i = 0; i < simbolo.length; i++) {

            estado = transicion(estado, simbolo[i]);
            if (estado == -1) {
                return false;
            }
        }
        return esFinal(estado);
    }

    public void reconocerPasoaPaso(char simbolo) {
        estadoPasoPaso = transicion(estadoPasoPaso, simbolo);
    }

    public boolean reconocerPasoaPasoUltimo(char simbolo) {
        estadoPasoPaso = transicion(estadoPasoPaso, simbolo);
        return esFinal(estadoPasoPaso);
    }

    public void setEstadoPasoPaso() {
        estadoPasoPaso = estadoinicial;
        solucionString = "";
    }

    public void setEstadoInicial(int ei) {
        estadoinicial = ei;
    }

    public void añadirEstado(int e) {
        if (!estados.contains(e)) {
            estados.add(e);
        }
    }

    public void añadirEstadoFinal(int ef) {
        estadosFinales.add(ef);
    }

    public void agregarTransicion(int eo, char simbolo, int ed) {
        transiciones.add(new Transicion(eo, simbolo, ed));
    }

    @Override
    public String toString() {
        String s = "TIPO: AFD";

        s += "\nESTADOS:";
        for (int i = 0; i < estados.size(); i++) {
            s += " q" + estados.get(i);
        }

        s += "\nINICIAL: q" + estadoinicial;

        s += "\nFINALES:";
        for (int i = 0; i < estadosFinales.size(); i++) {
            s += " q" + estadosFinales.get(i);
        }

        s += "\nTRANSICIONES:\n";
        for (int i = 0; i < transiciones.size(); i++) {
            Transicion t = transiciones.get(i);
            s += "q" + t.getEstadoinicial() + " '" + t.getSimbolo() + "' q" + t.getEstadofinal() + "\n";
        }

        s += "FIN\n";
        return s;
    }

    public ArrayList<Integer> getEstados() {
        return estados;
    }

    public int getEstadoinicial() {
        return estadoinicial;
    }

    public ArrayList<Integer> getEstadosFinales() {
        return estadosFinales;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public int getEstadoPasoPaso() {
        return estadoPasoPaso;
    }

    public String getStringAutomata(){
        return solucionString;
    }
}
