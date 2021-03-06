/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProblemRepresentation;

/**
 *
 * @author Renan
 */
public class Request implements Comparable, Cloneable{
	
	private Integer id;				// identificador da solicitacao
	
	private Integer origin,			// v�rtice de origem
					destination;	// v�rtice de destino
	
	// ?? usar int ou TIME?
	//tempo em minutos
	private Integer pickupE,		// tempo inicio p/ coletar na origem 
			pickupL,		// tempo fim p/ coletar na origem
			deliveryE,		// tempo inicio p/ entregar no destino
			deliveryL,		// tempo fim p/ entregar no destino
			pickupTime = -1,	// tempo em que foi coletado na origem
			deliveryTime = -1;	// tempo em que foi entregue no destino
		
	private Integer timeWindowDefault = 3;	
	
	
	public Request(){
		
	}
	
	public Request( int id, int origin, int destination, int pickupE, int deliveryE){
		this.id = id;
		
		this.origin = origin;
		this.destination = destination;
		
		this.pickupE = pickupE;
		this.pickupL = pickupE + timeWindowDefault;
		
		this.deliveryE = deliveryE;
		this.deliveryL = deliveryE + timeWindowDefault;
		
		
	}
	
	public Request( int id, int origin, int destination, int pickupE, int pickupL, int deliveryE, int deliveryL){
		this.id = id;
		
		this.origin = origin;
		this.destination = destination;
		
		this.pickupE = pickupE;
		if(pickupL - pickupE > 0)
			this.pickupL = pickupL;
		else
			this.pickupL = pickupE + timeWindowDefault;
		
		this.deliveryE = deliveryE;
		if(deliveryL - deliveryE > 0)
			this.deliveryL = deliveryL;
		else
			this.deliveryL = deliveryE + timeWindowDefault;
	}
	
