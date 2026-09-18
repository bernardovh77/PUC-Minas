import java.util.Scanner;

public class validacao {
    
public static boolean verificar(String a){  // funcao que retorna true se a senha for valida

int x=0,y=0,z=0,w=0;  // declaracao de variaveis
      if(a.length()>=8){  // checa se a string tem pelo menos 8 caracteres
        for(int i=0;i<a.length();i++){  // percorre a string fazendo as checagens
          char c =a.charAt(i);
          if(c>= 'A' && c<= 'Z') z=1;
          if(c>= 'a' && c<= 'z') w=1;
          if(c>= '0' && c<= '9') y=1;
          if(c=='#' || c=='&' || c=='%' || c=='$' || c=='@' || c=='!'|| c=='*' || c=='^') x=1;
        }
      }
      if(x==1 && z==1 && w==1 && y==1){  // caso as checagens sejam bem sucedidas retorna true
        return true;
      }
      else return false;

}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while(scanner.hasNextLine()){  // le ate EOF
      String senha = scanner.nextLine();
      if(senha.charAt(0)=='F' && senha.charAt(1)=='I' && senha.charAt(2)=='M') break;  // para ao ler FIM
      else if(verificar(senha)==true) System.out.println("SIM");  // printa a resposta final
      else System.out.println("NAO");
    }
    return;
  }
}
