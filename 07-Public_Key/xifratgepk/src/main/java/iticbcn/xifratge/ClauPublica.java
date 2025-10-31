package iticbcn.xifratge;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;



public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception{
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(2048);
        KeyPair parell = generador.generateKeyPair();
        return parell;
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception{
        Cipher xifrador = Cipher.getInstance("RSA");
        xifrador.init(Cipher.ENCRYPT_MODE, clauPublica);
        byte[] msgXifrat = xifrador.doFinal(msg.getBytes());
        return msgXifrat;

    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada) throws Exception{
        Cipher desxifrador = Cipher.getInstance("RSA");
        desxifrador.init(Cipher.DECRYPT_MODE, ClauPrivada);
        byte[] msgDesxifrat = desxifrador.doFinal(msgXifrat);
        return new String(msgDesxifrat);
    }





}
