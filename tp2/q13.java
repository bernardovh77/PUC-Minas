import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Data { // classe da data

    private int ano; // variaveis
    private int mes;
    private int dia;

    private Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public static Data parseData(String s) { // função para extrair informação da data
        String[] partes = s.split("-");

        int ano = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        return new Data(ano, mes, dia);
    }

    public String format() { // função para formatar datas
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }
}

class Veiculo { // classe de veiculos

    private int id; // variaveis
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    private Veiculo(int id, String marca, String modelo, int ano, String categoria,
                     String[] combustivel, int cilindros, double cilindrada,
                     String transmissao, String tracao, double consumoCidade,
                     double consumoEstrada, double co2, boolean turbo, Data dataRegistro) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.transmissao = transmissao;
        this.tracao = tracao;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
    }

    public static Veiculo parseVeiculo(String s) { // função para extrair informações dos veiculos
        String[] campos = s.split(",");

        int id = Integer.parseInt(campos[0]);
        String marca = campos[1];
        String modelo = campos[2];
        int ano = Integer.parseInt(campos[3]);
        String categoria = campos[4];
        String[] combustivel = campos[5].split(";"); // separa os combustiveis
        int cilindros = Integer.parseInt(campos[6]);
        double cilindrada = Double.parseDouble(campos[7]);
        String transmissao = campos[8];
        String tracao = campos[9];
        double consumoCidade = Double.parseDouble(campos[10]);
        double consumoEstrada = Double.parseDouble(campos[11]);
        double co2 = Double.parseDouble(campos[12]);
        boolean turbo = Boolean.parseBoolean(campos[13]); // analisa o texto e atribui valor booleano
        Data dataRegistro = Data.parseData(campos[14]); // aproveita a função da data

        return new Veiculo(id, marca, modelo, ano, categoria, combustivel, cilindros,
                cilindrada, transmissao, tracao, consumoCidade, consumoEstrada, co2,
                turbo, dataRegistro);
    }

    public String format() { // função para formatar as informações dos veiculos

        // monta "combustivel1,combustivel2"
        String combustivelStr = ""; // formata os combustiveis
        for (int i = 0; i < combustivel.length; i++) {
            combustivelStr += combustivel[i];
            if (i < combustivel.length - 1) {
                combustivelStr += ",";
            }
        }
        String cilindradaStr = String.format("%.1f", cilindrada);
        String consumoCidadeStr = String.format("%.2f", consumoCidade);
        String consumoEstradaStr = String.format("%.2f", consumoEstrada);
        String co2Str = String.format("%.1f", co2);

        return "[" + id + " ## " + marca + " ## " + modelo + " ## " + ano + " ## "
                + categoria + " ## [" + combustivelStr + "] ## "
                + cilindros + " ## " + cilindradaStr + " ## " + transmissao + " ## "
                + tracao + " ## " + consumoCidadeStr + " ## " + consumoEstradaStr + " ## "
                + co2Str + " ## " + turbo + " ## " + dataRegistro.format() + "]";
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public String[] getCombustivel() {
        return combustivel;
    }

    public int getCilindros() {
        return cilindros;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public String getTransmissao() {
        return transmissao;
    }

    public String getTracao() {
        return tracao;
    }

    public double getConsumoCidade() {
        return consumoCidade;
    }

    public double getConsumoEstrada() {
        return consumoEstrada;
    }

    public double getCo2() {
        return co2;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public Data getDataRegistro() {
        return dataRegistro;
    }
}

class LeitorCsv { // le o arquivo e transforma cada linha em um Veiculo

    public static Veiculo[] ler(String caminhoArquivo) {

        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            while (br.readLine() != null) { // descobre o tamanho do arquivo
                contador++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminhoArquivo + ": " + e.getMessage());
        }

        Veiculo[] veiculos = new Veiculo[contador - 1];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); // descarta a primeira linha
            String linha = br.readLine();
            int i = 0;
            while (linha != null) { // le o conteudo do arquivo
                veiculos[i] = Veiculo.parseVeiculo(linha);
                i++;
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminhoArquivo + ": " + e.getMessage());
        }

        return veiculos;
    }
}

class CelulaDupla { // classe da celula da lista dupla

    public Veiculo elemento; // variaveis
    public CelulaDupla ant;
    public CelulaDupla prox;

    public CelulaDupla(Veiculo elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.prox = null;
    }
}

