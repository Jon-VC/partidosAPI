package uol.compass.partidosAPI.service;

import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PartyService {

	PartyDto save(PartyFormDto body);

	Page<PartyDto> findAll(Pageable page);

	PartyDto search(Long id);

	PartyDto update(Long id, PartyFormDto body);

	void delete(Long id);
	
}
