package model;

public class Vehicle {
    
    private String plateNumber;
    private char vehicleState; // 'D' Disponible, 'A' Arrendado, 'M' Mantenimiento

    public Vehicle(String plateNumber, char vehicleState) {
        this.setPlateNumber(plateNumber);
        this.setVehicleState(vehicleState);
    }

    public Vehicle(String plateNumber) {
        this(plateNumber, 'D');
    }
    
    /*GETTERS */
    public String getPlateNumber(){
        return plateNumber;
    }

    public char getVehicleState(){
        return vehicleState;
    }

    /*SETTERS */
    public void setPlateNumber(String plateNumber){
        if(validData(plateNumber) && validatePlateNumberStructure(plateNumber)){
            this.plateNumber = plateNumber;
            return;
        }
        Tools.generateMessage("Datos ingresados en patente de vehículo incorrecto", false);
    }

    public void setVehicleState(char vehicleState){
        if(validateVehicleState(vehicleState)){
            this.vehicleState = vehicleState;
            return;
        }
        Tools.generateMessage("Estado ingresado no existe", false);
    }


    /*VALIDATORS */
    public boolean validData(String data){
        if (data != null && !data.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean validatePlateNumberStructure(String plateNumber){
        String platePattern = "^[A-Z]{4}-[0-9]{2}$";
        if (plateNumber != null && plateNumber.matches(platePattern)) {
            return true;
        }
        return false;
    }

    public boolean validateVehicleState(char vehicleState){
        if (vehicleState == 'D' || vehicleState == 'A') {
            return true;
        }
        return false;
    }


    /*PRINTERS */

    @Override
    public String toString() {
        return ("Vehículo patente " + this.getPlateNumber() + ", Condición: " + (vehicleState == 'D' ? "Disponible" : "Arrendado"));
    }

}
