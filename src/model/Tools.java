package model;

import javax.swing.JOptionPane;

public class Tools {
    /*PRINTERS*/
    public static void generateMessage(String message, boolean typeMesage){
        if(typeMesage){
            JOptionPane.showMessageDialog(null, message, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, message, "Error en la operación", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String toString() {
        return ("Cliente " + name + ", RUN: " + run + ", Estado: " + (isValid ? "Vigente" : "No Vigente"));
    }
}
