package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

@Entity
public class UnOrderedElement {

    public UnOrderedElement(Container container) {
        this.container = container;
    }

    public UnOrderedElement() {
        super();
    }

    public Long getId() {
        return id;
    }

    @GeneratedValue
    @Id
    Long id;

    @OrderColumn
    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    Container container;

}
