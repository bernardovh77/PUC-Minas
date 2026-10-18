import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Data {
    int ano;  // variaveis
    int mes;
    int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public static Data parseData(String s) {  // função para extrair informação da data
        String[] partes = s.split("-");
        return new Data(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }

    public String formatData() {  // função para formatar datas
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Veiculo {
    int id;  // variaveis
    int ano;
    int cilindros;
    double cilindrada;
    double consumoCidade;
    double consumoEstrada;
    double co2;
    Data dataRegistro;
    boolean turbo;
    String marca;
    String modelo;
    String categoria;
    String tracao;
    String transmissao;
    String[] combustivel = new String[5];

    public static Veiculo parseVeiculo(String s) {  // função para extrair informações dos veiculos
        Veiculo v = new Veiculo();
        String[] partes = s.split(",");

        v.id = Integer.parseInt(partes[0]);
        v.marca = partes[1];
        v.modelo = partes[2];
        v.ano = Integer.parseInt(partes[3]);
        v.categoria = partes[4];
        
        String combustivelR = partes[5];
        v.cilindros = Integer.parseInt(partes[6]);
        v.cilindrada = Double.parseDouble(partes[7]);
        v.transmissao = partes[8];
        v.tracao = partes[9];
        v.consumoCidade = Double.parseDouble(partes[10]);
        v.consumoEstrada = Double.parseDouble(partes[11]);
        v.co2 = Double.parseDouble(partes[12]);
        
        String turboR = partes[13];
        String dataR = partes[14];

        v.dataRegistro = Data.parseData(dataR);  // aproveita a função da data

        v.turbo = turboR.equals("true");  // analisa o texto e atribui valor booleano

        String[] tokens = combustivelR.split(";");  // separa os combustiveis
        for (int i = 0; i < tokens.length && i < 5; i++) {
            v.combustivel[i] = tokens[i];
        }
        for (int k = tokens.length; k < 5; k++) {  // inutiliza as strings de combustivel vazias
            v.combustivel[k] = "";
        }
        return v;
    }

    public String formatVeiculo() {  // função para formatar as informações dos veiculos
        String n = dataRegistro.formatData();
        String tur = turbo ? "true" : "false";  // transforma o booleano em texto

        StringBuilder veic = new StringBuilder();
        for (int k = 0; k < 5 && !combustivel[k].isEmpty(); k++) {  // formata os combustiveis
            if (k > 0) veic.append(",");
            veic.append(combustivel[k]);
        }

        return String.format("[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
                id, marca, modelo, ano, categoria, veic.toString(), cilindros, cilindrada,
                transmissao, tracao, consumoCidade, consumoEstrada, co2, tur, n);
    }
}

class Node {  // no da lista
    Veiculo v;
    Node next;  // aponta para o proximo no
    Node prev;  // aponta para o no anterior

    public Node(Veiculo v) {
        this.v = v;
        this.next = null;
        this.prev = null;
    }
}

class Lista {  // a lista em si
    Node inicio;  // primeiro no
    Node fim;     // ultimo no
    int tamanho;

    public Lista() {  // deixa a lista vazia
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public void inserirFim(Veiculo v) {  // insere um veiculo no fim da lista
        Node novo = new Node(v);
        novo.prev = fim;

        if (inicio == null) {  // lista vazia
            inicio = novo;
            fim = novo;
        } else {  // ja tem elementos
            fim.next = novo;
            fim = novo;
        }
        tamanho++;
    }
}

public class q5 {

    public static Veiculo[] lerCsv(String caminhoArquivo, int[] n) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            while (br.readLine() != null) {  // descobre o tamanho do arquivo
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Veiculo[] veiculos = new Veiculo[contador - 1];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();  // descarta a primeira linha
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {  // le o conteudo do arquivo
                veiculos[i] = Veiculo.parseVeiculo(linha);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        n[0] = contador - 1;
        return veiculos;
    }

    public static int compararVeiculo(Veiculo a, Veiculo b) {  // compara por consumo na estrada e, em caso de empate, pela marca
        if (a.consumoEstrada < b.consumoEstrada) return -1;
        if (a.consumoEstrada > b.consumoEstrada) return 1;

        return a.marca.compareTo(b.marca);
    }

    public static Node obterNo(Lista lista, int index) {  // obtem o no pelo indice
        Node atual = lista.inicio;
        for (int i = 0; i < index && atual != null; i++) {
            atual = atual.next;
        }
        return atual;
    }

    public static void quicksort(Lista lista, int start, int end) {
        if (start >= end) return;

        int i = start;
        int j = end;
        Node pivotNode = obterNo(lista, (start + end) / 2);  // pivo no meio do intervalo
        Veiculo pivot = pivotNode.v;
        Veiculo temp;

        Node nodeI = obterNo(lista, i);
        Node nodeJ = obterNo(lista, j);

        while (i <= j) {
            while (compararVeiculo(nodeI.v, pivot) < 0) {  // anda ate achar um elemento que nao vem antes do pivo
                i++;
                nodeI = nodeI.next;
            }
            while (compararVeiculo(nodeJ.v, pivot) > 0) {  // anda ate achar um elemento que nao vem depois do pivo
                j--;
                nodeJ = nodeJ.prev;
            }
            if (i <= j) {
                temp = nodeI.v;  // troca os dois de lado
                nodeI.v = nodeJ.v;
                nodeJ.v = temp;
                i++;
                nodeI = nodeI.next;
                j--;
                nodeJ = nodeJ.prev;
            }
        }

        quicksort(lista, start, j);  // ordena a parte da esquerda
        quicksort(lista, i, end);    // ordena a parte da direita
    }

    public static void main(String[] args) {
        int[] n = new int[1];
        Veiculo[] veiculos = lerCsv("/tmp/veiculos.csv", n);  // chama a funcao de ler o arquivo

        Lista selecionados = new Lista();  // lista com os veiculos da entrada

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {  // le o arquivo ate o id ser -1
            int id = scanner.nextInt();
            if (id == -1) {
                break;
            }

            for (int i = 0; i < n[0]; i++) {  // procura o id desejado no arquivo e guarda na lista
                if (id == veiculos[i].id) {
                    selecionados.inserirFim(veiculos[i]);
                }
            }
        }
        scanner.close();

        if (selecionados.tamanho > 0) {
            quicksort(selecionados, 0, selecionados.tamanho - 1);  // ordena os veiculos selecionados
        }

        Node p = selecionados.inicio;
        while (p != null) {  // printa os veiculos ordenados
            System.out.println(p.v.formatVeiculo());
            p = p.next;
        }
    }
}