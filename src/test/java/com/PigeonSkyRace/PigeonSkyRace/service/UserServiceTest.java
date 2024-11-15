package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.dto.UserRegistrationDto;
import com.PigeonSkyRace.PigeonSkyRace.exception.entitesCustomExceptions.NoUserWasFoundException;
import com.PigeonSkyRace.PigeonSkyRace.helper.Validator;
import com.PigeonSkyRace.PigeonSkyRace.model.Breeder;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;
import com.PigeonSkyRace.PigeonSkyRace.repository.BreederRepository;
import com.PigeonSkyRace.PigeonSkyRace.repository.PigeonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Validator validator;

    @Mock
    private BreederRepository breederRepository;

    @Mock
    private PigeonsRepository pigeonsRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    }

    @Test
    void testRegisterBreederWithPigeons_SuccessfulRegistration() {
        List<Pigeon> pigeons = List.of(new Pigeon("Ring123", "M", 1, "Blue"));
        UserRegistrationDto dto = new UserRegistrationDto("John Doe", "password", "john@example.com", "MyDoveCote", "GPS123", pigeons);

        when(breederRepository.save(any(Breeder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pigeonsRepository.saveAll(anyList())).thenReturn(pigeons);

        Breeder breeder = userService.registerBreederWithPigeons(dto);

        assertNotNull(breeder);
        assertEquals("John Doe", breeder.getName());
        assertEquals("encodedPassword", breeder.getPassword());
        assertEquals("GPS123", breeder.getGpsCoordinates());
        verify(pigeonsRepository, times(1)).saveAll(pigeons);
        verify(breederRepository, times(1)).save(any(Breeder.class));
    }

    @Test
    void testEmailExists_ReturnsTrue() {
        String email = "existing@example.com";
        when(breederRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = userService.emailExists(email);

        assertTrue(exists);
        verify(breederRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testEmailExists_ReturnsFalse() {
        String email = "nonexistent@example.com";
        when(breederRepository.existsByEmail(email)).thenReturn(false);

        boolean exists = userService.emailExists(email);

        assertFalse(exists);
        verify(breederRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testLogin_Successful() {
        Breeder breeder = new Breeder("John Doe","password", "test@example.com", "GPS123");

        when(validator.validateEmail(breeder.getEmail())).thenReturn(true);
        when(validator.validatePassword(breeder.getPassword())).thenReturn(true);
        when(breederRepository.existsByEmail(breeder.getEmail())).thenReturn(true);
        when(breederRepository.findByEmail(breeder.getEmail())).thenReturn(breeder);
        when(passwordEncoder.matches(breeder.getPassword(), breeder.getPassword())).thenReturn(true);

        Breeder loggedInBreeder = userService.login(breeder.getEmail(), breeder.getPassword());

        assertNotNull(loggedInBreeder);
        assertEquals(breeder.getEmail(), loggedInBreeder.getEmail());
    }

    @Test
    void testLogin_IncorrectPassword() {
        Breeder breeder = new Breeder("John Doe", "password","test@example.com", "GPS123");

        when(validator.validateEmail(breeder.getEmail())).thenReturn(true);
        when(validator.validatePassword(breeder.getPassword())).thenReturn(true);
        when(breederRepository.existsByEmail(breeder.getEmail())).thenReturn(true);
        when(breederRepository.findByEmail(breeder.getEmail())).thenReturn(breeder);
        when(passwordEncoder.matches(breeder.getPassword(), breeder.getPassword())).thenReturn(false);


        assertThrows(NoUserWasFoundException.class, () -> userService.login(breeder.getEmail(), breeder.getPassword()));
    }

    @Test
    void testGpsCoordinatesExists_ReturnsTrue() {
        String gpsCoordinates = "GPS123";
        when(breederRepository.existsByGpsCoordinates(gpsCoordinates)).thenReturn(true);

        boolean exists = userService.gpsCoordinatesEsists(gpsCoordinates);

        assertTrue(exists);
        verify(breederRepository, times(1)).existsByGpsCoordinates(gpsCoordinates);
    }
}
