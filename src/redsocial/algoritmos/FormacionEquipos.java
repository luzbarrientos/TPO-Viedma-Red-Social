
package redsocial.algoritmos;

import redsocial.modelo.Estudiante;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormacionEquipos {

    public static List<List<Estudiante>> resolver(
            List<Estudiante> estudiantes,
            int tamanioEquipo,
            Set<String> habilidadesRequeridas,
            Set<Integer> integrantesObligatorios,
            Set<String> incompatibilidades
    ) {

        if (estudiantes == null ||
                habilidadesRequeridas == null ||
                integrantesObligatorios == null ||
                incompatibilidades == null) {
            throw new IllegalArgumentException(
                    "Los datos no pueden ser null."
            );
        }

        if (tamanioEquipo <= 0) {
            throw new IllegalArgumentException(
                    "El tamanio del equipo debe ser positivo."
            );
        }

        // Verificamos que no haya estudiantes nulos ni IDs repetidos.
        Set<Integer> ids = new HashSet<>();

        for (Estudiante estudiante : estudiantes) {
            if (estudiante == null || !ids.add(estudiante.getId())) {
                throw new IllegalArgumentException(
                        "Hay estudiantes nulos o IDs repetidos."
                );
            }
        }

        List<List<Estudiante>> equiposValidos = new ArrayList<>();

        // Lista que almacena el equipo que estamos construyendo.
        List<Estudiante> equipoActual = new ArrayList<>();

        backtracking(
                estudiantes,
                tamanioEquipo,
                habilidadesRequeridas,
                integrantesObligatorios,
                incompatibilidades,
                0,
                equipoActual,
                equiposValidos
        );

        return equiposValidos;
    }

    private static void backtracking(
            List<Estudiante> estudiantes,
            int tamanioEquipo,
            Set<String> habilidadesRequeridas,
            Set<Integer> integrantesObligatorios,
            Set<String> incompatibilidades,
            int indice,
            List<Estudiante> equipoActual,
            List<List<Estudiante>> equiposValidos
    ) {

        // Caso base: el equipo ya tiene la cantidad requerida.
        if (equipoActual.size() == tamanioEquipo) {

            if (cumpleHabilidades(equipoActual, habilidadesRequeridas)
                    && cumpleObligatorios(equipoActual, integrantesObligatorios)) {

                equiposValidos.add(new ArrayList<>(equipoActual));
            }

            return;
        }

        // Si no quedan suficientes estudiantes, dejamos de explorar.
        if (equipoActual.size() + (estudiantes.size() - indice)
                < tamanioEquipo) {
            return;
        }

        if (indice == estudiantes.size()) {
            return;
        }

        Estudiante estudiante = estudiantes.get(indice);

        // Opcion 1: incluir al estudiante si es compatible.
        if (esCompatible(estudiante, equipoActual, incompatibilidades)) {

            equipoActual.add(estudiante);

            backtracking(
                    estudiantes,
                    tamanioEquipo,
                    habilidadesRequeridas,
                    integrantesObligatorios,
                    incompatibilidades,
                    indice + 1,
                    equipoActual,
                    equiposValidos
            );

            // Deshacemos la decision para probar otras combinaciones.
            equipoActual.remove(equipoActual.size() - 1);
        }

        // Opcion 2: no incluir al estudiante.
        backtracking(
                estudiantes,
                tamanioEquipo,
                habilidadesRequeridas,
                integrantesObligatorios,
                incompatibilidades,
                indice + 1,
                equipoActual,
                equiposValidos
        );
    }

    private static boolean esCompatible(
            Estudiante candidato,
            List<Estudiante> equipoActual,
            Set<String> incompatibilidades
    ) {

        for (Estudiante integrante : equipoActual) {

            int menor = Math.min(candidato.getId(), integrante.getId());
            int mayor = Math.max(candidato.getId(), integrante.getId());

            String par = menor + "-" + mayor;

            if (incompatibilidades.contains(par)) {
                return false;
            }
        }

        return true;
    }

    private static boolean cumpleHabilidades(
            List<Estudiante> equipo,
            Set<String> habilidadesRequeridas
    ) {

        Set<String> habilidadesEquipo = new HashSet<>();

        for (Estudiante estudiante : equipo) {
            habilidadesEquipo.addAll(estudiante.getHabilidades());
        }

        return habilidadesEquipo.containsAll(habilidadesRequeridas);
    }

    private static boolean cumpleObligatorios(
            List<Estudiante> equipo,
            Set<Integer> integrantesObligatorios
    ) {

        Set<Integer> idsEquipo = new HashSet<>();

        for (Estudiante estudiante : equipo) {
            idsEquipo.add(estudiante.getId());
        }

        return idsEquipo.containsAll(integrantesObligatorios);
    }
}

