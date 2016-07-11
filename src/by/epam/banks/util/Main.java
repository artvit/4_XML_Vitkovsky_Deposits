package by.epam.banks.util;

import by.epam.banks.action.AbstractDepositsBuilder;
import by.epam.banks.action.DepositsBuilderFactory;
import by.epam.banks.action.DepositsReporter;
import by.epam.banks.entity.Deposit;
import by.epam.banks.enumeration.BuilderType;
import by.epam.banks.exception.NotValidFileException;
import by.epam.banks.exception.ValidatorCreationException;
import by.epam.banks.exception.WrongBuilderTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String SCHEMA_FILE_NAME = "resource/bank.xsd";
    public static final String INPUT_FILE_NAME = "resource/banks.xml";

    public static void main(String[] args) {
        try {
            ValidatorSAXXSD validator = ValidatorSAXXSD.getValidator(SCHEMA_FILE_NAME);
            validator.validate(INPUT_FILE_NAME);
            LOGGER.info("Validation complete");
            DepositsBuilderFactory factory = new DepositsBuilderFactory();
            LOGGER.info("Factory created");
            DepositsReporter reporter = new DepositsReporter();
            LOGGER.info("Reporter created");

            AbstractDepositsBuilder builderDOM = factory.createDepositsBuilder(BuilderType.DOM, SCHEMA_FILE_NAME);
            LOGGER.info("DOM builder created");
            builderDOM.buildDepositsSet(INPUT_FILE_NAME);
            LOGGER.info("Builder has finished parsing");
            Set<Deposit> deposits = builderDOM.getDeposits();
            reporter.setDeposits(deposits);
            LOGGER.info("Result:\n" + reporter.getDepositsString());

            AbstractDepositsBuilder builderSAX = factory.createDepositsBuilder(BuilderType.SAX, SCHEMA_FILE_NAME);
            LOGGER.info("SAX builder created");
            builderSAX.buildDepositsSet(INPUT_FILE_NAME);
            LOGGER.info("Builder has finished parsing");
            Set<Deposit> depositsSAX = builderSAX.getDeposits();
            reporter.setDeposits(depositsSAX);
            LOGGER.info("Result:\n" + reporter.getDepositsString());

            AbstractDepositsBuilder builderStAX = factory.createDepositsBuilder(BuilderType.STAX);
            LOGGER.info("StAX builder created");
            builderStAX.buildDepositsSet(INPUT_FILE_NAME);
            LOGGER.info("Builder has finished parsing");
            Set<Deposit> depositsStAX = builderStAX.getDeposits();
            reporter.setDeposits(depositsStAX);
            LOGGER.info("Result:\n" + reporter.getDepositsString());
        } catch (ValidatorCreationException e) {
            LOGGER.error("Error while creating XSD validator", e);
        } catch (NotValidFileException e) {
            LOGGER.error(INPUT_FILE_NAME + " is not a valid file for " + SCHEMA_FILE_NAME + " schema!", e);
        } catch (WrongBuilderTypeException e) {
            LOGGER.error("Cannot create deposits builder", e);
        }
    }
}
