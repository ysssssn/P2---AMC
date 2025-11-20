/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.AFD;
import Modelo.AFND;
import Modelo.Transicion;
import Vista.VistaAutomata;
import Vista.VistaMensajes;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Function;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author USER
 */
public class ControladorAutomata implements ActionListener {

    private VistaAutomata vAutomata;
    private VistaMensajes vMensaje;

    private boolean esAFD;
    private AFD automataD;
    private AFND automataND;
    private String cadena;
    private char[] cadenaChar;
    private int indice, contador = 0;

    private String automata;

    private void addListeners() {
        vAutomata.buttonAgregarCadena.addActionListener(this);
        vAutomata.buttonEjecutar.addActionListener(this);
        vAutomata.buttonPasoAPaso.addActionListener(this);
        vAutomata.buttonSalir.addActionListener(this);
        vAutomata.buttonPintarGrafo.addActionListener(this);
    }

    public ControladorAutomata(AFD d, String archivo) {
        esAFD = true;

        this.automataD = d;
        this.vAutomata = new VistaAutomata();
        this.vMensaje = new VistaMensajes();
        addListeners();

        automata = automataD.toString();
        vAutomata.textAutomata.setText(automata);
        vAutomata.setTitle(archivo);
        vAutomata.setVisible(true);
    }

