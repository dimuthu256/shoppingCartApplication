// Generated with g9.

package com.org.shoppingcart.core.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="order_details")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, length=10)
    private int id;
    @Column(nullable=false, length=100)
    private String name;
    @Column(nullable=false, precision=22)
    private double amount;
    @Column(nullable=false, length=10)
    private int quantity;
    @Column(length=1000)
    private String description;
    @Column(nullable=false, length=1)
    private boolean status;
    @UpdateTimestamp
    @Column(name="added_on", nullable=false)
    private Timestamp addedOn; 
    @ManyToOne(optional=false)
    @JoinColumn(name="product_id", nullable=false)
    private Products products;

}
