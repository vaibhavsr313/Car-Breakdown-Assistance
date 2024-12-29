package com.example.abreak;

public class Users {

   private String phone,password,name;

   public Users()
   {

   }

   public Users(String phone, String password, String name) {
      this.phone = phone;
      this.password = password;
      this.name = name;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
