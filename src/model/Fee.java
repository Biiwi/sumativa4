package model;

public class Fee {
    private int number;
    private int value;
    private boolean isPaid;

    /*CONSTRUCTOR */
    public Fee(int number, int value){
        this.number = number;
        this.value = value;
        this.isPaid = false;
    }

    /*CUSTOMERS */
    public boolean payFee(){
        if(!this.isPaid){
            this.isPaid = true;
            return true;
        }
        return false;
    }

    /*GETTERS */
    public int getNumber() {return number;}
    public int getValue() {return value;}
    public boolean getIsPaid() {return isPaid;}

    /*PRINTERS */
    @Override
    public String toString() {
        return ("Cuota N°" + this.number + ", Valor: " + this.value + ", Estado: " + (isPaid ? "Pagada" : "Pendiente"));
    }
}
