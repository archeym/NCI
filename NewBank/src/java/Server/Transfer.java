/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Archie
 * Arkadijs Makarenko
 * ID: 15029042
 * Course: HDCLOUDJAN16
 */
@XmlRootElement
public class Transfer {
    
    private BigDecimal amount;
    private Integer fromAccountId;
    private Integer toAccountId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }
    
}
