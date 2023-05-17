package org.app.connection.utils.web;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.nio.file.*;

public class OpenWebPage {

    public static void open(String fileName){
        try {
            // Leer el contenido HTML del archivo
            String htmlContent = new String(Files.readAllBytes(Paths.get(fileName))).trim();

            // Crear un archivo temporal para mostrar el contenido HTML
            File tempFile = File.createTempFile("html_content", ".html");
            Files.write(Paths.get(tempFile.getAbsolutePath()), htmlContent.getBytes());

            // Comprobar si el Escritorio es compatible
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                // Abrir el archivo HTML en el navegador predeterminado
                Desktop.getDesktop().browse(tempFile.toURI());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Hubo un error al leer el archivo o el contenido HTML no es válido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Hubo un error al abrir el archivo HTML en el navegador: " + e.getMessage());
        }
    }

}