class ListaDupla { // classe da lista duplamente encadeada

    private CelulaDupla primeiro; // variaveis
    private CelulaDupla ultimo;

    public ListaDupla() {
        primeiro = new CelulaDupla(null); // celula cabeça
        ultimo = primeiro;
    }

    public void inserirInicio(Veiculo veiculo) { // insere na primeira posição
        CelulaDupla tmp = new CelulaDupla(veiculo);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) { // se a lista estava vazia ele tambem vira o ultimo
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
    }

    public void inserir(Veiculo veiculo, int posicao) { // insere na posição informada
        CelulaDupla i = primeiro;
        for (int j = 0; j < posicao; j++) { // anda ate a celula anterior a posição
            i = i.prox;
        }
        CelulaDupla tmp = new CelulaDupla(veiculo);
        tmp.ant = i;
        tmp.prox = i.prox;
        i.prox = tmp;
        if (i == ultimo) { // se inseriu depois do ultimo ele vira o ultimo
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
    }

    public void inserirFim(Veiculo veiculo) { // insere na ultima posição
        ultimo.prox = new CelulaDupla(veiculo);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public Veiculo removerInicio() { // remove o primeiro
        CelulaDupla tmp = primeiro; // o primeiro vira a nova celula cabeça
        primeiro = primeiro.prox;
        Veiculo resp = primeiro.elemento;
        tmp.prox = null;
        primeiro.ant = null;
        return resp;
    }

    public Veiculo remover(int posicao) { // remove da posição informada
        CelulaDupla i = primeiro.prox;
        for (int j = 0; j < posicao; j++) { // anda ate a celula da posição
            i = i.prox;
        }
        i.ant.prox = i.prox;
        if (i == ultimo) { // se removeu o ultimo o anterior vira o ultimo
            ultimo = i.ant;
        } else {
            i.prox.ant = i.ant;
        }
        i.prox = null;
        i.ant = null;
        return i.elemento;
    }

    public Veiculo removerFim() { // remove o ultimo
        Veiculo resp = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
        return resp;
    }

    public void mostrar() { // printa os veiculos do primeiro ao ultimo
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(i.elemento.format());
        }
    }

    public void mostrarInverso() { // printa do ultimo ao primeiro voltando pelo ant
        for (CelulaDupla i = ultimo; i != primeiro; i = i.ant) {
            System.out.println(i.elemento.format());
        }
    }
}

class q13 {

    public static void main(String[] args) {
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        ListaDupla lista = new ListaDupla();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) { // insere os veiculos da primeira parte no fim da lista
            int id = scanner.nextInt();
            if (id == -1) {
                break;
            }

            for (int i = 0; i < veiculos.length; i++) {
                if (id == veiculos[i].getId()) {
                    lista.inserirFim(veiculos[i]);
                }
            }
        }

        int qtd = scanner.nextInt(); // quantos comandos vao ter
        boolean pilha = false;

        for (int k = 0; k < qtd; k++) {
            String comando = scanner.next();
            if (comando.equals("I") || comando.equals("R")) { // se vier so I e R a lista funciona como pilha
                pilha = true;
            }

            if (comando.charAt(0) == 'I') { // comandos de inserção
                int posicao = 0;
                if (comando.equals("I*")) {
                    posicao = scanner.nextInt();
                }
                int id = scanner.nextInt();

                for (int i = 0; i < veiculos.length; i++) { // acha o veiculo e insere do jeito que o comando pede
                    if (id == veiculos[i].getId()) {
                        if (comando.equals("II")) {
                            lista.inserirInicio(veiculos[i]);
                        } else if (comando.equals("I*")) {
                            lista.inserir(veiculos[i], posicao);
                        } else {
                            lista.inserirFim(veiculos[i]);
                        }
                    }
                }
            } else { // comandos de remoção
                Veiculo removido;
                if (comando.equals("RI")) {
                    removido = lista.removerInicio();
                } else if (comando.equals("R*")) {
                    removido = lista.remover(scanner.nextInt());
                } else {
                    removido = lista.removerFim();
                }
                System.out.println("(R)" + removido.getMarca() + " " + removido.getModelo());
            }
        }

        if (pilha == true) { // na pilha mostra a partir do topo, que é o fim da lista
            lista.mostrarInverso();
        } else {
            lista.mostrar(); // mostra como a lista ficou
        }

        scanner.close();
    }
}
