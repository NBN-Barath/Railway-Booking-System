public class Accounts {
    protected String name; // store name
    protected String id; // store id
    protected String password; // store password
    protected String phone_number; // store phone number
    protected String email_id; // store email id


    public Accounts(String name, String id, String password, String phoneNumber, String emailId) { // constructor
        this.name=name;
        this.id=id;
        this.password=password;
        this.phone_number = phoneNumber;
        this.email_id = emailId;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }
    @Override
    public String toString(){
        return "Name : "+ name+" \n Id : "+id+" \n Phone Number : "+phone_number+" \n Email Id : "+email_id;
    }
}

