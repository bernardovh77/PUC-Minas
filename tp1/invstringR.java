import java.util.Scanner;

public class invstringR {

    static void imprimeInvertido(String s, int i) {  // metodo recursivo pra imprimir a string invertida

if(i<=0) return;
    System.out.print(s.charAt(i-1));
imprimeInvertido(s, i-1);
    }

  public static void main(String[] args){
Scanner scanner = new Scanner(System.in);

while (scanner.hasNextLine()){  // loop ate EOF
String linha = scanner.nextLine();
if(linha.length()>=3 && linha.charAt(0)=='F' && linha.charAt(1)=='I' && linha.charAt(2)=='M') break;  // checa se a linha e FIM para acabar o programa

   imprimeInvertido(linha, linha.length());     // chama o metodo recursivo pra printar a string invertida
      System.out.println();

  }

  scanner.close();
  }

}
