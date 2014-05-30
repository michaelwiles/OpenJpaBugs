package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class FooTest {

    
    @Autowired
    FooRepository repository;


    @Test
    @Transactional
    public void TestLookupWithMoreOneEnumInTheFilter() {
        Foo foo = new Foo();
        repository.save(foo);
    }
}
