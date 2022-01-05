package com.example.BankTask.User;


import com.example.BankTask.Deposit.Deposit;
import com.example.BankTask.Loan.Loan;
import com.example.BankTask.WithDraw.WithDraw;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "users")
public class Controller {

    int bankBalance =40000;

    ArrayList<User> users= new ArrayList<User>();

    @GetMapping
    public ResponseEntity <?> getUsers(){
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){

        if (user.getId()==0 || user.getName()==null || user.getPassword()==null){
            return ResponseEntity.status(400).body("Please Fill All Your information");
        }
        for (int i = 0; i <users.size() ; i++) {
            if (users.get(i).getEmail().equals(user.getEmail())){
                return ResponseEntity.status(400).body("Email Already Exist");
            }
        }
        user.setBalance(0);
        user.setLoanAmount(0);
        users.add(user);
        return ResponseEntity.ok("User Created");
    }

    @PostMapping("/withDraw")
    public ResponseEntity<?> withDraw(@RequestBody WithDraw withDraw){
        User FoundUser = findUserByID(withDraw);
        if (FoundUser == null){
            return ResponseEntity.status(400).body("User Not Found");
        }
        if (!FoundUser.getPassword().equals(withDraw.getPassword())){
            return ResponseEntity.status(400).body("Wrong Password");
        }
        if (FoundUser.getBalance()< withDraw.getWithDraw()){
            return ResponseEntity.status(400).body("Your Balance is not Enough");
        }
        FoundUser.setBalance(FoundUser.getBalance() - withDraw.getWithDraw());
        return ResponseEntity.ok("Your New Balance is: "+FoundUser.getBalance());
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Deposit deposit){
        User FoundUser = findUserByID(deposit);
        if (FoundUser == null){
            return ResponseEntity.status(400).body("Your Not Found");
        }
        if (!FoundUser.getPassword().equals(deposit.getPassword())){
            return ResponseEntity.status(400).body("Wrong Password");
        }
        FoundUser.setBalance(FoundUser.getBalance()+ deposit.getDeposit());
        return ResponseEntity.ok("Your New Balance is: "+ FoundUser.getBalance());
    }

    @PostMapping("/loan")
    public ResponseEntity<?> Loan(@RequestBody Loan loan){

        User FoundUser= findUserByID(loan) ;

        if (FoundUser == null){
            return ResponseEntity.status(400).body("User Not Found");
        }
        if (loan.getLoan()>bankBalance){
            return ResponseEntity.status(400).body("Sorry Bank doesn't have balance");
        }
        FoundUser.setBalance(FoundUser.getBalance()+loan.getLoan());
        FoundUser.setLoanAmount(FoundUser.getLoanAmount()+loan.getLoan());
        bankBalance = bankBalance-loan.getLoan();
        return ResponseEntity.status(200).body("Your New Balance is: "+ FoundUser.getBalance() +" and Amount of Loan is: "+ FoundUser.getLoanAmount());
    }


    @PostMapping("/loanPayment")
    public ResponseEntity<?> loanPayment(@RequestBody Loan loan){
        User FoundUser= findUserByID(loan) ;

        if (FoundUser == null){
            return ResponseEntity.status(400).body("User Not Found");
        }
        if (FoundUser.getBalance()<loan.getLoan()){
            return ResponseEntity.status(400).body("Your Balance is not enough");
        }
        if (FoundUser.getLoanAmount()- loan.getLoan()<0){
            return ResponseEntity.status(400).body("You are Paying more than your loan");
        }
        bankBalance = bankBalance +loan.getLoan();
        FoundUser.setLoanAmount(FoundUser.getLoanAmount()- loan.getLoan());
        FoundUser.setBalance(FoundUser.getBalance()-loan.getLoan());

        return ResponseEntity.status(200).body("Your loan payed correctly Your balance now is: " + FoundUser.getBalance() +" And your loan amount is: "+FoundUser.getLoanAmount());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        User FoundUser = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id ){
                FoundUser = users.get(i);
                break;
            }
        }
        if (FoundUser.getLoanAmount()>0){
            return ResponseEntity.status(400).body("You have to pay your loan before delete your account and it is: "+ FoundUser.getLoanAmount());
        }
        bankBalance = bankBalance +FoundUser.getBalance();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id ){
                users.remove(i);
                break;
            }
        }
        return ResponseEntity.ok("User Removed and his balance transfer to bank account: "+bankBalance);
    }





    public User findUserByID(WithDraw withDraw){

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(withDraw.getEmail())){
                return users.get(i);
            }
        }
        return null;
    }
    public User findUserByID(Deposit deposit){

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(deposit.getEmail())){
                return users.get(i);
            }
        }
        return null;
    }
    public User findUserByID(Loan loan){

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(loan.getEmail())){
                return users.get(i);
            }
        }
        return null;
    }

}
