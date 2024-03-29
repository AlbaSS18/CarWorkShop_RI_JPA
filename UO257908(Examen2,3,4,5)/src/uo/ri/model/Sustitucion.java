package uo.ri.model;

public class Sustitucion {
	
	private Long id;


	private Repuesto repuesto;
	private Intervencion intervencion;
	private int cantidad;
	
	Sustitucion(){
		
	}
	
	public Sustitucion(Repuesto repuesto, Intervencion intervencion) {
		Association.Sustituir.link(intervencion, this, repuesto);
	}

	public Sustitucion(Repuesto repuesto, Intervencion intervencion, 
			int cantidad) {
		this(repuesto,intervencion);
		this.setCantidad(cantidad);
	}	

	public Long getId() {
		return id;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		if(cantidad<=0) {
			throw new IllegalArgumentException("La cantidad es menor "
					+ "o igual que cero");
		}
		this.cantidad = cantidad;
	}
	
	public Repuesto getRepuesto() {
		return repuesto;
	}
	
	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}
	
	
	public double getImporte() {
		return this.repuesto.getPrecio()*this.cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * 
			result + ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime 
				* result + ((repuesto == null) ? 0 : repuesto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [cantidad=" + cantidad + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
