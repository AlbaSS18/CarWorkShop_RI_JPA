package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class Intervencion {
	
	private Long id;

	private Averia averia;
	private Mecanico mecanico;
	private int minutos;
	
	private Set<Sustitucion> sustituciones = new HashSet<>();
	
	Intervencion() {
	}
	
	public Intervencion( Mecanico mecanico, Averia averia) {
		Association.Intervenir.link(averia, this, mecanico);
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averia == null) ? 0 : averia.hashCode());
		result = prime * 
				result + ((mecanico == null) ? 0 : mecanico.hashCode());
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
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals(other.averia))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Intervencion [averia=" + averia + ", "
				+ "mecanico=" + mecanico + ", minutos=" + minutos + "]";
	}

	void _setAveria(Averia averia) {
		this.averia = averia;
	}
	
	public Averia getAveria() {
		return averia;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}


	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getMinutos() {
		return minutos;
	}
	
	public Set<Sustitucion> getSustituciones() {
		return new HashSet<>(sustituciones);
	}

	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}
	
	/**
	 * Método que devuelve el importe de la intervención, 
	 * sumando el importe de la mano de obra más el importe de repuesto
	 * @return importe, de tipo double
	 */
	public double getImporte() {
		double importeRepuesto = 0;
		
		for(Sustitucion sustitucion : sustituciones) {
			importeRepuesto += sustitucion.getImporte();
		}
		
		double importeManoDeObra = 0;
		
		TipoVehiculo tipo = this.averia.getVehiculo().getTipo();
		double precioHora = tipo.getPrecioHora();
		
		importeManoDeObra = precioHora/60 * minutos;
		
		return importeManoDeObra + importeRepuesto;

	}
	
		
}
