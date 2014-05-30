package model;

import com.google.common.collect.Maps;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyClass;
import javax.persistence.OneToMany;

import java.util.Map;

@Entity
public class Foo {

    @GeneratedValue
    @Id
    Long id;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @MapKeyClass(BarKey.class)
    private Map<BarKey, Bar> barMap = Maps.newLinkedHashMap();

    public Map<BarKey, Bar> getBarMap() {
        return barMap;
    }

}
