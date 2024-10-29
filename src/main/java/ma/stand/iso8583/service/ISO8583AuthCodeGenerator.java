package ma.stand.iso8583.service;

import java.security.SecureRandom;

public class ISO8583AuthCodeGenerator {
    private static final int AUTH_CODE_LENGTH = 6;
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String ALPHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();

    public static String generateAuthCode(boolean useAlphanumeric) {
        StringBuilder authCode = new StringBuilder(AUTH_CODE_LENGTH);
        String chars = useAlphanumeric ? NUMERIC_CHARS + ALPHA_CHARS : NUMERIC_CHARS;

        for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
            int index = random.nextInt(chars.length());
            authCode.append(chars.charAt(index));
        }

        return authCode.toString();
    }

    /**
     * Generates a numeric-only authorization code for ISO8583 Field 38
     * @return 6-digit authorization code
     */
    public static String generateNumericAuthCode() {
        return generateAuthCode(false);
    }

    /**
     * Generates an alphanumeric authorization code for ISO8583 Field 38
     * @return 6-character alphanumeric authorization code
     */
    public static String generateAlphanumericAuthCode() {
        return generateAuthCode(true);
    }
}