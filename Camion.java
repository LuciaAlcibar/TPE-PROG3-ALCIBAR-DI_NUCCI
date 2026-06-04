public class Camion {
    private int idCamion;
    private String patente;
    private boolean estaRefrigerado;
    private int capacidadKg;

    public Camion(int idCamion, String patente, boolean estaRefrigerado, int capacidadKg) {
        this.idCamion = idCamion;
        this.patente = patente;
        this.estaRefrigerado = estaRefrigerado;
        this.capacidadKg = capacidadKg;
    }

    public int getIdCamion() {
        return idCamion;
    }

    public String getPatente() {
        return patente;
    }

    public boolean estaRefrigerado() {
        return estaRefrigerado;
    }

    public int getCapacidadKg() {
        return capacidadKg;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setEstaRefrigerado(boolean estaRefrigerado) {
        this.estaRefrigerado = estaRefrigerado;
    }

    public void setCapacidadKg(int capacidadKg) {
        this.capacidadKg = capacidadKg;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "idCamion=" + idCamion +
                ", patente='" + patente + '\'' +
                ", estaRefrigerado=" + estaRefrigerado +
                ", capacidadKg=" + capacidadKg +
                '}';
    }
}
