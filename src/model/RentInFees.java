package model;
import java.util.ArrayList;

public class RentInFees extends Rent {
    private int feeCount;
    private ArrayList<Fee> fees;

    /*CONSTRUCTOR */
    public RentInFees(int rentId, String date, int days, Client client, Vehicle vehicle, int feeCount){
        super(rentId, date, days, client, vehicle);
        this.feeCount = feeCount;
        this.fees = new ArrayList<Fee>();
    }
    /*GETTERS */
    public ArrayList<Fee> getFees(){return fees;}

    public int getFeeCount(){return feeCount;}

    /*CUSTOMERS */
    public boolean enterRentWithFee(int pricePerDay){
        if(super.evaluateRent()){
            this.getVehicle().setVehicleState('A');
            this.fees = generateFees(pricePerDay);
            return true;
        }
        return false;
    }

    public ArrayList<Fee> generateFees(int pricePerDay){
        ArrayList<Fee> generatedList = new ArrayList<>();
        int totalAmount = super.getAmountToPay(pricePerDay);
        int feeValue = totalAmount / this.feeCount;

        for (int i = 1; i <= this.feeCount; i++) {
            generatedList.add(new Fee(i, feeValue));
        }
        return generatedList;
    }

    @Override
    public String toString() {
        return "Arriendo en cuotas N°" + this.getRentId() + ", Cantidad de cuotas: " + this.feeCount + "\n" + "Cliente: " + this.getClient().getName() + " (" + this.getClient().getRun() + ")\n" + "Vehículo patente: " + this.getVehicle().getPlateNumber() ;
    }
}
