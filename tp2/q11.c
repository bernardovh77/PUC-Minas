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

typedef struct Celula{  // struct da celula da lista

  Veiculo elemento;  // variaveis
  struct Celula* prox;
} Celula;

Celula* primeiro;  // celula cabeca da lista
Celula* ultimo;

void inserirInicio(Veiculo veiculo){  // função para inserir na primeira posicao

  Celula* tmp = malloc(sizeof(Celula));
  tmp->elemento = veiculo;
  tmp->prox = primeiro->prox;
  primeiro->prox = tmp;
  if (primeiro == ultimo) {  // se a lista estava vazia ele tambem vira o ultimo
    ultimo = tmp;
  }
}

void inserir(Veiculo veiculo, int posicao){  // função para inserir na posicao informada

  Celula* i = primeiro;
  for (int j = 0; j < posicao; j++) {  // anda ate a celula anterior a posicao
    i = i->prox;
  }
  Celula* tmp = malloc(sizeof(Celula));
  tmp->elemento = veiculo;
  tmp->prox = i->prox;
  i->prox = tmp;
  if (i == ultimo) {  // se inseriu depois do ultimo ele vira o ultimo
    ultimo = tmp;
  }
}

void inserirFim(Veiculo veiculo){  // função para inserir na ultima posicao

  Celula* tmp = malloc(sizeof(Celula));
  tmp->elemento = veiculo;
  tmp->prox = NULL;
  ultimo->prox = tmp;
  ultimo = tmp;
}

Veiculo removerInicio(){  // função para remover o primeiro

  Celula* tmp = primeiro;  // o primeiro vira a nova celula cabeca
  primeiro = primeiro->prox;
  Veiculo resp = primeiro->elemento;
  free(tmp);
  return resp;
}

Veiculo remover(int posicao){  // função para remover da posicao informada

  Celula* i = primeiro;
  for (int j = 0; j < posicao; j++) {  // anda ate a celula anterior a posicao
    i = i->prox;
  }
  Celula* tmp = i->prox;
  Veiculo resp = tmp->elemento;
  i->prox = tmp->prox;
  if (tmp == ultimo) {  // se removeu o ultimo o anterior vira o ultimo
    ultimo = i;
  }
  free(tmp);
  return resp;
}

Veiculo removerFim(){  // função para remover o ultimo

  Celula* i = primeiro;
  while (i->prox != ultimo) {  // anda ate o penultimo
    i = i->prox;
  }
  Veiculo resp = ultimo->elemento;
  free(ultimo);
  ultimo = i;
  ultimo->prox = NULL;
  return resp;
}

void mostrar(){  // função para mostrar a lista do primeiro ao ultimo

  for (Celula* i = primeiro->prox; i != NULL; i = i->prox) {
    char buffer[300];
    formatVeiculo(i->elemento, buffer);
    printf("%s\n", buffer);
  }
}

int main() {
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);  // chama a funcao de ler o arquivo

    primeiro = malloc(sizeof(Celula));  // cria a celula cabeca
    primeiro->prox = NULL;
    ultimo = primeiro;

    int id;
    while (scanf("%d", &id) == 1) {  // insere os veiculos da primeira parte no fim da lista
        if (id == -1) {
            break;
        }

        for (int i = 0; i < n; i++) {
            if (id == veiculos[i].id) {
                inserirFim(veiculos[i]);
            }
        }
    }

    int qtd;
    scanf("%d", &qtd);  // quantos comandos vao ter

    for (int k = 0; k < qtd; k++) {
        char comando[5];
        scanf("%s", comando);

        if (comando[0] == 'I') {  // comandos de inserção
            int posicao = 0;
            if (strcmp(comando, "I*") == 0) {
                scanf("%d", &posicao);
            }
            scanf("%d", &id);

            for (int i = 0; i < n; i++) {  // acha o veiculo e insere do jeito que o comando pede
                if (id == veiculos[i].id) {
                    if (strcmp(comando, "II") == 0) {
                        inserirInicio(veiculos[i]);
                    } else if (strcmp(comando, "I*") == 0) {
                        inserir(veiculos[i], posicao);
                    } else {
                        inserirFim(veiculos[i]);
                    }
                }
            }
        } else {  // comandos de remoção
            Veiculo r;
            if (strcmp(comando, "RI") == 0) {
                r = removerInicio();
            } else if (strcmp(comando, "R*") == 0) {
                int posicao;
                scanf("%d", &posicao);
                r = remover(posicao);
            } else {
                r = removerFim();
            }
            printf("(R)%s %s\n", r.marca, r.modelo);
        }
    }

    mostrar();  // mostra como a lista ficou

    while (primeiro != NULL) {  // libera as celulas da lista
        Celula* tmp = primeiro;
        primeiro = primeiro->prox;
        free(tmp);
    }
    free(veiculos);

    return 0;
}
