/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgoritmoHorarios;

/**
 *
 * @author Sebastián G.
 */

public class Clase {
    private String salon;
    private String horario;
    private Profesor profesor;
    
    public Clase(String salon, String horario, Profesor profesor) {
        this.salon = salon;
        this.horario = horario;
        this.profesor = profesor;
    }
    
    public String getSalon() {
        return salon;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public Profesor getProfesor() {
        return profesor;
    }
    
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}