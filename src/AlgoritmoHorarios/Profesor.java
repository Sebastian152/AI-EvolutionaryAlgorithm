/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgoritmoHorarios;

/**
 *
 * @author Sebastián G.
 */

public class Profesor {
    private String nombre;
    private String materia;
    private int prioridad;
    
    public Profesor(String nombre, String materia, int prioridad) {
        this.nombre = nombre;
        this.materia = materia;
        this.prioridad = prioridad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getMateria() {
        return materia;
    }
    
    public int getPrioridad() {
        return prioridad;
    }
}
