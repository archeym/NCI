/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Archie
 * Arkadijs Makarenko
 * ID: 15029042
 * Course: HDCLOUDJAN16
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByTransactionsId", query = "SELECT t FROM Transactions t WHERE t.transactionsId = :transactionsId"),
    @NamedQuery(name = "Transactions.findByTransDate", query = "SELECT t FROM Transactions t WHERE t.transDate = :transDate"),
    @NamedQuery(name = "Transactions.findByTransValue", query = "SELECT t FROM Transactions t WHERE t.transValue = :transValue"),
    @NamedQuery(name = "Transactions.findByTransType", query = "SELECT t FROM Transactions t WHERE t.transType = :transType"),
    @NamedQuery(name = "Transactions.findByDescription", query = "SELECT t FROM Transactions t WHERE t.description = :description"),
    @NamedQuery(name = "Transactions.findByBalance", query = "SELECT t FROM Transactions t WHERE t.balance = :balance")})
public class Transactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TransactionsId")
    private Integer transactionsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransDate")
    @Temporal(TemporalType.DATE)
    private Date transDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransValue")
    private BigDecimal transValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransType")
    private boolean transType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Balance")
    private BigDecimal balance;
    @JoinColumn(name = "AccountId", referencedColumnName = "AccountId")
    @ManyToOne(optional = false)
    private Accounts accountId;

    public Transactions() {
    }

    public Transactions(Integer transactionsId) {
        this.transactionsId = transactionsId;
    }

    public Transactions(Integer transactionsId, Date transDate, BigDecimal transValue, boolean transType, String description, BigDecimal balance) {
        this.transactionsId = transactionsId;
        this.transDate = transDate;
        this.transValue = transValue;
        this.transType = transType;
        this.description = description;
        this.balance = balance;
    }

    public Integer getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(Integer transactionsId) {
        this.transactionsId = transactionsId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public BigDecimal getTransValue() {
        return transValue;
    }

    public void setTransValue(BigDecimal transValue) {
        this.transValue = transValue;
    }

    public boolean getTransType() {
        return transType;
    }

    public void setTransType(boolean transType) {
        this.transType = transType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Accounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Accounts accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionsId != null ? transactionsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transactionsId == null && other.transactionsId != null) || (this.transactionsId != null && !this.transactionsId.equals(other.transactionsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Server.Transactions[ transactionsId=" + transactionsId + " ]";
    }

}
