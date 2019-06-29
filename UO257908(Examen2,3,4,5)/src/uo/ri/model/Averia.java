package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import uo.ri.model.types.AveriaStatus;

public class Averia {

	private Long id;
	
	private String descripcion;
	private Date fecha;
	private double importe = 0.0;
	private AveriaStatus status = AveriaStatus.ABIERTA;
	
	private Vehiculo vehiculo;
	private Mecanico mecanico;
	private Factura factura;

	private Set<Intervencion> intervencion = new HashSet<>();
	
	Averia(){
		
	}
	
	public Averia(Vehiculo vehiculo) {
		super();
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}
	

	public Averia(Vehiculo vehiculo, String descripcion) {
		this(vehiculo);
		this.setDescripcion(descripcion);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime *
				result + ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return new Date(fecha.getTime());
	}

	public double getImporte() {
		return importe;
	}

	public AveriaStatus getStatus() {
		return status;
	}
	
	

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion 
				+ ", fecha=" + fecha + ", importe=" 
				+ importe + ", status=" + status
				+ ", vehiculo=" + vehiculo + "]";
	}

	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado ABIERTA, o
	 *  - La avería ya está enlazada con otro mecánico
	 */
	public void assignTo(Mecanico mecanico) {
		// Solo se puede asignar una averia que está ABIERTA
		// linkado de averia y mecanico
		// la averia pasa a ASIGNADA
		if(this.getStatus().equals(AveriaStatus.ABIERTA)) {
			this.status = AveriaStatus.ASIGNADA;
			Association.Asignar.link(mecanico, this);			
		}
		else {
			throw new IllegalStateException(
					"La avería no está en estado abierta");
		}
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el 
	 * importe y pasa a TERMINADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado ASIGNADA, o
	 *  - La avería no está enlazada con un mecánico
	 */
	public void markAsFinished() {
		if(this.getStatus().equals(AveriaStatus.ASIGNADA)) {
			if(this.mecanico!=null) {
				this.status = AveriaStatus.TERMINADA;
				calcularImporte();	  
			}
			else {
				throw new IllegalStateException(
						"La avería no está asignada con ningún mecánico");
			}
		}
		else {
			throw new IllegalStateException(
					"La avería no está en estado asignada");
		}
	}


	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico
	 * (p.e., el primero no ha hecho bien la reparación), pero debe ser pasada 
	 * a ABIERTA primero
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado TERMINADA
	 */
	public void reopen() {
		// Se verifica que está en estado TERMINADA
		// Se pasa la averia a ABIERTA
		if(this.getStatus().equals(AveriaStatus.TERMINADA)) {
			this.status = AveriaStatus.ABIERTA;
		}
		else {
			throw new IllegalStateException(
					"La avería no está en estado terminada");
		}
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.removeAveria()
	 * Retrocede la averia a TERMINADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado FACTURADA, o 
	 *  - La avería aún está enlazada con la factura
	 */
	public void markBackToFinished() {
		if(this.getStatus().equals(AveriaStatus.FACTURADA))
			if(factura!=null) {
				this.status = AveriaStatus.TERMINADA;
			}
			else {
				throw new IllegalStateException(
						"La avería no está enlazada con una factura");
			}
		else {
			throw new IllegalStateException(
					"La avería no está en estado facturada");
		}
		
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria()
	 * Marca la averia como FACTURADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado TERMINADA, o 
	 *  - La avería no está enlazada con una factura
	 */
	public void markAsInvoiced() {
		if(this.getStatus().equals(AveriaStatus.TERMINADA))
			if(factura!=null) {
				this.status = AveriaStatus.FACTURADA;
			}
			else {
				throw new IllegalStateException(
						"La avería no está enlazada con una factura");
			}
		else {
			throw new IllegalStateException(
					"La avería no está en estado terminada");
		}
	}

	/**
	 * Desvincula la avería en estado ASIGNADA del mecánico y la pasa a ABIERTA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado ASIGNADA, o 
	 */
	public void desassign() {
		if(this.getStatus().equals(AveriaStatus.ASIGNADA)) {
			Association.Asignar.unlink(mecanico, this);
			this.status = AveriaStatus.ABIERTA;
		}
		else {
			throw new IllegalStateException(
					"La avería no está en estado asignada");
		}
		
	}
	
	Set<Intervencion> _getIntervenciones(){
		return intervencion;
	}
	
	 public Set<Intervencion> getIntervenciones(){
		 return new HashSet<>(intervencion);
	 }
	
	 public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	public Factura getFactura() {
		return factura;
	}


	void _setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
	/**
	 * Método que calcula el importe de la avería.
	 * @return el importe de la avería, de tipo double
	 */
	public double calcularImporte() {
		double imp = 0;
		for(Intervencion intervenciones:  getIntervenciones()) {
			imp += intervenciones.getImporte();
		}
		this.setImporte(imp);
		return imp;
	}


	public void setImporte(double calcularImporte) {
		this.importe = calcularImporte;
	}

}
