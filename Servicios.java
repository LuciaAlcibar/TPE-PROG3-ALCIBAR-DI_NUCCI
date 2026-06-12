
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.reverseOrder;
import java.util.Comparator;
import java.util.List;

public class Servicios {

    private GestionArchivos gestionArchivos;

    //Complejidad: O(C + P²)
    public Servicios(String pathCamiones, String pathPaquetes) {
        this.gestionArchivos = new GestionArchivos();
        this.gestionArchivos.cargarInformacion(pathCamiones, pathPaquetes);
    }

    //SERVICIO 1. Complejidad : O(1)
    public Paquete servicio1(String codigo_paquete) {
        return gestionArchivos.getPaquetePorCodigo(codigo_paquete);
    }

    //SERVICIO 2. Complejidad : O(P)
    public List<Paquete> servicio2(boolean contieneAlimentos) {

        if (contieneAlimentos) {
            return gestionArchivos.getPaquetesConAlimento();
        }
        return gestionArchivos.getPaquetesSinAlimento();
    }

    //SERVICIO 3. Complejidad : O(P)
    public List<Paquete> servicio3(int urgenciaMin, int urgenciaMax) {
        return gestionArchivos.getArbolUrgencias().getPaquetesEnRango(urgenciaMin, urgenciaMax);
    }

    //GREEDY
    /*
     Estrategia Greedy: buscar el paquete de mayor peso. 
     En cada iteración se selecciona el paquete de mayor peso aún no asignado, ya que dejar sin asignar paquetes pesados incrementa más el peso total no transportado. Luego, se busca un camión factible que respete las restricciones de capacidad y refrigeración, eligiendo aquel que deje el menor espacio libre disponible. De esta manera se intenta aprovechar mejor la capacidad de los camiones y minimizar el peso total de los paquetes no asignados.
     Complejidad: O(P log P + P * C)
     */
    public void greedy() {
        List<Paquete> paquetes = gestionArchivos.getPaquetes();
        List<Camion> camiones = gestionArchivos.getCamiones();

        for (Camion c : camiones) {
            c.vaciar();
        }

        double pesoNoAsignado = 0;
        int candidatosConsiderados = 0;

        Collections.sort(paquetes, Comparator.comparingDouble(Paquete::getPeso_kg).reversed());

        for (Paquete p : paquetes) {

            candidatosConsiderados++;

            Camion mejorCamion = buscarCamionFactible(p, camiones);

            if (mejorCamion != null) {

                mejorCamion.agregarPaquete(p);

            } else {

                pesoNoAsignado += p.getPeso_kg();
            }
        }

        mostrarResultado(
                "GREEDY",
                camiones,
                pesoNoAsignado,
                candidatosConsiderados,
                "Candidatos considerados");
    }

    private Camion buscarCamionFactible(Paquete p, List<Camion> camiones) {
        Camion mejor = null;
        double menorSobrante = Double.MAX_VALUE;

        for (Camion c : camiones) {
            if (c.getCapacidadActual() >= p.getPeso_kg()) {
                if (p.contiene_alimento() && !c.estaRefrigerado()) {
                    continue;
                }
                double sobrante = c.getCapacidadActual() - p.getPeso_kg();
                if (sobrante < menorSobrante) {
                    menorSobrante = sobrante;
                    mejor = c;
                }

            }
        }

        return mejor;
    }
    //BACKTRACKING
    /*
    Estrategia Backtracking: se exploran todas las posibles asignaciones de paquetes a camiones, considerando para cada paquete la opción de ubicarlo en cada camión factible o dejarlo sin asignar. Un camión es factible si tiene capacidad suficiente y, en caso de que el paquete contenga alimentos, si está refrigerado. Durante la exploración se guarda la mejor solución encontrada, es decir, aquella que minimiza el peso total no asignado. Además, se aplican podas para descartar ramas que ya no pueden mejorar la mejor solución actual, reduciendo la cantidad de estados generados. 
    Complejidad: O(P * (C + 1)^P).
    */
    public void asignarPaquetesBacktracking() {
        List<Paquete> paquetes = gestionArchivos.getPaquetes();
        List<Camion> camiones = gestionArchivos.getCamiones();

        for (Camion c : camiones) {
            c.vaciar();
        }

        double[] mejorPesoNoAsignado = {Double.MAX_VALUE};
        int[] estadosConsiderados = {0};
        int[] asignacionActual = new int[paquetes.size()];
        int[] mejorAsignacion = new int[paquetes.size()];

        Arrays.fill(asignacionActual, -1);
        Arrays.fill(mejorAsignacion, -1);

        Collections.sort(paquetes, reverseOrder());

        backtracking(paquetes, camiones, 0, 0.0, mejorPesoNoAsignado, asignacionActual, mejorAsignacion, estadosConsiderados);
        reconstruirMejorAsignacion(paquetes, camiones, mejorAsignacion);

        mostrarResultado("BACKTRACKING", camiones, mejorPesoNoAsignado[0], estadosConsiderados[0], "Estados generados");
    }

