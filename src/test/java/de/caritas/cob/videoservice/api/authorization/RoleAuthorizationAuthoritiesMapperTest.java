package de.caritas.cob.videoservice.api.authorization;

import static de.caritas.cob.videoservice.api.testhelper.TestConstants.ROLE_CONSULTANT;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import de.caritas.cob.videoservice.api.authorization.Authority.AuthorityValue;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
class RoleAuthorizationAuthoritiesMapperTest {

  private final RoleAuthorizationAuthorityMapper roleAuthorizationAuthorityMapper =
      new RoleAuthorizationAuthorityMapper();

  @Test
  void mapAuthorities_Should_returnGrantedConsultantAuthority_When_authorityIsConsultant() {
    List<GrantedAuthority> grantedAuthorities =
        Stream.of(ROLE_CONSULTANT).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities =
        this.roleAuthorizationAuthorityMapper.mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(1));
    assertThat(mappedAuthorities.iterator().next().getAuthority(), is(AuthorityValue.CONSULTANT));
  }

  @Test
  void
      mapAuthorities_Should_returnGrantedConsultantAuthority_When_authoritiesContainConsultant() {
    List<GrantedAuthority> grantedAuthorities =
        Stream.of("a", "v", ROLE_CONSULTANT, "c")
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities =
        this.roleAuthorizationAuthorityMapper.mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(1));
    assertThat(mappedAuthorities.iterator().next().getAuthority(), is(AuthorityValue.CONSULTANT));
  }

  @Test
  void mapAuthorities_Should_returnEmptyCollection_When_authorityIsEmpty() {
    Collection<? extends GrantedAuthority> mappedAuthorities =
        this.roleAuthorizationAuthorityMapper.mapAuthorities(emptyList());

    assertThat(mappedAuthorities, hasSize(0));
  }

  @Test
  void mapAuthorities_Should_returnEmptyCollection_When_authoritiesAreNotProvided() {
    List<GrantedAuthority> grantedAuthorities =
        Stream.of("a", "v", "b", "c").map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    Collection<? extends GrantedAuthority> mappedAuthorities =
        this.roleAuthorizationAuthorityMapper.mapAuthorities(grantedAuthorities);

    assertThat(mappedAuthorities, hasSize(0));
  }
}
