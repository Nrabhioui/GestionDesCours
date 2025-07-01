package hepl.garage.model.authentification;

public abstract class Authenticator {

    public boolean authenticate(String username, String password) {
        if (isLoginExists(username)) {
            String storedPassword = getPassword(username);
            return storedPassword != null && storedPassword.equals(password);
        }
        return false;
    }

    public boolean CheckChechkBox(String username, String password) {
        if(username.isEmpty() || password.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public boolean SignIn(String username, String password){
        if (isLoginExists(username)) {
           return false;
        }else{
            String addUser = AddUser(username, password);
            return true;
        }
    }

    protected abstract String AddUser(String username, String password);

    protected abstract boolean isLoginExists(String username);

    protected abstract String getPassword(String username);
}