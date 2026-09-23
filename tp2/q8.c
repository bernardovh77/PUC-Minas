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

    Veiculo* selecionados = malloc(n * sizeof(Veiculo));  // array separado para fazer as pesquisas
    int m = 0;

    int id;
    while (scanf("%d", &id) == 1) {  // le o arquivo
        if (id == -1) {
            break;
        }

        for (int i = 0; i < n; i++) {  // procura o id desejado no arquivo e guarda no array
            if (id == veiculos[i].id) {
                selecionados[m] = veiculos[i];
                m++;
            }
        }
    }

    for (int i = 0; i < m - 1; i++) {  // ordenação por seleção
        int menor = i;
        for (int j = i + 1; j < m; j++) {  // procura o menor modelo no resto do array
            if (compararModelo(selecionados[menor].modelo, selecionados[j].modelo) > 0) {
                menor = j;
            }
        }
        Veiculo tmp = selecionados[i];  // troca o menor com a posicao atual
        selecionados[i] = selecionados[menor];
        selecionados[menor] = tmp;
    }

    char chave[100];
    while (scanf(" %99[^\r\n]", chave) == 1) {  // le os modelos ate chegar no FIM
        if (strcmp(chave, "FIM") == 0) {
            break;
        }

        bool achou = false;
        int esq = 0;
        int dir = m - 1;
        while (esq <= dir && achou == false) {  // pesquisa binaria
            int meio = (esq + dir) / 2;
            int comp = compararModelo(chave, selecionados[meio].modelo);
            if (comp == 0) {
                achou = true;
            } else if (comp > 0) {  // se for maior procura na direita
                esq = meio + 1;
            } else {  // se for menor procura na esquerda
                dir = meio - 1;
            }
        }

        if (achou == true) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    free(selecionados);
    free(veiculos);

    return 0;
}
