package pl.khuzzuk.wfrpchar.entities;

public interface Featured extends Named<String> {
    String getSpecialFeatures();

    void setSpecialFeatures(String specialFeatures);

    void setName(String name);
}
