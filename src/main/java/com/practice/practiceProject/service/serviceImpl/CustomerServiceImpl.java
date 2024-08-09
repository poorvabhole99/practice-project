package com.practice.practiceProject.service.serviceImpl;

import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.response.CustomerResponse;
import com.practice.practiceProject.response.ErrorResponse;
import com.practice.practiceProject.service.CustomerService;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
  private final UserRepository userRepository;

  public CustomerServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomerResponse getAllBooksBooksForACustomer(String customerId,
      Boolean isRental) {

    //validate customer exist


    return null;
  }

  @Override
  public CustomerResponse getAllCustomerOrder(String shopOwnerId) throws PracticeProjectException {
    //validate shop owner id
    validateShopOwnerById(shopOwnerId);


    return null;
  }

  private void validateShopOwnerById(final String shopOwnerId) throws PracticeProjectException {
    Optional<User> optionalUser = this.userRepository.findByUserIdAndStatus(shopOwnerId,"ACTIVE");
    if(optionalUser.isEmpty()){
      throw new PracticeProjectException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(),false,ErrorEnum.USER_NOT_FOUND.getErrorCode()));
    }
    final User user = optionalUser.get();
    final Set<String> userRoles = user.getRoles();
    boolean hasShopOwnerRole = userRoles.stream().anyMatch(role -> role.equals("SHOP_OWNER"));

    if (!hasShopOwnerRole) {
      throw new PracticeProjectException(new ErrorResponse("You are not owner of any shop",false,"100"));
    }

  }
}
