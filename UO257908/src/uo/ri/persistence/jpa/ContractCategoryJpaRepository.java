package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.CategoryRepository;
import uo.ri.model.ContractCategory;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractCategoryJpaRepository 
	extends BaseRepository<ContractCategory> 
	implements CategoryRepository {

	@Override
	public ContractCategory findById(Long id) {
		return Jpa.getManager()
				.createNamedQuery("ContractCategory.findById", 
						ContractCategory.class)
				.setParameter(1, id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<ContractCategory> findAll() {
		return Jpa.getManager()
				.createNamedQuery("ContractCategory.findAll", 
						ContractCategory.class)
				.getResultList();
	}

	@Override
	public ContractCategory findByName(String name) {
		return Jpa.getManager()
				.createNamedQuery("ContractCategory.findByName", 
						ContractCategory.class)
				.setParameter(1, name)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

}
