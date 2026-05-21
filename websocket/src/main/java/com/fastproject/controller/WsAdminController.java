package com.fastproject.controller;

import com.fastproject.domain.Application;
import com.fastproject.domain.User;
import com.fastproject.repository.ApplicationRepository;
import com.fastproject.repository.UserRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.fastproject.utils.vo.ResultVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class WsAdminController {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

//    private static final Cache<String, Integer> loginAttemptsCache = Caffeine.newBuilder()
//            .expireAfterWrite(30, TimeUnit.SECONDS)
//            .build();

    private final Cache<String, Integer> loginAttemptsCache = Caffeine.newBuilder()
            .maximumSize(10)                // 100万条，适合50万在线用户
            .expireAfterWrite(1, TimeUnit.MINUTES) // 1分钟过期
            .initialCapacity(1)               // 初始化容量
            .recordStats()
            .build();


    @GetMapping
    public String index(HttpSession session, Model model) {
        // 1. 检查是否有用户
        if (userRepository.count() == 0) {
            // 如果没有用户，跳转到创建账号页面
            return "redirect:/register";
        }

        // 2. 检查是否登录
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            // 如果没登录，跳转到登录页面
            return "redirect:/login";
        }

        return "index";
    }

    @GetMapping("/api/session")
    @ResponseBody
    public ResultVo<User> getSession(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResultVo.fail("未登录");
        }
        return ResultVo.success(loginUser);
    }

    @GetMapping("/api/apps")
    @ResponseBody
    public ResultVo<List<Application>> getApps(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResultVo.fail("未登录");
        }
        return ResultVo.success(applicationRepository.findAll());
    }

    @GetMapping("/register")
    public String registerPage() {
        // 如果已经有用户了，不允许再注册第一个管理员，直接跳到登录
        if (userRepository.count() > 0) {
            return "redirect:/login";
        }
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // 1. 如果没有用户，先去注册
        if (userRepository.count() == 0) {
            return "redirect:/register";
        }
        // 2. 如果已经登录，直接跳到首页
        if (session.getAttribute("loginUser") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultVo<User> login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpServletRequest request,
                        HttpSession session) {
        String clientIp = request.getRemoteAddr();
        Integer attempts = loginAttemptsCache.getIfPresent(clientIp);
        if (attempts == null) {
            attempts = 0;
        }

        if (attempts >= 10) {
            return ResultVo.fail("登录太快啦，请30秒后再试");
        }

        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();

        if (userOpt.isPresent()) {
            loginAttemptsCache.invalidate(clientIp);
            session.setAttribute("loginUser", userOpt.get());
            return ResultVo.success(userOpt.get());
        } else {
            loginAttemptsCache.put(clientIp, attempts + 1);
            return ResultVo.fail("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultVo<User> register(@RequestParam String username, 
                           @RequestParam String password, 
                           HttpSession session) {
        // 只有当没有用户时才允许注册第一个管理员
        if (userRepository.count() == 0) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            User savedUser = userRepository.save(newUser);
            // 注册成功后自动登录
            session.setAttribute("loginUser", savedUser);
            return ResultVo.success(savedUser);
        }
        return ResultVo.fail("系统已初始化");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/login";
    }
}
