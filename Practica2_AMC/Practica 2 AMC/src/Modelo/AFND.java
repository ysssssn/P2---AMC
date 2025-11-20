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
public class AFND implements Proceso {

    private ArrayList<Integer> estados; //indica cuales son los estados
    private int estadoinicial;
    private ArrayList<Integer> estadosFinales; //indica cuales son los estados Finales
    private ArrayList<Transicion> transiciones; //indica la lista de transiciones del AFND
    private ArrayList<Transicion> transicionesLambda; //indica la lista de transiciones λ del AFND

    private ArrayList<Integer> estadosPaP;
    private String solucionString;

    public AFND() {
        estados = new ArrayList<>();
        estadoinicial = 0;
        estadosFinales = new ArrayList<>();
        transiciones = new ArrayList<>();
        transicionesLambda = new ArrayList<>();
        solucionString = "";
    }

    private ArrayList<Integer> transicion(int estado, char simbolo) {
        int i = 0;

        while (i < transiciones.size()) {
            if (estado == transiciones.get(i).getEstadoinicial() && simbolo == transiciones.get(i).getSimbolo()) {
                return transiciones.get(i).getEstadosFinales();
            } else {
                i++;
            }
        }

        return null;
    }

    public ArrayList<Integer> transicion(ArrayList<Integer> macroestado, char simbolo) {
        ArrayList<Integer> macroEstDevolver = new ArrayList<>();

        for (int i = 0; i < macroestado.size(); i++) {
            ArrayList<Integer> aux = transicion(macroestado.get(i), simbolo);

            if (aux != null) {
                for (int j = 0; j < aux.size(); j++) {
                    if (!macroEstDevolver.contains(aux.get(j))) {
                        macroEstDevolver.add(aux.get(j));
                    }
                }
            }
        }

        solucionString += "\n\nMACROESTADO DESPUES TRANSICION:";
        for (int j = 0; j < macroEstDevolver.size(); j++) {
            solucionString += " q" + macroEstDevolver.get(j);
        }

        return lambda_clausura(macroEstDevolver);
    }

    public ArrayList<Integer> transicionLambda(int estado) {
        int i = 0;

        while (i < transicionesLambda.size()) {
            if (estado == transicionesLambda.get(i).getEstadoinicial()) {
                return transicionesLambda.get(i).getEstadosFinales();
            } else {
                i++;
            }
        }

        return null;
    }

    private ArrayList<Integer> lambda_clausura(ArrayList<Integer> macroestado) {

        for (int i = 0; i < macroestado.size(); i++) {
            ArrayList<Integer> aux = transicionLambda(macroestado.get(i));

            if (aux != null) {
                for (int j = 0; j < aux.size(); j++) {
                    if (!macroestado.contains(aux.get(j))) {
                        macroestado.add(aux.get(j));
                    }
                }
            }
        }

        solucionString += "\n\nMACROESTADO DESPUES LAMBDA:";
        for (int j = 0; j < macroestado.size(); j++) {
            solucionString += " q" + macroestado.get(j);
        }

        return macroestado;
    }
    
    @Override
    public boolean esFinal(int estado) {
        int i = 0;
        while (i < estadosFinales.size()) {
            if (estado == estadosFinales.get(i)) {
                return true;
            }
            i++;
        }

        return false;
    }

    public boolean esFinal(ArrayList<Integer> macroestado) {
        boolean aux = false;

        for (int i = 0; i < macroestado.size(); i++) {
            aux = aux || esFinal(macroestado.get(i));
            if (aux) {
                solucionString += "\n\nCADENA ACEPTADA\n\n";
                return aux;
            }
        }

        solucionString += "\n\nCADENA RECHAZADA\n\n";

        return aux;
    }

    @Override
    public boolean reconocer(String cadena) {
        solucionString = "";

        char[] simbolo = cadena.toCharArray();

        ArrayList<Integer> macroestado = new ArrayList<>();
        macroestado.add(estadoinicial); //El estado inicial es el 0
        solucionString += "\n\nMACROESTADO INICIAL:";
        for (int j = 0; j < macroestado.size(); j++) {
            solucionString += " q" + macroestado.get(j);
        }

        macroestado = lambda_clausura(macroestado);

        for (int i = 0; i < simbolo.length; i++) {
            solucionString += "\n\nSIMBOLO: " + simbolo[i];
            macroestado = transicion(macroestado, simbolo[i]);
        }

        return esFinal(macroestado);
    }

    public void reconocerPasoaPaso(char simbolo) {
        solucionString += "\n\nSIMBOLO: " + simbolo;
        estadosPaP = transicion(estadosPaP, simbolo);
    }

    public boolean reconocerPasoaPasoUltimo(char simbolo) {
        solucionString += "\n\nSIMBOLO: " + simbolo;
        estadosPaP = transicion(estadosPaP, simbolo);

        return esFinal(estadosPaP);
    }

    public void setEstadoPasoPaso() {
        estadosPaP = new ArrayList<>();
        estadosPaP.add(estadoinicial);
        solucionString = "\n\nMACROESTADO INICIAL:";
        for (int j = 0; j < estadosPaP.size(); j++) {
            solucionString += " q" + estadosPaP.get(j);
        }

        estadosPaP = lambda_clausura(estadosPaP);
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

    public void agregarTransicion(int e1, char simbolo, ArrayList<Integer> e2) {
        transiciones.add(new Transicion(e1, simbolo, e2));
    }

    public void agregarTransicionλ(int e1, ArrayList<Integer> e2) {
        transicionesLambda.add(new Transicion(e1, e2));
    }

    @Override
    public String toString() {
        String s = "TIPO: AFND";

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
            s += "q" + t.getEstadoinicial() + " '" + t.getSimbolo() + "'";
            ArrayList<Integer> aux = t.getEstadosFinales();
            for (int j = 0; j < aux.size(); j++) {
                s += " q" + aux.get(j);
            }
            if (i != transiciones.size() - 1) {
                s += "\n";
            }
        }

        s += "\nTRANSICIONES LAMBDA:\n";
        for (int i = 0; i < transicionesLambda.size(); i++) {
            Transicion t = transicionesLambda.get(i);
            s += "q" + t.getEstadoinicial();
            ArrayList<Integer> aux = t.getEstadosFinales();
            for (int j = 0; j < aux.size(); j++) {
                s += " q" + aux.get(j);
            }
            if (i != transicionesLambda.size() - 1) {
                s += "\n";
            }
        }

        s += "\nFIN";
        return s;
    }

    public int getEstadoinicial() {
        return estadoinicial;
    }

    public String getStringAutomata() {
        return solucionString;
    }

    public ArrayList<Integer> getEstados() {
        return estados;
    }

    public ArrayList<Integer> getEstadosFinales() {
        return estadosFinales;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public ArrayList<Integer> getEstadosPaP() {
        return estadosPaP;
    }

    public ArrayList<Transicion> getTransicionesLambda() {
        return transicionesLambda;
    }
    
    
}
