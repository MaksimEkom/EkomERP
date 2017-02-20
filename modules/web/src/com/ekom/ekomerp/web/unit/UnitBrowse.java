package com.ekom.ekomerp.web.unit;

import com.ekom.ekomerp.entity.Unit;
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

public class UnitBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Unit} records
     * to be displayed in {@link UnitBrowse#unitsTable} on the left
     */
    @Inject
    private CollectionDatasource<Unit, UUID> unitsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link UnitBrowse#unitsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link UnitBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Unit> unitDs;

    /**
     * The {@link Table} instance, containing a list of {@link Unit} records,
     * loaded via {@link UnitBrowse#unitsDs}
     */
    @Inject
    private Table<Unit> unitsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link UnitBrowse#unitDs}
     * and shows fields of the selected {@link Unit} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link UnitBrowse#unitsTable}
     */
    @Named("unitsTable.remove")
    private RemoveAction unitsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Unit} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /**
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link unitsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link unitDs}
         */
        unitsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Unit reloadedItem = dataSupplier.reload(e.getDs().getItem(), unitDs.getView());
                unitDs.setItem(reloadedItem);
            }
        });

        /**
         * Adding {@link CreateAction} to {@link unitsTable}
         * The listener removes selection in {@link unitsTable}, sets a newly created item to {@link unitDs}
         * and enables controls for record editing
         */
        unitsTable.addAction(new CreateAction(unitsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                unitsTable.setSelected(Collections.emptyList());
                unitDs.setItem((Unit) newItem);
                enableEditControls(true);
            }
        });

        /**
         * Adding {@link EditAction} to {@link unitsTable}
         * The listener enables controls for record editing
         */
        unitsTable.addAction(new EditAction(unitsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (unitsTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        /**
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link unitsTableRemove}
         * to reset record, contained in {@link unitDs}
         */
        unitsTableRemove.setAfterRemoveHandler(removedItems -> unitDs.setItem(null));

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void save() {
        getDsContext().commit();

        Unit editedItem = unitDs.getItem();
        if (creating) {
            unitsDs.includeItem(editedItem);
        } else {
            unitsDs.updateItem(editedItem);
        }
        unitsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void cancel() {
        Unit selectedItem = unitsDs.getItem();
        if (selectedItem != null) {
            Unit reloadedItem = dataSupplier.reload(selectedItem, unitDs.getView());
            unitsDs.setItem(reloadedItem);
        } else {
            unitDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Unit} is being created
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
        unitsTable.requestFocus();
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