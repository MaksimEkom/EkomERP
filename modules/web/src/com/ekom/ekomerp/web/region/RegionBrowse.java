package com.ekom.ekomerp.web.region;

import com.ekom.ekomerp.entity.Region;
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

public class RegionBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Region} records
     * to be displayed in {@link RegionBrowse#regionsTable} on the left
     */
    @Inject
    private CollectionDatasource<Region, UUID> regionsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link RegionBrowse#regionsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link RegionBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Region> regionDs;

    /**
     * The {@link Table} instance, containing a list of {@link Region} records,
     * loaded via {@link RegionBrowse#regionsDs}
     */
    @Inject
    private Table<Region> regionsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link RegionBrowse#regionDs}
     * and shows fields of the selected {@link Region} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link RegionBrowse#regionsTable}
     */
    @Named("regionsTable.remove")
    private RemoveAction regionsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Region} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link regionsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link regionDs}
         */
        regionsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Region reloadedItem = dataSupplier.reload(e.getDs().getItem(), regionDs.getView());
                regionDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link regionsTable}
         * The listener removes selection in {@link regionsTable}, sets a newly created item to {@link regionDs}
         * and enables controls for record editing
         */
        regionsTable.addAction(new CreateAction(regionsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                regionsTable.setSelected(Collections.emptyList());
                regionDs.setItem((Region) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link regionsTable}
         * The listener enables controls for record editing
         */
        regionsTable.addAction(new EditAction(regionsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (regionsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link regionsTableRemove}
         * to reset record, contained in {@link regionDs}
         */
        regionsTableRemove.setAfterRemoveHandler(removedItems -> regionDs.setItem(null));

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

        Region editedItem = regionDs.getItem();
        if (creating) {
            regionsDs.includeItem(editedItem);
        } else {
            regionsDs.updateItem(editedItem);
        }
        regionsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        Region selectedItem = regionsDs.getItem();
        if (selectedItem != null) {
            Region reloadedItem = dataSupplier.reload(selectedItem, regionDs.getView());
            regionsDs.setItem(reloadedItem);
        } else {
            regionDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Region} is being created
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
        regionsTable.requestFocus();
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