package test.by.epam.banks.suit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.by.epam.banks.action.DepositDOMBuilderTest;
import test.by.epam.banks.util.ValidatorSAXXSDTest;

@Suite.SuiteClasses( { ValidatorSAXXSDTest.class, DepositDOMBuilderTest.class} )
@RunWith(Suite.class)
public class AllTest {

}
