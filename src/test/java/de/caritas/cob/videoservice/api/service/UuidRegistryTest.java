package de.caritas.cob.videoservice.api.service;

import static de.caritas.cob.videoservice.api.service.UuidRegistry.GENERATED_UUIDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UuidRegistryTest {

  private UuidRegistry uuidRegistry = new UuidRegistry();

  @BeforeEach
  void setUp() {
    uuidRegistry.cleanUpUuidRegistry();
  }

  @Test
  void generateUniqueUuid_Should_ReturnValidUuid() {
    String response = uuidRegistry.generateUniqueUuid();

    assertThat(UUID.fromString(response), instanceOf(UUID.class));
  }

  @Test
  void generateUniqueUuid_Should_AddGeneratedUuidToRegistry() {
    String response = uuidRegistry.generateUniqueUuid();

    assertThat(UUID.fromString(response), instanceOf(UUID.class));
    assertThat(GENERATED_UUIDS.size(), equalTo(1));
  }

  @Test
  void cleanUpEntireList_Should_cleanListOfUuids() {
    uuidRegistry.cleanUpUuidRegistry();
    assertEquals(0, GENERATED_UUIDS.size());
  }
}
