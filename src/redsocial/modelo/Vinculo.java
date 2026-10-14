
package redsocial.modelo;

public class Vinculo {
    private final String destino;
    private final int minutos;

    public Vinculo(String destino, int minutos) {
        if (destino == null || destino.isBlank() || minutos <= 0) {
            throw new IllegalArgumentException("Vinculo invalido");
        }

        this.destino = destino;
        this.minutos = minutos;
    }

    public String getDestino() {
        return destino;
    }

    public int getMinutos() {
        return minutos;
    }
}
