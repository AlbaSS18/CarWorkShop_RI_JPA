package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Payroll;

public interface NominaRepository extends Repository<Payroll> {

	/**
	 * @param mecanicoId
	 * @return la lista de nóminas del mecánico especificado
	 */
	List<Payroll> findPayrollByMechanicId(Long mecanicoId);

	
	/**
	 * @return la lista de las últimas nóminas generadas
	 */
	List<Payroll> findLastGeneratedPayrolls();

	
	/**
	 * @param idMecanico 
	 * @return la última nómina generada para un mecánico especificado
	 */
	Payroll findLastPayrollByMechanicId(Long idMecanico);


	List<Payroll> findPayrollProductivityNull();

}
