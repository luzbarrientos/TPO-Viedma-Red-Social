package redsocial.algoritmos;

import redsocial.modelo.Iniciativa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mochila {

    public static class Resultado {

        public final List<Iniciativa> seleccionadas;
        public final int costo;
        public final int impacto;
        public final int restante;

        public Resultado(List<Iniciativa> seleccionadas,
                         int costo,
                         int impacto,
                         int restante) {

            this.seleccionadas = seleccionadas;
            this.costo = costo;
            this.impacto = impacto;
            this.restante = restante;
        }
    }

    public static Resultado resolver(List<Iniciativa> iniciativas,
                                     int presupuesto) {

        if (presupuesto < 0) {
            throw new IllegalArgumentException("Presupuesto negativo");
        }

        int n = iniciativas.size();

        int[][] dp = new int[n + 1][presupuesto + 1];

        for (int i = 1; i <= n; i++) {

            Iniciativa actual = iniciativas.get(i - 1);

            for (int p = 0; p <= presupuesto; p++) {

                dp[i][p] = dp[i - 1][p];

                if (actual.getCosto() <= p) {

                    int incluir = actual.getImpacto()
                            + dp[i - 1][p - actual.getCosto()];

                    dp[i][p] = Math.max(dp[i][p], incluir);
                }
            }
        }

        List<Iniciativa> seleccionadas = new ArrayList<>();

        int disponible = presupuesto;
        int costoTotal = 0;

        for (int i = n; i >= 1; i--) {

            Iniciativa actual = iniciativas.get(i - 1);

            if (actual.getCosto() <= disponible
                    && dp[i][disponible] > dp[i - 1][disponible]) {

                seleccionadas.add(actual);

                disponible -= actual.getCosto();
                costoTotal += actual.getCosto();
            }
        }

        Collections.reverse(seleccionadas);

        return new Resultado(
                seleccionadas,
                costoTotal,
                dp[n][presupuesto],
                presupuesto - costoTotal
        );
    }
}
