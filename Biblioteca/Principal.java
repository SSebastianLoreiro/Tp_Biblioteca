package Biblioteca;

import Biblioteca.UI.MenuBiblioteca;
import Biblioteca.model.Libro;
import Biblioteca.model.Estudiante;
import Biblioteca.services.BibliotecaService;

public class Principal {
    public static void main(String[] args) {
        System.out.println("Cargando base de datos del sistema...");

        // 1. Creamos el motor lógico (Service)
        BibliotecaService service = new BibliotecaService();

        // 2. Cargamos los 5 libros de prueba que pide el TP
        service.agregarLibro(new Libro("111-A", "Java a Fondo", "Pablo Sznajdleder", 2020));
        service.agregarLibro(new Libro("222-B", "Patrones de Diseño", "GoF", 1994));
        service.agregarLibro(new Libro("333-C", "Clean Code", "Robert C. Martin", 2008));
        service.agregarLibro(new Libro("444-D", "Estructuras de Datos", "Luis Joyanes", 2015));
        service.agregarLibro(new Libro("555-E", "Fisica Universitaria", "Sears Zemansky", 2013));

        // 3. Cargamos los 3 estudiantes de prueba
        service.registrarEstudiante(
                new Estudiante("1001", "Sergio Sebastian Loreiro", "Sistemas", "sergio@unlar.edu.ar"));
        service.registrarEstudiante(new Estudiante("1002", "Ana Garcia", "Abogacía", "ana@unlar.edu.ar"));
        service.registrarEstudiante(new Estudiante("1003", "Luis Perez", "Medicina", "luis@unlar.edu.ar"));

        // 4. Le pasamos el Service ya cargado a la UI
        MenuBiblioteca menu = new MenuBiblioteca(service);

        // 5. Arrancamos el programa
        menu.iniciar();
    }
}