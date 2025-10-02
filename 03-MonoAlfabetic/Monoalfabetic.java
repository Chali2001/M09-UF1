import java.util.Random;

public class Monoalfabetic {
    public static final char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWYXZÀÁÄÈÉËÌÍÏÒÓÖÙÚÜÇN".toCharArray();

    public static char[] alfabetPermutat = permutaAlfabet(alfabet);

    public static char[] permutaAlfabet(char[] alfabet) {
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

    private static int indexOf(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) return i;
        }
        return -1;
    }

    public static String xifraMonoAlfa(String cadena) {
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

    public static String desxifraMonoAlfa(String cadena) {
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

    public static void main(String[] args) {
        String text = "Perdó, per tu què és?";
        String textXifrat = xifraMonoAlfa(text);
        String textDesXifrat = desxifraMonoAlfa(textXifrat);
        System.out.println("Text Original: " + text);
        System.out.println("Text Xifrat: " + textXifrat);
        System.out.println("Text DesXifrat: " + textDesXifrat);
    }

}
