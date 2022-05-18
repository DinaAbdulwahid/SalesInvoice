package com.project.controller;

import com.project.model.InvoiceHeader;
import com.project.model.InvoiceItem;
import com.project.model.ItemTableModel;
import com.project.view.InvoiceDialog;
import com.project.view.InvoiceScreen;
import com.project.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener{
    
        private InvoiceScreen screen;
        private InvoiceDialog invoiceDialog;
        private LineDialog itemDialog;
        

    public ButtonController(InvoiceScreen frame) {
        this.screen = frame;
    }
        
        
     
      public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        //System.out.println("Action Done");
        
        if(actionCommand == "Create New Invoice")
        {
            createNewInvoice();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "Delete Invoice")
        {
            deleteInvoice();
            System.out.println("Action Done" + actionCommand);
        }
        /*save means create new item related to invoice*/
        else if(actionCommand == "Save")
        {
            createNewItem();
            System.out.println("Action Done" + actionCommand);
        }
           /*Cancel means delete item*/
        else if(actionCommand == "Cancel")
        {
            deleteItem();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "createInvoiceOK")
        {
            createInvoiceOK();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "createInvoiceCancel")
        {
            createInvoiceCancel();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "createLineOK")
        {
            createLineOK();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "createLineCancel")
        {
            createLineCancel();
            System.out.println("Action Done" + actionCommand);
        }
        else {
         System.out.println("WRONG ACTION!!!!");
        } 
       
    }

    private void deleteInvoice() {
        int SelectedRow = screen.getHeaderTable().getSelectedRow();
        if(SelectedRow != -1){
          screen.getInvoices().remove(SelectedRow);
          screen.getHeaderTableModel().fireTableDataChanged();
        }
        
    }

    private void deleteItem() {
        int selectedInvoice = screen.getHeaderTable().getSelectedRow();
        int selectedRow = screen.getLineTable().getSelectedRow();
        
        if(selectedInvoice != -1 && selectedRow != -1){
          ItemTableModel lineTableModel = (ItemTableModel) screen.getLineTable().getModel();
          lineTableModel.getItems().remove(selectedRow);
          lineTableModel.fireTableDataChanged();
        }
        
    }

    private void createInvoiceOK() {
        String createdDate = invoiceDialog.getInvDateField().getText();
        String createdCustomer = invoiceDialog.getCustNameField().getText();
        int createdNumber = screen.getNextInvoiceNum();
        
        InvoiceHeader header = new InvoiceHeader(createdNumber, createdDate, createdCustomer);
        screen.getInvoices().add(header);
        screen.getHeaderTableModel().fireTableDataChanged();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
        
    }

    private void createLineOK() {
        String itemName = itemDialog.getItemNameField().getText();
        String countStr = itemDialog.getItemCountField().getText();
        String priceStr = itemDialog.getItemPriceField().getText();
        int itemCount = Integer.parseInt(countStr);
        double itemPrice = Double.parseDouble(priceStr);
        int selectedInvoice = screen.getHeaderTable().getSelectedRow();
        if(selectedInvoice != -1)
        { 
            InvoiceHeader header = screen.getInvoices().get(selectedInvoice);
            InvoiceItem item = new InvoiceItem(itemName, itemPrice, itemCount, header);
            header.getItems().add(item);
            ItemTableModel ItemsTableModel = (ItemTableModel) screen.getLineTable().getModel();
            ItemsTableModel.fireTableDataChanged();
            screen.getHeaderTableModel().fireTableDataChanged();

        }
        
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

    private void createLineCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
        
    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceDialog(screen);
        invoiceDialog.setVisible(true);
    }

    private void createNewItem() {
        itemDialog = new LineDialog(screen);
        itemDialog.setVisible(true);
    }
    
}
