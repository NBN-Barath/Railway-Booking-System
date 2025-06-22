import java.util.ArrayList;
import java.util.List;

public class AccountListGeneric<A extends Accounts> {
    private List<A> accounts = new ArrayList<>();

    public void add(A a){
        if (a != null) {
            for (A account : accounts ) {
                if (account.equals(a)) {
                    System.out.println("Sorry, Already exist");
                } else {
                    accounts.add(a);
                }
            }
        } else {
            System.out.println("Error: Account is null...");
        }
    }
    public List<A> getAccounts(){
        return accounts;
    }

    public Boolean equals(A a1,A a2){
        if(a1.equals(a2)){
            return true;
        }
        else {
            return false;
        }
    }

    public void remove(A a1){
        if(a1.equals(accounts)){
            accounts.remove(a1);
        }
        else {
            System.out.println("No value found");
        }
    }

}
