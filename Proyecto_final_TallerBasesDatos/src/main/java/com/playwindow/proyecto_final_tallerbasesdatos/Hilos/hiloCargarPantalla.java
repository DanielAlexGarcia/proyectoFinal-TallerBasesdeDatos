/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Hilos;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author daniel
 */
public class hiloCargarPantalla<T> extends SwingWorker<T, Void>{
    // Simulación del diálogo de carga que usas (showMessageDialog)
    private JDialog dialogoDeCarga;
    private Component ventanaPadre;
    
    // La tarea que se ejecutará en segundo plano (simula la consulta a la BD)
    private Supplier<T> tareaEnSegundoPlano;
    
    // El código a ejecutar en el Hilo de Eventos de Swing (EDT) al finalizar (actualiza el panel)
    private Consumer<T> tareaAlFinalizar; 

    /**
     * Constructor para inicializar el CargadorDePanel.
     * @param padre La ventana (JFrame o JInternalFrame) sobre la que aparecerá el diálogo de carga.
     * @param tareaBG La función (Supplier) que ejecuta la lógica larga (ej: la consulta a la BD).
     * @param tareaDone La función (Consumer) que recibe el resultado y actualiza la GUI (ej: setDonador).
     */
    public hiloCargarPantalla(Component padre, Supplier<T> tareaBG, Consumer<T> tareaDone) {
        this.ventanaPadre = padre;
        this.tareaEnSegundoPlano = tareaBG;
        this.tareaAlFinalizar = tareaDone;
        
        // 1. Inicializa el diálogo de carga (simula el showMessageDialog(..., true))
        this.dialogoDeCarga = crearDialogoDeCarga();
    }
    
    // --- Lógica del SwingWorker ---
    
    @Override
    protected T doInBackground() throws Exception {
        // 2. Muestra el diálogo justo antes de empezar (debe hacerse en el EDT)
        SwingUtilities.invokeLater(() -> {
            dialogoDeCarga.setVisible(true);
        });
        
        // Ejecuta la tarea larga (consulta a la BD) en el hilo de fondo
        return tareaEnSegundoPlano.get();
    }

    @Override
    protected void done() {
        // 3. Este método se ejecuta automáticamente en el Hilo de Eventos de Swing (EDT)
        // Oculta/cierra el diálogo de carga (simula el showMessageDialog(..., false))
        dialogoDeCarga.dispose();

        try {
            // Obtiene el resultado de doInBackground()
            T resultado = get();
            
            // 4. Ejecuta la lógica para actualizar la GUI con el resultado
            tareaAlFinalizar.accept(resultado);
            
        } catch (InterruptedException | ExecutionException e) {
            // Manejo de errores durante la ejecución
            JOptionPane.showMessageDialog(ventanaPadre, 
                "Ocurrió un error al cargar los datos: " + e.getMessage(), 
                "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Método auxiliar para el indicador ---

    private JDialog crearDialogoDeCarga() {
        // Crea un JDialog simple para simular el indicador de "Buscando..."
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(ventanaPadre), "Cargando...", Dialog.ModalityType.APPLICATION_MODAL);
        JLabel label = new JLabel("  Buscando datos, por favor espere...  ");
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        dialog.add(label);
        dialog.pack();
        dialog.setLocationRelativeTo(ventanaPadre);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Impide que el usuario lo cierre
        return dialog;
    }
}
