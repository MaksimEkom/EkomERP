package com.ekom.ekomerp.web.operation;

import com.ekom.ekomerp.entity.Operation;
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

public class OperationBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Operation} records
     * to be displayed in {@link OperationBrowse#operationsTable} on the left
     */
    @Inject
    private CollectionDatasource<Operation, UUID> operationsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link OperationBrowse#operationsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link OperationBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Operation> operationDs;

    /**
     * The {@link Table} instance, containing a list of {@link Operation} records,
     * loaded via {@link OperationBrowse#operationsDs}
     */
    @Inject
    private Table<Operation> operationsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link OperationBrowse#operationDs}
     * and shows fields of the selected {@link Operation} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link OperationBrowse#operationsTable}
     */
    @Named("operationsTable.remove")
    private RemoveAction operationsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Operation} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /**
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link operationsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link operationDs}
         */
        operationsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Operation reloadedItem = dataSupplier.reload(e.getDs().getItem(), operationDs.getView());
                operationDs.setItem(reloadedItem);
            }
        });

        /**
         * Adding {@link CreateAction} to {@link operationsTable}
         * The listener removes selection in {@link operationsTable}, sets a newly created item to {@link operationDs}
         * and enables controls for record editing
         */
        operationsTable.addAction(new CreateAction(operationsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                operationsTable.setSelected(Collections.emptyList());
                operationDs.setItem((Operation) newItem);
                enableEditControls(true);
            }
        });

        /**
         * Adding {@link EditAction} to {@link operationsTable}
         * The listener enables controls for record editing
         */
        operationsTable.addAction(new EditAction(operationsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (operationsTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        /**
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link operationsTableRemove}
         * to reset record, contained in {@link operationDs}
         */
        operationsTableRemove.setAfterRemoveHandler(removedItems -> operationDs.setItem(null));

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void save() {
        getDsContext().commit();

        Operation editedItem = operationDs.getItem();
        if (creating) {
            operationsDs.includeItem(editedItem);
        } else {
            operationsDs.updateItem(editedItem);
        }
        operationsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void cancel() {
        Operation selectedItem = operationsDs.getItem();
        if (selectedItem != null) {
            Operation reloadedItem = dataSupplier.reload(selectedItem, operationDs.getView());
            operationsDs.setItem(reloadedItem);
        } else {
            operationDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Operation} is being created
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
        operationsTable.requestFocus();
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