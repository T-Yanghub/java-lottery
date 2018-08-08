package com.itheima.thymeleaf.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.thymeleaf.Util.BallRandom;
import com.itheima.thymeleaf.Util.IdUtils;
import com.itheima.thymeleaf.Util.SMSUtils;
import com.itheima.thymeleaf.Util.UploadUtils;
import com.itheima.thymeleaf.bean.User;
import com.itheima.thymeleaf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.apache.catalina.startup.ClassLoaderFactory.RepositoryType.DIR;

/**
 * ClassName:UserController
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //去注册页面
    @GetMapping("/to_register")
    public String to_register() {
        System.out.println("wowo");
        return "register";
    }

    @PostMapping("/sendCode")
    @ResponseBody
    public String sendCode(String mobile, HttpSession session) {
        String code = "";
        try {
            code = SMSUtils.sendSms(mobile);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        session.setAttribute("code", code);
        return code;
    }

    //注册
    @PostMapping("/do_register")
    public String do_register(User user, String code, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object s_code = session.getAttribute("code");
        if (code.equals(s_code)) {
           // System.out.println("hhh");
            user.setMoney(100);
            user.setState(1);
            user.setHeadimg("icon.jpg");
            user.setUid(IdUtils.getId());

            userService.save_user(user);
            return "redirect:/user/to_login";
        }

        model.addAttribute("user", user);
        model.addAttribute("massige", "验证码错误");
        return "register";
    }

    @GetMapping("/to_login")
    public String to_login() {

        return "login";
    }

    @PostMapping("/do_login")
    public String do_login(String email, String password, HttpSession session, Model model) {
        User user = userService.findOne(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            String url = (String) session.getAttribute("url");
            if (url != null) {
                return "redirect:" + url;
            }

            return "redirect:/";
        }
        model.addAttribute("msg", "密码或邮箱错误");
        return "login";
    }

    @GetMapping("/to_myParge")
    public String toMyParge(HttpServletRequest request, HttpSession session) {
     /*   System.out.println("hhhh");
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");*/
        List<String> randomRed = BallRandom.randomRed();
        String randomBlue = BallRandom.randomBlue();
        String luckeyBall = "";
        for (String s : randomRed) {
            luckeyBall += s + ",";
        }
        luckeyBall += randomBlue;
        session.setAttribute("lukeyBall", luckeyBall);

        return "my";
    }

    /*修改密码*/
    @GetMapping("to_recode")
    public String toReCode() {


        return "reCode";
    }

    @PostMapping("do_recode")
    public String doReCode(String newCode, String reNewCode, HttpSession session, Model model) {
        if (newCode == null || newCode.length() < 2 || reNewCode == null || reNewCode.length() < 2) {
            model.addAttribute("msg1", "请重新输入");

            return "reCode";
        }
        if (!newCode.equals(reNewCode)) {
            model.addAttribute("msg2", "两次输入的不一样");
            return "reCode";
        }
        User user = (User) session.getAttribute("user");

        user.setPassword(newCode);

        userService.save_user(user);

        return "redirect:/user/to_login";
    }

    /*退出登录*/
    @GetMapping("out_login")
    public String outLogin(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /*更换头像*/

    @GetMapping("to_reHeadimg")
    public String toReHeadimg() {
        return "reHeadimg";
    }

    /*图片上传*/
    @PostMapping("/upload")


    public String upload2(MultipartFile file, HttpSession session) throws IOException {
        //获取input中name属性的值
        String name = file.getName();
        //获取上传的文件名
        String filename = file.getOriginalFilename();

        //解决IE浏览器包含文件路径的问题
        //  String realName = UploadUtils.getRealName(filename);

        //解决文件有可能重名的问题
        String uuidName = UploadUtils.getUUIDName(filename);

        //解决一个目录下文件太多的问题,目录分离
       // String randomDir = UploadUtils.getDir();

        //判断目标文件夹是否已经存在
        // File dir = new File(DIR+randomDir);
       // File dir = new File("/img/");
      //  if (!dir.exists()) {
            //注意最后的这个丝 s 表示的是若父级目录不存在,则一起创建
      //      dir.mkdirs();
      //  }

        //构建目标文件对象
        File destFile = new File("D:/headimg",uuidName);/*("D:\\springcode\\thymeleaf\\src\\main\\resources\\static\\img", uuidName);*/
        System.out.println(destFile);
        //将上传的文件写到目标文件中
        file.transferTo(destFile);
        User user = (User) session.getAttribute("user");

        user.setHeadimg(uuidName);
        userService.saveWithOutPassword(user);



        return "redirect:/user/to_myParge";
    }
   /* @GetMapping("to_bbs")
    public String to_bbs() {
        return "bbs";
    }*/

    /*图片访问*/
    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/get2/{dir1}/{dir2}/{filename:.+}")
    @ResponseBody
    public ResponseEntity get2(@PathVariable String dir1,
                               @PathVariable String dir2,
                               @PathVariable String filename) {
        //获取文件的路径
        Path path = Paths.get(DIR + "/" + dir1 + "/" + dir2, filename);
        System.out.println(path);
        //使用资源加载器将资源读取进来,其中 file: 是文件协议的前缀,主要用于访问本地计算机资源
        Resource resource = resourceLoader.getResource("file:" + path.toString());

        return ResponseEntity.ok(resource);

    }
    @GetMapping("/get/{filename:.+}")
    @ResponseBody
    public ResponseEntity get2(@PathVariable String filename) {
        //获取文件的路径
        Path path = Paths.get("D:/headimg",filename);
        System.out.println(path);
        //使用资源加载器将资源读取进来,其中 file: 是文件协议的前缀,主要用于访问本地计算机资源
        Resource resource = resourceLoader.getResource("file:" + path.toString());

        return ResponseEntity.ok(resource);

    }
}
