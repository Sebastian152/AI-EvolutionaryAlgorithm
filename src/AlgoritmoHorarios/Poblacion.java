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
import java.util.Random;

public class Poblacion {
    private ArrayList<Horario> horarios;
    
    public Poblacion(int tamano, boolean inicializar) {
        horarios = new ArrayList<>();
        
        if (inicializar) {
            for (int i = 0; i < tamano; i++) {
                Horario nuevoHorario = new Horario();
                nuevoHorario = generarHorarioAleatorio(nuevoHorario);
                horarios.add(nuevoHorario);
            }
        }
    }
    
    private Horario generarHorarioAleatorio(Horario horario) {
        Random rand = new Random();
        
        for (int i = 0; i < horario.getTamano(); i++) {
            Clase claseOriginal = horario.getClase(i);
            
            // Seleccionar un profesor aleatorio
            Profesor profesor = AlgoritmoHorarios.getProfesores().get(
                rand.nextInt(AlgoritmoHorarios.getProfesores().size()));
            
            Clase nuevaClase = new Clase(claseOriginal.getSalon(), claseOriginal.getHorario(), profesor);
            horario.setClase(i, nuevaClase);
        }
        
        return horario;
    }
    
    public Horario getMasApto() {
        Horario masApto = horarios.get(0);
        
        for (Horario horario : horarios) {
            if (horario.getFitness() > masApto.getFitness()) {
                masApto = horario;
            }
        }
        
        return masApto;
    }
    
    public Horario torneoSeleccion(int tamanoTorneo) {
        Poblacion torneo = new Poblacion(tamanoTorneo, false);
        Random rand = new Random();
        
        for (int i = 0; i < tamanoTorneo; i++) {
            int idAleatorio = rand.nextInt(horarios.size());
            torneo.horarios.add(horarios.get(idAleatorio));
        }
        
        return torneo.getMasApto();
    }
    
    public ArrayList<Horario> getHorarios() {
        return horarios;
    }
}
