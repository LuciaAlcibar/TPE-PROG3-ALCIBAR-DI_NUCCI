import java.util.List;

public class Main {

    public static void main(String[] args) {
        String pathCamiones = "csv/camiones.csv";
        String pathPaquetes = "csv/paquetes.csv";

        System.out.println("Inicializando servicios y cargando archivos...");
        Servicios servicios = new Servicios(pathCamiones, pathPaquetes);

        // Prueba del Servicio 1
        System.out.println("\n--- Servicio 1: Buscar paquete por código ---");
        String codigoPrueba = "P001"; 
        Paquete paqueteObtenido = servicios.servicio1(codigoPrueba);
        if (paqueteObtenido != null) {
            System.out.println("Resultado: " + paqueteObtenido);
        } else {
            System.out.println("Resultado: Paquete con código '" + codigoPrueba + "' no encontrado.");
        }

        // Prueba del Servicio 2
        System.out.println("\n--- Servicio 2: Paquetes con y sin alimentos ---");
        List<Paquete> conAlimentos = servicios.servicio2(true);
        System.out.println("Cantidad de paquetes CON alimentos: " + conAlimentos.size());
        

        List<Paquete> sinAlimentos = servicios.servicio2(false);
        System.out.println("Cantidad de paquetes SIN alimentos: " + sinAlimentos.size());

        // Prueba del Servicio 3
        System.out.println("\n--- Servicio 3: Búsqueda por rango de urgencia (arbol) ---");
        int min = 20; 
        int max = 80;
        List<Paquete> urgentes = servicios.servicio3(min, max);
        System.out.println("Paquetes encontrados en el rango [" + min + " - " + max + "]: " + urgentes.size());
        for (Paquete p : urgentes) {
            System.out.println(p);
        }

        // Prueba de Greedy
        System.out.println("\n--- Prueba de Asignación con Algoritmo Greedy ---");
        servicios.greedy();

        // Prueba de Backtracking
        System.out.println("\n--- Prueba de Asignación con Algoritmo Backtracking ---");
        servicios.asignarPaquetesBacktracking();
    }
}
