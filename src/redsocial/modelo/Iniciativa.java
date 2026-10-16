package redsocial.modelo;

public class Iniciativa {

    private final int id;
    private final int costo;
    private final int impacto;

    public Iniciativa(int id, int costo, int impacto) {

        if (id <= 0 || costo <= 0 || impacto < 0) {
            throw new IllegalArgumentException("Iniciativa invalida");
        }

        this.id = id;
        this.costo = costo;
        this.impacto = impacto;
    }

    public int getId() {
        return id;
    }

    public int getCosto() {
        return costo;
    }

    public int getImpacto() {
        return impacto;
    }

    @Override
    public String toString() {
        return "Iniciativa " + id
                + " | Costo: " + costo
                + " | Impacto: " + impacto;
    }
}