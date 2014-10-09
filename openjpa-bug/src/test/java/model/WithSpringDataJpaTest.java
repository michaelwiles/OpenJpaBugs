package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextJpaRepositories.class)
public class WithSpringDataJpaTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    PlatformTransactionManager tm;

    @Test
    public void OrderColumnTest() {

        TransactionStatus transaction = tm.getTransaction(new DefaultTransactionDefinition());
        Container container = new Container();
        container.getElementsWithOrder().add(new OrderedElement(container));
        container.getElementsWithOrder().add(new OrderedElement(container));
        em.persist(container);
        em.flush();
        tm.commit(transaction);

        transaction = tm.getTransaction(new DefaultTransactionDefinition());

        em.find(Container.class, container.getId());
        OrderedElement element = new OrderedElement(container);
        container.getElementsWithOrder().add(1, element);
        em.flush();
        tm.commit(transaction);

        transaction = tm.getTransaction(new DefaultTransactionDefinition());
        container = em.find(Container.class, container.getId());
        Query query = em.createNativeQuery("select elementsWithOrder_ORDER from OrderedElement where id = ?");

        query.setParameter(1, container.getElementsWithOrder().get(0).getId());
        assertThat(query.getResultList().get(0)).isEqualTo(0);

        query.setParameter(1, container.getElementsWithOrder().get(1).getId());
        assertThat(query.getResultList().get(0)).isEqualTo(1);

        em.flush();
        tm.commit(transaction);
    }
}
