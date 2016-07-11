package test.by.epam.banks.action;


import by.epam.banks.action.AbstractDepositsBuilder;
import by.epam.banks.action.DepositsBuilderFactory;
import by.epam.banks.entity.Deposit;
import by.epam.banks.enumeration.BuilderType;
import by.epam.banks.exception.WrongBuilderTypeException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class DepositDOMBuilderTest {
    public static final String SCHEMA_FILE_NAME = "resource/bank.xsd";

    @Test
    public void buildDepositsSetTest() {
        try {
            DepositsBuilderFactory factory = new DepositsBuilderFactory();
            AbstractDepositsBuilder builderDOM = factory.createDepositsBuilder(BuilderType.DOM, SCHEMA_FILE_NAME);
            builderDOM.buildDepositsSet("resource/banks.xml");
            Set<Deposit> deposits = builderDOM.getDeposits();
            Assert.assertTrue(deposits.size() == 3);
        } catch (WrongBuilderTypeException e) {
            Assert.fail();
        }
    }
}
