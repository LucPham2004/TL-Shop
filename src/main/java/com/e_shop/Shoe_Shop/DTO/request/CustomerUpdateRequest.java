package com.e_shop.Shoe_Shop.DTO.request;

public class CustomerUpdateRequest {
    private Integer id;
        private String email;
        private String name;
        private String phone;
        private String address;

        @Override
        public String toString() {
            return "UpdateUserInfoRequest [id=" + id + ", email=" + email + ", name=" + name + ", phone=" + phone
                    + ", address=" + address + "]";
        }

        public CustomerUpdateRequest(Integer id, String email, String name, String phone, String address) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.phone = phone;
            this.address = address;
        }

        public CustomerUpdateRequest() {
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
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
}
