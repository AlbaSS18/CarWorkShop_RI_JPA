package uo.ri.business.repository;

import java.util.Date;
import java.util.List;

import uo.ri.model.Contract;

public interface ContractRepository extends Repository<Contract> {
	
	/**
	 * @param id
	 * @return el contrato identificado por id o null si no hay 
	 */
	Contract findById (Long id);

	
	/**
	 * @param fechaNomina
	 * @return la lista de contratos que están activos o que han finalizado en el mes para el que se calcula la nómina
	 */
	List<Contract> findContractsActiveOrExtinguished(Date fechaNomina);

}
