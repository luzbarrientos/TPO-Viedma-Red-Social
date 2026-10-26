
package redsocial.modelo;

import java.util.HashSet;
import java.util.Set;

public class Estudiante {

    private final int id;
    private final String nombre;
    private final Set<String> habilidades;

    public Estudiante(int id, String nombre, Set<String> habilidades) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (habilidades == null) {
            throw new IllegalArgumentException("Las habilidades no pueden ser null.");
        }

        this.id = id;
        this.nombre = nombre.trim();
        this.habilidades = new HashSet<>(habilidades);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<String> getHabilidades() {
        return new HashSet<>(habilidades);
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ", habilidades: " + habilidades + ")";
    }
}