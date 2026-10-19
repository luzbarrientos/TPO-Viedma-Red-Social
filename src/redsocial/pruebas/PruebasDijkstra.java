
package redsocial.pruebas;

import redsocial.algoritmos.Dijkstra;
import redsocial.datos.DatosEjemplo;
import redsocial.modelo.Vinculo;

import java.util.*;

public class PruebasDijkstra {

    private static int correctas = 0;
    private static int fallidas = 0;

    private static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("[OK] " + nombre);
        } else {
            fallidas++;
            System.out.println("[ERROR] " + nombre);
        }
    }

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("\n- PRUEBAS DIJKSTRA -");

        Map<String, List<Vinculo>> red = DatosEjemplo.red();

        Dijkstra.Resultado r1 = Dijkstra.buscar(
                red, "Ana", "Eva");

        verificar("CP13 - Camino minimo Ana a Eva",
                r1.alcanzable()
                        && r1.minutos == 11
                        && r1.camino.equals(Arrays.asList(
                        "Ana", "Caro", "Beto", "Dani", "Eva")));

        Dijkstra.Resultado r2 = Dijkstra.buscar(
                red, "Eva", "Ana");

        verificar("CP14 - Destino inalcanzable",
                !r2.alcanzable()
                        && r2.camino.isEmpty());

        Dijkstra.Resultado r3 = Dijkstra.buscar(
                red, "Ana", "Ana");

        verificar("CP15 - Origen igual a destino",
                r3.alcanzable()
                        && r3.minutos == 0
                        && r3.camino.equals(Collections.singletonList("Ana")));

        boolean usuarioInvalido = false;

        try {
            Dijkstra.buscar(red, "UsuarioInexistente", "Eva");
        } catch (IllegalArgumentException e) {
            usuarioInvalido = true;
        }

        verificar("CP16 - Usuario inexistente",
                usuarioInvalido);

        Dijkstra.Resultado r4 = Dijkstra.buscar(
                red, "Ana", "Beto");

        verificar("CP17 - Camino indirecto mas rapido",
                r4.minutos == 3
                        && r4.camino.equals(Arrays.asList(
                        "Ana", "Caro", "Beto")));

        verificar("CP18 - Registra decisiones",
                !r1.decisiones.isEmpty());

        System.out.println("Correctas: " + correctas
                + " | Fallidas: " + fallidas);

        return fallidas == 0;
    }

    public static void main(String[] args) {
        ejecutar();
    }
}
