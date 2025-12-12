package model;

public class Rent {
    
    private int rentId;
    private String date;
    private int days;

    private Client client;
    private Vehicle vehicle;

    public Rent(int rentId, String date, int days, Client client, Vehicle vehicle) {
        this.rentId = rentId;
        this.date = date;
        this.setDays(days);
        this.client = client;
        this.vehicle = vehicle;
    }

    /*GETTERS*/
    public int getRentId(){
        return rentId;
    }

    public String getDate(){
        return date;
    }

    public int getDays(){
        return days;
    }

    public Client getClient(){
        return client;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    /*CUSTOMERS */
    public int getAmountToPay(int pricePerDay){
        return days * pricePerDay;
    }

    public boolean evaluateRent() {
        if (client == null || vehicle == null) {
            Tools.generateMessage("Faltan datos de cliente o vehículo", false);
            return false;
        }

        if(client.getValidState() && (vehicle.getVehicleState() == 'D')){
            return true;
        }
        
        Tools.generateMessage("Cliente no válido o vehículo no disponible", false);
        return false;
    }

    /*SETTERS */
    public void setRentId(int rentId){
        this.rentId = rentId;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setDays(int days){
        if(days > 0){
            this.days = days;
        }
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    /*PRINTERS */

    @Override
    public String toString() {
        return ("Arriendo numero: " + this.rentId + ", fecha: " + this.date + ", días de arriendo: " + this.days + " patente vehículo:  "+ vehicle.getPlateNumber() + "arrendatario: " + client.getName() + "(" + client.getRun() + ")");
    }
}
