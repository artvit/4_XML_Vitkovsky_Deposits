package by.epam.banks.action;

import by.epam.banks.enumeration.BuilderType;
import by.epam.banks.exception.WrongBuilderTypeException;

public class DepositsBuilderFactory {
    public AbstractDepositsBuilder createDepositsBuilder(BuilderType type) throws WrongBuilderTypeException {
        switch (type) {
            case DOM:
                return new DepositsDOMBuilder();
            case SAX:
                return new DepositsSAXBuilder();
            case STAX:
                return new DepositsStAXBuilder();
            default:
                throw new WrongBuilderTypeException("Wrong type of deposits builder");
        }
    }

    public AbstractDepositsBuilder createDepositsBuilder(BuilderType type, String schemaFileName) throws WrongBuilderTypeException {
        switch (type) {
            case DOM:
                return new DepositsDOMBuilder(schemaFileName);
            case SAX:
                return new DepositsSAXBuilder(schemaFileName);
            case STAX:
                return new DepositsStAXBuilder();
            default:
                throw new WrongBuilderTypeException("Wrong type of deposits builder");
        }
    }
}
