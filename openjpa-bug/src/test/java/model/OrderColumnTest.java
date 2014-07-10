package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class OrderColumnTest {

    @Autowired
    OrderedElementRepository orderedElementRepository;

    @Autowired
    UnorderedElementRepository unorderedElementRepository;

    @Autowired
    ContainerRepository repository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void OrderColumnTestWorks() {
        Container container = new Container();
        container.elementsWithOrder.add(orderedElementRepository.save(new OrderedElement(container)));
        container.elementsWithOrder.add(orderedElementRepository.save(new OrderedElement(container)));
        repository.save(container);

        repository.findOne(container.getId());
        OrderedElement element = orderedElementRepository.saveAndFlush(new OrderedElement(container));
        container.elementsWithOrder.add(1, element);
        repository.flush();

        Query query = em.createNativeQuery("select elementsWithOrder_ORDER from OrderedElement where id = ?");
        query.setParameter(1, element.getId());
        repository.flush();

        assertThat(query.getResultList().get(0)).isEqualTo(1);
    }

    @Test
    @Transactional
    public void OrderColumnTestFails() {
        Container container = new Container();
        container.elementsNoOrder.add(unorderedElementRepository.save(new UnOrderedElement(container)));
        container.elementsNoOrder.add(unorderedElementRepository.save(new UnOrderedElement(container)));
        repository.save(container);
        repository.flush();

        repository.findOne(container.getId());
        UnOrderedElement element = unorderedElementRepository.saveAndFlush(new UnOrderedElement(container));
        container.elementsNoOrder.add(1, element);
        repository.flush();

        Query query = em.createNativeQuery("select elementsNoOrder_ORDER from UnOrderedElement where id = ?");
        query.setParameter(1, element.getId());
        assertThat(query.getResultList().get(0)).isEqualTo(1);
        repository.flush();
    }

}
