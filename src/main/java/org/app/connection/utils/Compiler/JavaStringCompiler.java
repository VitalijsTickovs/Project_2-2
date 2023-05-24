package org.app.connection.utils.Compiler;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Arrays;

public class JavaStringCompiler {

    static final String SAVEPATH = new File("").getAbsolutePath() + "/"+"src/main/resources/CompiledScripts";

    public static String getResponse(String filename){
        try{
            String abs = new File("").getAbsolutePath() + "/" + filename;
            abs = abs.replace("\n", "").trim();
            System.out.println(abs);
            String code = readFileAsString(abs);
            compileAndRun(code);
            return "opening program";
        }catch (Exception e){
            System.out.println(e);
            return "Invalid call on application";
        }
    }

    public static void compileAndRun(String code) throws Exception {
        String className = extractClassName(code);

        // Compile the code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileObject file = new JavaSourceFromString(className, code);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(SAVEPATH)));

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

        if (!task.call()) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri());
            }
            return;
        }

        // Load the compiled class
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(SAVEPATH).toURI().toURL()});
        Class<?> clazz = Class.forName(className, true, classLoader);

        // Invoke the 'main' method
        Method mainMethod = clazz.getMethod("main", String[].class);
        String[] mainArgs = {}; // Pass any arguments required by the main method
        mainMethod.invoke(null, (Object) mainArgs);
    }

    private static String extractClassName(String code) {
        String className = "Unknown";
        String[] lines = code.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class ")) {
                className = line.substring(13, line.indexOf('{')).trim();
                break;
            }
        }
        return className;
    }

    // A utility class to convert the string containing Java code to a JavaFileObject
    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        String data = "";
        File file = new File(fileName);
        data = new String(Files.readAllBytes(file.toPath()));
        return data;
    }
}