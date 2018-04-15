package com.nju.bff_service.restController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.nju.bff_service.restController.info.*;
import com.nju.bff_service.restController.vo.*;
import com.nju.bff_service.service.BffService;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bff")
public class BffController {
    @Autowired
    BffService bffService;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResultVO health(){
        return new ResultVO("health is ok!", 1);
    }

    @RequestMapping(value = "/remote/health", method = RequestMethod.GET)
    public ResultVO remoteHealth() throws IOException {
        return new ResultVO(bffService.health("http://127.0.0.1:8000/cart/health"), 1);
    }

    //register
    @HystrixCommand(fallbackMethod = "registerFallback")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVO register(@RequestBody UserInfo registerInfo) throws IOException {
        if (bffService.addUser(registerInfo) == 1){
            return new ResultVO("success", 1);
        }
        return new ResultVO("fail", 0);
    }

    //login
    @HystrixCommand(fallbackMethod = "loginFallback")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO login(@RequestBody LoginInfo loginInfo, HttpSession session) throws IOException {
        int id = bffService.login(loginInfo);
        if (id >= 0){
            session.setAttribute("userId", id);
            return new ResultVO("success", 1);
        }
        return new ResultVO("fail", 0);
    }

    //list my orders
    @HystrixCommand(fallbackMethod = "getOrdersFallback")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public OrderListVO getOrders(HttpSession session) throws IOException {
        int uid = (Integer) session.getAttribute("userId");
        return bffService.getOrders(uid);
    }

    //order detail
    @HystrixCommand(fallbackMethod = "getOrderFallback")
    @RequestMapping(value = "/order/{oid}", method = RequestMethod.GET)
    public OrderVO getOrder(@PathVariable Integer oid) throws IOException {
        return bffService.getOrder(oid);
    }

    //refund
    @HystrixCommand(fallbackMethod = "refundFallback")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResultVO refund(@RequestBody RefundInfo refundInfo) throws IOException {
        if (bffService.refund(refundInfo) == 1){
            return new ResultVO("success", 1);
        }
        return new ResultVO("fail", 0);
    }

    //buy
    @HystrixCommand(fallbackMethod = "buyFallback")
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ResultVO buy(@RequestBody OrderInfo orderInfo) throws IOException {
        if (bffService.buy(orderInfo) == 1){
            return new ResultVO("success", 1);
        }
        return new ResultVO("fail", 0);
    }

    //list category
    @HystrixCommand(fallbackMethod = "getCategoryFallback")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public CategoryListVO getCategory() throws IOException {
        return bffService.getCategories();
    }

    //list pets
    @HystrixCommand(fallbackMethod = "getPetFallback")
    @RequestMapping(value = "/pets/{cid}", method = RequestMethod.GET)
    public PetListVO getPet(@PathVariable Integer cid) throws IOException {
        return bffService.getPets(cid);
    }

    //update my info
    @HystrixCommand(fallbackMethod = "updateUserFallback")
    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    public ResultVO updateUser(@RequestBody UserInfo userInfo) throws IOException {
        if (bffService.addUser(userInfo) == 1){
            return new ResultVO("success", 1);
        }
        return new ResultVO("fail", 0);
    }

    public ResultVO registerFallback(UserInfo registerInfo){
        return new ResultVO("注册用户超时", 0);
    }

    public ResultVO loginFallback(LoginInfo loginInfo, HttpSession session){return new ResultVO("登录超时", 0);}

    public OrderListVO getOrdersFallback(HttpSession session){
        List<OrderVO> list=new ArrayList<>();
        OrderListVO orderListVO=new OrderListVO();
        OrderVO orderVO=new OrderVO();
        orderVO.setBill("默认详单");
        orderVO.setDescription("默认描述");
        orderVO.setOrderItems(null);
        orderVO.setPrice(-1);
        orderVO.setStatus(-1);
        list.add(orderVO);
        orderListVO.setOrders(list);
        return orderListVO;
    }

    public OrderVO getOrderFallback(Integer oid){
        OrderVO orderVO=new OrderVO();
        orderVO.setBill("默认详单");
        orderVO.setDescription("默认描述");
        orderVO.setOrderItems(null);
        orderVO.setPrice(-1);
        orderVO.setStatus(-1);
        return orderVO;
    }

    public ResultVO refundFallback(RefundInfo refundInfo){
        return new ResultVO("退货超时",0);
    }
    public ResultVO buyFallback(OrderInfo orderInfo){
        return new ResultVO("购买失败",0);
    }
    public CategoryListVO getCategoryFallback(){
        List<CategoryItem> list=new ArrayList<>();
        CategoryListVO categoryListVO=new CategoryListVO();
        CategoryItem categoryItem=new CategoryItem();
        categoryItem.setId(-1);
        categoryItem.setDept("默认宠物种类");
        categoryItem.setName("默认类别名称");
        list.add(categoryItem);
        categoryListVO.setCategoryInfos(list);
        return categoryListVO;
    }
    public PetListVO getPetFallback(Integer cid){
        List<PetItem> list=new ArrayList<>();
        PetListVO petListVO=new PetListVO();
        PetItem petItem=new PetItem();
        petItem.setId(-1);
        petItem.setCategory_id(-1);
        petItem.setPet_name("默认宠物名称");
        petItem.setPet_price(-1);
        petItem.setPet_store(-1);
        list.add(petItem);
        petListVO.setPetItems(list);
        return petListVO;
    }
    public ResultVO updateUserFallback(UserInfo userInfo){
        return new ResultVO("账户修改失败",0);
    }


}
