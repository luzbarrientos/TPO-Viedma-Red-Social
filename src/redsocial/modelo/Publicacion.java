package redsocial.modelo;

import java.time.LocalDateTime;

public class Publicacion {

    private final int id;
    private final String autor;
    private final LocalDateTime fecha;
    private final int reacciones;
    private final int comentarios;

    public Publicacion(int id, String autor, LocalDateTime fecha,
                       int reacciones, int comentarios) {

        if (id <= 0 || autor == null || autor.isBlank()
                || fecha == null || reacciones < 0 || comentarios < 0) {
            throw new IllegalArgumentException("Publicacion invalida");
        }

        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.reacciones = reacciones;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getRelevancia() {
        return reacciones + comentarios;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Autor: " + autor
                + " | Fecha: " + fecha
                + " | Reacciones: " + reacciones
                + " | Comentarios: " + comentarios
                + " | Relevancia: " + getRelevancia();
    }
}