package org.simulate.dw.util.bcrypt;

import org.junit.Test;

/**
 *
 */
public class BCryptPasswordEncoderTest {

    @Test
    public void encode() {
        String password = "123456";

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encode = encoder.encode(password);

        System.out.println(encode);

        System.out.println(encoder.matches(password,"$2a$10$BudAEMMZvZtwHG3QA7G5Au3154zdyjVHAAhphlQjLDUDDR9VtOHqm"));
    }
}