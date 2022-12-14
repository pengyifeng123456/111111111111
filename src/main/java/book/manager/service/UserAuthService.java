package book.manager.service;

import book.manager.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAuthService implements UserDetailsService {
    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //然后我们需要创建个Service实现， 实现的是UserDetailsService, 它支持我们自己返回一个UserDetails对象， 我们只需直接返回一个
        //包含数据库中的用户名、密码等信息的UserDetails即可，SpringSecurity会 自动进行比对。
        String password = mapper.getPassWordByUsername(s); //从数据库 根据用户名获取密码
        if (password == null)
            throw new UsernameNotFoundException(" 登录失败，用户名或密码错误! ");
        return User //这里需要返回UserDetails, SpringSecurity会根据给定的信息进行比对
                .withUsername(s)
                .password(password) //直接从数据库取的密码
                .roles("user") //用户角色
                .build();
    }
}
