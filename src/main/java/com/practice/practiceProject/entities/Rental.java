package com.practice.practiceProject.entities;

import com.practice.practiceProject.audit.Auditable;
import com.practice.practiceProject.constant.RentalConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rental")
public class
Rental extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;//Primary Key)

  @Column(name = "user_id")
  @NotNull(message = RentalConstant.NULL_USER_ID)
  private Integer userId; //Foreign Key to User)

  @Column(name = "book_id")
  @NotNull(message = RentalConstant.NULL_BOOK_ID)
  private Integer bookId; //Foreign Key to Book)

  @Column(name = "rental_date")
  @NotNull(message = RentalConstant.NULL_RENTAL_DATE)
  private Date rentalDate;

  @Column(name = "return_date")
  @NotNull(message = RentalConstant.NULL_RETURN_DATE)
  private Date returnDate;
}
