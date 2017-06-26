package com.ekom.ekomerp;

import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.chile.core.datatypes.FormatStrings;
import com.haulmont.chile.core.datatypes.impl.BigDecimalDatatype;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Sergey on 26.06.2017.
 */
public class FourDigitsScaleBigDecimal extends BigDecimalDatatype {


    public static final String NAME = "fourDigitsScaleBigDecimal";
    public static final String FORMAT = "#0.0000";

    public FourDigitsScaleBigDecimal(Element element) {
        super(element);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String format(Object value, Locale locale) {
        if (value == null)
            return "";
        FormatStrings formatStrings = Datatypes.getFormatStrings(locale);
        if (formatStrings == null)
            return format(value);
        NumberFormat format = new DecimalFormat(FORMAT, formatStrings.getFormatSymbols());
        return format.format(value);
    }

    @Override
    public BigDecimal parse(String value, Locale locale) throws ParseException {
        if (StringUtils.isBlank(value))
            return null;
        FormatStrings formatStrings = Datatypes.getFormatStrings(locale);
        if (formatStrings == null)
            return parse(value);

        NumberFormat format = new DecimalFormat(FORMAT, formatStrings.getFormatSymbols());
        return BigDecimal.valueOf(parse(value, format).doubleValue());
    }
}
