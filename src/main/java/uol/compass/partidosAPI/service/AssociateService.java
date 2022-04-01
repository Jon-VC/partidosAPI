package uol.compass.partidosAPI.service;

import uol.compass.partidosAPI.dto.AssociateDto;
import uol.compass.partidosAPI.dto.AssociateFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociateService {

	//post
	AssociateDto save(AssociateFormDto body);
	
	//get all
	Page<AssociateDto> findAll(Pageable page);
	
	//get by id
	AssociateDto search(Long id);

	//update
	AssociateDto update(Long id, AssociateFormDto body);
	
	AssociateDto registerParty(Long id, Long partido);
	
	//delete
	void delete(Long id);
	
}
