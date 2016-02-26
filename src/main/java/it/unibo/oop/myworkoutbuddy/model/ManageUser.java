package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * 
 *
 */
public class ManageUser {

    private List<User> listUser;
    private List<Account> listAccount;
    private Optional<User> currentUser = Optional.empty();
    private Optional<Account> currentAccount = Optional.empty();

    private Body body;
    private String currentBodyZone;

    /**
     * 
     */
    public ManageUser() {
        this.listUser = new ArrayList<>();
        this.listAccount = new ArrayList<>();
        this.body = new Body();
    }

    /**
     * Add a new Account.
     * @param userName String
     * @param password String
     * @param avatar String
     */
    public void addAccount(final String userName, final String password, final String avatar) {
        final Account account = new AccountImpl(userName, password, avatar);
        if (!this.isAccount(account)) {
            this.listAccount.add(account);
            this.currentAccount = Optional.of(account);
        }
    }

    /**
     * Add a new User.
     * @param firstName String
     * @param secondName String
     * @param age Integer
     * @param email String
     */
    public void addUser(final String firstName, final String secondName, final int age, final String email) {
        if (checkCurrentAccount()) {
            final Person person = new PersonImpl(firstName, secondName, age, email);
            final User user = new UserImpl(getCurrentAccount(), person, this.body);
            this.listUser.add(user);
            this.currentUser = Optional.of(user);
        }
    }

    /**
     * give alphabetic name of Current Account.
     * @return a String
     */
    public Optional<String> getCurrentNameAccount() {
        return (this.checkCurrentAccount()) ? Optional.of(this.getCurrentAccount().getUserName()) : Optional.empty();
    }

    /**
     * user's login.
     * @param userName String
     * @param password String
     */
    public void loginUser(final String userName, final String password) {
        this.getUserList().forEach(i -> {
            final Account account = i.getAccount();
            if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
                this.currentUser = Optional.of(i);
                this.currentAccount = Optional.of(account);
            }
        });
    }

    /**
     * the default body.
     */
    public void body() {
        this.body = new Body();
    }

    /**
     * add a new bodyPart mapped in specific set of bodyZone.
     * @param bodyPart String
     * @param bodyZone String
     */
    public void body(final String bodyPart, final String bodyZone) {
        this.body.addMap(bodyZone, bodyPart);
        this.currentBodyZone = bodyZone;
    }

    /**
     * add a new bodyPart mapped in specific set of current bodyZone.
     * @param bodyPart String
     */
    public void body(final String bodyPart) {
        this.body.addMap(this.currentBodyZone, bodyPart);
    }

    /**
     * give the list of Users.
     * @return a List<User>
     */
    public List<User> getUserList() {
        return this.listUser;
    }

    /**
     * set the current user with the given Optional.
     * @param optUser Optional<User>
     */
    protected void setCurrentUser(final Optional<User> optUser) {
        this.currentUser = optUser;
    }

    /**
     * set the current account with the given Optional.
     * @param optAccount Optional<Account>
     */
    protected void setCurrentAccount(final Optional<Account> optAccount) {
        this.currentAccount = optAccount;
    }

    /**
     * true if there is the zone for the given bodyPart.
     * @param bodyPart String
     * @return a boolean
     */
    protected boolean checkBodyPart(final String bodyPart) {
        final boolean ok = this.checkOptValue(this.body.getPartZone(bodyPart));
        if (!ok) {
            System.out.println("\n Body Part not present : " + bodyPart);
        }
        return ok;
    }

    /**
     * give the current account.
     * @return a Account
     */
    protected Account getCurrentAccount() {
        return this.currentAccount.get();
    }

    /**
     * give the current user.
     * @return a User
     */
    protected User getCurrentUser() {
        return this.currentUser.get();
    }

    /**
     * give the class body.
     * @return a Body
     */
    protected Body getBody() {
        return this.body;
    }

    /**
     * true if current account exists.
     * @return boolean
     */
    protected boolean checkCurrentAccount() {
        final boolean ok = this.checkOptValue(this.currentAccount);
        if (!ok) {
            System.out.println("\n Account not set");
        }
        return ok;
    }

    /**
     * true if current user exists.
     * @return a boolean
     */
    protected boolean checkCurrentUser() {
        final boolean ok = this.checkOptValue(this.currentUser);
        if (!ok) {
            System.out.println("\n User not set");
        }
        return ok;
    }

    /**
     * return true if the account param is present.
     * @param account Account
     * @return a boolean
     */
    protected boolean isAccount(final Account account) {
        return !this.listAccount.stream().noneMatch(i->i.equals(account));
    }

    /**
     * @param <X> generic date
     * @param optvalue Optional<X>
     * @return a boolean
     */
    protected<X> boolean checkOptValue(final Optional<X> optvalue) {
        return optvalue.isPresent();
    }
}
