package model;

import com.google.common.collect.Maps;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

import java.util.Map;

@Entity
public class Foo {

    @GeneratedValue
    @Id
    Long id;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "foo")
    @MapKeyClass(BarKey.class)
    //@MapKeyJoinColumn(name = "BARKEY_ID")
    private Map<BarKey, Bar> barMap = Maps.newLinkedHashMap();

    /* @OneToMany
     @JoinColumn
     private List<Bar> bars;*/

    public Map<BarKey, Bar> getBarMap() {
        return barMap;
    }

}
