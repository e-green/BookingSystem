package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by ruwan on 2/11/16.
 */
@Entity
@Table(name = "type")
@JsonIgnoreProperties
public class Type implements EntityInterface <String> {
    private String typeId;
    private String typeName;
    private BigDecimal amount;

    @Id
    @Column(name = "typeId")
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



    @Override
    @Transient
    public String getId() {
        return getTypeId();
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId='" + typeId + '\'' +
                "typeName='" + typeName + '\'' +
                "amount='" + amount + '\'' +
                '}';
    }

   }
