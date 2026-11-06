
package redsocial.algoritmos;

import redsocial.modelo.Actividad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeleccionActividades {

    public static class Resultado {

        public final List<Actividad> seleccionadas;
        public final int interesTotal;

        public Resultado(List<Actividad> seleccionadas, int interesTotal) {
            this.seleccionadas = new ArrayList<>(seleccionadas);
            this.interesTotal = interesTotal;
        }
    }

    public static Resultado resolver(List<Actividad> actividades) {

        if (actividades == null) {
            throw new IllegalArgumentException(
                    "La lista de actividades no puede ser null."
            );
        }

        List<Actividad> ordenadas = new ArrayList<>(actividades);

        ordenadas.sort(
                Comparator.comparingInt(Actividad::getFin)
                        .thenComparingInt(Actividad::getInicio)
                        .thenComparingInt(Actividad::getId)
        );

        int n = ordenadas.size();

        if (n == 0) {
            return new Resultado(new ArrayList<>(), 0);
        }

        // p[i] guarda cuantas actividades anteriores podemos
        // considerar si elegimos la actividad i.
        int[] p = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = buscarCompatible(ordenadas, i);
        }

        int[] dp = new int[n + 1];

        dp[0] = 0;

        for (int i = 1; i <= n; i++) {

            Actividad actual = ordenadas.get(i - 1);

            int incluir = actual.getInteres() + dp[p[i - 1]];
            int excluir = dp[i - 1];

            dp[i] = Math.max(incluir, excluir);
        }

        List<Actividad> seleccionadas = new ArrayList<>();

        int i = n;

        while (i > 0) {

            Actividad actual = ordenadas.get(i - 1);

            int incluir = actual.getInteres() + dp[p[i - 1]];
            int excluir = dp[i - 1];

            if (incluir > excluir) {
                seleccionadas.add(0, actual);
                i = p[i - 1];
            } else {
                i--;
            }
        }

        return new Resultado(seleccionadas, dp[n]);
    }

    private static int buscarCompatible(
            List<Actividad> actividades,
            int indice
    ) {

        int inicioActual = actividades.get(indice).getInicio();

        int izquierda = 0;
        int derecha = indice - 1;

        int resultado = -1;

        while (izquierda <= derecha) {

            int medio = izquierda + (derecha - izquierda) / 2;

            if (actividades.get(medio).getFin() <= inicioActual) {
                resultado = medio;
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        // Convertimos el indice a cantidad de actividades
        // para utilizarlo directamente en dp.
        return resultado + 1;
    }
}
