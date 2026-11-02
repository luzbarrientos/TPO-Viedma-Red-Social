
package redsocial.datos;

import redsocial.modelo.Vinculo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatosDifusion {

    private DatosDifusion() {
        // Clase utilizada solamente para proporcionar datos.
    }

    // Reutilizamos la red de contactos de Dijkstra.
    public static Map<String, List<Vinculo>> crearRed() {
        return DatosEjemplo.red();
    }

    // Usuario desde el cual comienza la difusion.
    public static String crearOrigen() {
        return "Ana";
    }

    // Usuario al que queremos llegar.
    public static String crearDestino() {
        return "Eva";
    }

    // Tiempo maximo permitido para una cadena.
    public static int crearTiempoMaximo() {
        return 16;
    }

    // Cantidad maxima de usuarios intermediarios.
    public static int crearMaximoIntermediarios() {
        return 3;
    }

    // Usuarios que no pueden participar en las cadenas.
    public static Set<String> crearUsuariosExcluidos() {
        return Set.of();
    }
}
