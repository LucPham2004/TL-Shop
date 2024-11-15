package com.e_shop.Shoe_Shop.dto.response;

import java.util.Date;
import java.util.Set;

import com.e_shop.Shoe_Shop.entity.Role;


public class LoginResponse {
    private UserLogin user;
    private String jwt;

    public LoginResponse(UserLogin user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }
    public LoginResponse() {
    }

    public static class UserLogin {

        private long id;
        private String email;
        private String name;
        private String phone;
        private String address;
        private Date dayCreated;
        private Set<Role> authorities;

        public UserLogin(long id, String email, String name, String phone, String address, Date dayCreated,
                Set<Role> authorities) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.phone = phone;
            this.address = address;
            this.dayCreated = dayCreated;
            this.authorities = authorities;
        }

        public UserLogin() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Set<Role> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Set<Role> authorities) {
            this.authorities = authorities;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Date getDayCreated() {
            return dayCreated;
        }

        public void setDayCreated(Date dayCreated) {
            this.dayCreated = dayCreated;
        }
   }

    public static class UserGetAccount {
        public UserGetAccount() {
        }

        public UserGetAccount(UserLogin user) {
            this.user = user;
        }

        private UserLogin user;

        public UserLogin getUser() {
            return user;
        }

        public void setUser(UserLogin user) {
            this.user = user;
        }
   }

   public static class UserInsideToken {
        public UserInsideToken() {
    }
        public UserInsideToken(long id, String email) {
        this.id = id;
        this.email = email;
    }
        private long id;
        private String email;

        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
   }

    public UserLogin getUser() {
        return user;
    }
    public void setUser(UserLogin user) {
        this.user = user;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
