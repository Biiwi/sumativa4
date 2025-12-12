package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

public class PaymentView extends JFrame {

    private JComboBox<Client> comboClients;
    private JList<RentInFees> listRents; // Lista para mostrar arriendos
    private DefaultListModel<RentInFees> listModelRents;
    private JTable tableFees;
    private DefaultTableModel modelFees;
    private JButton buttonShow, buttonPay;

    private RentInFees selectedRent;

    public PaymentView(){
        setTitle("Pago de cuotas Arriendo");
        setSize(750, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        initController();
    }

    private void initUI(){
        JLabel titleLabel = new JLabel("PAGO DE CUOTAS DE ARRIENDO");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        titleLabel.setBounds(250, 20, 300, 25);
        add(titleLabel);

        JLabel clientLabel = new JLabel("Seleccione Cliente:");
        clientLabel.setBounds(20, 60, 150, 25);
        add(clientLabel);

        comboClients = new JComboBox<>();
        comboClients.setBounds(150, 60, 200, 25);
        add(comboClients);

        JLabel rentsLabel = new JLabel("Arriendos del Cliente:");
        rentsLabel.setBounds(20, 100, 200, 25);
        add(rentsLabel);

        listModelRents = new DefaultListModel<>();
        listRents = new JList<>(listModelRents);

        JScrollPane scrollRents = new JScrollPane(listRents);
        scrollRents.setBounds(20, 130, 350, 200);
        add(scrollRents);

        buttonShow = new JButton("Mostrar Cuotas");
        buttonShow.setBounds(400, 180, 150, 30);
        buttonShow.setFont(new Font("Arial", Font.PLAIN, 12));
        add(buttonShow);

        JLabel payLabel = new JLabel("Pagos");
        payLabel.setFont(new Font("Arial", Font.BOLD, 14));
        payLabel.setBounds(400, 130, 100, 25);
        add(payLabel);

        String[] columnNames = {"N° Cuota", "Monto", "Estado"};
        modelFees = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
        }};
        tableFees = new JTable(modelFees);

        JScrollPane scrollFees = new JScrollPane(tableFees);
        scrollFees.setBounds(400, 160, 320, 200);
        add(scrollFees);

        buttonPay = new JButton("Pagar Cuota Seleccionada");
        buttonPay.setBounds(400, 370, 150, 30);
        add(buttonPay);
        
    }

    private void initController(){
        loadClients();

        comboClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                selectRentForClient();
            }
        });

        buttonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showFeesOfRent();
                buttonShow.setEnabled(false);
                buttonShow.setVisible(false);
            }      
        });

        buttonPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                paySelectedFee();
            }
        });
    }

    private void loadClients(){
        comboClients.removeAllItems();
        for (Client c: Data.clients){
            comboClients.addItem(c);
        }
        comboClients.setSelectedIndex(-1);
    }

    private void selectRentForClient(){
        listModelRents.clear();
        modelFees.setRowCount(0);
        selectedRent = null;

        Client client = (Client) comboClients.getSelectedItem();
        if(client == null) {return;}
        for(RentInFees r: Data.rents){
            if(r.getClient().getRun().equals(client.getRun())){
                listModelRents.addElement(r);
            }
        }

        if (listModelRents.isEmpty()){
            Tools.generateMessage("Este cliente no cuenta con arriendos en curso", false);
        }
        else{
            buttonShow.setVisible(true);
            buttonShow.setEnabled(true);
        }
    }

    private void showFeesOfRent(){
        selectedRent = listRents.getSelectedValue();
        if(selectedRent == null){
            Tools.generateMessage("Debe seleccionar un arriendo", false);
            return;
        }

        fillTable(selectedRent);

    }


    private void fillTable(RentInFees rent){
        modelFees.setRowCount(0);
        for (Fee f : rent.getFees()){
            Object[] row = {f.getNumber(), f.getValue(), f.getIsPaid() ? "SI" : "NO"};
            modelFees.addRow(row);
        }
    }

    private void paySelectedFee(){
        int selectedRow = tableFees.getSelectedRow();
        if(selectedRow == -1){
            Tools.generateMessage("Seleccione una fila válida", false);
            return;
        }

        int numFee = (int) tableFees.getValueAt(selectedRow, 0);

        boolean correctOperation = false;
        for (Fee f: selectedRent.getFees()){
            if(f.getNumber() == numFee){
                if(f.payFee()){
                    correctOperation = true;
                    Tools.generateMessage("Pago registrado exitosamente", true);
                    fillTable(selectedRent);
                } else{
                    Tools.generateMessage("cuota seleccionada ya se encuentra pagada", false);
                }
                break;
            }
        }
    }
}