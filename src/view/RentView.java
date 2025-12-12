package view;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;

public class RentView extends JFrame {

    private JComboBox<Client> comboClients;
    private JComboBox<Vehicle> comboVehicles;
    private JTextField textDate, textDays, textPrice, textFeeCount, textTotalAmount;
    private JTable tableFees;
    private DefaultTableModel modelFees;
    private JButton buttonSave, buttonPay, buttonNewClient;

    private RentInFees currentRent;

    public RentView(){
        setTitle("Arriendo en cuotas");
        setSize(600, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadData();
        initController();
    }

    private void initUI(){
        JLabel titleLabel = new JLabel("ARRIENDO DE VEHÍCULOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(150, 20, 300, 25);
        add(titleLabel);

        JLabel clientLabel = new JLabel("Seleccione Cliente:");
        clientLabel.setBounds(20, 60, 150, 25);
        add(clientLabel);

        comboClients = new JComboBox<>();
        comboClients.setBounds(150, 60, 200, 25);
        add(comboClients);

        buttonNewClient = new JButton("Nuevo Cliente");
        buttonNewClient.setBounds(370, 60, 130, 25);
        add(buttonNewClient);

        JLabel vehicleLabel = new JLabel("Seleccione Vehículo:");
        vehicleLabel.setBounds(20, 100, 150, 25);
        add(vehicleLabel);

        comboVehicles = new JComboBox<>();
        comboVehicles.setBounds(150, 100, 200, 25);
        add(comboVehicles);

        int startY = 140;

        addLabel("Fecha de Arriendo (dd/mm/yyyy):", 20, startY);
        
        textDate = new JTextField();
        textDate.setBounds(220, startY, 100, 25);
        add(textDate);

        addLabel("Días de Arriendo:", 20, startY + 40);
        textDays = new JTextField();
        textDays.setBounds(220, startY + 40, 100, 25);
        add(textDays);

        addLabel("Precio Diario:", 20, startY + 80);
        textPrice = new JTextField();
        textPrice.setBounds(220, startY + 80, 100, 25);
        add(textPrice);

        addLabel("Número de Cuotas:", 350, startY);
        textFeeCount = new JTextField();
        textFeeCount.setBounds(480, startY, 100, 25);
        add(textFeeCount);
        
        addLabel("Monto Total:", 350, startY + 40);
        textTotalAmount = new JTextField();
        textTotalAmount.setBounds(480, startY + 40, 100, 25);
        textTotalAmount.setEditable(false);
        add(textTotalAmount);

        buttonSave = new JButton("Generar Arriendo");
        buttonSave.setBounds(220, startY + 120, 150, 30);
        add(buttonSave);

        JLabel tableLabel = new JLabel("Detalle Arriendo:");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tableLabel.setBounds(20, startY + 170, 200, 25);
        add(tableLabel);

        // JLabel headersLabel = new JLabel("N°   Monto   Estado");
        // headersLabel.setBounds(20, startY + 200, 200, 25);
        // add(headersLabel);

        String[] columnNames = {"N° Cuota", "Monto", "Estado"};
        modelFees = new DefaultTableModel(columnNames, 0){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }};
        tableFees = new JTable(modelFees);

        JScrollPane scrollTable = new JScrollPane(tableFees);
        scrollTable.setBounds(20, startY + 230, 560, 150);
        add(scrollTable);

        buttonPay = new JButton("Pagar Cuota Seleccionada");
        buttonPay.setBounds(200, startY + 400, 200, 30);
        add(buttonPay);
    }

    private void loadData(){
        comboClients.removeAllItems();
        for (Client c: Data.clients){
            comboClients.addItem(c);
        }

        comboVehicles.removeAllItems();
        for (Vehicle v: Data.vehicles){
            comboVehicles.addItem(v);
        }

        comboClients.setSelectedIndex(-1);
        comboVehicles.setSelectedIndex(-1);
    }

    private void initController(){
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                saveRent();
            }
        });

        buttonPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                payFee();
                fillFeeTable(currentRent.getFees());
            }
        });

        buttonNewClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ClientView cv = new ClientView();
                cv.setVisible(true);
                cv.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosed(WindowEvent windowEvent){
                        loadData();
                    }
                });
            }
        });
    }

    private void saveRent(){
        try {
            Client client = (Client) comboClients.getSelectedItem();
            Vehicle vehicle = (Vehicle) comboVehicles.getSelectedItem();

            if(client == null || vehicle == null){
                Tools.generateMessage("Debe seleccionar un cliente y un vehículo", false);
                return;
            }

            String date = textDate.getText();
            int days = Integer.parseInt(textDays.getText());
            int price = Integer.parseInt(textPrice.getText());
            int feeCount = Integer.parseInt(textFeeCount.getText());

            int newId = Data.rents.size() + 1;

            currentRent = new RentInFees(newId, date, days, client, vehicle, feeCount);

            if (currentRent.enterRentWithFee(price)){
                Data.rents.add(currentRent);
                textTotalAmount.setText(String.valueOf(currentRent.getAmountToPay(price)));
                fillFeeTable(currentRent.getFees());

                buttonPay.setEnabled(true);
                buttonSave.setEnabled(true);

                Tools.generateMessage("Arriendo generado exitosamente", true);
            }

        } catch (NumberFormatException ex){
            Tools.generateMessage("Formato de fecha, precio o cuotas escrito incorrectamente.\n Asegurese que estén escrito en números", false);
        } catch (Exception e) {
            Tools.generateMessage(e.getMessage(), false);  
        }
    }

    private void fillFeeTable(ArrayList<Fee> fees){
        modelFees.setRowCount(0);
        for (Fee f: fees){
            Object[] row = {f.getNumber(), f.getValue(), f.getIsPaid() ? "SI" : "NO"};
            modelFees.addRow(row);
        }
    }

    private void payFee(){
        if(currentRent != null){
            for (Fee f: currentRent.getFees()){
                if (f.getNumber() == 1){
                    if(f.payFee()){
                        Tools.generateMessage("cuota Pagada", true);
                    } else{
                        Tools.generateMessage("Cuota ya se encuentra pagada", false);
                    }
                    return;
                }
            }
        }
    }

    private void addLabel(String text, int x, int y){
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 25);
        add(label);
    }
}
