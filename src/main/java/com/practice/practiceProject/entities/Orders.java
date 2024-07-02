package com.practice.practiceProject.entities;

import com.practice.practiceProject.constant.OrderValidationConstant;
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
@Table(name = "orders")
public class Orders {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "user_id")
  @NotNull(message = OrderValidationConstant.NULL_USER_ID)
  private Integer userId;  //Foreign Key to User

  @Column(name = "total_price")
  @NotNull(message = OrderValidationConstant.NULL_TOTAL_PRICE)
  private Double totalPrice;

  @Column(name = "order_date")
  @NotNull(message = OrderValidationConstant.NULL_ORDER_DATE)
  private Date orderDate;
}
