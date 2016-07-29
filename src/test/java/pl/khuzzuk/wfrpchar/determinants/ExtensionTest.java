package pl.khuzzuk.wfrpchar.determinants;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtensionTest {
    @Test(groups = "fast")
    public void parseProffesionExtension() throws Exception {
        Extension extension = Extension.parseExtension("10-1-true");
        assertThat(extension).isExactlyInstanceOf(ProfessionExtension.class);
        assertThat(extension).hasFieldOrPropertyWithValue("modifier", 10);
        assertThat(extension).hasFieldOrPropertyWithValue("expSequence", 1);
    }

    @Test(groups = "fast")
    public void parseMiscExtension() throws Exception {
        Extension extension = Extension.parseExtension("20-desc-false");
        assertThat(extension).isExactlyInstanceOf(MiscExtension.class);
        assertThat(extension).hasFieldOrPropertyWithValue("modifier", 20);
        assertThat(extension).hasFieldOrPropertyWithValue("description", "desc");
    }
}