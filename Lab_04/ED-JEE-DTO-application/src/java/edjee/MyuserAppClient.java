/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edjee;

import entity.MyuserDTO;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import session.MyuserFacadeRemote;

/**
 *
 * @author adamk
 */
public class MyuserAppClient {

    @EJB
    private static MyuserFacadeRemote myuserFacade;

    public MyuserAppClient() {
    }

    public static void main(String[] args) {
        MyuserAppClient client = new MyuserAppClient();
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO = new MyuserDTO("000001", "Wei Lai", "123456",
                "wlai@swin.edu.au", "9876543210", "Swinburne EN510b",
                "What is my name?", "Wei");
        boolean result = client.createRecord(myuserDTO);
        client.showCreateResult(result, myuserDTO);
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO2 = new MyuserDTO("000007", "Man Lai", "654321",
                "wlai@swin.edu.au", "9876543210", "Swinburne EN510b",
                "What is my name?", "Wei");
        result = client.createRecord(myuserDTO2);
        client.showCreateResult(result, myuserDTO2);
        
        //Show GetRecord
        String findById = "000007";
        MyuserDTO myuserDTO3 = client.getRecord(findById);
        if(myuserDTO3 != null) {
            System.out.println("\nUser with id " + findById + " was found!");
            System.out.println("Hello " + myuserDTO3.getName());
        } else {
            System.out.println("\nNo user found with id " + findById);
        }
        
        //Show update Record
        MyuserDTO myuserDTO4 = new MyuserDTO("000001", "Adam Knox", "232323",
                "aknox@swin.edu.au", "92148618", "Swinburne EN510b",
                "What is my name?", "Adam");
        result = client.updateRecord(myuserDTO4);
        if (result) {
            MyuserDTO updatedUser = client.getRecord(myuserDTO4.getUserid());
            System.out.println("\nUpdate Successful!");
            System.out.println("Original user " + myuserDTO4.getName() 
                + " matches the updated record " + updatedUser.getName());
        } else {
            System.out.println("\nError updating the user in table!");
        }
        
        //Show delete record
        String idForDeletion = "000007";
        result = client.deleteRecord(idForDeletion);
        if(result) {
            System.out.println("\nUser was successfully deleted!");
        } else {
            System.out.println("\nError deleting user record");
        }
        
        //Show returned list of users
        String findByAddress = "Swinburne EN510b";      //This address should pull records 1 and 7.
        List<MyuserDTO> foundUsers = client.getRecordsByAddress(findByAddress);
        if(foundUsers != null) {
            System.out.println("\nUsers have been found with address: " + findByAddress);
            for(MyuserDTO user: foundUsers) {
                System.out.println("User: " + user.getName());
            }
        } else {
            System.out.println("\nNo users found with address: " + findByAddress);
        }
    }

    public void showCreateResult(boolean result, MyuserDTO myuserDTO) {
        if (result) {
            System.out.println("\nRecord with primary key " + myuserDTO.getUserid()
                    + " has been created in the database table.");
        } else {
            System.out.println("\nRecord with primary key " + myuserDTO.getUserid()
                    + " could not be created in the database table!");
        }
    }

    public Boolean createRecord(MyuserDTO myuserDTO) {
        return myuserFacade.createRecord(myuserDTO);
    }
    
    public MyuserDTO getRecord(String userId) {
        return myuserFacade.getRecord(userId);
    }

    public Boolean updateRecord(MyuserDTO myDTO) {
        return myuserFacade.updateRecord(myDTO);
    }
    
    public Boolean deleteRecord(String userId) {
        return myuserFacade.deleteRecord(userId);
    }
    
    public List<MyuserDTO> getRecordsByAddress(String address) {
        return myuserFacade.getRecordsByAddress(address);
    }
}
