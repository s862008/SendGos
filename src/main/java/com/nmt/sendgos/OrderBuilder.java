
package com.nmt.sendgos;


public interface OrderBuilder {
    
   public Order build();
   public String getJsonFormat();

    @Override
    public String toString();
   

}
