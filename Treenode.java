import java.util.LinkedList;
import java.util.List;

public class Treenode {

    private int urgencia;
    private List<Paquete> paquetes;

    private Treenode left;
    private Treenode right;

    public Treenode(int urgencia) {
        this.urgencia = urgencia;
        this.paquetes = new LinkedList<>();
        this.left = null;
        this.right = null;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public Treenode getLeft() {
        return left;
    }

    public void setLeft(Treenode left) {
        this.left = left;
    }

    public Treenode getRight() {
        return right;
    }

    public void setRight(Treenode right) {
        this.right = right;
    }

    public void addPaquete(Paquete paquete) {
        this.paquetes.add(paquete);
    }
}