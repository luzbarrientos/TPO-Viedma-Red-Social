
package redsocial.pruebas;

import redsocial.algoritmos.SeleccionActividades;
import redsocial.datos.DatosActividades;
import redsocial.modelo.Actividad;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PruebasSeleccionActividades {
    private static int correctas;
    private static int fallidas;

    private static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("[OK] " + nombre);
        } else {
            fallidas++;
            System.out.println("[ERROR] " + nombre);
        }
    }

    private static boolean seleccionValida(List<Actividad> entrada,
                                           SeleccionActividades.Resultado resultado) {
        Set<Integer> ids = new HashSet<>();
        int suma = 0;
        for (Actividad a : resultado.seleccionadas) {
            if (!entrada.contains(a) || !ids.add(a.getId())) return false;
            suma += a.getInteres();
        }
        for (int i = 0; i < resultado.seleccionadas.size(); i++) {
            Actividad a = resultado.seleccionadas.get(i);
            for (int j = i + 1; j < resultado.seleccionadas.size(); j++) {
                Actividad b = resultado.seleccionadas.get(j);
                if (a.getInicio() < b.getFin() && b.getInicio() < a.getFin()) return false;
            }
        }
        return suma == resultado.interesTotal;
    }

    private static boolean ids(List<Actividad> seleccionadas, int... esperados) {
        if (seleccionadas.size() != esperados.length) return false;
        Set<Integer> presentes = new HashSet<>();
        for (Actividad a : seleccionadas) presentes.add(a.getId());
        if (presentes.size() != esperados.length) return false;
        for (int id : esperados) if (!presentes.contains(id)) return false;
        return true;
    }

    private static List<Actividad> lista(Actividad... actividades) {
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad a : actividades) resultado.add(a);
        return resultado;
    }

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;
        System.out.println("\n--- PRUEBAS SELECCION DE ACTIVIDADES ---");

        List<Actividad> ejemplo = DatosActividades.crearActividades();
        SeleccionActividades.Resultado r19 = SeleccionActividades.resolver(ejemplo);
        verificar("CP19 - Datos de ejemplo", r19.interesTotal == 22
                && ids(r19.seleccionadas, 1, 3, 4) && seleccionValida(ejemplo, r19));

        List<Actividad> vacia = lista();
        SeleccionActividades.Resultado r20 = SeleccionActividades.resolver(vacia);
        verificar("CP20 - Lista vacia", r20.interesTotal == 0
                && r20.seleccionadas.isEmpty());

        List<Actividad> una = lista(new Actividad(1, "Taller", 540, 600, 8));
        SeleccionActividades.Resultado r21 = SeleccionActividades.resolver(una);
        verificar("CP21 - Una actividad", r21.interesTotal == 8
                && ids(r21.seleccionadas, 1) && seleccionValida(una, r21));

        List<Actividad> superpuestas = lista(
                new Actividad(1, "A", 540, 660, 8),
                new Actividad(2, "B", 600, 720, 12));
        SeleccionActividades.Resultado r22 = SeleccionActividades.resolver(superpuestas);
        verificar("CP22 - Actividades superpuestas", r22.interesTotal == 12
                && ids(r22.seleccionadas, 2) && seleccionValida(superpuestas, r22));

        List<Actividad> consecutivas = lista(
                new Actividad(1, "A", 540, 600, 8),
                new Actividad(2, "B", 600, 660, 12));
        SeleccionActividades.Resultado r23 = SeleccionActividades.resolver(consecutivas);
        verificar("CP23 - Actividades consecutivas", r23.interesTotal == 20
                && ids(r23.seleccionadas, 1, 2) && seleccionValida(consecutivas, r23));

        List<Actividad> combinadas = lista(
                new Actividad(1, "Larga", 540, 780, 15),
                new Actividad(2, "Primera", 540, 660, 10),
                new Actividad(3, "Segunda", 660, 780, 10));
        SeleccionActividades.Resultado r24 = SeleccionActividades.resolver(combinadas);
        verificar("CP24 - Optimo combinado", r24.interesTotal == 20
                && ids(r24.seleccionadas, 2, 3) && seleccionValida(combinadas, r24));

        List<Actividad> empate = lista(
                new Actividad(1, "A", 540, 600, 10),
                new Actividad(2, "B", 540, 600, 10),
                new Actividad(3, "C", 600, 660, 10));
        SeleccionActividades.Resultado r25 = SeleccionActividades.resolver(empate);
        verificar("CP25 - Empate entre soluciones", r25.interesTotal == 20
                && r25.seleccionadas.size() == 2 && seleccionValida(empate, r25));

        boolean excepcion = false;
        try {
            new Actividad(1, "Invalida", 720, 600, 10);
        } catch (IllegalArgumentException e) {
            excepcion = true;
        }
        verificar("CP26 - Horario invalido", excepcion);

        System.out.println("Correctas: " + correctas + " | Fallidas: " + fallidas);
        return fallidas == 0;
    }

    public static void main(String[] args) {
        ejecutar();
    }
}
