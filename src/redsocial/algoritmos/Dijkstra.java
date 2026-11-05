package redsocial.algoritmos;

import redsocial.modelo.Vinculo;
import java.util.*;

public class Dijkstra {

    public static class Resultado {
        public final List<String> camino;
        public final long minutos;
        public final List<String> decisiones;

        public Resultado(List<String> camino, long minutos,
                         List<String> decisiones) {
            this.camino = camino;
            this.minutos = minutos;
            this.decisiones = decisiones;
        }

        public boolean alcanzable() {
            return !camino.isEmpty();
        }
    }

    private static class Estado {
        String usuario;
        long distancia;

        Estado(String usuario, long distancia) {
            this.usuario = usuario;
            this.distancia = distancia;
        }
    }

    // Verifica la estructura completa de la red.
    private static void validarRed(Map<String, List<Vinculo>> red) {

        if (red == null) {
            throw new IllegalArgumentException("La red no puede ser null");
        }

        for (Map.Entry<String, List<Vinculo>> entrada : red.entrySet()) {

            String usuario = entrada.getKey();
            List<Vinculo> vinculos = entrada.getValue();

            if (usuario == null || usuario.isBlank()) {
                throw new IllegalArgumentException(
                        "La red contiene un usuario invalido");
            }

            if (vinculos == null) {
                throw new IllegalArgumentException(
                        "La lista de vinculos de " + usuario + " es null");
            }

            for (Vinculo vinculo : vinculos) {

                if (vinculo == null) {
                    throw new IllegalArgumentException(
                            "La red contiene un vinculo null");
                }

                if (!red.containsKey(vinculo.getDestino())) {
                    throw new IllegalArgumentException(
                            "El destino " + vinculo.getDestino()
                                    + " no existe en la red");
                }

                if (vinculo.getMinutos() <= 0) {
                    throw new IllegalArgumentException(
                            "Los tiempos deben ser positivos");
                }
            }
        }
    }

    public static Resultado buscar(
            Map<String, List<Vinculo>> red,
            String origen, String destino) {

        validarRed(red);

        if (origen == null || destino == null
                || origen.isBlank() || destino.isBlank()) {
            throw new IllegalArgumentException(
                    "El origen y destino deben ser validos");
        }

        if (!red.containsKey(origen) || !red.containsKey(destino)) {
            throw new IllegalArgumentException(
                    "El origen o destino no existe");
        }

        Map<String, Long> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        List<String> decisiones = new ArrayList<>();

        for (String usuario : red.keySet()) {
            distancias.put(usuario, Long.MAX_VALUE);
        }

        distancias.put(origen, 0L);

        PriorityQueue<Estado> cola = new PriorityQueue<>(
                Comparator.comparingLong(e -> e.distancia));

        cola.add(new Estado(origen, 0));

        while (!cola.isEmpty()) {
            Estado actual = cola.poll();

            if (!visitados.add(actual.usuario)) {
                continue;
            }

            decisiones.add("Se visita " + actual.usuario
                    + " con tiempo " + actual.distancia);

            if (actual.usuario.equals(destino)) {
                break;
            }

            for (Vinculo vinculo : red.get(actual.usuario)) {

                String vecino = vinculo.getDestino();
                long minutos = vinculo.getMinutos();

                // Evita desbordamientos al sumar distancias.
                if (actual.distancia > Long.MAX_VALUE - minutos) {
                    continue;
                }

                long nuevoTiempo = actual.distancia + minutos;

                if (nuevoTiempo < distancias.get(vecino)) {
                    distancias.put(vecino, nuevoTiempo);
                    anteriores.put(vecino, actual.usuario);
                    cola.add(new Estado(vecino, nuevoTiempo));

                    decisiones.add("Se mejora " + vecino
                            + " a " + nuevoTiempo + " minutos");
                }
            }
        }

        if (distancias.get(destino) == Long.MAX_VALUE) {
            return new Resultado(
                    Collections.emptyList(), -1, decisiones);
        }

        LinkedList<String> camino = new LinkedList<>();

        for (String usuario = destino; usuario != null;
             usuario = anteriores.get(usuario)) {
            camino.addFirst(usuario);
        }

        return new Resultado(
                camino, distancias.get(destino), decisiones);
    }
}
