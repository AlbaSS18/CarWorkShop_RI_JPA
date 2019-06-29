package uo.ri.otrosTest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	uo.ri.otrosTest.associations.AllTests.class,
	uo.ri.otrosTest.service.AllTests.class,
	uo.ri.otrosTest.persistence.PersistenceTest.class,
})
public class AllTests { }
