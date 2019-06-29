package uo.ri.business.repository;

import uo.ri.model.ContractCategory;

public interface CategoryRepository extends Repository<ContractCategory>{

	/**
	 * @param id
	 * @return el tipo de categoría identificado por id
	 */
	ContractCategory findById(Long id);

	/**
	 * @param name
	 * @return la categoría de contrato identificado por name
	 */
	ContractCategory findByName(String name);
}
