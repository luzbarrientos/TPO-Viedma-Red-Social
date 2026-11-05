package redsocial.pruebas;

import redsocial.algoritmos.FormacionEquipos;
import redsocial.datos.DatosEquipos;
import redsocial.modelo.Estudiante;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PruebasFormacionEquipos {

    private static int correctas = 0;
    private static int fallidas = 0;

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("\n=== PRUEBAS B1: FORMACION DE EQUIPOS ===");

        probarDatosEjemplo();
        probarTamanioEquipo();
        probarHabilidades();
        probarIntegrantesObligatorios();
        probarIncompatibilidades();
        probarSinSoluciones();
        probarSinEquiposRepetidos();
        probarEntradaInvalida();

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

    // CP27: Datos de ejemplo, cuatro equipos validos.
    private static void probarDatosEjemplo() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        Set<String> obtenidos = new HashSet<>();

        for (List<Estudiante> equipo : equipos) {
            obtenidos.add(claveEquipo(equipo));
        }

        Set<String> esperados = Set.of(
                "1-2-3", "1-2-5", "1-3-4", "1-4-5"
        );

        verificar("CP27 - Equipos esperados",
                equipos.size() == 4 && obtenidos.equals(esperados));
    }

    // CP28: Todos los equipos deben tener tres integrantes.
    private static void probarTamanioEquipo() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        boolean correcto = !equipos.isEmpty();

        for (List<Estudiante> equipo : equipos) {
            if (equipo.size() != 3) {
                correcto = false;
            }
        }

        verificar("CP28 - Tamanio de equipo", correcto);
    }

    // CP29: Todos los equipos deben cubrir Java y Testing.
    private static void probarHabilidades() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        boolean correcto = !equipos.isEmpty();

        for (List<Estudiante> equipo : equipos) {
            Set<String> habilidades = new HashSet<>();

            for (Estudiante estudiante : equipo) {
                habilidades.addAll(estudiante.getHabilidades());
            }

            if (!habilidades.containsAll(Set.of("Java", "Testing"))) {
                correcto = false;
            }
        }

        verificar("CP29 - Habilidades requeridas", correcto);
    }

    // CP30: Ana debe aparecer en todos los equipos.
    private static void probarIntegrantesObligatorios() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        boolean correcto = !equipos.isEmpty();

        for (List<Estudiante> equipo : equipos) {
            boolean tieneAna = false;

            for (Estudiante estudiante : equipo) {
                if (estudiante.getId() == 1) {
                    tieneAna = true;
                }
            }

            if (!tieneAna) {
                correcto = false;
            }
        }

        verificar("CP30 - Integrantes obligatorios", correcto);
    }

    // CP31: No deben aparecer parejas incompatibles.
    private static void probarIncompatibilidades() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        boolean correcto = !equipos.isEmpty();

        for (List<Estudiante> equipo : equipos) {
            Set<Integer> ids = new HashSet<>();

            for (Estudiante estudiante : equipo) {
                ids.add(estudiante.getId());
            }

            if ((ids.contains(2) && ids.contains(4))
                    || (ids.contains(3) && ids.contains(5))) {
                correcto = false;
            }
        }

        verificar("CP31 - Incompatibilidades", correcto);
    }

    // CP32: Restricciones imposibles, sin soluciones.
    private static void probarSinSoluciones() {
        List<List<Estudiante>> equipos = FormacionEquipos.resolver(
                DatosEquipos.crearEstudiantes(),
                2,
                Set.of("Java", "Testing"),
                Set.of(1, 2, 3),
                Set.of()
        );

        verificar("CP32 - Sin soluciones", equipos.isEmpty());
    }

    // CP33: Ningun equipo debe aparecer repetido.
    private static void probarSinEquiposRepetidos() {
        List<List<Estudiante>> equipos = resolverEjemplo();
        Set<String> claves = new HashSet<>();

        for (List<Estudiante> equipo : equipos) {
            claves.add(claveEquipo(equipo));
        }

        verificar("CP33 - Sin equipos repetidos",
                claves.size() == equipos.size());
    }

    // CP34: Rechazar tamanio de equipo invalido.
    private static void probarEntradaInvalida() {
        boolean rechazo = false;

        try {
            FormacionEquipos.resolver(
                    DatosEquipos.crearEstudiantes(),
                    0,
                    Set.of(),
                    Set.of(),
                    Set.of()
            );
        } catch (IllegalArgumentException e) {
            rechazo = true;
        }

        verificar("CP34 - Entrada invalida", rechazo);
    }

    private static List<List<Estudiante>> resolverEjemplo() {
        return FormacionEquipos.resolver(
                DatosEquipos.crearEstudiantes(),
                DatosEquipos.crearTamanioEquipo(),
                DatosEquipos.crearHabilidadesRequeridas(),
                DatosEquipos.crearIntegrantesObligatorios(),
                DatosEquipos.crearIncompatibilidades()
        );
    }

    private static String claveEquipo(List<Estudiante> equipo) {
        List<Integer> ids = new ArrayList<>();

        for (Estudiante estudiante : equipo) {
            ids.add(estudiante.getId());
        }

        ids.sort(Integer::compareTo);

        StringBuilder clave = new StringBuilder();

        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                clave.append("-");
            }
            clave.append(ids.get(i));
        }

        return clave.toString();
    }

    public static void main(String[] args) {
        ejecutar();
    }
}
