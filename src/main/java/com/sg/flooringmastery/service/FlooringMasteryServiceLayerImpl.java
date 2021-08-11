package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.util.List;
import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;


public class FlooringMasteryServiceLayerImpl implements
        FlooringMasteryServiceLayer {

    FlooringMasteryDao dao;
    private final FlooringMasteryAuditDao auditDao;
   
    @Autowired
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public void createOrder(Order order, String date) throws 
        FlooringMasteryDuplicateIdException,
        FlooringMasteryDataValidationException, 
        FlooringMasteryPersistenceException,
        FlooringMasteryIllegalStateException {

        // First check to see if there is alreay a order 
        // associated with the given order's id
        // If so, we're all done here - 
        // throw a FlooringMasteryDuplicateIdException
        if (dao.getOrder(order.getOrderNumber(), date) != null) {
            throw new FlooringMasteryDuplicateIdException(
                    "ERROR: Could not create order.  Order Id "
                    + order.getOrderNumber()
                    + " already exists");
        }
        
        // Compute fields
        computeFields(order);

        // Now validate all the fields on the given Order object.  
        // This method will throw an
        // exception if any of the validation rules are violated.
        validateOrderData(order);
        // We passed all our business rules checks so go ahead 
        // and persist the Order object
        dao.addOrder(order.getOrderNumber(), order, date);

        // The order was successfully created, now write to the audit log
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " CREATED.");

    }
    
    @Override
    public int getNewOrderId(String date) throws FlooringMasteryPersistenceException {
        // TODO: No data
        List<Order> orders = dao.getAllOrders(date);
        try {
            return orders.get(orders.size() - 1).getOrderNumber() + 1;
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
    }

    @Override
    public List<Order> getAllOrders(String date) throws
            FlooringMasteryPersistenceException {
        return dao.getAllOrders(date);
    }

    @Override
    public Order getOrder(int orderId, String date) throws
            FlooringMasteryPersistenceException {
        return dao.getOrder(orderId, date);
    }

    @Override
    public Order removeOrder(int orderId, String date) throws FlooringMasteryPersistenceException {
        Order removedOrder = dao.removeOrder(orderId, date);
        // Write to audit log
        auditDao.writeAuditEntry("Order " + orderId + " REMOVED.");
        return removedOrder;
    }

    @Override
    public Map<String, StateTax> getStateData() throws FlooringMasteryPersistenceException {
        return dao.getStateTaxData();
    }
    
    @Override
    public Map<String, Product> getProductData() throws FlooringMasteryPersistenceException {
        return dao.getProductData();
    }

    
    @Override
    public void computeFields(Order order) throws FlooringMasteryPersistenceException, FlooringMasteryIllegalStateException {
        
        Map<String, Product> products = dao.getProductData();
        Map<String, StateTax> taxes = dao.getStateTaxData();
        
        Product currProduct = products.get(order.getProductType());
        StateTax currStateTax = taxes.get(order.getState());
        if (currStateTax == null)
            throw new FlooringMasteryIllegalStateException("Sale within state is not allowed.");
        else
            order.setTaxRate(currStateTax.getTaxRate());
                
        order.setCostPerSqrFt(currProduct.getCostPerSqrFt());
        order.setLaborCostPerSqrFt(currProduct.getLaborCostPerSqrFt());
        
        // Material Cost
        BigDecimal matCost = order.getArea().multiply(order.getCostPerSqrFt());
        matCost = matCost.setScale(2, RoundingMode.HALF_UP);
        
        order.setMaterialCost(matCost);
        
        // Labor Cost
        BigDecimal laborCost = order.getArea().multiply(order.getLaborCostPerSqrFt());
        laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
        
        order.setLaborCost(laborCost);
                
        // Tax = (MaterialCost + LaborCost) * (TaxRate/100)
        BigDecimal tax = order.getMaterialCost().add(order.getLaborCost());
        BigDecimal quotient = order.getTaxRate().divide(new BigDecimal("100"));
        
        tax = tax.multiply(quotient);
        
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        
        order.setTax(tax);
        
        // Total = (MaterialCost + LaborCost + Tax)
        BigDecimal total = order.getMaterialCost().add(order.getLaborCost());
        total = total.add(order.getTax());
        
        total = total.setScale(2, RoundingMode.HALF_UP);
        
        order.setTotal(total);
    }

    private void validateOrderData(Order order) throws
            FlooringMasteryDataValidationException {

        // TODO: Verify file values (i.e. TaxRate)?
        if (order.getOrderNumber() == 0
                || order.getCustomerName()== null
                || order.getState().trim().length() == 0
                || order.getTaxRate() == null
                || order.getProductType() == null
                || order.getArea()== null
                || order.getCostPerSqrFt()== null
                || order.getLaborCostPerSqrFt()== null
                || order.getMaterialCost()== null
                || order.getLaborCost()== null
                || order.getTax()== null
                || order.getTotal()== null) {

            throw new FlooringMasteryDataValidationException(
                    "ERROR: All fields [OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total] are required.");
        }
    }
}