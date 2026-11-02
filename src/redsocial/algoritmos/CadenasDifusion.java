package redsocial.algoritmos;

import redsocial.modelo.Vinculo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadenasDifusion {

    // Representa una cadena valida de difusion.
    public static class Cadena {

        public final List<String> usuarios;
        public final int tiempoTotal;

        public Cadena(List<String> usuarios, int tiempoTotal) {
            this.usuarios = new ArrayList<>(usuarios);
            this.tiempoTotal = tiempoTotal;
        }
    }

    // Resultado general del algoritmo.
    public static class Resultado {

        public final List<Cadena> cadenas;
        public final Cadena cadenaMasRapida;

        public Resultado(List<Cadena> cadenas, Cadena cadenaMasRapida) {
            this.cadenas = new ArrayList<>(cadenas);
            this.cadenaMasRapida = cadenaMasRapida;
        }
    }

    public static Resultado resolver(
            Map<String, List<Vinculo>> red,
            String origen,
            String destino,
            int tiempoMaximo,
            int maximoIntermediarios,
            Set<String> usuariosExcluidos
    ) {

        if (red == null || origen == null || destino == null
                || usuariosExcluidos == null) {
            throw new IllegalArgumentException(
                    "Los datos no pueden ser null."
            );
        }

        if (origen.isBlank() || destino.isBlank()) {
            throw new IllegalArgumentException(
                    "El origen y el destino no pueden estar vacios."
            );
        }

        if (tiempoMaximo < 0 || maximoIntermediarios < 0) {
            throw new IllegalArgumentException(
                    "Los limites no pueden ser negativos."
            );
        }

        if (!red.containsKey(origen) || !red.containsKey(destino)) {
            throw new IllegalArgumentException(
                    "El origen o el destino no existen en la red."
            );
        }

        if (usuariosExcluidos.contains(origen)
                || usuariosExcluidos.contains(destino)) {
            return new Resultado(new ArrayList<>(), null);
        }

        List<Cadena> cadenasValidas = new ArrayList<>();
        List<String> caminoActual = new ArrayList<>();
        Set<String> visitados = new HashSet<>();

        caminoActual.add(origen);
        visitados.add(origen);

        backtracking(
                red,
                origen,
                destino,
                tiempoMaximo,
                maximoIntermediarios,
                usuariosExcluidos,
                0,
                caminoActual,
                visitados,
                cadenasValidas
        );

        Cadena masRapida = null;

        for (Cadena cadena : cadenasValidas) {
            if (masRapida == null
                    || cadena.tiempoTotal < masRapida.tiempoTotal) {
                masRapida = cadena;
            }
        }

        return new Resultado(cadenasValidas, masRapida);
    }

    private static void backtracking(
            Map<String, List<Vinculo>> red,
            String actual,
            String destino,
            int tiempoMaximo,
            int maximoIntermediarios,
            Set<String> usuariosExcluidos,
            int tiempoActual,
            List<String> caminoActual,
            Set<String> visitados,
            List<Cadena> cadenasValidas
    ) {

        // Si llegamos al destino, guardamos la cadena.
        if (actual.equals(destino)) {
            cadenasValidas.add(
                    new Cadena(caminoActual, tiempoActual)
            );
            return;
        }

        // No podemos agregar mas intermediarios.
        // Todavia podemos ir directamente al destino.
        int intermediariosActuales = caminoActual.size() - 1;

        List<Vinculo> vecinos = red.get(actual);

        if (vecinos == null) {
            return;
        }

        for (Vinculo vinculo : vecinos) {

            String siguiente = vinculo.getDestino();
            int minutos = vinculo.getMinutos();

            // No repetir usuarios en una misma cadena.
            if (visitados.contains(siguiente)) {
                continue;
            }

            // No utilizar usuarios excluidos.
            if (usuariosExcluidos.contains(siguiente)) {
                continue;
            }

            // Verificar que el usuario exista en la red.
            if (!red.containsKey(siguiente)) {
                continue;
            }

            // Verificar tiempo maximo.
            if (minutos > tiempoMaximo - tiempoActual) {
                continue;
            }

            // Verificar cantidad de intermediarios.
            if (!siguiente.equals(destino)
                    && intermediariosActuales >= maximoIntermediarios) {
                continue;
            }

            // Elegimos avanzar al siguiente usuario.
            caminoActual.add(siguiente);
            visitados.add(siguiente);

            backtracking(
                    red,
                    siguiente,
                    destino,
                    tiempoMaximo,
                    maximoIntermediarios,
                    usuariosExcluidos,
                    tiempoActual + minutos,
                    caminoActual,
                    visitados,
                    cadenasValidas
            );

            // Backtracking: deshacemos la decision.
            visitados.remove(siguiente);
            caminoActual.remove(caminoActual.size() - 1);
        }
    }
}
