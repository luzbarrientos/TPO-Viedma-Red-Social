
package redsocial.datos;

import redsocial.modelo.*;
import java.time.LocalDateTime;
import java.util.*;

public class DatosEjemplo {

    public static Publicacion[] publicaciones() {
        return new Publicacion[]{
                new Publicacion(1, "Paz",
                        LocalDateTime.of(2026, 10, 10, 10, 0), 45, 15),
                new Publicacion(2, "Luis",
                        LocalDateTime.of(2026, 10, 12, 9, 0), 20, 5),
                new Publicacion(4, "Sol",
                        LocalDateTime.of(2026, 10, 11, 15, 0), 15, 10),
                new Publicacion(5, "Ana",
                        LocalDateTime.of(2026, 10, 9, 12, 0), 10, 5)
        };
    }

    public static List<Iniciativa> iniciativas() {
        return Arrays.asList(
                new Iniciativa(1, 4, 7),
                new Iniciativa(2, 5, 9),
                new Iniciativa(3, 3, 6),
                new Iniciativa(4, 2, 4)
        );
    }

    public static Map<String, List<Vinculo>> red() {
        Map<String, List<Vinculo>> red = new LinkedHashMap<>();

        for (String nombre : Arrays.asList(
                "Ana", "Beto", "Caro", "Dani", "Eva")) {
            red.put(nombre, new ArrayList<>());
        }

        conectar(red, "Ana", "Beto", 4);
        conectar(red, "Ana", "Caro", 2);
        conectar(red, "Caro", "Beto", 1);
        conectar(red, "Beto", "Dani", 5);
        conectar(red, "Caro", "Dani", 10);
        conectar(red, "Dani", "Eva", 3);

        return red;
    }

    private static void conectar(
            Map<String, List<Vinculo>> red,
            String origen, String destino, int minutos) {

        red.get(origen).add(new Vinculo(destino, minutos));
    }
}
