package redsocial.datos;

import redsocial.modelo.Actividad;

import java.util.ArrayList;
import java.util.List;

public class DatosActividades {

    private DatosActividades() {
        // Esta clase solo proporciona datos de ejemplo.
    }

    public static List<Actividad> crearActividades() {
        List<Actividad> actividades = new ArrayList<>();

        actividades.add(
                new Actividad(1, "Taller Java", 540, 660, 8)
        );

        actividades.add(
                new Actividad(2, "Taller Python", 600, 720, 12)
        );

        actividades.add(
                new Actividad(3, "Charla IA", 660, 780, 9)
        );

        actividades.add(
                new Actividad(4, "Taller Git", 780, 840, 5)
        );

        return actividades;
    }
}