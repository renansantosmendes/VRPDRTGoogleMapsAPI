/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renanmo;

//import static Algoritmos.Algoritmos.GRASP_reativo;
//import static Algoritmos.Algoritmos.GeneticAlgorithm;
//import static Algoritmos.Algoritmos.GeraPopGulosa;
//import static Algoritmos.Algoritmos.ILS;
//import static Algoritmos.Algoritmos.LeituraPesosArquivo;
//import static Algoritmos.Algoritmos.Perturbacao;
//import static Algoritmos.Algoritmos.VND;
import static Algoritmos.Algoritmos.GRASP_reativo;
import static Algoritmos.Algoritmos.GeneticAlgorithm;
import static Algoritmos.Algoritmos.ILS;
import static Algoritmos.Algoritmos.VND;
import static Algoritmos.Algoritmos.floydWarshall;
import static Algoritmos.Algoritmos.greedyConstructive;
//import static Algoritmos.Algoritmos.geraPesos;
//import static Algoritmos.Algoritmos.greedyConstructive;
//import static Algoritmos.AlgoritmosMO.Dominancia;
//import static Algoritmos.AlgoritmosMO.FNDS;
//import static Algoritmos.AlgoritmosMO.FNDS2;
//import static Algoritmos.AlgoritmosMO.FNDS3;
//import static Algoritmos.AlgoritmosMO.FNDS3;
//import static Algoritmos.AlgoritmosMO.FitnessMOGA;
//import static Algoritmos.AlgoritmosMO.FitnessMOGA2;
//import static Algoritmos.AlgoritmosMO.FitnessMOGA3;
//import static Algoritmos.AlgoritmosMO.Inicializa;
//import static Algoritmos.AlgoritmosMO.MOGA;
//import static Algoritmos.AlgoritmosMO.MOILS;
//import static Algoritmos.AlgoritmosMO.MOVND;
import static Algoritmos.AlgoritmosMO.NSGAII;
import static Algoritmos.AlgoritmosMO.SPEA2;
import static Algoritmos.Funcoes.ImprimePopulacao;
import static Algoritmos.Funcoes.InicializaPopulacao;
import static Algoritmos.Funcoes.InicializaPopulacaoPerturbacao;
import static Algoritmos.Funcoes.NewFitness;
//import static Algoritmos.AlgoritmosMO.Normalizacao;
//import static Algoritmos.Funcoes.GeraSolucaoAleatoria;
//import static Algoritmos.Algoritmos.ILS;
//import static Algoritmos.Algoritmos.greedyConstructive;
//import static Algoritmos.Funcoes.ImprimePopulacao;
//import static Algoritmos.Funcoes.InicializaPopulacao;
//import static Algoritmos.Funcoes.OrdenaPopulacao;
//import static Algoritmos.Funcoes.buscaTabu;
import static Algoritmos.Funcoes.loaderProblem;
import static Algoritmos.Funcoes.carregaAdjacencia;
import VRPDRTSD.IntanceReaderWithMySQL.AdjacenciesDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.RequestDataAcessObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
//import static Algoritmos.Funcoes.melhorVizinho;
//import static Algoritmos.Funcoes.vizinhoAleatorio;
//import static Algoritmos.Inicializacao.CarregaSolucao;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintStream;
//import static Algoritmos.Funcoes.primeiroMelhorVizinho;
//import static Algoritmos.Funcoes.primeiroMelhorVizinhoAleatorio;
//import static Algoritmos.Inicializacao.CarregaUmaSolucao;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//import java.util.Random;
import java.util.Set;
import representacao.Request;
//import representacao.Rota;
import representacao.Solucao;

/**
 *
 * @author Renan
 */
public class VRPDRT {

    /**
     * @param args the command line arguments
     */
    // Tempo que o ve�culo aguarda no local uma poss�vel solicita��o
    final static Integer TimeWindows = 3;
    //Lista de solicitacoes de transporte; cada passageiro realiza uma e somente uma solicitacao
    static List<Request> P = new ArrayList<>(); // P = {1,2,...,p}
    // Lista de Adjac�ncia LAij
    static List<List<Integer>> la = new LinkedList<>();
    //Vetor (Lista de Adjac�ncia) Custo de atravessar (Km) o n� i ao j
    //cij
    static List<List<Integer>> c = new LinkedList<>();
    //Vetor (Lista de Adjac�ncia) Tempo de viagem (min) do n� i ao j
    //dij
    static List<List<Integer>> d = new LinkedList<>();
    //Conj. todas as origens
    static Set<Integer> Pmais = new HashSet<>();
    //Conj. todos os destinos
    static Set<Integer> Pmenos = new HashSet<>();
    //Conj. n�s representando as paradas; n� 0 � o dep�sito de ve�culos
    static Set<Integer> V = new HashSet<>(); // V = {0} U Pmais U Pmenos = {0,1,2,...,n}
    static final Integer n = 12; //Qtd. de n�s presentes no grafo

