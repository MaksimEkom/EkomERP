package com.ekom.ekomerp.web.paymentcondition;

import com.ekom.ekomerp.entity.PaymentCondition;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class PaymentConditionBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link PaymentCondition} records
     * to be displayed in {@link PaymentConditionBrowse#paymentConditionsTable} on the left
     */
    @Inject
    private CollectionDatasource<PaymentCondition, UUID> paymentConditionsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link PaymentConditionBrowse#paymentConditionsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link PaymentConditionBrowse#init(Map)} method
     */
    @Inject
    private Datasource<PaymentCondition> paymentConditionDs;

    /**
     * The {@link Table} instance, containing a list of {@link PaymentCondition} records,
     * loaded via {@link PaymentConditionBrowse#paymentConditionsDs}
     */
    @Inject
    private Table<PaymentCondition> paymentConditionsTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link PaymentConditionBrowse#paymentConditionDs}
     * and shows fields of the selected {@link PaymentCondition} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link PaymentConditionBrowse#paymentConditionsTable}
     */
    @Named("paymentConditionsTable.remove")
    private RemoveAction paymentConditionsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link PaymentCondition} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link paymentConditionsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link paymentConditionDs}
         */
        paymentConditionsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                PaymentCondition reloadedItem = dataSupplier.reload(e.getDs().getItem(), paymentConditionDs.getView());
                paymentConditionDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link paymentConditionsTable}
         * The listener removes selection in {@link paymentConditionsTable}, sets a newly created item to {@link paymentConditionDs}
         * and enables controls for record editing
         */
        paymentConditionsTable.addAction(new CreateAction(paymentConditionsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                paymentConditionsTable.setSelected(Collections.emptyList());
                paymentConditionDs.setItem((PaymentCondition) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link paymentConditionsTable}
         * The listener enables controls for record editing
         */
        paymentConditionsTable.addAction(new EditAction(paymentConditionsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (paymentConditionsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link paymentConditionsTableRemove}
         * to reset record, contained in {@link paymentConditionDs}
         */
        paymentConditionsTableRemove.setAfterRemoveHandler(removedItems -> paymentConditionDs.setItem(null));

        disableEditControls();
    }

    private void refreshOptionsForLookupFields() {
        for (Component component : fieldGroup.getOwnComponents()) {
            if (component instanceof LookupField) {
                CollectionDatasource optionsDatasource = ((LookupField) component).getOptionsDatasource();
                if (optionsDatasource != null) {
                    optionsDatasource.refresh();
                }
            }
        }
    }

    /**
     * Method that is invoked by clicking Ok button after editing an existing or creating a new record
     */
    public void save() {
        if (!validate(Collections.singletonList(fieldGroup))) {
            return;
        }
        getDsContext().commit();

        PaymentCondition editedItem = paymentConditionDs.getItem();
        if (creating) {
            paymentConditionsDs.includeItem(editedItem);
        } else {
            paymentConditionsDs.updateItem(editedItem);
        }
        paymentConditionsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        PaymentCondition selectedItem = paymentConditionsDs.getItem();
        if (selectedItem != null) {
            PaymentCondition reloadedItem = dataSupplier.reload(selectedItem, paymentConditionDs.getView());
            paymentConditionsDs.setItem(reloadedItem);
        } else {
            paymentConditionDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link PaymentCondition} is being created
     */
    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    /**
     * Disabling editing controls
     */
    private void disableEditControls() {
        initEditComponents(false);
        paymentConditionsTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}