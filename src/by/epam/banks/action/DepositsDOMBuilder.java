package by.epam.banks.action;

import by.epam.banks.entity.CurrencyAmount;
import by.epam.banks.entity.Deposit;
import by.epam.banks.entity.MetalAmount;
import by.epam.banks.enumeration.DepositAttribute;
import by.epam.banks.enumeration.DepositType;
import by.epam.banks.exception.DepositParseException;
import by.epam.banks.exception.SchemaCreationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DepositsDOMBuilder extends AbstractDepositsBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private DocumentBuilderFactory documentBuilderFactory;

    public DepositsDOMBuilder() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
    }

    public DepositsDOMBuilder(String schemaFileName) {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        if (schemaFileName != null) {
            try {
                documentBuilderFactory.setSchema(createSchema(schemaFileName));
            } catch (SchemaCreationException e) {
                LOGGER.error("Cannot create XSD schema from file " + schemaFileName);
            }
        }
        documentBuilderFactory.setNamespaceAware(true);
    }

    @Override
    public void buildDepositsSet(String fileName) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList depositsList = root.getElementsByTagName(DepositAttribute.DEPOSIT);
            for (int i = 0; i < depositsList.getLength(); ++i) {
                Element depositElement = (Element) depositsList.item(i);
                try {
                    Deposit deposit = buildDeposit(depositElement);
                    deposits.add(deposit);
                } catch (DepositParseException e) {
                    LOGGER.error("Cannot parse deposit", e);
                }
            }
        } catch (ParserConfigurationException e) {
            LOGGER.error("Cannot create DocumentBuilder", e);
        } catch (SAXException | IOException e) {
            LOGGER.error("Cannot parse file " + fileName, e);
        }
    }

    private Deposit buildDeposit(Element element) throws DepositParseException {
        Deposit deposit = new Deposit();

        String id = element.getAttribute(DepositAttribute.ID);
        if (!id.isEmpty()) {
            deposit.setDepositId(id);
        } else {
            throw new DepositParseException("No id attribute found");
        }

        String country = element.getAttribute(DepositAttribute.COUNTRY);
        if (!country.isEmpty()) {
            deposit.setCountry(country);
        }

        Element bankElement = getChildElement(element, DepositAttribute.BANK);
        deposit.setBank(getElementTextContent(bankElement));

        Element depositorElement = getChildElement(element, DepositAttribute.DEPOSITOR);
        Element depositorFirstNameElement = getChildElement(depositorElement, DepositAttribute.FIRST_NAME);
        deposit.setDepositorFirstName(getElementTextContent(depositorFirstNameElement));
        Element depositorLastNameElement = getChildElement(depositorElement, DepositAttribute.LAST_NAME);
        deposit.setDepositorLastName(getElementTextContent(depositorLastNameElement));

        Element typeElement = getChildElement(element, DepositAttribute.TYPE);
        DepositType type = DepositType.valueOf(getElementTextContent(typeElement).toUpperCase());
        deposit.setType(type);

        Element amountElement = getChildElement(element, DepositAttribute.CURRENCY_AMOUNT);
        if (amountElement != null) {
            String currency = amountElement.getAttribute(DepositAttribute.CURRENCY);
            double amount = Double.valueOf(getElementTextContent(amountElement));
            deposit.setAmount(new CurrencyAmount(currency, amount));
        }
        amountElement = getChildElement(element, DepositAttribute.METAL_AMOUNT);
        if (amountElement != null) {
            String metal = amountElement.getAttribute(DepositAttribute.METAL);
            double amount = Double.valueOf(getElementTextContent(amountElement));
            deposit.setAmount(new MetalAmount(metal, amount));
        }

        Element profitabilityElement = getChildElement(element, DepositAttribute.PROFITABILITY);
        String profitabilityString = getElementTextContent(profitabilityElement);
        double profitability = Double.valueOf(profitabilityString.substring(0, profitabilityString.length() - 1));
        deposit.setProfitability(profitability);

        Element timeElement = getChildElement(element, DepositAttribute.TIME);
        String timeString = getElementTextContent(timeElement);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            calendar.setTime(format.parse(timeString));
            deposit.setTime(calendar);
        } catch (ParseException e) {
            throw new DepositParseException("Cannot parse time", e);
        }

        return deposit;
    }

    private static Element getChildElement(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        return (Element) nodeList.item(0);
    }

    private static String getElementTextContent(Element element) throws DepositParseException {
        String content = element.getTextContent();
        if (content != null && !content.isEmpty()) {
            return content;
        } else {
            throw new DepositParseException("No content found in element");
        }
    }
}
