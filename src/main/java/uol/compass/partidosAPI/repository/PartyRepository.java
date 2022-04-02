package uol.compass.partidosAPI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.partidosAPI.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uol.compass.partidosAPI.model.constants.Ideology;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long>{

    Page<Party> findByIdeology(Ideology ideology, Pageable pagination);
}
