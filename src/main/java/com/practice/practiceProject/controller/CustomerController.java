package com.practice.practiceProject.controller;

import com.practice.practiceProject.response.CustomerResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {
  /**
   * Retrieves a list of books available for a specific customer.
   *
   * @param customerId the ID of the customer whose books are to be retrieved
   * @param isRental indicates whether to fetch books available for rent or purchase
   * @return a ResponseEntity containing the list of books for the customer
   */
  @GetMapping("/books")
  public ResponseEntity<CustomerResponse> getAllBooksForACustomer(@PathParam("customerId")final String customerId,
      @PathParam("isRental") final Boolean isRental) {
    return ResponseEntity.ok(null);
  }

  /**
   * POST /customer/orders Create a new order (buy books).
   */

  @PostMapping("/order")
  public ResponseEntity<CustomerResponse> createOrder() {
    return ResponseEntity.ok(null);
  }

  /**
   * GET /customer/orders Get a list of customer's orders.
   */
  @GetMapping("/orders")
  public ResponseEntity<CustomerResponse> getAllCustomerOrder() {
    return ResponseEntity.ok(null);
  }

  /**
   * POST /customer/rentals Rent a book.
   */
  @PostMapping("/rentals")
  public ResponseEntity<CustomerResponse> rentBook() {
    return ResponseEntity.ok(null);
  }


  /**
   * GET /customer/rentals Get a list of customer's rentals.
   */
  @GetMapping("/rentals")
  public ResponseEntity<CustomerResponse> getAllRentalBooks() {
     return ResponseEntity.ok(null);
  }
}
