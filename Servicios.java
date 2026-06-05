
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

    public List<Paquete> servicio2(boolean contieneAlimentos){

        if(contieneAlimentos){
            return gestionArchivos.getPaquetesConAlimento();
        }
        return gestionArchivos.getPaquetesSinAlimento();
    }

    public List<Paquete> servicio3(int urgenciaMin, int urgenciaMax) { 
        return gestionArchivos.getArbolUrgencias().getPaquetesEnRango(urgenciaMin, urgenciaMax);
    }
}
