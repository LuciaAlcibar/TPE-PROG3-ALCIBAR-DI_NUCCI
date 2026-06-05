
import java.util.List;

public class Servicios {

    private GestionArchivos gestionArchivos;

    public Servicios(String pathCamiones, String pathPaquetes) {
        this.gestionArchivos = new GestionArchivos();
        this.gestionArchivos.cargarInformacion(pathCamiones, pathPaquetes);
    }

    public Paquete servicio1(String codigo_paquete) {
        return gestionArchivos.getPaquetesPorCodigo().get(codigo_paquete);
    }

    public List<Paquete> servicio2(boolean contieneAlimentos) {

        if (contieneAlimentos) {
            return gestionArchivos.getPaquetesConAlimento();
        }
        return gestionArchivos.getPaquetesSinAlimento();
    }

    public List<Paquete> servicio3(int urgenciaMin, int urgenciaMax) {
        return gestionArchivos.getArbolUrgencias().getPaquetesEnRango(urgenciaMin, urgenciaMax);
    }

    // GREEDY
    /*
     * Estrategia Greedy: buscar el paquete de mayor peso. 
     * En cada iteración se selecciona el paquete de mayor peso aún no asignado,
     * ya que dejar sin asignar paquetes pesados incrementa más el peso total no
     * transportado. Luego, se busca un camión factible que respete las
     * restriccionesde capacidad y refrigeración, eligiendo aquel que deje el menor
     * espacio libre disponible. De esta manera se intenta aprovechar mejor la 
     * capacidad de los camiones y minimizar el peso total de los paquetes no asignados.
     */
    public void greedy() {

        List<Paquete> paquetes = gestionArchivos.getPaquetes();
        List<Camion> camiones = gestionArchivos.getCamiones();

        double pesoNoAsignado = 0;
        int candidatosConsiderados = 0;

        while (!paquetes.isEmpty()) {

            Paquete p = seleccionar(paquetes);

            paquetes.remove(p);

            candidatosConsiderados++;

            Camion mejorCamion = buscarCamionFactible(p, camiones);

            if (mejorCamion != null) {

                mejorCamion.agregarPaquete(p);

            } else {

                pesoNoAsignado += p.getPeso_kg();
            }
        }

        mostrarResultado(
                camiones,
                pesoNoAsignado,
                candidatosConsiderados);
    }

    private void mostrarResultado(
            List<Camion> camiones,
            double pesoNoAsignado,
            int candidatosConsiderados) {

        System.out.println("----- GREEDY -----");

        // mostrar camiones y paquetes

        System.out.println(
                "Peso no asignado: "
                        + pesoNoAsignado
                        + " kg");

        System.out.println(
                "Cantidad de candidatos considerados: "
                        + candidatosConsiderados);
    }

    public Paquete seleccionar(List<Paquete> paquetes) {
        double mejor = -1;
        Paquete paquete = null;

        for (Paquete p : paquetes) {
            if (p.getPeso_kg() > mejor) {
                mejor = p.getPeso_kg();
                paquete = p;
            }
        }
        return paquete;
    }

    private Camion buscarCamionFactible(Paquete p, List<Camion> camiones) {
        Camion mejor = null;
        double menorSobrante = Double.MAX_VALUE;

        for (Camion c : camiones) {
            if (c.getCapacidadActual() >= p.getPeso_kg()) {
                if (p.contiene_alimento() && !c.estaRefrigerado()) {
                    continue;
                }
                double sobrante = c.getCapacidadKg() - p.getPeso_kg();
                if (sobrante < menorSobrante) {
                    menorSobrante = sobrante;
                    mejor = c;
                }

            }
        }

        return mejor;
    }
}
