package ra.demo.gen_pass;

import org.mindrot.jbcrypt.BCrypt;

public class GeneratePassword {
    public static void main(String[] args) {
        System.out.println("Password 123456 là: "+ BCrypt.hashpw("123456",BCrypt.gensalt(12)));
    }
}
