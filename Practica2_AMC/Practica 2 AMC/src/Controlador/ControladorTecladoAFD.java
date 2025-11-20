/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.AFD;
import Vista.VistaTecladoAFD;
import Vista.VistaMensajes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class ControladorTecladoAFD implements ActionListener {

    private AFD automataD;
    private boolean noseguir = false;

    private VistaTecladoAFD vTecladoAFD;
    private VistaMensajes vMensaje;
    private DefaultTableModel dtm;

    public void addListeners() {
        vTecladoAFD.buttonAT.addActionListener(this);
        vTecladoAFD.buttonBT.addActionListener(this);
        vTecladoAFD.buttonCargar.addActionListener(this);
        vTecladoAFD.buttonSalir.addActionListener(this);
    }

    public ControladorTecladoAFD(AFD automataD) {
        vMensaje = new VistaMensajes();
        vTecladoAFD = new VistaTecladoAFD();
        this.automataD = automataD;

        addListeners();

        vTecladoAFD.setResizable(false);
        vTecladoAFD.setLocationRelativeTo(null);

        dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Est Origen", "Simbolo", "Est Destino"});
        vTecladoAFD.tablaTransiciones.setModel(dtm);

        vTecladoAFD.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "AñadirT":
                String[] t = new String[3];
                t[0] = vMensaje.mensajeImput("Estado Origen: ", vTecladoAFD);
                t[1] = vMensaje.mensajeImput("Simbolo: ", vTecladoAFD);
                t[2] = vMensaje.mensajeImput("Estado Destino: ", vTecladoAFD);

                dtm.addRow(new Object[]{t[0], t[1], t[2]});

                break;

            case "BorrarT":
                int fila = vTecladoAFD.tablaTransiciones.getSelectedRow();

                dtm.removeRow(fila);

                break;

            case "Cargar":

                String aux;
                String[] partes;

                ArrayList<String> estados = new ArrayList<>();

                partes = vTecladoAFD.txtEstados.getText().split(" ");
                for (String parte : partes) {
                    estados.add(parte);
                    String[] est = parte.split("q");
                    automataD.añadirEstado(Integer.parseInt(est[1]));
                }

                aux = vTecladoAFD.txtEstIni.getText();
                if (estados.contains(aux)) {
                    partes = aux.split("q");
                    automataD.setEstadoInicial(Integer.parseInt(partes[1]));
                } else {
                    vMensaje.mensajeJOptionPaneERROR("El estado inicial no pertenece a los estados", "", vTecladoAFD);
                    break;
                }

                boolean fin = false;
                partes = vTecladoAFD.txtEstFin.getText().split(" ");
                for (int i = 0; i < partes.length; i++) {
                    if (estados.contains(partes[i]) && !partes[i].equals(aux)) {
                        String[] est = partes[i].split("q");
                        automataD.añadirEstadoFinal(Integer.parseInt(est[1]));
                    } else {
                        vMensaje.mensajeJOptionPaneERROR("El estado final " + partes[i] + " no pertenece a los estados"
                                + "\nO el estado final es igual que el estado inicial", "", vTecladoAFD);
                        fin = true;
                        break;
                    }
                }
                if (fin) {
                    break;
                }

                while (dtm.getRowCount() > 0) {
                    String c0 = (String) dtm.getValueAt(0, 0);
                    if (!estados.contains(c0)) {
                        vMensaje.mensajeJOptionPaneERROR("El estado " + c0 + " no pertenece a los estados", "", vTecladoAFD);
                        fin = true;
                        break;
                    }
                    String c1 = (String) dtm.getValueAt(0, 1);

                    String c2 = (String) dtm.getValueAt(0, 2);
                    if (!estados.contains(c2)) {
                        vMensaje.mensajeJOptionPaneERROR("El estado " + c2 + " no pertenece a los estados", "", vTecladoAFD);
                        fin = true;
                        break;
                    }

                    String[] c = c0.split("q");
                    c0 = c[1];

                    c = c2.split("q");
                    c2 = c[1];

                    automataD.agregarTransicion(Integer.parseInt(c0), c1.charAt(0), Integer.parseInt(c2));
                    dtm.removeRow(0);
                }
                if (fin) {
                    break;
                }

                vTecladoAFD.dispose();
                break;

            case "Salir":
                vTecladoAFD.dispose();
                noseguir=true;
                break;
        }
    }
    
    public boolean noSeguir(){
        return noseguir;
    }

}
