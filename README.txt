GastroVote

Autor: Ginés Rodríguez Morales
Tecnología: Java (Android Studio)
Proyecto: GastroVote – Aplicación Android para valorar tapas.

Descripción del proyecto

GastroVote es una aplicación Android desarrollada en Java cuyo objetivo es permitir la valoración de tapas mediante una interfaz sencilla e intuitiva.

El proyecto se desarrolla utilizando Android Studio, Java y XML para la creación de las interfaces gráficas.


<<Entrega 1>> 
Lo implementado en la Entrega 1:
Funcionalidades implementadas

Selección de bares: navegación entre 8 bares mediante botones anterior y siguiente.

Información de las tapas: visualización del nombre, descripción, precio base y valoración media de cada tapa.

Sistema de favoritos: posibilidad de marcar y desmarcar bares como favoritos.

Formulario de reseñas: selección del tamaño de la tapa, puntuación de 0 a 5 estrellas y valoración de calidad-precio.

Etiquetas: selección de características de las tapas: vegetariana, sin gluten y picante.

Me gusta: interruptor para indicar si la tapa ha gustado.

Descuento de estudiante: aplicación de un descuento del 10 % sobre el precio.

Comentarios: campo de texto opcional con un límite de 200 caracteres y contador actualizado.

Publicación de reseñas: validación de la puntuación y cálculo del precio final según el tamaño y el descuento.

Resumen de reseñas: presentación de los datos de la reseña publicada.

Limpieza del formulario: confirmación antes de borrar los datos introducidos.

Progreso de la ruta: contador y barra de progreso que registran los bares distintos visitados mediante reseñas.

Logro «Crítico gastronómico»: diálogo especial al publicar reseñas en tres bares diferentes.

Internacionalización: traducción de la interfaz al español y al inglés.

Modo oscuro: adaptación de los colores de la aplicación al tema oscuro del dispositivo.

Recursos y diseño

Textos centralizados en strings.xml y values-en/strings.xml.

Datos de bares y tapas organizados en arrays.xml.

Colores definidos en recursos para los modos claro y oscuro.

Estilos reutilizables para mantener la coherencia visual de la interfaz.


//Capturas de pantalla, guardadas en la carpeta docs/capturas/ del repositorio.


## Tabla de pruebas

| Nº | Prueba realizada | Resultado esperado | Resultado obtenido |
|---|---|---|---|
| 1.1 | Small Phone en vertical | La pantalla se adapta y permite desplazarse para ver todo el contenido. | Se ve bien y se puede desplazar por toda la pantalla. |
| 1.2 | Medium Tablet en vertical | La interfaz se muestra correctamente en una pantalla de mayor tamaño. | Se ve bien, con más espacio al ser una pantalla más grande, y permite desplazarse. |
| 2.1 | Tema claro | Los textos y elementos se visualizan correctamente. | Correcto. |
| 2.2 | Tema oscuro | Los colores se adaptan al tema oscuro. | Correcto. |
| 3.1 | Idioma español | Todos los textos aparecen en español. | Todos los textos aparecen en español. |
| 3.2 | Idioma inglés | Todos los textos aparecen en inglés. | Los textos aparecen en inglés, excepto aquellos que, según la documentación, no deben traducirse. |
| 4.1 | Tamaño de fuente del sistema al máximo | Los textos siguen siendo legibles y no se solapan. | Siguen siendo legibles y no se solapan. |
| 5.1 | Navegación con ◀ y ▶ | Se recorren los 8 bares y funciona el salto circular entre el primero y el último, y viceversa. | Se recorren los 8 bares y se puede pasar del primero al último y del último al primero. |
| 5.2 | Publicar sin estrellas | La aplicación impide publicar la reseña. | Impide publicar la reseña y muestra un Toast avisando de que hay que seleccionar estrellas. |
| 5.3 | Precio con los tres tamaños sin descuento | El precio se calcula correctamente según el tamaño seleccionado. | El precio se calcula correctamente. |
| 5.4 | Precio con los tres tamaños y descuento | Se aplica correctamente el descuento del 10 %. | El precio se calcula correctamente y se aplica el descuento del 10 %. |
| 5.5 | Reseñar dos veces el mismo bar | El contador de bares visitados no aumenta por segunda vez. | El contador no aumenta al reseñar por segunda vez el mismo bar. |
| 5.6 | Cancelar el diálogo de Limpiar | El formulario conserva los datos introducidos. | Los datos del formulario se conservan al cancelar. |


### Problema de diseño detectado con Layout Inspector
El boton Me gusta no tenia color
Lo solucione asignandole el color principal(en este caso naranja), ahora se ve bien

Limitaciones conocidas

Las reseñas no se guardan permanentemente; al cerrar la aplicación se pierden.

Los favoritos se mantienen mientras la aplicación está abierta, pero no se conservan al reiniciarla.

La aplicación utiliza un catálogo de 8 bares predefinidos y no permite añadir bares nuevos.

Hay datos que no se han implementado como la direccion y el Telefono
