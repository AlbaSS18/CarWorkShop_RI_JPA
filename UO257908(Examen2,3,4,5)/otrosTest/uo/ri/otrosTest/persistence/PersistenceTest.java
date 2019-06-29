package uo.ri.otrosTest.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cargo;
import uo.ri.model.Cliente;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Factura;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.Payroll;
import uo.ri.model.Repuesto;
import uo.ri.model.Sustitucion;
import uo.ri.model.TarjetaCredito;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;
import uo.ri.model.types.Address;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.ContractStatus;


public class PersistenceTest {
	
	private EntityManagerFactory factory;
	private Cliente cliente;
	private Sustitucion sustitucion;
	private Cargo cargo;
	private ContractType tipoContrato;
	private ContractCategory categoriaContrato;
	private Contract contrato;
	
	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("carworkshop");
		List<Object> graph = createGraph();
		persistGraph(graph);
	}

	@After
	public void tearDown() {
		removeGraph();
		factory.close();
	}
	
	@Test
	public void testAveria() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Cliente cl = mapper.merge( cliente );
		Set<Vehiculo> vehiculoCliente = cl.getVehiculos();
		Vehiculo v = vehiculoCliente.iterator().next();
		Set<Averia> averiasDelVehiculo = v.getAverias();
		Averia averia = averiasDelVehiculo.iterator().next();
		
		assertNotNull( averia.getId() );
		assertEquals( averia.getDescripcion(), "falla la junta la trocla" );
		assertEquals( averia.getImporte(), 250.0, 0);
		assertEquals( averia.getStatus(), AveriaStatus.FACTURADA);
		assertEquals( averia.getVehiculo(), v);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testMediosPago() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Cliente cl = mapper.merge( cliente );
		Set<MedioPago> mediosPago = cl.getMediosPago();
		assertEquals( mediosPago.size(), 3); //Bono, tarjeta de crédito y metálico
		
		MedioPago[] mediosPagoArray = mediosPago.toArray(new MedioPago[mediosPago.size()]);
		assertEquals( mediosPagoArray.length, 3); 
		
		Bono bono = (Bono)mediosPagoArray[0]; //Bono
		assertNotNull( bono.getId() );
		assertEquals( bono.getCodigo(), "B-100" );
		assertEquals( bono.getDisponible(), 100.0, 0.0 );
		assertEquals( bono.getDescripcion(), "Voucher just for testing" );
		
		/*
		 * Sustitución importe --> precioRepuesto*cantidad --> 100*2 = 200
		 * Intervención importe --> 
		 * 			importeManoDeObra(precioHoraDelTipoVehiculo/60 * minutos) + importeRepuesto (suma del importe de todas las sustituciones)
		 * 							importeRepuesto = 200
		 * 							importeManoDeObra = 50/60*60=50
		 * 			Importe intervención = 250
		 * Avería importe --> suma del importe de todas las intervenciones --> 250
		 * 
		 * Factura importe --> (sumaAveria + (sumaAveria * this.getIva()/100))*100)/100
		 * 						((250+(250*21/100))*100)/100 = 302.5
		 * 							
		 */
		TarjetaCredito tarjeta = (TarjetaCredito)mediosPagoArray[1]; //Tarjeta de crédito
		assertNotNull( tarjeta.getId() );
		assertEquals( tarjeta.getNumero(), "1234567" );
		assertEquals( tarjeta.getTipo(), "visa" );
		assertEquals( tarjeta.getValidez(), Dates.inYearsTime( 1 ) );
		assertEquals(tarjeta.getAcumulado(), 302.5, 0.0);
		
		Metalico metalico = (Metalico)mediosPagoArray[2]; //Metálico
		assertNotNull( metalico.getId() );
		assertEquals(metalico.getCliente(), cl);

		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testTipoVehiculo() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Cliente cl = mapper.merge( cliente );
		Set<Vehiculo> vehiculos = cl.getVehiculos();
		Vehiculo v = vehiculos.iterator().next();
		TipoVehiculo tipoVehiculo = v.getTipo();
		
		assertNotNull( tipoVehiculo.getId() );
		assertEquals(tipoVehiculo.getNombre(), "coche");
		assertEquals(tipoVehiculo.getPrecioHora(), 50.0, 0.0);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testMecanico() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Cliente cl = mapper.merge( cliente );
		Set<Vehiculo> vehiculoCliente = cl.getVehiculos();
		Vehiculo v = vehiculoCliente.iterator().next();
		Set<Averia> averiasDelVehiculo = v.getAverias();
		Averia averia = averiasDelVehiculo.iterator().next();
		Mecanico mecanico = averia.getMecanico();
		
		assertNotNull( mecanico.getId() );
		assertEquals(mecanico.getDni(), "dni-mecanico");
		assertEquals(mecanico.getNombre(), "nombre");
		assertEquals(mecanico.getApellidos(), "apellidos");
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testContrato() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Sustitucion s  = mapper.merge( sustitucion );
		Set<Contract> contratos = s.getIntervencion().getMecanico().getContracts();
		
		Contract[] contratosArray = contratos.toArray(new Contract[contratos.size()]);
		
		//Primer contrato
		assertNotNull( contratosArray[1].getId() );
		assertEquals( contratosArray[1].getMechanic(), s.getIntervencion().getMecanico());
		assertEquals( contratosArray[1].getStartDate(), Dates.fromDdMmYyyy(1, 6, 2012)); // Primer día del mes
		assertEquals( contratosArray[1].getEndDate(), Dates.fromDdMmYyyy(31, 7, 2017)); // Último día del mes
		assertEquals( contratosArray[1].getStatus(), ContractStatus.FINISHED); //Se marcó como terminado
		assertEquals( contratosArray[1].getCompensation(), 37.573385518591 , 0.0);
		
		
		// Segundo contrato
		assertNotNull( contratosArray[0].getId() );
		assertEquals( contratosArray[0].getMechanic(), s.getIntervencion().getMecanico());
		assertEquals( contratosArray[0].getStartDate(), Dates.fromDdMmYyyy(1, 5, 2019)); // Primer día del mes
		assertEquals( contratosArray[0].getEndDate(), Dates.fromDdMmYyyy(31, 12, 2020)); // Último día del mes
		assertEquals( contratosArray[0].getBaseSalaryPerYear(), 2000, 0.0);
		assertEquals( contratosArray[0].getContractCategory(), categoriaContrato);
		assertEquals( contratosArray[0].getContractType(), tipoContrato);
		assertEquals( contratosArray[0].getStatus(), ContractStatus.ACTIVE);
		assertEquals( contratosArray[0].getCompensation(), 0 , 0.0);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testTipoContrato() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Sustitucion s  = mapper.merge( sustitucion );
		Set<Contract> contratos = s.getIntervencion().getMecanico().getContracts();
		ContractType type = contratos.iterator().next().getContractType();
		
		assertNotNull( type.getId() );
		assertEquals( type.getName(), "TipoContratoTest");
		assertEquals( type.getCompensationDays(), 4);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testCategoriaContrato() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Sustitucion s  = mapper.merge( sustitucion );
		Set<Contract> contratos = s.getIntervencion().getMecanico().getContracts();
		ContractCategory type = contratos.iterator().next().getContractCategory();
		
		assertNotNull( type.getId() );
		assertEquals( type.getName(), "CategoriaContratoTest");
		assertEquals( type.getTrieniumSalary(), 1100, 0.0);
		assertEquals( type.getProductivityPlus(), 200, 0.0);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testNomina() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Sustitucion s  = mapper.merge( sustitucion );
		Set<Contract> contratos = s.getIntervencion().getMecanico().getContracts();
		Set<Payroll> nominas = contratos.iterator().next().getPayrolls();
		Payroll nomina = nominas.iterator().next();
		
		assertNotNull( nomina.getId() );
		assertEquals( nomina.getDate(), Dates.fromDdMmYyyy(30, 6 , 2019));
		assertEquals(nomina.getBaseSalary(), 142.85714285714286, 0.0);
		assertEquals(nomina.getExtraSalary(), 142.85714285714286, 0.0);
		assertEquals(nomina.getProductivity(), 1400, 0.0);
		assertEquals(nomina.getTriennium(), 0.0, 0.0);
		assertEquals(nomina.getIrpf(), 0.0, 0.0);
		assertEquals(nomina.getSocialSecurity(), 8.333333333333334, 0.0);
		
		trx.commit();
		mapper.close();	
	}
	
	@Test
	public void testVincular() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Contract c = mapper.merge( contrato );
		Mecanico m = c.getMechanic();
		
		assertTrue( m.getContracts().contains(contrato) ); 
		
		trx.commit();
		mapper.close();		
	}
	
	@Test
	public void testCategorizar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Contract c = mapper.merge( contrato );
		ContractCategory category = c.getContractCategory();
		
		assertTrue( category.getContract().contains(c) ); 
		
		trx.commit();
		mapper.close();		
	}
	
	@Test
	public void testTipificar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Contract c = mapper.merge( contrato );
		ContractType tipo = c.getContractType();
		
		assertTrue( tipo.getContrato().contains(c) ); 
		
		trx.commit();
		mapper.close();		
	}
	
	@Test
	public void testPercibir() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		Contract c = mapper.merge( contrato );
		
		for(Payroll p: c.getPayrolls()) {
			assertSame( p.getContract(), c );
		}
		
		trx.commit();
		mapper.close();		
	}
	
	protected List<Object> createGraph() {

		cliente = new Cliente("dni", "nombre", "apellidos");
		Address address = new Address("street", "city", "zipcode");
		cliente.setAddress(address);
		Vehiculo vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);
		
		TipoVehiculo tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);
		
		Averia averia = new Averia(vehiculo, "falla la junta la trocla");
		Mecanico mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);
	
		// Categoría del contrato del mecánico
		categoriaContrato = new ContractCategory("CategoriaContratoTest", 1100, 200);
		
		// Tipo del contrato del mecánico
		tipoContrato = new ContractType("TipoContratoTest", 4);
		
		// Añadimos un contrato
		ContractCategory categoriaContrato2 = new ContractCategory("CategoriaContratoTest2", 1100, 200);
		ContractType tipoContrato2 = new ContractType("TipoContratoTest2", 4);
		Contract contratoNuevo = new Contract(mecanico,  Dates.fromDdMmYyyy(12, 6, 2012), null , 2000, categoriaContrato2, tipoContrato2);				
		Payroll nominaContratoNuevo = new Payroll(contratoNuevo, Dates.fromDdMmYyyy(12, 7, 2012), 2);
		
		// Finalizo el contrato
		contratoNuevo.markAsFinished(Dates.fromDdMmYyyy(25, 7, 2017));
		
		// Nuevo contrato del mecánico
		contrato = new Contract(mecanico, Dates.fromDdMmYyyy(5, 5, 2019), Dates.fromDdMmYyyy(28, 12, 2020) , 2000, categoriaContrato, tipoContrato);				
		
		//Añadimos una nómina para el último contrato
		Payroll nomina = new Payroll(contrato, Dates.fromDdMmYyyy(4, 7, 2019), 7);
		
		Intervencion intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);
		averia.markAsFinished();
		
		Repuesto repuesto = new Repuesto("R1001", "junta la trocla", 100.0);
		sustitucion = new Sustitucion(repuesto, intervencion, 2);
		
		Bono bono = new Bono("B-100", 100.0);
		bono.setDescripcion( "Voucher just for testing" );
		Association.Pagar.link(bono, cliente);
		
		TarjetaCredito tarjetaCredito = new TarjetaCredito( 
					"1234567", 
					"visa", 
					Dates.inYearsTime( 1 ) 
				);
		Association.Pagar.link(tarjetaCredito, cliente);
		
		Metalico metalico = new Metalico( cliente );
		
		Factura factura = new Factura( 1L );
		factura.setFecha( Dates.today() );
		factura.addAveria(averia);

		cargo = new Cargo(factura, tarjetaCredito, factura.getImporte());
		
		List<Object> res = new LinkedList<Object>();
		
		res.add(tipoVehiculo);
		res.add(repuesto);
		res.add(mecanico);
		res.add(cliente);
		res.add(bono);
		res.add(tarjetaCredito);
		res.add(metalico);
		res.add(vehiculo);
		res.add(factura);
		res.add(averia);
		res.add(intervencion);
		res.add(sustitucion);
		res.add(cargo);
		res.add(categoriaContrato);
		res.add(tipoContrato);
		res.add(categoriaContrato2);
		res.add(tipoContrato2);
		res.add(contratoNuevo);
		res.add(contrato);
		res.add(nomina);
		res.add(nominaContratoNuevo);
		
		return res;
	}
	
	private void persistGraph(List<Object> graph) {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		for(Object o: graph) {
			mapper.persist(o);
		}

		trx.commit();
		mapper.close();
	}
	
	private void removeGraph() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		
		List<Object> merged = mergeGraph(mapper);
		
		for(Object o: merged) {
			mapper.remove(o);
		}

		trx.commit();
		mapper.close();
	}

	private List<Object> mergeGraph(EntityManager mapper) {
		List<Object> res = new LinkedList<Object>();
		
		res.add( mapper.merge(cargo) );

		Sustitucion s  = mapper.merge( sustitucion );
		res.add( s );
		res.add( s.getRepuesto() );
		res.add( s.getIntervencion() );
		for(Contract contract : s.getIntervencion().getMecanico().getContracts()) {
			for(Payroll nominas: contract.getPayrolls()) {
				res.add(nominas);
			}
			res.add(contract);
			ContractType tipo = contract.getContractType();
			res.add(tipo);
			ContractCategory categoria = contract.getContractCategory();
			res.add(categoria);
		}
		res.add( s.getIntervencion().getMecanico() );
		res.add( s.getIntervencion().getAveria() );
		res.add( s.getIntervencion().getAveria().getVehiculo() );
		res.add( s.getIntervencion().getAveria().getVehiculo().getTipo() );
		res.add( s.getIntervencion().getAveria().getFactura() );
		
		Cliente cl = mapper.merge(cliente);
		for(MedioPago mp: cl.getMediosPago()) {
			res.add( mp );
		}
		res.add( cl );
		
		return res;
	}

}
