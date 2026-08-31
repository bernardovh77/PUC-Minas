import java.util.Scanner;
import java.util.Random;

public class alteracao{

    
    
  
    static String mudar (String s) {  // sorteia duas letras minusculas e troca a primeira pela segunda

        Random rand = new Random();

        char original = (char) ('a' + rand.nextInt(26));   // sorteia as duas letras

        char nova = (char) ('a' + rand.nextInt(26));

        int tamanho = s.length();
        char[] resultado = new char[tamanho];

        for (int i = 0; i < tamanho; i++) {  // percorre a string copiando cada caractere trocando quando bate com original

            char c = s.charAt(i);
            if (c == original) {
                resultado[i] = nova;
            } else {
                resultado[i] = c;
            }
        }

        return new String(resultado);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {  // loop ate o EOF
            String linha = scanner.nextLine();

            boolean Fim = (linha.length() == 3);  // checa se a linha e FIM para parar o programa

            if (Fim) {
                for (int i = 0; i < 3; i++) {
                    if (linha.charAt(i) != "FIM".charAt(i)) {
                        Fim = false;
                        break;
                    }
                }
            }

            if (Fim) {
                break;
            }

            String linhamud = mudar(linha);
            System.out.println(linhamud);
        }

        scanner.close();
    }
}
