package github.mahmoudesse.microserviceproduits.model;

import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String description;
  private String image;
  private float price;
}
