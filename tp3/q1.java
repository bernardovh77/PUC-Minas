
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Data { // classe da data

    private int ano; // variaveis
    private int mes;
    private int dia;

    private Data(int ano, int mes, int dia) { // construtor privado, a data so e criada pelo parseData
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public static Data parseData(String s) { // função para extrair informação da data
        String[] partes = s.split("-"); // separa ano, mes e dia

        int ano = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        return new Data(ano, mes, dia);
    }

    public String format() { // função para formatar datas
        return String.format("%02d/%02d/%04d", dia, mes, ano); // formato dd/mm/aaaa
    }

    public int getAno() { // retorna o ano
        return ano;
    }

    public int getMes() { // retorna o mes
        return mes;
    }

    public int getDia() { // retorna o dia
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

    private Veiculo(int id, String marca, String modelo, int ano, String categoria, // construtor privado, o veiculo so e criado pelo parseVeiculo
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
        String[] campos = s.split(","); // separa os campos da linha do csv

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

        return new Veiculo(id, marca, modelo, ano, categoria, combustivel, cilindros, // cria o veiculo com os campos extraidos
                cilindrada, transmissao, tracao, consumoCidade, consumoEstrada, co2,
                turbo, dataRegistro);
    }

    public String format() { // função para formatar as informações dos veiculos

        // monta "combustivel1,combustivel2"
        String combustivelStr = ""; // formata os combustiveis
        for (int i = 0; i < combustivel.length; i++) {
            combustivelStr += combustivel[i];
            if (i < combustivel.length - 1) { // so coloca virgula entre os combustiveis
                combustivelStr += ",";
            }
        }
        String cilindradaStr = String.format("%.1f", cilindrada); // 1 casa decimal
        String consumoCidadeStr = String.format("%.2f", consumoCidade); // 2 casas decimais
        String consumoEstradaStr = String.format("%.2f", consumoEstrada);
        String co2Str = String.format("%.1f", co2);

        return "[" + id + " ## " + marca + " ## " + modelo + " ## " + ano + " ## " // junta tudo separado por " ## "
                + categoria + " ## [" + combustivelStr + "] ## "
                + cilindros + " ## " + cilindradaStr + " ## " + transmissao + " ## "
                + tracao + " ## " + consumoCidadeStr + " ## " + consumoEstradaStr + " ## "
                + co2Str + " ## " + turbo + " ## " + dataRegistro.format() + "]";
    }

    public int getId() { // retorna o id
        return id;
    }

    public String getMarca() { // retorna a marca
        return marca;
    }

    public String getModelo() { // retorna o modelo
        return modelo;
    }

    public int getAno() { // retorna o ano
        return ano;
    }

    public String getCategoria() { // retorna a categoria
        return categoria;
    }

    public String[] getCombustivel() { // retorna os combustiveis
        return combustivel;
    }

    public int getCilindros() { // retorna o numero de cilindros
        return cilindros;
    }

    public double getCilindrada() { // retorna a cilindrada
        return cilindrada;
    }

    public String getTransmissao() { // retorna a transmissao
        return transmissao;
    }

    public String getTracao() { // retorna a tracao
        return tracao;
    }

    public double getConsumoCidade() { // retorna o consumo na cidade
        return consumoCidade;
    }

    public double getConsumoEstrada() { // retorna o consumo na estrada
        return consumoEstrada;
    }

    public double getCo2() { // retorna a emissao de co2
        return co2;
    }

    public boolean isTurbo() { // retorna se o veiculo e turbo
        return turbo;
    }

    public Data getDataRegistro() { // retorna a data de registro
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

        Veiculo[] veiculos = new Veiculo[contador - 1]; // tira 1 por causa do cabecalho

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); // descarta a primeira linha
            String linha = br.readLine();
            int i = 0;
            while (linha != null) { // le o conteudo do arquivo
                veiculos[i] = Veiculo.parseVeiculo(linha); // transforma a linha em veiculo
                i++;
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminhoArquivo + ": " + e.getMessage());
        }

        return veiculos;
    }
}

class q1 {

    public static void main(String[] args) {
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv"); // le todos os veiculos do arquivo

        Veiculo[] selecionados = new Veiculo[veiculos.length]; // array com os veiculos da entrada
        int n = 0; // quantidade de veiculos selecionados

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
        Veiculo[] selected = new Veiculo[n]; // array do tamanho exato, sem posicoes null

        for(int i=0; i<n; i++){ // copia so os veiculos selecionados
          selected[i] = selecionados[i];
        }

        selected = mergesort(selected); // ordena por consumo na cidade e, em caso de empate, por categoria

        for(int i=0; i<selected.length; i++){ // printa os veiculos ordenados
          System.out.println(selected[i].format());
        }
    }

  public static Veiculo[] mergesort(Veiculo[] array){ // ordena o array dividindo ao meio e juntando as metades ordenadas

    if( array.length <= 1) return array; // caso base: array com 0 ou 1 elemento ja esta ordenado


    int middle = (array.length / 2) - 1; // posicao do ultimo elemento da primeira metade
    Veiculo[] a = new Veiculo[(array.length / 2)]; // primeira metade
    Veiculo[] b = new Veiculo[(array.length - (array.length /2))]; // segunda metade

    for(int i = 0; i <= middle; i++){ // copia a primeira metade
      a[i] = array[i];
    }
    for(int i = 0; i < (array.length - middle - 1); i++){ // copia a segunda metade
      b[i] = array[i + middle + 1];
    }

     a = mergesort(a); // ordena cada metade
     b = mergesort(b);
    array = merge(a,b); // junta as duas metades ja ordenadas
    return array;
  }


  public static Veiculo[] merge(Veiculo[] a, Veiculo[] b){ // junta dois arrays ordenados em um so

    int pt1 = 0; // posicao atual em a
    int pt2 = 0; // posicao atual em b
    Veiculo[] array = new Veiculo[a.length + b.length]; // array com o resultado
    int j=0; // posicao atual no resultado

    while(pt1 < a.length || pt2 < b.length){ // repete ate usar todos os elementos de a e b

    if(pt1 == a.length){ // a acabou, pega o proximo de b

      array[j] = b[pt2];
      pt2++;
    }
    else if(pt2 == b.length){ // b acabou, pega o proximo de a

      array[j] = a[pt1];
      pt1++;
    }
    else if(a[pt1].getConsumoCidade() < b[pt2].getConsumoCidade()){ // menor consumo em a

    array[j] = a[pt1];
    pt1++;
    }
    else if(b[pt2].getConsumoCidade() < a[pt1].getConsumoCidade()){ // menor consumo em b

      array[j] = b[pt2];
      pt2++;
    }

    else if(a[pt1].getConsumoCidade() == b[pt2].getConsumoCidade()){ // consumos iguais, desempata pela categoria

      if(a[pt1].getCategoria().compareTo(b[pt2].getCategoria()) <= 0){ // categoria de a vem primeiro (ou categorias iguais, mantem a ordem da entrada)
        array[j] = a[pt1];
        pt1++;
      }
      else{ // categoria de b vem primeiro
        array[j] = b[pt2];
        pt2++;
      }

    }
    j++;
  }
  return array;
 }
}
