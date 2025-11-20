/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.AFND;
import Vista.VistaMensajes;
import Vista.VistaTecladoAFND;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class ControladorTecladoAFND implements ActionListener {

    private AFND automataND;
    private boolean noseguir = false;

    private VistaTecladoAFND vTecladoAFND;
    private VistaMensajes vMensaje;
    private DefaultTableModel dtmT, dtmTLambda;

    private void addListeners() {
        vTecladoAFND.buttonAT.addActionListener(this);
        vTecladoAFND.buttonBT.addActionListener(this);
        vTecladoAFND.buttonCargar.addActionListener(this);
        vTecladoAFND.buttonSalir.addActionListener(this);
        vTecladoAFND.buttonATLambda.addActionListener(this);
        vTecladoAFND.buttonBTLambda.addActionListener(this);
    }

    public ControladorTecladoAFND(AFND automataND) {
        this.automataND = automataND;
        vTecladoAFND = new VistaTecladoAFND();
        vMensaje = new VistaMensajes();

        addListeners();

        vTecladoAFND.setResizable(false);
        vTecladoAFND.setLocationRelativeTo(null);

        dtmT = new DefaultTableModel();
        dtmT.setColumnIdentifiers(new String[]{"Est Origen", "Simbolo", "Est Destino"});
        vTecladoAFND.tablaTransiciones.setModel(dtmT);

        dtmTLambda = new DefaultTableModel();
        dtmTLambda.setColumnIdentifiers(new String[]{"Est Origen", "Est Destino"});
        vTecladoAFND.tablaTransicionesLAMBDA.setModel(dtmTLambda);

        vTecladoAFND.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "AñadirT": {
                String[] t = new String[3];
                t[0] = vMensaje.mensajeImput("Estado Origen: ", vTecladoAFND);
                t[1] = vMensaje.mensajeImput("Simbolo: ", vTecladoAFND);
                t[2] = vMensaje.mensajeImput("Estado Destino: ", vTecladoAFND);

                dtmT.addRow(new Object[]{t[0], t[1], t[2]});
            }
            break;

            case "BorrarT": {
                int fila = vTecladoAFND.tablaTransiciones.getSelectedRow();
                dtmT.removeRow(fila);
            }
            break;

            case "AñadirTLambda": {
                String[] t = new String[2];
                t[0] = vMensaje.mensajeImput("Estado Origen: ", vTecladoAFND);
                t[1] = vMensaje.mensajeImput("Estado Destino: ", vTecladoAFND);

                dtmTLambda.addRow(new Object[]{t[0], t[1]});
            }
            break;

            case "BorrarTLambda": {
                int fila = vTecladoAFND.tablaTransicionesLAMBDA.getSelectedRow();
                dtmT.removeRow(fila);
            }
            break;

            case "Cargar":

                String aux;
                String[] partes;

                ArrayList<String> estados = new ArrayList<>();

                partes = vTecladoAFND.txtEstados.getText().split(" ");
                for (String parte : partes) {
                    estados.add(parte);
                    String[] est = parte.split("q");
                    automataND.añadirEstado(Integer.parseInt(est[1]));
                }

                aux = vTecladoAFND.txtEstIni.getText();
                if (estados.contains(aux)) {
                    partes = aux.split("q");
                    automataND.setEstadoInicial(Integer.parseInt(partes[1]));
                } else {
                    vMensaje.mensajeJOptionPaneERROR("El estado inicial no pertenece a los estados", "", vTecladoAFND);
                    break;
                }

                boolean fin = false;
                partes = vTecladoAFND.txtEstFin.getText().split(" ");
                for (int i = 0; i < partes.length; i++) {
                    if (estados.contains(partes[i]) && !partes[i].equals(aux)) {
                        String[] est = partes[i].split("q");
                        automataND.añadirEstadoFinal(Integer.parseInt(est[1]));
                    } else {
                        vMensaje.mensajeJOptionPaneERROR("El estado final " + partes[i] + " no pertenece a los estados"
                                + "\nO el estado final es igual que el estado inicial", "", vTecladoAFND);
                        fin = true;
                        break;
                    }
                }
                if (fin) {
                    break;
                }

                while (dtmT.getRowCount() > 0) {
                    String c0 = (String) dtmT.getValueAt(0, 0);
                    if (!estados.contains(c0)) {
                        vMensaje.mensajeJOptionPaneERROR("El estado " + c0 + " no pertenece a los estados", "", vTecladoAFND);
                        fin = true;
                        break;
                    }
                    String c1 = (String) dtmT.getValueAt(0, 1);

                    String c2 = (String) dtmT.getValueAt(0, 2);
                    partes = c2.split(" ");

                    ArrayList<Integer> estadosDestino = new ArrayList<>();
                    for (int i = 0; i < partes.length; i++) {
                        if (!estados.contains(partes[i])) {
                            vMensaje.mensajeJOptionPaneERROR("El estado " + partes[i] + " no pertenece a los estados", "", vTecladoAFND);
                            fin = true;
                            break;
                        }

                        String[] p = partes[i].split("q");
                        estadosDestino.add(Integer.valueOf(p[1]));
                    }

                    String[] c = c0.split("q");
                    c0 = c[1];

                    automataND.agregarTransicion(Integer.parseInt(c0), c1.charAt(0), estadosDestino);
                    dtmT.removeRow(0);
                }
                if (fin) {
                    break;
                }

                while (dtmTLambda.getRowCount() > 0) {
                    String c0 = (String) dtmTLambda.getValueAt(0, 0);
                    if (!estados.contains(c0)) {
                        vMensaje.mensajeJOptionPaneERROR("El estado " + c0 + " no pertenece a los estados", "", vTecladoAFND);
                        fin = true;
                        break;
                    }

                    String c1 = (String) dtmTLambda.getValueAt(0, 1);
                    partes = c1.split(" ");

                    ArrayList<Integer> estadosDestino = new ArrayList<>();
                    for (int i = 0; i < partes.length; i++) {
                        if (!estados.contains(partes[i])) {
                            vMensaje.mensajeJOptionPaneERROR("El estado " + partes[i] + " no pertenece a los estados", "", vTecladoAFND);
                            fin = true;
                            break;
                        }

                        String[] p = partes[i].split("q");
                        estadosDestino.add(Integer.valueOf(p[1]));
                    }

                    String[] c = c0.split("q");
                    c0 = c[1];

                    automataND.agregarTransicionλ(Integer.parseInt(c0), estadosDestino);
                    dtmTLambda.removeRow(0);
                }
                if (fin) {
                    break;
                }

                vTecladoAFND.dispose();
                break;

            case "Salir":
                vTecladoAFND.dispose();
                noseguir = true;
                break;
        }
    }

    public boolean noSeguir() {
        return noseguir;
    }

}
