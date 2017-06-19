package com.ekom.ekomerp.web.calculation;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Calculation;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import java.util.Map;

public class CalculationEdit extends AbstractEditor<Calculation> {


    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private TextField BGSTextField;
    @Inject
    private TextField FSZNTextField;
    @Inject
    private TextField commercialExpensesTextField;
    @Inject
    private TextField productionExpensesTextField;
    @Inject
    private TextField profitTextField;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
    }

    @Override
    protected boolean preCommit() {
        setNumberField();
        return super.preCommit();
    }

    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("CalculationNumber");
    }

    private void setNumberField(){
        if (getItem().getNumber().equals("Новый")){
            String number = "Кл-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
            getItem().setNumber(number);
        }
    }
}