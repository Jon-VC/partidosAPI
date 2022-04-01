package uol.compass.partidosAPI.repository;

import uol.compass.partidosAPI.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long>{

}
