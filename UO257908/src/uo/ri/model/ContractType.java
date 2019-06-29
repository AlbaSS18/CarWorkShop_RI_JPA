package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class ContractType {
	
	private Long id;
	
	private String name;
	private int compensationDays;

	private Set<Contract> contrato = new HashSet<>();

	ContractType() {
		
	}
	
	public ContractType(String name) {
		this.name = name;
	}
	
	public ContractType(String name, int compensationDays) {
		this(name);
		this.setCompensationDays(compensationDays);
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getCompensationDays() {
		return compensationDays;
	}
	
	public void setCompensationDays(int compensationDays) {
		if(compensationDays<0) {
			throw new IllegalArgumentException("Los días de "
					+ "compensación son negativos");
		}
		this.compensationDays = compensationDays;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ContractType other = (ContractType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ContractType [name=" + name 
				+ ", compensationDays=" + compensationDays + "]";
	}

	Set<Contract> _getContrato() {
		return contrato;
	}
	
	public Set<Contract> getContrato() {
		return new HashSet<>(contrato);
	}
	
	

}
