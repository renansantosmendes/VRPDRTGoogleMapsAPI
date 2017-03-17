/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import static Algorithms.Algorithms.ILS;
import static Algorithms.Algorithms.Perturbacao;
import static Algorithms.Algorithms.RVND;
import static Algorithms.Algorithms.avaliaSolucao;
import static Algorithms.Algorithms.geraPesos;
import static Algorithms.Methods.CopiaMelhorSolucao;
import static Algorithms.Methods.Cruzamento;
import static Algorithms.Methods.Cruzamento2Pontos;
import static Algorithms.Methods.Fitness;
import static Algorithms.Methods.ImprimePopulacao;
import static Algorithms.Methods.InicializaPopulacao;
import static Algorithms.Methods.InsereMelhorIndividuo;
import static Algorithms.Methods.Mutacao;
import static Algorithms.Methods.Mutacao2Opt;
import static Algorithms.Methods.Mutacao2Shuffle;
import static Algorithms.Methods.MutacaoShuffle;
import static Algorithms.Methods.OrdenaPopulacao;
import static Algorithms.Methods.Selecao;
import static Algorithms.Methods.melhorVizinho;
import static Algorithms.Methods.primeiroMelhorVizinho;
import static Algorithms.Methods.vizinhoAleatorio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import ProblemRepresentation.Request;
import ProblemRepresentation.Solution;

/**
 *
 * @author Renan
 */
