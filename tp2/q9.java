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

class Lista { // classe da lista com alocação sequencial

    private Veiculo[] array; // variaveis
    private int n;

    public Lista(int tamanho) {
        array = new Veiculo[tamanho];
        n = 0;
    }

    public void inserirInicio(Veiculo veiculo) { // insere no inicio e empurra os outros para a direita
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = veiculo;
        n++;
    }

    public void inserir(Veiculo veiculo, int posicao) { // insere na posição e empurra os outros para a direita
        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }
        array[posicao] = veiculo;
        n++;
    }

    public void inserirFim(Veiculo veiculo) { // insere na ultima posição
        array[n] = veiculo;
        n++;
    }

    public Veiculo removerInicio() { // remove o primeiro e puxa os outros para a esquerda
        Veiculo resp = array[0];
        n--;
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }

    public Veiculo remover(int posicao) { // remove da posição e puxa os outros para a esquerda
        Veiculo resp = array[posicao];
        n--;
        for (int i = posicao; i < n; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }

    public Veiculo removerFim() { // remove o ultimo
        n--;
        return array[n];
    }

    public void mostrar() { // printa os veiculos do primeiro ao ultimo
        for (int i = 0; i < n; i++) {
            System.out.println(array[i].format());
        }
    }
}

class q9 {

    public static void main(String[] args) {
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        Lista lista = new Lista(veiculos.length);

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

        for (int k = 0; k < qtd; k++) {
            String comando = scanner.next();

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

        lista.mostrar(); // mostra como a lista ficou

        scanner.close();
    }
}
