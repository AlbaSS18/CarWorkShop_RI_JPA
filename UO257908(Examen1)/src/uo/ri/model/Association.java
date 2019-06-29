package uo.ri.model;

public class Association {

	public static class Poseer {

		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo._setCliente(cliente);
			cliente._getVehiculos().add(vehiculo);
		}
		
		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			cliente._getVehiculos().remove(vehiculo);
			vehiculo._setCliente(null);
		}
	}

	public static class Clasificar {

		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo._setTipo(tipoVehiculo);
			tipoVehiculo._getVehiculo().add(vehiculo);
			
		}
		
		public static void unlink(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			tipoVehiculo._getVehiculo().remove(vehiculo);
			vehiculo._setTipo(null);
			
		}
	}

	public static class Pagar {

		public static void link(MedioPago medioPago,Cliente cliente) {
			medioPago._setCliente(cliente);
			cliente._getMediosPagos().add(medioPago);
		}
		
		public static void unlink(Cliente cliente,MedioPago medioPago) {
			cliente._getMediosPagos().remove(medioPago);
			medioPago._setCliente(null);
		}
	}

	public static class Averiar {

		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo(vehiculo);
			vehiculo._getAverias().add(averia);
		}
		
		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove(averia);
			averia._setVehiculo(null);
		}
	}

	public static class Facturar {
		
		public static void link(Factura factura, Averia averia) {
			averia._setFactura(factura);
			factura._getAverias().add(averia);
		}
		
		public static void unlink(Factura factura, Averia averia) {
			factura._getAverias().remove(averia);
			averia._setFactura(null);
			
		}
		
		
	}

	public static class Cargar {

		public static void link(MedioPago medioPago, Cargo cargo, Factura factura) {
			cargo._setFactura(factura);
			cargo._setMedioPago(medioPago);
			
			medioPago._getCargos().add(cargo);
			factura._getCargos().add(cargo);
		}
		
		public static void unlink(Cargo cargo) {		
			cargo.getMedioPago()._getCargos().remove(cargo);
			cargo.getFactura()._getCargos().remove(cargo);
			
			cargo._setFactura(null);
			cargo._setMedioPago(null);
		}

	}
	
	public static class Asignar {

		public static void link(Mecanico mecanico, Averia averia) {
			averia._setMecanico(mecanico);
			mecanico._getAsignadas().add(averia);
			
		}
		
		public static void unlink(Mecanico mecanico, Averia averia) {
			mecanico._getAsignadas().remove(averia);
			averia._setMecanico(null);
			
		}
	}

	public static class Intervenir {

		public static void link(Averia averia, Intervencion intervencion, Mecanico mecanico) {
			intervencion._setAveria(averia);
			intervencion._setMecanico(mecanico);
			
			averia._getIntervenciones().add(intervencion);
			mecanico._getIntervenciones().add(intervencion);
		}

		public static void unlink(Intervencion intervencion) {
			Averia averia = intervencion.getAveria();
			Mecanico mecanico = intervencion.getMecanico();
			averia._getIntervenciones().remove(intervencion);
			mecanico._getIntervenciones().remove(intervencion);
			
			intervencion._setAveria(null);
			intervencion._setMecanico(null);
		}
	}

	public static class Sustituir {

		public static void link(Intervencion intervencion, Sustitucion sustitucion, Repuesto repuesto) {
			sustitucion._setIntervencion(intervencion);
			sustitucion._setRepuesto(repuesto);
			
			repuesto._getSustituciones().add(sustitucion);
			intervencion._getSustituciones().add(sustitucion);
			
		}

		public static void unlink(Sustitucion sustitucion) {
			sustitucion.getIntervencion()._getSustituciones().remove(sustitucion);
			sustitucion.getRepuesto()._getSustituciones().remove(sustitucion);
			
			sustitucion._setIntervencion(null);
			sustitucion._setRepuesto(null);
			
		}
	}
	
	public static class Categorize {

		public static void link(Contract contrato, ContractCategory category) {
			contrato._setContractCategory(category);
			category._getContract().add(contrato);
		}
		
		public static void unlink(Contract contrato, ContractCategory category) {
			category._getContract().remove(contrato);
			contrato._setContractCategory(null);
		}
		
	}
	
	public static class Typefy{

		public static void link(Contract contrato, ContractType typeContract) {
			contrato._setContractType(typeContract);
			typeContract._getContrato().add(contrato);
		}
		
		public static void unlink(Contract contrato, ContractType typeContract) {
			typeContract._getContrato().remove(contrato);
			contrato._setContractType(null);
		}
		
	}
	
	public static class Percibir{
		
		public static void link(Payroll nomina, Contract contrato) {
			nomina._setContract(contrato);
			contrato._getPayrolls().add(nomina);			
		}
		
		public static void unlink(Payroll nomina, Contract contrato) {
			contrato._getPayrolls().remove(nomina);
			nomina._setContract(null);
		}
	}
	
	public static class Vincular{
		
		public static void link(Mecanico mecanico, Contract contrato) {
			contrato._setMechanic(mecanico);
			mecanico._getContracts().add(contrato);
		}
		
		public static void unlink(Mecanico mecanico, Contract contrato) {
			mecanico._getContracts().remove(contrato);
			contrato._setMechanic(null);
		}
		
		
	}
		
}
