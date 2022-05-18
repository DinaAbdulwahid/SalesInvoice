
package com.project.model;

//handle data from invoice line csv file(numberLine,itemName,itemPrice,itemCount,itemTotal)
public class InvoiceItem {
    //private int numberLine;
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private InvoiceHeader inv;

    public InvoiceItem() {
    }

    public InvoiceItem(String itemName, double itemPrice, int itemCount, InvoiceHeader inv) {
        //this.numberLine = numberLine;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.inv = inv;
    }
    
    
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    public double getItemTotal()
    {
        return itemPrice * itemCount;
    }
    @Override
    public String toString() {
        return "InvoiceItem{" + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemCount=" + itemCount + ", inv=" + inv + '}';
    }
    
    public InvoiceHeader getInv() {
        return inv;
    }

    public void setInv(InvoiceHeader inv) {
        this.inv = inv;
    }
    
    public String getAsCSV()
    { 
        return inv.getInvoiceNumber()+ "," + itemName + "," + itemPrice + "," + itemCount;
    }
    
    
}
