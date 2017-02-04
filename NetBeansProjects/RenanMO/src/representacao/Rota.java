package representacao;

import java.util.ArrayList;
import java.util.List;

public class Rota implements Comparable<Rota>{
	//rota - sequencia de visita��o
	private List<Integer> listaVisitacao;
	
	//Vetor Qik carga(lotacao) do veiculo k ao deixar o vertice i
	private List<Integer> Qik;
	
	// Vetor Tempoik do veiculo k ao deixar o vertice i
	private List<Integer> Tempoik;
	
	// Lista de solicitacoes atendidas	
	private List<Request> listaAtendimento;
        
        private Integer tempoExtra;
	
	public Rota(){
		listaVisitacao = new ArrayList<Integer>();
		Qik = new ArrayList<Integer>();
		Tempoik = new ArrayList<Integer>();	
		listaAtendimento = new ArrayList<Request>();
	}
	
	public Rota(Rota rota2){
		listaVisitacao = new ArrayList<Integer>(rota2.getListaVisitacao());
		Qik = new ArrayList<Integer>(rota2.getQik());
		Tempoik = new ArrayList<Integer>(rota2.getTempoik());
		listaAtendimento = new ArrayList<Request>(rota2.getListaAtendimento());
	}
	
	public List<Integer> getListaVisitacao() {
		return listaVisitacao;
	}
	
	public void setListaVisitacao(List<Integer> listaVisitacao) {
		this.listaVisitacao.clear();
		this.listaVisitacao.addAll(listaVisitacao);
	}
	
	public List<Integer> getQik() {
		return Qik;
	}

	public void setQik(List<Integer> qik) {
		this.Qik.clear();
		this.Qik.addAll(qik);
	}

	public List<Integer> getTempoik() {
		return Tempoik;
	}

	public void setTempoik(List<Integer> tempoik) {
		this.Tempoik.clear();
		this.Tempoik.addAll(tempoik);
	}

	public List<Request> getListaAtendimento() {
		return listaAtendimento;
	}
	
	public void setListaAtendimento(List<Request> listaAtendimento) {
		this.listaAtendimento.clear();
		this.listaAtendimento.addAll(new ArrayList<Request>(listaAtendimento));
	}
	

	public Integer getLastNode(){
		int posicao = listaVisitacao.size()-1;
		return listaVisitacao.get(posicao);
	}
	
	public Integer getLotacaoAtual(){
		int posicao = Qik.size()-1;
		return Qik.get(posicao);
	}
	
	public Integer getTempoAtual(){
		int posicao = Tempoik.size()-1;
		return Tempoik.get(posicao);
	}
	
        public Integer getTempoExtra(){
            return tempoExtra;
        }
        
        public void setTempoExtra(Integer tempo){
            tempoExtra = tempo;
        }
        
	public void setTempoikDeposito(int horario){
		Tempoik.set(0, horario);
	}
	
	public void addVisitacao(Integer visitacao){
		listaVisitacao.add(visitacao);
		
		int posicao = Qik.size()-1;
		int lotacao;
		
		if(posicao >= 0){
			lotacao = Qik.get(posicao);
			Qik.add(lotacao);
		}
		else
			Qik.add(0);
		
		Tempoik.add(-1);
	}
	
	public void addEmbarque(Request request, int horario){
		int posicao = Qik.size()-1;
		int lotacao = Qik.get(posicao);
		
		Qik.set(posicao, lotacao+1);
		
		Tempoik.set(posicao, horario);
		
		request.setPickupTime(horario);
		addAtendimento(request);		
	}
	
	public void addDesembarque(Request request, int horario){
		int posicao = Qik.size()-1;
		if(posicao == -1 || posicao != Tempoik.size()-1)
			System.out.println("POSICAO INVALIDA");
		int lotacao = Qik.get(posicao);
		
		Qik.set(posicao, lotacao-1);
		
		Tempoik.set(posicao, horario);
		
		int posListaAtendimento = getListaAtendimento().indexOf(request);
		if(posListaAtendimento == -1 )
			System.out.println("O CARA "+request);//EIN???????
		Request reqArmazenada = getListaAtendimento().get(posListaAtendimento);
		
		reqArmazenada.setDeliveryTime(horario);
		getListaAtendimento().set(posListaAtendimento, reqArmazenada);
		//System.out.println();
	}
	
	public void addAtendimento(Request request){
		listaAtendimento.add((Request)request.clone());
	}
	
	public void removeAtendimento(Request request){
		listaAtendimento.remove((Request)request.clone());
	}
	
	@Override
	public String toString(){
		String s = "";
		
		//if(listaVisitacao != null){// como impedir a chamada do m�todo para listaVisitacao == null ???
			for(Integer v : listaVisitacao)
				s += v+" ";
		//}
		return s;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Rota && equals((Rota)obj);
	}

	public boolean equals (Rota rota2){
		if(this == rota2)
			return true;
		
		if(rota2 == null)
			return false;
		
		if(listaVisitacao.size() != rota2.getListaVisitacao().size())
			return false;
		
		for(int i = 0; i < listaVisitacao.size(); i++)
			if(listaVisitacao.get(i)!= rota2.getListaVisitacao().get(i))
                		return false;
		
		return true;
	}
	
	@Override
	public int hashCode(){
		
		if(listaVisitacao == null)
			return -1;
		
		int hash = 0;
		String s = "";
		
		for(Integer i : listaVisitacao)
			s += i.toString();
		
		hash = s.hashCode();
		/*for(int i = 0; i < listaVisitacao.size(); i++)
				s += listaVisitacao.get(i)*(i+1);*/
		
		return hash;
	}
        
        @Override
        public int compareTo(Rota r){
            if(this.getListaAtendimento().size() > r.getListaAtendimento().size()){
                return 1;
            }
            if(this.getListaAtendimento().size() < r.getListaAtendimento().size()){
                return -1;
            }
            return 0;
        }
        
}