#include <stdio.h>
#include <stdlib.h>

char adicionar(char vet[],int y,int z){  // funcao recursiva para adicionar 3 a cada caractere do vetor
                                         // um por um
  if(y==(z-1)) return (vet[y]+3);
  printf("%c",vet[y]+3);
    return adicionar(vet,(y+1),z);
 }

int main(){

  char vetor[100]={0};  // declaracao das variaveis
  int i=0,x=0;
while(scanf("%c",&vetor[i])!=EOF){  // loop ate EOF
if(vetor[0]=='F' && vetor[1]== 'I' && vetor[2]== 'M') break;  // checa se o vetor e FIM para parar o codigo
else if(vetor[i]=='\n'){  // ao detectar um enter a funcao e chamada e o vetor decifrado e printado
 printf("%c",adicionar(vetor,x,i));
 printf("\n");
      i=-1;
    }
    i++;
  }
  return 0;
}
