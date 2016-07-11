package by.epam.banks.util;

import by.epam.banks.exception.NotValidFileException;
import by.epam.banks.exception.ValidatorCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorSAXXSD {
    private static final Logger LOGGER = LogManager.getLogger();

    private Validator validator;

    private ValidatorSAXXSD(Validator validator) {
        this.validator = validator;
    }

    public static ValidatorSAXXSD getValidator(String schemaName) throws ValidatorCreationException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            ValidatorSAXXSD validatorSAXXSD = new ValidatorSAXXSD(validator);
            LOGGER.info("New SAX XSD validator has been created");
            return validatorSAXXSD;
        } catch (SAXException e) {
            throw new ValidatorCreationException("Cannot initialize XSD validator", e);
        }
    }

    public void validate(String filename) throws NotValidFileException {
        try {
            Source source = new StreamSource(filename);
            validator.validate(source);
            LOGGER.info(filename + " is valid.");
        } catch (SAXException | IOException e) {
            throw new NotValidFileException(filename + " is not valid because " + e.getMessage(), e);
        }
    }
}
