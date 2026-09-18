import java.util.Scanner;

public class ciframento {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()){  //Loop até o EOF

        String line = sc.nextLine();  // Le a string
       

        char[] chars = new char[line.length()]; // Cria um vetor do tamanho da string lida
 

        for(int i=0; i<line.length() ;i++){ // Transfere o conteudo da string para o vetor
          chars[i]= line.charAt(i);
        }
          if(chars[0]=='F'&& chars[1]=='I'&& chars[2]=='M'){
            return;
          }

        for(int i=0;i<chars.length;i++)  // Adiciona 3 a cada caractere do vetor
        {
           chars[i] = (char) (chars[i]+3);

        }
        System.out.println(new String (chars)); //Transforma o vetor em string e printa
        
          
        }
    }
}
