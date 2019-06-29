package uo.ri.otrosTest.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.ContractType;
import uo.ri.model.Mecanico;


public class TypefyTest {
	private Mecanico mecanico;
	private Contract contrato;
	private ContractType tipoContrato;
	
	@Before
	public void setUp() {
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		contrato = new Contract(mecanico, Dates.fromDdMmYyyy(18, 7, 2019));
		tipoContrato = new ContractType("Tipo de contrato test");
		
		Association.Typefy.link(contrato, tipoContrato);
	}
	
	@Test
	public void testAsignarLinked() {
		assertTrue( tipoContrato.getContrato().contains(contrato));
		assertTrue( contrato.getContractType() == tipoContrato );
	}

	@Test
	public void testAsignarUnlink() {
		Association.Typefy.unlink(contrato, tipoContrato);
		
		assertTrue(! tipoContrato.getContrato().contains(contrato));
		assertTrue(tipoContrato.getContrato().size() == 0);
		assertTrue(contrato.getContractType() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Contract> contratos = tipoContrato.getContrato();
		contratos.remove(this.contrato);

		assertTrue( contratos.size() == 0 );
		assertTrue( "Se tiene que retornar copia de la colección", 
			tipoContrato.getContrato().size() == 1
		);
	}

}
