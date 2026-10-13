package redsocial.algoritmos;

import redsocial.modelo.Publicacion;

public class MergeSortPublicaciones {

    public enum Criterio {
        RECIENTE, RELEVANTE
    }

    public static Publicacion[] ordenar(
            Publicacion[] publicaciones, Criterio criterio) {

        Publicacion[] copia = publicaciones.clone();
        Publicacion[] auxiliar = new Publicacion[copia.length];

        mergeSort(copia, auxiliar, 0, copia.length - 1, criterio);

        return copia;
    }

    private static void mergeSort(Publicacion[] datos,
                                  Publicacion[] auxiliar,
                                  int inicio,
                                  int fin,
                                  Criterio criterio) {

        if (inicio >= fin) {
            return;
        }

        int medio = inicio + (fin - inicio) / 2;

        mergeSort(datos, auxiliar, inicio, medio, criterio);
        mergeSort(datos, auxiliar, medio + 1, fin, criterio);

        mezclar(datos, auxiliar, inicio, medio, fin, criterio);
    }

    private static void mezclar(Publicacion[] datos,
                                Publicacion[] auxiliar,
                                int inicio,
                                int medio,
                                int fin,
                                Criterio criterio) {

        int i = inicio;
        int j = medio + 1;
        int k = inicio;

        while (i <= medio && j <= fin) {

            if (comparar(datos[i], datos[j], criterio) <= 0) {
                auxiliar[k++] = datos[i++];
            } else {
                auxiliar[k++] = datos[j++];
            }
        }

        while (i <= medio) {
            auxiliar[k++] = datos[i++];
        }

        while (j <= fin) {
            auxiliar[k++] = datos[j++];
        }

        for (int x = inicio; x <= fin; x++) {
            datos[x] = auxiliar[x];
        }
    }

    private static int comparar(Publicacion a,
                                Publicacion b,
                                Criterio criterio) {

        if (criterio == Criterio.RELEVANTE) {

            int resultado = Integer.compare(
                    b.getRelevancia(),
                    a.getRelevancia()
            );

            if (resultado != 0) {
                return resultado;
            }
        }

        int fecha = b.getFecha().compareTo(a.getFecha());

        if (fecha != 0) {
            return fecha;
        }

        return Integer.compare(a.getId(), b.getId());
    }
}
