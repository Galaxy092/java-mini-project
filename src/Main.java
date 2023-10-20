import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Util.Validation.*;

public class Main {
    private static Integer pageNumber = 1;
    private static Integer rowSize = 3;

    public static void main(String[] args) {
        // Scanner object
        Scanner sc = new Scanner(System.in);

        // Create ArrayList of Product object
        List<Product> productList = new ArrayList<>();

        // Static Data
        productList.add(new Product(1, "Coca", 2500.00, 10, LocalDate.now()));
        productList.add(new Product(2, "Pepsi", 2500.00, 20, LocalDate.now()));
        productList.add(new Product(3, "Sprite", 2000.00, 30, LocalDate.now()));
        productList.add(new Product(4, "Sting", 2000.00, 40, LocalDate.now()));

        System.out.println("""
                         ██████╗███████╗████████╗ █████╗ ██████╗          ██╗ █████╗ ██╗   ██╗ █████╗    \s
                        ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗         ██║██╔══██╗██║   ██║██╔══██╗   \s
                        ██║     ███████╗   ██║   ███████║██║  ██║         ██║███████║██║   ██║███████║   \s
                        ██║     ╚════██║   ██║   ██╔══██║██║  ██║    ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║   \s
                        ╚██████╗███████║   ██║   ██║  ██║██████╔╝    ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║   \s""");

        System.out.println("STOCK MANAGEMENT SYSTEM");
        outer:while(true) {
            int totalPage = (int) Math.ceil((double) productList.size() / rowSize);
            Table table = getTable();
            System.out.println(table.render());
            System.out.print("Command --> ");
            String opt = sc.nextLine().toLowerCase();

            switch (opt) {
                case "*", "display" -> {
                    displayTable(productList, pageNumber, rowSize);
                }

                case "w", "write" -> {
                    Product p = new Product();
                    String name, price, qty;
                    System.out.print("Product ID : ");
                    System.out.println(productList.size() + 1);

                    do {
                        System.out.print("Product's name : ");
                        name = sc.nextLine();
                    } while (name.isBlank());

                    do {
                        System.out.print("Product's Price : ");
                        price = sc.nextLine();
                    } while (!isDouble(price));

                    do {
                        System.out.print("Product's Qty : ");
                        qty = sc.nextLine();
                    } while(!isInteger(qty));

                    Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                    table1.addCell(" ID" + " ".repeat(14) + " : " + (productList.size() + 1));
                    table1.addCell(" Name" + " ".repeat(12) + " : " + name);
                    table1.addCell(" Unit Price" + " ".repeat(6) + " : " + Double.parseDouble(price));
                    table1.addCell(" Qty" + " ".repeat(13) + " : " + qty);
                    table1.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                    System.out.println();
                    System.out.println(table1.render());
                    System.out.println();
                    while (true){
                        System.out.print("Are you sure want to add this record? [Y/y] or [N/n]: ");
                        String choice = sc.nextLine().toLowerCase();
                        if (choice.equals("n")){
                            break;
                        }
                        else if (choice.equals("y")) {
                            productList.add(new Product(productList.size() + 1, name, Double.parseDouble(price), Integer.parseInt(qty), LocalDate.now()));
                            System.out.println();
                            Table table2 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                            table2.addCell("  "+ (productList.size()) + " was added successfully  ");
                            System.out.println(table2.render());
                            System.out.println();
                            break;
                        }
                        else System.out.println("Invalid Input");
                    }
                }

                case "r", "read" -> {
                    boolean isFound= false;
                    String readById;
                    do {
                        System.out.print("Read by ID: ");
                        readById = sc.nextLine();
                    } while (!isInteger(readById));
                    for(Product p: productList) {
                        if(readById.equalsIgnoreCase(p.getId().toString())) {
                            Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            table1.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                            table1.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                            table1.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                            table1.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                            table1.addCell(" Imported Date" + " ".repeat(3) + " : " + p.getDate() + " ".repeat(6));
                            System.out.println();
                            System.out.println(table1.render());
                            System.out.println();
                            isFound = true;
                        }
                    }
                    if(!isFound) {
                        Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                        System.out.println();
                        table1.addCell(" ".repeat(5) + "ID: " + readById + " is not found!" + " ".repeat(5));
                        System.out.println(table1.render());
                    }
                }

                case "u", "update" -> {
                    String inputID;
                    do {
                        System.out.print("Please Input ID of Product : ");
                        inputID = sc.nextLine();
                    } while(!isInteger(inputID));

                    for(Product p: productList) {
                        if(inputID.equalsIgnoreCase(p.getId().toString())) {
                            Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            table1.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                            table1.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                            table1.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                            table1.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                            table1.addCell(" Imported Date" + " ".repeat(3) + " : " + p.getDate() + " ".repeat(6));
                            System.out.println();
                            System.out.println(table1.render());
                            System.out.println();
                            String option;
                            while(true) {
                                Table table2 = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.SURROUND);
                                System.out.println("What do you want to update?");
                                table2.addCell(" ".repeat(4) + "1. All" + " ".repeat(4) + "2. Name" + " ".repeat(4) + "3. Quantity" + " ".repeat(4) + "4. Unit Price" + " ".repeat(4) + "5. Back to Menu" + " ".repeat(4));
                                System.out.println(table2.render());
                                do {
                                    System.out.print("Option (1 - 5) : ");
                                    option = sc.nextLine();
                                } while (!isInteger(option));

                                switch (Integer.parseInt(option)) {
                                    case 1 -> {
                                        String newName, newQuantity, newPrice;
                                        do {
                                            System.out.print("Product's name : ");
                                            newName = sc.nextLine();
                                        } while (newName.isBlank());

                                        do {
                                            System.out.print("Product's Price : ");
                                            newPrice = sc.nextLine();
                                        } while (!isDouble(newPrice));

                                        do {
                                            System.out.print("Product's Qty : ");
                                            newQuantity = sc.nextLine();
                                        } while (!isInteger(newQuantity));
                                        Table table3 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table3.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                        table3.addCell(" Name" + " ".repeat(12) + " : " + newName);
                                        table3.addCell(" Unit Price" + " ".repeat(6) + " : " + Double.parseDouble(newPrice));
                                        table3.addCell(" Qty" + " ".repeat(13) + " : " + Integer.parseInt(newQuantity));
                                        table3.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                                        System.out.println();
                                        System.out.println(table3.render());
                                        System.out.println();

                                        while (true){
                                            System.out.print("Are you sure want to update this record? [Y/y] or [N/n]: ");
                                            String choice = sc.nextLine().toLowerCase();
                                            if (choice.equals("n")){
                                                break;
                                            }
                                            else if (choice.equals("y")) {
                                                p.setName(newName);
                                                p.setPrice(Double.parseDouble(newPrice));
                                                p.setQuantity(Integer.parseInt(newQuantity));
                                                System.out.println();
                                                Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                                table4.addCell(" ".repeat(5)+ "Product was updated." + " ".repeat(5));
                                                System.out.println(table4.render());
                                                System.out.println();
                                                break;
                                            }
                                            else System.out.println("Invalid Input");
                                        }
                                    }

                                    case 2 -> {
                                        String newName;
                                        do {
                                            System.out.print("Product's name : ");
                                            newName = sc.nextLine();
                                        } while (newName.isBlank());
                                        Table table3 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table3.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                        table3.addCell(" Name" + " ".repeat(12) + " : " + newName);
                                        table3.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                                        table3.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                                        table3.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                                        System.out.println();
                                        System.out.println(table3.render());
                                        System.out.println();

                                        while (true){
                                            System.out.print("Are you sure want to update this record? [Y/y] or [N/n]: ");
                                            String choice = sc.nextLine().toLowerCase();
                                            if (choice.equals("n")){
                                                break;
                                            }
                                            else if (choice.equals("y")) {
                                                p.setName(newName);
                                                System.out.println();
                                                Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                                table4.addCell(" ".repeat(5)+ "Product was updated." + " ".repeat(5));
                                                System.out.println(table4.render());
                                                System.out.println();
                                                break;
                                            }
                                            else System.out.println("Invalid Input");
                                        }
                                    }

                                    case 3 -> {
                                        String newQty;
                                        do {
                                            System.out.print("Product's Qty : ");
                                            newQty = sc.nextLine();
                                        } while (!isInteger(newQty));
                                        Table table3 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table3.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                        table3.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                                        table3.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                                        table3.addCell(" Qty" + " ".repeat(13) + " : " + newQty);
                                        table3.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                                        System.out.println();
                                        System.out.println(table3.render());
                                        System.out.println();

                                        while (true){
                                            System.out.print("Are you sure want to update this record? [Y/y] or [N/n]: ");
                                            String choice = sc.nextLine().toLowerCase();
                                            if (choice.equals("n")){
                                                break;
                                            }
                                            else if (choice.equals("y")) {
                                                p.setQuantity(Integer.parseInt(newQty));
                                                System.out.println();
                                                Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                                table4.addCell(" ".repeat(5)+ "Product was updated." + " ".repeat(5));
                                                System.out.println(table4.render());
                                                System.out.println();
                                                break;
                                            }
                                            else System.out.println("Invalid Input");
                                        }
                                    }

                                    case 4 -> {
                                        String newPrice;
                                        do {
                                            System.out.print("Product's Qty : ");
                                            newPrice = sc.nextLine();
                                        } while (!isDouble(newPrice));
                                        Table table3 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table3.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                        table3.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                                        table3.addCell(" Unit Price" + " ".repeat(6) + " : " + Double.parseDouble(newPrice));
                                        table3.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                                        table3.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                                        System.out.println();
                                        System.out.println(table3.render());
                                        System.out.println();

                                        while (true){
                                            System.out.print("Are you sure want to update this record? [Y/y] or [N/n]: ");
                                            String choice = sc.nextLine().toLowerCase();
                                            if (choice.equals("n")){
                                                break;
                                            }
                                            else if (choice.equals("y")) {
                                                p.setPrice(Double.parseDouble(newPrice));
                                                System.out.println();
                                                Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                                table4.addCell(" ".repeat(5)+ "Product was updated." + " ".repeat(5));
                                                System.out.println(table4.render());
                                                System.out.println();
                                                break;
                                            }
                                            else System.out.println("Invalid Input");
                                        }
                                    }

                                    case 5 -> {
                                        continue outer;
                                    }
                                    default -> {
                                        Table table3 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                        table3.addCell(" ".repeat(7) + "INPUT IS INVALID" + " ".repeat(7));
                                        System.out.println(table3.render());
                                    }
                                }
                            }
                        }
                    }
                    Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                    System.out.println();
                    table1.addCell("     ID: " + inputID + " is not found!     ");
                    System.out.println(table1.render());
                }

                case "d", "delete" -> {
                    String inputID;
                    do {
                        System.out.print("Please Input ID of Product : ");
                        inputID = sc.nextLine();
                    } while(!isInteger(inputID));
                    for(Product p: productList) {
                        if (inputID.equals(p.getId().toString())) {
                            Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            table1.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                            table1.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                            table1.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                            table1.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                            table1.addCell(" Imported Date" + " ".repeat(3) + " : " + p.getDate() + " ".repeat(6));
                            System.out.println();
                            System.out.println(table1.render());
                            System.out.println();

                            System.out.print("Are you sure want to delete this record? [Y/y] or [N/n]: ");
                            String choice = sc.nextLine().toLowerCase();
                            if (choice.equals("n")){
                                break;
                            }
                            else if (choice.equals("y")) {
                                productList.remove(p);
                                System.out.println();
                                Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                table4.addCell(" ".repeat(5)+ "Product was removed" + " ".repeat(5));
                                System.out.println(table4.render());
                                System.out.println();
                                break;
                            }
                            else System.out.println("Invalid Input");
                        }
                    }
                }

                case "f", "first" -> {
                    if (pageNumber >= 1) {
                        pageNumber = 1;
                    }
                    displayTable(productList, pageNumber, rowSize);
                }

                case "p", "previous" -> {
                    if (pageNumber > 1) {
                        pageNumber--;
                    } else pageNumber = totalPage;
                    displayTable(productList, pageNumber, rowSize);
                }

                case "n", "next" -> {
                    if (pageNumber < totalPage) {
                        pageNumber++;
                    } else pageNumber = 1;
                    displayTable(productList, pageNumber, rowSize);
                }

                case "l", "last" -> {
                    if (pageNumber < totalPage) {
                        pageNumber = totalPage;
                    }
                    displayTable(productList, pageNumber, rowSize);
                }

                case "s", "search" -> {
                    String searchName;
                    do {
                        System.out.print("Search by Name : ");
                        searchName = sc.nextLine();
                    } while (searchName.isBlank());

                    CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
                    Table table1 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
                    table1.setColumnWidth(0,20, 30);
                    table1.setColumnWidth(1,20, 30);
                    table1.setColumnWidth(2,20, 30);
                    table1.setColumnWidth(3,20, 30);
                    table1.setColumnWidth(4,20, 30);

                    table1.addCell("ID", cellStyle);
                    table1.addCell("NAME", cellStyle);
                    table1.addCell("Unit Price", cellStyle);
                    table1.addCell("Qty", cellStyle);
                    table1.addCell("Imported Date", cellStyle);
                    int found = 0;
                    for(Product p: productList) {
                        if(p.getName().equalsIgnoreCase(searchName) || p.getName().contains(searchName)) {
                            table1.addCell(p.getId().toString(), cellStyle);
                            table1.addCell(p.getName());
                            table1.addCell(p.getPrice().toString(), cellStyle);
                            table1.addCell(p.getQuantity().toString(), cellStyle);
                            table1.addCell(p.getDate().toString(), cellStyle);
                            found++;
                        }
                    }
                    if (found == 0) {
                        Table table2 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                        System.out.println();
                        table2.addCell(" ".repeat(5) + "Product for [" + searchName + "] is not found!" + " ".repeat(5));
                        System.out.println(table2.render());
                    }
                    if(found == 1) {
                        System.out.println("Product found for [" + searchName + "] : " + found);
                        System.out.println(table1.render());
                        Table table2 = new Table(1, BorderStyle.DESIGN_CURTAIN, ShownBorders.SURROUND);
                        table2.addCell( "Page : " + pageNumber + " of " + totalPage + " ".repeat(74) + "Total record: "+ found);
                        System.out.println(table2.render());
                    }
                    if(found > 1) {
                        System.out.println("Product found for [" + searchName + "] : " + found);
                        System.out.println(table1.render());
                        Table table2 = new Table(1, BorderStyle.DESIGN_CURTAIN, ShownBorders.SURROUND);
                        table2.addCell( "Page : " + pageNumber + " of " + totalPage + " ".repeat(74) + "Total record: "+ found);
                        System.out.println(table2.render());
                    }
                }

                case "g", "goto" -> {
                    String goTo;
                    do{
                        System.out.print("Go to Page: ");
                        goTo = sc.nextLine();
                    }while (!isInteger(goTo));
                    if (Integer.parseInt(goTo) > 0 && Integer.parseInt(goTo) <= totalPage){
                        pageNumber = Integer.parseInt(goTo);
                    }else {
                        return;
                    }
                    displayTable(productList, pageNumber, rowSize);
                }

                case "se", "setrow" -> {
                    Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                    String setRow;
                    do {
                        System.out.print("Please enter Row for Display : ");
                        setRow = sc.nextLine();
                    } while(!isInteger(setRow));
                    rowSize = Integer.parseInt(setRow);
                    System.out.println();
                    table1.addCell("  Set row to " + setRow + " Successfully.  ");
                    System.out.println(table1.render());
                    System.out.println();
                }

                case "h", "help" -> {
                    helpMenu();
                }

                case "e", "exit" -> {
                    System.exit(0);
                }
                default -> {
                    String[] shortCutOpt = opt.split("[#-]");
                    switch (shortCutOpt[0]){
                        case "W", "w" -> {
                            Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            table1.addCell(" ID" + " ".repeat(14) + " : " + (productList.size() + 1));
                            table1.addCell(" Name" + " ".repeat(12) + " : " + shortCutOpt[1]);
                            table1.addCell(" Unit Price" + " ".repeat(6) + " : " + Double.parseDouble(shortCutOpt[2]));
                            table1.addCell(" Qty" + " ".repeat(13) + " : " + shortCutOpt[3]);
                            table1.addCell(" Imported Date" + " ".repeat(3) + " : " + LocalDate.now() + " ".repeat(6));

                            System.out.println();
                            System.out.println(table1.render());
                            System.out.println();
                            while (true){
                                System.out.print("Are you sure want to add this record? [Y/y] or [N/n]: ");
                                String choice = sc.nextLine().toLowerCase();
                                if (choice.equals("n")){
                                    break;
                                }
                                else if (choice.equals("y")) {
                                    productList.add(new Product(productList.size() + 1, shortCutOpt[1], Double.parseDouble(shortCutOpt[2]), Integer.parseInt(shortCutOpt[3]), LocalDate.now()));
                                    System.out.println();
                                    Table table2 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                    table2.addCell("  "+ (productList.size()) + " was added successfully  ");
                                    System.out.println(table2.render());
                                    System.out.println();
                                    break;
                                }
                                else System.out.println("Invalid Input");
                            }
                        }

                        case "R", "r" -> {
                            boolean isFound = false;
                            for(Product p: productList) {
                                if(shortCutOpt[1].equalsIgnoreCase(p.getId().toString())) {
                                    Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                    table1.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                    table1.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                                    table1.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                                    table1.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                                    table1.addCell(" Imported Date" + " ".repeat(3) + " : " + p.getDate() + " ".repeat(6));
                                    System.out.println();
                                    System.out.println(table1.render());
                                    System.out.println();
                                    isFound = true;
                                }
                            }
                            if(!isFound) {
                                Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                System.out.println();
                                table1.addCell(" ".repeat(5) + "ID: " + shortCutOpt[1] + " is not found!" + " ".repeat(5));
                                System.out.println(table1.render());
                            }
                        }
                        case "D", "d" -> {
                            boolean isFound = false;
                            for(Product p: productList) {
                                if (shortCutOpt[1].equals(p.getId().toString())) {
                                    Table table1 = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                    table1.addCell(" ID" + " ".repeat(14) + " : " + p.getId());
                                    table1.addCell(" Name" + " ".repeat(12) + " : " + p.getName());
                                    table1.addCell(" Unit Price" + " ".repeat(6) + " : " + p.getPrice());
                                    table1.addCell(" Qty" + " ".repeat(13) + " : " + p.getQuantity());
                                    table1.addCell(" Imported Date" + " ".repeat(3) + " : " + p.getDate() + " ".repeat(6));
                                    System.out.println();
                                    System.out.println(table1.render());
                                    System.out.println();

                                    System.out.print("Are you sure want to delete this record? [Y/y] or [N/n]: ");
                                    String choice = sc.nextLine().toLowerCase();
                                    isFound = true;
                                    if (choice.equals("n")){
                                        break;
                                    }
                                    else if (choice.equals("y")) {
                                        productList.remove(p);
                                        System.out.println();
                                        Table table4 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                        table4.addCell(" ".repeat(5)+ "Product was removed" + " ".repeat(5));
                                        System.out.println(table4.render());
                                        System.out.println();
                                    }
                                }
                            }
                            if(!isFound) {
                                Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                                System.out.println();
                                table1.addCell(" ".repeat(5) + "ID: " + shortCutOpt[1] + " is not found!" + " ".repeat(5));
                                System.out.println(table1.render());
                            }
                        }

                        default -> {
                            Table table1 = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
                            table1.addCell(" ".repeat(7) + "INPUT IS INVALID" + " ".repeat(7));
                            System.out.println(table1.render());
                        }
                    }
                }
            }
        }
    }

    public static Table getTable() {
        Table table = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        table.addCell(" ".repeat(4) + "*)Display");
        table.addCell(" ".repeat(4) + " | W)rite");
        table.addCell(" ".repeat(4) + " | R)ead");
        table.addCell(" ".repeat(4) + " | U)pdate");
        table.addCell(" ".repeat(4) + " | D)elete");
        table.addCell(" ".repeat(4) + " | F)irst");
        table.addCell(" ".repeat(4) + " | P)revious");
        table.addCell(" ".repeat(4) + " | N)ext");
        table.addCell(" ".repeat(4) + " | L)ast" + " ".repeat(4));
        table.addCell(" ".repeat(4) + "S)earch");
        table.addCell(" ".repeat(4) + " | G)oto");
        table.addCell(" ".repeat(4) + " | Se)t row");
        table.addCell(" ".repeat(4) + " | H)elp");
        table.addCell(" ".repeat(4) + " | E)xit");
        return table;
    }

    public static void displayTable(List<Product> productList, int pageNumber, int rowSize) {
        int totalPage = (int) Math.ceil((double) productList.size() / rowSize);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table1 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        table1.setColumnWidth(0,20, 30);
        table1.setColumnWidth(1,20, 30);
        table1.setColumnWidth(2,20, 30);
        table1.setColumnWidth(3,20, 30);
        table1.setColumnWidth(4,20, 30);

        table1.addCell("ID", cellStyle);
        table1.addCell("NAME", cellStyle);
        table1.addCell("Unit Price", cellStyle);
        table1.addCell("Qty", cellStyle);
        table1.addCell("Imported Date", cellStyle);
        for (int i = (pageNumber - 1) * rowSize; i < (pageNumber - 1) * rowSize + rowSize && i < productList.size(); i++) {
            Product product = productList.get(i);
            table1.addCell(product.getId().toString(), cellStyle);
            table1.addCell(product.getName());
            table1.addCell(product.getPrice().toString(), cellStyle);
            table1.addCell(product.getQuantity().toString(), cellStyle);
            table1.addCell(product.getDate().toString(), cellStyle);
        }
        System.out.println(table1.render());
        Table table2 = new Table(1, BorderStyle.DESIGN_CURTAIN, ShownBorders.SURROUND);
        table2.addCell( "Page : " + pageNumber + " of "+ totalPage + " ".repeat(74) + "Total record: "+productList.size());
        System.out.println(table2.render());
    }
    public static void helpMenu() {
        Table table;
        table = new Table(1, BorderStyle.CLASSIC_COMPATIBLE_LIGHT_WIDE, ShownBorders.SURROUND);
        table.addCell("1." + " ".repeat(4) + "Press" + " ".repeat(4) + "* : Display all record of products");
        table.addCell("2." + " ".repeat(4) + "Press" + " ".repeat(4) + "w : Add new product");
        table.addCell(" ".repeat(6) + "Press" + " ".repeat(4) + "w -> #proname-unitprice-qty : shortcut for add new product");
        table.addCell("3." + " ".repeat(4) + "Press" + " ".repeat(4) + "r : read Content any content");
        table.addCell(" ".repeat(6) + "Press" + " ".repeat(4) + "r#proId : shortcut for read product by Id");
        table.addCell("4." + " ".repeat(4) + "Press" + " ".repeat(4) + "u : Update Data");
        table.addCell("5." + " ".repeat(4) + "Press" + " ".repeat(4) + "d : Delete Data");
        table.addCell(" ".repeat(6) + "Press" + " ".repeat(4) + "d#proId : shortcut for delete product by Id");
        table.addCell("6." + " ".repeat(4) + "Press" + " ".repeat(4) + "f : Display First Page");
        table.addCell("7." + " ".repeat(4) + "Press" + " ".repeat(4) + "p : Display Previous Page");
        table.addCell("8." + " ".repeat(4) + "Press" + " ".repeat(4) + "n : Display Next Page");
        table.addCell("9." + " ".repeat(4) + "Press" + " ".repeat(4) + "l : Display Last Page");
        table.addCell("10." + " ".repeat(3) + "Press" + " ".repeat(4) + "s : Search product by name");
        table.addCell("11." + " ".repeat(3) + "Press" + " ".repeat(4) + "h : Help");
        table.addCell("12." + " ".repeat(3) + "Press" + " ".repeat(4) + "e : Exit");
        System.out.println(table.render());
    }
}
