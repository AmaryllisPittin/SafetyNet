/*
 * package com.example.demo.utilTests;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * import static org.junit.jupiter.api.Assertions.assertTrue;
 * 
 * import java.io.File;
 * import java.io.IOException;
 * import java.util.List;
 * 
 * import org.junit.jupiter.api.Test;
 * 
 * import com.safety.safetynet.model.FireStation;
 * import com.safety.safetynet.model.MedicalRecord;
 * import com.safety.safetynet.model.Person;
 * import com.safety.safetynet.utils.JsonReader;
 * 
 * public class JsonReaderTests {
 * 
 * @Test
 * void InitDataFileCreatesFile() throws IOException {
 * File file = new File("config/data.json");
 * if (file.exists())
 * file.delete();
 * JsonReader.getDataFile();
 * assertTrue(file.exists());
 * }
 * 
 * @Test
 * void WritePersonsPersistData() throws IOException {
 * List<Person> persons = List.of(new Person("Kendrik", "Stelzer",
 * "947 E. Rose Dr", "Culver", "97451",
 * "841-874-7784", "bstel@email.com"));
 * JsonReader.writePersons(persons);
 * 
 * List<Person> readBack = JsonReader.readPersons();
 * assertEquals(persons.size(), readBack.size());
 * assertEquals(persons.get(0).getFirstName(), readBack.get(0).getFirstName());
 * }
 * 
 * @Test
 * void WriteMedicalRecordsPersistData() throws IOException {
 * List<MedicalRecord> medicalRecords = List.of(new MedicalRecord("John",
 * "Boyd", "03/06/1984",
 * List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
 * JsonReader.writeMedicalRecord(medicalRecords);
 * 
 * List<MedicalRecord> readBack = JsonReader.readMedicalRecord();
 * assertEquals(medicalRecords.size(), readBack.size());
 * assertEquals(medicalRecords.get(0).getFirstName(),
 * readBack.get(0).getFirstName());
 * }
 * 
 * @Test
 * void WriteFireStationsPersistData() throws Exception {
 * List<FireStation> fireStations = List.of(new FireStation("951 LoneTree Rd",
 * "2"));
 * JsonReader.writeFireStation(fireStations);
 * 
 * List<FireStation> readBack = JsonReader.readFireStations();
 * assertEquals(fireStations.size(), readBack.size());
 * assertEquals(fireStations.get(0).getAddress(), readBack.get(0).getAddress());
 * }
 * 
 * }
 */
