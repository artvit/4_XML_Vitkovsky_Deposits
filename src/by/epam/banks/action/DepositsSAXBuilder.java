package by.epam.banks.action;

import by.epam.banks.exception.SchemaCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class DepositsSAXBuilder extends AbstractDepositsBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private DepositsHandler handler;
    private SAXParser parser;

    public DepositsSAXBuilder() {
        handler = new DepositsHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error("Cannot create SAX parser", e);
        }
    }

    public DepositsSAXBuilder(String schemaFileName) {
        handler = new DepositsHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        if (schemaFileName != null) {
            try {
                factory.setSchema(createSchema(schemaFileName));
            } catch (SchemaCreationException e) {
                LOGGER.error("Cannot create XSD schema from file " + schemaFileName);
            }
        }
        factory.setNamespaceAware(true);
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error("Cannot create SAX parser", e);
        }
    }

    @Override
    public void buildDepositsSet(String fileName) {
        try {
            parser.parse(fileName, handler);
        } catch (SAXException | IOException e) {
            LOGGER.error("Cannot parse " + fileName, e);
        }
        deposits = handler.getDeposits();
    }
}
