
import java.util.ArrayList;
import java.util.List;

public class Camion {
    private int idCamion;
    private String patente;
    private boolean estaRefrigerado;
    private int capacidadKg, capacidadActual;
    private List<Paquete> paquetes;

    public Camion(int idCamion, String patente, boolean estaRefrigerado, int capacidadKg) {
        this.idCamion = idCamion;
        this.patente = patente;
        this.estaRefrigerado = estaRefrigerado;
        this.capacidadKg = capacidadKg;
        this.capacidadActual = capacidadKg;
        this.paquetes = new ArrayList<>();
    }

    public void agregarPaquete(Paquete p) {
        if (!paquetes.contains(p)) {
            paquetes.add(p);
            capacidadActual -= p.getPeso_kg();
        }
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

    // devolver copia
    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }
}