    //Lista de solicita��es que possuem o v�rtice i como origem, i = todos os n�s
    static Map<Integer, List<Request>> Pin = new HashMap<>();

    //Lista de solicita��es que possuem o v�rtice i como destino, i = todos os n�s
    static Map<Integer, List<Request>> Pout = new HashMap<>();
    //Vetor m da diferenca entre todos as solicita��es de origem e destino do n� i
    //mi
    static List<Integer> m = new LinkedList<>();
    static Integer Kmax = 5; //Qtd. de ve�culos presentes no problema
    // Conj. de veiculos, todos com mesma capacidade
    static Set<Integer> K;
    // Capacidade de cada ve�culo
    static Integer Qmax = 10;

    //Lista das solicita��es n�o satisfeitas
    static List<Request> U = new ArrayList<>();

    //-----------------Teste--------------------------------
    static Integer currentTime;
    static Integer lastNode;

    public static void main(String[] args) throws FileNotFoundException {
        carregaAdjacencia(la, c, d, n);
        floydWarshall(c, d, n);
        loaderProblem(P, Pmais, Pmenos, Pin, Pout, V, n, m);
        System.out.println(P.size());

        List<Request> listP = new ArrayList<>(), listPOriginal = new ArrayList<>(P);
        int tamP = 250;
        listP.addAll(listPOriginal.subList(0, tamP));

        int Kmax = 5;
        int Qmax = 10;
        int iteracoes = 3;
        //System.out.println("-------------- Configuração  Q = " + Qmax + " " + " K = " + Kmax + "--------------");
        System.out.println("Quantidade de veículos = " + Kmax);
        System.out.println("Capacidade = " + Qmax);

        // Conj. de veiculos, todos com mesma capacidade
        Set<Integer> K = new HashSet<>(Kmax);
        for (int i = 0; i < Kmax; i++) {
            K.add(i);
        }

        Solucao Soriginal;

        Solucao S0 = new Solucao();
        Solucao S = new Solucao();
        Solucao S1 = new Solucao();

        Integer TamPop = 100;
        Integer TamArq = 100;
        Integer MaxGer = 200;
        double Pm = 0.2;
        double Pc = 0.8;
        List<Solucao> Pop = new ArrayList<>();
        List<Solucao> Q = new ArrayList<>();
        List<Solucao> naoDominados = new ArrayList<>();
        List<List<Solucao>> fronts = new ArrayList<>();

//        PrintStream saida = new PrintStream("ILS-30-Execucoes");
//
//        for (int i = 0; i < 30; i++) {
//            Solucao s0 = greedyConstructive(0.2, 0.15, 0.55, 0.10, listP, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//            System.out.println(s0);
//
//            Solucao s = new Solucao();
//            //s.setSolucao(GRASP_reativo(1, 0.2, 0.15, 0.55, 0.10, 2, 1, 1, listP, saida, Pin, Pout, n, Qmax, K,  U,P, m, d, c, TimeWindows));
//            //s.setSolucao(VND(s0, listP, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
//            s.setSolucao(ILS(s0, listP, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
//            System.out.println(s);
//            saida.print(s + "\n");
//        }
        //GeneticAlgorithm(Pop, TamPop, MaxGer, Pm, Pc, listP, Pin,  Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode) ;
//        InicializaPopulacaoPerturbacao(Pop, TamPop, listP, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//        Pop.sort(Comparator.comparing(Solucao::getFuncaoObjetivo));
//        NewFitness(Pop);
//        ImprimePopulacao(Pop);
//        System.out.println(listP.get(0));
//        RequestDataAcessObject dao = new RequestDataAcessObject();
//        System.out.println(listP.size());
//        listP.forEach(u -> dao.addRequestIntoDataBaseVRPDRTSD(u));
        //dao.addRequestIntoDataBase(listP.get(0));
        //System.out.println(listP.get(60).getDeliveryE());
        //System.out.println(listP.get(0));
        //System.out.println(d);
        
        
//        AdjacenciesDataAcessObject adao = new AdjacenciesDataAcessObject();
//        adao.addAdjacenciesIntoDataBase(n, d, d);
    }
}
