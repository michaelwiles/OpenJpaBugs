package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class FooTest {

    @Autowired
    FooRepository repository;

    @Autowired
    PlatformTransactionManager tm;

    @Test
    @Transactional
    @Rollback(false)
    public void TestLookupWithMoreOneEnumInTheFilter() {
        
        TransactionTemplate template = new TransactionTemplate(tm);
        template.execute(new TransactionCallback<String>() {

            @Override
            public String doInTransaction(TransactionStatus status) {
                Foo foo = new Foo();
                Bar bar = new Bar("x", 1);
                foo.getBarMap().put(new BarKey("x", 1), bar);
                bar.setFoo(foo);
                repository.save(foo);
                return null;
            }
        });


        template.execute(new TransactionCallback<String>() {

            @Override
            public String doInTransaction(TransactionStatus status) {
                List<Foo> findAll = repository.findAll();

                for (Foo foo2 : findAll) {
                    System.out.println(foo2.getBarMap());
                }
                return null;
            }
        });
     
        
    }
}