	public Request( Request req){
		this.id = req.id;
		
		this.origin = req.origin;
		this.destination = req.destination;
		
		this.pickupE = req.pickupE;
		this.pickupL = req.pickupL;		
		
		this.deliveryE = req.deliveryE;
		this.deliveryL = req.deliveryL;
		
		this.pickupTime = req.pickupTime;
		this.deliveryTime = req.deliveryTime;
		
		this.timeWindowDefault = req.timeWindowDefault;
		
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getPickupE() {
		return pickupE;
	}

	public void setPickupE(int pickupE) {
		this.pickupE = pickupE;
	}

	public int getPickupL() {
		return pickupL;
	}

	public void setPickupL(int pickupL) {
		
		if(pickupL - this.pickupE > 0)
			this.pickupL = pickupL;
		else
			this.pickupL = pickupE + timeWindowDefault;
	}

	public int getDeliveryE() {
		return deliveryE;
	}

	public void setDeliveryE(int deliveryE) {
		this.deliveryE = deliveryE;
	}

	public int getDeliveryL() {
		return deliveryL;
	}

	public void setDeliveryL(int deliveryL) {
		
		if(deliveryL - this.deliveryE > 0)
			this.deliveryL = deliveryL;
		else
			this.deliveryL = deliveryE + timeWindowDefault;
	}

	public Integer getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(int pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getTimeWindowDefault() {
		return timeWindowDefault;
	}

	public void setTimeWindowDefault(int timeWindowDefault) {
		if(timeWindowDefault > 0)
			this.timeWindowDefault = timeWindowDefault;
	}
	
	

	@Override
	public String toString(){
		
		if( this.origin == null || this.destination == null || 	this.pickupE == null || this.pickupL == null || this.deliveryE == null || this.deliveryL == null )
			return "V"+origin+"-V"+destination+": ["+pickupE+","+pickupL+"]-["+deliveryE+","+deliveryL+"];";
		
			String s = "id_"+id+"_V"+origin+"-V"+destination+": [";
			
			if(pickupTime/60 > -1 && pickupTime/60 < 10)
				s += "0";
			s += pickupTime/60+"h";
			
			if(pickupTime%60 > -1 && pickupTime%60 < 10)
				s += "0";
			s += pickupTime%60+": ";
			
			
			if(pickupE/60 > -1 && pickupE/60 < 10)
				s += "0";
			s += pickupE/60+"h";
			
			if(pickupE%60 > -1 && pickupE%60 < 10)
				s += "0";
			s += pickupE%60+",";
			
			if(pickupL/60 > -1 && pickupL/60 < 10)
				s += "0";
			s += pickupL/60+"h";
			
			if(pickupL%60 > -1 && pickupL%60 < 10)
				s += "0";
			s += pickupL%60+"]-[";
			
			
			if(deliveryTime/60 > -1 && deliveryTime/60 < 10)
				s += "0";
			s += deliveryTime/60+"h";
			
			if(deliveryTime%60 > -1 && deliveryTime%60 < 10)
				s += "0";
			s += deliveryTime%60+": ";
			
			
			if(deliveryE/60 > -1 && deliveryE/60 < 10)
				s += "0";
			s += deliveryE/60+"h";
			
			if(deliveryE%60 > -1 && deliveryE%60 < 10)
				s += "0";
			s += deliveryE%60+",";
			
			if(deliveryL/60 > -1 && deliveryL/60 < 10)
				s += "0";
			s += deliveryL/60+"h";
			
			if(deliveryL%60 > -1 && deliveryL%60 < 10)
				s += "0";
			s += deliveryL%60+"] ";
			
			//FALTA ACRESCENTAR O PICKUPTIME E O DELIVERYTIME NA IMPRESSAO

		return s;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Request && equals((Request)obj);
	}

	public boolean equals (Request request2){
		if(this == request2)
			return true;
		
		if(request2 == null)
			return false;
		
		if( !id.equals(request2.id) /*|| !origin.equals(request2.origin)  || !destination.equals(request2.destination) || 
				!pickupE.equals(request2.pickupE) || !pickupL.equals(request2.pickupL) || 
				!deliveryE.equals(request2.deliveryE) || !deliveryL.equals(request2.deliveryL)*/ /*||
				!pickupTime.equals(request2.pickupTime) || !deliveryTime.equals(request2.deliveryTime) ||
				!timeWindowDefault.equals(request2.timeWindowDefault)*/ )
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode(){
		
		if(origin == -1 || destination == -1)
			return -1;
		
		int hash = 0;
		
		String s = Integer.toString(id) + Integer.toString(origin) + Integer.toString(destination) + 
					Integer.toString(pickupE) + Integer.toString(pickupL) + 
					Integer.toString(deliveryE) + Integer.toString(deliveryL) + 
					Integer.toString(pickupTime) + Integer.toString(deliveryTime) + Integer.toString(timeWindowDefault);
		
		hash = s.hashCode();
		
		return hash;
	}
	
	@Override
	public int compareTo(Object obj) {
		return compareTo((Request)obj);
	}
	
	public int compareTo(Request r2){
		if(r2.getId().compareTo(this.id) == 0)
			return 0;
		
		if(((Integer)r2.pickupE).compareTo(this.pickupE) > 0)
			return -1;
		
		if(((Integer)r2.pickupE).compareTo(this.pickupE) < 0)
			return 1;
		
		if(((Integer)r2.deliveryE).compareTo(this.deliveryE) > 0)
			return -1;
		
		if(((Integer)r2.deliveryE).compareTo(this.deliveryE) < 0)
			return 1;
		
		if(((Integer)r2.pickupL).compareTo(this.pickupL) > 0)
			return -1;
		
		if(((Integer)r2.pickupL).compareTo(this.pickupL) < 0)
			return 1;
		
		if(((Integer)r2.deliveryL).compareTo(this.deliveryL) > 0)
			return -1;
		
		if(((Integer)r2.deliveryL).compareTo(this.deliveryL) < 0)
			return 1;
		
		return 0;
	}
		
	@Override
	public Object clone(){
		
		Request request = null;
		
		try {
			request = (Request)super.clone();
			
			request.id = new Integer(this.id);
			
			request.origin = new Integer(this.origin);
			
			request.destination = new Integer(this.destination);
			
			request.pickupE = new Integer(this.pickupE);
			
			request.pickupL = new Integer(this.pickupL);
			
			request.deliveryE = new Integer(this.deliveryE);
			
			request.deliveryL = new Integer(this.deliveryL);
			
			request.pickupTime = new Integer(this.pickupTime);
			
			request.deliveryTime = new Integer(this.deliveryTime);
			
			request.timeWindowDefault = new Integer(this.timeWindowDefault);
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return request;
		
	}
	
}