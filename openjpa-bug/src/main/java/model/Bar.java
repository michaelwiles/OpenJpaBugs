package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bar {

    @GeneratedValue
    @Id
    Long id;

    private String name;
    private int modelVersion;

    @ManyToOne
    @JoinColumn
    Foo foo;

    public Bar() {
    }

    public Bar(String name, int modelVersion) {
        super();
        this.name = name;
        this.modelVersion = modelVersion;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

}
