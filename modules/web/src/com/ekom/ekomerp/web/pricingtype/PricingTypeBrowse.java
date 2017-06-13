package com.ekom.ekomerp.web.pricingtype;

import com.ekom.ekomerp.entity.PricingType;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.EntityOp;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class PricingTypeBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link PricingType} records
     * to be displayed in {@link PricingTypeBrowse#pricingTypesTable} on the left
     */
    @Inject
    private CollectionDatasource<PricingType, UUID> pricingTypesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link PricingTypeBrowse#pricingTypesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link PricingTypeBrowse#init(Map)} method
     */
    @Inject
    private Datasource<PricingType> pricingTypeDs;

    /**
     * The {@link Table} instance, containing a list of {@link PricingType} records,
     * loaded via {@link PricingTypeBrowse#pricingTypesDs}
     */
    @Inject
    private Table<PricingType> pricingTypesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link PricingTypeBrowse#pricingTypeDs}
     * and shows fields of the selected {@link PricingType} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link PricingTypeBrowse#pricingTypesTable}
     */
    @Named("pricingTypesTable.remove")
    private RemoveAction pricingTypesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link PricingType} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link pricingTypesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link pricingTypeDs}
         */
        pricingTypesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                PricingType reloadedItem = dataSupplier.reload(e.getDs().getItem(), pricingTypeDs.getView());
                pricingTypeDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link pricingTypesTable}
         * The listener removes selection in {@link pricingTypesTable}, sets a newly created item to {@link pricingTypeDs}
         * and enables controls for record editing
         */
        pricingTypesTable.addAction(new CreateAction(pricingTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                pricingTypesTable.setSelected(Collections.emptyList());
                pricingTypeDs.setItem((PricingType) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link pricingTypesTable}
         * The listener enables controls for record editing
         */
        pricingTypesTable.addAction(new EditAction(pricingTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (pricingTypesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }

            @Override
            public void refreshState() {
                if (target != null) {
                    CollectionDatasource ds = target.getDatasource();
                    if (ds != null && !captionInitialized) {
                        setCaption(messages.getMainMessage("actions.Edit"));
                    }
                }
                super.refreshState();
            }

            @Override
            protected boolean isPermitted() {
                CollectionDatasource ownerDatasource = target.getDatasource();
                boolean entityOpPermitted = security.isEntityOpPermitted(ownerDatasource.getMetaClass(), EntityOp.UPDATE);
                if (!entityOpPermitted) {
                    return false;
                }
                return super.isPermitted();
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link pricingTypesTableRemove}
         * to reset record, contained in {@link pricingTypeDs}
         */
        pricingTypesTableRemove.setAfterRemoveHandler(removedItems -> pricingTypeDs.setItem(null));

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

        PricingType editedItem = pricingTypeDs.getItem();
        if (creating) {
            pricingTypesDs.includeItem(editedItem);
        } else {
            pricingTypesDs.updateItem(editedItem);
        }
        pricingTypesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        PricingType selectedItem = pricingTypesDs.getItem();
        if (selectedItem != null) {
            PricingType reloadedItem = dataSupplier.reload(selectedItem, pricingTypeDs.getView());
            pricingTypesDs.setItem(reloadedItem);
        } else {
            pricingTypeDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link PricingType} is being created
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
        pricingTypesTable.requestFocus();
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