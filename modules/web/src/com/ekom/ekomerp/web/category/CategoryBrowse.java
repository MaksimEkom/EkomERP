package com.ekom.ekomerp.web.category;

import com.ekom.ekomerp.entity.Category;
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

public class CategoryBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Category} records
     * to be displayed in {@link CategoryBrowse#categoriesTable} on the left
     */
    @Inject
    private CollectionDatasource<Category, UUID> categoriesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link CategoryBrowse#categoriesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link CategoryBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Category> categoryDs;

    /**
     * The {@link Table} instance, containing a list of {@link Category} records,
     * loaded via {@link CategoryBrowse#categoriesDs}
     */
    @Inject
    private Table<Category> categoriesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link CategoryBrowse#categoryDs}
     * and shows fields of the selected {@link Category} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link CategoryBrowse#categoriesTable}
     */
    @Named("categoriesTable.remove")
    private RemoveAction categoriesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Category} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /**
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link categoriesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link categoryDs}
         */
        categoriesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Category reloadedItem = dataSupplier.reload(e.getDs().getItem(), categoryDs.getView());
                categoryDs.setItem(reloadedItem);
            }
        });

        /**
         * Adding {@link CreateAction} to {@link categoriesTable}
         * The listener removes selection in {@link categoriesTable}, sets a newly created item to {@link categoryDs}
         * and enables controls for record editing
         */
        categoriesTable.addAction(new CreateAction(categoriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                categoriesTable.setSelected(Collections.emptyList());
                categoryDs.setItem((Category) newItem);
                enableEditControls(true);
            }
        });

        /**
         * Adding {@link EditAction} to {@link categoriesTable}
         * The listener enables controls for record editing
         */
        categoriesTable.addAction(new EditAction(categoriesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (categoriesTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        /**
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link categoriesTableRemove}
         * to reset record, contained in {@link categoryDs}
         */
        categoriesTableRemove.setAfterRemoveHandler(removedItems -> categoryDs.setItem(null));

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void save() {
        getDsContext().commit();

        Category editedItem = categoryDs.getItem();
        if (creating) {
            categoriesDs.includeItem(editedItem);
        } else {
            categoriesDs.updateItem(editedItem);
        }
        categoriesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void cancel() {
        Category selectedItem = categoriesDs.getItem();
        if (selectedItem != null) {
            Category reloadedItem = dataSupplier.reload(selectedItem, categoryDs.getView());
            categoriesDs.setItem(reloadedItem);
        } else {
            categoryDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Category} is being created
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
        categoriesTable.requestFocus();
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