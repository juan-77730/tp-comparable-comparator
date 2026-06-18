package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    // ✅ Usamos la anotación oficial de Spring en lugar de usar 'new'
    @Autowired
    private EstudianteService service;

    @GetMapping
    public ResponseEntity<List<Estudiante>> getEstudiantes(
            @RequestParam(defaultValue = "promedio") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        
        return ResponseEntity.ok(service.obtenerEstudiantesOrdenados(sortBy, order));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidSortBy(IllegalArgumentException ex) {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("error", "Criterio de ordenamiento no válido");
        errorBody.put("criterioRecibido", ex.getMessage());
        errorBody.put("criteriosAceptados", List.of("promedio", "edad", "nombre", "materiasAprobadas", "legajo"));
        
        return ResponseEntity.badRequest().body(errorBody);
    }
}
