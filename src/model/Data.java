package model;

import java.util.ArrayList;

public class Data {
    
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<RentInFees> rents = new ArrayList<>();

    public static void initTestData(){
        clients.add(new Client("12345678-9", "Juan Perez"));
        clients.add(new Client("09876543-2", "Felipe Figueroa", false));

        vehicles.add(new Vehicle("ABCD-11", 'D'));
        vehicles.add(new Vehicle("EFGH-22", 'A'));
        vehicles.add(new Vehicle("IJKL-33"));
        Tools.generateMessage("Datos de prueba iniciales cargados en memoria", true);
    }
}
