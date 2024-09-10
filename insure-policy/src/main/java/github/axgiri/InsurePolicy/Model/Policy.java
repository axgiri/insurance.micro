package github.axgiri.InsurePolicy.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policies")
public class Policy {
//TODO: покупка страховки
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private github.axgiri.InsurePolicy.Enum.TypeEnum insuranceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private github.axgiri.InsurePolicy.Enum.PackageEnum insuransePackage;

    @Column(nullable = false)
    private Float price;
}
