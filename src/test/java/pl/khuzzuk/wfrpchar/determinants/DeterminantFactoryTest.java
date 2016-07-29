package pl.khuzzuk.wfrpchar.determinants;

import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeterminantFactoryTest {
    @Test(groups = "fast")
    public void createDeterminants() throws Exception {
        DeterminantFactory factory = new DeterminantFactory();
        List<Determinant> determinants =
                factory.createDeterminants("25,BATTLE,10-2-true:5-desc-false|-10,OPPONENT_PARRY");
        assertThat(determinants.size()).isEqualTo(2);
        assertThat(determinants.get(0)).isExactlyInstanceOf(PercentageDeterminant.class);
        assertThat(determinants.get(0)).hasFieldOrPropertyWithValue("baseValue", 25);
        assertThat(determinants.get(0)).hasFieldOrPropertyWithValue("type", DeterminantsType.BATTLE);
        List<Extension> extensions = determinants.get(0).getExtensions();
        assertThat(extensions.size()).isEqualTo(2);
        assertThat(extensions.get(0)).isExactlyInstanceOf(ProfessionExtension.class);
        assertThat(extensions.get(0)).hasFieldOrPropertyWithValue("expSequence", 2);
        assertThat(extensions.get(1)).hasFieldOrPropertyWithValue("description", "desc");
    }
}