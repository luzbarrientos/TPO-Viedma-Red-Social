
package redsocial;

import redsocial.algoritmos.*;
import redsocial.datos.DatosEjemplo;
import redsocial.modelo.*;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n- RED SOCIAL UNIVERSITARIA -");
            System.out.println("1. Ordenar publicaciones por fecha");
            System.out.println("2. Ordenar publicaciones por relevancia");
            System.out.println("3. Buscar cadena de difusion");
            System.out.println("4. Seleccionar iniciativas");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> mostrarPublicaciones(
                        MergeSortPublicaciones.Criterio.RECIENTE);
                case 2 -> mostrarPublicaciones(
                        MergeSortPublicaciones.Criterio.RELEVANTE);
                case 3 -> buscarDifusion();
                case 4 -> seleccionarIniciativas();
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarPublicaciones(
            MergeSortPublicaciones.Criterio criterio) {

        Publicacion[] ordenadas = MergeSortPublicaciones.ordenar(
                DatosEjemplo.publicaciones(), criterio);

        System.out.println("\nPUBLICACIONES ORDENADAS");

        for (Publicacion publicacion : ordenadas) {
            System.out.println(publicacion);
        }
    }

    private static void buscarDifusion() {
        Map<String, List<Vinculo>> red = DatosEjemplo.red();

        System.out.println("Usuarios disponibles: " + red.keySet());
        System.out.print("Origen: ");
        String origen = scanner.nextLine().trim();

        System.out.print("Destino: ");
        String destino = scanner.nextLine().trim();

        try {
            Dijkstra.Resultado resultado =
                    Dijkstra.buscar(red, origen, destino);

            System.out.println("\nDECISIONES DEL ALGORITMO");
            for (String decision : resultado.decisiones) {
                System.out.println(decision);
            }

            if (resultado.alcanzable()) {
                System.out.println("Camino: "
                        + String.join(" -> ", resultado.camino));
                System.out.println("Tiempo: "
                        + resultado.minutos + " minutos");
            } else {
                System.out.println("No existe cadena de difusion.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void seleccionarIniciativas() {
        System.out.print("Ingrese el presupuesto: ");

        try {
            int presupuesto = Integer.parseInt(scanner.nextLine());

            Mochila.Resultado resultado = Mochila.resolver(
                    DatosEjemplo.iniciativas(), presupuesto);

            System.out.println("\nINICIATIVAS SELECCIONADAS");
            for (Iniciativa iniciativa : resultado.seleccionadas) {
                System.out.println(iniciativa);
            }

            System.out.println("Costo total: " + resultado.costo);
            System.out.println("Impacto total: " + resultado.impacto);
            System.out.println("Presupuesto restante: "
                    + resultado.restante);

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero entero.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