public class AlgorithmsForMultiObjectiveOptimization {

//    public static void MOGA(List<Solucao> Pop, Integer TamPop, Integer MaxGer, double Pm, double Pc, List<Request> listRequests,Map<Integer, List<Request>> Pin,
//                            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, 
//                            List<List<Integer>> d, List<List<Integer>> c,Integer TimeWindows, Integer currentTime, Integer lastNode){
//        String diretorio, nomeArquivo;
//        try {
//            //----------------------------------------------------------------------------------------------------
//            //                       Inicialiações do algoritmo, arquivo e variáveis
//            //----------------------------------------------------------------------------------------------------
//   
//            List<Integer> pais = new ArrayList<>();
//            
//            double tempoInicio,tempoFim;
//            //SBest = CopiaMelhorSolucao(Pop, SBest);
//            //System.out.println("Melhor Individuo = " + SBest);
//            int somaTotal;
//            double media, desvio;
//            diretorio = "\\home\\renanMulti";
//            nomeArquivo = "MOGA";
//            boolean success = (new File(diretorio)).mkdirs();
//            if (!success) {
//                System.out.println("Diretórios ja existem!");
//            }
//            PrintStream saida;
//            saida = new PrintStream(diretorio + "\\" + nomeArquivo + ".txt");
//            //----------------------------------------------------------------------------------------------------
//            for (int cont = 0; cont < 1; cont++) {
//                //--------------- Inicializa com a mesma população inicial ------------------
//                List<Solucao> naoDominados = new ArrayList();
//                double dist[][] = new double[TamPop][TamPop];
//                double sigmaSH = 0.05;
//                double sigma = 0.25;
//                double alfa = 1;
//                
//                InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                Inicializa(Pop,TamPop,listRequests,Pin, Pout, n, Qmax,  K,  U,  P, m,d,c, TimeWindows, currentTime,lastNode);
//                //CarregaSolucao(Pop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                //Dominancia(Pop,naoDominados);
//                 
//                
//                FitnessMOGA(Pop);
//                Partilha(Pop,dist,sigmaSH,sigma,alfa);
//                //Normalizacao(Pop);
//                ImprimePopulacao(Pop);
//                Distancia(Pop,dist);
//                Dominancia(Pop,naoDominados);
//                OrdenaPopulacao(Pop);
//                
//                //System.out.println("Pareto");
//                //ImprimePopulacao(naoDominados); 
//                                              
//                System.out.println("Execução = " + cont);
//                int GerAtual = 0;
//                while (GerAtual < MaxGer) {
//                    //Ordenação da população
//                    //OrdenaPopulacao(Pop);
//
//                    //Cálculo do fitness - aptidão
//                    FitnessMOGA(Pop);
//                    Partilha(Pop,dist,sigmaSH,sigma,alfa);
//                    Normalizacao(Pop);
//                    
//                    //Selecionar
//                    Selecao(pais, Pop);
//
//                    //Cruzamento
//                    Cruzamento2Pontos(Pop, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//
//                    //Mutação
//                    Mutacao2Shuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                           
//                    FitnessMOGA(Pop);
//                    Partilha(Pop,dist,sigmaSH,sigma,alfa);
//                    Normalizacao(Pop);
//                    Distancia(Pop,dist);
//                    Dominancia(Pop,naoDominados);
//                    System.out.println("Geração = " + GerAtual);
//                    GerAtual++;
//                }
//                List<Solucao> melhores = new ArrayList<>();
//                Dominancia(naoDominados,melhores);
//                //melhores.addAll(naoDominados);
//                ImprimePopulacao(melhores);
//                
//                Pop.clear();
//                
//            }
//            
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
    public static void MOGA(List<Solution> Pop, Integer TamPop, Integer MaxGer, double Pm, double Pc, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m,
            List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        String diretorio, nomeArquivo;
        try {
            //----------------------------------------------------------------------------------------------------
            //                       Inicialiações do algoritmo, arquivo e variáveis
            //----------------------------------------------------------------------------------------------------

            List<Integer> pais = new ArrayList<>();

            double tempoInicio, tempoFim;
            //SBest = CopiaMelhorSolucao(Pop, SBest);
            //System.out.println("Melhor Individuo = " + SBest);
            int somaTotal;
            double media, desvio;
            diretorio = "\\home\\renanMulti";
            nomeArquivo = "MOGA";
            boolean success = (new File(diretorio)).mkdirs();
            if (!success) {
                System.out.println("Diretórios ja existem!");
            }
            PrintStream saida;
            saida = new PrintStream(diretorio + "\\" + nomeArquivo + ".txt");
            //----------------------------------------------------------------------------------------------------
            for (int cont = 0; cont < 1; cont++) {
                //--------------- Inicializa com a mesma população inicial ------------------
                List<Solution> naoDominados = new ArrayList();
                List<Solution> arquivo = new ArrayList();
                double dist[][] = new double[TamPop][TamPop];
                double sigmaSH = Math.sqrt(2) / 60;// 0.05;//deixado para o algoritmo calcular o raio do nicho
                double sigma = 1;
                double alfa = 1;
                int TamMax = 60;
                InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                //CarregaSolucao(Pop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                Inicializa(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                for (int i = 0; i < TamPop; i++) {
                    Solution s = new Solution();
                    s.setSolucao(vizinhoAleatorio(Pop.get(i), i, i + 1, 1, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    Pop.get(i).setSolucao(s);
                }

                Dominancia(Pop, naoDominados);
                //ImprimePopulacao(Pop);
                System.out.println("Primeira Fronteira");
                ImprimePopulacao(naoDominados);
                System.out.println("\n\n\n");
                FitnessMO(Pop);
                Normalizacao2(Pop);
                Distancia(Pop, dist);
                //Partilha(Pop,dist,sigmaSH,sigma,alfa);

                ImprimePopulacao(Pop);

                //Partilha(Pop,dist,sigmaSH,sigma,alfa);
                //OrdenaPopulacao(Pop);
                //System.out.println("Pareto");
                //ImprimePopulacao(naoDominados); 
                System.out.println("Execução = " + cont);
                int GerAtual = 0;
                while (GerAtual < MaxGer) {
                    //Atualiza arquivo
                    atualizaArquivo(Pop, arquivo, TamMax);
                    //System.out.println("Tamanho da população = " + Pop.size());
                    //ImprimePopulacao(Pop);
                    //Ordenação da população
                    OrdenaPopulacao(Pop);

                    //Selecionar
                    //Selecao(pais, Pop);
                    //System.out.println(pais);
                    //System.out.println("Pais.size = " + pais.size());    
                    //Cruzamento
                    //Cruzamento2Pontos(Pop, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    //Mutação
                    Mutacao2Shuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //ImprimePopulacao(Pop);
                    //Normalizacao2(Pop);
                    //ImprimePopulacao(Pop);

                    Dominancia(Pop, naoDominados);

                    atualizaArquivo(Pop, arquivo, TamMax);

                    //Partilha(Pop,dist,sigmaSH,sigma,alfa);
                    //System.out.println("Tamanho da população antes = " + Pop.size());
                    FitnessMO(Pop);
                    //System.out.println("Tamanho da população depois = " + Pop.size());

                    Normalizacao2(Pop);

                    Distancia(Pop, dist);

                    //Partilha(Pop,dist,sigmaSH,sigma,alfa);
                    //System.out.println("Popsize = " + Pop.size());
                    //Normalizacao(Pop);
                    System.out.println("Geração = " + GerAtual);
                    GerAtual++;
                    //ImprimePopulacao(Pop);
                }
                List<Solution> melhores = new ArrayList<>();
                Dominancia(arquivo, melhores);
                Normalizacao2(melhores);
                //melhores.addAll(naoDominados);
                System.out.println("Pareto final");
                ImprimePopulacao(melhores);
                //ImprimePopulacao(arquivo);
                Pop.clear();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

//    public static void NSGAII(List<Solucao> Pop, List<Solucao> Q,Integer TamPop, Integer MaxGer, double Pm, double Pc, List<Request> listRequests,Map<Integer, List<Request>> Pin,
//                            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, 
//                            List<List<Integer>> d, List<List<Integer>> c,Integer TimeWindows, Integer currentTime, Integer lastNode){
//        String diretorio, nomeArquivo;
//        try {
//            //----------------------------------------------------------------------------------------------------
//            //                       Inicialiações do algoritmo, arquivo e variáveis
//            //----------------------------------------------------------------------------------------------------
//   
//            List<Integer> pais = new ArrayList<>();
//                        
//            diretorio = "\\home\\renanMulti";
//            nomeArquivo = "NSGAII-";
//            boolean success = (new File(diretorio)).mkdirs();
//            if (!success) {
//                System.out.println("Diretórios ja existem!");
//            }
//            PrintStream saida1,saida2,saida3,saida4;
//            //saida  = new PrintStream(diretorio + "\\" + nomeArquivo + ".txt");
//            saida1 = new PrintStream(diretorio + "\\" + nomeArquivo + "FO_normalizada.txt");
//            saida2 = new PrintStream(diretorio + "\\" + nomeArquivo + "tamanho_arquivo.txt");
//            saida3 = new PrintStream(diretorio + "\\" + nomeArquivo + "Pareto_Combinado-Solucoes.txt");
//            saida4 = new PrintStream(diretorio + "\\" + nomeArquivo + "Pareto_Combinado-FOs.txt");
//            //----------------------------------------------------------------------------------------------------
//            List<Solucao> paretoCombinado = new ArrayList<>();
//            for (int cont = 0; cont < 1; cont++) {//laço para fazer os experimentos estatísticos, alterar para cont < 30
//                String numero;
//                numero = Integer.toString(cont);
//                PrintStream saida = new PrintStream(diretorio + "\\" + nomeArquivo + "Solution-exec-" +numero+".txt");
//                PrintStream saida5 = new PrintStream(diretorio + "\\" + nomeArquivo + "Execucao-" +numero+".txt");
//                PrintStream saida6 = new PrintStream(diretorio + "\\" + nomeArquivo + "tamanho_arquivo"+numero+".txt");
//                //--------------- Inicializa com a mesma população inicial ------------------
//                List<Solucao> naoDominados = new ArrayList();
//                List<Solucao> arquivo = new ArrayList();
//                List<Solucao> Pop_linha = new ArrayList();
//                List<Solucao> Pop_2linha = new ArrayList();//P'(t) = P(t) U Q(t)
//                List<List<Solucao>> fronts = new ArrayList<>();//fronteiras não dominadas
//                double dist[][] = new double[TamPop][TamPop];
//                double sigmaSH = Math.sqrt(2)/60;// 0.05;//deixado para o algoritmo calcular o raio do nicho
//                double sigma = 1;
//                double alfa = 1;
//                //int TamMax = 60;//não sei porque é 60, mas que estava assim estava 
//                int TamMax = 10;
//                //int TamMax = Pop.size();
//                Q.clear();
//                InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                //CarregaSolucao(Pop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                Inicializa(Pop,TamPop,listRequests,Pin, Pout, n, Qmax,  K,  U,  P, m,d,c, TimeWindows, currentTime,lastNode);
//                for(int i =0; i<TamPop; i++){
//                    Solution s = new Solution();
//                    s.setSolucao(vizinhoAleatorio(Pop.get(i),i,i+1, 1, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
//                    Pop.get(i).setSolucao(s);
//                }
//                Dominancia(Pop,naoDominados);
//                atualizaArquivoNSGAII2(naoDominados,arquivo,TamMax);
//                //----------------------------------------------------------------------------------------------------------------------
//                //                                                   GRAVAÇÃO NO ARQUIVO
//                //----------------------------------------------------------------------------------------------------------------------
//                for(Solution s: arquivo){
//                    saida.print("\t" + s + "\n");
//                    saida1.print("\t" + s.getF1n() + "\t"+ s.getF2n() + "\n");
//                    saida5.print("\t" + s.getF1n() + "\t"+ s.getF2n() + "\n");
//                }
//                saida.print("\n\n");
//                saida1.print("\n\n");
//                saida5.print("\n\n");
//                saida2.print(arquivo.size() + "\n");
//                saida6.print(arquivo.size() + "\n");
//                //----------------------------------------------------------------------------------------------------------------------
//                Q.addAll(Pop);
//                Dominancia(Q,naoDominados);
//                FNDS3(Q,fronts);
//                
//                FitnessMO(Q);
//                
//                Normalizacao2(Q);
//                //System.out.println("Descendencia = ");
//                //ImprimePopulacao(Q);
//                Distancia(Q, dist);
//                //Partilha(Pop,dist,sigmaSH,sigma,alfa);
//                
//                //Gera Q - descendencia
//                OrdenaPopulacao(Q);
//                //Selecionar
//                Selecao(pais, Q);
//                //Cruzamento
////                Cruzamento2Pontos(Q, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//                Cruzamento(Q, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//                //Mutação
//                //Mutacao2Shuffle(Q, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                MutacaoShuffle(Q, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                
//                //Dominancia(Q,naoDominados);
//                //FitnessMO(Q);
//                //Normalizacao2(Q);
//                //atualizaArquivo(Q,arquivo,TamMax);
//                //Normalizacao2(arquivo);
//                //System.out.println("Primeiro Arquivo");
//                //ImprimePopulacao(arquivo);
//                //Q.addAll(Pop);
//                
//                
//                //Partilha(Pop,dist,sigmaSH,sigma,alfa);
//                //OrdenaPopulacao(Pop);
//                
//                //System.out.println("Pareto");
//                //ImprimePopulacao(naoDominados); 
//                                              
//                System.out.println("Execução = " + cont);
//                int GerAtual = 0;
//                while (GerAtual < MaxGer) {
//                    //retiraIguais(Pop); 
//                    //Passo 3.1 - Fi(Q) e Fi(A)
//                    //Dominancia(Q,naoDominados);
//                    //FitnessMO(Q);
//                    //System.out.println("passou");
//                    //FitnessMO(arquivo);
//                    //Normalizacao2(Q);
//                    //Normalizacao2(arquivo);
//                    
//                    
//                    //Passo 3.2 - P'(t) <- P(t) U Q(t)                
//                    Pop_linha.clear();
//                    Pop_linha.addAll(Pop);
//                    Pop_linha.addAll(Q);
//                    
//                    //ImprimePopulacao(Pop_linha);
//                    //System.out.println("\n");
//                    //naoDominados.clear();
//                    Dominancia(Pop_linha,naoDominados);//é feito para calcular o número de indivíduos que a solução i domina
//                    
//                    //ImprimePopulacao(naoDominados);
//                    
//                    FNDS3(Pop_linha,fronts);
//                    //System.out.println(Pop_linha.size());
//                    //Passo 3.3 - Calcula Fi(P')                    
//                    FitnessMO(Pop_linha);
//                    
//                    //Passo 3.4 - Atualizar Arquivo(P'(t),A(t),fi'(t),Fi_A(t))
//                    //System.out.println(Pop_linha.size());
//                    atualizaArquivoNSGAII2(Pop_linha,arquivo,TamMax);
//                    //System.out.println(Pop_linha.size());
//                    //Ordenação da população
//                    OrdenaPopulacao(Pop_linha);
//                    
//                    //Passo 3.5 - Redução da População - usando os primeiros TamPop melhores
//                    //System.out.println("Tamanho antes da redução = " + Pop_linha.size());
//                    
//                    //Redução ao tamanho original
//                    //Pop_linha.subList(TamPop, Pop_linha.size()).clear();
//                    //System.out.println("Tamanho depois da redução = " + Pop_linha.size());
//                    //Pop.clear();
//                    //Pop.addAll(Pop_linha);
//                    
//                    Reducao(Pop,fronts,TamMax);
//                    
//                    Q.clear();
//                    Q.addAll(Pop);
//                    /*
//                    olhar se tem algo nessa parte aqui, uma vez que a seleção não é feita usando o arquivo
//                    recalcular o fitness do arquivo + população para que todas tenham chances de cruzar?
//                    */
//                    
//                    
//                    
//                    //System.out.println(Q.size()); 
//                    //Passo 3.6 - Selecionar
//                    Selecao(pais, Q);
//
//                    //Passo 3.7.1 - Cruzamento
//                    Cruzamento(Q, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//
//                    //Passo 3.7.2 - Mutação
//                    MutacaoShuffle(Q, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                    Dominancia(Q,naoDominados);
//                    FitnessMO(Q);
//                    Normalizacao2(Q);
//                    atualizaArquivoNSGAII2(Q,arquivo,TamMax);
//                    
//                    ImprimePopulacao(arquivo);
//                    
//                    //----------------------------------------------------------------------------------------------------------------------
//                    //                                                   GRAVAÇÃO NO ARQUIVO
//                    //----------------------------------------------------------------------------------------------------------------------
//                    for (Solution s : arquivo) {
//                        saida.print("\t" + s + "\n");
//                        saida1.print("\t" + s.getF1n() + "\t" + s.getF2n() + "\n");
//                        saida5.print("\t" + s.getF1n() + "\t" + s.getF2n() + "\n");
//                    }
//                    saida.print("\n\n");
//                    saida1.print("\n\n");
//                    saida5.print("\n\n");
//                    saida2.print(arquivo.size() + "\n");
//                    saida6.print(arquivo.size() + "\n");
//                    //----------------------------------------------------------------------------------------------------------------------
//                    //System.out.println(Q.size());            
//                    System.out.println("Geração = " + GerAtual);
//                    GerAtual++;
//                    //ImprimePopulacao(Pop);
//                }
//                List<Solucao> melhores = new ArrayList<>();
//                Dominancia(naoDominados,melhores);
//                Normalizacao2(melhores);
//                //melhores.addAll(naoDominados);
//                System.out.println("Pareto final");
//                //ImprimePopulacao(melhores);
//                //ImprimePopulacao(arquivo);
//                paretoCombinado.addAll(arquivo);
//                Pop.clear();
//                
//            }
//            List<Solucao> naoDominados = new ArrayList<>();
//            Dominancia(paretoCombinado,naoDominados);
//            for(Solution s: naoDominados){
//                saida3.print("\t" + s +"\n");
//                saida4.print("\t" + s.getF1() + "\t" + s.getF2() +"\n");
//            }
//            ImprimePopulacao(naoDominados);
//            
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    public static void NSGAII(List<Solution> Pop, List<Solution> Q, Integer TamPop, Integer MaxGer, double Pm, double Pc, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m,
            List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {

        List<Solution> naoDominados = new ArrayList();
        List<Solution> arquivo = new ArrayList();
        List<Integer> pais = new ArrayList<>();
        List<Solution> Pop_linha = new ArrayList();
        List<Solution> Pop_2linha = new ArrayList();//P'(t) = P(t) U Q(t)
        List<List<Solution>> fronts = new ArrayList<>();//fronteiras não dominadas
        String diretorio, nomeArquivo;
        diretorio = "\\home\\Multivariada";
        nomeArquivo = "NSGAII-Puro";
        boolean success = (new File(diretorio)).mkdirs();
        if (!success) {
            System.out.println("Diretórios ja existem!");
        }
        try {

            List<Solution> paretoCombinado = new ArrayList<>();
            for (int cont = 0; cont < 1; cont++) {
                String numero;
                numero = Integer.toString(cont);
                PrintStream saida1 = new PrintStream(diretorio + "\\" + nomeArquivo + "-Execucao-" + numero + ".txt");
                PrintStream saida2 = new PrintStream(diretorio + "\\" + nomeArquivo + "-tamanho_arquivo-" + numero + ".txt");
                PrintStream saida3 = new PrintStream(diretorio + "\\" + nomeArquivo + "-Execucao-Normalizada-" + numero + ".txt");
                int TamMax;
                InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                ImprimePopulacao(Pop);
                Normalizacao2(Pop);
                Dominancia(Pop, naoDominados);
                TamMax = Pop.size();
                Q.addAll(Pop);
                FNDS3(Q, fronts);
                FitnessMO(Q);

                Selecao(pais, Q, TamMax);
                //Cruzamento2Pontos(Q, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                Cruzamento2Pontos(Q, Pop, TamMax, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                Mutacao2Shuffle(Q, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);

                Normalizacao2(arquivo);
                for (Solution s : arquivo) {
                    saida1.print("\t" + s.getF1() + "\t" + s.getF2() + "\n");
                    saida3.print("\t" + s.getF1n() + "\t" + s.getF2n() + "\n");
                }
                saida1.print("\n\n");
                saida2.print(arquivo.size() + "\n");
                saida3.print("\n\n");

                Dominancia(Q, naoDominados);
                arquivo.addAll(naoDominados);
                System.out.println("Execução = " + cont );
                int gerAtual = 0;
                while (gerAtual < MaxGer) {
                    Dominancia(Q, naoDominados);
                    FNDS3(Q, fronts);
                    FitnessMO(Q);
                    Pop_linha.clear();
                    //Pop_linha.addAll(arquivo);
                    Pop_linha.addAll(Pop);
                    Pop_linha.addAll(Q);

                    FNDS3(Pop_linha, fronts);
                    FitnessMO(Pop_linha);
                    Dominancia(Pop_linha, naoDominados);
//                if(gerAtual%5==0){
//                    ImprimePopulacao(naoDominados);
//                    System.out.println();
//                }

                    //atualizaArquivoNSGA(naoDominados, arquivo, TamMax);
                    atualizaArquivoNSGA(Pop_linha, arquivo, TamMax);
                    Normalizacao2(arquivo);
                    Reducao(Pop, fronts, TamMax);
                    Q.clear();

                    //Q.addAll(arquivo);
                    Q.addAll(Pop);
                    Selecao(pais, Q,TamMax);
                    //Cruzamento2Pontos(Q, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    Cruzamento2Pontos(Q, Pop, TamMax, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    Mutacao2Shuffle(Q, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);

//                if((gerAtual%200 == 0) && (gerAtual != 0)){
//                    buscaLocal(arquivo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//                }
                    //System.out.println("Geração = " + gerAtual + "\t" + arquivo.size());
                    System.out.println("Geração = " + gerAtual + "\t" + arquivo.size());
                    //System.out.println("Geração = " + gerAtual+" TamPop = " + Pop.size());

                    for (Solution s : arquivo) {
                        saida1.print("\t" + s.getF1() + "\t" + s.getF2() + "\n");
                        saida3.print("\t" + s.getF1n() + "\t" + s.getF2n() + "\n");
                    }
                    saida1.print("\n\n");
                    saida2.print(arquivo.size() + "\n");
                    saida3.print("\n\n");
                    gerAtual++;
                }
                Q.clear();
                Pop_linha.clear();
                paretoCombinado.addAll(arquivo);
                arquivo.clear();
                Pop.clear();
                fronts.clear();
            }
            List<Solution> paretoFinal = new ArrayList<>();
            Dominancia(paretoCombinado, paretoFinal);

            ImprimePopulacao(paretoFinal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void SPEA2(List<Solution> Pop, List<Solution> Q, Integer TamPop, Integer TamArq, Integer MaxGer, double Pm, double Pc, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m,
            List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {

        List<Solution> naoDominados = new ArrayList();
        List<Solution> arquivo = new ArrayList();
        List<Integer> pais = new ArrayList<>();
        List<Solution> Pop_linha = new ArrayList();
        String diretorio, nomeArquivo;
        diretorio = "\\home\\EMO2017\\SPEA2\\30Exec\\AtualizacaoArquivo\\SegundaExecucao_ConferindoDados";
        nomeArquivo = "SPEA2-Puro";
        boolean success = (new File(diretorio)).mkdirs();
        if (!success) {
            System.out.println("Diretórios ja existem!");
        }
        try {
            List<Solution> paretoCombinado = new ArrayList<>();
            for (int cont = 0; cont < 30; cont++) {
                String numero;
                int TamMax;
                double dist[][] = new double[TamPop][TamPop];
                numero = Integer.toString(cont);
                PrintStream saida1 = new PrintStream(diretorio + "\\" + nomeArquivo + "-Execucao-" + numero + ".txt");
                PrintStream saida2 = new PrintStream(diretorio + "\\" + nomeArquivo + "-tamanho_arquivo-" + numero + ".txt");
                PrintStream saida3 = new PrintStream(diretorio + "\\" + nomeArquivo + "-Execucao-Normalizada-" + numero + ".txt");
                InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                Normalizacao2(Pop);

                TamMax = Pop.size();
                Dominancia(Pop, arquivo);
                Distancia(Pop, dist);

                System.out.println("Execução = " + cont);
                int gerAtual = 0;
                FitnessSPEA2(Pop, dist, TamPop, TamArq);
                List<Solution> teste = new ArrayList<>();
                while (gerAtual < MaxGer) {
                    FitnessSPEA2(Pop, dist, TamPop, TamArq);
                    System.out.println("Geração = " + gerAtual);
                    Pop_linha.addAll(Pop);
                    Pop_linha.addAll(arquivo);
                    //System.out.println("Tamanho Pop_linha = " + Pop_linha.size());
                    Dominancia(Pop_linha, naoDominados);
                    //teste.addAll(naoDominados);
                    //retiraIguais(teste);
                    atualizaArquivoSPEA2(Pop_linha, arquivo, TamArq);

                    Dominancia(arquivo, naoDominados);

                    Selecao(pais, arquivo, TamMax);//a seleção é feita somente no arquivo
                    //System.out.println("Pais = " + pais);
                    //A população P(t + 1) é gerada com base em A(t + 1)
                    //System.out.println(arquivo.size());
                    Cruzamento(Pop, arquivo, TamMax, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    //Cruzamento2Pontos(Pop, arquivo, TamMax, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    //System.out.println(arquivo.size());
                    //Mutacao(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    Mutacao2Opt(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //MutacaoShuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //Mutacao2Shuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);

//                    if ((gerAtual % 200 == 0) && (gerAtual != 0)) {
//                        buscaLocal(arquivo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
//                    }
                    gerAtual++;
                    Pop_linha.clear();
                    gravaArquivo(naoDominados, saida1, saida2, saida3);
                    //gravaArquivo(arquivo, saida1, saida2, saida3);
                }
                //ImprimePopulacao(arquivo);
                paretoCombinado.addAll(arquivo);
                arquivo.clear();
                Pop.clear();
                Pop_linha.clear();
                System.out.println(arquivo.size());
            }
            List<Solution> paretoFinal = new ArrayList<>();
            Dominancia(paretoCombinado, paretoFinal);
            PrintStream saida4 = new PrintStream(diretorio + "\\ParetoCombinado.txt");
            PrintStream saida5 = new PrintStream(diretorio + "\\ParetoCombinadoFOs.txt");
            for (Solution s : paretoFinal) {
                saida4.print(s + "\n");
                saida5.print(s.getF1() + "\t" + s.getF2() + "\n");
            }
            ImprimePopulacao(paretoFinal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void gravaArquivo(List<Solution> arquivo, PrintStream saida1, PrintStream saida2, PrintStream saida3) {
        for (Solution s : arquivo) {
            saida1.print("\t" + s.getF1() + "\t" + s.getF2() + "\n");
            saida3.print("\t" + s.getF1n() + "\t" + s.getF2n() + "\n");
        }
        saida1.print("\n\n");
        saida2.print(arquivo.size() + "\n");
        saida3.print("\n\n");
    }

    public static void FitnessSPEA2(List<Solution> Pop, double[][] dist, Integer TamPop, Integer TamArq) {
        Integer k = (int) Math.sqrt(TamPop + TamArq);
        List<Double> lista = new ArrayList<>();
        double maximo = 0, minimo = 9999999;
        for (int i = 0; i < TamPop; i++) {
            lista.clear();
            for (int j = 0; j < TamPop; j++) {
                lista.add(dist[i][j]);
            }
            Collections.sort(lista);
            double fitness = Pop.get(i).getR() + 1 / (lista.get(k) + 2);//fitness(i) = R(i) + D(i)
            Pop.get(i).setFitness(fitness);
            if (fitness > maximo) {
                maximo = fitness;
            } else if (fitness < minimo) {
                minimo = fitness;
            }
        }
        //Normalizar o Fitness
        double soma = 0;
        for (Solution s : Pop) {
            double fitness = (maximo - s.getFitness()) / (maximo - minimo);
            s.setFitness(fitness);
            soma += fitness;
        }
        for (Solution s : Pop) {
            s.setFitness(s.getFitness() / soma);
        }
    }

    public static void Reducao(List<Solution> Pop, List<List<Solution>> fronts, int TamMax) {
        //OrdenaArquivo(Pop);
        //ImprimePopulacao(Pop);
        int contador = 0;
        Pop.clear();
        while (Pop.size() < TamMax) {
            //System.out.println("Tamanho fronteiras = " + fronts.size());
            Pop.addAll(fronts.get(contador));
            contador++;
        }
        if (Pop.size() > TamMax) {//inserir nessa parte o cálculo de crowd distance
            Pop.subList(TamMax, Pop.size()).clear();
        }
    }

    public static void FitnessMOGA(List<Solution> Pop) {
//        int soma = 0;
//        for (int i = 0; i < Pop.size(); i++) {
//            soma += Pop.get(i).geteDom();
//        }
//        List<Double> fit = new ArrayList<>();
//
//        for (int i = 0; i < Pop.size(); i++) {
//            fit.add((double) Pop.get(i).geteDom()/ soma);
//        }
//        Collections.sort(fit);
//        Collections.reverse(fit);
//        
//        for (int i = 0; i < Pop.size(); i++) {
//            Pop.get(i).setFitness(fit.get(i));
//        }

        for (Solution s : Pop) {
            s.setFitness(s.geteDom() + 1);
            //System.out.println(s.geteDom());
        }

        int soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma += Pop.get(i).getFitness();
        }
        List<Double> fit = new ArrayList<>();

        for (int i = 0; i < Pop.size(); i++) {
            fit.add((double) Pop.get(i).getFitness() / soma);
        }
        Collections.sort(fit);
        Collections.reverse(fit);

        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(fit.get(i));
        }
        for (int i = 0; i < Pop.size(); i++) {
            //System.out.println(Pop.get(i).getFitness());
        }

    }

    public static void FitnessMOGA2(List<Solution> Pop) {
        List<Integer> frequencia = new ArrayList<>();
        List<Integer> fa = new ArrayList<>();
        for (Solution s : Pop) {
            s.setFitness(s.geteDom() + 1);
            //System.out.println(s.geteDom());
        }

        Collections.sort(Pop);
        //System.out.println("Populção Ordenada");
        //ImprimePopulacao(Pop);

        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i);
            fa.add(0);
        }
        //System.out.println(frequencia);
        //System.out.println(fa);

        for (int i = 0; i < frequencia.size(); i++) {
            int valor = frequencia.get(i);
            for (int j = 0; j < frequencia.size(); j++) {
                if (Pop.get(j).geteDom() == valor) {
                    fa.set(valor, fa.get(i) + 1);
                }
            }
        }
        //System.out.println(fa);
        frequencia.clear();
        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i + 1);
        }

        //System.out.println(frequencia);
        int posicao = 0;
        for (int i = 0; i < fa.size(); i++) {
            int nvezes = fa.get(i);
            int soma = 0;

            for (int j = 0; j < nvezes; j++) {
                soma = soma + frequencia.get(j);
            }
            //System.out.println("soma = "+soma);
//           if(nvezes == 0){
//               nvezes = 1;
//               soma = frequencia.get(0);
//           }
            //System.out.println("soma = "+soma);
            double fitness;
            if (nvezes != 0) {
                fitness = (double) soma / nvezes;
            } else {
                fitness = (double) soma;
                //System.out.println("Teve zero!!!");
            }
            //System.out.println("fitness = "+ fitness);
            frequencia.subList(0, nvezes).clear();
            for (int k = posicao; k < (posicao + nvezes); k++) {
                Pop.get(k).setFitness(fitness);
            }
            posicao = posicao + nvezes;
        }

//       for(Solution s: Pop){
//           System.out.println("Solution s = "+s.getFitness());
//       }
        double soma = 0;

        double max = -999999;
        double min = 999999;
        for (int i = 0; i < Pop.size(); i++) {
            if (Pop.get(i).getFitness() > max) {
                max = Pop.get(i).getFitness();
            }
            if (Pop.get(i).getFitness() < min) {
                min = Pop.get(i).getFitness();
            }
        }
        //System.out.println(max);
        //System.out.println(min);
        for (int i = 0; i < Pop.size(); i++) {
            double fit = (max - Pop.get(i).getFitness()) / (max - min);
            soma = soma + fit;
            Pop.get(i).setFitness(fit);
        }
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(Pop.get(i).getFitness() / soma);
        }
//        int soma = 0;
//        for (int i = 0; i < Pop.size(); i++) {
//            soma += Pop.get(i).getFitness();
//        }
//        List<Double> fit = new ArrayList<>();
//
//        for (int i = 0; i < Pop.size(); i++) {
//            fit.add((double) Pop.get(i).getFitness()/ soma);
//        }
//        Collections.sort(fit);
//        Collections.reverse(fit);
//        
//        for (int i = 0; i < Pop.size(); i++) {
//            Pop.get(i).setFitness(fit.get(i));
//        }
//        for (int i = 0; i < Pop.size(); i++) {
//           //System.out.println(Pop.get(i).getFitness());
//        }

    }

    public static void FitnessMOGA3(List<Solution> Pop) {
        List<Integer> mi = new ArrayList<>();
        List<Integer> frequencia = new ArrayList<>();
        List<Integer> fa = new ArrayList<>();

        for (Solution s : Pop) {
            s.setFitness(s.geteDom() + 1);
        }
        OrdenaPopulacao(Pop);

        int soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i);
            fa.add(0);
        }
        //System.out.println(frequencia);
        //System.out.println(fa);

        for (int i = 0; i < frequencia.size(); i++) {
            int valor = frequencia.get(i);
            for (int j = 0; j < frequencia.size(); j++) {
                if (Pop.get(j).geteDom() == valor) {
                    fa.set(valor, fa.get(i) + 1);
                }
            }
        }
        //System.out.println(fa);
        frequencia.clear();

        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i + 1);
        }

        //----------------------------------------------------------------------------------    
        //System.out.println("Tamanho da população = " + Pop.size());
        Solution maior = new Solution();
        Solution menor = new Solution();

        maior.setSolucao(Collections.max(Pop));
        menor.setSolucao(Collections.min(Pop));
        //System.out.println(melhor);
        //System.out.println(pior);

        soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma += Pop.get(i).getFitness();
        }
        List<Double> fit = new ArrayList<>();

        for (int i = 0; i < Pop.size(); i++) {
            //fit.add((double) Pop.get(i).getFitness() / soma);
            fit.add((double) (maior.getFitness() - Pop.get(i).getFitness()) / soma);
        }
        //Collections.sort(fit);
        //Collections.reverse(fit);

        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(fit.get(i));
        }
        //----------------------------------------------------------------------------------
        double somaTeste = 0;
        for (int i = 0; i < Pop.size(); i++) {
            somaTeste += Pop.get(i).getFitness();
        }

        //System.out.println("Soma Total = " + somaTeste);
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(Pop.get(i).getFitness() / somaTeste);
        }

