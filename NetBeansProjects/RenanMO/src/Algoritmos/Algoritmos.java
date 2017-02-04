package Algoritmos;

import static Algoritmos.Funcoes.AdicionaNo;
import static Algoritmos.Funcoes.AnaliseSolicitacoesViaveisEmU;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
//import static principal.PrincipalRenan.buscaLocal;
//import static principal.PrincipalRenan.buscaTabu;

import representacao.Request;
import representacao.Rota;
import representacao.Solucao;
import static Algoritmos.Funcoes.SeparaOrigemDestino;
import static Algoritmos.Funcoes.CalculaCRL;
import static Algoritmos.Funcoes.CalculaDRL;
import static Algoritmos.Funcoes.CalculaListaSemNosViaveis;
import static Algoritmos.Funcoes.CalculaNRF;
import static Algoritmos.Funcoes.CalculaNRL;
import static Algoritmos.Funcoes.CalculaTRL;
import static Algoritmos.Funcoes.CopiaMelhorSolucao;
import static Algoritmos.Funcoes.Cruzamento;
import static Algoritmos.Funcoes.Cruzamento2Pontos;
import static Algoritmos.Funcoes.Desembarca;
import static Algoritmos.Funcoes.Embarca;
import static Algoritmos.Funcoes.EmbarcaRelaxacao;
import static Algoritmos.Funcoes.EncontraNosViaveis;
import static Algoritmos.Funcoes.FinalizaRota;
import static Algoritmos.Funcoes.Fitness;
import static Algoritmos.Funcoes.ImprimePopulacao;

import static Algoritmos.Funcoes.ProcuraSolicitacaoParaAtender;
import static Algoritmos.Funcoes.RetiraSolicitacaoNaoSeraAtendida;
import static Algoritmos.Funcoes.RetiraSolicitacoesInviaveis;
import static Algoritmos.Inicializacao.CarregaSolucao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;
import static Algoritmos.Funcoes.InicializaPopulacao;
import static Algoritmos.Funcoes.InicializaPopulacaoPerturbacao;
import static Algoritmos.Funcoes.InsereMelhorIndividuo;
import static Algoritmos.Funcoes.Mutacao;
import static Algoritmos.Funcoes.Mutacao2Opt;
import static Algoritmos.Funcoes.Mutacao2Shuffle;
import static Algoritmos.Funcoes.MutacaoILS;
import static Algoritmos.Funcoes.MutacaoShuffle;
import static Algoritmos.Funcoes.NewFitness;
import static Algoritmos.Funcoes.OrdenaPopulacao;
import static Algoritmos.Funcoes.Selecao;
import static Algoritmos.Funcoes.buscaTabu;
import static Algoritmos.Funcoes.melhorVizinho;
import static Algoritmos.Funcoes.primeiroMelhorVizinho;
import static Algoritmos.Funcoes.primeiroMelhorVizinhoAleatorio;
import static Algoritmos.Funcoes.vizinhoAleatorio;
import java.time.Clock;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

public class Algoritmos {

