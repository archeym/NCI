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
public class Balance {

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Balance(BigDecimal value) {
        this.value = value;
    }

    public Balance() {
    }
    
    
    private BigDecimal value;

}
