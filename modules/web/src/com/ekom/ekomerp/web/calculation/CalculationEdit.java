package com.ekom.ekomerp.web.calculation;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.app.PersistenceManagerService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private EntityManager entityManager;
    @Inject
    private Persistence persistence;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionDatasource<CalculationMaterialLine, UUID> consumptionLineDs;
    @Inject
    private LookupPickerField productPickerField;
    @Inject
    private CollectionDatasource<CalculationLaboriousnessLine, UUID> laboriousLineDs;
    @Inject
    private Table<CalculationMaterialLine> consumptionTable;
    @Inject
    private Table<CalculationLaboriousnessLine> laborTable;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private DatatypeFormatter formatter;

    @Override
    public void init(Map<String, Object> params) {
        consumptionTable.addGeneratedColumn("totalLinePrice", new Table.ColumnGenerator<CalculationMaterialLine>() {
            @Override
            public Component generateCell(CalculationMaterialLine entity) {
                Label label = (Label) componentsFactory.createComponent(Label.NAME);
                label.setValue(entity.getPrice()*entity.getQuantity());
                label.setEditable(false);
                return label;
            }
        });
        laborTable.addGeneratedColumn("totalLaborPrice", new Table.ColumnGenerator<CalculationLaboriousnessLine>() {
            @Override
            public Component generateCell(CalculationLaboriousnessLine entity) {
                Label label = (Label) componentsFactory.createComponent(Label.NAME);
                label.setValue(entity.getPrice()*entity.getValue());
                label.setEditable(false);
                return label;
            }
        });
        productPickerField.addValueChangeListener(e -> {
            setConsumptionDataSource(getConsumptionSet(productPickerField.getValue()));
            setLaboriousDataSource(getLaboriousnessSet(productPickerField.getValue()));
            materialSumCount();
            laborSumCount();
//            productionExpensesCount();
            fsznCount();
            bgsCount();
        });
        consumptionLineDs.addItemPropertyChangeListener(e -> {
            if(e.getProperty().equals("price")){
                materialSumCount();
                consumptionTable.repaint();
            }
        });
        laboriousLineDs.addItemPropertyChangeListener(e -> {
            if(e.getProperty().equals("price")){
                laborSumCount();
                laborTable.repaint();
//                productionExpensesCount();
                fsznCount();
                bgsCount();
            }
        });
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
            String number = "КЛ-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
            getItem().setNumber(number);
        }
    }

    private Set<Consumption> getConsumptionSet(Product product){
        Set consumptionSet;
        if (product.getConsumption()!=null){
            consumptionSet = product.getConsumption();
        }else {
            LoadContext<Consumption> loadContext = LoadContext.create(Consumption.class)
                    .setQuery(LoadContext.createQuery("select c from ekomerp$Consumption c where c.product.code = :analogCode")
                            .setParameter("analogCode", product.getAnalog()))
                    .setView("consumption-edit");
            consumptionSet = (Set) dataManager.loadList(loadContext);
        }
        return consumptionSet;
    }

    private Set<Laboriousness> getLaboriousnessSet(Product product){
        Set laboriousnessSet;
        if (product.getLaboriousness()!=null){
            laboriousnessSet = product.getLaboriousness();
        }else {
            LoadContext<Laboriousness> loadContext = LoadContext.create(Laboriousness.class)
                    .setQuery(LoadContext.createQuery("select l from ekomerp$Laboriousness l where l.product.code = :analogCode")
                            .setParameter("analogCode", product.getAnalog()))
                    .setView("laboriousness-edit");
            laboriousnessSet = (Set) dataManager.loadList(loadContext);
        }
        return laboriousnessSet;
    }

    private void setLaboriousDataSource(Set<Laboriousness> laboriousnessSet){
        laboriousLineDs.clear();
        if(laboriousnessSet!=null){
            for (Laboriousness laboriousness:laboriousnessSet) {
                CalculationLaboriousnessLine calculationLaboriousnessLine = new CalculationLaboriousnessLine();
                calculationLaboriousnessLine.setCalculation(getItem());
                calculationLaboriousnessLine.setOperation(laboriousness.getOperation());
                calculationLaboriousnessLine.setPrice(3.2);
                calculationLaboriousnessLine.setValue(laboriousness.getValue());
                laboriousLineDs.addItem(calculationLaboriousnessLine);
            }
        }

    }

    private void setConsumptionDataSource(Set<Consumption> consumptionSet){
        consumptionLineDs.clear();
        if(consumptionSet!=null){
            for (Consumption consumption:consumptionSet) {
                CalculationMaterialLine calculationMaterialLine = new CalculationMaterialLine();
                calculationMaterialLine.setCalculation(getItem());
                calculationMaterialLine.setMaterial(consumption.getConsumableProduct());
                calculationMaterialLine.setQuantity(consumption.getQuantity());
                calculationMaterialLine.setPrice(0.0);
                consumptionLineDs.addItem(calculationMaterialLine);
            }
        }

    }
    private void materialSumCount(){
        BigDecimal materialSum = BigDecimal.ZERO;
        for (CalculationMaterialLine calculationMaterialLine:consumptionLineDs.getItems()) {
            materialSum = materialSum.add(new BigDecimal(calculationMaterialLine.getPrice()*calculationMaterialLine.getQuantity()));
        }
        getItem().setMaterialSum(materialSum);
    }

    private void laborSumCount(){
        BigDecimal laborSum = BigDecimal.ZERO;
        for (CalculationLaboriousnessLine calculationLaboriousnessLine:laboriousLineDs.getItems()) {
            laborSum = laborSum.add(new BigDecimal(calculationLaboriousnessLine.getPrice()*calculationLaboriousnessLine.getValue()).setScale(3,RoundingMode.HALF_UP));
        }
        getItem().setLaborSum(laborSum.setScale(3, RoundingMode.HALF_UP));
    }
    private void fsznCount(){
        getItem().setFszn(getItem().getLaborSum().multiply(getItem().getFsznRate().divide(BigDecimal.valueOf(100.0))));
    }
    private void bgsCount(){
        getItem().setBgs(getItem().getLaborSum().multiply(getItem().getBgsRate().divide(new BigDecimal(100.0))));
    }
//    private void productionExpensesCount(){
//        Double productionExpenses = 0.0;
//        getItem().setProductionExpenses(getItem().getLaborSum()*getItem().getProductionExpensesRate()/100);
//    }
}