    public static void floydWarshall(List<List<Integer>> c, List<List<Integer>> d, Integer n) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (c.get(i).get(j) > c.get(i).get(k) + c.get(k).get(j)) {
                        c.get(i).set(j, c.get(i).get(k) + c.get(k).get(j));
                        d.get(i).set(j, d.get(i).get(k) + d.get(k).get(j));
                    }
                }
            }
        }
    }

    public static double FuncaoDeAvaliacao(Solucao S, List<Request> listOfRequests, List<List<Integer>> c) {
        double avalicao = 0;
        double V = 12;
        //double alfa = S.getListaNaoAtendimento().size();
        double alfa = 10 * listOfRequests.size();
        double beta = 1 / 10;
        double gama = 1;//12 é o número de pontos de parada incluindo o depósito

        avalicao = S.getfObjetivo1() + alfa * S.getfObjetivo4() + beta * S.getfObjetivo8() + gama * S.getfObjetivo9();

        return avalicao;
    }

    //Função Objetivo
    public static int FO(Solucao S, List<List<Integer>> c) {
        int totalCost = 0;

        int W = 1000,
                costU = 800;//200;

        for (Rota r : S.getConjRotas()) {
            totalCost += W;
            for (int i = 0, j = r.getListaVisitacao().size(); i < j - 1; i++) {
                totalCost += c.get(r.getListaVisitacao().get(i)).get(r.getListaVisitacao().get(i + 1));
            }
        }

        totalCost += S.getListaNaoAtendimento().size() * costU;

        return totalCost;
    }

    public static int FOp(Solucao S, List<List<Integer>> c) {
        int totalCost = 0;
        int tempoMaximo = 3;
        int PENALIDADE = 2000;
        int W = 1000,
                costU = 10000;//200;//800

        for (Rota r : S.getConjRotas()) {
            totalCost += W;
            for (int i = 0, j = r.getListaVisitacao().size(); i < j - 1; i++) {
                totalCost += c.get(r.getListaVisitacao().get(i)).get(r.getListaVisitacao().get(i + 1));
            }
        }

        totalCost += S.getListaNaoAtendimento().size() * costU;

        int somaTotal = 0;
        for (Rota r : S.getConjRotas()) {
            int soma = 0;

            for (Request request : r.getListaAtendimento()) {
                //System.out.println("Solicitação = " + request);
                if (request.getDeliveryTime() > request.getDeliveryL()) {
                    int dif = request.getDeliveryTime() - request.getDeliveryL();
                    if (dif < tempoMaximo) {
                        totalCost += dif * PENALIDADE;
                    } else {
                        totalCost += 15000;
                    }
                    soma += dif;
                }
            }
            r.setTempoExtra(soma);
            somaTotal += soma;
        }
        S.setTempoExtraTotal(somaTotal);
        return totalCost;
    }

    public static int FO1(Solucao S, List<List<Integer>> c) {
        int totalCost = 0;
        //o custo do veiculo é de 1000
        int W = 1000,//1000,
                costU = 0;//800;//200;

        for (Rota r : S.getConjRotas()) {
            totalCost += W;
            for (int i = 0, j = r.getListaVisitacao().size(); i < j - 1; i++) {
                totalCost += c.get(r.getListaVisitacao().get(i)).get(r.getListaVisitacao().get(i + 1));
            }
        }

        totalCost += S.getListaNaoAtendimento().size() * costU;

        return totalCost;
    }

    public static int FO2(Solucao S, List<List<Integer>> c) {
        int somaTotal = 0;

        for (Rota r : S.getConjRotas()) {
            int soma = 0;

            for (Request request : r.getListaAtendimento()) {
                //System.out.println("Solicitação = " + request);
                if (request.getDeliveryTime() > request.getDeliveryL()) {
                    int dif = request.getDeliveryTime() - request.getDeliveryL();
                    /*if(dif<tempoMaximo){
                     totalCost += dif*PENALIDADE;
                     }else{
                     totalCost += 15000;
                     }*/
                    soma += dif;
                }
            }
            r.setTempoExtra(soma);
            somaTotal += soma;
        }
        return somaTotal;
    }

    public static int FO3(Solucao S) {
        int diferenca;
        Rota maiorRota = new Rota(Collections.max(S.getConjRotas()));
        Rota menorRota = new Rota(Collections.min(S.getConjRotas()));
        diferenca = maiorRota.getListaAtendimento().size() - menorRota.getListaAtendimento().size();

        return diferenca;
        //S.setDif(maiorRota.getListaAtendimento().size() - menorRota.getListaAtendimento().size());

    }

    public static int FO4(Solucao S) {
        return S.getListaNaoAtendimento().size();
    }

    public static int FO5(Solucao S) {
        return S.getConjRotas().size();
    }

    public static int FO6(Solucao S) {
        Set<Rota> rotas = new HashSet<>();
        rotas.addAll(S.getConjRotas());
        int soma = 0;
        for (Rota r : rotas) {
            for (Request request : r.getListaAtendimento()) {
                soma += request.getDeliveryTime() - request.getPickupTime();
            }
        }
        return soma;
    }

    public static int FO7(Solucao S) {
        Set<Rota> rotas = new HashSet<>();
        rotas.addAll(S.getConjRotas());
        int soma = 0;
        for (Rota r : rotas) {
            for (Request request : r.getListaAtendimento()) {
                soma += request.getPickupTime() - request.getPickupE();
            }
        }
        return soma;
    }

    public static int FO8(Solucao S) {
        Set<Rota> rotas = new HashSet<>();
        rotas.addAll(S.getConjRotas());
        int soma = 0;
        for (Rota r : rotas) {
            for (Request request : r.getListaAtendimento()) {
                soma += Math.abs(Math.max(request.getDeliveryE() - request.getDeliveryTime(), 0));
            }
        }
        return soma;
    }

    public static int FO9(Solucao S) {
        Set<Rota> rotas = new HashSet<>();
        rotas.addAll(S.getConjRotas());
        int soma = 0;
        for (Rota r : rotas) {
            for (Request request : r.getListaAtendimento()) {
                soma += Math.max(request.getDeliveryTime() - request.getDeliveryL(), 0);
            }
        }
        return soma;
    }

    public static void FOagregados(Solucao S, double alfa, double beta, double gama, double delta, double epslon) {
        //Agregações feitas (combinações lineares) com base na Análise de Componentes Principais
//        S.setF1(0.0066222948*S.getfObjetivo1() + 0.6240729533*S.getfObjetivo2() + 0.0005134346*S.getfObjetivo3() + 0.0001470923*S.getfObjetivo4() 
//                - 0.0005010187*S.getfObjetivo5() + 0.7813356030*S.getfObjetivo6() + 0.0017981648*S.getfObjetivo7());
//        
//        S.setF2(- 0.0096951156*S.getfObjetivo1() - 0.7812557639*S.getfObjetivo2() + 0.0002265355*S.getfObjetivo3() - 0.0035572217*S.getfObjetivo4() 
//                + 0.0007717224*S.getfObjetivo5() + 0.6241041415*S.getfObjetivo6() - 0.0051142916*S.getfObjetivo7());

        //Agregação feita com base na análise de cluster, utilizando a matriz de correlação amostral Rij
        S.setF1(800 * S.getfObjetivo5() + 500 * S.getfObjetivo4() + S.getfObjetivo6() + 20 * S.getfObjetivo7());
        S.setF2(S.getfObjetivo1() + S.getfObjetivo2() + 20 * S.getfObjetivo3());

//        S.setF1(alfa * S.getfObjetivo2() + /*beta**/ 10 * S.getfObjetivo3() + /*delta**/ 50 * S.getfObjetivo4());
//        S.setF2(gama * S.getfObjetivo1() +/*epslon*/ 800 * S.getfObjetivo5());
    }

    // Algoritmo Construtivo Guloso
    public static Solucao greedyConstructive(Double alphaD, Double alphaP, Double alphaV, Double alphaT, List<Request> listRequests,
            Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K,
            List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        P.clear();
        U.clear();
        P.addAll(listRequests);

        //Step 1
        Solucao S = new Solucao();
        String log = "";

        int currentK;
        Map<Integer, Double> CRL = new HashMap<>(n), // Cost Rank List
                NRL = new HashMap<>(n), // Number of Passengers Rank List
                DRL = new HashMap<>(n), // Delivery time-window Rank List
                TRL = new HashMap<>(n), // Time-window Rank List
                NRF = new HashMap<>(n);	// Time-window Rank List

        Iterator<Integer> itK = K.iterator();
        U.clear();
        while (!P.isEmpty() && itK.hasNext()) {

            SeparaOrigemDestino(U, Pin, Pout, n, P);

            //Step 2
            Rota R = new Rota();
            currentK = itK.next();
            log += "\tGROTA " + (currentK + 1) + " ";

            //Step 3
            R.addVisitacao(0);

            currentTime = 0;
            //-------------------------------------------------------------------
            double max, min;
            //Integer  lastNode = R.getLastNode();
            lastNode = R.getLastNode();

            boolean encontrado;

            while (!P.isEmpty()) {
                encontrado = false;
                m.clear();
                for (int i = 0; i < n; i++) {
                    m.add(Pin.get(i).size() - Pout.get(i).size());
                }

                //Step 4
                Set<Integer> FeasibleNode = new HashSet<>();
                List<Integer> EarliestTime = new ArrayList<>();

                EncontraNosViaveis(n, lastNode, encontrado, Qmax, R, Pin, Pout, FeasibleNode, d, currentTime, TimeWindows);

                //System.out.println("FEASIBLE NODES = "+ FeasibleNode);			
                if (FeasibleNode.size() > 1) {
                    //Step 4.1
                    CalculaCRL(FeasibleNode, CRL, c, lastNode);
                    //Step 4.2
                    CalculaNRL(FeasibleNode, NRL, m, lastNode);
                    //Step 4.3
                    CalculaDRL(FeasibleNode, DRL, Pout, lastNode, d, EarliestTime);
                    //Step 4.4
                    CalculaTRL(FeasibleNode, TRL, Pin, lastNode, d, EarliestTime);
                } else {
                    //Step 4.1
                    CalculaListaSemNosViaveis(CRL, FeasibleNode);
                    //Step 4.2
                    CalculaListaSemNosViaveis(NRL, FeasibleNode);
                    //Step 4.3
                    CalculaListaSemNosViaveis(DRL, FeasibleNode);
                    //Step 4.4
                    CalculaListaSemNosViaveis(TRL, FeasibleNode);
                }

                //Step 5
                CalculaNRF(NRF, CRL, NRL, DRL, TRL, alphaD, alphaP, alphaV, alphaT, FeasibleNode);

                //Step 6              
                //System.out.println("Tamanho da NRF = " + NRF.size());              
                max = Collections.max(NRF.values());

                currentTime = AdicionaNo(NRF, CRL, NRL, DRL, TRL, max, lastNode, Pin, d, EarliestTime, currentTime, R);
                lastNode = R.getLastNode();

                //Step 7
                //RETIRAR A LINHA DE BAIXO DEPOIS - inicialização de listRequestAux
                List<Request> listRequestAux = new LinkedList<>();
                //Desembarca as solicitações no nó 
                Desembarca(Pin, Pout, lastNode, currentTime, P, listRequestAux, R, log);
                //Embarca as solicitações sem tempo de espera
                Embarca(Pin, lastNode, currentTime, P, listRequestAux, R, log, Qmax);
                //Embarca agora as solicitações onde o veículo precisar esperar e guarda atualiza o tempo (currentTime)                               
                currentTime = EmbarcaRelaxacao(Pin, lastNode, currentTime, P, listRequestAux, R, log, Qmax, TimeWindows);

                //---------- Trata as solicitações inviáveis -----------
                RetiraSolicitacoesInviaveis(Pin, Pout, listRequestAux, currentTime, P, U);
                encontrado = ProcuraSolicitacaoParaAtender(R, Qmax, Pin, Pout, currentTime, n, d, lastNode, TimeWindows, encontrado);
                RetiraSolicitacaoNaoSeraAtendida(encontrado, Pin, Pout, listRequestAux, currentTime, P, U);

                //Step 8
                currentTime = FinalizaRota(P, R, currentTime, lastNode, d, S);
            }

            //Step 9
            AnaliseSolicitacoesViaveisEmU(U, P, itK, d);
        }

        //S.setFuncaoObjetivo(FuncaoObjetivo(S,c));
        S.setListaNaoAtendimento(U);
        S.setfObjetivo1(FO1(S, c));
        S.setfObjetivo2(FO2(S, c));
        S.setfObjetivo3(FO3(S));
        S.setfObjetivo4(FO4(S));
        S.setfObjetivo5(FO5(S));
        S.setfObjetivo6(FO6(S));
        S.setfObjetivo7(FO7(S));
        S.setfObjetivo8(FO8(S));
        S.setfObjetivo9(FO9(S));
        FOagregados(S, 1, 1, 1, 1, 1);
        S.setLogger(log);
        S.concatenarRota();
        //S.setfObjetivo1((int) FuncaoObjetivo(S, c));
        S.setFuncaoObjetivo(FuncaoDeAvaliacao(S, listRequests, c));

        return S;
    }

    /**
     * Avalia Vizinho
     *
     */
    public static Solucao avaliaSolucao(List<Integer> vizinho, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U,
            Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c,
            Integer n, Integer Qmax, Integer TimeWindows) {
        P.clear();
        P.addAll(listRequests);

        //Step 1
        Solucao S = new Solucao();
        S.setRotaConcatenada(vizinho);
        String log = "";

        int currentK;

        Iterator<Integer> itK = K.iterator();
        U.clear();
        //Pin.clear();
        //Pout.clear();

        List<Request> auxP = new LinkedList<>(P);
        for (Request request : auxP) {//para cada solicitação, olha se apos o movimento nn contem os nos de embarque, desembarque e a janela de tempo
            if (!vizinho.contains(request.getOrigin()) || !vizinho.contains(request.getDestination()) || !(d.get(0).get(request.getOrigin()) <= request.getPickupL())) {
                U.add((Request) request.clone());
                P.remove((Request) request.clone());
            }
        }

        while (!P.isEmpty() && itK.hasNext() && !vizinho.isEmpty()) {
            /*do{*/
            //U.clear();
            //SeparaOrigemDestino(U,Pin,Pout,n,P);
            //----------------------------------------------------------------------------------------------------------
            //     Tomar cuidado com essa parte aqui, modularizando ela o funcionamento alterou significativamente   
            //----------------------------------------------------------------------------------------------------------
            Pin.clear();
            Pout.clear();
            List<Request> origem = new LinkedList<Request>();
            List<Request> destino = new LinkedList<Request>();
            for (int j = 0; j < n; j++) {

                for (Request request : P) {
                    if (request.getOrigin() == j) {
                        origem.add((Request) request.clone());
                    }
                    if (request.getDestination() == j) {
                        destino.add((Request) request.clone());
                    }
                }

                Pin.put(j, new LinkedList<Request>(origem));
                Pout.put(j, new LinkedList<Request>(destino));

                origem.clear();
                destino.clear();
            }
            //----------------------------------------------------------------------------------------------------------
            //Step 2
            Rota R = new Rota();
            currentK = itK.next();
            log += "\tROTA " + (currentK + 1) + " ";

            /*if(currentK+1 == 3)
             System.out.println("ROTA BREMA");*/
            //Step 3
            R.addVisitacao(0);
            int currentTime = 0;

            Integer lastNode = R.getLastNode();
            Integer newNode;
            boolean encontrado;

            while (!P.isEmpty()) {

//				lastNode = R.getLastNode();
                newNode = -1;
                encontrado = false;

                //List<Integer> vizinhoCopia = new ArrayList<Integer>(vizinho);
                for (int k = 0; !encontrado && k < vizinho.size(); k++) {
                    int i = vizinho.get(k);

                    if (i != lastNode) {
                        if (R.getLotacaoAtual() < Qmax) {
                            for (Request request : Pin.get(i)) {
                                if (lastNode == 0 && d.get(lastNode).get(i) <= request.getPickupL() && vizinho.contains(request.getDestination())) {
                                    newNode = vizinho.remove(k);
                                    encontrado = true;
                                    break;
                                }
                                //if( (currentTime + d.get(lastNode).get(i)) <= request.getPickupL()){
                                if (currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                                        && currentTime + d.get(lastNode).get(i) <= request.getPickupL() && vizinho.contains(request.getDestination())) {
                                    newNode = vizinho.remove(k);
                                    encontrado = true;
                                    break;
                                }
                            }
                        }

                        /**
                         * E OS N�S DE ENTREGA? DEVEM SER VI�VEIS TAMB�M?*
                         */
                        if (!encontrado && R.getLotacaoAtual() > 0) {
                            for (Request request : Pout.get(i)) {
                                if (!Pin.get(request.getOrigin()).contains(request)) {
                                    newNode = vizinho.remove(k);
                                    encontrado = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (newNode == -1) {
                    System.out.println("newNode Invalido");
                }
                //-------------------------------------------------------------------------------------------------
                //Step 6
                List<Integer> EarliestTime = new ArrayList<>();

                if (lastNode == 0) {
                    //System.out.println("VIZINHO PROBLEMATICO "+vizinho);
                    for (Request request : Pin.get(newNode)) {
                        if (d.get(lastNode).get(newNode) <= request.getPickupL() && vizinho.contains(request.getDestination())) {
                            EarliestTime.add(request.getPickupE());
                        }
                    }

                    currentTime = Math.max(Collections.min(EarliestTime) - d.get(lastNode).get(newNode), 0);
                    R.setTempoikDeposito(currentTime);

                    EarliestTime.clear();
                }

                currentTime += d.get(lastNode).get(newNode);

                R.addVisitacao(newNode);
                lastNode = R.getLastNode();

                //Step 7
                /**
                 * SOLICITA��ES J� ATENDIDAS *
                 */
                /*
                 *  se Qik > 0
                 *  	para cada request in P
                 *  		se request.destination == lastNode && R.getListaVisitacao().contains(request.origin) && currentTime > request.pickupL
                 * 				Qik--
                 * 				Pout.remove(request)
                 * 				P.remove(request)
                 * 				atualizar vari�veis que dependam de P
                 * 	
                 * 	se Qik < Qmax
                 * 		para cada request in P
                 *  		se request.origin == lastNode && 
                 *  			( (currentTime >= request.pickupE && currentTime <= request.pickupL) ||
                 *  				(currentTime+TimeWindows >= request.pickupE && currentTime+TimeWindows <= request.pickupL) )
                 * 				Qik++
                 * 				Pin.remove(request)
                 * 				atualizar vari�veis que dependam de P
                 * */
                List<Request> listRequestAux = new LinkedList<>(Pout.get(lastNode));

                for (Request request : listRequestAux) {

                    if (!Pin.get(request.getOrigin()).contains(request)) {
                        Pout.get(lastNode).remove((Request) request.clone());
                        P.remove((Request) request.clone());

                        //if(currentK == 1){
                        log += "ENTREGA: " + currentTime + ": " + (Request) request.clone() + " ";
                        //}

                        R.addDesembarque((Request) request.clone(), currentTime);

                        //EXTRA
                        log += "Q=" + R.getLotacaoAtual() + " ";
                    }
                }
                listRequestAux.clear();

                listRequestAux.addAll(Pin.get(lastNode));

                for (Request request : listRequestAux) {
                    if (R.getLotacaoAtual() < Qmax && currentTime >= request.getPickupE() && currentTime <= request.getPickupL() && vizinho.contains(request.getDestination())) {
                        Pin.get(lastNode).remove((Request) request.clone());

                        //if(currentK == 1){
                        /**
                         * ** Anexado a classe Rota
                         * S.addAtendimento((Request)request.clone()); **
                         */
                        log += "COLETA: " + currentTime + ": " + (Request) request.clone() + " ";
                        //}

                        R.addEmbarque((Request) request.clone(), currentTime);

                        //EXTRA
                        log += "Q=" + R.getLotacaoAtual() + " ";
                    }
                }

                listRequestAux.clear();

                listRequestAux.addAll(Pin.get(lastNode));

                int waitTime = TimeWindows;
                int aux;

                for (Request request : listRequestAux) {
                    if (R.getLotacaoAtual() < Qmax && currentTime + waitTime >= request.getPickupE() && currentTime + waitTime <= request.getPickupL() && vizinho.contains(request.getDestination())) {

                        aux = currentTime + waitTime - request.getPickupE();
                        currentTime = Math.min(currentTime + waitTime, request.getPickupE());
                        waitTime = aux;

                        Pin.get(lastNode).remove((Request) request.clone());

                        //if(currentK == 1){
                        /**
                         * ** Anexado a classe Rota
                         * S.addAtendimento((Request)request.clone()); **
                         */
                        log += "COLETAw: " + currentTime + ": " + (Request) request.clone() + " ";
                        //}

                        R.addEmbarque((Request) request.clone(), currentTime);

                        //EXTRA
                        log += "Q=" + R.getLotacaoAtual() + " ";
                    }
                }

                /**
                 * SOLICITA��ES INVI�VEIS *
                 */
                listRequestAux.clear();

                for (Integer key : Pin.keySet()) {

                    listRequestAux.addAll(Pin.get(key));

                    for (Integer i = 0, k = listRequestAux.size(); i < k; i++) {
                        Request request = listRequestAux.get(i);

                        if (currentTime > request.getPickupL() || !vizinho.contains(request.getOrigin()) || !vizinho.contains(request.getDestination())) {
                            U.add((Request) request.clone());
                            P.remove((Request) request.clone());
                            Pin.get(key).remove((Request) request.clone());
                            Pout.get(request.getDestination()).remove((Request) request.clone());
                        }
                    }
                    listRequestAux.clear();

                }

                encontrado = false;
                for (int k = 0; !encontrado && k < vizinho.size(); k++) {
                    int i = vizinho.get(k);

                    if (i != lastNode) {

                        if (R.getLotacaoAtual() < Qmax) {
                            for (Request request : Pin.get(i)) {
                                if (currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                                        && currentTime + d.get(lastNode).get(i) <= request.getPickupL() && vizinho.contains(request.getDestination())) {
                                    encontrado = true;
                                    break;
                                }
                            }
                        }

                        /**
                         * E OS N�S DE ENTREGA? DEVEM SER VI�VEIS TAMB�M?*
                         */
                        if (!encontrado && R.getLotacaoAtual() > 0) {
                            for (Request request : Pout.get(i)) {
                                if (!Pin.get(request.getOrigin()).contains(request)) {
                                    encontrado = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!encontrado) {
                    for (Integer key : Pin.keySet()) {

                        listRequestAux.addAll(Pin.get(key));

                        for (Integer i = 0, k = listRequestAux.size(); i < k; i++) {
                            Request request = listRequestAux.get(i);

                            U.add((Request) request.clone());
                            P.remove((Request) request.clone());
                            Pin.get(key).remove((Request) request.clone());
                            Pout.get(request.getDestination()).remove((Request) request.clone());

                        }
                        listRequestAux.clear();

                    }
                }

                //Step 8
                if (P.isEmpty()) {
                    R.addVisitacao(0);
                    //log += R.toString()+"\n";
                    //System.out.println("Rota "+R+" - "+currentTime);
                    currentTime += d.get(lastNode).get(0);
                    S.getConjRotas().add(R);
                }

            }

            //Step 9
            if (!U.isEmpty() && itK.hasNext()) {
                List<Request> auxU = new LinkedList<>(U);
                for (Request request : auxU) {
                    if (vizinho.contains(request.getOrigin()) && vizinho.contains(request.getDestination()) && d.get(0).get(request.getOrigin()) <= request.getPickupL()) {
                        P.add((Request) request.clone());
                        U.remove((Request) request.clone());
                    }
                }
            }

            /*}while(!U.isEmpty());*/
        }
        //System.out.print("Usize = "+U.size()+"\n"+FO(S,U.size())+" -> ");
        S.setListaNaoAtendimento(U);
        S.setfObjetivo1(FO1(S, c));
        S.setfObjetivo2(FO2(S, c));
        S.setfObjetivo3(FO3(S));
        S.setfObjetivo4(FO4(S));
        S.setfObjetivo5(FO5(S));
        S.setfObjetivo6(FO6(S));
        S.setfObjetivo7(FO7(S));
        S.setfObjetivo8(FO8(S));
        S.setfObjetivo9(FO9(S));
        FOagregados(S, 1, 1, 1, 1, 1);
        S.setLogger(log);
        //S.setfObjetivo1((int) FuncaoObjetivo(S, c));
        S.setFuncaoObjetivo(FuncaoDeAvaliacao(S, listRequests, c));
        //System.out.println(FO(S,U.size())+"\t"+U.size());
        //System.out.println(FO(S)+"\t"+S.getListaNaoAtendimento().size());
        //if(ativaLog)
        //	System.out.println(S.getLogger());

        return S;
    }

    public static void GeneticAlgorithm(List<Solucao> Pop, Integer TamPop, Integer MaxGer, double Pm, double Pc, List<Request> listRequests,
            Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K,
            List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        String diretorio, nomeArquivo;
        try {
            //Inicializar população
            //InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
            //Fitness(Pop);
            //OrdenaPopulacao(Pop);

            //System.out.println("População Inicial");
            //ImprimePopulacao(Pop);
            Solucao SBest = new Solucao();
            SBest.setFuncaoObjetivo(1000000000);
            SBest.setfObjetivo1(1000000000);
            SBest.setfObjetivo2(1000000000);

            //System.out.println("Teste do elitismo, SBest = "+ SBest);
            List<Integer> pais = new ArrayList<>();
            double tempoInicio, tempoFim;
            //SBest = CopiaMelhorSolucao(Pop, SBest);
            //System.out.println("Melhor Individuo = " + SBest);
            int somaTotal;
            double media, desvio;
            diretorio = "\\GA_Funcao_Avaliação";
            nomeArquivo = "Teste_GA";
            boolean success = (new File(diretorio)).mkdirs();
            if (!success) {
                System.out.println("Diretórios ja existem!"); 
            }
            PrintStream saida;
            saida = new PrintStream(diretorio + "\\GA-DOUTORADO" + nomeArquivo + ".txt");

            for (int cont = 0; cont < 1; cont++) {
                //--------------- Inicializa com a mesma população inicial ------------------
                InicializaPopulacaoPerturbacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                
                
                Pop.sort(Comparator.comparingDouble(Solucao::getFuncaoObjetivo));
                NewFitness(Pop);
                //OrdenaPopulacao(Pop);
                

                //CarregaSolucao(Pop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
//                double x, y, z, w;
//                Solucao S = new Solucao();
//                x = 0.5402697457767974;
//                y = 0.12127711977568245;
//                z = 0.17712922815436938;
//                w = 0.16132390629315074;
//                S.setSolucao(greedyConstructive(x, y, z, w, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));

                //MÉTODO NOVO DAS SOLUÇÕES MELHORES
                //GeraPopGulosa(Pop,TamPop,listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                //Fitness(Pop);
                //OrdenaPopulacao(Pop);
                ImprimePopulacao(Pop);
                SBest.setFuncaoObjetivo(1000000000);
                SBest.setfObjetivo2(1000000000);
                //SBest.setSolucao(S);
                //System.out.println("População Inicial");
                //ImprimePopulacao(Pop);

                System.out.println("Execução = " + cont);
                int GerAtual = 0;
                while (GerAtual < MaxGer) {
                    //Ordenação da população
                    OrdenaPopulacao(Pop);

                    //Cálculo do fitness - aptidão
                    NewFitness(Pop);
                    SBest = CopiaMelhorSolucao(Pop, SBest);
                    saida.print(SBest.getFuncaoObjetivo() + "\t");

                    //Selecionar
                    Selecao(pais, Pop, TamPop);
                    //Cruzamento
                    //Cruzamento(Pop, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    
                    Cruzamento2Pontos(Pop, Pop, TamPop, Pc, pais, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows);
                    //Mutação
                    //Mutacao(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //Mutacao2Opt(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //MutacaoShuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);                   
                    Mutacao2Shuffle(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
                    //MutacaoILS(Pop, Pm, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);

                    //Elitismo
                    InsereMelhorIndividuo(Pop, SBest);
                    NewFitness(Pop);

                    //BuscaLocal
                    //System.out.println("GerAtual = " + GerAtual);
//                    if ((GerAtual % 150 == 0) && (GerAtual != 0)) {
//                        Solucao s = new Solucao(SBest);
//                        SBest.setSolucao(ILS(s, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
//                    }
                    System.out.println("Geração = " + GerAtual + "\tMelhorFO = " + SBest.getFuncaoObjetivo());
                    GerAtual++;
                }
                Pop.clear();
                saida.print(SBest + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        OrdenaPopulacao(Pop);
        Fitness(Pop);
        //System.out.println("Geração final = ");
        ImprimePopulacao(Pop);
    }

    public static void InicializaSolucaoArquivo(List<Solucao> Pop, String NomeArquivo, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {

        List<Integer> lista = new ArrayList<>();

        Solucao S = new Solucao();
        //S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        //Pop.get(i).setSolucao(S);

        try {
            FileReader arq = new FileReader("SolucaoInicial.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String[] linha = lerArq.readLine().split(",");

            //List<String> linha = new ArrayList<>();
            int no, cont;
            String teste;
            cont = 0;
            lista.clear();
            for (int i = 0; i < linha.length; i++) {
                no = Integer.parseInt(linha[i]);
                lista.add(no);
                teste = linha[i];
            }

            S.setSolucao(avaliaSolucao(lista, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            System.out.println("Solução = " + S);
            Pop.get(cont).setSolucao(S);

            //System.out.println("Pop(0) = " + Pop.get(0).getRotaConcatenada());
            while (linha != null) {
                cont++;
                lista.clear();

                //System.out.printf("%s\n", linha);
                linha = lerArq.readLine().split(",");
                for (int i = 0; i < linha.length; i++) {
                    no = Integer.parseInt(linha[i]);
                    teste = linha[i];
                    lista.add(no);
                    //System.out.println("Nó = " + no);
                }
                S.setSolucao(avaliaSolucao(lista, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                System.out.println("Solução = " + S);
                Pop.get(cont).setSolucao(S);
                //Pop.get(cont).setRotaConcatenada(lista);
                // System.out.println("Pop("+cont+") = " + Pop.get(cont).getRotaConcatenada());
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
        System.out.println();

    }

    public static Solucao VND(Solucao s_0, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n,
            Integer Qmax, Integer TimeWindows) {

        Random rnd = new Random();
        Solucao melhor = new Solucao(s_0);
        Solucao s_linha = new Solucao();
        Solucao s = new Solucao();
        int cont1 = 0;
        //Solucao aux = new Solucao();
        int k, r;
        //r = 4;
        r = 6;
        //k = 1 + rnd.nextInt(4);
        k = 1;

        while ((k <= r)/* && (cont1 <= 50)*/) {
            if (k == 4) {
                k++;
            }
            System.out.println("k = " + k);
            //System.out.println("iteração VND = " + cont1);
            //s.setSolucao(primeiroMelhorVizinho(s_0,k,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
            s.setSolucao(melhorVizinho(s_0, k, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            if (s.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                melhor.setSolucao(s);
                k = 1;
                //k = 1 + rnd.nextInt(4);
                //return melhor;
                //break;
            } else {
                k = k + 1;
                // k = 1 + rnd.nextInt(4);
            }
            cont1++;
        }
//        System.out.println("Soluçao retornada do VND = " + melhor);
        return melhor;
    }

    public static Solucao RVND(Solucao s_0, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n,
            Integer Qmax, Integer TimeWindows) {

        Solucao melhor = new Solucao(s_0);
        Solucao s = new Solucao();
        List<Integer> vizinhanca = new ArrayList<>();
        int k, r;
        r = 6;
        k = 1;

        for (int i = 0; i < r; i++) {
            if (i != 4) {
                vizinhanca.add(i + 1);
            }

        }
        Collections.shuffle(vizinhanca);

        //System.out.println("Vizinhança = " + vizinhanca);
        while ((k <= r)) {

            System.out.println("k = " + k + "\tN = " + vizinhanca.get(k - 1));
            //System.out.println("iteração VND = " + cont1);
            //s.setSolucao(primeiroMelhorVizinho(s_0,k,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
            s.setSolucao(melhorVizinho(s_0, vizinhanca.get(k - 1), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            if (s.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                melhor.setSolucao(s);
                k = 1;
            } else {
                k = k + 1;
            }
        }
        return melhor;
    }

    public static Solucao VNS(Solucao s_0, List<Request> listRequests, List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n,
            Integer Qmax, Integer TimeWindows) {

        Solucao melhor = new Solucao(s_0);
        Solucao s_linha = new Solucao();
        Solucao s_2linha = new Solucao();
        Solucao s = new Solucao();
        int cont = 0;
        int MAXCONT = 5;

        int k, r;
        r = 6;

        while (cont < MAXCONT) {
            k = 1;
            while ((k <= r)) {

                //s_linha.setSolucao(vizinhoAleatorio(s_0, k, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                s_2linha.setSolucao(VND(s_linha, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                if (s_2linha.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                    melhor.setSolucao(s_2linha);
                    k = 1;

                } else {
                    k = k + 1;
                }
                cont++;
            }
        }
        System.out.println("Soluçao retornada do VNS = " + melhor);
        return melhor;
    }

    public static Solucao Perturbacao(Solucao s, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        int NUMPERT = 2;//número de perturções

        List<Integer> original = new ArrayList<>(s.getRotaConcatenada());
        //for (int i = 0; i < NUMPERT; i++) {
        posicao1 = p1.nextInt(original.size());

        do {
            posicao2 = p2.nextInt(original.size());
        } while (Objects.equals(original.get(posicao1), original.get(posicao2)));

        //Collections.swap(original, posicao1, posicao2);
        //Collections.shuffle(original);
        original.add(posicao1, original.remove(posicao2));
        //}
        Solucao S = new Solucao();
        S.setSolucao(avaliaSolucao(original, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        s.setSolucao(S);

        return s;
    }
    
    
    public static Solucao PerturbacaoSemente(Solucao s, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows) {
        Random rnd = new Random(19700621);
        Random p1 = new Random(19622);
        Random p2 = new Random(19700623);
        int posicao1, posicao2;
        int NUMPERT = 2;//número de perturções

        List<Integer> original = new ArrayList<>(s.getRotaConcatenada());
        //for (int i = 0; i < NUMPERT; i++) {
        posicao1 = p1.nextInt(original.size());

        do {
            posicao2 = p2.nextInt(original.size());
        } while (Objects.equals(original.get(posicao1), original.get(posicao2)));

        //Collections.swap(original, posicao1, posicao2);
        //Collections.shuffle(original);
        original.add(posicao1, original.remove(posicao2));
        //}
        Solucao S = new Solucao();
        S.setSolucao(avaliaSolucao(original, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        s.setSolucao(S);

        return s;
    }

    public static Solucao ILS(Solucao s_0, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows) {
        //Solução inicial já é gerada pelo GA
        Solucao s = new Solucao(s_0);
        Solucao s_linha = new Solucao();
        Solucao s_2linha = new Solucao();
        List<Solucao> historico = new ArrayList<>();
        int MAXITER = 2;

        //BuscaLocal
        s.setSolucao(RVND(s_0, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        System.out.println("Apos a primeria busca local s= " + s);
        //s.setSolucao(primeiroMelhorVizinho(s_0,2,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
        int cont = 0;
        while (cont < MAXITER) {
            //System.out.println("Entrou no laço do ILS\tFO = " + s.getfObjetivo());

            System.out.println("Interação ILS = " + cont);
            //Perturbação
            s_linha.setSolucao(Perturbacao(s, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
            System.out.println("Apos perturbação s'= " + s_linha);
            //BuscaLocal
            s_2linha.setSolucao(RVND(s_linha, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            //s_2linha.setSolucao(primeiroMelhorVizinho(s_0,2,listRequests,P,K,U,Pin,Pout, d, c, n, Qmax,TimeWindows));
            //System.out.println("Apos busca local s'' = " + s_2linha);
            //CriterioAceitacao
            if (s_2linha.getFuncaoObjetivo() < s_0.getFuncaoObjetivo()) {
                //System.out.println("s_0 = " + s_0.getfObjetivo());
                //System.out.println("s_2linha = "+s_2linha.getfObjetivo());
                s.setSolucao(s_2linha);
                s_0.setSolucao(s_2linha);
                //historico.add(s_2linha);

                System.out.println("Atualizou\tFO = " + s.getFuncaoObjetivo());
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

    public static Solucao geraPesos(Integer semente, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        //for (int i = 0; i < 1; i++) {
        Solucao S = new Solucao();
        Random rnd = new Random(semente);
        //Random rnd = new Random();
        double x, y, z, w;
        do {
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            z = rnd.nextDouble();
            w = 1 - x - y - z;
        } while (x + y + z > 1);

        //System.out.println(x + "\t" + y + "\t" + z + "\t" + w);
        S.setSolucao(greedyConstructive(x, y, z, w, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
        //System.out.println("SolucaoGulosaAleatoria = "+ S);
        //S.setSolucao(GeraSolucaoAleatoria(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
        //Pop.add(S);
        return S;
        //}
    }

    public static void LeituraPesosArquivo(List<Solucao> Pop, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows) {
        List<Integer> lista = new ArrayList<>();

        Solucao S = new Solucao();
        //S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
        //Pop.get(i).setSolucao(S);

        try {
            Scanner scanner = new Scanner(new FileReader("Pesos.txt")).useDelimiter("\\t");
            String linha = new String();

            while (scanner.hasNext()) {
                //double x = Double.parseDouble(scanner.next()) ;
                linha = scanner.nextLine();
                String[] valores = linha.split("\t");
                Double x = Double.parseDouble(valores[0]);
                Double y = Double.parseDouble(valores[1]);
                Double z = Double.parseDouble(valores[2]);

                //System.out.println("X = " + linha);
                //System.out.println("Valores = " + valores[2]);
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
        System.out.println();
    }

    public static void GeraPopGulosa(List<Solucao> Pop, Integer TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d,
            List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Solucao s = new Solucao();
        //Pop.clear();
        InicializaPopulacao(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);

        for (int i = 0; i < TamPop; i++) {
            s.setSolucao(geraPesos(i, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            //Pop.add(s);
            //System.out.println(s);
            Pop.get(i).setSolucao(s);
        }
        //System.out.println("Tamanho da Pop = " + Pop.size());
        //Fitness(Pop);
        //OrdenaPopulacao(Pop);

        //ImprimePopulacao(Pop);
    }

    // Algoritmo GRASP_reativo 
    public static Solucao GRASP_reativo(Integer MAX_ITERATIONS, Double alphaD, Double alphaP, Double alphaV, Double alphaT, int tipoBusca,
            int tipoEstrategia, int tipoMovimento, List<Request> listRequests, PrintStream saida, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m,
            List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows) {

        Solucao SStar = new Solucao();
        Solucao original = new Solucao();
        Solucao originalFinal = new Solucao();

        SStar.setfObjetivo1(9999999);
        SStar.setfObjetivo2(9999999);
        double tempoInicio, tempoFim, tempo = 0;
        /*String logStar = "";
		
		
         int SStarCost = 99999,
         solutionCost,
         SStarUsize = 0;*/

        Solucao S = new Solucao();
        String log;

        Integer num_iterations = 0;

        /*Map<Double, Double> pAlpha = new HashMap<Double, Double>();

         pAlpha.put(0.0, 1.0);
         pAlpha.put(0.1, 1.0);
         pAlpha.put(0.2, 1.0);
         pAlpha.put(0.3, 1.0);
         pAlpha.put(0.4, 1.0);
         pAlpha.put(0.5, 1.0);
         pAlpha.put(0.6, 1.0);
         pAlpha.put(0.7, 1.0);
         pAlpha.put(0.8, 1.0);
         pAlpha.put(0.9, 1.0);
         pAlpha.put(1.0, 1.0);*/
        Double alpha;
        Iterator<Integer> itK;
        int currentK;
        Map<Integer, Double> CRL = new HashMap<Integer, Double>(n), // Cost Rank List
                NRL = new HashMap<Integer, Double>(n), // Number of Passengers Rank List
                DRL = new HashMap<Integer, Double>(n), // Delivery time-window Rank List
                TRL = new HashMap<Integer, Double>(n), // Time-window Rank List
                NRF = new HashMap<Integer, Double>(n);	// Time-window Rank List
        Random r = new Random(System.nanoTime());

        //Double A[] = {0.30,0.35,0.40,0.45,0.50,0.55,0.60,0.65,0.70};
        Map<Double, List<Double>> A = new TreeMap<Double, List<Double>>();
        double doubleAlfa;

        for (int alfa = 30; alfa <= 70; alfa += 5) {

            doubleAlfa = Double.parseDouble(Float.toString(new Float(alfa * 0.01)));
            A.put(doubleAlfa, new ArrayList<Double>(5));

        }

        /*List<Double> A = new ArrayList<Double>();
         A.add(0.30);
         A.add(0.35);
         A.add(0.40);
         A.add(0.45);
         A.add(0.50);
         A.add(0.55);
         A.add(0.60);
         A.add(0.65);
         A.add(0.70);
		
         int count[] = new int[A.size()];
         int score[] = new int[A.size()];
         double prob[] = new double[A.size()];
         double avg[] = new double[A.size()];
         double Qk[] = new double[A.size()];*/
        double teta = 10, sigma;
        double probAcumulada, auxProbAcumulada;

        /*for(int k = 0; k < A.size(); k++){
         count[k] = score[k] = 0;
         prob[k] = 1.0/((double)A.size());
         }*/
        /**
         * aux[0] = count[] aux[1] = score[] aux[2] = prob[] aux[3] = avg[]
         * aux[4] = Qk[] *
         */
        List<Double> auxA = new ArrayList<Double>(5);
        auxA.add(0.0);
        auxA.add(0.0);
        auxA.add(1.0 / A.size());
        auxA.add(0.0);
        auxA.add(0.0);

        for (Map.Entry<Double, List<Double>> e : A.entrySet()) {
            e.setValue(new ArrayList<Double>(auxA));
        }
        auxA.clear();

        while (num_iterations < MAX_ITERATIONS) {
            //if (num_iterations % 10 == 0) {
            System.out.println("Iteração = " + num_iterations);
            //}
            //solutionCost = 0;
            log = "";

            P.clear();
            P.addAll(listRequests);

            probAcumulada = r.nextDouble();
            auxProbAcumulada = 0;
            //alpha = (double)(r.nextInt(11)/10);
            alpha = null;
            for (Map.Entry<Double, List<Double>> e : A.entrySet()) {
                if (auxProbAcumulada <= probAcumulada && probAcumulada <= e.getValue().get(2) + auxProbAcumulada) {
                    alpha = e.getKey();
                    break;
                }
                auxProbAcumulada += e.getValue().get(2);
            }

            S.resetSolucao(99999999, 9999999, 9999999, 9999999, 9999999, 9999999, 9999999, 9999999, 9999999, 9999999);

            itK = K.iterator();
            //construction phase
            while (!P.isEmpty() && itK.hasNext()) {
                U.clear();
                Pin.clear();
                Pout.clear();
                List<Request> origem = new LinkedList<Request>();
                List<Request> destino = new LinkedList<Request>();
                for (int j = 0; j < n; j++) {

                    for (Request request : P) {
                        if (request.getOrigin() == j) {
                            origem.add((Request) request.clone());
                        }
                        if (request.getDestination() == j) {
                            destino.add((Request) request.clone());
                        }
                    }

                    Pin.put(j, new LinkedList<Request>(origem));
                    Pout.put(j, new LinkedList<Request>(destino));

                    origem.clear();
                    destino.clear();
                }

                //Step 2
                Rota R = new Rota();
                currentK = itK.next();
                log += "\tGROTA " + (currentK + 1) + " ";

                //Step 3
                R.addVisitacao(0);
                int currentTime = 0;

                Double min, max;

                Integer lastNode = R.getLastNode();

                boolean encontrado;

                while (!P.isEmpty()) {

                    //Build Candidate List (CL) from current Node using NRF
                    List<Integer> CL = new LinkedList<Integer>();

                    m.clear();
                    for (int i = 0; i < n; i++) {
                        m.add(Pin.get(i).size() - Pout.get(i).size());
                    }

                    //Step 4
                    Set<Integer> FeasibleNode = new HashSet<Integer>();
                    List<Integer> EarliestTime = new ArrayList<Integer>();

                    //lastNode = R.getLastNode();					
                    for (int i = 1; i < n; i++) {
                        if (i != lastNode) {
                            encontrado = false;

                            if (R.getLotacaoAtual() < Qmax) {
                                for (Request request : Pin.get(i)) {
                                    if (lastNode == 0 && d.get(lastNode).get(i) <= request.getPickupL()) {
                                        FeasibleNode.add(i);
                                        encontrado = true;
                                        break;
                                    }
                                    //if( (currentTime + d.get(lastNode).get(i)) <= request.getPickupL()){
                                    if (!encontrado && currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                                            && currentTime + d.get(lastNode).get(i) <= request.getPickupL()) {
                                        FeasibleNode.add(i);
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }

                            /**
                             * E OS N�S DE ENTREGA? DEVEM SER VI�VEIS TAMB�M?*
                             */
                            if (!encontrado && R.getLotacaoAtual() > 0) {
                                for (Request request : Pout.get(i)) {
                                    if (!Pin.get(request.getOrigin()).contains(request)) {
                                        FeasibleNode.add(i);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    //System.out.println("\nFEASIBLE NODES = "+FeasibleNode+"Qik"+Qik+" ROTA "+R+" SOLUCAO S "+S);
                    //System.out.println("FEASIBLE NODES = "+FeasibleNode+"Qik"+Qik+" ROTA "+R+" SOLUCAO S "+S);
                    if (FeasibleNode.size() > 1) {
                        //Step 4.1
                        for (Integer i : FeasibleNode) {
                            CRL.put(i, (double) c.get(lastNode).get(i));
                        }

                        //Normalizacao
                        /*if(CRL.size() > 0){*/
                        min = Collections.min(CRL.values());
                        max = Collections.max(CRL.values());

                        if (min != max) {
                            for (Integer i : FeasibleNode) {
                                CRL.put(i, ((max - CRL.get(i)) / (max - min)));
                            }
                        } else {
                            for (Integer i : FeasibleNode) {
                                CRL.put(i, 1.0);
                            }
                        }
                        /*}

                         for(Integer i : FeasibleNode)
                         if(!CRL.containsKey(i))
                         CRL.put(i, 0.0);*/

                        //Step 4.2
                        for (Integer i : FeasibleNode) {
                            NRL.put(i, (double) m.get(i));
                        }

                        //Normalizacao
                        /*if(NRL.size() > 0){*/
                        min = Collections.min(NRL.values());
                        max = Collections.max(NRL.values());

                        if (min != max) {
                            for (Integer i : FeasibleNode) {
                                NRL.put(i, ((NRL.get(i) - min) / (max - min)));
                            }
                        } else {
                            for (Integer i : FeasibleNode) {
                                NRL.put(i, 1.0);
                            }
                        }
                        /*}

                         for(Integer i : FeasibleNode)
                         if(!NRL.containsKey(i))
                         NRL.put(i, 0.0);*/

                        //Step 4.3
                        for (Integer i : FeasibleNode) {
                            if (Pout.get(i).size() > 0) {
                                for (Request request : Pout.get(i)) {
                                    EarliestTime.add(request.getDeliveryE());
                                }

                                DRL.put(i, (double) (Collections.min(EarliestTime) + d.get(lastNode).get(i)));

                                EarliestTime.clear();
                            }
                        }

                        //Normalizacao
                        if (DRL.size() > 0) {
                            min = Collections.min(DRL.values());
                            max = Collections.max(DRL.values());

                            if (min != max) {
                                for (Integer i : DRL.keySet()) {
                                    DRL.put(i, ((max - DRL.get(i)) / (max - min)));
                                }
                            } else {
                                for (Integer i : DRL.keySet()) {
                                    DRL.put(i, 1.0);
                                }
                            }
                        }

                        for (Integer i : FeasibleNode) {
                            if (!DRL.containsKey(i)) {
                                DRL.put(i, 0.0);
                            }
                        }

                        //Step 4.4
                        for (Integer i : FeasibleNode) {
                            if (Pin.get(i).size() > 0) {
                                for (Request request : Pin.get(i)) {
                                    EarliestTime.add(request.getPickupE());
                                }

                                TRL.put(i, (double) (Collections.min(EarliestTime) + d.get(lastNode).get(i)));

                                EarliestTime.clear();
                            }
                        }

                        //Normalizacao
                        if (TRL.size() > 0) {
                            min = Collections.min(TRL.values());
                            max = Collections.max(TRL.values());

                            if (min != max) {
                                for (Integer i : TRL.keySet()) {
                                    TRL.put(i, ((max - TRL.get(i)) / (max - min)));
                                }
                            } else {
                                for (Integer i : TRL.keySet()) {
                                    TRL.put(i, 1.0);
                                }
                            }
                        }

                        for (Integer i : FeasibleNode) {
                            if (!TRL.containsKey(i)) {
                                TRL.put(i, 0.0);
                            }
                        }
                    } else {
                        //Step 4.1
                        for (Integer i : FeasibleNode) {
                            CRL.put(i, 1.0);
                        }

                        //Step 4.2
                        for (Integer i : FeasibleNode) {
                            NRL.put(i, 1.0);
                        }

                        //Step 4.3
                        for (Integer i : FeasibleNode) {
                            DRL.put(i, 1.0);
                        }

                        //Step 4.4
                        for (Integer i : FeasibleNode) {
                            TRL.put(i, 1.0);
                        }
                    }

                    //System.out.println("Keys\nCRL= "+CRL.keySet()+"\nNRL= "+NRL.keySet()+"\nDRL = "+DRL.keySet()+"\nTRL ="+TRL.keySet());
                    //System.out.println("V= "+V);
                    //Step 5
                    for (Integer i : FeasibleNode) {
                        NRF.put(i, (alphaD * CRL.get(i) + alphaP * NRL.get(i))
                                + (alphaV * DRL.get(i) + alphaT * TRL.get(i)));
                    }
                    //System.out.println("NRF = "+NRF);

                    //Ordenar CL a partir de NRF					
                    for (Integer NRFkey : NRF.keySet()) {
                        if (!CL.isEmpty()) {

                            for (Integer CLkey : CL) {
                                if (NRF.get(NRFkey) > NRF.get(CLkey)) {
                                    CL.add(CL.indexOf(CLkey), NRFkey);
                                    break;
                                }
                            }

                            if (!CL.contains(NRFkey)) {
                                CL.add(NRFkey);
                            }
                        } else {
                            CL.add(NRFkey);
                        }

                    }
                    /*Float teste = (float) 1.0;*/
                    //System.out.println("CL"+CL);
                    //System.out.println("alpha*size = "+Math.max((int)Math.ceil(alpha.floatValue()*CL.size()), 1));

                    //Build Restricted Candidate List (RCL) using current alpha
                    List<Integer> RCL = new LinkedList<Integer>(CL.subList(0, Math.max((int) Math.ceil(alpha.floatValue() * CL.size()), 1)));

                    //System.out.println("RCL"+RCL);
                    int position = r.nextInt(RCL.size());

                    //System.out.println("RCL position["+position+"] = "+RCL.get(position));
                    //Step 6
                    /**
                     * NECESS�RIO TESTAR SE O N� ESCOLHIDO � VI�VEL? AFINAL DE
                     * CONTAS, O CONJ TRABALHADO J� � O DE N�S VI�VEIS*
                     */
                    Integer newNode = RCL.get(position);
                    if (lastNode == 0) {
                        for (Request request : Pin.get(newNode)) {
                            if (d.get(lastNode).get(newNode) <= request.getPickupL()) {
                                EarliestTime.add(request.getPickupE());
                            }
                        }

                        currentTime = Math.max(Collections.min(EarliestTime) - d.get(lastNode).get(newNode), 0);
                        R.setTempoikDeposito(currentTime);

                        EarliestTime.clear();
                    }

                    currentTime += d.get(lastNode).get(newNode);

                    R.addVisitacao(newNode);
                    lastNode = R.getLastNode();

                    CRL.clear();
                    NRL.clear();
                    DRL.clear();
                    TRL.clear();
                    NRF.clear();

                    //Step 7
                    /**
                     * SOLICITA��ES J� ATENDIDAS *
                     */
                    List<Request> listRequestAux = new LinkedList<Request>(Pout.get(lastNode));

                    for (Request request : listRequestAux) {

                        if (!Pin.get(request.getOrigin()).contains(request)) {
                            Pout.get(lastNode).remove((Request) request.clone());
                            P.remove((Request) request.clone());

                            //if(currentK == 0){
                            log += "ENTREGA: " + currentTime + ": " + (Request) request.clone() + " ";
                            //}

                            R.addDesembarque((Request) request.clone(), currentTime);

                            //EXTRA
                            log += "Q=" + R.getLotacaoAtual() + " ";
                        }
                    }
                    listRequestAux.clear();

                    listRequestAux.addAll(Pin.get(lastNode));

                    for (Request request : listRequestAux) {
                        if (R.getLotacaoAtual() < Qmax && currentTime >= request.getPickupE() && currentTime <= request.getPickupL()) {
                            Pin.get(lastNode).remove((Request) request.clone());

                            //if(currentK == 1){
                            /**
                             * ** Anexado a classe Rota
                             * S.addAtendimento((Request)request.clone()); **
                             */
                            log += "COLETA: " + currentTime + ": " + (Request) request.clone() + " ";
                            //}

                            R.addEmbarque((Request) request.clone(), currentTime);

                            //EXTRA
                            log += "Q=" + R.getLotacaoAtual() + " ";
                        }
                    }

                    listRequestAux.clear();

                    listRequestAux.addAll(Pin.get(lastNode));

                    int waitTime = TimeWindows;
                    int aux;

                    for (Request request : listRequestAux) {
                        if (R.getLotacaoAtual() < Qmax && currentTime + waitTime >= request.getPickupE() && currentTime + waitTime <= request.getPickupL()) {

                            aux = currentTime + waitTime - request.getPickupE();
                            currentTime = Math.min(currentTime + waitTime, request.getPickupE());
                            waitTime = aux;

                            Pin.get(lastNode).remove((Request) request.clone());

                            //if(currentK == 1){
                            /**
                             * ** Anexado a classe Rota
                             * S.addAtendimento((Request)request.clone()); **
                             */
                            log += "COLETAw: " + currentTime + ": " + (Request) request.clone() + " ";
                            //}

                            R.addEmbarque((Request) request.clone(), currentTime);

                            //EXTRA
                            log += "Q=" + R.getLotacaoAtual() + " ";
                        }
                    }

                    /**
                     * SOLICITA��ES INVI�VEIS *
                     */
                    listRequestAux.clear();

                    for (Integer key : Pin.keySet()) {

                        listRequestAux.addAll(Pin.get(key));

                        for (Integer i = 0, n1 = listRequestAux.size(); i < n1; i++) {
                            Request request = listRequestAux.get(i);

                            if (currentTime > request.getPickupL()) {
                                U.add((Request) request.clone());
                                P.remove((Request) request.clone());
                                Pin.get(key).remove((Request) request.clone());
                                Pout.get(request.getDestination()).remove((Request) request.clone());
                            }
                        }
                        listRequestAux.clear();
                    }

                    encontrado = false;
                    for (int i = 1; !encontrado && i < n; i++) {
                        if (i != lastNode) {

                            if (R.getLotacaoAtual() < Qmax) {
                                for (Request request : Pin.get(i)) {
                                    if (currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                                            && currentTime + d.get(lastNode).get(i) <= request.getPickupL()) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }

                            /**
                             * E OS N�S DE ENTREGA? DEVEM SER VI�VEIS TAMB�M?*
                             */
                            if (!encontrado && R.getLotacaoAtual() > 0) {
                                for (Request request : Pout.get(i)) {
                                    if (!Pin.get(request.getOrigin()).contains(request)) {
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (!encontrado) {
                        for (Integer key : Pin.keySet()) {

                            listRequestAux.addAll(Pin.get(key));

                            for (Integer i = 0, n1 = listRequestAux.size(); i < n1; i++) {
                                Request request = listRequestAux.get(i);

                                U.add((Request) request.clone());
                                P.remove((Request) request.clone());
                                Pin.get(key).remove((Request) request.clone());
                                Pout.get(request.getDestination()).remove((Request) request.clone());

                            }
                            listRequestAux.clear();

                        }
                    }

                }

                //Step 8
                if (P.isEmpty()) {
                    R.addVisitacao(0);
                    //log += R.toString()+"\n";
                    //System.out.println("Rota "+R+" - "+currentTime);
                    //solutionCost += currentTime;
                    currentTime += d.get(lastNode).get(0);
                    S.getConjRotas().add(R);
                    //System.out.println("Solucao S = "+S);
                }

                //Step 9
                if (!U.isEmpty() && itK.hasNext()) {
                    encontrado = false;
                    List<Request> auxU = new ArrayList<Request>(U);
                    for (Request request : auxU) {
                        if (d.get(0).get(request.getOrigin()) <= request.getPickupL()) {
                            encontrado = true;
                        }
                    }

                    //Step 9
                    if (encontrado) {
                        P.addAll(U);
                    }
                }
                /*
                 //Step 9
                 if(!U.isEmpty())
                 P.addAll(U);*/
            }
            //solutionCost += FO(S,U.size()); 
            //solutionCost = FO(S,U.size());
            S.setListaNaoAtendimento(U);
            S.setfObjetivo1(FO1(S, c));
            S.setfObjetivo2(FO2(S, c));
            S.setfObjetivo3(FO3(S));
            S.setfObjetivo4(FO4(S));
            S.setfObjetivo5(FO5(S));
            S.setfObjetivo6(FO6(S));
            S.setfObjetivo7(FO7(S));
            S.setfObjetivo8(FO8(S));
            S.setfObjetivo9(FO9(S));
            S.setFuncaoObjetivo(FuncaoDeAvaliacao(S, listRequests, c));

            FOagregados(S, 1, 1, 1, 1, 1);
            S.setLogger(log);
            S.concatenarRota();
            //solutionCost = FO(S);// ???IMPORTANTE?????

            tempoInicio = (System.nanoTime() * 0.000001);
            /**
             * Tipo Vizinho: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
             * Operacao: 1 - Troca, 2 - Insercao, 3 - Movimento, 4 - Aleatoria
             *
             */
            Solucao busca;
            switch (tipoBusca) {
                case 1:
                    busca = new Solucao(buscaLocal(S, tipoEstrategia, tipoMovimento, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    break;
                case 2:
                    //busca = new Solucao( buscaTabu(S, tipoEstrategia, tipoMovimento, listRequests) );
                    busca = new Solucao(buscaTabu(S, tipoEstrategia, tipoMovimento, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    break;
                default:
                    busca = new Solucao();
            }

            tempoFim = (System.nanoTime() * 0.000001);
            tempo += (tempoFim - tempoInicio);

            original.setSolucao(S);
            S.setSolucao(busca);

            if (S.getFuncaoObjetivo() < SStar.getFuncaoObjetivo()) {
                //System.out.println("ACHEI: "+solutionCost+" -> "+S);
                SStar.setSolucao(S);
                originalFinal.setSolucao(original);
                /*logStar = log;

                 SStarCost = solutionCost;
                 SStarUsize = U.size();*/

                //pAlpha.put(alpha, pAlpha.get(alpha)+1.0);
            }

            /**
             * aux[0] = count[] aux[1] = score[] aux[2] = prob[] aux[3] = avg[]
             * aux[4] = Qk[] *
             */
            auxA.clear();
            auxA = A.get(alpha);
            auxA.set(0, auxA.get(0) + 1.0);
            auxA.set(1, auxA.get(1) + S.getfObjetivo1());
            A.put(alpha, new ArrayList<Double>(auxA));

            if (num_iterations > 0 && num_iterations % 20 == 0) {
                sigma = 0;

                for (Map.Entry<Double, List<Double>> e : A.entrySet()) {
                    auxA.clear();
                    auxA = e.getValue();
                    if (auxA.get(0) == 0.0 || auxA.get(1) == 0.0) {
                        auxA.set(4, 0.0);
                    } else {
                        auxA.set(3, auxA.get(1) / auxA.get(0));
                        auxA.set(4, Math.pow(SStar.getfObjetivo1() / auxA.get(3), teta));

                        sigma += auxA.get(4);
                    }
                    e.setValue(new ArrayList<Double>(auxA));
                }

                for (Map.Entry<Double, List<Double>> e : A.entrySet()) {
                    auxA.clear();
                    auxA = e.getValue();
                    auxA.set(2, auxA.get(4) / sigma);
                    e.setValue(new ArrayList<Double>(auxA));
                    //System.out.println("");
                }
            }

            num_iterations++;
        }
        //System.out.print("Usize = "+SStarUsize+"\n"+SStarCost+" -> ");
        //System.out.println(SStarCost+"\t"+SStarUsize);
        //System.out.println(SStar.getfObjetivo()+"\t"+SStar.getListaNaoAtendimento().size());
        /**
         * IMPRIME PRIMEIRO O TEMPO DE BUSCA*
         */

        //saida.print((int)tempo+"\t"+originalFinal);
        //saida2.println();
        /*if(ativaLog){
         //System.out.println(SStar.getLogger());
         //	System.out.println("NaoAtendimento "+SStar.getListaNaoAtendimento().size());
         //System.out.println("Atendimento "+SStar.getListaAtendimento().size());
         }*/
        return SStar;
    }

    /**
     * Busca Local
     *
     */
    public static Solucao buscaLocal(Solucao inicial, int tipoEstrategia, int tipoMovimento, List<Request> listRequests, List<Request> P,
            Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao melhor = new Solucao();
        Solucao s = new Solucao(inicial);

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        if (tipoEstrategia == 1) { // utiliza o m�todo melhorVizinho

            do {

                melhor.setSolucao(s);
                //s.setSolucao(melhorVizinho(melhor,tipoMovimento, listRequests));
                s.setSolucao(melhorVizinho(melhor, tipoMovimento, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

            } while (!s.equals(melhor));

        } else {

            do {

                melhor.setSolucao(s);
                //s.setSolucao(primeiroMelhorVizinho(melhor,tipoMovimento, listRequests));
                s.setSolucao(primeiroMelhorVizinho(melhor, tipoMovimento, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

            } while (!s.equals(melhor));

        }

        return melhor;
    }

}
