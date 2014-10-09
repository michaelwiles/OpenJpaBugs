package model;

import com.google.common.collect.Lists;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import java.util.List;

@Entity
public class Container {

    /*
     * //This is in the parent table
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentTable", fetch = FetchType.EAGER)
    @OrderColumn
    private ArrayList<child_class_type> childTable = new ArrayList<child_class_type>();

    //This is in the child table
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private parentTableClass parentTable;
     */
    @GeneratedValue
    @Id
    Long id;

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "container")
    private List<OrderedElement> elementsWithOrder = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public List<OrderedElement> getElementsWithOrder() {
        return elementsWithOrder;
    }

    public void setElementsWithOrder(List<OrderedElement> elementsWithOrder) {
        this.elementsWithOrder = elementsWithOrder;
    }

}
