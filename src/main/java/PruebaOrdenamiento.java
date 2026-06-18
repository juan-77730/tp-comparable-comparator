import model.Estudiante;
import java.util.Comparator;

public class PruebaOrdenamiento {
    public static void main(String[] args) {
        // Alumnos de prueba requeridos con Integer.MAX_VALUE y -1
        Estudiante e1 = new Estudiante("LU-999", "Alumno Mayor Edad", 9.0, Integer.MAX_VALUE, 20);
        Estudiante e2 = new Estudiante("LU-000", "Alumno Menor Edad", 6.0, -1, 10);

        //  DEMOSTRACIÓN DEL FALLO: El anti-patrón de la resta
        Comparator<Estudiante> restaTramposa = (a, b) -> a.getEdad() - b.getEdad();
        int resultadoResta = restaTramposa.compare(e1, e2);
        
        System.out.println("=== PRUEBA PRÁCTICA EJERCICIO 6 ===");
        System.out.println("Operación: Integer.MAX_VALUE - (-1)");
        System.out.println("Resultado de la resta binaria (Overflow): " + resultadoResta);
        
        if (resultadoResta < 0) {
            System.out.println("❌ CRÍTICO: La resta dio negativa. Java interpretará que el alumno de edad MAX es MENOR.");
        }

        //  SOLUCIÓN SEGURA CON INTEGER.COMPARE
        Comparator<Estudiante> correccionSegura = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());
        int resultadoSeguro = correccionSegura.compare(e1, e2);
        
        System.out.println("\n=== SOLUCIÓN CON COMPARACIÓN LÓGICA ===");
        System.out.println("Resultado de Integer.compare(): " + resultadoSeguro);
        if (resultadoSeguro > 0) {
            System.out.println("✅ ÉXITO: Detecta correctamente el orden sin riesgo de desbordamiento.");
        }
    }
}