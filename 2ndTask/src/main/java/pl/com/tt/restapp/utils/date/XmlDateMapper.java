package pl.com.tt.restapp.utils.date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.com.tt.restapp.exceptionservice.customexceptions.CustomDateParserException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XmlDateMapper {

    public static XMLGregorianCalendar localDateToXmlGregorianConverter(LocalDate localDate) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
        } catch (DatatypeConfigurationException e) {
            throw new CustomDateParserException();
        }
    }

    public static LocalDate xmlGregorianToLocalDateConverter(XMLGregorianCalendar xmlDate) {
        int year = xmlDate.getYear();
        int month = xmlDate.getMonth();
        int day = xmlDate.getDay();
        return LocalDate.of(year, month, day);
    }
}