        somaTeste = 0;
        for (int i = 0; i < Pop.size(); i++) {
            somaTeste += Pop.get(i).getFitness();
        }
        //System.out.println("Soma Total(normalizada) = " + somaTeste);

//        double fitnessTotal;
//        for (int i = 0; i < Pop.size(); i++) {
//            fitnessTotal = 0;
//            int nvezes = fa.get(i);
//            //System.out.println(nvezes);
//            if(nvezes!=0){
//                for(int j=0;j<nvezes;j++){
//                    fitnessTotal += Pop.get(i).getFitness();
//                }
//                //System.out.println(fitnessTotal);
//            }else{
//                fitnessTotal = Pop.get(i).getFitness();
//                //System.out.println(fitnessTotal);
//            }
//            
//        }
    }

    public static void FitnessMOGA4(List<Solution> Pop) {
        List<Integer> mi = new ArrayList<>();
        List<Integer> frequencia = new ArrayList<>();
        List<Integer> fa = new ArrayList<>();

        for (Solution s : Pop) {
            s.setFitness(s.geteDom() + 1);
        }
        OrdenaPopulacao(Pop);

        int soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i);
            fa.add(0);
        }
        //System.out.println(frequencia);
        //System.out.println(fa);

        for (int i = 0; i < frequencia.size(); i++) {
            int valor = frequencia.get(i);
            for (int j = 0; j < frequencia.size(); j++) {
                if (Pop.get(j).geteDom() == valor) {
                    fa.set(valor, fa.get(i) + 1);
                }
            }
        }
        //System.out.println(fa);
        frequencia.clear();

        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i + 1);
        }

        //----------------------------------------------------------------------------------    
        //System.out.println("Tamanho da população = " + Pop.size());
        Solution maior = new Solution();
        Solution menor = new Solution();

        maior.setSolucao(Collections.max(Pop));
        menor.setSolucao(Collections.min(Pop));
        //System.out.println(melhor);
        //System.out.println(pior);

        soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma += Pop.get(i).getFitness();
        }
        List<Double> fit = new ArrayList<>();

        for (int i = 0; i < Pop.size(); i++) {
            //fit.add((double) Pop.get(i).getFitness() / soma);
            fit.add((double) (maior.getFitness() - Pop.get(i).getFitness()) / soma);
        }
        //Collections.sort(fit);
        //Collections.reverse(fit);

        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(fit.get(i));
        }
        //----------------------------------------------------------------------------------
        double somaTeste = 0;
        for (int i = 0; i < Pop.size(); i++) {
            somaTeste += Pop.get(i).getFitness();
        }

        //System.out.println("Soma Total = " + somaTeste);
        fit.clear();
        for (int i = 0; i < Pop.size(); i++) {
            fit.add((double) Pop.get(i).getFitness() / (somaTeste));//Pop.get(i).setFitness(Pop.get(i).getFitness()/somaTeste);
        }

        //System.out.println(fit);
        somaTeste = 0;
        for (int i = 0; i < Pop.size(); i++) {
            somaTeste += Pop.get(i).getFitness();
        }
    }

    public static void FitnessMO(List<Solution> Pop) {
        List<Integer> frequencia = new ArrayList<>();
        List<Integer> fa = new ArrayList<>();
        for (Solution s : Pop) {
            s.setFitness(s.geteDom() + 1);
            //System.out.println(s.geteDom());
        }

        Collections.sort(Pop);
        //System.out.println("Populção Ordenada");
        //ImprimePopulacao(Pop);

        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i);
            fa.add(0);
        }
        //System.out.println(frequencia);
        //System.out.println(fa);

        for (int i = 0; i < frequencia.size(); i++) {
            int valor = frequencia.get(i);
            for (int j = 0; j < frequencia.size(); j++) {
                if (Pop.get(j).geteDom() == valor) {
                    fa.set(valor, fa.get(i) + 1);
                }
            }
        }
        //System.out.println(fa);
        frequencia.clear();
        for (int i = 0; i < Pop.size(); i++) {
            frequencia.add(i + 1);
        }

        //System.out.println(frequencia);
        int posicao = 0;
        for (int i = 0; i < fa.size(); i++) {
            int nvezes = fa.get(i);
            int soma = 0;

            for (int j = 0; j < nvezes; j++) {
                soma = soma + frequencia.get(j);
            }
            //System.out.println("soma = "+soma);
//           if(nvezes == 0){
//               nvezes = 1;
//               soma = frequencia.get(0);
//           }
            //System.out.println("soma = "+soma);
            double fitness;
            if (nvezes != 0) {
                fitness = (double) soma / nvezes;
            } else {
                fitness = (double) soma;
                //System.out.println("Teve zero!!!");
            }
            //System.out.println("fitness = "+ fitness);
            frequencia.subList(0, nvezes).clear();
            for (int k = posicao; k < (posicao + nvezes); k++) {
                Pop.get(k).setFitness(fitness);
            }
            posicao = posicao + nvezes;
        }

        double soma = 0;

        double max = -999999;
        double min = 999999;
        for (int i = 0; i < Pop.size(); i++) {
            if (Pop.get(i).getFitness() > max) {
                max = Pop.get(i).getFitness();
            }
            if (Pop.get(i).getFitness() < min) {
                min = Pop.get(i).getFitness();
            }
        }

        for (int i = 0; i < Pop.size(); i++) {
            double fit = (max - Pop.get(i).getFitness()) / (max - min);
            soma = soma + fit;
            Pop.get(i).setFitness(fit);
        }
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(Pop.get(i).getFitness() / soma);
        }
        //fitness sharing
        int TamPop = Pop.size();
        double dist[][] = new double[TamPop][TamPop];
        double sigmaSH = Math.sqrt(2) / 60;// 0.05;//deixado para o algoritmo calcular o raio do nicho
        double sigma = 1;
        double alfa = 1;

        Partilha(Pop, dist, sigmaSH, sigma, alfa);
        NormalizaFitness(Pop);

        double soma2 = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma2 += Pop.get(i).getFitness();
        }
        //System.out.println("Total = " + soma2);
        //NormalizaFitness(Pop);

    }

    public static void NormalizaFitness(List<Solution> Pop) {
//        List<Double> lista = new ArrayList<>();
//        for(int i=0; i<Pop.size(); i++){
//            lista.add(Pop.get(i).getFitness());
//        }
//        double maximo, minimo;
//        maximo = Collections.max(lista);
//        minimo = Collections.min(lista);
//        
//        for(int i=0; i<Pop.size(); i++){
//            Pop.get(i).setFitness((Pop.get(i).getFitness() - minimo)/(maximo - minimo));
//        }
        double soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma += Pop.get(i).getFitness();
        }
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(Pop.get(i).getFitness() / soma);
        }
    }

    public static void Dominancia(List<Solution> Pop, List<Solution> naoDominados) {
        //--------------------------------------------------------------------------------------------------------------
        //List<Solucao> naoDominados = new ArrayList<>();
        naoDominados.clear();
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).seteDom(0);
            Pop.get(i).setnDom(0);
            Pop.get(i).setL(new ArrayList<>());
        }
        //Ficar atento nesse reset aqui em cima, pode ser que de problema depois
        //--------------------------------------------------------------------------------------------------------------

        for (int i = 0; i < Pop.size(); i++) {
            for (int j = 0; j < Pop.size(); j++) {
                if (i != j) {
                    //if((Pop.get(i).getF1()<Pop.get(j).getF1())&&(Pop.get(i).getF2()<Pop.get(j).getF2())){
                    if (((Pop.get(i).getF1() < Pop.get(j).getF1()) && (Pop.get(i).getF2() < Pop.get(j).getF2())
                            || (Pop.get(i).getF1() < Pop.get(j).getF1()) && (Pop.get(i).getF2() == Pop.get(j).getF2())
                            || (Pop.get(i).getF1() == Pop.get(j).getF1()) && (Pop.get(i).getF2() < Pop.get(j).getF2()))) {
                        Pop.get(i).addnDom();
                        Pop.get(j).addeDom();
                        Pop.get(i).addL(j);//adiciona a posição da solucao que é dominada - usado no NSGA-II
                    }
                }
            }
        }

        for (int i = 0; i < Pop.size(); i++) {//Determina S, número de soluções que são dominadas pela solução i
            Pop.get(i).setS(Pop.get(i).getnDom());
        }

        for (int i = 0; i < Pop.size(); i++) {
            for (int j = 0; j < Pop.size(); j++) {
                if (((Pop.get(j).getF1() < Pop.get(i).getF1()) && (Pop.get(j).getF2() < Pop.get(i).getF2())
                        || (Pop.get(j).getF1() < Pop.get(i).getF1()) && (Pop.get(j).getF2() == Pop.get(i).getF2())
                        || (Pop.get(j).getF1() == Pop.get(i).getF1()) && (Pop.get(j).getF2() < Pop.get(i).getF2()))) {
                    Pop.get(i).setR(Pop.get(i).getR() + Pop.get(j).getnDom());
                }
            }
        }

        for (int i = 0; i < Pop.size(); i++) {
            if (Pop.get(i).geteDom() == 0) {
                naoDominados.add(Pop.get(i));
            }
        }
        retiraIguais(naoDominados);
    }

    public static void Normalizacao(List<Solution> Pop) {
        double max = -999999999;
        double min = 999999999;

        //para F1
        //obtendo o valor maximo
        for (Solution s : Pop) {
            if (s.getF1() > max) {
                max = s.getF1();
            }
        }
        //obtendo o valor minimo
        for (Solution s : Pop) {
            if (s.getF1() < min) {
                min = s.getF1();
            }
        }
        //System.out.println("F1: max = " + max +"\tmin = " + min);
        for (Solution s : Pop) {
            s.setF1n((s.getF1() - min) / (max - min));
        }

        max = -999999999;
        min = 999999999;

        //para F2
        //obtendo o valor maximo
        for (Solution s : Pop) {
            if (s.getF2() > max) {
                max = s.getF2();
            }
        }
        //obtendo o valor minimo
        for (Solution s : Pop) {
            if (s.getF2() < min) {
                min = s.getF2();
            }
        }
        //System.out.println("F2: max = " + max +"\tmin = " + min);
        for (Solution s : Pop) {
            s.setF2n((s.getF2() - min) / (max - min));
        }
    }

    public static void Distancia(List<Solution> Pop, double dist[][]) {
        //calcula a distancia entre os elementos - usa somente metada da matriz
        List<Double> linha = new ArrayList<>();
        for (int i = 0; i < Pop.size(); i++) {

            for (int j = i + 1; j < Pop.size(); j++) {
                dist[i][j] = Math.sqrt(Math.pow(Pop.get(i).getF1n() - Pop.get(j).getF1n(), 2) + Math.pow(Pop.get(i).getF2n() - Pop.get(j).getF2n(), 2));
            }
        }
        //coloca zero na diagonal principal
        for (int i = 0; i < Pop.size(); i++) {
            dist[i][i] = 0;
        }
        //Replico na metade inferior da matriz de distancia
        for (int i = 0; i < Pop.size(); i++) {
            for (int j = i + 1; j < Pop.size(); j++) {
                dist[j][i] = dist[i][j];
            }
        }
        //imprime a matriz de distancias
//        System.out.println("Matriz d = ");
//        for(int i=0; i<Pop.size();i++){
//            linha.clear();
//            for(int j=0;j<Pop.size();j++){
//                linha.add(dist[i][j]);
//            }
//            System.out.println(linha);
//        };
    }

    public static void Partilha(List<Solution> Pop, double dist[][], double sigmaSH, double sigma, double alfa) {
        for (int i = 0; i < Pop.size(); i++) {
            double soma = 0;
            List<Double> s = new ArrayList<>();
            List<Double> sh = new ArrayList<>();
            for (int j = 0; j < Pop.size(); j++) {
                if (dist[i][j] <= sigmaSH) {
                    //System.out.println("Entrou");
                    s.add(1 - Math.pow((dist[i][j] / sigma), alfa));
                } else {
                    s.add(0.0);
                }
            }

            for (int j = 0; j < Pop.size(); j++) {
                soma = soma + s.get(j);//somatorio de s(d(i,j))
            }
            sh.add(soma);
            //System.out.println("sh = "+ s);
            //System.out.println("Fitness antes = " + Pop.get(i).getFitness());
            Pop.get(i).setFitness(Pop.get(i).getFitness() / soma); //fi(i) = fi(i)/sigma
            //System.out.println("Fitness depois = " + Pop.get(i).getFitness());
        }
    }

    public static void Inicializa(List<Solution> Pop, int TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Solution s0 = new Solution();
        for (int i = 0; i < TamPop; i++) {
            s0.setSolucao(geraPesos(i, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            //Pop.add(s0);
            Pop.get(i).setSolucao(s0);
            //System.out.println("s0 = " + s0);

        }
    }

    public static void Normalizacao2(List<Solution> Pop) {
        double max = -999999999;
        double min = 999999999;

        //para F1
        //obtendo o valor maximo
        for (Solution s : Pop) {
            if (s.getF1() > max) {
                max = s.getF1();
            }
        }
        //obtendo o valor minimo
        for (Solution s : Pop) {
            if (s.getF1() < min) {
                min = s.getF1();
            }
        }
        //System.out.println("F1: max = " + max +"\tmin = " + min);
        for (int i = 0; i < Pop.size(); i++) {
            //Pop.get(i).setF1n((Pop.get(i).getF1() - min)/(max - min));
            Pop.get(i).setF1n((Pop.get(i).getF1()) / (3500));
        }

        max = -999999999;
        min = 999999999;

        //para F2
        //obtendo o valor maximo
        for (Solution s : Pop) {
            if (s.getF2() > max) {
                max = s.getF2();
            }
        }
        //obtendo o valor minimo
        for (Solution s : Pop) {
            if (s.getF2() < min) {
                min = s.getF2();
            }
        }
        //System.out.println("F2: max = " + max +"\tmin = " + min);
        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setF2n((Pop.get(i).getF2()) / (20000));
        }
    }

    public static void Normalizacao3(List<Solution> Pop) {
        Solution maior = new Solution();
        Solution menor = new Solution();

        maior.setSolucao(Collections.max(Pop));
        menor.setSolucao(Collections.min(Pop));

        for (Solution s : Pop) {
            s.setFitness((maior.getFitness() - s.getFitness()) / (maior.getFitness() - menor.getFitness()));
        }
    }

    public static void atualizaArquivo(List<Solution> Pop, List<Solution> arquivo, int TamMax) {

        List<Solution> naoDominados = new ArrayList<>();
        List<Solution> Q = new ArrayList<>();
        Random rnd = new Random();
        double melhorFitness;
        if (Pop.size() > 0) {
            melhorFitness = Pop.get(0).getFitness();
        } else {
            //System.out.println("Populaçao teve tamanho zero!!!");
            melhorFitness = rnd.nextFloat();
        }

        arquivo.addAll(Pop);

        //ImprimePopulacao(Pop);
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);
        Normalizacao2(arquivo);
        for (Solution s : arquivo) {
            s.setFitness(melhorFitness);
        }
        //retiraIguais(arquivo);

        //System.out.println("Tamanho arquivo = " + arquivo.size());
//        Q.addAll(Pop);
//        Q.addAll(arquivo);
//        Dominancia(arquivo,naoDominados);
//        System.out.println("Q"+Q.size());
//        //arquivo.clear();
//        arquivo.addAll(naoDominados);
//        Dominancia(Pop,naoDominados);
//        arquivo.addAll(naoDominados);
//        naoDominados.clear();
//        Dominancia(arquivo,naoDominados);
//        arquivo.clear();
//        arquivo.addAll(naoDominados);
        double sigmaSH = Math.sqrt(2) / 60;
        List<Integer> c = new ArrayList<>();
        if (arquivo.size() > TamMax) {//reduzir arquivo
            //double dist[][] = new double[arquivo.size()][arquivo.size()];
            while (arquivo.size() > TamMax) {
                c.clear();
                //Distancia(arquivo,dist);
                // System.out.println("Ultrapassou o tamanho maximo do arquivo!!! Tem que reduzir");
                //  System.out.println("Redução");
                Normalizacao2(arquivo);
                for (int i = 0; i < arquivo.size(); i++) {
                    int soma = 0;
                    double dist[][] = new double[arquivo.size()][arquivo.size()];
                    for (int j = 0; j < arquivo.size(); j++) {

                        dist[i][j] = Math.sqrt(Math.pow(arquivo.get(i).getF1n() - arquivo.get(j).getF1n(), 2) + Math.pow(arquivo.get(i).getF2n() - arquivo.get(j).getF2n(), 2));
                        if (dist[i][j] < sigmaSH) {
                            soma++;
                        }
                    }
                    c.add(soma);
                }
                int max = Collections.max(c);

                for (int i = 0; i < arquivo.size(); i++) {

                    if (c.get(i) == max) {
                        arquivo.remove(i);
                        c.remove(i);
                        break;
                    }
                }
                //System.out.println(c);
                //System.out.println("Tamanho do arquivo = " + arquivo.size());
            }
        }
        //
    }

    public static void atualizaArquivoNSGAII(List<Solution> Pop, List<Solution> arquivo, int TamMax) {
        //retiraIguais(Pop);        

        List<Solution> naoDominados = new ArrayList<>();
        List<Solution> Q = new ArrayList<>();
        Random rnd = new Random();
        double melhorFitness;
        if (Pop.size() > 0) {
            melhorFitness = Pop.get(0).getFitness();
        } else {
            //System.out.println("Populaçao teve tamanho zero!!!");
            melhorFitness = rnd.nextFloat();
        }

        arquivo.addAll(Pop);
        //System.out.println("Teste tamanho do arquivo = " + arquivo.size());
        //System.out.println(Pop.size());
        //Pop.clear();
        //ImprimePopulacao(Pop);
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);
        retiraIguais(arquivo);
        Normalizacao2(arquivo);
        for (Solution s : arquivo) {
            s.setFitness(melhorFitness);
        }
        //---------------------------------------------------------------------
        //System.out.println("Teste tamanho do arquivo = " + arquivo.size());

        if (arquivo.size() > TamMax) {//reduzir arquivo

            int k = (int) Math.ceil(Math.sqrt(TamMax));
            //System.out.println(k);
            while (arquivo.size() > TamMax) {
                //System.out.println("Tamanho arquivo = " + arquivo.size());
                double dist[][] = new double[arquivo.size()][arquivo.size()];
                Normalizacao2(arquivo);
                Distancia(arquivo, dist);
                List<Double> ci = new ArrayList<>();
                List<Double> linha = new ArrayList<>();
                for (int i = 0; i < arquivo.size(); i++) {
                    for (int l = 0; l < arquivo.size(); l++) {
                        if (dist[i][l] != 0) {
                            linha.add(dist[i][l]);
                        }
                    }
                    Collections.sort(linha);
                    //System.out.println(linha);
                    double soma = 0;
                    for (int j = 0; j < k; j++) {
                        soma += linha.get(k);
                    }
                    ci.add(soma);
                }
                //System.out.println(ci);
                double max = Collections.max(ci);
                int pos = 0;
                for (int i = 0; i < arquivo.size(); i++) {
                    if (max == ci.get(i)) {
                        pos = i;
                    }
                }
                arquivo.remove(pos);
            }
        }
        OrdenaArquivo(arquivo);
    }

    public static void atualizaArquivoNSGAII2(List<Solution> Pop, List<Solution> arquivo, int TamMax) {
        //retiraIguais(Pop);        

        List<Solution> naoDominados = new ArrayList<>();
        List<Solution> Q = new ArrayList<>();
        Random rnd = new Random();
        double melhorFitness;
        if (Pop.size() > 0) {
            melhorFitness = Pop.get(0).getFitness();
        } else {
            //System.out.println("Populaçao teve tamanho zero!!!");
            melhorFitness = rnd.nextFloat();
        }

        //arquivo.addAll(Pop);
        //System.out.println("Teste tamanho do arquivo = " + arquivo.size());
        //System.out.println(Pop.size());
        //Pop.clear();
        //ImprimePopulacao(Pop);
        Dominancia(Pop, naoDominados);
        //arquivo.clear();
        arquivo.addAll(naoDominados);
        retiraIguais(arquivo);
        naoDominados.clear();
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);

        Normalizacao2(arquivo);

        for (Solution s : arquivo) {
            s.setFitness(melhorFitness);
        }
        //---------------------------------------------------------------------
        //System.out.println("Teste tamanho do arquivo = " + arquivo.size());

        if (arquivo.size() > TamMax) {//reduzir arquivo

            int k = (int) Math.ceil(Math.sqrt(TamMax));
            //System.out.println(k);
            while (arquivo.size() > TamMax) {
                //System.out.println("Tamanho arquivo = " + arquivo.size());
                double dist[][] = new double[arquivo.size()][arquivo.size()];
                Normalizacao2(arquivo);
                Distancia(arquivo, dist);
                List<Double> ci = new ArrayList<>();
                List<Double> linha = new ArrayList<>();
                for (int i = 0; i < arquivo.size(); i++) {
                    for (int l = 0; l < arquivo.size(); l++) {
                        if (dist[i][l] != 0) {
                            linha.add(dist[i][l]);
                        }
                    }
                    Collections.sort(linha);
                    //System.out.println(linha);
                    double soma = 0;
                    for (int j = 0; j < k; j++) {
                        soma += linha.get(k);
                    }
                    ci.add(soma);
                }
                //System.out.println(ci);
                double max = Collections.max(ci);
                int pos = 0;
                for (int i = 0; i < arquivo.size(); i++) {
                    if (max == ci.get(i)) {
                        pos = i;
                    }
                }
                arquivo.remove(pos);
            }
        }
        OrdenaArquivo(arquivo);
    }
    
    public static void atualizaArquivoTeste(List<Solution> Pop, List<Solution> arquivo, int TamMax){
        List<Solution> naoDominados = new ArrayList<>();
        Dominancia(Pop, naoDominados);
        arquivo.addAll(naoDominados);
        naoDominados.clear();
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);
        if (arquivo.size() > TamMax) {
            System.out.println("Reduzir");
        }
    }
    
    public static void atualizaArquivoSPEA2(List<Solution> Pop, List<Solution> arquivo, int TamMax) {
        List<Solution> naoDominados = new ArrayList<>();
        arquivo.addAll(Pop);
        retiraIguais(arquivo);
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);

        if (arquivo.size() > TamMax) {
            System.out.println("Reduzir");
        }
        //OrdenaArquivo(Pop);
        int i = 0;
        if (arquivo.size() < TamMax) {
            List<Solution> NewPop = new ArrayList<>();
            NewPop.addAll(Pop);
            NewPop.addAll(arquivo);
            do {
                arquivo.add(NewPop.get(i));
                i++;
                //System.out.println("Tamanho arquivo = " + arquivo.size());
            } while (arquivo.size() < TamMax);
        }
        //ImprimePopulacao(arquivo);
        //System.out.println("Tamanho arquivo = " + arquivo.size());
        OrdenaArquivo(arquivo);
    }

    public static void atualizaArquivoNSGA(List<Solution> Pop, List<Solution> arquivo, int TamMax) {
//        List<Solucao> naoDominados = new ArrayList<>();
//        List<Solucao> lista = new ArrayList<>();
//        for(Solution s: Pop){
//            for(Solution s_arquivo: arquivo){
//                if(s != s_arquivo){
//                    lista.add(s);
//                    break;
//                }            
//            }
//        }
//        arquivo.addAll(lista);
//        //arquivo.addAll(Pop);
//        //retiraIguais(arquivo);
//        Dominancia(arquivo,naoDominados);
//        arquivo.clear();
//        arquivo.addAll(naoDominados);
        //ImprimePopulacao(arquivo);

        //arquivo.addAll(Pop);
        List<Solution> naoDominados = new ArrayList<>();
        arquivo.addAll(Pop);
        retiraIguais(arquivo);
        Dominancia(arquivo, naoDominados);
        arquivo.clear();
        arquivo.addAll(naoDominados);

        if (arquivo.size() > TamMax) {//reduzir arquivo

            int k = (int) Math.ceil(Math.sqrt(TamMax));
            //System.out.println(k);
            while (arquivo.size() > TamMax) {
                //System.out.println("Tamanho arquivo = " + arquivo.size());
                double dist[][] = new double[arquivo.size()][arquivo.size()];
                Normalizacao2(arquivo);
                Distancia(arquivo, dist);
                List<Double> ci = new ArrayList<>();
                List<Double> linha = new ArrayList<>();
                for (int i = 0; i < arquivo.size(); i++) {
                    for (int l = 0; l < arquivo.size(); l++) {
                        if (dist[i][l] != 0) {
                            linha.add(dist[i][l]);
                        }
                    }
                    Collections.sort(linha);
                    //System.out.println(linha);
                    double soma = 0;
                    for (int j = 0; j < k; j++) {
                        soma += linha.get(k);
                    }
                    ci.add(soma);
                }
                //System.out.println(ci);
                double max = Collections.max(ci);
                int pos = 0;
                for (int i = 0; i < arquivo.size(); i++) {
                    if (max == ci.get(i)) {
                        pos = i;
                    }
                }
                arquivo.remove(pos);
            }
        }
        OrdenaArquivo(arquivo);
    }

    public static void OrdenaArquivo(List<Solution> arquivo) {
        int tamanhoArquivo = arquivo.size();

        for (int i = 0; i < tamanhoArquivo; i++) {
            for (int j = 0; j < tamanhoArquivo; j++) {
                if (arquivo.get(i).getF1() < arquivo.get(j).getF1()) {
                    Solution s = new Solution();
                    s.setSolucao(arquivo.get(i));
                    arquivo.get(i).setSolucao(arquivo.get(j));
                    arquivo.get(j).setSolucao(s);
                }
            }
        }
    }

    public static void retiraIguais(List<Solution> arquivo) {
        int tamanhoArquivo = arquivo.size();
        for (int i = 0; i < tamanhoArquivo; i++) {
            for (int j = i + 1; j < tamanhoArquivo; j++) {
                if ((arquivo.get(i).getF1() == arquivo.get(j).getF1()) && (arquivo.get(i).getF2() == arquivo.get(j).getF2())) {
                    arquivo.remove(j);
                    tamanhoArquivo = arquivo.size();
                    //j=0;
                    j = i + 1;
                }
            }
        }
    }
