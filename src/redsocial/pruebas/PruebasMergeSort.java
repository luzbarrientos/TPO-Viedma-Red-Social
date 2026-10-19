
package redsocial.pruebas;

import redsocial.algoritmos.MergeSortPublicaciones;
import redsocial.modelo.Publicacion;
import java.time.LocalDateTime;

public class PruebasMergeSort {

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

    private static boolean idsIguales(Publicacion[] datos, int... ids) {
        if (datos.length != ids.length) return false;

        for (int i = 0; i < ids.length; i++) {
            if (datos[i].getId() != ids[i]) return false;
        }
        return true;
    }

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("\n- PRUEBAS MERGE SORT -");

        Publicacion[] publicaciones = {
                new Publicacion(1, "Paz",
                        LocalDateTime.of(2026, 10, 10, 10, 0), 45, 15),
                new Publicacion(2, "Luis",
                        LocalDateTime.of(2026, 10, 12, 9, 0), 20, 5),
                new Publicacion(4, "Sol",
                        LocalDateTime.of(2026, 10, 11, 15, 0), 15, 10),
                new Publicacion(5, "Ana",
                        LocalDateTime.of(2026, 10, 9, 12, 0), 10, 5)
        };

        Publicacion[] porFecha = MergeSortPublicaciones.ordenar(
                publicaciones, MergeSortPublicaciones.Criterio.RECIENTE);

        verificar("CP01 - Ordenamiento por fecha",
                idsIguales(porFecha, 2, 4, 1, 5));

        Publicacion[] porRelevancia = MergeSortPublicaciones.ordenar(
                publicaciones, MergeSortPublicaciones.Criterio.RELEVANTE);

        verificar("CP02 - Ordenamiento por relevancia",
                idsIguales(porRelevancia, 1, 2, 4, 5));

        LocalDateTime mismaFecha =
                LocalDateTime.of(2026, 10, 10, 10, 0);

        Publicacion[] empate = {
                new Publicacion(8, "Juan", mismaFecha, 5, 5),
                new Publicacion(3, "Pedro", mismaFecha, 8, 2)
        };

        Publicacion[] desempate = MergeSortPublicaciones.ordenar(
                empate, MergeSortPublicaciones.Criterio.RELEVANTE);

        verificar("CP03 - Empate por relevancia y fecha",
                idsIguales(desempate, 3, 8));

        Publicacion[] vacio = MergeSortPublicaciones.ordenar(
                new Publicacion[0],
                MergeSortPublicaciones.Criterio.RECIENTE);

        verificar("CP04 - Arreglo vacio", vacio.length == 0);

        Publicacion[] uno = MergeSortPublicaciones.ordenar(
                new Publicacion[]{publicaciones[0]},
                MergeSortPublicaciones.Criterio.RECIENTE);

        verificar("CP05 - Una sola publicacion",
                idsIguales(uno, 1));

        verificar("CP06 - No modifica el arreglo original",
                idsIguales(publicaciones, 1, 2, 4, 5));

        System.out.println("Correctas: " + correctas
                + " | Fallidas: " + fallidas);

        return fallidas == 0;
    }

    public static void main(String[] args) {
        ejecutar();
    }
}