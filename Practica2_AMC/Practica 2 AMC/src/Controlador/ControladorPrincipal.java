/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.AFD;
import Modelo.AFND;
import Vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author USER
 */
public class ControladorPrincipal implements ActionListener {

    private final VistaPrincipal vPrincipal;
    private final VistaMensajes vMensaje;

    private AFD automataD;
    private AFND automataND;

    private void addListeners() {
        vPrincipal.buttonFichero.addActionListener(this);
        vPrincipal.buttonTeclado.addActionListener(this);
        vPrincipal.buttonSalir.addActionListener(this);
    }

    public ControladorPrincipal() {
        vMensaje = new VistaMensajes();
        vPrincipal = new VistaPrincipal();

        addListeners();
        vPrincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Fichero": {
                JFileChooser jfile = new JFileChooser();
                jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);

                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
                jfile.setFileFilter(filtro);

                if (jfile.showOpenDialog(jfile) != JFileChooser.CANCEL_OPTION) {
                    File fichero = jfile.getSelectedFile();

                    try {
                        Scanner scan = new Scanner(fichero);
                        String[] partes = scan.nextLine().split(" ");

                        if (partes[1].equals("AFD")) {
                            automataD = null;
                            leerFicheroAFD(fichero);
                            vPrincipal.dispose();

                            ControladorAutomata ca = new ControladorAutomata(automataD, fichero.toString());

                            vPrincipal.setVisible(true);

                        } else if (partes[1].equals("AFND")) {
                            automataND = null;
                            leerFicheroAFND(fichero);
                            vPrincipal.dispose();

                            ControladorAutomata ca = new ControladorAutomata(automataND, fichero.toString());

                            vPrincipal.setVisible(true);

                        } else {
                            vMensaje.mensajeJOptionPaneERROR("El archivo no es un automata", "ERROR", jfile);
                        }

                    } catch (FileNotFoundException ex) {
                        vMensaje.mensajeJOptionPaneERROR("Error al obtener el fichero", "ERROR", jfile);
                    }

                } else {
                    jfile.setVisible(false);
                }

            }
            break;

            case "Teclado": {
                if (vPrincipal.jComboBox.getSelectedItem().equals("AFD")) {
                    automataD = new AFD();
                    vPrincipal.dispose();
                    
                    ControladorTecladoAFD ctAFD = new ControladorTecladoAFD(automataD);
                    if(ctAFD.noSeguir()){
                        vPrincipal.setVisible(true);
                        break;
                    }

                    ControladorAutomata ca = new ControladorAutomata(automataD, "Automata Creado por Teclado");
                    
                    vPrincipal.setVisible(true);

                } else {
                    automataND = new AFND();
                    vPrincipal.dispose();
                    
                    ControladorTecladoAFND ctAFND = new ControladorTecladoAFND(automataND);
                    if(ctAFND.noSeguir()){
                        vPrincipal.setVisible(true);
                        break;
                    }

                    ControladorAutomata ca = new ControladorAutomata(automataND, "Automata Creado por Teclado");
                    
                    vPrincipal.setVisible(true);

                }
            }
            break;

            case "Salir":
                vPrincipal.dispose();
                break;
        }
    }

    public void leerFicheroAFD(File fichero) {
        try {
            automataD = new AFD();
            Scanner scan = new Scanner(fichero);

            for (int i = 0; i < 5; i++) {
                String linea = scan.nextLine();
                String[] partes = linea.split(" ");

                switch (partes[0]) {
                    case "ESTADOS:":
                        for (int j = 1; j < partes.length; j++) {
                            String[] ef = partes[j].split("q");
                            automataD.añadirEstado(Integer.parseInt(ef[1]));
                        }
                        break;

                    case "INICIAL:":
                        String[] ei = partes[1].split("q");
                        automataD.setEstadoInicial(Integer.parseInt(ei[1]));
                        break;

                    case "FINALES:":
                        for (int j = 1; j < partes.length; j++) {
                            String[] ef = partes[j].split("q");
                            automataD.añadirEstadoFinal(Integer.parseInt(ef[1]));
                        }
                        break;

                    case "TRANSICIONES:":
                        linea = scan.nextLine();
                        while (!linea.equals("FIN")) {
                            partes = linea.split(" ");

                            String[] eo = partes[0].split("q");
                            String[] simb = partes[1].split("'");
                            String[] ed = partes[2].split("q");

                            automataD.agregarTransicion(Integer.parseInt(eo[1]), simb[1].charAt(0),
                                    Integer.parseInt(ed[1]));

                            linea = scan.nextLine();
                        }
                        break;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        }
    }

    public void leerFicheroAFND(File fichero) {
        try {
            automataND = new AFND();

            Scanner scan = new Scanner(fichero);

            for (int i = 0; i < 5; i++) {
                String linea = scan.nextLine();
                String[] partes = linea.split(" ");

                switch (partes[0]) {
                    case "ESTADOS:":
                        for (int j = 1; j < partes.length; j++) {
                            String[] ef = partes[j].split("q");
                            automataND.añadirEstado(Integer.parseInt(ef[1]));
                        }
                        break;

                    case "INICIAL:":
                        String[] ei = partes[1].split("q");
                        automataND.setEstadoInicial(Integer.parseInt(ei[1]));
                        break;

                    case "FINALES:":
                        for (int j = 1; j < partes.length; j++) {
                            String[] ef = partes[j].split("q");
                            automataND.añadirEstadoFinal(Integer.parseInt(ef[1]));
                        }
                        break;

                    case "TRANSICIONES:":
                        linea = scan.nextLine();
                        while (!linea.equals("TRANSICIONES LAMBDA:")) {
                            partes = linea.split(" ");

                            String[] eo = null;
                            String[] simb = null;
                            ArrayList<Integer> eds = new ArrayList<>();

                            for (int j = 0; j < partes.length; j++) {
                                switch (j) {
                                    case 0:
                                        eo = partes[0].split("q");
                                        break;
                                    case 1:
                                        simb = partes[1].split("'");
                                        break;
                                    default:
                                        String[] ed = partes[j].split("q");
                                        eds.add(Integer.parseInt(ed[1]));
                                }
                            }

                            automataND.agregarTransicion(Integer.parseInt(eo[1]), simb[1].charAt(0),
                                    eds);
                            linea = scan.nextLine();
                        }

                        linea = scan.nextLine();
                        while (!linea.equals("FIN")) {
                            partes = linea.split(" ");

                            String[] eo = null;
                            ArrayList<Integer> eds = new ArrayList<>();

                            for (int j = 0; j < partes.length; j++) {
                                switch (j) {
                                    case 0:
                                        eo = partes[0].split("q");
                                        break;
                                    default:
                                        String[] ed = partes[j].split("q");
                                        eds.add(Integer.parseInt(ed[1]));
                                }
                            }
                            automataND.agregarTransicionλ(Integer.parseInt(eo[1]), eds);
                            linea = scan.nextLine();
                        }
                        break;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        }
    }
}
