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
        // Arrange
        List<Pigeon> pigeons = List.of(new Pigeon("Ring123", "M", 1, "Blue"));
        UserRegistrationDto dto = new UserRegistrationDto("John Doe", "password", "john@example.com", "MyDoveCote", "GPS123", pigeons);

        when(breederRepository.save(any(Breeder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Breeder breeder = userService.registerBreederWithPigeons(dto);

        // Assert
        assertNotNull(breeder);
        assertEquals("John Doe", breeder.getName());
        assertEquals("encodedPassword", breeder.getPassword());
        verify(pigeonsRepository, times(1)).saveAll(pigeons);
        verify(breederRepository, times(1)).save(any(Breeder.class));
    }

    @Test
    void testEmailExists_ReturnsTrue() {
        // Arrange
        String email = "existing@example.com";
        when(breederRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean exists = userService.emailExists(email);

        // Assert
        assertTrue(exists);
        verify(breederRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testEmailExists_ReturnsFalse() {
        // Arrange
        String email = "nonexistent@example.com";
        when(breederRepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean exists = userService.emailExists(email);

        // Assert
        assertFalse(exists);
        verify(breederRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testLogin_Successful() {
        // Arrange
        String email = "test@example.com";
        String rawPassword = "password";
        Breeder breeder = new Breeder();
        breeder.setEmail(email);
        breeder.setPassword("encodedPassword");

        when(validator.validateEmail(email)).thenReturn(true);
        when(validator.validatePassword(rawPassword)).thenReturn(true);
        when(breederRepository.existsByEmail(email)).thenReturn(true);
        when(breederRepository.findByEmail(email)).thenReturn(breeder);
        when(passwordEncoder.matches(rawPassword, breeder.getPassword())).thenReturn(true);

        // Act
        Breeder loggedInBreeder = userService.login(email, rawPassword);

        // Assert
        assertNotNull(loggedInBreeder);
        assertEquals(email, loggedInBreeder.getEmail());
    }

    @Test
    void testLogin_IncorrectPassword() {
        // Arrange
        String email = "test@example.com";
        String rawPassword = "wrongPassword";
        Breeder breeder = new Breeder();
        breeder.setEmail(email);
        breeder.setPassword("encodedPassword");

        when(validator.validateEmail(email)).thenReturn(true);
        when(validator.validatePassword(rawPassword)).thenReturn(true);
        when(breederRepository.existsByEmail(email)).thenReturn(true);
        when(breederRepository.findByEmail(email)).thenReturn(breeder);
        when(passwordEncoder.matches(rawPassword, breeder.getPassword())).thenReturn(false);

        // Act and Assert
        assertThrows(NoUserWasFoundException.class, () -> userService.login(email, rawPassword));
    }

    @Test
    void testLogin_EmailNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password";

        when(validator.validateEmail(email)).thenReturn(true);
        when(validator.validatePassword(password)).thenReturn(true);
        when(breederRepository.existsByEmail(email)).thenReturn(false);

        // Act and Assert
        assertThrows(NoUserWasFoundException.class, () -> userService.login(email, password));
    }

    @Test
    void testGpsCoordinatesExists_ReturnsTrue() {
        // Arrange
        String gpsCoordinates = "GPS123";
        when(breederRepository.existsByGpsCoordinates(gpsCoordinates)).thenReturn(true);

        // Act
        boolean exists = userService.gpsCoordinatesEsists(gpsCoordinates);

        // Assert
        assertTrue(exists);
        verify(breederRepository, times(1)).existsByGpsCoordinates(gpsCoordinates);
    }

    @Test
    void testGpsCoordinatesExists_ReturnsFalse() {
        // Arrange
        String gpsCoordinates = "GPS999";
        when(breederRepository.existsByGpsCoordinates(gpsCoordinates)).thenReturn(false);

        // Act
        boolean exists = userService.gpsCoordinatesEsists(gpsCoordinates);

        // Assert
        assertFalse(exists);
        verify(breederRepository, times(1)).existsByGpsCoordinates(gpsCoordinates);
    }

    @Test
    void testRegisterBreederWithPigeons_NullPassword() {
        // Arrange
        List<Pigeon> pigeons = List.of(new Pigeon("Ring123", "M", 1, "Blue"));
        UserRegistrationDto dto = new UserRegistrationDto("John Doe", null, "john@example.com", "MyDoveCote", "GPS123", pigeons);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerBreederWithPigeons(dto));
        verify(breederRepository, never()).save(any(Breeder.class));
    }
}