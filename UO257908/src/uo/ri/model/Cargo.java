package uo.ri.model;

import uo.ri.model.types.FacturaStatus;

public class Cargo {
	
	private Long id;

	private Factura factura;
	private MedioPago medioPago;
	private double importe = 0.0;
	
	Cargo(){
		
	}
	
	public Cargo(Factura factura, MedioPago medioPago, double importe) {
		// incrementar el importe en el acumulado del medio de pago
		// guardar el importe
		// enlazar (link) factura, este cargo y medioDePago
		if(medioPago instanceof Bono) {
			Bono bono = (Bono) medioPago;
			if(bono.getDisponible()<importe) {
				throw new IllegalStateException("El bono tiene menos"
						+ " disponible que el importe del cargo");
			}
			else {
				double disponible = bono.getDisponible();
				disponible = disponible - importe;
				bono.setDisponible(disponible);
			}	
		}
		double acumulado = medioPago.acumulado;
		acumulado = acumulado + importe;
		medioPago.acumulado = acumulado;
		Association.Cargar.link(medioPago,this,factura);
	}
	
	
	public Long getId() {
		return id;
	}
	
	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago
	 * Solo se puede hacer si la factura no está abonada
	 * Decrementar el acumulado del medio de pago
	 * Desenlazar el cargo de la factura y el medio de pago 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// verificar que la factura no esta ABONADA
		// decrementar acumulado en medio de pago (medioPago.pagar( -importe ))
		// desenlazar factura, cargo y medio de pago
		if(factura.getStatus().equals(FacturaStatus.SIN_ABONAR)) {
			medioPago.pagar(-importe);
			Association.Cargar.unlink(this);
		}
		else {
			throw new IllegalStateException("La factura está abonada");
		}
	}
	
	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}	

	public MedioPago getMedioPago() {
		return medioPago;
	}

	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public double getImporte() {
		return importe;
	}

	void _setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Cargo [importe=" + importe + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * 
				result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * 
				result + ((medioPago == null) ? 0 : medioPago.hashCode());
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals(other.medioPago))
			return false;
		return true;
	}
	
	
	
	
	
	
	

	
}
