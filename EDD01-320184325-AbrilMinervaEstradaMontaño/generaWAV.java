import java.io.*;

/**
 * Clase para generar archivos WAV a partir de señales senoidales.
 */
public class GeneraWAV {
    /**
     * Genera un archivo WAV con una señal senoidal.
     *
     * @param nombre             Nombre del archivo a generar.
     * @param iTiempo            Tiempo de duración en segundos.
     * @param iFrecuenciaMuestreo Frecuencia de muestreo en Hz.
     * @param iArmonico          Armónico de la señal.
     * @throws IOException Si ocurre un error de E/S.
     */
    public void escribe(String nombre, int iTiempo, int iFrecuenciaMuestreo, int iArmonico)
            throws IOException {
        if (nombre == null) {
            throw new NullPointerException("El nombre del archivo no puede ser nulo.");
        }
        if (iTiempo <= 0 || iFrecuenciaMuestreo <= 0 || iArmonico <= 0) {
            throw new IllegalArgumentException("Los valores de tiempo, frecuencia de muestreo y armónico deben ser mayores que cero.");
        }

        byte[] audioData = new byte[iTiempo * iFrecuenciaMuestreo * 2];

        for (int t = 0; t < iTiempo * iFrecuenciaMuestreo; t++) {
            double angle = 2.0 * Math.PI * t / (iFrecuenciaMuestreo / iArmonico);
            short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
            audioData[2 * t] = (byte) (sample & 0xFF);
            audioData[2 * t + 1] = (byte) ((sample >> 8) & 0xFF);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(nombre))) {
            fileOutputStream.write(new byte[] { 'R', 'I', 'F', 'F' });
            fileOutputStream.write(intToByteArray(36 + audioData.length), 0, 4);
            fileOutputStream.write(new byte[] { 'W', 'A', 'V', 'E', 'f', 'm', 't', ' ' });
            fileOutputStream.write(intToByteArray(16), 0, 4);
            fileOutputStream.write(shortToByteArray((short) 1), 0, 2);
            fileOutputStream.write(shortToByteArray((short) 1), 0, 2);
            fileOutputStream.write(intToByteArray(iFrecuenciaMuestreo), 0, 4);
            fileOutputStream.write(intToByteArray(iFrecuenciaMuestreo * 2), 0, 4);
            fileOutputStream.write(shortToByteArray((short) 2), 0, 2);
            fileOutputStream.write(shortToByteArray((short) 16), 0, 2);
            fileOutputStream.write(new byte[] { 'd', 'a', 't', 'a' });
            fileOutputStream.write(intToByteArray(audioData.length), 0, 4);
            fileOutputStream.write(audioData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] intToByteArray(int value) {
        return new byte[] { (byte) (value), (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24) };
    }

    private byte[] shortToByteArray(short value) {
        return new byte[] { (byte) (value), (byte) (value >> 8) };
    }

    /**
     * Método de inicio del programa.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        GeneraWAV generador = new GeneraWAV();

        try {
            generador.escribe("senoidal.wav", 5, 44100, 440);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
