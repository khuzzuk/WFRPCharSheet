package pl.khuzzuk.wfrpchar.determinants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("2")
@Entity
public class AbsoluteDeterminant extends Determinant {
}
