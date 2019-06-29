package uo.ri.otrosTest.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;


public class VincularTest {
	private Mecanico mecanico;
	private Contract contrato;

	@Before
	public void setUp() {
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		contrato = new Contract(mecanico, Dates.fromDdMmYyyy(18, 7, 2019));
	}
	
	@Test
	public void testAsignarLinked() {
		assertTrue(mecanico.getContracts().contains(contrato));
		assertTrue(contrato.getMechanic() == mecanico);
	}

	@Test
	public void testAsignarUnlink() {
		Association.Vincular.unlink(mecanico, contrato);
		assertTrue(!mecanico.getContracts().contains(contrato));
		assertTrue(mecanico.getContracts().size() == 0);
		assertTrue(contrato.getMechanic() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Contract> contratos = mecanico.getContracts();
		contratos.remove(this.contrato);
		

		assertTrue( contratos.size() == 0 );
		assertTrue( "Se tiene que retornar copia de la colección", 
				mecanico.getContracts().size() == 1
		);
	}

}
