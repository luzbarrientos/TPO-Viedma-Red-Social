
package redsocial.pruebas;

public class EjecutarPruebas {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("     TPO PROGRAMACION III");
        System.out.println("  PRUEBAS DE LAS DOS ETAPAS");
        System.out.println("================================");

        // PRIMERA ETAPA
        boolean mergeSort = PruebasMergeSort.ejecutar();
        boolean dijkstra = PruebasDijkstra.ejecutar();
        boolean mochila = PruebasMochila.ejecutar();

        // SEGUNDA ETAPA
        boolean actividades =
                PruebasSeleccionActividades.ejecutar();

        boolean equipos =
                PruebasFormacionEquipos.ejecutar();

        boolean cadenas =
                PruebasCadenasDifusion.ejecutar();

        // RESUMEN GENERAL
        System.out.println("\n================================");
        System.out.println("       RESUMEN GENERAL");
        System.out.println("================================");

        System.out.println("Merge Sort: "
                + (mergeSort ? "CORRECTO" : "FALLIDO"));

        System.out.println("Dijkstra: "
                + (dijkstra ? "CORRECTO" : "FALLIDO"));

        System.out.println("Mochila 0/1: "
                + (mochila ? "CORRECTO" : "FALLIDO"));

        System.out.println("Seleccion de actividades: "
                + (actividades ? "CORRECTO" : "FALLIDO"));

        System.out.println("Formacion de equipos: "
                + (equipos ? "CORRECTO" : "FALLIDO"));

        System.out.println("Cadenas alternativas: "
                + (cadenas ? "CORRECTO" : "FALLIDO"));

        boolean todasCorrectas =
                mergeSort &&
                        dijkstra &&
                        mochila &&
                        actividades &&
                        equipos &&
                        cadenas;

        System.out.println("\n================================");

        if (todasCorrectas) {
            System.out.println("RESULTADO: 43/43 PRUEBAS CORRECTAS");
            System.out.println("TODAS LAS FUNCIONALIDADES APROBADAS");
        } else {
            System.out.println("RESULTADO: HAY PRUEBAS FALLIDAS");
            System.out.println("REVISAR LOS RESULTADOS ANTERIORES");
        }

        System.out.println("================================");
    }
}
