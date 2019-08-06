// Generated with g9.

package com.org.shoppingcart.core.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="accounts")
public class Accounts implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, length=10)
    private int id;
    @Column(name="user_name", nullable=false, length=20)
    private String userName;
    @Column(nullable=false, length=1)
    private boolean active;
    @Column(name="user_role", nullable=false, length=20)
    private String userRole;

    /** Default constructor. */
    public Accounts() {
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
     * Access method for userName.
     *
     * @return the current value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for userName.
     *
     * @param aUserName the new value for userName
     */
    public void setUserName(String aUserName) {
        userName = aUserName;
    }

    /**
     * Access method for active.
     *
     * @return true if and only if active is currently true
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter method for active.
     *
     * @param aActive the new value for active
     */
    public void setActive(boolean aActive) {
        active = aActive;
    }

    /**
     * Access method for userRole.
     *
     * @return the current value of userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Setter method for userRole.
     *
     * @param aUserRole the new value for userRole
     */
    public void setUserRole(String aUserRole) {
        userRole = aUserRole;
    }

    /**
     * Compares the key for this instance with another Accounts.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Accounts and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Accounts)) {
            return false;
        }
        Accounts that = (Accounts) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Accounts.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Accounts)) return false;
        return this.equalKeys(other) && ((Accounts)other).equalKeys(this);
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
        StringBuffer sb = new StringBuffer("[Accounts |");
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
