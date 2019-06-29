package uo.ri.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

public class Factura {

	private Long id;
	
	private Long numero;
	private Date fecha;
	private double importe;
	private double iva;
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;
	
	private Set<Cargo> cargos = new HashSet<>();
	private Set<Averia> averias = new HashSet<>();


	Factura(){
		
	}
	
	public Factura(Long numero) {
		super();
		this.fecha = new Date();
		this.numero = numero;
	}

	
	public Factura(Long numero, Date fecha) {
		this(numero);
		this.setFecha(fecha);
	}


	public Factura(Long numero, List<Averia> averias) {
		this(numero);
		for(Averia averia : averias) {
			this.addAveria(averia);
		}
	}


	public Factura(Long numero, Date fecha, List<Averia> averias) {
		this(numero,fecha);		
		for(Averia averia : averias) {
			this.addAveria(averia);
		}
	}
	
	public Long getId() {
		return id;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return new Date(fecha.getTime());
	}


	public double getImporte() {
		return importe;
	}


	public double getIva() {
		return iva;
	}


	public FacturaStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha 
				+ ", importe=" + importe + ", iva=" 
				+ iva + ", status=" + status + "]";
	}


	/**
	 * Añade la averia a la factura y actualiza el importe e iva de la factura
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void addAveria(Averia averia) {
		
		// Verificar que la factura está en estado SIN_ABONAR
		// Verificar que La averia está TERMINADA
		// linkar factura y averia
		// marcar la averia como FACTURADA ( averia.markAsInvoiced() )
		// calcular el importe
		if(this.getStatus().equals( FacturaStatus.SIN_ABONAR)) {
			if(averia.getStatus().equals(AveriaStatus.TERMINADA)) {
				Association.Facturar.link(this, averia);
				averia.markAsInvoiced();
				calcularImporte();
			}
			else {
				throw new IllegalStateException("La avería no está terminada");
			}
		}
		else {
			throw new IllegalStateException("La factura está abonada");
		}
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de 
	 * factura
	 */
	void calcularImporte() {
		// iva = ...
		// importe = ...
		//IVA
		Calendar calendario = Calendar.getInstance();
		calendario.set(2012, 7, 1);
		
		Date fechaAComparar = calendario.getTime();
		
		if(fecha.before(fechaAComparar)) {
			this.iva = 18;
		}
		else {
			this.iva = 21;
		}
//		//Importe
		double sumaAveria = 0;
		for(Averia averia : averias) {
			sumaAveria += averia.calcularImporte();
		}
		this.importe =Math.round((sumaAveria + (sumaAveria * 
				this.getIva()/100))*100d)/100d;
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula 
	 * el importe
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		// desenlazar factura y averia
		// retornar la averia al estado FINALIZADA ( averia.markBackToFinished() )
		// volver a calcular el importe
		if(this.getStatus().equals( FacturaStatus.SIN_ABONAR)) {
			averia.markBackToFinished();
			Association.Facturar.unlink(this, averia);
			calcularImporte();
		}
		else {
			throw new IllegalStateException("La factura está abonada");
		}
	}

	/**
	 * Marks the invoice as ABONADA, but
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException if
	 * 	- Is already settled, or 
	 *  - the amounts paid with charges to payment means does not cover 
	 *  	the total of the invoice  
	 */
	public void settle() {
		if(this.status.equals(FacturaStatus.ABONADA)) {
			throw new IllegalStateException("La factura ya está abonada");
		}
		else if(calcularTotalCargos()<this.importe) {
			throw new IllegalStateException("La cantidad pagada con "
					+ "los cargos no cubre el total de la factura");
		}
		this.status = FacturaStatus.ABONADA;		
	}


	private double calcularTotalCargos() {
		double suma = 0;
		for(Cargo ca : cargos) {
			suma += ca.getImporte();
		}
		return suma;
	}

	public Set<Cargo> getCargos() {
		return new HashSet<>(cargos);
	}
	
	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Set<Averia> getAverias() {
		return new HashSet<>(averias);
	}
	
	Set<Averia> _getAverias() {
		return averias;
	}


	public void setFecha(Date fecha) {
		Date date = new Date(fecha.getTime());
		this.fecha = date;
	}
}
