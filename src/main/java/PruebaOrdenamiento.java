import model.Estudiante;
import model.EstudianteService;
import java.util.List;

public class PruebaOrdenamiento {
    public static void main(String[] args) {
        EstudianteService service = new EstudianteService();

        System.out.println("--- PRUEBA ENUNCIADO: ORDENADO POR PROMEDIO (DESC) ---");
        List<Estudiante> porPromedio = service.obtenerEstudiantesOrdenados("promedio", "desc");
        for (Estudiante e : porPromedio) {
            System.out.println(e.getNombre() + " - Promedio: " + e.getPromedio() + " - Legajo: " + e.getLegajo());
        }

        System.out.println("\n--- PRUEBA ENUNCIADO: ORDENADO POR EDAD (ASC) ---");
        List<Estudiante> porEdad = service.obtenerEstudiantesOrdenados("edad", "asc");
        for (Estudiante e : porEdad) {
            System.out.println(e.getNombre() + " - Edad: " + e.getEdad());
        }
    }
}