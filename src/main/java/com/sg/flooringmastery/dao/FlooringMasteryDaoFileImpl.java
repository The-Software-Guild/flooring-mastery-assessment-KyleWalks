package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import com.sg.flooringmastery.ui.UserIO;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FlooringMasteryDaoFileImpl implements UserIO, FlooringMasteryDao {

    final private Scanner console = new Scanner(System.in);

    private Map<Integer, Order> orders = new HashMap<>();
    final private Map<String, Product> products = new HashMap<>();
    final private Map<String, StateTax> taxes = new HashMap<>();

    private final String ORDER_HEADERS;
    private final String ORDER_FILE;
    private final String PRODUCT_FILE;
    private final String STATETAX_FILE;
    public static final String DELIMITER = "::";

    public FlooringMasteryDaoFileImpl(){
        String currDir = System.getProperty("user.dir");
        ORDER_HEADERS = "OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total";
        ORDER_FILE = currDir + "\\Orders\\Orders_";
        PRODUCT_FILE = currDir + "\\Data\\Products.txt";
        STATETAX_FILE = currDir + "\\Data\\Taxes.txt";
    }

    public FlooringMasteryDaoFileImpl(String databaseTextFile, String productTextFile, String stateTaxTextFile){
        ORDER_HEADERS = "OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total";
        ORDER_FILE = databaseTextFile;
        PRODUCT_FILE = productTextFile;
        STATETAX_FILE = stateTaxTextFile;
    }
    
    public FlooringMasteryDaoFileImpl(String databaseTextFile){
        ORDER_HEADERS = "OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total";
        String currDir = System.getProperty("user.dir");
        ORDER_FILE = databaseTextFile;
        PRODUCT_FILE = currDir + "\\Data\\Products.txt";
        STATETAX_FILE = currDir + "\\Data\\Taxes.txt";
    }
    
    /**
     * Takes a string representing a VendingItem object and splits it on the DELIMITER
     * to obtain each property of the VendingItem.
     * 
     * OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
     * 12::Ricky Martin::KY::6.00::Tile::150.00::3.50::4.15::525.00::622.50::69.03::1216.53
     * ___________________________________________________________________________
     * |  |            |  |    |    |      |    |    |      |      |     |       |
     * |12|Ricky Martin|KY|6.00|Tile|150.00|3.50|4.15|525.00|622.50|69.03|1216.53|
     * |  |            |  |    |    |      |    |    |      |      |     |       |
     * ---------------------------------------------------------------------------
     *
     * @param orderAsText String representing an Order object as text.
     * @return Order loaded from database.
     */
    private Order unmarshallOrder(String orderAsText){
        String[] orderTokens = orderAsText.split(DELIMITER);

        // Setup order object.
        int orderId = Integer.parseInt(orderTokens[0]);

        Order orderFromFile = new Order(orderId);

        orderFromFile.setCustomerName(orderTokens[1]);

        orderFromFile.setState(orderTokens[2]);

        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        
        orderFromFile.setProductType(orderTokens[4]);
        
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        
        orderFromFile.setCostPerSqrFt(new BigDecimal(orderTokens[6]));
        
        orderFromFile.setLaborCostPerSqrFt(new BigDecimal(orderTokens[7]));
        
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));

        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));

        return orderFromFile;
    }

     /**
     * Converts a VendingItem object into a string where each property
     * is split by the DELIMITER.
     * 
     * OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
     * 12::Ricky Martin::KY::6.00::Tile::150.00::3.50::4.15::525.00::622.50::69.03::1216.53
     *
     * @param aOrder VendingItem object to be converted.
     * @return the string representing the VendingItem object.
     */
    private String marshallOrder(Order aOrder){
        String orderAsText = aOrder.getOrderNumber()+ DELIMITER;

        orderAsText += aOrder.getCustomerName()+ DELIMITER;

        orderAsText += aOrder.getState() + DELIMITER;
        
        orderAsText += aOrder.getTaxRate()+ DELIMITER;
        
        orderAsText += aOrder.getProductType()+ DELIMITER;
        
        orderAsText += aOrder.getArea()+ DELIMITER;
        
        orderAsText += aOrder.getCostPerSqrFt()+ DELIMITER;
        
        orderAsText += aOrder.getLaborCostPerSqrFt()+ DELIMITER;
        
        orderAsText += aOrder.getMaterialCost()+ DELIMITER;
        
        orderAsText += aOrder.getLaborCost()+ DELIMITER;
        
        orderAsText += aOrder.getTax()+ DELIMITER;
        
        orderAsText += aOrder.getTotal();

        return orderAsText;
    }

    /**
     * Writes all orders in the database out to the ORDER_FILE.  See loadDatabase
     * for file format.
     *
     * @throws FlooringMasteryPersistenceException if an error occurs writing to the file
     */
    private void writeDatabase(String date) throws FlooringMasteryPersistenceException {
        PrintWriter out;
                
        try {
            out = new PrintWriter(new FileWriter(ORDER_FILE + date + ".txt"));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }
        // Write header
        out.println(ORDER_HEADERS);
        out.flush();
        
        String orderAsText;
        List<Order> orderList = new ArrayList(orders.values());
        for (Order currentOrder : orderList) {
            // turn a Order into a String
            orderAsText = marshallOrder(currentOrder);
            // write the Order object to the file
            out.println(orderAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    private void loadDatabase(String date, boolean newFile) throws FlooringMasteryPersistenceException {
        Scanner scanner;
        orders = new HashMap<>();
        
        String path = ORDER_FILE + date + ".txt";
        // Create file if not found
        
        // Check if a new file could be expected.
        if (newFile) {
            try {
                File file = new File(path);
                if (file.createNewFile()) {
                    // Create Scanner for reading the file
                    PrintWriter out;
                    try {
                        out = new PrintWriter(new FileWriter(path));
                    } catch (IOException e) {
                        throw new FlooringMasteryPersistenceException(
                                "Could not setup new file.", e);
                    }
                    out.println(ORDER_HEADERS);
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                throw new FlooringMasteryPersistenceException("Could not load database data into memory.");
            }
        }
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(path)));
        } catch (FileNotFoundException e) {

            throw new FlooringMasteryPersistenceException("Unable to locate file @ " + path + "\n");
        }


        String currentLine;

        Order currentOrder;
        // Skip the header.
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);

            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        // close scanner
        scanner.close();
    }
    
    private Product unmarshallProduct(String productAsText) {
        String[] orderTokens = productAsText.split(DELIMITER);

        // Setup order object.
        String productType = (orderTokens[0]);

        Product productFromFile = new Product(productType);

        productFromFile.setCostPerSqrFt(new BigDecimal(orderTokens[1]));

        productFromFile.setLaborCostPerSqrFt(new BigDecimal(orderTokens[2]));

        return productFromFile;
    }
    
    private void loadProductData() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load database data into memory.", e);
        }
        String currentLine;
        
        Product currentProduct;
        // Skip the header.
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            
            currentProduct = unmarshallProduct(currentLine);
            
            products.put(currentProduct.getProductType(), currentProduct);
        }
        // close scanner
        scanner.close();
    }
    
    @Override
    public Map<String, Product> getProductData() throws FlooringMasteryPersistenceException {
        loadProductData();
        return products;
    }

    private StateTax unmarshallStateTax(String stateTaxAsText) {
        String[] orderTokens = stateTaxAsText.split(DELIMITER);

        // Setup order object.
        String state = (orderTokens[0]);

        StateTax stateTaxFromFile = new StateTax(state);

        stateTaxFromFile.setStateName(orderTokens[1]);

        stateTaxFromFile.setTaxRate(new BigDecimal(orderTokens[2]));

        return stateTaxFromFile;
    }
    
    private void loadStateTaxData() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(STATETAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load state tax database data into memory.", e);
        }
        String currentLine;
        
        StateTax currentStateTax;
        // Skip the header.
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            
            currentStateTax = unmarshallStateTax(currentLine);
            
            taxes.put(currentStateTax.getStateAbrv(), currentStateTax);
        }
        // close scanner
        scanner.close();
    }
    
    @Override
    public Map<String, StateTax> getStateTaxData() throws FlooringMasteryPersistenceException {
        loadStateTaxData();
        return taxes;
    }
    
    @Override
    public Order addOrder(Integer orderId, Order order, String date) throws FlooringMasteryPersistenceException {
        loadDatabase(date, true);
        Order newOrder = orders.put(orderId, order);
        writeDatabase(date);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders(String date) throws FlooringMasteryPersistenceException {
        loadDatabase(date, true);
        return new ArrayList(orders.values());
    }

    @Override
    public Order getOrder(Integer orderId, String date) throws FlooringMasteryPersistenceException {
        loadDatabase(date, false);
        return orders.get(orderId);
    }

    @Override
    public Order removeOrder(Integer orderId, String date) throws FlooringMasteryPersistenceException {
        loadDatabase(date, false);
        Order removedOrder = orders.remove(orderId);
        writeDatabase(date);
        return removedOrder;
    }

    /**
     *
     * A very simple method that takes in a message to display on the console 
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    @Override
    public String readString(String msgPrompt, int minLen) {
        
        String stringValue = "";
        do {
            try {
                stringValue = this.readString(msgPrompt);
                System.out.println(stringValue);
                System.out.println(stringValue.length());
                        
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        } while (stringValue.length() <= minLen);
        return stringValue;
    }
    
    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as long
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a BigDecimal
     * to be returned as the answer to that message.
     *
     * @param prompt - String explaining what information you want from the user.
     * @return the answer to the message as BigDecimal
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                return new BigDecimal(this.readString(prompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a BigDecimal
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param prompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @return a BigDecimal value as an answer to the message prompt within the min/max range
     */
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        BigDecimal result;

        do {
            result = readBigDecimal(prompt);
        } while (result.compareTo(min) < 0);

        return result;
    }
}
