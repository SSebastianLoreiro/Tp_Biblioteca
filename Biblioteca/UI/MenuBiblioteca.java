package Biblioteca.UI;

import java.util.Scanner;
import java.util.List;
import Biblioteca.model.Libro; // Asumiendo que tenés esta clase
import Biblioteca.services.BibliotecaService;

public class MenuBiblioteca {
    private Scanner teclado = new Scanner(System.in);
    private BibliotecaService service;

    public MenuBiblioteca(BibliotecaService service) {
        this.service = service;
    }

    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN BIBLIOTECARIA ---");
            System.out.println("1. Registrar Préstamo");
            System.out.println("2. Buscar Libro");
            System.out.println("3. Calcular Multa por Retraso"); // Agregamos la multa
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = teclado.nextInt();
            teclado.nextLine(); // <-- ¡CLAVE! Limpia el "Enter" que quedó flotando

            switch (opcion) {
                case 1:
                    ejecutarRegistroPrestamo();
                    break;
                case 2:
                    buscarLibroPorTitulo();
                    break;
                case 3:
                    calcularMultaUI();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private void ejecutarRegistroPrestamo() {
        System.out.print("Ingrese ISBN: ");
        String isbn = teclado.nextLine(); // Cambié next() por nextLine() para evitar cortes
        System.out.print("Ingrese Legajo: ");
        String legajo = teclado.nextLine();

        try {
            service.registrarPrestamo(isbn, legajo);
            System.out.println("Préstamo exitoso.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Implementación del método que faltaba
    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese el título (o parte de él) a buscar: ");
        String titulo = teclado.nextLine();

        List<Libro> encontrados = service.buscarLibroPorTituloOIsbn(titulo);

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            System.out.println("--- Libros Encontrados ---");
            for (Libro lib : encontrados) {
                System.out.println(lib.toString()); // Usa el toString que te pide el TP
            }
        }
    }

    // Implementación de la prueba recursiva
    private void calcularMultaUI() {
        System.out.print("Ingrese la cantidad de días de retraso: ");
        int dias = teclado.nextInt();
        System.out.print("Ingrese el valor del libro: $");
        double valor = teclado.nextDouble();

        double multa = service.calcularMulta(dias, valor);
        System.out.println("La multa a cobrar es de: $" + multa);
    }
}