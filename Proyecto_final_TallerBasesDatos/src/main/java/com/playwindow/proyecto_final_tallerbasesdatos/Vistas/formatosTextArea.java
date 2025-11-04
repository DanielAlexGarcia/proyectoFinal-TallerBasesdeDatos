/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Vistas;

import java.text.ParseException;
import javax.swing.text.*;
import java.util.regex.Pattern;
import javax.swing.JFormattedTextField;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author daniel
 */
public class formatosTextArea {
    // --- MÉTODOS DE APLICACIÓN DE FILTRO BÁSICO ---

    /**
     * Limita el JTextArea a solo letras y un número máximo de caracteres.
     * @param textArea El JTextArea a formatear.
     * @param maxChars El límite de caracteres.
     */
    public static void setSoloLetras(JTextArea textArea, int maxChars) {
        // [a-zA-Z\\s]+ permite letras mayúsculas, minúsculas y espacios.
        applyDocumentFilter(textArea, new MaxCharDocumentFilter(maxChars, "[a-zA-Z\\s]*"));
    }

    /**
     * Limita el JTextArea a un número máximo de caracteres, permitiendo cualquier tipo.
     * @param textArea El JTextArea a formatear.
     * @param maxChars El límite de caracteres.
     */
    public static void setMaxCaracteres(JTextArea textArea, int maxChars) {
        // null en el regexString permite cualquier carácter.
        applyDocumentFilter(textArea, new MaxCharDocumentFilter(maxChars, null));
    }
    
    public static void FormatoTelefono(JFormattedTextField textField) {
        try {
            // La máscara: ## (código de país o prefijo) + (separador) 
            // tres grupos de 3, 3, y 4 dígitos.
            MaskFormatter mascara = new MaskFormatter("##+ ### ### ####");
            mascara.setPlaceholderCharacter('_'); // Carácter para campos vacíos
            
            textField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
                @Override
                public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                    return mascara;
                }
            });
            
        } catch (ParseException e) {
            System.err.println("Error al crear la máscara de teléfono: " + e.getMessage());
        }
    }
    
    public static void FormatoFecha(JFormattedTextField textField) {
        try {
            // La máscara: 4 dígitos (Año), guión, 2 dígitos (Mes), guión, 2 dígitos (Día)
            MaskFormatter mascara = new MaskFormatter("####-##-##");
            mascara.setPlaceholderCharacter('0'); // Sugerir el '0' para campos vacíos
            
            textField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
                @Override
                public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                    return mascara;
                }
            });
            
        } catch (ParseException e) {
            System.err.println("Error al crear la máscara de fecha: " + e.getMessage());
        }
    }
    
    public static void FormatoHora(JFormattedTextField textField) {
        try {
            // La máscara: 2 dígitos (Hora), dos puntos, 2 dígitos (Minutos)
            MaskFormatter mascara = new MaskFormatter("##:##");
            mascara.setPlaceholderCharacter('0'); // Sugerir el '0' para campos vacíos
            
            textField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
                @Override
                public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                    return mascara;
                }
            });
            
        } catch (ParseException e) {
            System.err.println("Error al crear la máscara de hora: " + e.getMessage());
        }
    }
    
    // --- MÉTODO PRIVADO PARA APLICAR EL FILTRO ---

    /**
     * Método auxiliar para aplicar un DocumentFilter a un JTextArea.
     * @param textArea El componente de texto.
     * @param filter El DocumentFilter a aplicar.
     */
    private static void applyDocumentFilter(JTextArea textArea, DocumentFilter filter) {
        AbstractDocument doc = (AbstractDocument) textArea.getDocument();
        doc.setDocumentFilter(filter);
    }
}




class MaxCharDocumentFilter extends DocumentFilter {
    private int maxCharacters;
    private Pattern allowedPattern;

    /**
     * Constructor principal.
     * @param maxCharacters El número máximo de caracteres permitidos.
     * @param regexString La expresión regular que define los caracteres permitidos. 
     * Si es null o vacío, permite cualquier carácter.
     */
    public MaxCharDocumentFilter(int maxCharacters, String regexString) {
        this.maxCharacters = maxCharacters;
        if (regexString != null && !regexString.isEmpty()) {
            // Se usa el patrón para verificar que *todos* los caracteres a insertar coincidan
            this.allowedPattern = Pattern.compile(regexString);
        } else {
            this.allowedPattern = null; // No hay restricción de caracteres
        }
    }

    /**
     * Aplica el límite de caracteres y el filtro de patrón.
     */
    protected boolean check(FilterBypass fb, String text) {
        // 1. Límite de caracteres
        if ((fb.getDocument().getLength() + text.length()) > maxCharacters) {
            // El documento ya tiene la longitud máxima o la superará.
            return false;
        }

        // 2. Filtro de patrón (si existe)
        if (allowedPattern != null) {
            if (!allowedPattern.matcher(text).matches()) {
                // El texto a insertar contiene caracteres no permitidos
                return false;
            }
        }
        
        return true;
    }

    // --- Sobrescritura de métodos para manejar las mutaciones del Documento ---

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (check(fb, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        // Primero, se obtiene el texto resultante de la operación de reemplazo
        Document doc = fb.getDocument();
        String currentText;
        try {
             currentText = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
             currentText = "";
        }
        
        // Se simula el reemplazo para ver la longitud final
        StringBuilder sb = new StringBuilder(currentText);
        sb.replace(offset, offset + length, text);

        // Se verifica la longitud después de la operación (el DocumentFilter
        // se aplica sobre el texto A INSERTAR/REEMPLAZAR, no sobre el Documento final, 
        // por eso verificamos la longitud primero con el StringBuilder)
        if (sb.length() > maxCharacters) {
            return; // No permite el reemplazo si excede el límite
        }
        
        // Se verifica el patrón solo para el texto a insertar.
        // Los caracteres existentes ya se consideran válidos.
        if (check(fb, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}



