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

typedef struct Node{  // no da lista

  Veiculo v;
  struct Node* next;  // aponta para o proximo no
} Node;

typedef struct Lista{  // a lista em si

  Node* inicio;  // primeiro no
  Node* fim;     // ultimo no
  int tamanho;
} Lista;

void iniciarLista(Lista* lista){  // deixa a lista vazia

  lista -> inicio = NULL;
  lista -> fim = NULL;
  lista -> tamanho = 0;
}

void inserirFim(Lista* lista, Veiculo v){  // insere um veiculo no fim da lista

  Node* novo = malloc(sizeof(Node));
  novo -> v = v;
  novo -> next = NULL;

  if (lista -> inicio == NULL) {  // lista vazia
    lista -> inicio = novo;
    lista -> fim = novo;
  }
  else {  // ja tem elementos
    lista -> fim -> next = novo;
    lista -> fim = novo;
  }
  lista -> tamanho++;
}

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

int compararModelo(char* a, char* b){  // função para comparar modelos sem diferenciar maiusculas e minusculas

  int i = 0;
  int resp = 0;
  while (resp == 0 && (a[i] != '\0' || b[i] != '\0')) {
    char ca = a[i];
    char cb = b[i];
    if (ca >= 'A' && ca <= 'Z') {  // passa as letras para minusculas
      ca = ca + 32;
    }
    if (cb >= 'A' && cb <= 'Z') {
      cb = cb + 32;
    }
    resp = ca - cb;
    i++;
  }
  return resp;
}

int main() {
    int n;
    Veiculo* veiculos = lerCsv("/tmp/veiculos.csv", &n);  // chama a funcao de ler o arquivo

    Lista selecionados;  // lista com os veiculos da entrada
    iniciarLista(&selecionados);

    int id;
    while (scanf("%d", &id) == 1) {  // le o arquivo ate o id ser -1
        if (id == -1) {
            break;
        }

        for (int i = 0; i < n; i++) {  // procura o id desejado no arquivo e guarda na lista
            if (id == veiculos[i].id) {
                inserirFim(&selecionados, veiculos[i]);
            }
        }
    }

    for (Node* p = selecionados.inicio; p != NULL && p -> next != NULL; p = p -> next) {  // ordenação por seleção
        Node* menor = p;
        for (Node* j = p -> next; j != NULL; j = j -> next) {  // procura o menor modelo no resto da lista
            if (compararModelo(menor -> v.modelo, j -> v.modelo) > 0) {
                menor = j;
            }
        }
        Veiculo tmp = p -> v;  // troca os dados do menor com o da posicao atual
        p -> v = menor -> v;
        menor -> v = tmp;
    }

    for (Node* p = selecionados.inicio; p != NULL; p = p -> next) {  // printa os veiculos ordenados
        char buffer[300];
        formatVeiculo(p -> v, buffer);
        printf("%s\n", buffer);
    }

    Node* p = selecionados.inicio;  // libera cada no da lista
    while (p != NULL) {
        Node* next = p -> next;  // guarda o proximo ANTES de liberar
        free(p);
        p = next;
    }
    selecionados.inicio = NULL;
    selecionados.fim = NULL;
    selecionados.tamanho = 0;

    free(veiculos);

    return 0;
}
