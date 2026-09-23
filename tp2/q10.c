#include <stdbool.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>


typedef struct Data{  // struct da data

  int ano;  // variaveis
  int mes;
  int dia;
} Data;

Data parseData(char* s){  // função para extrair informação da data

  Data d;

  sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);

  return d;
}

void formatData(Data d, char* buffer){  // função para formatar datas

  sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);

}

typedef struct Veiculo{  // struct de veiculos

  int id;  // variaveis
  int ano;
  int cilindros;
  double cilindrada;
  double consumoCidade;
  double consumoEstrada;
  double co2;
  Data dataRegistro;
  bool turbo;
  char marca[100];
  char modelo[100];
  char categoria[100];
  char tracao[100];
  char transmissao[100];
  char combustivel[5][30];
} Veiculo;

Veiculo parseVeiculo(char* s){  // função para extrair informações dos veiculos

  Veiculo v;

  char combustivelR[50];
  char turboR[10];
  char dataR[20];

  sscanf(s,"%d,%99[^,],%99[^,],%d,%99[^,],%99[^,],%d,%lf,%99[^,],%99[^,],%lf,%lf,%lf,%99[^,],%99[^,]",
         &v.id, v.marca, v.modelo, &v.ano, v.categoria, combustivelR, &v.cilindros, &v.cilindrada,
         v.transmissao, v.tracao, &v.consumoCidade, &v.consumoEstrada, &v.co2, turboR, dataR);

  v.dataRegistro=parseData(dataR);  // aproveita a função da data

  if (strcmp(turboR, "true") == 0) {  // analisa o texto e atribui valor booleano
    v.turbo = true;
  }
  else {
    v.turbo = false;
  }

  char* token = strtok(combustivelR, ";");  // separa os combustiveis
  int i = 0;
  while (token != NULL) {
    sprintf(v.combustivel[i], "%s", token);
    i++;
    token = strtok(NULL, ";");
  }
  for (int k = i; k < 5; k++) {  // inutiliza as strings de combustivel vazias
    v.combustivel[k][0] = '\0';
  }
  return v;
}

void formatVeiculo(Veiculo v, char* buffer){  // função para formatar as informações dos veiculos

  char n[50];
  formatData(v.dataRegistro, n);

  char tur[10];
  if (v.turbo==true) {  // transforma o booleano em texto
    sprintf(tur, "true");
  } else {
    sprintf(tur, "false");
  }

  char veic[50];
  int pos = 0;
  for (int k = 0; k < 5 && v.combustivel[k][0] != '\0'; k++) {  // formata os combustiveis
    if(k>0) pos+=sprintf(veic+pos,",");
    pos+=sprintf(veic+pos,"%s", v.combustivel[k]);
  }

  sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
          v.id, v.marca, v.modelo, v.ano, v.categoria, veic, v.cilindros, v.cilindrada,
          v.transmissao, v.tracao, v.consumoCidade, v.consumoEstrada, v.co2, tur, n);
}

Veiculo* lerCsv(char* caminhoArquivo, int* n) {

    int contador = 0;

    FILE* arquivo = fopen(caminhoArquivo, "r");
    char linha[500];
    while (fgets(linha, 500, arquivo) != NULL) {  // descobre o tamanho do arquivo
        contador++;
    }
    fclose(arquivo);

    Veiculo* veiculos = malloc(contador * sizeof(Veiculo));

    arquivo = fopen(caminhoArquivo, "r");
    fgets(linha, 500, arquivo);  // descarta a primeira linha
    int i = 0;
    while (fgets(linha, 500, arquivo) != NULL) {  // le o conteudo do arquivo
        veiculos[i] = parseVeiculo(linha);
        i++;
    }
    fclose(arquivo);

    *n = (contador - 1);

    return veiculos;
}

#define MAXTAM 6  // 5 posicoes da fila + 1 que fica sempre vazia

Veiculo array[MAXTAM];  // variaveis da fila circular
int primeiro = 0;
int ultimo = 0;

Veiculo remover() {  // função para remover o primeiro da fila
  Veiculo resp = array[primeiro];
  primeiro = (primeiro + 1) % MAXTAM;
  return resp;
}

void inserir(Veiculo v) {  // função para inserir no fim da fila
  if ((ultimo + 1) % MAXTAM == primeiro) {  // se a fila estiver cheia remove antes
    Veiculo r = remover();
    printf("(R)%s %s\n", r.marca, r.modelo);
  }
  array[ultimo] = v;
  ultimo = (ultimo + 1) % MAXTAM;
}

void mostrar() {  // função para mostrar a fila do primeiro ao ultimo
  for (int i = primeiro; i != ultimo; i = (i + 1) % MAXTAM) {
    char buffer[300];
    formatVeiculo(array[i], buffer);
    printf("%s\n", buffer);
  }
}

int main() {
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);  // chama a funcao de ler o arquivo

    int id;
    while (scanf("%d", &id) == 1) {  // insere os veiculos da primeira parte na fila
        if (id == -1) {
            break;
        }

        for (int i = 0; i < n; i++) {
            if (id == veiculos[i].id) {
                inserir(veiculos[i]);
            }
        }
    }

    int qtd;
    scanf("%d", &qtd);  // quantos comandos vao ter

    for (int k = 0; k < qtd; k++) {
        char comando[5];
        scanf("%s", comando);

        if (strcmp(comando, "I") == 0) {  // enfileira o veiculo do id
            scanf("%d", &id);
            for (int i = 0; i < n; i++) {
                if (id == veiculos[i].id) {
                    inserir(veiculos[i]);
                }
            }
        } else {  // desenfileira
            Veiculo r = remover();
            printf("(R)%s %s\n", r.marca, r.modelo);
        }
    }

    mostrar();  // mostra como a fila ficou

    free(veiculos);

    return 0;
}
