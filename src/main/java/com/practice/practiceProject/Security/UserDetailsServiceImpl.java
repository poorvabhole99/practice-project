package com.practice.practiceProject.Security;

import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userInfo = this.userRepository.findByEmailId(username);
    return userInfo.map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));
  }
}
