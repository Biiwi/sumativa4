package view;

import java.awt.*;
import javax.swing.*;
import model.*;

public class MainView extends JFrame {


    private JMenuItem clientsMenu;
    private JMenuItem rentMenu;
    private JMenuItem payMenu;

    public MainView() {

        setTitle("Sistema Car-REnt - Gestión de Arriendos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Data.initTestData();
        
        initMenu();
        initContent();

    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu gestionMenu = new JMenu("Clientes");
        clientsMenu = new JMenuItem("Administrar clientes");
        gestionMenu.add(clientsMenu);
        clientsMenu.addActionListener(e -> {
            ClientView clientView = new ClientView();
            clientView.setVisible(true);
        });

        JMenu operationsMenu = new JMenu("Operaciones");
        rentMenu = new JMenuItem("Arriendo");
        payMenu = new JMenuItem("Pagar");

        operationsMenu.add(rentMenu);
        rentMenu.addActionListener(e -> {
            RentView rentView = new RentView();
            rentView.setVisible(true);
        });

        operationsMenu.addSeparator();

        operationsMenu.add(payMenu);
        payMenu.addActionListener(e -> {
            PaymentView paymentView = new PaymentView();
            paymentView.setVisible(true);
        });

        menuBar.add(gestionMenu);
        menuBar.add(operationsMenu);
        
        setJMenuBar(menuBar);
    }

    private void initContent(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(Box.createVerticalStrut(150));

        JLabel titleLabel = new JLabel("Bienvenido a Car-REnt");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Sistema de gestión de arriendos de vehículos con cuotas");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        add(mainPanel, BorderLayout.CENTER);
    }
}
