package uol.compass.partidosAPI.service;

import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.partidosAPI.model.Party;


public interface PartyService {

	PartyDto save(PartyDto body);

	Page<PartyDto> findAll(Pageable page);

	PartyDto search(Long id);

	PartyDto update(Long id, PartyFormDto body);

	void delete(Long id);

    Party findById(Long id);
}
