package uo.ri.otrosTest.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;


public class PercibirTest {
	private Payroll nomina;
	private Contract contrato;
	private Mecanico mecanico;	
	
	
	@Before
	public void setUp() {
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		contrato = new Contract(mecanico, Dates.fromDdMmYyyy(18, 4, 2019));
		nomina = new Payroll(contrato, Dates.fromDdMmYyyy(18, 7, 2019));
	}
	
	@Test
	public void testAsignarLinked() {
		assertTrue(nomina.getContract() == contrato);
		assertTrue( contrato.getPayrolls().contains(nomina) );
	}

	@Test
	public void testAsignarUnlink() {
		Association.Percibir.unlink(nomina, contrato);
		
		assertTrue( ! contrato.getPayrolls().contains(nomina));
		assertTrue( contrato.getPayrolls().size() == 0);
		assertTrue( nomina.getContract() == null );
	}

	@Test
	public void testSafeReturn() {
		Set<Payroll> nominas = contrato.getPayrolls();
		nominas.remove(this.nomina);
		
		assertTrue( nominas.size() == 0 );
		assertTrue( "Se tiene que retornar copia de la colección", 
			contrato.getPayrolls().size() == 1
		);
	}

}