//    public static void FNDS(List<Solucao> Pop, List<List<Solucao>> fronts){
//        List<Solucao> PopAux = new ArrayList<>();//lista para remover soluções
//        PopAux.addAll(Pop);
//        List<Solucao> front = new ArrayList<>();//lista com apenas uma fronteira
//        //List<Solucao> front = new ArrayList<>();
//        int contador = 0;
//        while(PopAux.size() > 0){//enquanto todos os indivíduos não forem classificados
//            fronts.add(new ArrayList<>());
//            
//            for(int i=0; i< PopAux.size(); i++){
//                if(PopAux.get(i).geteDom() == 0){
//                    front.add(Pop.get(i));
//                }
//            }
//            
//            System.out.println("primeira fronteira = " + front);
//            List<Integer> dominadas = new ArrayList<>();
//            
//            for(Solution s: front){
//                
//                dominadas.addAll(s.getL());
//                
//                
//                //System.out.println("Solucoes Dominadas = " + dominadas);
//            }
//            
//            for(int posicao: dominadas){
//                    Pop.get(posicao).redeDom();
//            }
//            //Dominancia(Pop,naoDominados);
//            dominadas.clear();
//            PopAux.removeAll(front);
//            //System.out.println(PopAux.size());
//            fronts.add(new ArrayList<>());
//            fronts.get(contador).addAll(front);
//            front.clear();
//            contador++;
//        }
//    }

    public static void FNDS(List<Solution> Pop, List<List<Solution>> fronts) {
        List<Solution> PopAux = new ArrayList<>();//lista para remover soluções
        PopAux.addAll(Pop);
        fronts.clear();
        List<Solution> front = new ArrayList<>();//lista com apenas uma fronteira
        int contador = 0;
        //ImprimePopulacao(Pop);
        while (PopAux.size() > 0) {
            for (int i = 0; i < PopAux.size(); i++) {
                if (PopAux.get(i).geteDom() == 0) {
                    front.add(PopAux.get(i));
                }
            }

            for (Solution s : front) {
                for (int posicao : s.getL()) {
                    Pop.get(posicao).redeDom();
                }
            }

            PopAux.removeAll(front);
            fronts.add(new ArrayList<>());
            fronts.get(contador).addAll(front);
            //System.out.println(front);
            //System.out.println(fronts.size());
            //ImprimePopulacao(front);
            for (Solution s : PopAux) {//forçando a barra - olhar o motivo de dar numero negativo no eDom
                if (s.geteDom() < 0) {
                    s.seteDom(0);
                }
            }
            //System.out.println("FNDS");
            front.clear();
            contador++;
            //System.out.println("popaux = " + PopAux);
        }
        //System.out.println("Saiu do laço");
        //----- teste ------

        for (int i = 0; i < fronts.size(); i++) {
            //System.out.println(i);
            front.addAll(fronts.get(i));
            for (Solution s : front) {
                s.seteDom(i);
            }
            //System.out.println(front.size());
            front.clear();
        }
        //System.out.println("\n");
        Pop.clear();
        for (List<Solution> f : fronts) {
            Pop.addAll(f);
            //System.out.println(f);
        }
    }

    public static void FNDS2(List<Solution> Pop, List<List<Solution>> fronts) {
        List<Solution> PopAux = new ArrayList<>();//lista para remover soluções
        PopAux.addAll(Pop);
        List<Solution> front = new ArrayList<>();//lista com apenas uma fronteira

        //fronts.add(new ArrayList<>());
        int contador = 0;
//        for(int i=0; i<Pop.size();i++){
//            for(int j=0; j<PopAux.size();j++){
//                for(Solution s: PopAux){
//                    if(s.geteDom()==i){
//                        front.add(s);
//                    }
//                }
//            System.out.println(front);
//            fronts.get(contador).addAll(front);
//            PopAux.removeAll(front);
//            front.clear();
//            contador++;
//            }           
//        }
        while (PopAux.size() > 0) {
            for (int j = 0; j < PopAux.size(); j++) {
                if (PopAux.get(j).geteDom() == 0) {
                    front.add(PopAux.get(j));
                }
            }
            PopAux.removeAll(front);
            for (int j = 0; j < PopAux.size(); j++) {
                PopAux.get(j).redeDom();
            }
            //System.out.println("Fronteira 1 = " + front);
            fronts.add(new ArrayList<>());

            fronts.get(contador).addAll(front);

            front.clear();
            contador++;
            //System.out.println("Tamanho" + PopAux.size());
        }
        //fronts.get(fronts.size()-1).clear();
        //System.out.println("Fronteiras = " + fronts);
        //System.out.println("Fronteiras Não Dominadas");
        for (int i = 0; i < fronts.size(); i++) {
            //System.out.println(fronts.get(i));
        }
        //System.out.println("Tamanho = " + fronts.size());
    }

    public static void FNDS3(List<Solution> Pop, List<List<Solution>> fronts) {
        List<Solution> PopAux = new ArrayList<>();//lista para remover soluções
        PopAux.addAll(Pop);
        fronts.clear();
        List<Solution> front = new ArrayList<>();//lista com apenas uma fronteira
        int contador = 0;

        while (PopAux.size() > 0) {
            fronts.add(new ArrayList<>());
            Dominancia(PopAux, front);
            //System.out.println("front = ");
            //ImprimePopulacao(front);

            fronts.get(contador).addAll(front);
            PopAux.removeAll(front);
            front.clear();
            contador++;
        }

        //System.out.println(fronts);
        //System.out.println("Tamanho de PopAux = " + PopAux.size());
        //Dominancia(PopAux,front);
        //ImprimePopulacao(front);
        for (int i = 0; i < fronts.size(); i++) {
            //System.out.println(i);
            front.addAll(fronts.get(i));
            for (Solution s : front) {
                s.seteDom(i);
            }
            //System.out.println(front.size());
            front.clear();
        }
        //System.out.println("\n");
        Pop.clear();
        for (List<Solution> f : fronts) {
            Pop.addAll(f);
            //System.out.println(f);
        }
    }

    public static void ReduzPopulacao(List<Solution> Pop_linha, List<List<Solution>> fronts, int TamPop) {
        for (int i = 0; i < fronts.size(); i++) {
            Pop_linha.addAll(fronts.get(i));
            ImprimePopulacao(Pop_linha);
            System.out.println("\n");

            if (Pop_linha.size() > TamPop) {//confere se ao adicinar a fronteira i, ultrapassa o tamanho original da populacao
                System.out.println("Reduzir!!!!!!!!!!");
            }
        }
    }

    public static Solution MOVND(Solution s_0, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n,
            Integer Qmax, Integer TimeWindows) {

        Random rnd = new Random();
        Solution melhor = new Solution(s_0);
        Solution s_linha = new Solution();
        Solution s = new Solution();
        int cont1 = 0;
        int k, r;
        r = 4;
        //r = 6;
        k = 1;

        while (k <= r) {
            System.out.println("k = " + k);
            s.setSolucao(primeiroMelhorVizinhoMO(s_0, k, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            //System.out.println(s);
            //s.setSolucao(melhorVizinho(s_0,k,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
            if (((s.getF1() < melhor.getF1()) && (s.getF2() < melhor.getF2())) || ((s.getF1() < melhor.getF1()) && (s.getF2() == melhor.getF2()))
                    || ((s.getF1() == melhor.getF1()) && (s.getF2() < melhor.getF2()))) {
                melhor.setSolucao(s);
                k = 1;
            } else {
                k = k + 1;
            }
            cont1++;
        }
        return melhor;
    }

    public static void buscaLocal(List<Solution> arquivo, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U,
            Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n,
            Integer Qmax, Integer TimeWindows) {
        Random rnd = new Random();
        int numeroSolucoes = arquivo.size() / 5;
        //numeroSolucoes = 10;
        //System.out.println("Numero solucoes para busca = " + numeroSolucoes);

        List<Solution> naoDominados = new ArrayList<>();
        Dominancia(arquivo, naoDominados);
        numeroSolucoes = naoDominados.size() / 5;
        //ImprimePopulacao(naoDominados);
        for (int i = 0; i < numeroSolucoes; i++) {
//            int posicao = rnd.nextInt(arquivo.size());
//            System.out.println(posicao);
//            Solution s = new Solution(MOVND(arquivo.get(posicao), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
//            arquivo.get(posicao).setSolucao(s);
            int posicao = rnd.nextInt(naoDominados.size());
            System.out.println(posicao);
            Solution s = new Solution(MOVND(naoDominados.get(posicao), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            arquivo.add(s);
        }

    }

    public static Solution MOILS(Solution s_0, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows) {
        //Solução inicial já é gerada pelo GA
        Solution s = new Solution(s_0);
        Solution s_linha = new Solution();
        Solution s_2linha = new Solution();
        List<Solution> historico = new ArrayList<>();
        int MAXITER = 20;

        //BuscaLocal
        s.setSolucao(MOVND(s_0, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        //s.setSolucao(primeiroMelhorVizinho(s_0,2,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
        int cont = 0;
        while (cont < MAXITER) {
            //System.out.println("Entrou no laço do ILS\tFO = " + s.getfObjetivo());

            System.out.println("Interação MOILS = " + cont);

            //Perturbação
            s_linha.setSolucao(Perturbacao(s, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
            //System.out.println("Apos perturbação s'= " + s_linha);

            //BuscaLocal
            s_2linha.setSolucao(MOVND(s_linha, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            //s_2linha.setSolucao(primeiroMelhorVizinho(s_0,2,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
            //System.out.println("Apos busca local s'' = " + s_2linha);
            //CriterioAceitacao
            if ((s_2linha.getF1() < s_0.getF1()) && (s_2linha.getF2() < s_0.getF2())
                    || ((s_2linha.getF1() < s_0.getF1()) && (s_2linha.getF2() == s_0.getF2()))
                    || ((s_2linha.getF1() == s_0.getF1()) && (s_2linha.getF2() < s_0.getF2()))) {
                //System.out.println("s_0 = " + s_0.getfObjetivo());

                //System.out.println("s_0 = " + s_0.getfObjetivo());//System.out.println("s_2linha = "+s_2linha.getfObjetivo());
                s.setSolucao(s_2linha);
                s_0.setSolucao(s_2linha);
                //historico.add(s_2linha);
                System.out.println("s_0 = " + s_0);
                //System.out.println("Atualizou\tFO = " + s.getfObjetivo1() );

                //System.out.println("Tamanho Historico =  " + historico.size() );
                //return s_0;
            }

            cont++;
        }
        //Collections.sort(historico);
        //System.out.println("Historico = ");
        System.out.println("Soluçao retornada do ILS = " + s_0);
        //ImprimePopulacao(historico);
        //s.setSolucao(CopiaMelhorSolucao(historico,s));
        //s.setSolucao(historico.get(1));
        return s_0;
    }

    public static Solution primeiroMelhorVizinhoMO(Solution s, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solution melhor = new Solution(s);

        Solution aux = new Solution();

        List<Integer> original = new ArrayList<Integer>(s.getRotaConcatenada());

        List<Integer> vizinho = new ArrayList<Integer>();

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        switch (tipoMovimento) {
            case 1: // troca						

                for (int i = 0; i < original.size() - 1; i++) {
                    for (int j = i + 1; j < original.size(); j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != vizinho.get(j)) {
                            Collections.swap(vizinho, i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI TROCA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }
                        vizinho.clear();
                    }
                }
                break;

            case 2: // substituicao

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 1; j < n; j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != j) {
                            vizinho.set(i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI INSERCAO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }

                        vizinho.clear();
                    }
                }
                break;

            case 3: // deslocamento

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 0; j < original.size(); j++) {
                        if (i != j) {
                            vizinho.addAll(original);
                            vizinho.remove(i);
                            vizinho.add(j, original.get(i));

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }
                        vizinho.clear();
                    }
                }

                break;

            case 4: // aleatoria

                Random r1 = new Random();

                int escolhaVizinho;

                Random r2 = new Random(System.nanoTime());

                int qtd = (int) (0.1 * (original.size() * original.size()));

                int elemento,
                 posicao,
                 posicao1,
                 posicao2;

                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...

                    vizinho.addAll(original);

                    escolhaVizinho = r1.nextInt(120);

                    if (escolhaVizinho < 20 || escolhaVizinho >= 60 && escolhaVizinho < 80) {
                        //Troca

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (vizinho.get(posicao1) == vizinho.get(posicao2));

                        Collections.swap(vizinho, posicao1, posicao2);

                    } else if (escolhaVizinho >= 20 && escolhaVizinho < 40 || escolhaVizinho >= 80 && escolhaVizinho < 100) {
                        //Insercao

                        posicao = r1.nextInt(original.size());

                        do {
                            elemento = r2.nextInt(n);
                        } while (elemento == 0 || elemento == vizinho.get(posicao));

                        vizinho.set(posicao, elemento);
                    } else {
                        //Movimento

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (posicao1 == posicao2);

                        vizinho.remove(posicao1);
                        vizinho.add(posicao2, original.get(posicao1));
                    }

                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                    if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                        //System.out.println("ACHEI ALEATORIA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                        melhor.setSolucao(aux);
                        return melhor;

                    }

                    vizinho.clear();
                }

                break;

            case 5:
                for (int i = 0; i < original.size() - 1; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 2));

                            vizinho.subList(i, i + 2).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;
            case 6:
                for (int i = 0; i < original.size() - 2; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1) && (j != i + 2)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 3));

                            vizinho.subList(i, i + 3).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;
        }

        return melhor;
    }

    public static Solution melhorVizinho(Solution s, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solution melhor = new Solution(s);

        Solution aux = new Solution();

        List<Integer> original = new ArrayList<Integer>(s.getRotaConcatenada());

        List<Integer> vizinho = new ArrayList<Integer>();

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        switch (tipoMovimento) {
            case 1: // troca						

                for (int i = 0; i < original.size() - 1; i++) {
                    for (int j = i + 1; j < original.size(); j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != vizinho.get(j)) {
                            Collections.swap(vizinho, i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI TROCA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;

            case 2: // substituicao

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 1; j < n; j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != j) {
                            vizinho.set(i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI INSERCAO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }

                        vizinho.clear();
                    }
                }
                break;

            case 3: // deslocamento

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 0; j < original.size(); j++) {
                        if (i != j) {
                            vizinho.addAll(original);
                            vizinho.remove(i);
                            vizinho.add(j, original.get(i));

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;

            case 4: // aleatoria

                Random r1 = new Random();

                int escolhaVizinho;

                Random r2 = new Random(System.nanoTime());

                int qtd = (int) (0.1 * (original.size() * original.size()));

                int elemento,
                 posicao,
                 posicao1,
                 posicao2;

                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...

                    vizinho.addAll(original);

                    escolhaVizinho = r1.nextInt(120);

                    if (escolhaVizinho < 20 || escolhaVizinho >= 60 && escolhaVizinho < 80) {
                        //Troca

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (vizinho.get(posicao1) == vizinho.get(posicao2));

                        Collections.swap(vizinho, posicao1, posicao2);

                    } else if (escolhaVizinho >= 20 && escolhaVizinho < 40 || escolhaVizinho >= 80 && escolhaVizinho < 100) {
                        //Insercao

                        posicao = r1.nextInt(original.size());

                        do {
                            elemento = r2.nextInt(n);
                        } while (elemento == 0 || elemento == vizinho.get(posicao));

                        vizinho.set(posicao, elemento);
                    } else {
                        //Movimento

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (posicao1 == posicao2);

                        vizinho.remove(posicao1);
                        vizinho.add(posicao2, original.get(posicao1));
                    }

                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                    if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                        //System.out.println("ACHEI ALEATORIA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                        melhor.setSolucao(aux);

                    }

                    vizinho.clear();
                }

                break;

            case 5:
                for (int i = 0; i < original.size() - 1; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 2));

                            vizinho.subList(i, i + 2).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;
            case 6:
                for (int i = 0; i < original.size() - 2; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1) && (j != i + 2)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 3));

                            vizinho.subList(i, i + 3).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if ((aux.getF1() < melhor.getF1()) && (aux.getF2() < melhor.getF2())) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;
        }

        return melhor;
    }

}
