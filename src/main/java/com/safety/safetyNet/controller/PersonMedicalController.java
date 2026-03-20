/*
 * package com.safety.safetynet.controller;
 * 
 * import org.springframework.http.HttpStatus;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.web.bind.annotation.DeleteMapping;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.PathVariable;
 * import org.springframework.web.bind.annotation.PostMapping;
 * import org.springframework.web.bind.annotation.PutMapping;
 * import org.springframework.web.bind.annotation.RequestBody;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.bind.annotation.RestController;
 * 
 * import com.safety.safetynet.model.Person;
 * import com.safety.safetynet.service.PersonMedicalProfileService;
 * 
 * @RestController
 * 
 * @RequestMapping("")
 * public class PersonMedicalController {
 * private final PersonMedicalProfileService personMedicalProfile;
 * 
 * public PersonMedicalController(PersonMedicalProfileService
 * personMedicalProfile) {
 * this.personMedicalProfile = personMedicalProfile;
 * }
 * 
 * @GetMapping("/{firstName}")
 * public ResponseEntity<Person> getPersonByFirstName(@PathVariable String
 * firstName) throws Exception {
 * try {
 * return personMedicalProfile.getPersonWithMedicalRecord(firstName)
 * .map(ResponseEntity::ok)
 * .orElse(ResponseEntity.notFound().build());
 * } catch (Exception e) {
 * e.printStackTrace();
 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
 * }
 * }
 * 
 * @PostMapping
 * public ResponseEntity<Void> addPerson(@RequestBody Person person) throws
 * Exception {
 * personMedicalProfile.addPersonWithMedicalRecord(person);
 * return ResponseEntity.status(HttpStatus.CREATED).build();
 * }
 * 
 * @PutMapping("/{firstName}")
 * public ResponseEntity<Void> updatePerson(@PathVariable String
 * firstName, @RequestBody Person updatedPerson)
 * throws Exception {
 * boolean updated =
 * personMedicalProfile.updatePersonWithMedicalRecord(firstName, updatedPerson);
 * return updated ? ResponseEntity.ok().build() :
 * ResponseEntity.notFound().build();
 * }
 * 
 * @DeleteMapping("/{firstName}")
 * public ResponseEntity<Void> deletePerson(@PathVariable String firstName)
 * throws Exception {
 * boolean deleted =
 * personMedicalProfile.deletePersonWithMedicalRecord(firstName);
 * return deleted ? ResponseEntity.ok().build() :
 * ResponseEntity.notFound().build();
 * }
 * }
 */
