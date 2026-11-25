package redsocial.algoritmos;

public class AsignacionTutores {

    private long mejorCosto;
    private int[] mejorAsignacion;

    public int[] resolver(int[][] costos) {

        int n = costos.length;

        if (n == 0 || n > 10) {
            throw new IllegalArgumentException("Cantidad invalida");
        }

        for (int[] fila : costos) {
            if (fila.length != n) {
                throw new IllegalArgumentException("Matriz no cuadrada");
            }

            for (int costo : fila) {
                if (costo < -1) {
                    throw new IllegalArgumentException("Costo invalido");
                }
            }
        }

        mejorCosto = Long.MAX_VALUE;
        mejorAsignacion = null;

        boolean[] usados = new boolean[n];
        int[] actual = new int[n];

        backtracking(costos, 0, 0, usados, actual);

        return mejorAsignacion;
    }

    private void backtracking(int[][] costos, int grupo,
                              long costoActual, boolean[] usados,
                              int[] actual) {

        // Poda por costo.
        if (costoActual >= mejorCosto) {
            return;
        }

        // Caso base.
        if (grupo == costos.length) {
            mejorCosto = costoActual;
            mejorAsignacion = actual.clone();
            return;
        }

        for (int tutor = 0; tutor < costos.length; tutor++) {

            int costo = costos[tutor][grupo];

            // Verificar restricciones.
            if (!usados[tutor] && costo != -1) {

                // Elegir.
                usados[tutor] = true;
                actual[grupo] = tutor;

                // Explorar.
                backtracking(costos, grupo + 1,
                        costoActual + costo, usados, actual);

                // Deshacer.
                usados[tutor] = false;
                actual[grupo] = -1;
            }
        }
    }

    public long getMejorCosto() {
        return mejorCosto;
    }
}