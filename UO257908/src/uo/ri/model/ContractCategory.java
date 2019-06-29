package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class ContractCategory {

	private Long id;

	private String name;
	private double trieniumSalary;
	private double productivityPlus;

	private Set<Contract> contract = new HashSet<>();

	ContractCategory() {
	}

	public ContractCategory(String name) {
		this.name = name;
	}

	public ContractCategory(String name, double trienniumSalary, 
			double productivityPlus) {
		this(name);
		this.setTrieniumSalary(trienniumSalary);
		this.setProductivityPlus(productivityPlus);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getTrieniumSalary() {
		return trieniumSalary;
	}

	public double getProductivityPlus() {
		return productivityPlus;
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
		ContractCategory other = (ContractCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractCategory [name=" + name 
				+ ", trieniumSalary=" + trieniumSalary + ", productivityPlus="
				+ productivityPlus + "]";
	}

	public Set<Contract> getContract() {
		return new HashSet<>(contract);
	}

	Set<Contract> _getContract() {
		return contract;
	}

	public void setTrieniumSalary(double trieniumSalary) {
		if (trieniumSalary < 0) {
			throw new IllegalArgumentException("El triennium "
					+ "es negativo");
		}
		this.trieniumSalary = trieniumSalary;

	}

	public void setProductivityPlus(double productivityPlus) {
		if (productivityPlus < 0) {
			throw new IllegalArgumentException("El plus de "
					+ "productividad es negativo");
		}
		this.productivityPlus = productivityPlus;
	}

}
