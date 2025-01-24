package de.caritas.cob.videoservice.api.authorization;

import static de.caritas.cob.videoservice.api.authorization.Authority.CONSULTANT;
import static de.caritas.cob.videoservice.api.authorization.Authority.USER;
import static de.caritas.cob.videoservice.api.testhelper.TestConstants.ROLE_CONSULTANT;
import static de.caritas.cob.videoservice.api.testhelper.TestConstants.ROLE_UNKNOWN;
import static de.caritas.cob.videoservice.api.testhelper.TestConstants.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorityTest {

  @Test
  void fromRoleName_Should_ReturnCorrectAuthority_ForKeycloakRoleConsultant() {

    Authority result = Authority.fromRoleName(ROLE_CONSULTANT);

    assertNotNull(result);
    assertEquals(CONSULTANT, result);
  }

  @Test
  void fromRoleName_Should_ReturnCorrectAuthority_ForKeycloakRoleUser() {

    Authority result = Authority.fromRoleName(ROLE_USER);

    assertNotNull(result);
    assertEquals(USER, result);
  }

  @Test
  void fromRoleName_Should_ReturnNull_ForUnknownKeycloakRole() {

    Authority result = Authority.fromRoleName(ROLE_UNKNOWN);

    assertNull(result);
  }
}
