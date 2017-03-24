package ProblemRepresentation;

import GoogleMapsApi.StaticGoogleMap;
import InstanceReaderWithMySQL.NodeDAO;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Solution implements Comparable<Solution> {

    private Set<Route> setOfRoutes;
    private double objectiveFunction;
    private int solutionTotalCost;//f1
    private int solutionTotalDelay;//f2
    private int chargeBalance;//f3
    private int numberOfNonAttendedRequests;//f4
    private int numberOfVehicles;//f5
    private int totalTravelTime;//f6
    private int totalWaintingTime;//f7
    private int deliveryTimeWindowAntecipation;//f8
    private int deliveryTimeWindowDelay;//f9

    private double aggregatedObjective1;
    private double aggregatedObjective2;
    private double aggregatedObjective1Normalized;
    private double aggregatedObjective2Normalized;
    private int numberOfDominatedSolutionsByThisSolution;
    private int numberOfSolutionsWichDomineThisSolution;
    private List<Integer> listOfSolutionsDominatedByThisSolution;
    private double fitness;
    private int dif;
    private int S;
    private int R;
    private List<Request> nonAttendedRequestsList;
    private List<Integer> linkedRouteList;
    private String logger;
    private int tempoExtraTotal;

    public Solution() {
        setOfRoutes = new HashSet<Route>();
        listOfSolutionsDominatedByThisSolution = new ArrayList<>();
        objectiveFunction = -1;
        solutionTotalCost = -1;
        solutionTotalDelay = -1;
        chargeBalance = -1;
        numberOfNonAttendedRequests = -1;
        numberOfVehicles = -1;
        totalTravelTime = -1;
        totalWaintingTime = -1;
        deliveryTimeWindowAntecipation = -1;
        deliveryTimeWindowDelay = -1;
        aggregatedObjective1 = -1;
        aggregatedObjective2 = -1;
        aggregatedObjective1Normalized = -1;
        aggregatedObjective2Normalized = -1;
        R = 0;
        S = 0;
        fitness = -0.9;
        dif = -1;
        numberOfDominatedSolutionsByThisSolution = 0;
        numberOfSolutionsWichDomineThisSolution = 0;
        nonAttendedRequestsList = new ArrayList<Request>();
        linkedRouteList = new ArrayList<Integer>();
        logger = "";
    }

    public Solution(Solution solution) {
        setOfRoutes = new HashSet<Route>(solution.getSetOfRoutes());
        listOfSolutionsDominatedByThisSolution = new ArrayList<>(solution.getListOfSolutionsDominatedByThisSolution());
        objectiveFunction = solution.getObjectiveFunction();
        solutionTotalCost = solution.getSolutionTotalCost();
        solutionTotalDelay = solution.getSolutionTotalDelay();
        chargeBalance = solution.getChargeBalance();
        numberOfNonAttendedRequests = solution.getNumberOfNonAttendedRequests();
        numberOfVehicles = solution.getNumberOfVehicles();
        totalTravelTime = solution.getTotalTravelTime();
        totalWaintingTime = solution.getTotalWaintingTime();
        deliveryTimeWindowAntecipation = solution.getDeliveryTimeWindowAntecipation();
        deliveryTimeWindowDelay = solution.getDeliveryTimeWindowDelay();
        aggregatedObjective1 = solution.getAggregatedObjective1();
        aggregatedObjective2 = solution.getAggregatedObjective2();
        aggregatedObjective1Normalized = solution.getAggregatedObjective1Normalized();
        aggregatedObjective2Normalized = solution.getAggregatedObjective2Normalized();
        fitness = solution.getFitness();
        tempoExtraTotal = solution.getTempoExtraTotal();
        nonAttendedRequestsList = new ArrayList<Request>(solution.getNonAttendedRequestsList());
        linkedRouteList = new ArrayList<Integer>(solution.getLinkedRouteList());
        logger = new String(solution.getLogger());
    }

    public void setSolucao(Solution solution) {
        setSetOfRoutes(solution.getSetOfRoutes());
        setListOfSolutionsDominatedByThisSolution(solution.getListOfSolutionsDominatedByThisSolution());
        setObjectiveFunction(solution.getObjectiveFunction());
        setSolutionTotalCost(solution.getSolutionTotalCost());
        setSolutionTotalDelay(solution.getSolutionTotalDelay());
        setChargeBalance(solution.getChargeBalance());
        setNumberOfNonAttendedRequests(solution.getNumberOfNonAttendedRequests());
        setNumberOfVehicles(solution.getNumberOfVehicles());
        setTotalTravelTime(solution.getTotalTravelTime());
        setTotalWaintingTime(solution.getTotalWaintingTime());
        setDeliveryTimeWindowAntecipation(solution.getDeliveryTimeWindowAntecipation());
        setDeliveryTimeWindowDelay(solution.getDeliveryTimeWindowDelay());
        setAggregatedObjective1(solution.getAggregatedObjective1());
        setAggregatedObjective2(solution.getAggregatedObjective2());
        setAggregatedObjective1Normalized(solution.getAggregatedObjective1Normalized());
        setAggregatedObjective2Normalized(solution.getAggregatedObjective2Normalized());
        setFitness(solution.getFitness());
        setR(solution.getR());
        setS(solution.getS());
        setNonAttendedRequestsList(solution.getNonAttendedRequestsList());
        setLinkedRouteList(solution.getLinkedRouteList());
        setLogger(solution.getLogger());
        setTempoExtraTotal(solution.getTempoExtraTotal());
    }

    public void resetSolucao(double FO, int FO1, int FO2, int FO3, int FO4, int FO5, int FO6, int FO7, int FO8, int FO9) {
        setOfRoutes.clear();
        listOfSolutionsDominatedByThisSolution.clear();
        objectiveFunction = FO;
        solutionTotalCost = FO1;
        solutionTotalDelay = FO2;
        chargeBalance = FO3;
        numberOfNonAttendedRequests = FO4;
        numberOfVehicles = FO5;
        totalTravelTime = FO6;
        totalWaintingTime = FO7;
        deliveryTimeWindowAntecipation = FO8;
        deliveryTimeWindowDelay = FO9;
        aggregatedObjective1 = 99999999;
        aggregatedObjective2 = 99999999;
        aggregatedObjective1Normalized = 99999999;
        aggregatedObjective2Normalized = 99999999;
        nonAttendedRequestsList.clear();
        linkedRouteList.clear();
        logger = "";
    }

    public Set<Route> getSetOfRoutes() {
        return setOfRoutes;
    }

    public Set<List<Integer>> getRoutesForMap() {
        Set<List<Integer>> routes = new HashSet<>();
        for (Route route : this.getSetOfRoutes()) {
            routes.add(route.getListaVisitacao());
        }
        return routes;
    }

    public void getStaticMapWithAllRoutes(String nodesData) throws IOException {
        new StaticGoogleMap(new NodeDAO(nodesData).getListOfNodes(), this.getRoutesForMap()).buildMapInWindow();
    }

    public void getStaticMapForEveryRoute(String nodesData) throws IOException {

        Set<List<Integer>> routes = new HashSet<>();
        for (List<Integer> route : this.getRoutesForMap()) {
            routes.add(route);
            new StaticGoogleMap(new NodeDAO(nodesData).getListOfNodes(), routes).buildMapInWindow();
            routes.clear();
        }

    }

    public List<Integer> getListOfSolutionsDominatedByThisSolution() {
        return this.listOfSolutionsDominatedByThisSolution;
    }

    public int getR() {
        return R;
    }

    public int getS() {
        return S;
    }

    public void setR(int R) {
        this.R = R;
    }

    public void setS(int S) {
        this.S = S;
    }

    public void setSetOfRoutes(Set<Route> conjRotas) {
        this.setOfRoutes.clear();
        this.setOfRoutes.addAll(new HashSet<Route>(conjRotas));
    }

    public void setListOfSolutionsDominatedByThisSolution(List<Integer> listOfSolutionsDominatedByThisSolution) {
        this.listOfSolutionsDominatedByThisSolution.clear();
        this.listOfSolutionsDominatedByThisSolution.addAll(listOfSolutionsDominatedByThisSolution);
    }

    public void addL(int posicao) {
        this.listOfSolutionsDominatedByThisSolution.add(posicao);
    }

    public void addnDom() {
        this.numberOfDominatedSolutionsByThisSolution++;
    }

    public void addeDom() {
        this.numberOfSolutionsWichDomineThisSolution++;
    }

    public void setNumberOfDominatedSolutionsByThisSolution(int numberOfDominatedSolutionsByThisSolution) {
        this.numberOfDominatedSolutionsByThisSolution = numberOfDominatedSolutionsByThisSolution;
    }

    public void setNumberOfSolutionsWichDomineThisSolution(int numberOfSolutionsWichDomineThisSolution) {
        this.numberOfSolutionsWichDomineThisSolution = numberOfSolutionsWichDomineThisSolution;
    }

    public void redeDom() {
        this.numberOfSolutionsWichDomineThisSolution--;
    }

    public double getObjectiveFunction() {
        return this.objectiveFunction;
    }

    public int getSolutionTotalCost() {
        return solutionTotalCost;
    }

    public int getSolutionTotalDelay() {
        return solutionTotalDelay;
    }

    public int getChargeBalance() {
        return chargeBalance;
    }

    public int getNumberOfNonAttendedRequests() {
        return numberOfNonAttendedRequests;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    public int getTotalWaintingTime() {
        return totalWaintingTime;
    }

    public int getDeliveryTimeWindowAntecipation() {
        return deliveryTimeWindowAntecipation;
    }

    public int getDeliveryTimeWindowDelay() {
        return deliveryTimeWindowDelay;
    }

    public double getAggregatedObjective1() {
        return this.aggregatedObjective1;
    }

    public double getAggregatedObjective2() {
        return this.aggregatedObjective2;
    }

    public double getAggregatedObjective1Normalized() {
        return this.aggregatedObjective1Normalized;
    }

    public double getAggregatedObjective2Normalized() {
        return this.aggregatedObjective2Normalized;
    }

    public double getFitness() {
        return this.fitness;
    }

    public int getNumberOfDominatedSolutionsByThisSolution() {
        return this.numberOfDominatedSolutionsByThisSolution;
    }

    public int getNumberOfSolutionsWichDomineThisSolution() {
        return this.numberOfSolutionsWichDomineThisSolution;
    }

    public int getTempoExtraTotal() {
        return this.tempoExtraTotal;
    }

    public void setTempoExtraTotal(int tempo) {
        this.tempoExtraTotal = tempo;
    }

    public void setObjectiveFunction(double objectiveFunction) {
        this.objectiveFunction = objectiveFunction;
    }

    public void setSolutionTotalCost(int solutionTotalCost) {
        this.solutionTotalCost = solutionTotalCost;
    }

    public void setSolutionTotalDelay(int solutionTotalDelay) {
        this.solutionTotalDelay = solutionTotalDelay;
    }

    public void setChargeBalance(int chargeBalance) {
        this.chargeBalance = chargeBalance;
    }

    public void setNumberOfNonAttendedRequests(int numberOfNonAttendedRequests) {
        this.numberOfNonAttendedRequests = numberOfNonAttendedRequests;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public void setTotalTravelTime(int totalTravelTime) {
        this.totalTravelTime = totalTravelTime;
    }

    public void setTotalWaintingTime(int totalWaintingTime) {
        this.totalWaintingTime = totalWaintingTime;
    }

    public void setDeliveryTimeWindowAntecipation(int deliveryTimeWindowAntecipation) {
        this.deliveryTimeWindowAntecipation = deliveryTimeWindowAntecipation;
    }

    public void setDeliveryTimeWindowDelay(int deliveryTimeWindowDelay) {
        this.deliveryTimeWindowDelay = deliveryTimeWindowDelay;
    }

    public void setAggregatedObjective1(double aggregatedObjective1) {
        this.aggregatedObjective1 = aggregatedObjective1;
    }

    public void setAggregatedObjective2(double aggregatedObjective2) {
        this.aggregatedObjective2 = aggregatedObjective2;
    }

    public void setAggregatedObjective1Normalized(double aggregatedObjective1Normalized) {
        this.aggregatedObjective1Normalized = aggregatedObjective1Normalized;
    }

    public void setAggregatedObjective2Normalized(double aggregatedObjective2Normalized) {
        this.aggregatedObjective2Normalized = aggregatedObjective2Normalized;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public List<Request> getNonAttendedRequestsList() {
        return nonAttendedRequestsList;
    }

    public void setNonAttendedRequestsList(List<Request> listaNaoAtendimento) {
        this.nonAttendedRequestsList.clear();
        this.nonAttendedRequestsList.addAll(new LinkedList<Request>(listaNaoAtendimento));
    }

    public List<Integer> getLinkedRouteList() {
        return linkedRouteList;
    }

    public void setLinkedRouteList(List<Integer> rotaConcatenada) {
        this.linkedRouteList.clear();
        this.linkedRouteList.addAll(new ArrayList<Integer>(rotaConcatenada));
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;

    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public void linkTheRoutes() {
        for (Route r : setOfRoutes) {
            linkedRouteList.addAll(r.getListaVisitacao().subList(1, r.getListaVisitacao().size() - 1));
        }
    }

    public void addNonAttendeRequest(Request request) {
        nonAttendedRequestsList.add(request);
    }

    public void removeNonAttendeRequest(Request request) {
        nonAttendedRequestsList.remove(request);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.000");
        //System.out.println(NumberFormat.getCurrencyInstance().format(12345678.908874));
        //if(conjRotas != null){// como impedir a chamada do metodo para conjRotas == null ???
        //String s = "FO1 = " + solutionTotalCost + "\t FO2 = " + solutionTotalDelay + "\t FO3 = " +  chargeBalance +/*"\t Fit = "+ fitness +*/"\t U = "+listaNaoAtendimento.size() + "\t N = " + conjRotas.size() + "\t";
        //String s = solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+listaNaoAtendimento.size() + "\t" + conjRotas.size() + ";\t";
        //String s = solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + ";\t";
        //String s = objectiveFunction + "\t"+ aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + "\t" + totalTravelTime + "\t" + totalWaintingTime + "\t";
        String s = objectiveFunction + "\t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" + chargeBalance + "\t" + numberOfNonAttendedRequests + "\t" + numberOfVehicles + "\t" + totalTravelTime + "\t" + totalWaintingTime + "\t" + deliveryTimeWindowAntecipation + "\t" + deliveryTimeWindowDelay + "\t";
        //String s = objectiveFunction + "\t" + solutionTotalCost + "\t" + numberOfNonAttendedRequests + "\t" + deliveryTimeWindowAntecipation + "\t" + deliveryTimeWindowDelay + "\t";
        //String s = solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + "\t" + totalTravelTime + "\t" + totalWaintingTime + "\t";
        //String s = "aggregatedObjective1 = "+ aggregatedObjective1 + "\tF2 = "+ aggregatedObjective2 + "\tFit = " + fitness +"\tf1 = " + solutionTotalCost + "\tf2 = " + solutionTotalDelay + "\tf3 = " +  chargeBalance +"\tf4 = "+numberOfNonAttendedRequests + "\tf5 = " + numberOfVehicles + ";\t";
        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t"+ aggregatedObjective1Normalized + "\t"+ aggregatedObjective2Normalized + "\t" + numberOfSolutionsWichDomineThisSolution +"\t" + fitness +"\t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + ";\t";

        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t" + numberOfSolutionsWichDomineThisSolution +"\t" + fitness +"\t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + ";\t";
        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t" + numberOfSolutionsWichDomineThisSolution +"\t"+ listOfSolutionsDominatedByThisSolution +";\t";
        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t"+ df.format(aggregatedObjective1Normalized) + "\t"+ df.format(aggregatedObjective2Normalized) + "\t" + numberOfSolutionsWichDomineThisSolution +"\t" + df.format(fitness) +"\t" + listOfSolutionsDominatedByThisSolution +" \t "+ S +" \t "+ R +" \t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + ";\t";
        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2 + "\t"+ listOfSolutionsDominatedByThisSolution + "\t" + numberOfDominatedSolutionsByThisSolution +"\t" + " \t "+ S +" \t "+ R +" \t" + solutionTotalCost + "\t" + solutionTotalDelay + "\t" +  chargeBalance +"\t"+numberOfNonAttendedRequests + "\t" + numberOfVehicles + ";\t";
        //String s = aggregatedObjective1 + "\t"+ aggregatedObjective2;
        //String s = df.format(aggregatedObjective1Normalized) + "\t"+ df.format(aggregatedObjective2Normalized)+";\t";
        int indice = 1;
        String listaAtendimento = " ";
        for (Route r : setOfRoutes) {
            s += "R" + indice + ": " + r + " ";
            listaAtendimento += "R" + indice++ + ": ";
            for (Request req : r.getListaAtendimento()) {
                listaAtendimento += req + " ";
            }
        }

        s += "\t";
        //for(Request req : r.listaAtendimento)
        s += listaAtendimento;// + " ";

        s += "\t";
//		for(Request req : listaNaoAtendimento)
//			s += req + " ";
//
//		s += "\t"+rotaConcatenada+logger;
        return s;
        //}	
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Solution && equals((Solution) obj);
    }

    public boolean equals(Solution solucao2) {
        if (this == solucao2) {
            return true;
        }

        if (solucao2 == null) {
            return false;
        }

        if (setOfRoutes.size() != solucao2.getSetOfRoutes().size()) {
            return false;
        }

        for (Iterator<Route> i = setOfRoutes.iterator(); i.hasNext();) {
            if (!solucao2.getSetOfRoutes().contains(i.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {

        if (setOfRoutes == null) {
            return -1;
        }

        int hash = 0;

        for (Route i : setOfRoutes) {
            hash += i.hashCode();
        }

        return hash;
    }

    @Override
    public int compareTo(Solution solucao) {
        if (this.getFitness() > solucao.getFitness()) {
            return 1;
        }
        if (this.getFitness() < solucao.getFitness()) {
            return -1;
        }
        return 0;
    }

}
