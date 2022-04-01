package uol.compass.partidosAPI.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import uol.compass.partidosAPI.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/partidos")
public class PartyController {

	@Autowired
	private PartyService partyService;

	@PostMapping
	public ResponseEntity<PartyDto> save(@RequestBody @Valid PartyFormDto body) {
		PartyDto party = this.partyService.save(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(party);
	}
	
	@GetMapping
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<Page<PartyDto>> findAll(@PageableDefault Pageable page) {
		Page<PartyDto> party = this.partyService.findAll(page);
		return ResponseEntity.ok(party);
	}
	
	@GetMapping(path = "/{id}")
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<PartyDto> search(@PathVariable Long id) {
		PartyDto partido = this.partyService.search(id);
		return ResponseEntity.ok(partido);
	}

	@PutMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<PartyDto> update(@PathVariable Long id, @RequestBody @Valid PartyFormDto body) {
		PartyDto party = this.partyService.update(id, body);
		return ResponseEntity.ok(party);
	}

	@DeleteMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.partyService.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	
}
