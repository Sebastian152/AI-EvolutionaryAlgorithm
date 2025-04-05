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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Horario {
    private ArrayList<Clase> clases;
    private double fitness = -1;
    
    public Horario() {
        clases = new ArrayList<>();
        
        // Inicializar con clases vacías
        for (String salon : AlgoritmoHorarios.getSalones()) {
            for (String horario : AlgoritmoHorarios.getHorarios()) {
                clases.add(new Clase(salon, horario, null));
            }
        }
    }
    
    public Horario(ArrayList<Clase> clases) {
        this.clases = clases;
    }
    
    public double getFitness() {
        if (fitness == -1) {
            fitness = calcularFitness();
        }
        return fitness;
    }
    
    private double calcularFitness() {
        int conflictos = 0;
        int totalClases = clases.size();
        
        // Verificar conflictos de profesores
        Map<String, Set<String>> profesorHorarios = new HashMap<>();
        
        for (Clase clase : clases) {
            if (clase.getProfesor() != null) {
                String profesor = clase.getProfesor().getNombre();
                String horario = clase.getHorario();
                
                if (!profesorHorarios.containsKey(profesor)) {
                    profesorHorarios.put(profesor, new HashSet<>());
                }
                
                if (profesorHorarios.get(profesor).contains(horario)) {
                    conflictos++;
                } else {
                    profesorHorarios.get(profesor).add(horario);
                }
            }
        }
        
        // Verificar conflictos de salones
        Map<String, Set<String>> salonHorarios = new HashMap<>();
        
        for (Clase clase : clases) {
            String salon = clase.getSalon();
            String horario = clase.getHorario();
            
            if (!salonHorarios.containsKey(salon)) {
                salonHorarios.put(salon, new HashSet<>());
            }
            
            if (salonHorarios.get(salon).contains(horario)) {
                conflictos++;
            } else {
                salonHorarios.get(salon).add(horario);
            }
        }
        
        // Verificar prioridades de profesores
        for (Clase clase : clases) {
            if (clase.getProfesor() != null) {
                String materia = clase.getProfesor().getMateria();
                int prioridad = clase.getProfesor().getPrioridad();
                
                // Buscar si hay un profesor con mejor prioridad disponible
                for (Profesor prof : AlgoritmoHorarios.getProfesores()) {
                    if (prof.getMateria().equals(materia) && prof.getPrioridad() < prioridad) {
                        // Verificar si el profesor con mejor prioridad está disponible
                        boolean disponible = true;
                        for (Clase c : clases) {
                            if (c.getProfesor() != null && c.getProfesor().getNombre().equals(prof.getNombre()) 
                                && c.getHorario().equals(clase.getHorario())) {
                                disponible = false;
                                break;
                            }
                        }
                        
                        if (disponible) {
                            conflictos++;
                            break;
                        }
                    }
                }
            }
        }
        
        return 1.0 / (1.0 + conflictos);
    }
    
    public int getTamano() {
        return clases.size();
    }
    
    public Clase getClase(int pos) {
        return clases.get(pos);
    }
    
    public void setClase(int pos, Clase clase) {
        clases.set(pos, clase);
        fitness = -1; // Reset fitness al modificar
    }
    
    public void mostrarHorario() {
        // Organizar por salón
        Map<String, List<Clase>> horariosPorSalon = new HashMap<>();
        
        for (Clase clase : clases) {
            if (!horariosPorSalon.containsKey(clase.getSalon())) {
                horariosPorSalon.put(clase.getSalon(), new ArrayList<>());
            }
            horariosPorSalon.get(clase.getSalon()).add(clase);
        }
        
        // Mostrar cada salón
        for (Map.Entry<String, List<Clase>> entry : horariosPorSalon.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            System.out.println("Horario\t\tLunes\t\tMartes\t\tMiércoles");
            
            // Ordenar por horario
            entry.getValue().sort(Comparator.comparing(Clase::getHorario));
            
            // Mostrar en formato de tabla
            for (Clase clase : entry.getValue()) {
                String[] partes = clase.getHorario().split(" ");
                String dia = partes[0];
                String hora = partes[1];
                
                System.out.print(hora + "\t\t");
                
                if (dia.equals("Lunes")) {
                    System.out.print(clase.getProfesor() != null ? clase.getProfesor().getNombre() : "-");
                    System.out.print("\t\t");
                    System.out.print("\t\t");
                } else if (dia.equals("Martes")) {
                    System.out.print("\t\t");
                    System.out.print(clase.getProfesor() != null ? clase.getProfesor().getNombre() : "-");
                    System.out.print("\t\t");
                } else if (dia.equals("Miércoles")) {
                    System.out.print("\t\t");
                    System.out.print("\t\t");
                    System.out.print(clase.getProfesor() != null ? clase.getProfesor().getNombre() : "-");
                }
                
                System.out.println();
            }
        }
    }
    
    public void mutar() {
        Random rand = new Random();
        
        for (int i = 0; i < getTamano(); i++) {
            if (rand.nextDouble() < AlgoritmoHorarios.getTasaMutacion()) {
                // Mutar esta clase
                Clase claseOriginal = getClase(i);
                
                // Seleccionar un profesor aleatorio para la misma materia
                String materia = claseOriginal.getProfesor() != null ? 
                    claseOriginal.getProfesor().getMateria() : 
                    AlgoritmoHorarios.getProfesores().get(rand.nextInt(AlgoritmoHorarios.getProfesores().size())).getMateria();
                
                List<Profesor> profesoresMateria = new ArrayList<>();
                for (Profesor prof : AlgoritmoHorarios.getProfesores()) {
                    if (prof.getMateria().equals(materia)) {
                        profesoresMateria.add(prof);
                    }
                }
                
                if (!profesoresMateria.isEmpty()) {
                    Profesor nuevoProfesor = profesoresMateria.get(rand.nextInt(profesoresMateria.size()));
                    Clase nuevaClase = new Clase(claseOriginal.getSalon(), claseOriginal.getHorario(), nuevoProfesor);
                    setClase(i, nuevaClase);
                }
            }
        }
    }
}
