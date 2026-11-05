# Red Social Universitaria

**Trabajo Práctico Obligatorio — Programación III**

Aplicación desarrollada en Java para resolver problemas de colaboración entre estudiantes universitarios mediante estrategias de diseño y análisis de algoritmos.

El proyecto integra seis funcionalidades, una interfaz gráfica desarrollada con Java Swing y pruebas automatizadas.

---

## Integrantes

- Luz Barrientos
- Germán Rocha Sánchez
- Martina Córdoba

---

## Descripción del proyecto

La Red Social Universitaria es una aplicación que simula situaciones de interacción y colaboración dentro de una comunidad académica.

Permite organizar publicaciones, encontrar conexiones entre usuarios, seleccionar iniciativas, planificar actividades, formar equipos y buscar cadenas alternativas de difusión.

Cada funcionalidad utiliza una estrategia algorítmica adecuada a las características del problema.

---

## Algoritmos implementados

### 1. Ordenamiento de publicaciones

**Algoritmo:** Merge Sort  
**Estrategia:** Divide y Vencerás  
**Complejidad temporal:** O(n log n)

Permite ordenar publicaciones según criterios como fecha y relevancia.

### 2. Camino más corto entre usuarios

**Algoritmo:** Dijkstra  
**Estrategia:** Greedy

Permite encontrar el camino de menor tiempo entre dos usuarios de la red social.

Utiliza una cola de prioridad y considera vínculos dirigidos con tiempos positivos.

### 3. Selección de iniciativas

**Algoritmo:** Mochila 0/1  
**Estrategia:** Programación Dinámica

Selecciona iniciativas universitarias buscando maximizar el impacto total sin superar un presupuesto establecido.

Cada iniciativa puede ser seleccionada como máximo una vez.

### 4. Selección de actividades compatibles (A3)

**Algoritmo:** Selección ponderada de intervalos  
**Estrategia:** Programación Dinámica

Permite elegir actividades que no se superpongan y que maximicen el interés total.

La interfaz gráfica permite:

- Agregar actividades.
- Editar actividades existentes.
- Eliminar actividades.
- Restaurar los datos originales.
- Calcular la selección óptima.

### 5. Formación de equipos (B1)

**Algoritmo:** Backtracking

Genera combinaciones válidas de estudiantes respetando las siguientes restricciones:

- Tamaño del equipo.
- Habilidades requeridas.
- Integrantes obligatorios.
- Incompatibilidades entre estudiantes.

Utiliza podas para evitar explorar combinaciones que no pueden completar un equipo válido.

### 6. Cadenas alternativas de difusión (B3)

**Algoritmo:** Backtracking

Busca cadenas de difusión entre un usuario de origen y otro de destino.

Considera:

- Tiempo máximo permitido.
- Cantidad máxima de intermediarios.
- Usuarios excluidos.
- Prohibición de repetir usuarios en una misma cadena.

Además, identifica la cadena válida de menor tiempo total.

---

## Interfaz gráfica

La aplicación cuenta con una interfaz desarrollada mediante **Java Swing**, organizada en seis pestañas:

1. Publicaciones.
2. Contactos.
3. Iniciativas.
4. Actividades.
5. Equipos.
6. Difusión.

Cada pestaña permite ejecutar la funcionalidad correspondiente y visualizar sus resultados.

---

## Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **Java Swing:** desarrollo de la interfaz gráfica.
- **IntelliJ IDEA:** entorno de desarrollo.
- **Git:** control de versiones.
- **GitHub:** almacenamiento del repositorio y trabajo colaborativo.

---

## Estructura del proyecto

El proyecto está organizado en los siguientes paquetes:

- **redsocial.modelo:** clases que representan las entidades del sistema.
- **redsocial.datos:** datos de ejemplo utilizados por los algoritmos.
- **redsocial.algoritmos:** implementación de las seis funcionalidades.
- **redsocial.interfaz:** ventana principal y paneles gráficos.
- **redsocial.pruebas:** pruebas automatizadas y ejecutor general.

### Clases principales de algoritmos

- MergeSortPublicaciones.java
- Dijkstra.java
- Mochila.java
- SeleccionActividades.java
- FormacionEquipos.java
- CadenasDifusion.java

### Clases principales de la interfaz

- MainGUI.java
- VentanaPrincipal.java
- PanelFuncionalidades.java
- PanelFuncionalidadesExtra.java

---

## Cómo ejecutar el proyecto

### Requisitos

- Tener instalado Java JDK 21.
- Contar con IntelliJ IDEA u otro entorno compatible con Java.

### Ejecutar la aplicación gráfica

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA.
3. Configurar el JDK 21.
4. Abrir la clase `redsocial.interfaz.MainGUI`.
5. Ejecutar su método `main`.

Se abrirá la ventana principal con las seis funcionalidades.

### Ejecutar las pruebas automatizadas

1. Abrir la clase `redsocial.pruebas.EjecutarPruebas`.
2. Ejecutar su método `main`.
3. Consultar los resultados en la consola.

El proyecto también conserva la clase `redsocial.Main` para la ejecución por consola.

---

## Pruebas realizadas

Se desarrollaron pruebas automatizadas para verificar el funcionamiento de los algoritmos y sus restricciones.

En la última revisión de integración se ejecutaron **43 pruebas**, con los siguientes resultados:

- **Pruebas correctas:** 43.
- **Pruebas fallidas:** 0.

Los casos comprueban resultados esperados, restricciones y distintas situaciones límite.

Estos resultados corresponden a la revisión realizada y deben verificarse nuevamente después de modificar el código.

---

## Estrategias algorítmicas utilizadas

**Divide y Vencerás:** permite dividir un problema en subproblemas más pequeños y combinar sus soluciones.

**Greedy:** construye soluciones mediante decisiones locales convenientes.

**Programación Dinámica:** resuelve problemas de optimización reutilizando resultados de subproblemas.

**Backtracking:** explora combinaciones y retrocede cuando una alternativa no cumple las restricciones.

---

## Objetivos académicos

- Aplicar diferentes estrategias de diseño de algoritmos.
- Analizar la eficiencia y complejidad de las soluciones.
- Utilizar estructuras de datos adecuadas.
- Resolver problemas de optimización y búsqueda.
- Implementar y ejecutar casos de prueba.
- Integrar las funcionalidades en una aplicación Java.
- Desarrollar una interfaz gráfica.
- Trabajar colaborativamente mediante Git y GitHub.

---

## Estado del proyecto

El proyecto **Red Social Universitaria** se encuentra finalizado, con las seis funcionalidades implementadas e integradas en una aplicación de escritorio desarrollada con Java Swing.

El sistema incluye:

- **Seis algoritmos implementados:** Merge Sort, Dijkstra, Mochila 0/1, Selección de Actividades, Formación de Equipos y Cadenas Alternativas de Difusión.
- **Interfaz gráfica completa:** seis pestañas que permiten utilizar las distintas funcionalidades.
- **Validaciones de entrada:** controles para detectar datos incorrectos y gestionar situaciones excepcionales.
- **Pruebas automatizadas:** 43 casos ejecutados satisfactoriamente en la revisión de integración.
- **Código organizado:** separación en paquetes de modelos, datos, algoritmos, interfaz y pruebas.
- **Control de versiones:** desarrollo colaborativo mediante Git y GitHub.

El proyecto integra los conocimientos de **Divide y Vencerás, Greedy, Programación Dinámica y Backtracking**, aplicados a problemas relacionados con la colaboración universitaria.

**Estado: Desarrollo e integración completados.**

---

**Proyecto académico — Programación III**

*Red Social Universitaria para la Colaboración entre Estudiantes.*
