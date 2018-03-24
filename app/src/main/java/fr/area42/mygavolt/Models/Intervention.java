package fr.area42.mygavolt.Models;

import java.util.Date;

/**
 * Created by allardk on 02/02/2018.
 */

public class Intervention {
    public int id;
    public Date date;
    public String report;

    public addressCustomer addressCustomer;
    public Employee employee;
    public Motive motive;

    public static class addressCustomer {
        public int id;
        public String streetNumber;
        public String streetName;
        public String zipCode;
        public String city;

        public Contact contact;
        public Customer customer;

        public static class Contact {
            public int id;
            public String firstname;
            public String lastname;
            public String email;
            public String phone;
            public String mobile;
        }

        public static class Customer {
            public int id;
            public String businessName;
            public String lastname;
            public String firstname;
            public String email;
            public String phone;
            public String mobile;
        }
    }

    public static class Employee {
        public int id;
        public String lastname;
        public String firstname;
    }

    public static class Motive {
        public int id;
        public String label;
    }
}
