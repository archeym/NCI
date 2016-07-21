/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import Server.Accounts;
import Server.Balance;
import Server.Lodgement;
import Server.Transactions;
import Server.Transfer;
import Server.Withdrawal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Archie
 * Arkadijs Makarenko
 * ID: 15029042
 * Course: HDCLOUDJAN16
 */
@Stateless
@Path("server.accounts")
public class AccountsFacadeREST extends AbstractFacade<Accounts> {
    @PersistenceContext(unitName = "NewBankPU")
    private EntityManager em;

    public AccountsFacadeREST() {
        super(Accounts.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Accounts entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Accounts entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Accounts find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Accounts> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Accounts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //edit
    @POST
    @Path("{id}/lodgement")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Balance lodgement(@PathParam("id") Integer id, Lodgement lodgement){
        
        Accounts account = find(id);    
        account.setBalance(account.getBalance().add(lodgement.getAmount()));
        
        Transactions transaction = new Transactions();
        transaction.setAccountId(account);
        transaction.setTransDate(new Date());
        transaction.setTransValue(lodgement.getAmount());
        transaction.setBalance(account.getBalance());
        transaction.setTransType(true);
        transaction.setDescription("lodgement");
        getEntityManager().persist(transaction);
        
        return new Balance(account.getBalance());
    }
    
    @POST
    @Path("{id}/withdrawal")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Balance withdrawal(@PathParam("id") Integer id, Withdrawal withdrawal){
        Accounts account = find(id); 
        account.setBalance(account.getBalance().subtract(withdrawal.getAmount()));

        Transactions transaction = new Transactions();
        transaction.setAccountId(account);
        transaction.setTransDate(new Date());
        transaction.setTransValue(withdrawal.getAmount());
        transaction.setBalance(account.getBalance());
        transaction.setTransType(false);
        transaction.setDescription("withdrawal");
        getEntityManager().persist(transaction);
        
        return new Balance(account.getBalance());
    }
    
    @POST
    @Path("transfer")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Balance[] transfer(Transfer transfer){
        Accounts fromAccount = find(transfer.getFromAccountId());
        Accounts toAccount = find(transfer.getToAccountId());
        
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transfer.getAmount()));
        
        Transactions lodgeTransaction = new Transactions();
        lodgeTransaction.setAccountId(toAccount);
        lodgeTransaction.setTransDate(new Date());
        lodgeTransaction.setTransValue(transfer.getAmount());
        lodgeTransaction.setBalance(toAccount.getBalance());
        lodgeTransaction.setTransType(true);
        lodgeTransaction.setDescription("transfer lodgement");
        getEntityManager().persist(lodgeTransaction);
        
        Transactions withdrawTransaction = new Transactions();
        withdrawTransaction.setAccountId(fromAccount);
        withdrawTransaction.setTransDate(new Date());
        withdrawTransaction.setTransValue(transfer.getAmount());
        withdrawTransaction.setBalance(fromAccount.getBalance());
        withdrawTransaction.setTransType(true);
        withdrawTransaction.setDescription("transfer withdrawal");
        getEntityManager().persist(withdrawTransaction);
        
        
        Balance[] result = new Balance[2];
        result[0] = new Balance(fromAccount.getBalance());
        result[1] = new Balance(toAccount.getBalance());
        return result;
    }
    
    @GET
    @Path("{id}/balance")
    @Produces({"application/xml", "application/json"})
    public Balance balance(@PathParam("id") Integer id){
        
        return new Balance(find(id).getBalance());
    }
}
