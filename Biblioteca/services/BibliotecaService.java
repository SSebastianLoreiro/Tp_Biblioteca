package Biblioteca.services;

import Biblioteca.model.*;
import Biblioteca.exceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BibliotecaService {
    private List<Libro> catalogoLibros;
    private Map<String, Estudiante> registroEstudiantes;
    private Set<Prestamo> prestamosActivos;

    // Constructor
    public BibliotecaService() {
        this.catalogoLibros = new ArrayList<>();
        this.registroEstudiantes = new HashMap<>();
        this.prestamosActivos = new HashSet<>();
    }

    // Métodos
    public void agregarLibro(Libro libro) {
        this.catalogoLibros.add(libro);
    }

    public void registrarEstudiante(Estudiante estudiante) {
        this.registroEstudiantes.put(estudiante.getLegajo(), estudiante);
    }

    // 1. Agregamos "throws" (con S) para avisar qué excepciones pueden salir
    // 2. Quitamos la coma y agregamos todas las que lanzas adentro
    public void registrarPrestamo(String ISBN, String legajo)
            throws LibroNoDisponibleException, EstudianteNoEncontradoException, LimitePrestamosExcedidoException {

        // 1. Buscar el libro
        Libro libro = catalogoLibros.stream()
                .filter(l -> l.getIsbn().equals(ISBN) && l.isDisponible())
                .findFirst()
                .orElseThrow(() -> new LibroNoDisponibleException("El libro no está disponible o no existe."));

        // 2. Buscar el estudiante
        Estudiante estudiante = registroEstudiantes.get(legajo);
        if (estudiante == null) {
            throw new EstudianteNoEncontradoException("Estudiante con legajo " + legajo + " no encontrado.");
        }

        // 3. Validar límite
        long cantidadPrestamos = prestamosActivos.stream()
                .filter(p -> p.getEstudiante().getLegajo().equals(legajo))
                .count();

        if (cantidadPrestamos >= 3) {
            throw new LimitePrestamosExcedidoException("El estudiante ya tiene 3 libros en su poder.");
        }

        // 4. Registrar el préstamo
        libro.setDisponible(false);
        Prestamo nuevoPrestamo = new Prestamo(libro, estudiante);
        prestamosActivos.add(nuevoPrestamo);

        System.out.println("Préstamo registrado con éxito: " + libro.getTitulo());
    }
}