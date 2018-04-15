package com.nju.bff_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nju.bff_service.restController.info.*;
import com.nju.bff_service.restController.vo.*;
import com.nju.bff_service.util.HttpUtil;
import com.nju.bff_service.util.URLConstant;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class BffService {
    public String health(String url) throws IOException {
        HttpResponse response = HttpUtil.httpGet(url);
        if (response == null) {
            return "fail to http get";
        }
        try {
            return getResponse(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //register
    public int addUser(UserInfo registerInfo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(registerInfo);
            HttpResponse response = null;
            if (registerInfo.getId() == -1) {
                response = HttpUtil.httpPost(s, URLConstant.url_add_user);
            } else {
                response = HttpUtil.httpPost(s, URLConstant.url_update_user);
            }
            if (response == null) return 0;
            return 1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //login
    public int login(LoginInfo loginInfo) throws IOException {
        //return id
        HttpResponse response = HttpUtil.httpGet(URLConstant.url_get_user + loginInfo.getUsername());
        if (response == null || response.getEntity() == null) return -1;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserInfo userInfo = objectMapper.readValue(getResponse(response.getEntity().getContent()), UserInfo.class);
            if (userInfo == null) return -1;
            if (!userInfo.getPassword().equals(loginInfo.getPassword())) return -1;
            return userInfo.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //get order list
    public OrderListVO getOrders(int uid) throws IOException {
        HttpResponse response = HttpUtil.httpGet(URLConstant.url_get_orders + uid);
        ObjectMapper mapper = new ObjectMapper();
        OrderListVO orderListVO = null;
        try {
            if (response == null || response.getEntity() == null) return null;
            orderListVO = mapper.readValue(getResponse(response.getEntity().getContent()), OrderListVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderListVO;
    }

    //get order
    public OrderVO getOrder(int oid) throws IOException {
        HttpResponse response = HttpUtil.httpGet(URLConstant.url_get_order + oid);
        ObjectMapper mapper = new ObjectMapper();
        OrderVO orderVO = null;
        try {
            if (response == null || response.getEntity() == null) return null;
            orderVO = mapper.readValue(getResponse(response.getEntity().getContent()), OrderVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderVO;
    }

    //refund
    public int refund(RefundInfo refundInfo) throws IOException {
        //refund order
        ObjectMapper mapper = new ObjectMapper();
        try {
            String refundString = mapper.writeValueAsString(refundInfo);
            HttpResponse response_refund = HttpUtil.httpPost(refundString, URLConstant.url_refund_order);
            if (response_refund == null || response_refund.getEntity() == null) return 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
        //update pet
        HttpResponse response_order = HttpUtil.httpGet(URLConstant.url_get_order + refundInfo.getOrderId());
        if (response_order == null || response_order.getEntity() == null) return 0;
        try {
            OrderVO orderVO = mapper.readValue(response_order.getEntity().toString(), OrderVO.class);
            for (OrderItemVO orderItemVO : orderVO.getOrderItems()) {
                HttpResponse response_pet = HttpUtil.httpGet(URLConstant.url_get_pet + orderItemVO.getPet_name());
                if (response_pet == null || response_pet.getEntity() == null) return 0;
                try {
                    PetItem petItem = mapper.readValue(response_pet.getEntity().toString(), PetItem.class);
                    int num = petItem.getPet_store();
                    petItem.setPet_store(num + orderItemVO.getPet_num());
                    String petString = mapper.writeValueAsString(petItem);
                    HttpResponse response_pet_update = HttpUtil.httpPost(petString, URLConstant.url_update_pet);
                    if (response_pet_update == null || response_pet_update.getEntity() == null) return 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    //buy
    public int buy(OrderInfo orderInfo) throws IOException {
        //add order
        ObjectMapper mapper = new ObjectMapper();
        try {
            String orderString = mapper.writeValueAsString(orderInfo);
            HttpResponse response_order = HttpUtil.httpPost(orderString, URLConstant.url_buy_order);
            if (response_order == null || response_order.getEntity() == null) return 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
        //update pet
        for (OrderItemInfo orderItemInfo : orderInfo.getOrderItems()) {
            HttpResponse response_pet = HttpUtil.httpGet(URLConstant.url_get_pet + orderItemInfo.getPet_name());
            if (response_pet == null || response_pet.getEntity() == null) return 0;
            try {
                PetItem petItem = mapper.readValue(response_pet.getEntity().toString(), PetItem.class);
                int num = petItem.getPet_store();
                if (orderItemInfo.getPet_num() > num) return 0;
                petItem.setPet_store(num - orderItemInfo.getPet_num());
                String petString = mapper.writeValueAsString(petItem);
                HttpResponse response_pet_update = HttpUtil.httpPost(petString, URLConstant.url_update_pet);
                if (response_pet_update == null || response_pet_update.getEntity() == null) return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 1;
    }

    //getCategories
    public CategoryListVO getCategories() throws IOException {
        HttpResponse response = HttpUtil.httpGet(URLConstant.url_get_category);
        ObjectMapper mapper = new ObjectMapper();
        CategoryListVO categoryListVO = null;
        try {
            if (response == null || response.getEntity() == null) return null;
            categoryListVO = mapper.readValue(getResponse(response.getEntity().getContent()), CategoryListVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryListVO;
    }

    //getPets
    public PetListVO getPets(int cid) throws IOException {
        HttpResponse response = HttpUtil.httpGet(URLConstant.url_get_pets + cid);
        ObjectMapper mapper = new ObjectMapper();
        PetListVO petListVO = null;
        try {
            if (response == null || response.getEntity() == null) return null;
            petListVO = mapper.readValue(getResponse(response.getEntity().getContent()), PetListVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return petListVO;
    }

    private String getResponse(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

//    public static void main(String[] args) {
//        BffService service = new BffService();
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(-1);
//        userInfo.setUsername("cr");
//        userInfo.setPassword("pwd");
//        userInfo.setEmail("1@cr.com");
//        service.addUser(userInfo);
//    }
}
