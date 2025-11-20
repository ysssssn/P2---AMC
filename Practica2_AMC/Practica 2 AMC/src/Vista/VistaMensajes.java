/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class VistaMensajes {
    
    public String mensajeImput(String texto, Component C){
        String s = "";
        
        s = JOptionPane.showInputDialog(C, texto);
        
        return s;
    }
    
    public int mensajeJOptionPaneYesNo(String texto, String titulo, Component C){
        return JOptionPane.showConfirmDialog(C,
                texto, titulo, JOptionPane.YES_NO_OPTION);
    }
    
    public void mensajeJOptionPaneINFORMATION(String texto, String titulo, Component C){
        JOptionPane.showMessageDialog(C,
                    texto, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mensajeJOptionPaneWARNING(String texto, String titulo, Component C){
        JOptionPane.showMessageDialog(C,
                    texto,titulo, JOptionPane.WARNING_MESSAGE);
    }
    
    public void mensajeJOptionPaneERROR(String texto, String titulo, Component C){
        JOptionPane.showMessageDialog(C,
                    texto,titulo, JOptionPane.ERROR_MESSAGE);
    }
    
}
