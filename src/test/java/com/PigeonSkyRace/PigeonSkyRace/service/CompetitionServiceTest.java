package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.CompetitionDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NegativeDurationException;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoCompetitionWasFound;
import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import com.PigeonSkyRace.PigeonSkyRace.model.PigeonResults;


import com.PigeonSkyRace.PigeonSkyRace.repository.CompetitionRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import com.PigeonSkyRace.PigeonSkyRace.helper.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CompetitionServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;


    @Mock
    private PigeonsRepository pigeonRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private CompetitionService competitionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testCreateCompetition() {
    //     List<Breeder> breeders = Arrays.asList(
    //             new Breeder("Breeder1", "password", "email1@example.com", "GPS1"),
    //             new Breeder("Breeder2", "password", "email2@example.com", "GPS2"));

    //     CompetitionDto competitionDto = new CompetitionDto("Race1", "GPS1", "open",
    //             Duration.ofHours(2), LocalDate.of(2023, 10, 10), LocalTime.of(10, 0),
    //             100.0, Arrays.asList("Breeder1", "Breeder2"));

    //     when(validator.validateDepartureDate(competitionDto.departureDate())).thenReturn(true);
    //     when(breederRepository.findAll()).thenReturn(breeders);
    //     when(competitionRepository.save(any(Competition.class))).thenAnswer(invocation -> invocation.getArgument(0));

    //     Competition competition = competitionService.createCompetition(competitionDto);
    //     assertNotNull(competition);
    //     assertEquals("Race1", competition.getRaceName());
    //     assertEquals("GPS1", competition.getReleasePointGps());
    //     assertEquals(Duration.ofHours(2), competition.getDuration());
    //     assertEquals("open", competition.getStatus());
    //     verify(competitionRepository, times(1)).save(any(Competition.class));
    // }

    // @Test
    // public void testCloseCompetition() {
    //     // Arrange
    //     PigeonResults pigeonResults = new PigeonResults();
    //     pigeonResults.setCompetitionID("Comp1");
    //     pigeonResults.setPigeon("Pigeon1");
    //     pigeonResults.setArrivalTime(LocalTime.of(12, 0));
    //     List<PigeonResults> pigeonResultsList = Arrays.asList(pigeonResults);

    //     Competition competition = new Competition();
    //     competition.setId("Comp1");
    //     competition.setDuration(Duration.ofHours(2));
    //     competition.setDepartureTime(LocalTime.of(10, 0));
    //     competition.setReleasePointGps("40.7128,-74.0060");

    //     Pigeon pigeon = new Pigeon("Pigeon1", "M", 1, "Blue");
    //     pigeon.setBreeder("Breeder1"); 

    //     Breeder breeder = new Breeder("John Doe", "password", "test@gmail.com", "34.0522,-118.2437");

    //     // Mock repositories with corrected data
    //     when(competitionRepository.findById("Comp1")).thenReturn(Optional.of(competition));
    //     when(pigeonRepository.findById("Pigeon1")).thenReturn(Optional.of(pigeon));
    //     when(breederRepository.findByPigeonId("Pigeon1")).thenReturn(Optional.of(breeder));

    //     // Act
    //     List<Result> results = competitionService.closeCompetition(pigeonResultsList);

    //     // Assert
    //     assertNotNull(results);
    // }

    // @Test
    // public void testCloseCompetition_NoCompetitionFound() {

    //     PigeonResults pigeonResults = new PigeonResults();
    //     pigeonResults.setCompetitionID("Comp1");
    //     pigeonResults.setPigeon("Pigeon1");
    //     pigeonResults.setArrivalTime(LocalTime.of(12, 0));
    //     List<PigeonResults> pigeonResultsList = Arrays.asList(pigeonResults);

    //     when(competitionRepository.findById("Comp1")).thenReturn(Optional.empty());
    //     assertThrows(NoCompetitionWasFound.class, () -> competitionService.closeCompetition(pigeonResultsList));
    //     verify(competitionRepository, times(1)).findById("Comp1");
    // }

    // @Test
    // public void testCreateCompetition_InvalidDate() {
    //     CompetitionDto competitionDto = new CompetitionDto("Race1", "GPS1", "open",
    //             Duration.ofHours(2), LocalDate.of(2023, 10, 10), LocalTime.of(10, 0),
    //             100.0, Arrays.asList("Breeder1", "Breeder2"));

    //     when(validator.validateDepartureDate(competitionDto.departureDate())).thenReturn(false);
    //     assertThrows(IllegalArgumentException.class, () -> competitionService.createCompetition(competitionDto));
    //     verify(competitionRepository, times(0)).save(any(Competition.class));
    // }

    // @Test
    // public void testCalcFlightTime_NegativeDuration() {
    //     Competition competition = new Competition();
    //     competition.setDepartureTime(LocalTime.of(10, 0));

    //     PigeonResults pigeonResults = new PigeonResults();
    //     pigeonResults.setCompetitionID("Comp1");
    //     pigeonResults.setPigeon("Pigeon1");
    //     pigeonResults.setArrivalTime(LocalTime.of(9, 0));
    //     assertThrows(NegativeDurationException.class,
    //             () -> competitionService.calcFlightTime(pigeonResults, competition));
    // }

    // @Test
    // public void testCalcDistance() {
    //     // Arrange
    //     Pigeon pigeon = new Pigeon("Pigeon1", "M", 1, "Blue");
    //     Competition competition = new Competition();
    //     competition.setReleasePointGps("40.7128,-74.0060");

    //     Breeder breeder = new Breeder("Breeder1", "password", "email1@example.com", "34.0522,-118.2437"); // Valid GPS
    //                                                                                                       // coordinates

    //     when(breederRepository.findByPigeonId(pigeon.getBreeder())).thenReturn(Optional.of(breeder));

    //     // Act
    //     double distance = competitionService.calcDistance(pigeon, competition);

    //     // Assert
    //     assertTrue(distance > 0);
    // }

    // @Test
    // public void testCalcSpeed() {
    //     double distance = 100.0;
    //     Duration flightDuration = Duration.ofHours(2);
    //     double adjustmentCoeff = 1.0;

    //     double speed = competitionService.calcSpeed(distance, flightDuration, adjustmentCoeff);
    //     assertEquals(50.0, speed);
    // }

    // @Test
    // public void testCalcPoints() {
    //     double averageSpeed = 50.0;
    //     double speed = 60.0;

    //     double points = competitionService.calcPoints(averageSpeed, speed);
    //     assertEquals(120.0, points);
    // }
}