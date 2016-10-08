package pl.khuzzuk.wfrpchar.entities.determinants;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DeterminantFactoryTest {
    @Test(groups = "fast")
    public void createDeterminants() throws Exception {
        Set<Determinant> determinants =
                DeterminantFactory.createDeterminants("25,BATTLE,10-2-true:5-desc-false|-10,OPPONENT_PARRY");
        assertThat(determinants.size()).isEqualTo(2);
        Determinant determinant = determinants.iterator().next();
        assertThat(determinant).isExactlyInstanceOf(PercentageDeterminant.class);
        assertThat(determinant).hasFieldOrPropertyWithValue("baseValue", 25);
        assertThat(determinant).hasFieldOrPropertyWithValue("type", DeterminantsType.BATTLE);
        List<Extension> extensions = determinant.getExtensions();
        assertThat(extensions.size()).isEqualTo(2);
        assertThat(extensions.get(0)).isExactlyInstanceOf(ProfessionExtension.class);
        assertThat(extensions.get(0)).hasFieldOrPropertyWithValue("expSequence", 2);
        assertThat(extensions.get(1)).hasFieldOrPropertyWithValue("description", "desc");
    }

    @Test(groups = "fast")
    public void createDeterminantFromGui() throws Exception {
        Set<Determinant> determinants = DeterminantFactory.createDeterminants(
                DeterminantFactory.getProfessionDeterminant("WW: 40") + "|" +
                        DeterminantFactory.getProfessionDeterminant("US: 20")
        );
        assertThat(determinants.size()).isEqualTo(2);
        Determinant determinant = determinants.stream().filter(d -> d.getType() == DeterminantsType.BATTLE).findAny().orElse(null);
        assertThat(determinant.getActualValue()).isEqualTo(40);
        assertThat(determinant.getExtensions().size()).isEqualTo(4);
    }
}