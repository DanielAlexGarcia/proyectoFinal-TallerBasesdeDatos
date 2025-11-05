    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playwindow.proyecto_final_tallerbasesdatos.Vistas;

import java.text.DecimalFormat;
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
    /**
     * Aplica un DocumentFilter a un JTextComponent.
     * Es una función auxiliar necesaria para el uso de los métodos de filtro.
     * @param comp El componente de texto (JTextArea o JTextField)
     * @param filter El DocumentFilter a aplicar.
     */
    private static void applyDocumentFilter(JTextComponent comp, DocumentFilter filter) {
        if (comp.getDocument() instanceof AbstractDocument) {
            AbstractDocument doc = (AbstractDocument) comp.getDocument();
            doc.setDocumentFilter(filter);
        } else {
            System.err.println("El documento del componente no es AbstractDocument, no se puede aplicar el filtro.");
        }
    }

    // --- Métodos Modificados para JTextComponent ---

    /**
     * Limita el JTextComponent (JTextArea/JTextField) a letras (a-z, A-Z) y espacios, con un límite de caracteres.
     * @param textComponent El JTextComponent a formatear (JTextArea o JTextField).
     * @param maxChars El límite máximo de caracteres.
     */
    public static void setSoloLetras(JTextComponent textComponent, int maxChars) {
        // [a-zA-Z\\s]* permite letras mayúsculas, minúsculas y espacios.
        // Asume que MaxCharDocumentFilter ya ha sido definido.
        applyDocumentFilter(textComponent, new MaxCharDocumentFilter(maxChars, "[a-zA-Z\\s]*"));
    }

    /**
     * Limita el JTextComponent (JTextArea/JTextField) a un número máximo de caracteres, permitiendo cualquier tipo.
     * @param textComponent El JTextComponent a formatear (JTextArea o JTextField).
     * @param maxChars El límite de caracteres.
     */
    public static void setMaxCaracteres(JTextComponent textComponent, int maxChars) {
        // null en el regexString permite cualquier carácter.
        // Asume que MaxCharDocumentFilter ya ha sido definido.
        applyDocumentFilter(textComponent, new MaxCharDocumentFilter(maxChars, null));
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
    public static void FormatoSalario(JFormattedTextField textField) {
    try {
        // 1. Definir el patrón decimal (5 enteros, 2 decimales)
        // # se usa para dígitos opcionales; 0 para dígitos obligatorios.
        // #####.00 asegura que siempre haya 2 decimales, y un máximo de 5 enteros.
        DecimalFormat formatoDecimal = new DecimalFormat("#####.00");
        formatoDecimal.setParseIntegerOnly(false); // Permitir decimales

        // 2. Crear el NumberFormatter con el patrón
        NumberFormatter formatter = new NumberFormatter(formatoDecimal);
        
        // 3. Establecer límites (opcional pero bueno para forzar el tipo de datos)
        // Aunque el DecimalFormat define el patrón, el setMaximum establece el valor máximo parseable.
        // Un valor máximo de 99999.99 asegura que se respeta el límite de 5 enteros.
        formatter.setMaximum(99999.99); 
        formatter.setMinimum(0.00); 
        
        // 4. Aplicar el FormatterFactory
        textField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                return formatter;
            }
        });
        
        // Opcional: Establecer un valor inicial y alineación
        textField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        textField.setValue(0.00); 

    } catch (Exception e) {
        System.err.println("Error al crear el formato de salario: " + e.getMessage());
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



