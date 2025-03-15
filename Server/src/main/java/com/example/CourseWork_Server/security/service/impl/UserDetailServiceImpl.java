package com.example.CourseWork_Server.security.service.impl;

import static com.example.CourseWork_Server.utils.SecurityUtils.getUserAuthorities;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.model.Credential;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.service.CredentialService;
import com.example.CourseWork_Server.service.UserService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserService userService;
  private final CredentialService credentialService;

  /**
   * Loads the user by email, retrieves user credentials, and returns a {@link UserDetails} object
   * containing the user's authentication information.
   *
   * @param email the email address of the user to be loaded.
   * @return a {@link UserDetails} object with the user's authentication information.
   * @throws UsernameNotFoundException if the user with the specified email is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.findByEmail(email);
    Credential credential = credentialService.findByUser(user);

    return processLoading(user, credential);
  }

  private UserDetails processLoading(User user, Credential credential) {
    AccountStatus status = user.getAccountStatus();

    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserAuthorities(user);
      }

      @Override
      public String getPassword() {
        return credential.getPassword();
      }

      @Override
      public String getUsername() {
        return user.getEmail();
      }

      @Override
      public boolean isAccountNonExpired() {
        return status != AccountStatus.DELETED;
      }

      @Override
      public boolean isAccountNonLocked() {
        return status != AccountStatus.BANNED;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return status == AccountStatus.VERIFIED;
      }
    };
  }
}
