package com.practice.practiceProject.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.practice.practiceProject.audit.Auditable;
import com.practice.practiceProject.constant.BookConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  @Size(min = 5, message = BookConstant.SHORT_BOOK_TITLE)
  @Size(max = 20, message = BookConstant.LONG_BOOK_TITLE)
  private String title;

  @Column(name = "author_name")
  @Size(min = 5,message = BookConstant.SHORT_AUTHOR_NAME)
  @Size(max = 20, message = BookConstant.LONG_AUTHOR_NAME)
  private String authorName;

  @Column(name = "isbn")
  private String isbn;  //Unique identifier for book

  @NotNull(message = BookConstant.NULL_BOOK_PRICE)
  @Column(name = "price")
  private Double price;

  @NotNull(message = BookConstant.NULL_RENTAL_PRICE)
  @Column(name = "rental_price")
  private Double rentalPrice;

  @NotNull(message = BookConstant.NULL_BOOK_STOCK)
  @Column(name = "stock")
  private Integer stock;

}
