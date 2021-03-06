// Generated with g9.

package com.org.shoppingcart.core.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="orders", indexes={@Index(name="orders_order_num_IX", columnList="order_num", unique=true)})
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, length=10)
    private int id;
    @Column(nullable=false, precision=22)
    private double amount;
    @Column(name="customer_address", length=255)
    private String customerAddress;
    @Column(name="customer_email", length=128)
    private String customerEmail;
    @Column(name="customer_name", length=255)
    private String customerName;
    @Column(name="customer_phone", length=128)
    private String customerPhone;
    @Column(name="order_date", nullable=false)
    @CreationTimestamp
    private Timestamp orderDate;
    @Column(name="order_num", unique=true, nullable=false, length=10)
    private int orderNum;

    /** Default constructor. */
    public Orders() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(int aId) {
        id = aId;
    }

    /**
     * Access method for amount.
     *
     * @return the current value of amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter method for amount.
     *
     * @param aAmount the new value for amount
     */
    public void setAmount(double aAmount) {
        amount = aAmount;
    }

    /**
     * Access method for customerAddress.
     *
     * @return the current value of customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter method for customerAddress.
     *
     * @param aCustomerAddress the new value for customerAddress
     */
    public void setCustomerAddress(String aCustomerAddress) {
        customerAddress = aCustomerAddress;
    }

    /**
     * Access method for customerEmail.
     *
     * @return the current value of customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Setter method for customerEmail.
     *
     * @param aCustomerEmail the new value for customerEmail
     */
    public void setCustomerEmail(String aCustomerEmail) {
        customerEmail = aCustomerEmail;
    }

    /**
     * Access method for customerName.
     *
     * @return the current value of customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter method for customerName.
     *
     * @param aCustomerName the new value for customerName
     */
    public void setCustomerName(String aCustomerName) {
        customerName = aCustomerName;
    }

    /**
     * Access method for customerPhone.
     *
     * @return the current value of customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Setter method for customerPhone.
     *
     * @param aCustomerPhone the new value for customerPhone
     */
    public void setCustomerPhone(String aCustomerPhone) {
        customerPhone = aCustomerPhone;
    }

    /**
     * Access method for orderDate.
     *
     * @return the current value of orderDate
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Setter method for orderDate.
     *
     * @param aOrderDate the new value for orderDate
     */
    public void setOrderDate(Timestamp aOrderDate) {
        orderDate = aOrderDate;
    }

    /**
     * Access method for orderNum.
     *
     * @return the current value of orderNum
     */
    public int getOrderNum() {
        return orderNum;
    }

    /**
     * Setter method for orderNum.
     *
     * @param aOrderNum the new value for orderNum
     */
    public void setOrderNum(int aOrderNum) {
        orderNum = aOrderNum;
    }

    /**
     * Compares the key for this instance with another Orders.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Orders and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Orders)) {
            return false;
        }
        Orders that = (Orders) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Orders.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Orders)) return false;
        return this.equalKeys(other) && ((Orders)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getId();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Orders |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("id", Integer.valueOf(getId()));
        return ret;
    }

}
