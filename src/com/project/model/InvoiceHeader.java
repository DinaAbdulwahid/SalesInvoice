
package com.project.model;

//handle Date from Invoice Header csv file (invoiceNumber,invoiceDate,customerName)

import java.util.ArrayList;

public class InvoiceHeader {
    private int invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceItem> Items;
    
    public InvoiceHeader() {
    }

    public InvoiceHeader(int invoiceNumber, String invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
     public double getInvoiceTotal()
     {
       double total = 0;
       for(InvoiceItem item: getItems()){
       total += item.getItemTotal();
       }
       return total;
     }
    @Override
    public String toString() {
        return "InvoiceHeader{" + "invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
    }

    public ArrayList<InvoiceItem> getItems() {
        if(Items == null){
             Items = new ArrayList<>();
        }
        return Items;
    }

    public void setItems(ArrayList<InvoiceItem> Items) {
        
        this.Items = Items;
    }
    
    
    public String getAsCSV()
    { 
        return invoiceNumber + "," + invoiceDate + "," + customerName;
    }
    
}
