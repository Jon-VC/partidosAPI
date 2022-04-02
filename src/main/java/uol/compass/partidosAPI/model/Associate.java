package uol.compass.partidosAPI.model;

import uol.compass.partidosAPI.model.constants.PoliticalOffice;
import uol.compass.partidosAPI.model.constants.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "associados")
public class Associate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(nullable = false)
	private String name;
	@ManyToMany
	@JoinColumn(name = "id_party", referencedColumnName = "id", nullable = false)
	private List<Party> party;
	@Column(nullable = false)
	private PoliticalOffice politicalOffice;
	@Column(nullable = false)
	private Date birthDate;
	@Column(nullable = false)
	private Gender gender;
	
}
