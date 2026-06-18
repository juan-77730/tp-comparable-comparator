import model.Estudiante;
import java.util.Comparator;

public class PruebaOrdenamiento {
    public static void main(String[] args) {
        // Alumnos de prueba para provocar el overflow del Ejercicio 6
        Estudiante e1 = new Estudiante("LU-01", "Alumno Max", 10.0, Integer.MAX_VALUE, 20);
        Estudiante e2 = new Estudiante("LU-02", "Alumno Negativo", 4.0, -1, 10);

        //  EL ANTI-PATRÓN DE LA RESTA TRAMPOSA
        Comparator<Estudiante> restaTramposa = (a, b) -> a.getEdad() - b.getEdad();
        int resultadoResta = restaTramposa.compare(e1, e2);
        
        System.out.println("--- EJERCICIO 6: PRUEBA DE LA RESTA TRAMPOSA ---");
        System.out.println("Resultado matemático esperado: 2147483647 - (-1) = 2147483648");
        System.out.println("Resultado REAL obtenido (Overflow por bits): " + resultadoResta);
        
        if (resultadoResta < 0) {
            System.out.println(" ERROR DETECTADO: Dice que Alumno Max es MENOR que Alumno Negativo debido al desbordamiento.");
        }

        //  LA CORRECCIÓN CON INTEGER.COMPARE
        Comparator<Estudiante> correccionCorrecta = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());
        int resultadoCorreccion = correccionCorrecta.compare(e1, e2);
        
        System.out.println("\n--- SOLUCIÓN CON INTEGER.COMPARE ---");
        System.out.println("Resultado de la comparación binaria segura: " + resultadoCorreccion);
        if (resultadoCorreccion > 0) {
            System.out.println(" ÉXITO: El orden es el correcto y seguro.");
        }
    }
}