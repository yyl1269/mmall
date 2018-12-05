package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
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

        try {
            ServerSocket serverSocket=new ServerSocket(3000);
            System.out.println("服务端已启动，等待客户端连接..");
            Socket socket=serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象


            //根据输入输出流和客户端连接
            InputStream inputStream=socket.getInputStream();//得到一个输入流，接收客户端传递的信息
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);//提高效率，将自己字节流转为字符流
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);//加入缓冲区
            String temp=null;
            String info="";
            while((temp=bufferedReader.readLine())!=null){
                info+=temp;
                System.out.println("已接收到客户端连接");
                System.out.println("服务端接收到客户端信息："+info+",当前客户端ip为："+socket.getInetAddress().getHostAddress());
            }

            OutputStream outputStream=socket.getOutputStream();//获取一个输出流，向服务端发送信息
            PrintWriter printWriter=new PrintWriter(outputStream);//将输出流包装成打印流
            printWriter.print("你好，服务端已接收到您的信息");
            printWriter.flush();
            socket.shutdownOutput();//关闭输出流



            //关闭相对应的资源
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStream.close();
            socket.close();

            return ServerResponse.createBySuccessMessage("成功，回应信息是："+info);


        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("失败");

        }
    }

}