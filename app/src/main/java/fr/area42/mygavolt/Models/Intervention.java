package fr.area42.mygavolt.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by allardk on 02/02/2018.
 */

public class Intervention {
    public int id;
    public Date date;
    public String report;
    public Date createdAt;
    public Date updatedAt;

    public List<AddressCustomer> addressCustomer;
    public List<Employee> employee;
    public List<Motive> motive;

    public static class AddressCustomer {
        public int id;
        public String streetNumber;
        public String streetName;
        public String zipCode;
        public String city;
        public String country;
        public Date createdAt;
        public Date updatedAt;

        public Contact contact;
        public Customer customer;

        public static class Contact {
            public int id;
            public String firstname;
            public String lastname;
            public String email;
            public String phone;
            public String mobile;
            public Date createdAt;
            public Date updatedAt;
        }

        public static class Customer {
            public int id;
            public String businessName;
            public String lastname;
            public String firstname;
            public String email;
            public String phone;
            public String mobile;
            public Date createdAt;
            public Date updatedAt;
            public Boolean isactive;
        }
    }

    public static class Employee {
        public int id;
        public String lastname;
        public String firstname;
        public String socialSecurityNumber;
        public String email;
        public String phone;
        public String mobile;
        public String maritalStatus;
        public Date birthdate;
        public Date arrivalDate;
        public String bankAccount;
        public String streetNumber;
        public String streetName;
        public String zipCode;
        public String city;
        public String country;
        public Date createdAt;
        public Date updatedAt;
        public boolean isactive;
    }

    public static class Motive {
        public int id;
        public String label;
        public Date createdAt;
        public Date updatedAt;
    }

}
