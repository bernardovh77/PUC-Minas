#include <stdio.h>
#include <stdlib.h>

int main(){
char linha[100];

while(scanf("%s",linha)!=EOF){ // loop ate EOF

int y=0;
while(linha[y]!='\0'){ // conta o tamanho da string lida
    y++;
}

if(linha[0]=='F' && linha[1]=='I' && linha[2]=='M') break; // para o programa se a linha for FIM

int ultimaPos[256];  // guarda a ultima posicao onde cada caractere apareceu
for(int k=0;k<256;k++){
    ultimaPos[k]=-1;
}

int x=0;
int maior=0;

for(int i=0;i<y;i++){ // percorre a string

char c = linha[i];

if(ultimaPos[(int)c] >= x){
    x = ultimaPos[(int)c] + 1;
      }

ultimaPos[(int)c] = i;   // atualiza a ultima posicao do caractere

    int tamanho = i - x + 1;
    if(tamanho > maior){
    maior = tamanho;
    }
}

printf("%d\n",maior);  // imprime o comprimento da maior substring sem repeticao

}

return 0;
}
