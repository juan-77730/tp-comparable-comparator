package model;

import java.util.Comparator;

public class ComparadorEdad implements Comparator<Estudiante> {
    @Override
    public int compare(Estudiante e1, Estudiante e2) {
        // Orden ascendente: de menor a mayor edad
        return Integer.compare(e1.getEdad(), e2.getEdad());
    }
}