package redsocial.algoritmos;

import redsocial.modelo.Vinculo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadenasDifusion {

    public static class Cadena {

        public final List<String> usuarios;
        public final int tiempoTotal;

        public Cadena(List<String> usuarios, int tiempoTotal) {
            this.usuarios = new ArrayList<>(usuarios);
            this.tiempoTotal = tiempoTotal;
        }
    }

    public static class Resultado {

        public final List<Cadena> cadenas;
        public final Cadena cadenaMasRapida;

        public Resultado(List<Cadena> cadenas, Cadena cadenaMasRapida) {
            this.cadenas = new ArrayList<>(cadenas);
            this.cadenaMasRapida = cadenaMasRapida;
        }
    }

    private static void validarRed(Map<String, List<Vinculo>> red) {

        if (red == null) {
            throw new IllegalArgumentException(
                    "La red no puede ser null."
            );
        }

        for (Map.Entry<String, List<Vinculo>> entrada : red.entrySet()) {

            String usuario = entrada.getKey();
            List<Vinculo> vinculos = entrada.getValue();

            if (usuario == null || usuario.isBlank()) {
                throw new IllegalArgumentException(
                        "La red contiene un usuario invalido."
                );
            }

            if (vinculos == null) {
                throw new IllegalArgumentException(
                        "La lista de vinculos de " + usuario + " es null."
                );
            }

            for (Vinculo vinculo : vinculos) {

                if (vinculo == null) {
                    throw new IllegalArgumentException(
                            "La red contiene un vinculo null."
                    );
                }

                if (!red.containsKey(vinculo.getDestino())) {
                    throw new IllegalArgumentException(
                            "El destino " + vinculo.getDestino()
                                    + " no existe en la red."
                    );
                }

                if (vinculo.getMinutos() <= 0) {
                    throw new IllegalArgumentException(
                            "Los tiempos deben ser positivos."
                    );
                }
            }
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

        validarRed(red);

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

        if (actual.equals(destino)) {
            cadenasValidas.add(
                    new Cadena(caminoActual, tiempoActual)
            );
            return;
        }

        int intermediariosActuales = caminoActual.size() - 1;

        List<Vinculo> vecinos = red.get(actual);

        if (vecinos == null) {
            return;
        }

        for (Vinculo vinculo : vecinos) {

            String siguiente = vinculo.getDestino();
            int minutos = vinculo.getMinutos();

            if (visitados.contains(siguiente)) {
                continue;
            }

            // No incluir usuarios excluidos.
            if (usuariosExcluidos.contains(siguiente)) {
                continue;
            }

            if (!red.containsKey(siguiente)) {
                continue;
            }

            if (minutos > tiempoMaximo - tiempoActual) {
                continue;
            }

            if (!siguiente.equals(destino)
                    && intermediariosActuales >= maximoIntermediarios) {
                continue;
            }

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

            visitados.remove(siguiente);
            caminoActual.remove(caminoActual.size() - 1);
        }
    }
}
