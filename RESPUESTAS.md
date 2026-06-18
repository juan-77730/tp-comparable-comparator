# Trabajo Práctico: Ordenamiento de Objetos 

##  PARTE 1 — El Problema

### Ejercicio 1: El error que dispara todo
* **Error de compilación copiado textual:** `no suitable method found for sort(java.util.List<model.Estudiante>)`
* **Pregunta 1:** ¿Por qué Collections.sort() no compila cuando le pasamos una List<Estudiante>? ¿Qué contrato exige Java que nuestra clase no está cumpliendo?
* **Respuesta:** No compila porque el método `Collections.sort()` exige estrictamente que el tipo de datos almacenado en la lista implemente la interfaz `Comparable`. Como la clase `Estudiante` original no implementaba esta interfaz, Java no conocía su "orden natural" y detuvo el proceso en la compilación para evitar un fallo de ejecución.

---

##  PARTE 2 — Comparable: el orden natural

### Ejercicio 2 & 3: Reflexión sobre la limitación de Comparable
* **Pregunta 2:** ¿Por qué elegiste el atributo promedio como orden natural? ¿Qué pasaría si mañana un nuevo requisito pide ordenar por cantidadMateriasAprobadas?
* **Respuesta:** El promedio es el criterio más intuitivo y universal en el ámbito académico para listar estudiantes por mérito de mayor a menor. Si el requisito cambiara a materias aprobadas, modificar el método `compareTo` rompería la arquitectura del dominio. Alteraría el orden por defecto de toda la aplicación, generando acoplamiento innecesario. Lo correcto es dejar el orden natural quieto y usar un `Comparator` para criterios alternativos.

* **Pregunta 3:** ¿Qué problemas de diseño introduce esto si nuestro sistema necesitara ordenar la misma lista de 4 formas distintas? Relacioná con SRP y OCP.
* **Respuesta:** Infringe de forma directa el **Principio de Responsabilidad Única (SRP)** porque le daría a la clase `Estudiante` la responsabilidad de conocer múltiples lógicas de negocio ajenas a su estado. También rompe el **Principio Abierto/Cerrado (OCP)** ya que obligaría a modificar el código fuente del dominio cada vez que aparezca un nuevo requerimiento en la interfaz de usuario, en vez de extender el comportamiento mediante clases externas.

---

##  PARTE 3 — Comparator y el Anti-patrón de la Resta

### Ejercicio 6: Explicación del Overflow de Enteros
* **Pregunta 4:** Explicá con tus palabras qué es un overflow de enteros, por qué el "truco de la resta" lo provoca, qué parte del contrato rompe, y por qué Integer.compare() no sufre este problema.
* **Respuesta:** El *overflow* sucede cuando una operación matemática excede el valor máximo que un tipo primitivo puede almacenar en memoria (para un `int` de 32 bits es 2.147.483.647). Si restamos `Integer.MAX_VALUE - (-1)`, el resultado real de la PC se "da la vuelta" y pasa a ser un número negativo largo (`-2147483648`). Esto rompe el contrato de `Comparator` porque le dice a Java que el primer número es menor que el segundo, desordenando todo de forma errática. `Integer.compare()` no sufre este problema porque no resta; usa operadores lógicos relacionales puros (`<`, `>`).

---

##  PARTE 4 — Integración con Spring Boot

### Ejercicio 8: Service con patrón Strategy
* **Pregunta 5:** ¿Qué patrón de diseño estás aplicando al usar un Map<String, Comparator<T>> en lugar de un switch?
* **Respuesta:** Estamos aplicando el **Patrón Strategy (Estrategia)**. En vez de usar lógica procedimental rígida (`switch` o `if-else`), encapsulamos los comparadores como estrategias intercambiables dentro de un mapa polimórfico. Esto cumple con el principio Abierto/Cerrado (OCP), ya que si mañana agregamos un nuevo tipo de orden, basta con registrarlo en el mapa sin alterar el flujo principal de ejecución del servicio.