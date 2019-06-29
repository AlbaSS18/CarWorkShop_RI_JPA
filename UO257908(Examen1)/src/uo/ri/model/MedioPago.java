package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public abstract class MedioPago {
	
	private Long id;
	
	protected double acumulado = 0.0;
	
	protected Cliente cliente; 
	private Set<Cargo> cargos = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	
	public void pagar(double importe) {
		this.acumulado += importe;
	}

	@Override
	public String toString() {
		return "MedioPago [acumulado=" + acumulado 
				+ ", cliente=" + cliente + "]";
	}
	
	public double getAcumulado() {
		return acumulado;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Set<Cargo> getCargos() {
		return new HashSet<>(cargos);
	}
	
	Set<Cargo> _getCargos() {
		return cargos;
	}
	
	
	
	
	

}
