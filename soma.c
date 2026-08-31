#include <stdio.h>
#include <stdlib.h>

int main(){

  int num=0;
  int a,b,c;  // declaração das variaveis
  int soma=0;
  while(scanf("%d",&num)!=EOF){  // loop ate EOF

  a=num%10;
  num=num/10;
  b=num%10;     // operação para extrair os digitos do numero inserido
  num=num/10;
  c=num%10;

  soma=a+b+c; // soma dos digitos extraidos
  printf("%d\n",soma); // print da soma
  }
  return 0;
}
