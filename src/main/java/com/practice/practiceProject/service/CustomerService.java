package com.practice.practiceProject.service;

import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.response.CustomerResponse;

public interface CustomerService {
   CustomerResponse getAllBooksBooksForACustomer(final String customerId, final Boolean isRental);
   CustomerResponse getAllCustomerOrder(final String shopOwnerId) throws PracticeProjectException;

}
