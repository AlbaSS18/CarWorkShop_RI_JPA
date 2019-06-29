package uo.ri.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import alb.util.date.Dates;
import uo.ri.model.types.ContractStatus;


public class Contract {

	private Long id;
	
	private Date startDate;
	private Date endDate = null;
	private double baseSalaryPerYear;
	private double compensation = 0.0;
	private ContractStatus status = ContractStatus.ACTIVE;

	private ContractType contractType;
	private ContractCategory contractCategory;
	private Mecanico mechanic;
	
	private Set<Payroll> payrolls = new HashSet<>();
	
	Contract(){
		
	}
	
	public Contract(Mecanico mecanico, Date startDate) {
		this.mechanic = mecanico;
		this.startDate = new Date(Dates.firstDayOfMonth(startDate).getTime());
		this.endDate = new Date();
		Association.Vincular.link(mecanico, this);
	}
	
	public Contract(Mecanico mechanic, Date startDate, double salarioBase) {
		this(mechanic,startDate);
		this.setBaseSalaryPerYear(salarioBase);
	}
	
	public Contract(Mecanico mechanic, Date startDate, Date endDate, 
			double baseSalary) {
		this(mechanic, startDate, baseSalary);
		this.setEndDate(endDate);
	}
	
	public void setCompensation(double compensation) {
		if(compensation<0) {
			throw new IllegalStateException("La compensación es negativa");
		}
		this.compensation = compensation;
	}

	
	public Contract(Mecanico mechanic, Date startDate, Date endDate,
			double baseSalary, ContractCategory categoria, ContractType tipo) {
		this(mechanic, startDate, endDate, baseSalary);
		Association.Typefy.link(this, tipo);
		Association.Categorize.link(this, categoria);
	}
	
	public Long getId() {
		return id;
	}

	public Date getStartDate() {
		return new Date(startDate.getTime());
	}

	public Date getEndDate() {
		if(endDate!=null)
			return new Date(endDate.getTime());
		return null;
	}
	
	public void setEndDate(Date endDate) {
		if(endDate!=null)
			this.endDate = Dates.lastDayOfMonth(endDate);
		else {
			this.endDate = null;
		}
	}

	public double getCompensation() {
		return compensation;
	}

	public double getBaseSalaryPerYear() {
		return baseSalaryPerYear;
	}

	public ContractStatus getStatus() {
		return status;
	}

	public void setBaseSalaryPerYear(double baseSalaryPerYear) {
		if(baseSalaryPerYear<0) {
			throw new IllegalArgumentException("El salario base es negativo");
		}
		this.baseSalaryPerYear = baseSalaryPerYear;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * 
				result + ((mechanic == null) ? 0 : mechanic.hashCode());
		result = prime * 
				result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Contract other = (Contract) obj;
		if (mechanic == null) {
			if (other.mechanic != null)
				return false;
		} else if (!mechanic.equals(other.mechanic))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contract [startDate=" + startDate + ", endDate=" 
				+ endDate + ", baseSalaryPerYear=" + baseSalaryPerYear + 
				", compensation=" + compensation + ", status=" + status + "]";
	}

	public ContractType getContractType() {
		return contractType;
	}

	void _setContractType(ContractType tipoContrato) {
		this.contractType = tipoContrato;
	}

	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	void _setContractCategory(ContractCategory categoriaContrato) {
		this.contractCategory = categoriaContrato;
	}

	public Mecanico getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mecanico mecanico) {
		this.mechanic = mecanico;
	}
	
