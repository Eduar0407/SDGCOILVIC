package sdgcoilvic.utilidades;

import java.security.SecureRandom;

public class GeneradorDeContrasenias {
    private static final String CARACTERES_PERMITIDOS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()[]{}<>?/\\|:;,.\\-_+=";

    public static String generarContraseña() {
        String patronContraseña = "^(?=.*[!@#$%&*()\\[\\]{}<>?/\\\\|:;,.\\-_+=])" +
                "[a-zA-Z0-9!@#$%&*()\\[\\]{}<>?/\\\\|:;,.\\-_+=]{8,12}$";

        SecureRandom random = new SecureRandom();
        StringBuilder contraseñaGenerada = new StringBuilder();
        do {
            contraseñaGenerada.setLength(0);
            for (int i = 0; i < 8 + random.nextInt(5); i++) {
                char caracter = CARACTERES_PERMITIDOS.charAt(random.nextInt(CARACTERES_PERMITIDOS.length()));
                contraseñaGenerada.append(caracter);
            }
        } while (!contraseñaGenerada.toString().matches(patronContraseña)); 
        return contraseñaGenerada.toString();
    }
}
