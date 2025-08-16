/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author USER
 */

@Entity
@Table(name="order_item")
public class OrderItems implements Serializable{
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
     
    @ManyToOne
    @JoinColumn(name="product_id") 
    private Product product;
    
     @Column(name = "qty", nullable = false)
    private int qty;
     
    @ManyToOne
    @JoinColumn(name="orders_id") 
    private Orders orders;
    
    @ManyToOne
    @JoinColumn(name="order_status_id")
    private OrderStatus orderStatus;
    
    @ManyToOne
    @JoinColumn(name="delivery_type_id")
    private DeliveryTypes deliveryTypes;
    
    @Column(name = "rating", nullable = false)
    private int rating;
    
    
    public OrderItems() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the orders
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    /**
     * @return the orderStatus
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the deliveryTypes
     */
    public DeliveryTypes getDeliveryTypes() {
        return deliveryTypes;
    }

    /**
     * @param deliveryTypes the deliveryTypes to set
     */
    public void setDeliveryTypes(DeliveryTypes deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
