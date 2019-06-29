package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.ContractType;

public interface ContractTypeRepository 
	extends Repository<ContractType>{

	/**
	 * @param id
	 * @return el tipo de contrato identificado por id o null si no hay 
	 */
	ContractType findById(Long id);
	
	
	/**
	 * @param id
	 * @return el tipo de contrato identificado por id o null si no hay 
	 */
	List<ContractType> findAll(Long id);
	
	/**
	 * @param name 
	 * @return tipo de contrato con el mismo nombre
	 */
	ContractType findByName(String name);
}
