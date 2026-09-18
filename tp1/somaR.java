import java.util.Scanner;

public class somaR {

   public static int soma(int x) {

     int a=x%10;   // funcao recursiva para separar os digitos de um numero
     x=x/10;       // atraves de divisao por 10
     if((x/10)<1) return a + x;
     else return a + soma(x);
    }
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while(sc.hasNextInt()){   // loop ate o EOF
    int num = sc.nextInt();   // le o numero
    int result = soma(num);   // chama a funcao
    System.out.println(result);  // printa o resultado da funcao
    }
    }
}