    public ControladorAutomata(AFND nd, String archivo) {
        esAFD = false;

        this.automataND = nd;
        this.vAutomata = new VistaAutomata();
        this.vMensaje = new VistaMensajes();
        addListeners();

        automata = automataND.toString();
        vAutomata.textAutomata.setText(automata);
        vAutomata.setTitle(archivo);
        vAutomata.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Introducir Cadena":
                cadena = vMensaje.mensajeImput("Introduzca la cadena a reconocer:", null);
                if (cadena != null) {
                    vAutomata.textCadena.setText(cadena);
                    cadenaChar = cadena.toCharArray();
                    indice = 0;
                }
                break;

            case "Ejecutar":
                if (cadena == null) {
                    vMensaje.mensajeJOptionPaneINFORMATION("La cadena no debe estar vacía", "", vAutomata);
                } else {
                    boolean reconoce;
                    if (esAFD) {
                        reconoce = automataD.reconocer(cadena);
                        vAutomata.textAutomata.setText(automata + automataD.getStringAutomata());

                        if (reconoce) {
                            vMensaje.mensajeJOptionPaneINFORMATION("CADENA ACEPTADA", "", vAutomata);
                        } else {
                            vMensaje.mensajeJOptionPaneINFORMATION("CADENA RECHAZADA", "", vAutomata);
                        }

                    } else {
                        reconoce = automataND.reconocer(cadena);
                        vAutomata.textAutomata.setText(automata + automataND.getStringAutomata());

                        if (reconoce) {
                            vMensaje.mensajeJOptionPaneINFORMATION("CADENA ACEPTADA", "", vAutomata);
                        } else {
                            vMensaje.mensajeJOptionPaneINFORMATION("CADENA RECHAZADA", "", vAutomata);
                        }
                    }
                }
                break;

            case "Paso a Paso":
                if (cadena == null) {
                    vMensaje.mensajeJOptionPaneINFORMATION("La cadena no debe estar vacía", "", vAutomata);
                } else {
                    if (esAFD) {
                        if (contador == 0) {
                            automataD.setEstadoPasoPaso();
                            contador++;
                        }
                        vAutomata.textSimbolo.setText("" + cadenaChar[indice]);

                        if (indice != cadenaChar.length - 1) {
                            automataD.reconocerPasoaPaso(cadenaChar[indice]);
                            indice++;
                            vAutomata.textAutomata.setText(automata + automataD.getStringAutomata());

                        } else {
                            boolean reconoce = automataD.reconocerPasoaPasoUltimo(cadenaChar[indice]);
                            vAutomata.textAutomata.setText(automata + automataD.getStringAutomata());

                            if (reconoce) {
                                vMensaje.mensajeJOptionPaneINFORMATION("CADENA ACEPTADA", "", vAutomata);
                            } else {
                                vMensaje.mensajeJOptionPaneINFORMATION("CADENA RECHAZADA", "", vAutomata);
                            }
                            vAutomata.textSimbolo.setText("");
                            contador = 0;
                        }

                    } else {
                        if (contador == 0) {
                            automataND.setEstadoPasoPaso();
                            contador++;
                        }
                        vAutomata.textSimbolo.setText("" + cadenaChar[indice]);

                        if (indice != cadenaChar.length - 1) {
                            automataND.reconocerPasoaPaso(cadenaChar[indice]);
                            indice++;
                            vAutomata.textAutomata.setText(automata + automataND.getStringAutomata());

                        } else {
                            boolean reconoce = automataND.reconocerPasoaPasoUltimo(cadenaChar[indice]);
                            vAutomata.textAutomata.setText(automata + automataND.getStringAutomata());

                            if (reconoce) {
                                vMensaje.mensajeJOptionPaneINFORMATION("CADENA ACEPTADA", "", vAutomata);
                            } else {
                                vMensaje.mensajeJOptionPaneINFORMATION("CADENA RECHAZADA", "", vAutomata);
                            }
                            vAutomata.textSimbolo.setText("");
                            contador = 0;
                        }
                    }

                }
                break;

            case "Salir":
                vAutomata.dispose();
                break;

            case "Dibujar Grafo":
                if (esAFD) {
                    pintarAFD();
                } else {
                    pintarAFND();
                }
                break;
        }
    }

    public void pintarAFD() {
        ArrayList<Integer> estados, estadosFinales;
        ArrayList<Transicion> transiciones;
        int estadoinicial, estadoPasoaPaso;

        estados = automataD.getEstados();
        estadosFinales = automataD.getEstadosFinales();
        transiciones = automataD.getTransiciones();
        estadoinicial = automataD.getEstadoinicial();
        estadoPasoaPaso = automataD.getEstadoPasoPaso();

        DirectedSparseGraph<String, String> grafo = new DirectedSparseGraph<>();

        for (int i = 0; i < estados.size(); i++) {
            grafo.addVertex("q" + estados.get(i));
        }

        for (int i = 0; i < transiciones.size(); i++) {
            Transicion tAux = transiciones.get(i);
            String sAux = "[q" + tAux.getEstadoinicial() + "q" + tAux.getEstadofinal() + "] - ";
            grafo.addEdge(sAux + tAux.getSimbolo(), "q" + tAux.getEstadoinicial(), "q" + tAux.getEstadofinal(), EdgeType.DIRECTED);
        }

        VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        Function<String, Paint> vertexPaint = vertex -> {
            if (vertex.equals("q" + estadoPasoaPaso)) {
                return Color.BLUE;
            }

            if (vertex.equals("q" + estadoinicial)) {
                return Color.GREEN;
            }

            for (int i = 0; i < estadosFinales.size(); i++) {
                String[] vertice = vertex.split("q");
                if (estadosFinales.contains(Integer.valueOf(vertice[1]))) {
                    return Color.RED;
                }
            }

            return Color.YELLOW;

        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint::apply);

        vAutomata.dispose();

        JDialog frame = new JDialog();
        frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().add(vv, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.dispose();

        vAutomata.setVisible(true);
    }

    public void pintarAFND() {
        ArrayList<Integer> estados, estadosFinales, estadosPasoaPaso, aux;
        ArrayList<Transicion> transiciones, transiciones_lambda;
        int estadoinicial;

        estados = automataND.getEstados();
        estadosFinales = automataND.getEstadosFinales();
        transiciones = automataND.getTransiciones();
        transiciones_lambda = automataND.getTransicionesLambda();
        estadoinicial = automataND.getEstadoinicial();
        estadosPasoaPaso = automataND.getEstadosPaP();

        DirectedSparseMultigraph<String, String> grafo = new DirectedSparseMultigraph<>();

        for (int i = 0; i < estados.size(); i++) {
            grafo.addVertex("q" + estados.get(i));
        }

        for (int i = 0; i < transiciones.size(); i++) {
            String v1 = "q" + transiciones.get(i).getEstadoinicial();

            aux = transiciones.get(i).getEstadosFinales();
            for (int j = 0; j < aux.size(); j++) {
                String v2 = "q" + aux.get(j);
                grafo.addVertex(v2);
                grafo.addEdge("[" + v1 + v2 + "] " + transiciones.get(i).getSimbolo(), v1, v2, EdgeType.DIRECTED);
            }
        }

        for (int i = 0; i < transiciones_lambda.size(); i++) {
            String v1 = "q" + transiciones_lambda.get(i).getEstadoinicial();

            aux = transiciones_lambda.get(i).getEstadosFinales();
            for (int j = 0; j < aux.size(); j++) {
                String v2 = "q" + aux.get(j);
                grafo.addVertex(v2);
                grafo.addEdge("[" + v1 + v2 + "] " + "λ", v1, v2, EdgeType.DIRECTED);
            }
        }

        VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        Function<String, Paint> vertexPaint = vertex -> {
            String[] vertice = vertex.split("q");

            if (estadosPasoaPaso != null) {
                if (estadosPasoaPaso.contains(Integer.valueOf(vertice[1]))) {
                    return Color.BLUE;
                }
            }

            if (vertex.equals("q" + estadoinicial)) {
                return Color.GREEN;
            }

            if (estadosFinales.contains(Integer.valueOf(vertice[1]))) {
                return Color.RED;
            }

            return Color.YELLOW;

        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint::apply);

        vAutomata.dispose();

        JDialog frame = new JDialog();
        frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().add(vv, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.dispose();

        vAutomata.setVisible(true);
    }
}
