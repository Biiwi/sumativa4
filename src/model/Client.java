package model;

public class Client {
    
    private String run;
    private String name;
    private boolean isValid;

    public Client(String run, String name, boolean isValid) {
        this.setRun(run);
        this.setName(name);
        this.setValidState(isValid);
    }

    public Client(String run, String name) {
        this(run, name, true);
    }

    /*GETTERS*/
    public String getRun(){
        return run;
    }

    public String getName(){
        return name;
    }

    public boolean getValidState(){
        return isValid;
    }

    /*SETTERS*/
    public void setRun(String run){
        if (validData(run) && validateRunStructure(run)) {
            this.run = run;
            return;
        }
        Tools.generateMessage("RUT ingresado es inválido", false);
    }

    public void setName(String name){
        if (validData(name)) {
            this.name = name.toUpperCase();
            return;
        }
        Tools.generateMessage("El Nombre ingresado es incorrecto", false);
    }

    public void setValidState(boolean isValid){
        this.isValid = isValid;
    }


    /*VALIDATORS */
    public boolean validateRunStructure(String run){
        String runPattern = "^[0-9]+-[0-9kK]{1}$";
        if (run != null && run.matches(runPattern)) {
            return true;
        }
        return false;
    }

    public boolean validData(String data){
        if (data != null && !data.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /*PRINTERS*/
    @Override
    public String toString() {
        return (name + ", " + run + ", " + (isValid ? "Vigente" : "No Vigente"));
    }
}
