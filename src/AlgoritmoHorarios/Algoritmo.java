/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgoritmoHorarios;

/**
 *
 * @author Sebastián G.
 */

import java.util.Random;

public class Algoritmo {
    public static Poblacion evolucionarPoblacion(Poblacion poblacion) {
        Poblacion nuevaPoblacion = new Poblacion(poblacion.getHorarios().size(), false);
        
        // Elitismo: mantener el mejor horario
        nuevaPoblacion.getHorarios().add(poblacion.getMasApto());
        
        // Cruzamiento
        for (int i = 1; i < poblacion.getHorarios().size(); i++) {
            Horario padre1 = poblacion.torneoSeleccion(5);
            Horario padre2 = poblacion.torneoSeleccion(5);
            Horario hijo = cruzar(padre1, padre2);
            nuevaPoblacion.getHorarios().add(hijo);
        }
        
        // Mutación
        for (int i = 1; i < nuevaPoblacion.getHorarios().size(); i++) {
            nuevaPoblacion.getHorarios().get(i).mutar();
        }
        
        return nuevaPoblacion;
    }
    
    private static Horario cruzar(Horario padre1, Horario padre2) {
        Horario hijo = new Horario();
        Random rand = new Random();
        
        for (int i = 0; i < hijo.getTamano(); i++) {
            if (rand.nextDouble() < 0.5) {
                hijo.setClase(i, padre1.getClase(i));
            } else {
                hijo.setClase(i, padre2.getClase(i));
            }
        }
        
        return hijo;
    }
}
