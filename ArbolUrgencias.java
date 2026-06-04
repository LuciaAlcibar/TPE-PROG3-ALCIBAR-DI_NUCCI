import java.util.LinkedList;
import java.util.List;

public class ArbolUrgencias {

    private Treenode root;

    public ArbolUrgencias() {
        this.root = null;
    }

    public void add(Paquete paquete) {
        root = add(root, paquete);
    }

    private Treenode add(Treenode nodo, Paquete paquete) {

        int urgencia = paquete.getNivel_urgencia();

        if (nodo == null) {
            Treenode nuevo = new Treenode(urgencia);
            nuevo.addPaquete(paquete);
            return nuevo;
        }

        if (urgencia < nodo.getUrgencia()) {
            nodo.setLeft(add(nodo.getLeft(), paquete));
        }
        else if (urgencia > nodo.getUrgencia()) {
            nodo.setRight(add(nodo.getRight(), paquete));
        }
        else {
            nodo.addPaquete(paquete);
        }

        return nodo;
    }

    public List<Paquete> getPaquetesEnRango(int min, int max) {
        List<Paquete> resultado = new LinkedList<>();
        getPaquetesEnRango(root, min, max, resultado);
        return resultado;
    }

    private void getPaquetesEnRango(Treenode nodo, int min, int max, List<Paquete> resultado) {
        if (nodo == null) {
            return;
        }

        if (nodo.getUrgencia() > min) {
            getPaquetesEnRango(
                    nodo.getLeft(),
                    min,
                    max,
                    resultado);
        }

        if (nodo.getUrgencia() >= min && nodo.getUrgencia() <= max) {

            resultado.addAll(nodo.getPaquetes());
        }

        if (nodo.getUrgencia() < max) {
            getPaquetesEnRango(
                    nodo.getRight(),
                    min,
                    max,
                    resultado);
        }
    }
}