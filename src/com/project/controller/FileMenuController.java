
package com.project.controller;

import com.project.model.HeaderTableModel;
import com.project.model.InvoiceHeader;
import com.project.model.InvoiceItem;
import com.project.view.InvoiceScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FileMenuController implements ActionListener{
    
    private InvoiceScreen screen;
    
    
    
    public FileMenuController (InvoiceScreen frame) {
        this.screen = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action Done");
        
        if(actionCommand == "Load File")
        {
            loadFile();
            System.out.println("Action Done" + actionCommand);
        }
        else if(actionCommand == "Save File")
        {
            saveFile();
            System.out.println("Action Done" + actionCommand);
        }
        else {
         System.out.println("WRONG ACTION!!!!");
        } 
        /*
        switch(actionCommand) 
        {
            case "Load File":
                loadFile();
            break;
            case "Save File":
                saveFile();
            break;    
        
        } */
    }

    
    
    private void loadFile() {
        JOptionPane.showMessageDialog(screen, "Please select header(invoice) file!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        try {
        int openDialog = fileChooser.showOpenDialog(screen);
        if(openDialog == JFileChooser.APPROVE_OPTION)
        { 
            File invHeader = fileChooser.getSelectedFile();
            Path headerPath = Paths.get(invHeader.getAbsolutePath());
            List<String> headerLines = Files.readAllLines(headerPath);
            System.out.println("invoice header file has been done");
            
            ArrayList<InvoiceHeader> headersArray = new ArrayList<>();
            for(String invoiceLine:headerLines)
            {
               String [] invoiceparts = invoiceLine.split(",");
               int invnum = Integer.parseInt(invoiceparts[0]); //invoice number
               String invDate = invoiceparts[1]; //invoicedate
               String custName = invoiceparts[2]; //customername
               
               InvoiceHeader header = new InvoiceHeader(invnum,invDate,custName);
               headersArray.add(header);
            }
            
            System.out.println("Check Point");
            JOptionPane.showMessageDialog(screen, "Please select Items file!", "Attension", JOptionPane.WARNING_MESSAGE);
            openDialog = fileChooser.showOpenDialog(screen);
            if(openDialog == JFileChooser.APPROVE_OPTION) {
                
            File invItem = fileChooser.getSelectedFile();
            Path itemsPath = Paths.get(invItem.getAbsolutePath());
            List<String> itemLines = Files.readAllLines(itemsPath);
            System.out.println("invoice Items file has been done");
            
            List<InvoiceItem> AllItems = new ArrayList<InvoiceItem>();;
            
            for(String itemLine:itemLines)
            {
               String [] itemparts = itemLine.split(",");
               int itemnum = Integer.parseInt(itemparts[0]); 
               String itemName = itemparts[1]; 
               double itemPrice = Double.parseDouble(itemparts[2]); 
               int itemCount = Integer.parseInt(itemparts[3]);
               
               InvoiceHeader invhead = null;
               for(InvoiceHeader invoice : headersArray){
                   if(invoice.getInvoiceNumber() == itemnum){
                      invhead = invoice;
                      break;
                   }
                   
               }
               InvoiceItem items = new InvoiceItem(itemName,itemPrice,itemCount,invhead);
               invhead.getItems().add(items);
               /*
               if(LastNumberLine != items.getNumberLine()){
                   System.out.println("Invoice "+items.getNumberLine());
                   System.out.println("{");
                   System.out.println(invhead.getInvoiceDate() + "," + invhead.getCustomerName());
                   LastNumberLine = items.getNumberLine();
               }

               System.out.println(items.getItemName()+  "," + items.getItemPrice() +  "," + items.getItemCount());
               System.out.println("}");*/
               AllItems.add(items);
            }
            System.out.println("Items are ready");
            // Test print!
            
            int LastNumberLine = 0;
            for(InvoiceItem OneItem : AllItems){
                if(LastNumberLine != OneItem.getInv().getInvoiceNumber()){ //Now we have a new Invoice number!
                    System.out.println("Invoice " + OneItem.getInv().getInvoiceNumber());
                    System.out.println("{");
                    System.out.println(OneItem.getInv().getInvoiceDate() + "," + OneItem.getInv().getCustomerName());

                    // Print all items per this invoice   
                    for(InvoiceItem SubItem : AllItems){
                        if(SubItem.getInv().getInvoiceNumber() == OneItem.getInv().getInvoiceNumber()){
                            System.out.println(SubItem.getItemName()+  "," + SubItem.getItemPrice() +  "," + SubItem.getItemCount());
                        }
                    }

                    System.out.println("}");
                    LastNumberLine = OneItem.getInv().getInvoiceNumber();
                }
            }
                    
            
            }
            
            screen.setInvoices(headersArray);
            HeaderTableModel headerTableModel = new HeaderTableModel(headersArray);
            screen.setHeaderTableModel(headerTableModel);
            screen.getHeaderTable().setModel(headerTableModel);
            screen.getHeaderTableModel().fireTableDataChanged();
        }
        } catch(IOException ex){
           ex.printStackTrace();
        }
  
    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = screen.getInvoices();
        String invoiceHeaders = "";
        String itemLines ="";
        for(InvoiceHeader invoice : invoices)
        {
           String invCSV = invoice.getAsCSV();
           invoiceHeaders += invCSV;
           invoiceHeaders += "\n";
           
        for(InvoiceItem line : invoice.getItems())
        {
           String lineCSV = line.getAsCSV();
           itemLines += lineCSV;
           itemLines += "\n"; 
        }
        }
        try {
           JFileChooser fc = new JFileChooser();
           int saveresult = fc.showSaveDialog(screen);
           if(saveresult == JFileChooser.APPROVE_OPTION){
           File headerFile = fc.getSelectedFile();
           FileWriter headerfw = new FileWriter(headerFile);
           headerfw.write(invoiceHeaders);
           headerfw.flush();
           headerfw.close();
           saveresult = fc.showSaveDialog(screen);
           if(saveresult == JFileChooser.APPROVE_OPTION){
           File lineFile = fc.getSelectedFile();
           FileWriter linefw = new FileWriter(lineFile);
           linefw.write(itemLines);
           linefw.flush();
           linefw.close();
           }
        }    
    }catch(Exception ex){
           
    }
    
    }  
}
