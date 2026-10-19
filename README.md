# TPO Programación III — Red Social Universitaria

## 1. Descripción del proyecto

Aplicación desarrollada en Java para el Trabajo Práctico Obligatorio de Programación III.

El proyecto consiste en una red social universitaria orientada a la colaboración entre estudiantes, que permite organizar publicaciones, encontrar cadenas de difusión entre usuarios y seleccionar iniciativas para financiar.

Se implementaron tres funcionalidades utilizando diferentes estrategias de diseño y análisis de algoritmos.

## 2. Integrantes — Grupo Viedma

- Luz Barrientos
- Germán Rocha Sánchez
- Martina Córdoba

## 3. Tecnologías utilizadas

- Java 21 (Temurin).
- IntelliJ IDEA.
- Git y GitHub para control de versiones.
- Aplicación de consola.

No se requieren bibliotecas externas.

## 4. Funcionalidades implementadas

### Funcionalidad 1 — Ordenamiento de publicaciones

**Algoritmo:** Merge Sort.

**Estrategia:** Divide y Conquista.

Permite ordenar publicaciones de dos maneras:

- Por fecha, desde la más reciente hasta la más antigua.
- Por relevancia, desde la más relevante hasta la menos relevante.

La relevancia se calcula como la suma de reacciones y comentarios.

En caso de empate, se prioriza la publicación más reciente y luego el identificador menor.

**Complejidad temporal:** O(n log n).

### Funcionalidad 2 — Cadena de difusión más rápida

**Algoritmo:** Dijkstra.

**Estrategia:** Greedy.

Permite encontrar el camino de menor tiempo entre dos usuarios de una red de contactos dirigida y ponderada.

El sistema muestra el recorrido, el tiempo total y las decisiones realizadas durante la búsqueda.

También identifica cuando el destino es inalcanzable.

**Complejidad temporal:** O((V + E) log V).

### Funcionalidad 3 — Selección de iniciativas

**Algoritmo:** Mochila 0/1.

**Estrategia:** Programación Dinámica.

Permite seleccionar iniciativas universitarias que maximicen el impacto total sin superar un presupuesto disponible.

Cada iniciativa puede financiarse como máximo una vez.

El programa informa las iniciativas seleccionadas, el costo total, el impacto obtenido y el presupuesto restante.

**Complejidad temporal:** O(n × P).

## 5. Estructura del proyecto

El código se organiza en los siguientes paquetes:

**`redsocial.modelo`**

Contiene las clases que representan los datos:

- `Publicacion.java`
- `Vinculo.java`
- `Iniciativa.java`

**`redsocial.algoritmos`**

Contiene las implementaciones de los algoritmos:

- `MergeSortPublicaciones.java`
- `Dijkstra.java`
- `Mochila.java`

**`redsocial.datos`**

Contiene los datos utilizados para demostrar el funcionamiento:

- `DatosEjemplo.java`

**`redsocial.pruebas`**

Contiene las pruebas automatizadas:

- `PruebasMergeSort.java`
- `PruebasDijkstra.java`
- `PruebasMochila.java`
- `EjecutarPruebas.java`

**`redsocial.Main`**

Contiene el menú principal de la aplicación.

## 6. Cómo ejecutar el programa

1. Abrir el proyecto en IntelliJ IDEA.
2. Configurar Java 21 como SDK del proyecto.
3. Abrir `src/redsocial/Main.java`.
4. Ejecutar el método `main` presionando Run.
5. Seleccionar una funcionalidad desde el menú de consola.

El programa permite consultar las funcionalidades utilizando los datos de ejemplo incluidos en el proyecto.

## 7. Ejecución de pruebas

Se implementaron 18 casos de prueba automatizados, distribuidos de la siguiente manera:

- Merge Sort: 6 casos.
- Mochila 0/1: 6 casos.
- Dijkstra: 6 casos.

Para ejecutarlos:

1. Abrir `src/redsocial/pruebas/EjecutarPruebas.java`.
2. Presionar Run en IntelliJ IDEA.
3. Revisar los resultados de cada prueba en la consola.

**Resultado verificado: 18 pruebas correctas y 0 fallidas.**

La documentación se encuentra en:

`pruebas/CASOS_DE_PRUEBA`

Las capturas de ejecución se encuentran en:

- `pruebas/resultado_pruebas_1.jpeg`
- `pruebas/resultado_pruebas_2.jpeg`

## 8. Funcionalidades previstas para la segunda entrega

Se seleccionaron las siguientes funcionalidades para desarrollar posteriormente:

**A3 — Selección de actividades compatibles**

Se utilizará Programación Dinámica para seleccionar actividades sin superposiciones y maximizar el interés total.

**B1 — Formación de equipos con restricciones**

Se utilizará Backtracking para generar equipos que cumplan las restricciones establecidas.

**B3 — Cadenas alternativas de difusión**

Se utilizará Backtracking para encontrar cadenas de difusión válidas bajo diferentes restricciones.

Estas funcionalidades están planificadas, pero no forman parte de la implementación de la primera entrega.

## 9. Documentación

El proyecto incluye:

- Código fuente de las tres funcionalidades.
- Menú principal de consola.
- Clases de pruebas automatizadas.
- Documento de casos de prueba.
- Capturas de los resultados de ejecución.
- Informe parcial correspondiente a la primera entrega.

## 10. Control de versiones

El desarrollo se realiza mediante Git y GitHub, utilizando un repositorio compartido.

Los cambios se registran mediante commits que permiten consultar la evolución del proyecto y los aportes realizados por los integrantes del grupo.