    private void backtracking(List<Paquete> paquetes, List<Camion> camiones, int pos, double pesoNoAsignadoActual, double[] mejorPesoNoAsignado, int[] asignacionActual, int[] mejorAsignacion, int[] estadosConsiderados) {
        estadosConsiderados[0]++;

        if (pesoNoAsignadoActual >= mejorPesoNoAsignado[0]) {
            return;
        }

        double pesoRestante = 0;
        for (int i = pos; i < paquetes.size(); i++) {
            pesoRestante += paquetes.get(i).getPeso_kg();
        }

        double capacidadLibre = capacidadDisponibleTotal(camiones);

        double minimoPesoNoAsignadoPosible
                = pesoNoAsignadoActual
                + Math.max(0, pesoRestante - capacidadLibre);

        if (minimoPesoNoAsignadoPosible >= mejorPesoNoAsignado[0]) {
            return;
        }

        if (pos == paquetes.size()) {
            if (pesoNoAsignadoActual < mejorPesoNoAsignado[0]) {
                mejorPesoNoAsignado[0] = pesoNoAsignadoActual;
                System.arraycopy(asignacionActual, 0, mejorAsignacion, 0, asignacionActual.length);
            }
            return;
        }

        Paquete p = paquetes.get(pos);

        for (int i = 0; i < camiones.size(); i++) {
            Camion c = camiones.get(i);
            if (p.contiene_alimento() && !c.estaRefrigerado()) {
                continue;
            }
            if (c.getCapacidadActual() >= p.getPeso_kg()) {
                asignacionActual[pos] = i;
                c.agregarPaquete(p); // Hacemos (Asignar)
                backtracking(paquetes, camiones, pos + 1, pesoNoAsignadoActual, mejorPesoNoAsignado, asignacionActual, mejorAsignacion, estadosConsiderados);
                c.eliminarPaquete(p); // Deshacemos (Backtrack)
                asignacionActual[pos] = -1;
            }
        }

        asignacionActual[pos] = -1;
        backtracking(paquetes, camiones, pos + 1, pesoNoAsignadoActual + p.getPeso_kg(), mejorPesoNoAsignado, asignacionActual, mejorAsignacion, estadosConsiderados);
    }

    private double capacidadDisponibleTotal(List<Camion> camiones) {
        double total = 0;

        for (Camion c : camiones) {
            total += c.getCapacidadActual();
        }

        return total;
    }

    private void reconstruirMejorAsignacion(List<Paquete> paquetes, List<Camion> camiones, int[] mejorAsignacion) {
        for (Camion c : camiones) {
            c.vaciar();
        }

        for (int i = 0; i < paquetes.size(); i++) {
            int indiceCamion = mejorAsignacion[i];

            if (indiceCamion != -1) {
                camiones.get(indiceCamion).agregarPaquete(paquetes.get(i));
            }
        }
    }

    private void mostrarResultado(
            String algoritmo,
            List<Camion> camiones,
            double pesoNoAsignado,
            int metrica,
            String nombreMetrica) {

        System.out.println("----- " + algoritmo + " -----");
        System.out.println("Solución obtenida:");

        for (Camion c : camiones) {
            System.out.println("Camión " + c.getIdCamion() + " (" + c.getPatente() + "):");
            if (c.getPaquetes().isEmpty()) {
                System.out.println("  Sin paquetes asignados.");
            } else {
                for (Paquete p : c.getPaquetes()) {
                    System.out.println("  - " + p);
                }
            }
        }

        System.out.println(
                "Peso no asignado: " + pesoNoAsignado + " kg");

        System.out.println(
                "Métrica ( " + nombreMetrica + " ): " + metrica);
    }

}
