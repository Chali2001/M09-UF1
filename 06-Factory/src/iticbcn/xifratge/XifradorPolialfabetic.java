package iticbcn.xifratge;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class XifradorPolialfabetic {
    public static final char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÄÈÉËÌÍÏÒÓÖÙÚÜÇÑ".toCharArray();
    public static String alfabetPermutat = "";
    public static Random random;

    public void initRandom(Long clau){
        random = new Random(clau);
    }

    public String permutaAlfabet() {
        List<Character> llista = new ArrayList<>();
        for (int i = 0; i < alfabet.length; i++) {
            llista.add(alfabet[i]);
        }
        alfabetPermutat = "";
        
        for (int i = 0; i < alfabet.length; i++){
            int index = random.nextInt(llista.size());
            alfabetPermutat += llista.remove(index);
        }
        return alfabetPermutat;
        
    }

    public String xifraPoliAlfa(String msg){
        String resultat = "";
        for (int i = 0; i < msg.length(); i++){
            char c = msg.charAt(i);
            boolean mayuscula = Character.isUpperCase(c);

            if(!Character.isLetter(c)){
                resultat += c;
                continue;
            }
            permutaAlfabet();
            int index = -1;
            if (!mayuscula){
                for (int j = 0; j < alfabet.length; j++) {
                    if(Character.toUpperCase(c) == alfabet[j]){
                        index = j;
                        break;
                    }
                }
                if (index != -1){
                    char novaLletra = alfabetPermutat.charAt(index);
                    resultat += Character.toLowerCase(novaLletra);
                }
            } else {
                for (int j = 0; j < alfabet.length; j++) {
                    if(c == alfabet[j]){
                        index = j;
                        break;
                    }
                }
                if (index != -1){
                    char novaLletra = alfabetPermutat.charAt(index);
                    resultat += novaLletra;
                }
            }

            
        }
        return resultat;
    }

    public String desxifraPoliAlfa(String msg){
        String resultat = "";
        for (int i = 0; i < msg.length(); i++){
            char c = msg.charAt(i);
            boolean mayuscula = Character.isUpperCase(c);

            if(!Character.isLetter(c)){
                resultat += c;
                continue;
            }
            permutaAlfabet();
            int index = -1;
            if (!mayuscula){
                for (int j = 0; j < alfabetPermutat.length(); j++) {
                    if (Character.toUpperCase(c) == alfabetPermutat.charAt(j)){
                        index = j;
                        break;
                    }
                }
                 if (index != -1) {
                    resultat += Character.toLowerCase(alfabet[index]);
                }
            } else {
                for (int j = 0; j < alfabetPermutat.length(); j++) {
                    if (c == alfabetPermutat.charAt(j)){
                        index = j;
                        break;
                    }
                }
                if (index != -1) {
                    resultat += alfabet[index];
                }
            }
        }
        return resultat;
    }
}