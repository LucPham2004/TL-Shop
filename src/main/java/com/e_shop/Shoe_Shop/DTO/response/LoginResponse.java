package com.e_shop.Shoe_Shop.dto.response;

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

        public UserLogin(long id, String email, Set<Role> authorities) {
            this.id = id;
            this.email = email;
            this.authorities = authorities;
        }

        public UserLogin() {
        }

        private long id;
        private String email;
        private Set<Role> authorities;


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
