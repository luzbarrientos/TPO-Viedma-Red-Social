package redsocial.pruebas;

import redsocial.algoritmos.AsignacionTutores;
import redsocial.datos.DatosTutores;

import java.util.Arrays;

public class PruebasAsignacionTutores {

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

        System.out.println("\n- PRUEBAS ASIGNACION DE TUTORES -");

        AsignacionTutores algoritmo = new AsignacionTutores();

        int[] resultado = algoritmo.resolver(DatosTutores.costos());

        verificar("CP44 - Asignacion optima",
                Arrays.equals(resultado, new int[]{0, 1, 2}));

        verificar("CP45 - Costo minimo correcto",
                algoritmo.getMejorCosto() == 9);

        int[][] costos2 = {
                {-1, 2},
                {3, -1}
        };

        resultado = algoritmo.resolver(costos2);

        verificar("CP46 - Respeta asignaciones prohibidas",
                Arrays.equals(resultado, new int[]{1, 0})
                        && algoritmo.getMejorCosto() == 5);

        int[][] costos3 = {
                {1, 2},
                {-1, -1}
        };

        verificar("CP47 - No existe asignacion valida",
                algoritmo.resolver(costos3) == null);

        int[][] costos4 = {
                {10, 1},
                {2, 10}
        };

        resultado = algoritmo.resolver(costos4);

        verificar("CP48 - Encuentra una solucion mejor",
                Arrays.equals(resultado, new int[]{1, 0})
                        && algoritmo.getMejorCosto() == 3);

        resultado = algoritmo.resolver(new int[][]{{5}});

        verificar("CP49 - Un solo tutor y un solo grupo",
                Arrays.equals(resultado, new int[]{0})
                        && algoritmo.getMejorCosto() == 5);

        System.out.println("Correctas: " + correctas
                + " | Fallidas: " + fallidas);

        return fallidas == 0;
    }

    public static void main(String[] args) {
        ejecutar();
    }
}
