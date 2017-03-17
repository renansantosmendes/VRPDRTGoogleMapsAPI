package ProblemRepresentation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Solution implements Comparable<Solution> {

    private Set<Route> conjRotas;
    private double funcaoObjetivo;
    private int fObjetivo1;//f1: custo
    private int fObjetivo2;//f2: atraso
    private int fObjetivo3;//f3: diferença entre rota maior e menor
    private int fObjetivo4;//f4: número de não atendimentos
    private int fObjetivo5;//f5: número de veículos
    private int fObjetivo6;//f6: tempo de viagem
    private int fObjetivo7;//f7: tempo de espera
    private int fObjetivo8;//f8: violação do limite inferior da janela de tempo de desembarque 
    private int fObjetivo9;//f9: violação do limite superior da janela de tempo de desembarque

    private double F1;//objetivo agregado 1 
    private double F2;//objetivo agregado 2 
    private double F1n;//objetivo agregado 1 normalizado
    private double F2n;//objetivo agregado 2 normalizado
    private int nDom;//número de soluções que são dominadas pela solução corrente
    private int eDom;//número de soluções que dominam esta solução 
    private List<Integer> L;//conjunto de solucoes que são dominadas por i (esta solucao)
    private double fitness;
    private int dif;
    private int S;
    private int R;
    /**
     * ******	Foi anexado a classe Route	******* private List<Request>
     * listaAtendimento;
	**
     */
    private List<Request> listaNaoAtendimento;
    private List<Integer> rotaConcatenada;
    private String logger;
    private int tempoExtraTotal;

    public Solution() {
        conjRotas = new HashSet<Route>();
        L = new ArrayList<>();
        funcaoObjetivo = -1;
        fObjetivo1 = -1;
        fObjetivo2 = -1;
        fObjetivo3 = -1;
        fObjetivo4 = -1;
        fObjetivo5 = -1;
        fObjetivo6 = -1;
        fObjetivo7 = -1;
        fObjetivo8 = -1;
        fObjetivo9 = -1;
        F1 = -1;
        F2 = -1;
        F1n = -1;
        F2n = -1;
        R = 0;
        S = 0;
        fitness = -0.9;
        dif = -1;
        nDom = 0;
        eDom = 0;
        /**
         * *****	Foi anexado a classe Rota	******* listaAtendimento = new
         * LinkedList<Request>();
		**
         */
        listaNaoAtendimento = new ArrayList<Request>();
        rotaConcatenada = new ArrayList<Integer>();
        logger = "";
    }

    public Solution(Solution solucao2) {
        conjRotas = new HashSet<Route>(solucao2.getConjRotas());
        L = new ArrayList<>(solucao2.getL());
        funcaoObjetivo = solucao2.getFuncaoObjetivo();
        fObjetivo1 = solucao2.getfObjetivo1();
        fObjetivo2 = solucao2.getfObjetivo2();
        fObjetivo3 = solucao2.getfObjetivo3();
        fObjetivo4 = solucao2.getfObjetivo4();
        fObjetivo5 = solucao2.getfObjetivo5();
        fObjetivo6 = solucao2.getfObjetivo6();
        fObjetivo7 = solucao2.getfObjetivo7();
        fObjetivo8 = solucao2.getfObjetivo8();
        fObjetivo9 = solucao2.getfObjetivo9();
        F1 = solucao2.getF1();
        F2 = solucao2.getF2();
        F1n = solucao2.getF1n();
        F2n = solucao2.getF2n();
        fitness = solucao2.getFitness();
        tempoExtraTotal = solucao2.getTempoExtraTotal();
        /**
         * *****	Foi anexado a classe Rota	******* listaAtendimento = new
         * LinkedList<Request>(solucao2.getListaAtendimento());
		 **
         */
        listaNaoAtendimento = new ArrayList<Request>(solucao2.getListaNaoAtendimento());
        rotaConcatenada = new ArrayList<Integer>(solucao2.getRotaConcatenada());
        logger = new String(solucao2.getLogger());
    }

    public void setSolucao(Solution solucao) {
        setConjRotas(solucao.getConjRotas());
        setL(solucao.getL());
        setFuncaoObjetivo(solucao.getFuncaoObjetivo());
        setfObjetivo1(solucao.getfObjetivo1());
        setfObjetivo2(solucao.getfObjetivo2());
        setfObjetivo3(solucao.getfObjetivo3());
        setfObjetivo4(solucao.getfObjetivo4());
        setfObjetivo5(solucao.getfObjetivo5());
        setfObjetivo6(solucao.getfObjetivo6());
        setfObjetivo7(solucao.getfObjetivo7());
        setfObjetivo8(solucao.getfObjetivo8());
        setfObjetivo9(solucao.getfObjetivo9());
        setF1(solucao.getF1());
        setF2(solucao.getF2());
        setF1n(solucao.getF1n());
        setF2n(solucao.getF2n());
        setFitness(solucao.getFitness());
        setR(solucao.getR());
        setS(solucao.getS());
        //setListaAtendimento(solucao.getListaAtendimento());
        setListaNaoAtendimento(solucao.getListaNaoAtendimento());
        setRotaConcatenada(solucao.getRotaConcatenada());
        setLogger(solucao.getLogger());
        setTempoExtraTotal(solucao.getTempoExtraTotal());
    }

    public void resetSolucao(double FO, int FO1, int FO2, int FO3, int FO4, int FO5, int FO6, int FO7, int FO8, int FO9) {
        conjRotas.clear();
        L.clear();
        funcaoObjetivo = FO;
        fObjetivo1 = FO1;
        fObjetivo2 = FO2;
        fObjetivo3 = FO3;
        fObjetivo4 = FO4;
        fObjetivo5 = FO5;
        fObjetivo6 = FO6;
        fObjetivo7 = FO7;
        fObjetivo8 = FO8;
        fObjetivo9 = FO9;
        //-----------------------------------------------------------
        //           Prestar atenção aqui, se der problema
        //-----------------------------------------------------------
        F1 = 99999999;
        F2 = 99999999;
        F1n = 99999999;
        F2n = 99999999;

        //listaAtendimento.clear();
        listaNaoAtendimento.clear();
        rotaConcatenada.clear();
        logger = "";
    }

    public Set<Route> getConjRotas() {
        return conjRotas;
    }

    public Set<List<Integer>> getRoutesForMap(){
        Set<List<Integer>> routes = new HashSet<>();
        for(Route route: this.getConjRotas()){
            routes.add(route.getListaVisitacao());
        }
        return routes;
    }
    
    public List<Integer> getL() {
        return this.L;
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

    public void setConjRotas(Set<Route> conjRotas) {
        this.conjRotas.clear();
        this.conjRotas.addAll(new HashSet<Route>(conjRotas));
    }

    public void setL(List<Integer> L) {
        this.L.clear();
        this.L.addAll(L);
    }

    public void addL(int posicao) {//adiciona posicao da solucao na populaçao corrente que é dominada por esta 
        this.L.add(posicao);
    }

    public void addnDom() {
        this.nDom++;
    }

    public void addeDom() {
        this.eDom++;
    }

    public void setnDom(int nDom) {
        this.nDom = nDom;
    }

    public void seteDom(int eDom) {
        this.eDom = eDom;
    }

    public void redeDom() {
        this.eDom--;
    }

    public double getFuncaoObjetivo() {
        return this.funcaoObjetivo;
    }

    public int getfObjetivo1() {
        return fObjetivo1;
    }

    public int getfObjetivo2() {
        return fObjetivo2;
    }

    public int getfObjetivo3() {
        return fObjetivo3;
    }

    public int getfObjetivo4() {
        return fObjetivo4;
    }

    public int getfObjetivo5() {
        return fObjetivo5;
    }

    public int getfObjetivo6() {
        return fObjetivo6;
    }

    public int getfObjetivo7() {
        return fObjetivo7;
    }

    public int getfObjetivo8() {
        return fObjetivo8;
    }

    public int getfObjetivo9() {
        return fObjetivo9;
    }

    public double getF1() {
        return this.F1;
    }

    public double getF2() {
        return this.F2;
    }

    public double getF1n() {
        return this.F1n;
    }

    public double getF2n() {
        return this.F2n;
    }

    public double getFitness() {
        return this.fitness;
    }

    public int getnDom() {
        return this.nDom;
    }

    public int geteDom() {
        return this.eDom;
    }

    public int getTempoExtraTotal() {
        return this.tempoExtraTotal;
    }

    public void setTempoExtraTotal(int tempo) {
        this.tempoExtraTotal = tempo;
    }

    public void setFuncaoObjetivo(double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public void setfObjetivo1(int fObjetivo1) {
        this.fObjetivo1 = fObjetivo1;
    }

    public void setfObjetivo2(int fObjetivo2) {
        this.fObjetivo2 = fObjetivo2;
    }

    public void setfObjetivo3(int fObjetivo3) {
        this.fObjetivo3 = fObjetivo3;
    }

    public void setfObjetivo4(int fObjetivo4) {
        this.fObjetivo4 = fObjetivo4;
    }

    public void setfObjetivo5(int fObjetivo5) {
        this.fObjetivo5 = fObjetivo5;
    }

    public void setfObjetivo6(int fObjetivo6) {
        this.fObjetivo6 = fObjetivo6;
    }

    public void setfObjetivo7(int fObjetivo7) {
        this.fObjetivo7 = fObjetivo7;
    }

    public void setfObjetivo8(int fObjetivo8) {
        this.fObjetivo8 = fObjetivo8;
    }

    public void setfObjetivo9(int fObjetivo9) {
        this.fObjetivo9 = fObjetivo9;
    }

    public void setF1(double F1) {
        this.F1 = F1;
    }

    public void setF2(double F2) {
        this.F2 = F2;
    }

    public void setF1n(double F1n) {
        this.F1n = F1n;
    }

    public void setF2n(double F2n) {
        this.F2n = F2n;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * ******	Foi anexado a classe Route	*******

 public void setListaAtendimento(List<Request> listaAtendimento) {
     * this.listaAtendimento.clear(); this.listaAtendimento.addAll(new
     * LinkedList<Request>(listaAtendimento)); }
     *
     * public List<Request> getListaAtendimento() { return listaAtendimento; }
	**
     */
    public List<Request> getListaNaoAtendimento() {
        return listaNaoAtendimento;
    }

    public void setListaNaoAtendimento(List<Request> listaNaoAtendimento) {
        this.listaNaoAtendimento.clear();
        this.listaNaoAtendimento.addAll(new LinkedList<Request>(listaNaoAtendimento));
    }

    public List<Integer> getRotaConcatenada() {
        return rotaConcatenada;
    }

    public void setRotaConcatenada(List<Integer> rotaConcatenada) {
        this.rotaConcatenada.clear();
        this.rotaConcatenada.addAll(new ArrayList<Integer>(rotaConcatenada));
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

    public void concatenarRota() {
        for (Route r : conjRotas) {
            rotaConcatenada.addAll(r.getListaVisitacao().subList(1, r.getListaVisitacao().size() - 1));
        }
    }

    /**
     * ******	Foi anexado a classe Route	*******

 public void addAtendimento(Request request){
 listaAtendimento.add(request); }

 public void removeAtendimento(Request request){
 listaAtendimento.remove(request); }
	**
     */
    public void addNaoAtendimento(Request request) {
        listaNaoAtendimento.add(request);
    }

    public void removeNaoAtendimento(Request request) {
        listaNaoAtendimento.remove(request);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.000");
        //System.out.println(NumberFormat.getCurrencyInstance().format(12345678.908874));
        //if(conjRotas != null){// como impedir a chamada do metodo para conjRotas == null ???
        //String s = "FO1 = " + fObjetivo1 + "\t FO2 = " + fObjetivo2 + "\t FO3 = " +  fObjetivo3 +/*"\t Fit = "+ fitness +*/"\t U = "+listaNaoAtendimento.size() + "\t N = " + conjRotas.size() + "\t";
        //String s = fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+listaNaoAtendimento.size() + "\t" + conjRotas.size() + ";\t";
        //String s = fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + ";\t";
        //String s = funcaoObjetivo + "\t"+ F1 + "\t"+ F2 + "\t" + fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + "\t" + fObjetivo6 + "\t" + fObjetivo7 + "\t";
        String s = funcaoObjetivo + "\t"+fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + "\t" + fObjetivo6 + "\t" + fObjetivo7 + "\t"+ fObjetivo8 + "\t" + fObjetivo9 + "\t";
        //String s = funcaoObjetivo + "\t" + fObjetivo1 + "\t" + fObjetivo4 + "\t" + fObjetivo8 + "\t" + fObjetivo9 + "\t";
        //String s = fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + "\t" + fObjetivo6 + "\t" + fObjetivo7 + "\t";
        //String s = "F1 = "+ F1 + "\tF2 = "+ F2 + "\tFit = " + fitness +"\tf1 = " + fObjetivo1 + "\tf2 = " + fObjetivo2 + "\tf3 = " +  fObjetivo3 +"\tf4 = "+fObjetivo4 + "\tf5 = " + fObjetivo5 + ";\t";
        //String s = F1 + "\t"+ F2 + "\t"+ F1n + "\t"+ F2n + "\t" + eDom +"\t" + fitness +"\t" + fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + ";\t";

        //String s = F1 + "\t"+ F2 + "\t" + eDom +"\t" + fitness +"\t" + fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + ";\t";
        //String s = F1 + "\t"+ F2 + "\t" + eDom +"\t"+ L +";\t";
        //String s = F1 + "\t"+ F2 + "\t"+ df.format(F1n) + "\t"+ df.format(F2n) + "\t" + eDom +"\t" + df.format(fitness) +"\t" + L +" \t "+ S +" \t "+ R +" \t" + fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + ";\t";
        //String s = F1 + "\t"+ F2 + "\t"+ L + "\t" + nDom +"\t" + " \t "+ S +" \t "+ R +" \t" + fObjetivo1 + "\t" + fObjetivo2 + "\t" +  fObjetivo3 +"\t"+fObjetivo4 + "\t" + fObjetivo5 + ";\t";
        //String s = F1 + "\t"+ F2;
        //String s = df.format(F1n) + "\t"+ df.format(F2n)+";\t";
        int indice = 1;
        String listaAtendimento = " ";
        for (Route r : conjRotas) {
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

        if (conjRotas.size() != solucao2.getConjRotas().size()) {
            return false;
        }

        // deve olhar se as listas de rotas sao iguais
        for (Iterator<Route> i = conjRotas.iterator(); i.hasNext();) {
            if (!solucao2.getConjRotas().contains(i.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {

        if (conjRotas == null) {
            return -1;
        }

        int hash = 0;

        for (Route i : conjRotas) {
            hash += i.hashCode();
        }

        /*for(Iterator<Rota> i = solucao.iterator(); i.hasNext();)
				hash += i.next().hashCode();*/
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
