
package redsocial.datos;

import redsocial.modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatosEquipos {

    private DatosEquipos() {
    }

    public static List<Estudiante> crearEstudiantes() {

        List<Estudiante> estudiantes = new ArrayList<>();

        estudiantes.add(new Estudiante(
                1, "Ana", Set.of("Java", "SQL")
        ));

        estudiantes.add(new Estudiante(
                2, "Bruno", Set.of("Python", "Diseno")
        ));

        estudiantes.add(new Estudiante(
                3, "Carla", Set.of("Java", "Testing")
        ));

        estudiantes.add(new Estudiante(
                4, "Diego", Set.of("SQL", "Python")
        ));

        estudiantes.add(new Estudiante(
                5, "Elena", Set.of("Testing", "Diseno")
        ));

        return estudiantes;
    }

    public static int crearTamanioEquipo() {
        return 3;
    }

    public static Set<String> crearHabilidadesRequeridas() {
        return Set.of("Java", "Testing");
    }

    public static Set<Integer> crearIntegrantesObligatorios() {
        return Set.of(1);
    }

    public static Set<String> crearIncompatibilidades() {
        return Set.of("2-4", "3-5");
    }
}