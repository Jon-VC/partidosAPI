package uol.compass.partidosAPI.repository;

import uol.compass.partidosAPI.model.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long>{
	
}
