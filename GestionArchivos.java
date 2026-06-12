
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GestionArchivos {

    private List<Camion> camiones;
    private List<Paquete> paquetes; 

    private HashMap<String, Camion> camionesPorPatente;
    private HashMap<String, Paquete> paquetesPorCodigo;

    private ArbolUrgencias arbolUrgencias;

    private LinkedList<Paquete> paquetesConAlimento;
    private LinkedList<Paquete> paquetesSinAlimento;

    public GestionArchivos() {
        this.paquetes = new ArrayList<>(); 
        this.paquetesPorCodigo = new HashMap<>();

        this.camiones = new ArrayList<>();
        this.camionesPorPatente = new HashMap<>(); 

        this.arbolUrgencias = new ArbolUrgencias();

        this.paquetesConAlimento = new LinkedList<>();
        this.paquetesSinAlimento = new LinkedList<>();

    }

    public void cargarInformacion(String archivoCamiones, String archivoPaquetes) {

        cargarCamiones(archivoCamiones);
        cargarPaquetes(archivoPaquetes);
    }

    private void cargarCamiones(String archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            br.readLine(); 

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                int id = Integer.parseInt(datos[0]);
                String patente = datos[1];
                boolean refrigerado = datos[2].equals("1");
                double capacidadKg = Double.parseDouble(datos[3]);

                Camion camion = new Camion(
                        id,
                        patente,
                        refrigerado,
                        capacidadKg
                );

                camiones.add(camion);
                camionesPorPatente.put(patente, camion);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPaquetes(String archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            br.readLine(); 

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

               
                int id = Integer.parseInt(datos[0]);
                String codigo = datos[1];
                double peso = Double.parseDouble(datos[2]);
                boolean contieneAlimento = datos[3].equals("1");
                int urgencia = Integer.parseInt(datos[4]);

                Paquete paquete = new Paquete(
                        id,
                        codigo,
                        contieneAlimento,
                        urgencia,
                        peso
                );

                paquetes.add(paquete);
                paquetesPorCodigo.put(codigo, paquete);
                arbolUrgencias.add(paquete);

                if (contieneAlimento) {
                    paquetesConAlimento.add(paquete);
                } else {
                    paquetesSinAlimento.add(paquete);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Camion> getCamiones() {
        return new ArrayList<>(camiones);
    }

    public List<Paquete> getPaquetes() {
        return new ArrayList<>(paquetes);
    }

    public HashMap<String, Paquete> getPaquetesPorCodigo() {
        return new HashMap<>(paquetesPorCodigo);
    }

    public Paquete getPaquetePorCodigo(String codigo) {
        return paquetesPorCodigo.get(codigo);
    }

    public HashMap<String, Camion> getCamionesPorPatente() {
        return new HashMap<>(camionesPorPatente);
    }

    public ArbolUrgencias getArbolUrgencias() {
        return arbolUrgencias;
    }

    public List<Paquete> getPaquetesConAlimento() {
        return new LinkedList<>(paquetesConAlimento);
    }

    public List<Paquete> getPaquetesSinAlimento() {
        return new LinkedList<>(paquetesSinAlimento);
    }
}
