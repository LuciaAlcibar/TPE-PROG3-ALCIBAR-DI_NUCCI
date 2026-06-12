
public class Paquete implements Comparable<Paquete> {

    private int id_paquete;
    private String codigo_paquete;
    private double peso_kg;
    private boolean contiene_alimento;
    private int nivel_urgencia;

    public Paquete(int id_paquete, String codigo_paquete, boolean contiene_alimento, int nivel_urgencia, double peso_kg) {
        this.id_paquete = id_paquete;
        this.codigo_paquete = codigo_paquete;
        this.contiene_alimento = contiene_alimento;
        setNivel_urgencia(nivel_urgencia);
        this.peso_kg = peso_kg;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public String getCodigo_paquete() {
        return codigo_paquete;
    }

    public void setCodigo_paquete(String codigo_paquete) {
        this.codigo_paquete = codigo_paquete;
    }

    public double getPeso_kg() {
        return peso_kg;
    }

    public void setPeso_kg(double peso_kg) {
        this.peso_kg = peso_kg;
    }

    public boolean contiene_alimento() {
        return contiene_alimento;
    }

    public void setContiene_alimento(boolean contiene_alimento) {
        this.contiene_alimento = contiene_alimento;
    }

    public int getNivel_urgencia() {
        return nivel_urgencia;
    }

    public void setNivel_urgencia(int nivel_urgencia) {
        if (nivel_urgencia >= 1 && nivel_urgencia <= 100) {
            this.nivel_urgencia = nivel_urgencia;
        } else {
            throw new IllegalArgumentException("El nivel de urgencia debe ser un valor entre 1 y 100.");
        }
    }

    @Override
    public int compareTo(Paquete otro) {
        return Double.compare(this.peso_kg, otro.peso_kg);
    }

    @Override
    public String toString() {
        return "Paquete{"
                + "id_paquete=" + id_paquete
                + ", codigo_paquete='" + codigo_paquete + '\''
                + ", peso_kg=" + peso_kg
                + ", contiene_alimento=" + contiene_alimento
                + ", nivel_urgencia=" + nivel_urgencia
                + '}';
    }
}
