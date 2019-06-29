package uo.ri.model;

import java.util.Date;

import alb.util.date.Dates;

public class Payroll {
	
	private Long id;

	private Date date;
	private double baseSalary;
	private double extraSalary;
	private double productivity;
	private double triennium;
	private double irpf;
	private double socialSecurity;

	private Contract contract;
	
	Payroll(){
		
	}
	
	public Payroll(Contract contrato, Date fecha) {		
		this.contract = contrato;
		
		Date fechaMesMenos = Dates.subMonths(fecha, 1);
		Date ultimoDia = Dates.lastDayOfMonth(fechaMesMenos);
		this.date = new Date(ultimoDia.getTime());
		if(this.date.before(contrato.getStartDate())) {
			throw new IllegalArgumentException("La fecha de la nómina es "
					+ "anterior a la fecha de inicio del contrato");
		}
		
		Association.Percibir.link(this, contrato);
	}
	
	public Payroll(Contract contractos, Date fecha, 
			double totalOfInterventions) {
		this(contractos,fecha);		
		
		this.setBaseSalary(this.contract.getBaseSalaryPerYear()/14.0); 
		this.setExtraSalary(this.contract.getBaseSalaryPerYear()/14.0); 
		this.setProductivity(totalOfInterventions
				*this.contract.getContractCategory().getProductivityPlus()); 
		int years = (Dates.diffDays(getDate(), contract.getStartDate())/365)/3;
		this.setTriennium(this.contract
				.getContractCategory().getTrieniumSalary()*years); 
		this.setIrpf(getGrossTotal() * this.contract.getIrpfPercent());		
		this.setSocialSecurity((this.contract.getBaseSalaryPerYear()/12)*0.05); 
		
	}
	
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public void setExtraSalary(double extraSalary) {
		if(Dates.month(this.date)==6 || Dates.month(this.date)==12) {
			this.extraSalary = extraSalary;
		}
	}

	public void setTriennium(double triennium) {
		this.triennium = triennium;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public double getExtraSalary() {
		return extraSalary;
	}

	public double getProductivity() {
		return productivity;
	}

	public double getTriennium() {
		return triennium;
	}

	public double getIrpf() {
		return irpf;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}
	
	public void setProductivity(double productivity) {
		this.productivity = productivity;
	}
	
	public void setIrpf(double irpf) {
		this.irpf = irpf;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime 
				* result + ((contract == null) ? 0 : contract.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Payroll other = (Payroll) obj;
		if (contract == null) {
			if (other.contract != null)
				return false;
		} else if (!contract.equals(other.contract))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Payroll [date=" + date + ", baseSalary=" 
				+ baseSalary + ", extraSalary=" + extraSalary
				+ ", productivity=" + productivity 
				+ ", trienniums=" + triennium + ", irpf=" + irpf
				+ ", socialSecurity=" + socialSecurity + "]";
	}

	public Contract getContract() {
		return contract;
	}

	void _setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * Método que calcula la suma de los abonos
	 * @return total bruto, de tipo double
	 */
	public double getGrossTotal() {
		return this.baseSalary + this.extraSalary 
				+ this.productivity + this.triennium;
	}

	/**
	 * Método que calcula la suma de los descuentos
	 * @return total de los descuentos, de tipo double
	 */
	public double getDiscountTotal() {
		return this.irpf + this.socialSecurity;
	}

	/**
	 * Método que calcula el total neto
	 * @return el total neto, de tipo double
	 */
	public double getNetTotal() {
		return getGrossTotal() - getDiscountTotal();
	}

	
	
	
	
}
