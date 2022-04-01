package uol.compass.partidosAPI.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.partidosAPI.dto.AssociateDto;
import uol.compass.partidosAPI.dto.AssociateFormDto;
import uol.compass.partidosAPI.service.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/associados")
public class AssociateController {

	@Autowired
	private AssociateService associateService;
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<AssociateDto> save(@RequestBody @Valid AssociateFormDto body) {
		AssociateDto associate = this.associateService.save(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(associate);
	}
	
	@PostMapping(path = "/{id}/partido/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<String> registerPartido(@PathVariable("id") Long id, @PathVariable("partido") Long partido) {
		AssociateDto associate = this.associateService.registerParty(id, partido);
		return ResponseEntity.status(HttpStatus.CREATED).body("");
	}

	@GetMapping
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<Page<AssociateDto>> findAll(@PageableDefault Pageable page) {
		Page<AssociateDto> associate = this.associateService.findAll(page);
		return ResponseEntity.ok(associate);
	}
	
	@GetMapping(path = "/{id}")
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<AssociateDto> search(@PathVariable Long id) {
		AssociateDto associate = this.associateService.search(id);
		return ResponseEntity.ok(associate);
	}
	
	@PutMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<AssociateDto> update(@PathVariable Long id, @RequestBody @Valid AssociateFormDto body) {
		AssociateDto associate = this.associateService.update(id, body);
		return ResponseEntity.ok(associate);
	}
	
	@DeleteMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDeAssociados", allEntries = true)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.associateService.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
