package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante implements Comparable<Estudiante> {
    private String legajo;
    private String nombre;
    private double promedio;
    private int edad;
    private int cantidadMateriasAprobadas;

    @Override
    public int compareTo(Estudiante otro) {
        // Orden natural: por promedio de mayor a menor (descendente)
        // Usamos Double.compare para evitar el anti-patrón de la resta
        return Double.compare(otro.getPromedio(), this.promedio);
    }
}