package com.payconiq.assignment.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="STOCK")
public class StockEntity implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "STOCK_KEY", nullable = false)
    private String stockKey;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CURRENT_PRICE", nullable = false)
    private Double currentPrice;

    @UpdateTimestamp
    private Date lastUpdate;
}
