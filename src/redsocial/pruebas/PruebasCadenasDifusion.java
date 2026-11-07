
package redsocial.pruebas;

import redsocial.algoritmos.CadenasDifusion;
import redsocial.datos.DatosDifusion;
import redsocial.modelo.Vinculo;

import java.util.*;

public class PruebasCadenasDifusion {
    private static int correctas = 0;
    private static int fallidas = 0;

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("\n | PRUEBAS B3: CADENAS ALTERNATIVAS |");

        probarCadenasEsperadas();
        probarCadenaMasRapida();
        probarTiempoMaximo();
        probarIntermediarios();
        probarExcluidos();
        probarSinSolucion();
        probarSinRepeticiones();
        probarOrigenIgualDestino();
        probarDatosInvalidos();

        System.out.println("\nCorrectas: " + correctas);
        System.out.println("Fallidas: " + fallidas);

        return fallidas == 0;
    }

    private static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("[OK] " + nombre);
        } else {
            fallidas++;
            System.out.println("[ERROR] " + nombre);
        }
    }

    private static CadenasDifusion.Resultado resolver(
            int maxTiempo,
            int maxIntermediarios,
            Set<String> excluidos) {

        return CadenasDifusion.resolver(
                DatosDifusion.crearRed(),
                "Ana",
                "Eva",
                maxTiempo,
                maxIntermediarios,
                excluidos
        );
    }

    // CP35: Verifica todas las cadenas posibles.
    private static void probarCadenasEsperadas() {
        CadenasDifusion.Resultado r = resolver(16, 3, Set.of());

        Set<String> obtenidas = new HashSet<>();

        for (CadenasDifusion.Cadena c : r.cadenas) {
            obtenidas.add(
                    String.join("-", c.usuarios) + ":" + c.tiempoTotal
            );
        }

        verificar("CP35 - Todas las cadenas esperadas",
                r.cadenas.size() == 3 &&
                        obtenidas.equals(Set.of(
                                "Ana-Beto-Dani-Eva:12",
                                "Ana-Caro-Beto-Dani-Eva:11",
                                "Ana-Caro-Dani-Eva:15"
                        ))
        );
    }

    // CP36: Comprueba que encuentre la cadena mas rapida.
    private static void probarCadenaMasRapida() {
        CadenasDifusion.Resultado r = resolver(16, 3, Set.of());

        verificar("CP36 - Cadena mas rapida",
                r.cadenaMasRapida != null &&
                        r.cadenaMasRapida.tiempoTotal == 11 &&
                        r.cadenaMasRapida.usuarios.equals(
                                List.of("Ana", "Caro", "Beto", "Dani", "Eva")
                        )
        );
    }

    // CP37: Comprueba que respete el tiempo maximo.
    private static void probarTiempoMaximo() {
        CadenasDifusion.Resultado r = resolver(12, 3, Set.of());

        verificar("CP37 - Limite de tiempo",
                r.cadenas.size() == 2 &&
                        r.cadenas.stream().allMatch(
                                c -> c.tiempoTotal <= 12
                        )
        );
    }

    // CP38: Comprueba el limite de intermediarios.
    private static void probarIntermediarios() {
        CadenasDifusion.Resultado r = resolver(16, 2, Set.of());

        verificar("CP38 - Limite de intermediarios",
                r.cadenas.size() == 2 &&
                        r.cadenas.stream().allMatch(
                                c -> c.usuarios.size() - 2 <= 2
                        )
        );
    }

    // CP39: Comprueba que excluya usuarios prohibidos.
    private static void probarExcluidos() {
        CadenasDifusion.Resultado r = resolver(
                16, 3, Set.of("Caro")
        );

        verificar("CP39 - Usuarios excluidos",
                r.cadenas.size() == 1 &&
                        r.cadenas.get(0).usuarios.equals(
                                List.of("Ana", "Beto", "Dani", "Eva")
                        )
        );
    }

    // CP40: Comprueba cuando no existe solucion.
    private static void probarSinSolucion() {
        CadenasDifusion.Resultado r = resolver(10, 3, Set.of());

        verificar("CP40 - Sin solucion",
                r.cadenas.isEmpty() &&
                        r.cadenaMasRapida == null
        );
    }

    // CP41: Comprueba que no se repitan usuarios.
    private static void probarSinRepeticiones() {
        Map<String, List<Vinculo>> red = DatosDifusion.crearRed();

        // Agregamos un ciclo para probar el algoritmo.
        red.get("Beto").add(new Vinculo("Ana", 1));

        CadenasDifusion.Resultado r =
                CadenasDifusion.resolver(
                        red, "Ana", "Eva", 16, 3, Set.of()
                );

        boolean sinRepetidos = !r.cadenas.isEmpty();

        for (CadenasDifusion.Cadena c : r.cadenas) {
            if (new HashSet<>(c.usuarios).size()
                    != c.usuarios.size()) {
                sinRepetidos = false;
            }
        }

        verificar("CP41 - Sin usuarios repetidos",
                sinRepetidos && r.cadenas.size() == 3
        );
    }

    // CP42: Comprueba cuando origen y destino coinciden.
    private static void probarOrigenIgualDestino() {
        CadenasDifusion.Resultado r =
                CadenasDifusion.resolver(
                        DatosDifusion.crearRed(),
                        "Ana",
                        "Ana",
                        0,
                        0,
                        Set.of()
                );

        verificar("CP42 - Origen igual a destino",
                r.cadenas.size() == 1 &&
                        r.cadenas.get(0).usuarios.equals(
                                List.of("Ana")
                        ) &&
                        r.cadenas.get(0).tiempoTotal == 0
        );
    }

    // CP43: Comprueba que rechace limites negativos.
    private static void probarDatosInvalidos() {
        boolean rechazo = false;

        try {
            resolver(-1, 3, Set.of());
        } catch (IllegalArgumentException e) {
            rechazo = true;
        }

        verificar("CP43 - Datos invalidos", rechazo);
    }

    public static void main(String[] args) {
        ejecutar();
    }
}
