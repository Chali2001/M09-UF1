package iticbcn.xifratge;
import java.util.Random;

public class XifradorMonoalfabetic implements Xifrador {
    public static final char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWYXZÀÁÄÈÉËÌÍÏÒÓÖÙÚÜÇN".toCharArray();

    public char[] alfabetPermutat = permutaAlfabet(alfabet);

    public char[] permutaAlfabet(char[] alfabet) {
        char[] copia = new char[alfabet.length];
        for (int i = 0; i < alfabet.length; i++) {
            copia[i] = alfabet[i];
        }
        Random aleatori = new Random();
        for (int i = copia.length - 1; i > 0; i--) {
            int j = aleatori.nextInt(i + 1);
            char tmp = copia[i];
            copia[i] = copia[j];
            copia[j] = tmp;
        }
        return copia;
    }

    private int indexOf(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) return i;
        }
        return -1;
    }

    public String xifraMonoAlfa(String cadena) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            boolean esMinuscula = Character.isLowerCase(caracter);
            char cMay = Character.toUpperCase(caracter);

            int index = indexOf(alfabet, cMay);
            if (index >= 0) {
                char subMay = alfabetPermutat[index];
                resultat += (esMinuscula ? Character.toLowerCase(subMay) : subMay);
            } else {
                resultat += caracter;
            }
        }
        return resultat;
    }

    public String desxifraMonoAlfa(String cadena) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            boolean esMinuscula = Character.isLowerCase(caracter);
            char cMay = Character.toUpperCase(caracter);

            int index = indexOf(alfabetPermutat, cMay);
            if (index >= 0) {
                char origMay = alfabet[index];
                resultat += (esMinuscula ? Character.toLowerCase(origMay) : origMay);
            } else {
                resultat += caracter;
            }
        }
        return resultat;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null){
            throw new ClauNoSuportada("Xifratje monoalfabètic no soporta clau != null");
        }

        String resultat = xifraMonoAlfa(msg);
        return new TextXifrat(resultat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratge monoalfabètic no suporta clau != null");
        }
        String msg = new String(xifrat.getBytes());
        return desxifraMonoAlfa(msg);
    }
}
