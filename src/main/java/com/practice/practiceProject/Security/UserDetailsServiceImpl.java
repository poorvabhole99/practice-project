package com.practice.practiceProject.Security;

import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = this.userRepository.findByEmailId(username);
//        User user =null;
//        if (optionalUser.isPresent()){
//            user = optionalUser.get();
//        }else {
//            try {
//                throw new PracticeProjectException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode()));
//            } catch (PracticeProjectException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
////        return user;
//        return new CustomUserDetails(user);
//    }
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userInfo = this.userRepository.findByEmailId(username);
    return userInfo.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
}
}
