#include <stdio.h>
#include <stdlib.h>

int main(){
char vetor[100]={0};  // declaração das variáveis
  int n=0,i=0;

while (scanf("%c", &vetor[i]) != EOF){  // loop ate EOF
if(vetor[0]=='F' && vetor[1]=='I' && vetor[2]=='M') break;  // checa se a string e FIM para acabar o programa
    n++;
    if(vetor[i]=='\n'){      // checa se o enter foi apertado para que 
                             // a string anterior seja printada 
                             // e uma nova se inicie

   for(int i=(n-1);i>0;i--){     // loop para printar a string invertida
    printf("%c",vetor[i-1]);
  }
      printf("\n");
      n=0;                    // reset das variáveis de contagem
      i=-1;
    }
  i++;

  }
  return 0;
  
}
