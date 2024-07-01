package com.practice.practiceProject.entities;

import com.practice.practiceProject.constant.OrderItemConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id; //Primary Key

  @Column(name = "order_id")
  @NotNull(message = OrderItemConstant.NULL_ORDER_ID)
  private Integer orderId; // Foreign Key to Order

  @Column(name = "book_id")
  @NotNull(message = OrderItemConstant.NULL_BOOK_ID)
  private Integer bookId; // Foreign Key to Book

  @Column(name = "quantity")
  @NotNull(message = OrderItemConstant.NULL_BOOK_QUANTITY)
  private Integer quantity;

  @Column(name = "price")
  @NotNull(message = OrderItemConstant.NULL_BOOK_PRICE)
  private Double price;
}
