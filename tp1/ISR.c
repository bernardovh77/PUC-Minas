#include <stdio.h>
#include <stdlib.h>

int vogais(char vog[], int x, int i){ // funcao recursiva para checar se e composto somente por vogais

if(i==x) return 1;

if(vog[i]<'a' || vog[i]>'z'){
    if(vog[i]<'A' || vog[i]>'Z') return 0;
}
if(vog[i]!='a'&& vog[i]!='A'&& vog[i]!='e'&& vog[i]!='E'&& vog[i]!='i'&& vog[i]!='I'&& vog[i]!='o'&& vog[i]!='O'&& vog[i]!='u'&& vog[i]!='U'){
  return 0;
}

return vogais(vog,x,i+1);
}

int consoantes(char con[], int x, int i){ // funcao recursiva para checar se e composto somente por consoantes

if(i==x) return 1;

if(con[i]<'a' || con[i]>'z'){
    if(con[i]<'A' || con[i]>'Z') return 0;
}
if(con[i]=='a'|| con[i]=='A'|| con[i]=='e'|| con[i]=='E'|| con[i]=='i'|| con[i]=='I'|| con[i]=='o'|| con[i]=='O'|| con[i]=='u'|| con[i]=='U'){
  return 0;
}

return consoantes(con,x,i+1);
}

int inteiro(char itr[], int x, int i){ // funcao recursiva para checar se e um numero inteiro

if(i==x) return 1;
if(itr[i]<'0' || itr[i]>'9'){
  return 0;
}

return inteiro(itr,x,i+1);
}

int real(char rea[], int x, int i, int a){ // funcao recursiva para checar se e um numero real

if(i==x) return 1;
if(rea[i]=='.'|| rea[i]==','){
 a++;
 if(a>1) return 0;
}
else if(rea[i]<'0' || rea[i]>'9'){
  return 0;
}

return real(rea,x,i+1,a);
}

int main(){
char linha[1024];  // declaração das variáveis

while(fgets(linha,sizeof(linha),stdin)!=NULL){ // loop ate EOF, lendo a linha inteira

int y=0;
while(linha[y]!='\0' && linha[y]!='\n' && linha[y]!='\r'){ // conta o tamanho da linha
    y++;
}
linha[y]='\0'; // remove o '\n' (ou '\r') do final, se houver

if(y==3 && linha[0]=='F' && linha[1]=='I' && linha[2]=='M') break; // para o programa se a linha for FIM

int X1=0,X2=0,X3=0,X4=0;

if(vogais(linha,y,0)==1) X1=1;       // checagem da string de acordo com os metodos
if(consoantes(linha,y,0)==1) X2=1;
if(inteiro(linha,y,0)==1) X3=1;
if(real(linha,y,0,0)==1) X4=1;


if(X1==1) printf("SIM ");  // printa a saida com base na verificacao dos metodos
else printf("NAO ");

if(X2==1) printf("SIM ");
else printf("NAO ");

if(X3==1) printf("SIM ");
else printf("NAO ");

if(X4==1) printf("SIM\n");
else printf("NAO\n");

}

return 0;
}
