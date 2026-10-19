
package redsocial.pruebas;

import redsocial.algoritmos.Mochila;
import redsocial.modelo.Iniciativa;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PruebasMochila {

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

    private static boolean contieneIds(
            Mochila.Resultado resultado, int... ids) {

        if (resultado.seleccionadas.size() != ids.length) {
            return false;
        }

        for (int id : ids) {
            boolean encontrado = false;

            for (Iniciativa iniciativa : resultado.seleccionadas) {
                if (iniciativa.getId() == id) {
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) return false;
        }

        return true;
    }

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("\n- PRUEBAS MOCHILA -");

        List<Iniciativa> iniciativas = Arrays.asList(
                new Iniciativa(1, 4, 7),
                new Iniciativa(2, 5, 9),
                new Iniciativa(3, 3, 6),
                new Iniciativa(4, 2, 4)
        );

        Mochila.Resultado r1 = Mochila.resolver(iniciativas, 9);

        verificar("CP07 - Presupuesto 9",
                r1.impacto == 17
                        && r1.costo == 9
                        && r1.restante == 0
                        && contieneIds(r1, 1, 3, 4));

        Mochila.Resultado r2 = Mochila.resolver(iniciativas, 2);

        verificar("CP08 - Presupuesto 2",
                r2.impacto == 4
                        && r2.costo == 2
                        && contieneIds(r2, 4));

        Mochila.Resultado r3 = Mochila.resolver(iniciativas, 0);

        verificar("CP09 - Presupuesto 0",
                r3.impacto == 0
                        && r3.costo == 0
                        && r3.seleccionadas.isEmpty());

        Mochila.Resultado r4 = Mochila.resolver(iniciativas, 14);

        verificar("CP10 - Presupuesto suficiente",
                r4.impacto == 26
                        && r4.costo == 14
                        && contieneIds(r4, 1, 2, 3, 4));

        Mochila.Resultado r5 = Mochila.resolver(
                Collections.emptyList(), 10);

        verificar("CP11 - Sin iniciativas",
                r5.impacto == 0
                        && r5.costo == 0
                        && r5.restante == 10);

        boolean rechazoNegativo = false;

        try {
            Mochila.resolver(iniciativas, -1);
        } catch (IllegalArgumentException e) {
            rechazoNegativo = true;
        }

        verificar("CP12 - Presupuesto negativo rechazado",
                rechazoNegativo);

        System.out.println("Correctas: " + correctas
                + " | Fallidas: " + fallidas);

        return fallidas == 0;
    }

    public static void main(String[] args) {
        ejecutar();
    }
}