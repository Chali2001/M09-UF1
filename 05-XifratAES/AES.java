import java.security.SecureRandom;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "ITICBCNdam2025";

    public static byte[] xifraAES(String msg, String password) throws Exception {
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        byte[] clauEnBytes = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] clauHash = sha.digest(clauEnBytes);
        SecretKeySpec clau = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

        byte[] msgEnBytes = msg.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, clau, ivParameterSpec);
        byte[] msgXifrat = cipher.doFinal(msgEnBytes);

        byte[] ivIMsgXifrat = new byte[iv.length + msgXifrat.length];
        System.arraycopy(iv, 0, ivIMsgXifrat, 0 , iv.length);
        System.arraycopy(msgXifrat, 0, ivIMsgXifrat, iv.length, msgXifrat.length);

        return ivIMsgXifrat;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String password) throws Exception {
        byte[] iv = new byte[MIDA_IV];
        byte[] msgXifrat = new byte[bIvIMsgXifrat.length - MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, msgXifrat, 0, msgXifrat.length);

        String prueba = "null";
        return prueba;
    }

    public static void main(String[] args) throws Exception {
        byte[] resultat = xifraAES("Hola", CLAU);
        System.out.println(Base64.getEncoder().encodeToString(resultat));
        String resultat2 = desxifraAES(resultat, CLAU);
        System.out.println(resultat2);
    }
}