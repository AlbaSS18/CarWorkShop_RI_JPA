package uo.ri.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uo.ri.model.types.ContractStatus;

public class Mecanico {

	private Long id;
	
	
	private String dni;
	private String apellidos;
	private String nombre;

	private Set<Averia> averias = new HashSet<>(); 
	private Set<Intervencion> intervencion = new HashSet<>();
	private Set<Contract> contracts = new HashSet<>(); 

	Mecanico(){}
	
	public Mecanico(String dni) {
		this.dni = dni;
	}

	public Mecanico(String dni, String nombre, String apellidos) {
		this(dni);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
	}

	public Long getId() {
		return id;
	}

	public String getDni() {
		return dni;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mecanico [dni=" + dni + ", apellidos=" 
				+ apellidos + ", nombre=" + nombre + "]";
	}
	
	
	public Set<Intervencion> getIntervenciones(){
		return new HashSet<>(intervencion) ;
	}
	
	 Set<Intervencion> _getIntervenciones(){
		 return intervencion;
	 }
	
	Set<Averia> _getAsignadas() {
		return averias;
	}

	public Set<Averia> getAsignadas() {
		return new HashSet<>(averias);
	}

	public void setApellidos(String surname) {
		this.apellidos = surname;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	Set<Contract> _getContracts() {
		return contracts;
	}
	
	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	/**
	 * Método que devuelve el contrato activo de un mecánico
	 * @return el contracto activo del mecánico
	 */
	public Contract getActiveContract() {
		for(Iterator<Contract> it = contracts.iterator(); it.hasNext();) { 
		    Contract x = (Contract)it.next();
		    if(x.getStatus().equals(ContractStatus.ACTIVE)) {
		    	return x;
		    }
		}
		return null;
	}
	
	
}
