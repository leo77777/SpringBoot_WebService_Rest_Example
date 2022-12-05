package fr.leo.youssfiscolariteservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.LifecycleState;
import org.hibernate.exception.DataException;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
class Student{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=2, max=7)
	private String name;
	private String email;
	private Date dateNaissance;

	@ManyToOne
	// @JsonIgnore
	private Laboratory laboratory;
}

@Entity 
@Data @AllArgsConstructor @NoArgsConstructor @ToString
class Laboratory{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String contact;

	@OneToMany(mappedBy = "laboratory")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Student> students;
}

@RestController
@RequestMapping("/api")
class StudentRestController{
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@GetMapping("/students")
	public List<Student> students(){
		return studentRepository.findAll();
	}
	@GetMapping("/laboratories")
	public List<Laboratory> laboratories(){
		return laboratoryRepository	.findAll();
	}
	@GetMapping("/students/{id}")
	public Student getOne( @PathVariable(name = "id") Long id ){
		return studentRepository.findById(id).get();
	}
	@PostMapping("/students")
	public Student save( @RequestBody  Student student){
		if (student.getLaboratory().getId() == null){
			Laboratory laboratory = laboratoryRepository.save(student.getLaboratory());
			student.setLaboratory(laboratory);
		}
		return studentRepository.save(student);
	}
	@PutMapping("/students/{id}")
	public Student update( @PathVariable(name = "id") Long id, @RequestBody  Student student){
		Student std = studentRepository.findById(id).get();
		return studentRepository.save(std);
	}
	@PatchMapping("/students/{id}")
	public Student updatePlus( @PathVariable(name = "id") Long id, @RequestBody  Student student){
		Student std = studentRepository.findById(id).get();
		std.setEmail(student.getEmail());
		std.setName(student.getName());
		std.setDateNaissance(student.getDateNaissance());
		System.out.println(student);
		return studentRepository.save(std);
	}
	@DeleteMapping("/students/{id}")
	public void delete( @PathVariable(name = "id") Long id){
		studentRepository.deleteById(id);
	}
}
	

@RepositoryRestResource
interface  LaboratoryRepository extends JpaRepository<Laboratory, Long>{
}

@RepositoryRestResource
interface StudentRepository extends JpaRepository<Student, Long>{
		@RestResource(path="/byName")
		public List<Student> findByNameContains( @Param(value ="mc") String mc);
}

@Projection(name="p1", types = Student.class)
interface StudentProjection{
	public String getEmail();
	public String getName();
	public Laboratory getLaboratory();
}

@SpringBootApplication
public class YoussfiScolariteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoussfiScolariteServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(StudentRepository studentRepository,
                            RepositoryRestConfiguration restConfiguration,
							LaboratoryRepository laboratoryRepository){
		return args -> {
		    restConfiguration.exposeIdsFor(Student.class);
		    Laboratory l1 =  laboratoryRepository.save(new Laboratory(null,
							"Informatique", "contact@gmail.com", null));
			Laboratory l2 =  laboratoryRepository.save(new Laboratory(null,
							"Biology", "contact2@gmail.com", null));
			studentRepository.save(new Student(null, "Rere", "rere@free.fr" , new Date(), l1));
			studentRepository.save(new Student(null, "Lala", "rere@free.fr" , new Date(), l1));
			studentRepository.save(new Student(null, "Vyvy", "rere@free.fr" , new Date(),l2));
			studentRepository.save(new Student(null, "Toto", "rere@free.fr" , new Date(),l2));
			studentRepository.findAll().forEach(s-> System.out.println(s.getName()));
		};
	}
}
