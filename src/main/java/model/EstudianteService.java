package model;

import java.util.*;

public class EstudianteService {

    private final EstudianteRepository repository;
    private final Map<String, Comparator<Estudiante>> estrategiasDeOrdenamiento = new HashMap<>();

    public EstudianteService() {
        this.repository = new EstudianteRepository();
        inicializarEstrategias();
    }

    private void inicializarEstrategias() {
        // Criterio base para desempatar por legajo alfabéticamente
        Comparator<Estudiante> porLegajo = Comparator.comparing(Estudiante::getLegajo);

        // Mapeamos los comparadores según los requisitos del TP
        estrategiasDeOrdenamiento.put("promedio", Comparator.comparingDouble(Estudiante::getPromedio).reversed().thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("edad", Comparator.comparingInt(Estudiante::getEdad).thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("nombre", Comparator.comparing(Estudiante::getNombre).thenComparing(porLegajo));
        estrategiasDeOrdenamiento.put("materiasAprobadas", Comparator.comparingInt(Estudiante::getCantidadMateriasAprobadas).thenComparing(porLegajo));
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
