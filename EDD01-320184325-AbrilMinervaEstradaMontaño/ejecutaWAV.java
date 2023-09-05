import java.io.*;

/**
 * Clase para generar y reproducir archivos WAV.
 */
public class EjecutaWAV {
    /**
     * Método principal para ejecutar el programa.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso incorrecto. Proporcione el nombre del archivo de entrada.");
            return;
        }

        String archivoEntrada = args[0];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada));
            String nombreArchivo = reader.readLine();
            int frecuenciaMuestreo = Integer.parseInt(reader.readLine());
            int frecuenciaDeseada = Integer.parseInt(reader.readLine());
            int tiempoDuracion = Integer.parseInt(reader.readLine());
            reader.close();

            generarYReproducirWAV(nombreArchivo, tiempoDuracion, frecuenciaMuestreo, frecuenciaDeseada);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera y reproduce un archivo WAV.
     *
     * @param nombreArchivo    Nombre del archivo WAV a generar.
     * @param tiempoDuracion   Duración en segundos.
     * @param frecuenciaMuestreo Frecuencia de muestreo en Hz.
     * @param frecuenciaDeseada Frecuencia deseada en Hz.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void generarYReproducirWAV(String nombreArchivo, int tiempoDuracion, int frecuenciaMuestreo, int frecuenciaDeseada)
            throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivo)) {
            // Encabezado del archivo WAV
            escribirString(fileOutputStream, "RIFF"); // ChunkID
            escribirEntero(fileOutputStream, 36 + tiempoDuracion * frecuenciaMuestreo * 2); // ChunkSize
            escribirString(fileOutputStream, "WAVE"); // Format

            // Subchunk 1: Formato PCM
            escribirString(fileOutputStream, "fmt "); // Subchunk1ID
            escribirEntero(fileOutputStream, 16); // Subchunk1Size
            escribirEntero(fileOutputStream, 1); // AudioFormat (PCM)
            escribirEntero(fileOutputStream, 1); // NumChannels (mono)
            escribirEntero(fileOutputStream, frecuenciaMuestreo); // SampleRate
            escribirEntero(fileOutputStream, frecuenciaMuestreo * 2); // ByteRate
            escribirEntero(fileOutputStream, 2); // BlockAlign
            escribirEntero(fileOutputStream, 16); // BitsPerSample

            // Subchunk 2: Datos de audio
            escribirString(fileOutputStream, "data"); // Subchunk2ID
            escribirEntero(fileOutputStream, tiempoDuracion * frecuenciaMuestreo * 2); // Subchunk2Size

            // Datos de audio
            for (int t = 0; t < tiempoDuracion * frecuenciaMuestreo; t++) {
                double angle = 2.0 * Math.PI * t / (frecuenciaMuestreo / frecuenciaDeseada);
                short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
                escribirEntero(fileOutputStream, sample);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reproducirWAV(nombreArchivo);
    }

    /**
     * Reproduce un archivo WAV.
     *
     * @param nombreArchivo Nombre del archivo WAV a reproducir.
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void reproducirWAV(String nombreArchivo) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(nombreArchivo)) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                System.out.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Escribe un entero en un flujo de salida.
     *
     * @param stream Flujo de salida.
     * @param valor  Valor entero a escribir.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void escribirEntero(FileOutputStream stream, int valor) throws IOException {
        stream.write(valor & 0xFF);
        stream.write((valor >> 8) & 0xFF);
        stream.write((valor >> 16) & 0xFF);
        stream.write((valor >> 24) & 0xFF);
    }

    /**
     * Escribe una cadena de caracteres en un flujo de salida.
     *
     * @param stream Flujo de salida.
     * @param cadena Cadena de caracteres a escribir.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void escribirString(FileOutputStream stream, String cadena) throws IOException {
        for (int i = 0; i < cadena.length(); i++) {
            stream.write(cadena.charAt(i));
        }
    }
}
