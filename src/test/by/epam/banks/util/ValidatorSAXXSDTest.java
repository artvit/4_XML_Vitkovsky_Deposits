package test.by.epam.banks.util;

import by.epam.banks.exception.NotValidFileException;
import by.epam.banks.exception.ValidatorCreationException;
import by.epam.banks.util.ValidatorSAXXSD;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorSAXXSDTest {
    public static final String SCHEMA_FILE_NAME = "resource/bank.xsd";

    private ValidatorSAXXSD validator;

    @Before
    public void init() {
        try {
            validator = ValidatorSAXXSD.getValidator(SCHEMA_FILE_NAME);
        } catch (ValidatorCreationException e) {
            Assert.fail();
        }
    }

    @Test(expected = NotValidFileException.class)
    public void testValidateEmpty() throws NotValidFileException {
        validator.validate("resource/empty.xml");
    }

    @Test(expected = NotValidFileException.class)
    public void testValidateIncorrectFile() throws NotValidFileException {
        validator.validate("resource/wrong.xml");
    }
}
