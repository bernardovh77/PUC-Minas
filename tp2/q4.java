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

class q4 {

    public static void main(String[] args) {
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        Veiculo[] selecionados = new Veiculo[veiculos.length]; // array com os veiculos da entrada
        int n = 0;

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) { // le a entrada ate o id ser -1
            int id = scanner.nextInt();
            if (id == -1) {
                break;
            }

            for (int i = 0; i < veiculos.length; i++) { // procura o id e guarda no array
                if (id == veiculos[i].getId()) {
                    selecionados[n] = veiculos[i];
                    n++;
                }
            }
        }

        for (int i = 1; i < n; i++) { // ordenação por inserção
            Veiculo tmp = selecionados[i];
            int j = i - 1;
            while (j >= 0 && selecionados[j].getMarca().compareTo(tmp.getMarca()) > 0) { // desloca as marcas maiores para a direita
                selecionados[j + 1] = selecionados[j];
                j--;
            }
            selecionados[j + 1] = tmp;
        }

        for (int i = 0; i < n; i++) { // printa os veiculos ordenados
            System.out.println(selecionados[i].format());
        }

        scanner.close();
    }
}
