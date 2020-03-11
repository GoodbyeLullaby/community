package com.ustc.community.interceptor;

import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import com.ustc.community.model.UserExample;
import com.ustc.community.service.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/3/2
 */
@Component
//@Service
public class SessionInterceptor implements HandlerInterceptor {
	@Resource
	private UserMapper userMapper;
	@Resource
	private NotificationService notificationService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//检查用户是否登陆
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length!=0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("token")){
					String token =cookie.getValue();
					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(userExample);
					if(users.size()!=0){
						request.getSession().setAttribute("user", users.get(0));
						Long unreadCount=notificationService.unreadCount(users.get(0).getId());
						request.getSession().setAttribute("unreadCount", unreadCount);
					}
					break;
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
