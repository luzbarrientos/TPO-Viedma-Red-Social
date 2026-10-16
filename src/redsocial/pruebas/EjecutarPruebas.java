
package redsocial.pruebas;

public class EjecutarPruebas {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println(" TPO PROGRAMACION III");
        System.out.println(" PRUEBAS DE LA PRIMERA ENTREGA");
        System.out.println("================================");

        boolean mergeSort = PruebasMergeSort.ejecutar();
        boolean mochila = PruebasMochila.ejecutar();
        boolean dijkstra = PruebasDijkstra.ejecutar();

        System.out.println("\n=== RESUMEN GENERAL ===");

        System.out.println("Merge Sort: "
                + (mergeSort ? "CORRECTO" : "FALLIDO"));

        System.out.println("Mochila: "
                + (mochila ? "CORRECTO" : "FALLIDO"));

        System.out.println("Dijkstra: "
                + (dijkstra ? "CORRECTO" : "FALLIDO"));

        if (mergeSort && mochila && dijkstra) {
            System.out.println("\nRESULTADO: 18/18 PRUEBAS CORRECTAS");
        } else {
            System.out.println("\nRESULTADO: HAY PRUEBAS FALLIDAS");
        }
    }
}
