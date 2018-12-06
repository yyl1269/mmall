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
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

    @RequestMapping(value="TestClient.do")
    @ResponseBody
    public ServerResponse TestClient() throws IOException {
        int TIMEOUT = 5000;  //设置接收数据的超时时间
        int MAXNUM = 5;      //设置重发数据的最多次数
        String str_send = "Hello UDPserver";
        byte[] buf = new byte[1024];

        DatagramSocket ds = new DatagramSocket();
        //InetAddress loc = InetAddress.getLocalHost();

        InetAddress loc = InetAddress.getByName("120.76.210.158");
        System.out.println(loc);

        //定义用来发送数据的DatagramPacket实例
        DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),loc,6910);
        //定义用来接收数据的DatagramPacket实例
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        ds.setSoTimeout(TIMEOUT);              //设置接收数据时阻塞的最长时间
        int tries = 0;                         //重发数据的次数
        boolean receivedResponse = false;     //是否接收到数据的标志位
        //直到接收到数据，或者重发次数达到预定值，则退出循环
        while(!receivedResponse && tries<MAXNUM){
            //发送数据
            ds.send(dp_send);
            try{
                //接收从服务端发送回来的数据
                ds.receive(dp_receive);
                //如果接收到的数据不是来自目标地址，则抛出异常

                //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                receivedResponse = true;
            }catch(InterruptedIOException e){
                //如果接收数据时阻塞超时，重发并减少一次重发的次数
                tries += 1;
                System.out.println("Time out," + (MAXNUM - tries) + " more tries..." );
            }
        }
        if(receivedResponse){
            //如果收到数据，则打印出来
            System.out.println("client received data from server：");
            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
            System.out.println(str_receive);
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            dp_receive.setLength(1024);
        }else{
            //如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
            System.out.println("No response -- give up.");
        }
        ds.close();

        return ServerResponse.createBySuccessMessage("成功");
    }



    @RequestMapping(value="TestClient2.do")
    @ResponseBody
    public ServerResponse TestClient2() throws IOException {
        int TIMEOUT = 5000;  //设置接收数据的超时时间
        int MAXNUM = 5;      //设置重发数据的最多次数
        String str_send = "Hello UDPserver";
        byte[] buf = new byte[1024];

        DatagramSocket ds = new DatagramSocket();
        //InetAddress loc = InetAddress.getLocalHost();

        InetAddress loc = InetAddress.getByName("118.31.62.7");
        System.out.println(loc);

        //定义用来发送数据的DatagramPacket实例
        DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),loc,6900);
        //定义用来接收数据的DatagramPacket实例
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        ds.setSoTimeout(TIMEOUT);              //设置接收数据时阻塞的最长时间
        int tries = 0;                         //重发数据的次数
        boolean receivedResponse = false;     //是否接收到数据的标志位
        //直到接收到数据，或者重发次数达到预定值，则退出循环
        while(!receivedResponse && tries<MAXNUM){
            //发送数据
            ds.send(dp_send);
            try{
                //接收从服务端发送回来的数据
                ds.receive(dp_receive);
                //如果接收到的数据不是来自目标地址，则抛出异常

                //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                receivedResponse = true;
            }catch(InterruptedIOException e){
                //如果接收数据时阻塞超时，重发并减少一次重发的次数
                tries += 1;
                System.out.println("Time out," + (MAXNUM - tries) + " more tries..." );
            }
        }
        if(receivedResponse){
            //如果收到数据，则打印出来
            System.out.println("client received data from server：");
            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
            System.out.println(str_receive);
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            dp_receive.setLength(1024);
        }else{
            //如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
            System.out.println("No response -- give up.");
        }
        ds.close();

        return ServerResponse.createBySuccessMessage("成功");
    }
}