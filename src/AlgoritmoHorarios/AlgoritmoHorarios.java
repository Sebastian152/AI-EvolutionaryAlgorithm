/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgoritmoHorarios;

/**
 *
 * @author Sebastián G.
 */

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoHorarios {
    // Parámetros del algoritmo
    private static final double TASA_MUTACION = 0.01;
    private static final int TAMANO_POBLACION = 100;
    private static final int GENERACIONES_MAX = 1000;
    
    // Datos de entrada
    private static List<Profesor> profesores = new ArrayList<>();
    private static List<String> salones = new ArrayList<>();
    private static List<String> horarios = new ArrayList<>();
    
    public static void main(String[] args) {
        inicializarDatos();
        Poblacion poblacion = new Poblacion(TAMANO_POBLACION, true);
        
        for (int generacion = 0; generacion < GENERACIONES_MAX; generacion++) {
            poblacion = Algoritmo.evolucionarPoblacion(poblacion);
            System.out.println("Generación " + generacion + ": Mejor fitness = " + poblacion.getMasApto().getFitness());
            
            if (poblacion.getMasApto().getFitness() == 1.0) {
                System.out.println("Solución óptima encontrada en generación " + generacion);
                break;
            }
        }
        
        // Mostrar el mejor horario encontrado
        System.out.println("\nMejor horario encontrado:");
        Horario mejorHorario = poblacion.getMasApto();
        mejorHorario.mostrarHorario();
    }
    
    private static void inicializarDatos() {
        // Profesores de Matemáticas
        profesores.add(new Profesor("W", "Matemáticas", 1));
        profesores.add(new Profesor("X", "Matemáticas", 2));
        profesores.add(new Profesor("Y", "Matemáticas", 3));
        
        // Profesores de Física
        profesores.add(new Profesor("A", "Física", 1));
        profesores.add(new Profesor("B", "Física", 2));
        profesores.add(new Profesor("C", "Física", 3));
        
        // Profesores de Programación
        profesores.add(new Profesor("F", "Programación", 1));
        profesores.add(new Profesor("G", "Programación", 2));
        profesores.add(new Profesor("H", "Programación", 3));
        
        // Salones disponibles
        salones.add("Salón 1");
        salones.add("Salón 2");
        salones.add("Salón 3");
        
        // Horarios disponibles (7-9 am, Lunes-Miércoles)
        horarios.add("Lunes 7");
        horarios.add("Lunes 8");
        horarios.add("Lunes 9");
        horarios.add("Martes 7");
        horarios.add("Martes 8");
        horarios.add("Martes 9");
        horarios.add("Miércoles 7");
        horarios.add("Miércoles 8");
        horarios.add("Miércoles 9");
    }
    
    public static List<Profesor> getProfesores() {
        return profesores;
    }
    
    public static List<String> getSalones() {
        return salones;
    }
    
    public static List<String> getHorarios() {
        return horarios;
    }
    
    public static double getTasaMutacion() {
        return TASA_MUTACION;
    }
}
