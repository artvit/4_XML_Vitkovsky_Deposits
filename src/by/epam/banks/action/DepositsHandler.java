package by.epam.banks.action;

import by.epam.banks.entity.Amount;
import by.epam.banks.entity.CurrencyAmount;
import by.epam.banks.entity.Deposit;
import by.epam.banks.entity.MetalAmount;
import by.epam.banks.enumeration.DepositAttribute;
import by.epam.banks.enumeration.DepositType;
import by.epam.banks.exception.DepositParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class DepositsHandler extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private Set<Deposit> deposits;
    private String currentElement;
    private Deposit deposit;
    private Amount amount;

    public DepositsHandler() {
        this.deposits = new HashSet<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DepositAttribute.DEPOSIT.equals(localName)) {
            deposit = new Deposit();
            for (int i = 0; i < attributes.getLength(); ++i) {
                if (DepositAttribute.COUNTRY.equals(attributes.getLocalName(i))) {
                    deposit.setCountry(attributes.getValue(i));
                } else if (DepositAttribute.ID.equals(attributes.getLocalName(i))) {
                    deposit.setDepositId(attributes.getValue(i));
                }
            }
        } else if (DepositAttribute.CURRENCY_AMOUNT.equals(localName)) {
            String currency = attributes.getValue(0);
            amount = new CurrencyAmount(currency);
        } else if (DepositAttribute.METAL_AMOUNT.equals(localName)) {
            String metal = attributes.getValue(0);
            amount = new MetalAmount(metal);
        }
        currentElement = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (DepositAttribute.DEPOSIT.equals(localName)) {
            deposits.add(deposit);
        } else if (DepositAttribute.CURRENCY_AMOUNT.equals(localName) ||
                DepositAttribute.METAL_AMOUNT.equals(localName)) {
            deposit.setAmount(amount);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length).trim();
        if (DepositAttribute.BANK.equals(currentElement)) {
            deposit.setBank(data);
        } else if (DepositAttribute.FIRST_NAME.equals(currentElement)) {
            deposit.setDepositorFirstName(data);
        } else if (DepositAttribute.LAST_NAME.equals(currentElement)) {
            deposit.setDepositorLastName(data);
        } else if (DepositAttribute.TYPE.equals(currentElement)) {
            DepositType type = DepositType.valueOf(data.toUpperCase());
            deposit.setType(type);
        } else if (DepositAttribute.TIME.equals(currentElement)) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            try {
                calendar.setTime(format.parse(data));
                deposit.setTime(calendar);
            } catch (ParseException e) {
                LOGGER.error("Cannot parse time", e);
            }
        } else if (DepositAttribute.PROFITABILITY.equals(currentElement)) {
            double profitability = Double.valueOf(data.substring(0, data.length() - 1));
            deposit.setProfitability(profitability);
        } else if (DepositAttribute.CURRENCY_AMOUNT.equals(currentElement) ||
                DepositAttribute.METAL_AMOUNT.equals(currentElement)) {
            double amountValue = Double.valueOf(data);
            amount.setAmount(amountValue);
        }
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }
}
