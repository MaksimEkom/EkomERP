package com.ekom.ekomerp.web.inventory;

import com.ekom.ekomerp.entity.Inventory;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class InventoryBrowseEdit extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Inventory} records
     * to be displayed in {@link InventoryBrowse#inventoriesTable} on the left
     */
    @Inject
    private CollectionDatasource<Inventory, UUID> inventoriesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link InventoryBrowse#inventoriesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link InventoryBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Inventory> inventoryDs;

    /**
     * The {@link Table} instance, containing a list of {@link Inventory} records,
     * loaded via {@link InventoryBrowse#inventoriesDs}
     */
    @Inject
    private Table<Inventory> inventoriesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link InventoryBrowse#inventoryDs}
     * and shows fields of the selected {@link Inventory} record
     */
    @Inject
    private FieldGroup fieldGroup;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Inventory} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link inventoriesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link inventoryDs}
         */
        inventoriesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Inventory reloadedItem = dataSupplier.reload(e.getDs().getItem(), inventoryDs.getView());
                inventoryDs.setItem(reloadedItem);
            }
        });


        /*
         * Adding {@link EditAction} to {@link inventoriesTable}
         * The listener enables controls for record editing
         */
        inventoriesTable.addAction(new EditAction(inventoriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (inventoriesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

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

        Inventory editedItem = inventoryDs.getItem();
        if (creating) {
            inventoriesDs.includeItem(editedItem);
        } else {
            inventoriesDs.updateItem(editedItem);
        }
        inventoriesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        Inventory selectedItem = inventoriesDs.getItem();
        if (selectedItem != null) {
            Inventory reloadedItem = dataSupplier.reload(selectedItem, inventoryDs.getView());
            inventoriesDs.setItem(reloadedItem);
        } else {
            inventoryDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Inventory} is being created
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
        inventoriesTable.requestFocus();
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