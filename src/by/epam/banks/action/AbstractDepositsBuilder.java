package by.epam.banks.action;

import by.epam.banks.entity.Deposit;
import by.epam.banks.exception.SchemaCreationException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDepositsBuilder {
    protected Set<Deposit> deposits;

    public AbstractDepositsBuilder() {
        this.deposits = new HashSet<>();
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    abstract public void buildDepositsSet(String fileName);

    public static Schema createSchema(String schemaFileName) throws SchemaCreationException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaLocation = new File(schemaFileName);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            return schema;
        } catch (SAXException e) {
            throw new SchemaCreationException("Cannot create schema", e);
        }
    }
}
