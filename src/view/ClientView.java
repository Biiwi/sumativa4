package view;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import model.*;

public class ClientView extends JFrame {

    private JTextField textRun, textName;
    private JButton buttonAdd;

    public ClientView() {
        setTitle("Gestión de clientes");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();

        buttonAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                addClient();
            }
        });
    }

    public void addClient(){
        String run = textRun.getText();
        String name = textName.getText();

        for (Client c : Data.clients) {
            if (c.getRun().equalsIgnoreCase(run)) {
                Tools.generateMessage("Ya existe un cliente con el RUT " + run, false);
                return;
            }
            if (c.getName().isEmpty()) {
                Tools.generateMessage("El campo de nombre está vacio " + run, false);
                return;
            }
        }

        Client newClient = new Client(run, name);

        if(newClient.getRun() != null && !newClient.getRun().isEmpty()){
            Data.clients.add(newClient);
            Tools.generateMessage("Cliente guardado exitosamente", true);
            dispose();
        }
    }


    private void initUI(){
        JLabel titleLabel = new JLabel("CLIENTES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(20, 20, 150, 20);
        add(titleLabel);

        JLabel runLabel = new JLabel("Cédula:");
        runLabel.setBounds(50, 60, 80, 25);
        add(runLabel);

        textRun = new JTextField();
        textRun.setBounds(120, 60, 180, 25);
        add(textRun);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setBounds(50, 100, 80, 25);
        add(nameLabel);

        textName = new JTextField();
        textName.setBounds(120, 100, 180, 25);
        add(textName);

        JLabel vigLabel = new JLabel("Vigencia: Habilitado"); // Fijo visualmente
        vigLabel.setBounds(120, 140, 150, 25);
        add(vigLabel);

        buttonAdd = new JButton("Agregar");
        buttonAdd.setBounds(150, 200, 100, 30);
        add(buttonAdd);

    }
}