package model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class BarKey {

    @Transient
    private String name;
    @Transient
    private int modelVersion;

    BarKey() {
    }

    public BarKey(String name, int modelVersion) {
        super();
        this.name = name;
        this.modelVersion = modelVersion;
    }
}
