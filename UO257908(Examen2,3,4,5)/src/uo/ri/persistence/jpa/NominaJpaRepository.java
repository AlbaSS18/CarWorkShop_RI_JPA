package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.NominaRepository;
import uo.ri.model.Payroll;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class NominaJpaRepository 
		extends BaseRepository<Payroll> 
		implements NominaRepository {

	@Override
	public List<Payroll> findPayrollByMechanicId(Long mecanicoId) {
		return Jpa.getManager()
				.createNamedQuery("Payroll.findPayrollByMechanicId",
						Payroll.class)
				.setParameter(1, mecanicoId)
				.getResultList();
	}

	@Override
	public List<Payroll> findLastGeneratedPayrolls() {
		return Jpa.getManager()
				.createNamedQuery("Payroll.findLastGeneratedPayrolls",
						Payroll.class)
				.getResultList();
	}

	@Override
	public Payroll findLastPayrollByMechanicId(Long idMecanico) {
		return Jpa.getManager()
				.createNamedQuery("Payroll.findLastPayrollByMechanicId",
						Payroll.class)
				.setParameter(1, idMecanico)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Payroll> findPayrollProductivityNull() {
		return Jpa.getManager()
				.createNamedQuery("Payroll.findPayrollProductivityNull",
						Payroll.class)
				.getResultList();
	}

}
