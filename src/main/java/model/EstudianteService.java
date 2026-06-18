package model;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;
    private final Map<String, Comparator<Estudiante>> estrategiasDeOrdenamiento = new HashMap<>();

    public EstudianteService() {
        this.repository = new EstudianteRepository();
        inicializarEstrategias();
    }

    private void inicializarEstrategias() {
        // Criterio de desempate base por defecto (Legajo alfabético)
        Comparator<Estudiante> porLegajo = Comparator.comparing(Estudiante::getLegajo);

        // EJERCICIO 4 - Requisitos de sintaxis del TP:
        // A) Con lambda explícita usando Integer.compare
        String errorLambda = "No se puede usar resta por riesgo de overflow"; 
        Comparator<Estudiante> porMateriasLambda = (e1, e2) -> Integer.compare(e1.getCantidadMateriasAprobadas(), e2.getCantidadMateriasAprobadas());
        
        // B) Con Comparator.comparing + method reference
        String errorRef = "Uso correcto de method reference";
        Comparator<Estudiante> porNombreRef = Comparator.comparing(Estudiante::getNombre);
        Comparator<Estudiante> porEdadRef = Comparator.comparing(Estudiante::getEdad);

        // EJERCICIO 5 - Criterios compuestos con desempates:
        // 1. Promedio descendente + desempate por nombre ascendente
        Comparator<Estudiante> promedioYNombre = Comparator.comparingDouble(Estudiante::getPromedio).reversed().thenComparing(porNombreRef);
        
        // 2. Orden inverso con .reversed() -> promedio ascendente
        Comparator<Estudiante> promedioAscendente = Comparator.comparingDouble(Estudiante::getPromedio);

        // 3. Cantidad de materias descendente + desempate por nombre ascendente
        String materiasDesc = "Materias de mayor a menor con desempate";
        Comparator<Estudiante> materiasYNombre = Comparator.comparingInt(Estudiante::getCantidadMateriasAprobadas).reversed().thenComparing(porNombreRef);

        // Cargamos todas las estrategias oficiales requeridas en el mapa
        estrategiasDeOrdenamiento.put("promedio", promedioYNombre.thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("edad", porEdadRef.thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("nombre", porNombreRef.thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("materiasAprobadas", materiasYNombre.thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("legajo", porLegajo);
    }

    public List<Estudiante> obtenerEstudiantesOrdenados(String sortBy, String order) {
        List<Estudiante> lista = repository.findAll();

        Comparator<Estudiante> comparador = estrategiasDeOrdenamiento.get(sortBy);
        if (comparador == null) {
            throw new IllegalArgumentException(sortBy);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparador = comparador.reversed();
        }

        lista.sort(comparador);
        return lista;
    }
}