	Set<Payroll> _getPayrolls() {
		return payrolls;
	}
	
	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
	}

	
	/**
	 * Método que marca como terminado un contrato, 
	 * le pone la fecha final y le calcula la compensación.
	 * @param endDate, la fecha final, de tipo Date 
	 */
	public void markAsFinished(Date endDate) {
		// Si ya hay un contrato activo, 
		// la fecha de inicio del nuevo contrato debe ser posterior
		// a la fecha de inicio del contrato activo, sino está mal
		if(endDate.before(this.startDate)) {
			throw new IllegalArgumentException("La fecha final es "
					+ "después de la fecha de inicio de contrato");
		}
			
		if(this.getStatus().equals(ContractStatus.ACTIVE)) {
			this.status = ContractStatus.FINISHED;
			setEndDate(endDate);
			calcularCompensacion();
		}
		else {
			throw new IllegalStateException("El contrato ya está terminado");
		}
		
	}

	/**
	 * Método que nos dice si el contrato tiene nóminas o no.
	 * @return true si no tiene nóminas, false si tiene nóminas.
	 */
	public boolean nominasParaEsteContrato() {
		Set<Payroll> listaNominas = this.payrolls;
		if(listaNominas.size()==0) {
			return true; 
		}
		return false;
	}

	/**
	 * Método que nos indica si un mecánico intervino en 
	 * averías entre la fecha de inicio y la fecha de fin del contrato
	 * @return false, si tuvo actividad en su vigencia. true si no la tuvo.
	 */
	public boolean actividadDuranteSuVigencia() {
		Set<Intervencion> listaIntervencionesMecanico = 
				this.getMechanic().getIntervenciones();
		for(Iterator<Intervencion> it = listaIntervencionesMecanico.iterator();
				it.hasNext();) { 
			Averia x = (Averia) it.next().getAveria();
			if(x.getFecha().before(this.endDate) && 
					x.getFecha().after(this.startDate)) {
				return false; 
			}
		}
		return true;
	}

	/**
	 * Método que calcula la compensación
	 */
	private void calcularCompensacion() {
		double calcularSalarioBruto12Meses = 
				calcularSalarioBruto12Meses()/365;
		double calcularIndemnizacion = calcularIndemnizacionTipoContrato();
		double calcularNumeroAños = calcularNumeroAños();
		
		this.setCompensation(calcularIndemnizacion * 
				calcularSalarioBruto12Meses * 
				calcularNumeroAños);
	}

	/**
	 * Método que calcula el número de años 
	 * entre la fecha final y la fecha de inicio.
	 * @return el número de años, de tipo double
	 */
	private double calcularNumeroAños() {	
		Date copiaFinal = new Date(this.endDate.getTime());
		copiaFinal = Dates.addDays(copiaFinal, 1);
		return Dates.diffDays(copiaFinal,this.startDate)/365;
	}

	private double calcularIndemnizacionTipoContrato() {
		return this.contractType.getCompensationDays();
	}

	/**
	 * Método que calcula la suma de los 
	 * salarios brutos de las últimas 12 nóminas. 
	 * @return la suma del los salarios brutos, de tipo double
	 */
	private double calcularSalarioBruto12Meses() {
		List<Payroll> lista = devolverLista12Nominas();
		double suma = 0;
		for(Payroll pay: lista) {
			suma += pay.getGrossTotal();
		}
		return suma;
	}

	/**
	 * Método que nos dice si el contrato está terminado o no.
	 * @return false, si está activo. True, si está terminado.
	 */
	public boolean isFinished() {
		if(this.getStatus().equals(ContractStatus.ACTIVE)) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Método que devuelve la última nómina del contrato
	 * @return la última nómina del contrato.
	 */
	public Payroll getLastPayroll() {
		Date d = Dates.fromString("0/0/0");
		for (Payroll p : payrolls) {
			if (Dates.isAfter(p.getDate(), d))
				d = p.getDate();
		}
		for (Payroll p : payrolls) {
			if (p.getDate().equals(d))
				return p;
		}
		return null;
	}

	public double getIrpfPercent() {
		if(baseSalaryPerYear<12000) {
			return 0;
		}
		else if(baseSalaryPerYear<30000) {
			return 10.0/100;
		}
		else if(baseSalaryPerYear<40000) {
			return 15.0/100;
		}
		else if(baseSalaryPerYear<50000) {
			return 20.0/100;
		}
		else if(baseSalaryPerYear<60000) {
			return 30.0/100;
		}
		else {
			return 40.0/100;
		}
	}
	
	/**
	 * Método que devuelve las últimas 12 nóminas del contrato.
	 * @return una lista con las últimas 12 nóminas.
	 */
	public List<Payroll> devolverLista12Nominas(){
		List<Payroll> lista = ordenarLista();
		
		int j=0;
		List<Payroll> lista12Nominas = new ArrayList<Payroll>();
		for(int i=lista.size()-1; i>=0; i--) {
			if(j<12) {
				lista12Nominas.add(lista.get(i));
			}
			j++;
		}

		return lista12Nominas;
		
	}
	
	class ComparadorNominas implements Comparator<Payroll> {

		@Override
		public int compare(Payroll o1, Payroll o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
		
	}
	
	/**
	 * Método que ordena la lista de las nóminas por fecha.
	 * @return una lista con las nóminas ordenadas por fecha.
	 */
	private List<Payroll> ordenarLista() {
		List<Payroll> payroll = new ArrayList<Payroll>();
		for( Iterator<Payroll> it = payrolls.iterator(); it.hasNext();) { 
			Payroll x = (Payroll)it.next();
			payroll.add(x);
		}
		
		payroll.sort(new ComparadorNominas());
		return payroll;
	}

	
	
}
