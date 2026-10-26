package redsocial.modelo;

public class Actividad {

    private final int id;
    private final String nombre;
    private final int inicio;
    private final int fin;
    private final int interes;

    public Actividad(int id, String nombre, int inicio, int fin, int interes) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (inicio < 0 || fin > 1440 || inicio >= fin) {
            throw new IllegalArgumentException(
                    "El horario debe cumplir 0 <= inicio < fin <= 1440."
            );
        }

        if (interes < 0) {
            throw new IllegalArgumentException(
                    "El interés no puede ser negativo."
            );
        }

        this.id = id;
        this.nombre = nombre.trim();
        this.inicio = inicio;
        this.fin = fin;
        this.interes = interes;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    public int getInteres() {
        return interes;
    }

    private String formatearHora(int minutos) {
        int horas = minutos / 60;
        int minutosRestantes = minutos % 60;

        return String.format("%02d:%02d", horas, minutosRestantes);
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horario=" + formatearHora(inicio) +
                "-" + formatearHora(fin) +
                ", interes=" + interes +
                '}';
    }
}