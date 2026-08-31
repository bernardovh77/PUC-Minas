import java.util.Scanner;

public class IS{

    public static boolean vogais(char[] vog,int x){ // funçao para checar se e composto somente por vogais

      for(int i=0;i<x;i++){
        if(vog[i]<'a' || vog[i]>'z'){
            if(vog[i]<'A' || vog[i]>'Z') return false;
        }
        if(vog[i]!='a'&& vog[i]!='A'&& vog[i]!='e'&& vog[i]!='E'&& vog[i]!='i'&& vog[i]!='I'&& vog[i]!='o'&& vog[i]!='O'&& vog[i]!='u'&& vog[i]!='U'){
          return false;
        }
      }
      return true;
    }

    public static boolean consoantes(char[] con,int x){ // funcao para checar se e composto somente por consoantes
      for(int i=0;i<x;i++){
        if(con[i]<'a' || con[i]>'z'){
            if(con[i]<'A' || con[i]>'Z') return false;
        }
        if(con[i]=='a'|| con[i]=='A'|| con[i]=='e'|| con[i]=='E'|| con[i]=='i'|| con[i]=='I'|| con[i]=='o'|| con[i]=='O'|| con[i]=='u'|| con[i]=='U'){
          return false;
        }
      }
      return true;
    }
    public static boolean inteiro(char[] itr,int x){ // funcao para checar se e um numero inteiro
      for(int i=0;i<x;i++){
        if(itr[i]<'0' || itr[i]>'9'){
          return false;
        }
      }
      return true;
    }
     public static boolean real(char[] rea,int x){ // funcao para checar se e um numero real
      int a=0;
      for(int i=0;i<x;i++){
        if(rea[i]=='.'|| rea[i]==','){
         a++;
         if(a>1) return false;
        }
         else if(rea[i]<'0' || rea[i]>'9'){
          return false;
        }
      }
      return true;
    }

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while(scanner.hasNextLine()){ // loop ate EOF

    boolean X1 = false;
    boolean X2 = false;
    boolean X3 = false;
    boolean X4 = false;

    String valor = scanner.nextLine(); // le a string

    char[] val = new char[valor.length()];
    for (int i = 0; i < valor.length(); i++) { // transforma a string em um array de char
        val[i] = valor.charAt(i);
    }
    if(val[0]=='F' && val[1]=='I' && val[2]=='M') return; // para o programa se a string for FIM
    int y = valor.length();
   if(vogais(val,y)==true) X1=true;       // checagem da string de acordo com os metodos
   if(consoantes(val,y)==true) X2=true;
   if(inteiro(val,y)==true) X3=true;
   if(real(val,y)==true) X4=true;


   if(X1==true) System.out.print("SIM ");  // printa a saida com base na verificacao booleana
   else System.out.print("NAO ");

   if(X2==true) System.out.print("SIM ");
   else System.out.print("NAO ");

   if(X3==true) System.out.print("SIM ");
   else System.out.print("NAO ");

   if(X4==true) System.out.println("SIM");
   else System.out.println("NAO");
    }
  }
}
