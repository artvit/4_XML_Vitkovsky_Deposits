package by.epam.banks.action;

import by.epam.banks.entity.CurrencyAmount;
import by.epam.banks.entity.Deposit;
import by.epam.banks.entity.MetalAmount;
import by.epam.banks.enumeration.DepositAttribute;
import by.epam.banks.enumeration.DepositType;
import by.epam.banks.exception.DepositParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DepositsStAXBuilder extends AbstractDepositsBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private XMLInputFactory factory;
    private XMLStreamReader reader;

    public DepositsStAXBuilder() {
        this.factory = XMLInputFactory.newFactory();
    }

    @Override
    public void buildDepositsSet(String fileName) {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
            reader = factory.createXMLStreamReader(inputStream);
            while(reader.hasNext()){
                int i = reader.next();
                if (i == XMLStreamConstants.START_ELEMENT) {
                    if (DepositAttribute.DEPOSIT.equals(reader.getLocalName())) {

                        try {
                            Deposit deposit = buildDeposit();
                            deposits.add(deposit);
                        } catch (DepositParseException e) {
                            LOGGER.error("Error while parsing deposit", e);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(fileName + " not found", e);
        } catch (XMLStreamException e) {
            LOGGER.error("Exception with XML stream reader", e);
        }
    }

    private Deposit buildDeposit() throws DepositParseException {
        Deposit deposit = new Deposit();
        String id = reader.getAttributeValue(null, DepositAttribute.ID);
        deposit.setDepositId(id);
        String country = reader.getAttributeValue(null, DepositAttribute.COUNTRY);
        if (country != null && !country.isEmpty()) {
            deposit.setCountry(country);
        }
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    handleStartElement(reader.getLocalName(), deposit);
                } else if (type == XMLStreamConstants.END_ELEMENT) {
                    if (DepositAttribute.DEPOSIT.equals(reader.getLocalName())) {
                        return deposit;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new DepositParseException("Error while parsing deposit", e);
        }
        return deposit;
    }

    private void handleStartElement(String localName, Deposit deposit) throws DepositParseException {
        if (DepositAttribute.BANK.equals(localName)) {
            deposit.setBank(getNextTextContent());
        } else if (DepositAttribute.FIRST_NAME.equals(localName)) {
            deposit.setDepositorFirstName(getNextTextContent());
        } else if (DepositAttribute.LAST_NAME.equals(localName)) {
            deposit.setDepositorLastName(getNextTextContent());
        } else if (DepositAttribute.TYPE.equals(localName)) {
            DepositType type = DepositType.valueOf(getNextTextContent().toUpperCase());
            deposit.setType(type);
        } else if (DepositAttribute.TIME.equals(localName)) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            try {
                calendar.setTime(format.parse(getNextTextContent()));
                deposit.setTime(calendar);
            } catch (ParseException e) {
                LOGGER.error("Cannot parse time", e);
            }
        } else if (DepositAttribute.PROFITABILITY.equals(localName)) {
            String data = getNextTextContent();
            double profitability = Double.valueOf(data.substring(0, data.length() - 1));
            deposit.setProfitability(profitability);
        } else if (DepositAttribute.CURRENCY_AMOUNT.equals(localName)) {
            String currency = reader.getAttributeValue(null, DepositAttribute.CURRENCY);
            if (currency != null && !currency.isEmpty()) {
                double amountValue = Double.valueOf(getNextTextContent());
                deposit.setAmount(new CurrencyAmount(currency, amountValue));
            } else {
                throw new DepositParseException("Cannot parse Currency Amount");
            }
        } else if (DepositAttribute.METAL_AMOUNT.equals(localName)) {
            String metal = reader.getAttributeValue(null, DepositAttribute.METAL);
            if (metal != null && !metal.isEmpty()) {
                double amountValue = Double.valueOf(getNextTextContent());
                deposit.setAmount(new MetalAmount(metal, amountValue));
            } else {
                throw new DepositParseException("Cannot parse Metal Amount");
            }
        }
    }

    private String getNextTextContent() throws DepositParseException {
        String data = null;
        try {
            if (reader.hasNext()) {
                reader.next();
                data = reader.getText();
            }
        } catch (XMLStreamException e) {
            LOGGER.error("Cannot get text", e);
        }
        if (data != null && !data.isEmpty()) {
            return data;
        } else {
            throw new DepositParseException("Cannot read element text");
        }
    }
}
