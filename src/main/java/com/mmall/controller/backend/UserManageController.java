package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }


    @RequestMapping(value="login2.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login2(@RequestBody Map<String,String> map, HttpSession session){
        System.out.println("！！！！！！！！要显著！！！！！！！！！！！！！！！！！！！！！！！！！！");
        System.out.println("！！！！！！！！要显著！！！！！！！！！！！！！！！！！！！！！！！！！！");
        System.out.println("！！！！！！！！要显著！！！！！！！！！！！！！！！！！！！！！！！！！！");
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));
        ServerResponse<User> response = iUserService.login(map.get("username"),map.get("password"));
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }
    @RequestMapping(value="login3.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login3(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

    @RequestMapping(value="TestServer.do")
    @ResponseBody
    public ServerResponse TestServer() throws IOException {
        try{
        String str_send = "Hello 我是服务端";
        byte[] buf = new byte[1024];
        //服务端在3000端口监听接收到的数据
        DatagramSocket ds = new DatagramSocket(6900);

        //接收从客户端发送过来的数据
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        String data = "";
        boolean f = true;

        while(f){
            //服务器端接收来自客户端的数据
            ds.receive(dp_receive);
            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();

            data = str_receive;
            System.out.println(str_receive);
            //数据发动到客户端的9000端口
            DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),dp_receive.getAddress(),9000);
            ds.send(dp_send);
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            dp_receive.setLength(1024);


        }
        ds.close();
        return ServerResponse.createBySuccessMessage("成功，回应信息是："+data);
        } catch (SocketException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("未成功！");
        }
    }

}