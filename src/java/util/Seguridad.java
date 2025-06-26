package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguridad {

    /**
     * Genera un hash SHA-256 de una cadena de texto
     * @param texto Texto original (por ejemplo, una contraseña)
     * @return Hash en formato hexadecimal
     */
    public static String hash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(texto.getBytes());

            // Convertir a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash SHA-256", e);
        }
    }
}
