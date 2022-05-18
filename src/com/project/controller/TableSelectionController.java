
package com.project.controller;

import com.project.model.InvoiceHeader;
import com.project.model.ItemTableModel;
import com.project.view.InvoiceScreen;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionController implements ListSelectionListener{
    private InvoiceScreen screen;
    
    
    
    public TableSelectionController (InvoiceScreen frame) {
        this.screen = frame;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
       int selectedIndex = screen.getHeaderTable().getSelectedRow();
       if(selectedIndex != -1){
       System.out.println("you have selected row :"+ selectedIndex);
       InvoiceHeader currentInvoice = screen.getInvoices().get(selectedIndex);
       screen.getNumberLabel().setText(""+currentInvoice.getInvoiceNumber());
       screen.getDatelabel().setText(currentInvoice.getInvoiceDate());
       screen.getNamelabel().setText(currentInvoice.getCustomerName());
       screen.getTotalLabel().setText(""+currentInvoice.getInvoiceTotal());
       ItemTableModel itemTableModel = new ItemTableModel(currentInvoice.getItems());
       screen.getLineTable().setModel(itemTableModel);
       itemTableModel.fireTableDataChanged();
       
       }
       
    }
    
}
