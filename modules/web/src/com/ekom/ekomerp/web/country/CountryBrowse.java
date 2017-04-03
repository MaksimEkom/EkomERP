package com.ekom.ekomerp.web.country;

import com.ekom.ekomerp.entity.Country;
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

public class CountryBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Country} records
     * to be displayed in {@link CountryBrowse#countriesTable} on the left
     */
    @Inject
    private CollectionDatasource<Country, UUID> countriesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link CountryBrowse#countriesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link CountryBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Country> countryDs;

    /**
     * The {@link Table} instance, containing a list of {@link Country} records,
     * loaded via {@link CountryBrowse#countriesDs}
     */
    @Inject
    private Table<Country> countriesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link CountryBrowse#countryDs}
     * and shows fields of the selected {@link Country} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link CountryBrowse#countriesTable}
     */
    @Named("countriesTable.remove")
    private RemoveAction countriesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Country} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link countriesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link countryDs}
         */
        countriesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Country reloadedItem = dataSupplier.reload(e.getDs().getItem(), countryDs.getView());
                countryDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link countriesTable}
         * The listener removes selection in {@link countriesTable}, sets a newly created item to {@link countryDs}
         * and enables controls for record editing
         */
        countriesTable.addAction(new CreateAction(countriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                countriesTable.setSelected(Collections.emptyList());
                countryDs.setItem((Country) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link countriesTable}
         * The listener enables controls for record editing
         */
        countriesTable.addAction(new EditAction(countriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (countriesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link countriesTableRemove}
         * to reset record, contained in {@link countryDs}
         */
        countriesTableRemove.setAfterRemoveHandler(removedItems -> countryDs.setItem(null));

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

        Country editedItem = countryDs.getItem();
        if (creating) {
            countriesDs.includeItem(editedItem);
        } else {
            countriesDs.updateItem(editedItem);
        }
        countriesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        Country selectedItem = countriesDs.getItem();
        if (selectedItem != null) {
            Country reloadedItem = dataSupplier.reload(selectedItem, countryDs.getView());
            countriesDs.setItem(reloadedItem);
        } else {
            countryDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Country} is being created
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
        countriesTable.requestFocus();
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