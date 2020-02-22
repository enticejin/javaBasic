package com.uwbhome.pm.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.uwbhome.pm.security.AuthenticationInterface;
import com.uwbhome.pm.security.SessionProvider;
import com.uwbhome.pm.utils.RtleRequestUtils;
import com.uwbhome.pm.utils.RtleUser;

/**
 * Sys上下文信息拦截器
 * 
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger
			.getLogger(AdminContextInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//System.out.println("测试：preHandle  "+request.getRequestURL() );
		// 获得用户
		
		RtleUser user = null;
		// 正常状态
		//查看session中是否存在该用户
		String userId = authMng.retrieveUserIdFromSession(session, request);
		
		// 此时用户可以为null
		RtleRequestUtils.setUser(request, user);
		String uri = getURI(request);
		// 不在验证的范围内
		if (exclude(uri)) {
			return true;
		}
		// 用户为null跳转到登陆页面
		if (userId == null) {
			response.sendRedirect(getLoginUrl(request));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		System.out.println("测试：postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("测试：afterCompletion");
	}

	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
//		buff.append(RETURN_URL).append("=").append(returnUrl);
//		if (!StringUtils.isBlank(processUrl)) {
//			buff.append("&").append(PROCESS_URL).append("=").append(
//					getProcessUrl(request));
//		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 0;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"path is illegal "
							+ uri);
		}
		return uri.substring(start);
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean permistionPass(String uri, Set<String> perms,
			boolean viewOnly) {
		String u = null;
		int i;
		for (String perm : perms) {
			if (uri.startsWith(perm)) {
				// 只读管理员
				if (viewOnly) {
					// 获得最后一个 '/' 的URI地址。
					i = uri.lastIndexOf("/");
					if (i == -1) {
						throw new RuntimeException("uri must start width '/':"
								+ uri);
					}
					u = uri.substring(i + 1);
					// 操作型地址被禁止
					if (u.startsWith("o_")) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private SessionProvider session;
	private AuthenticationInterface authMng;
	
	
	private boolean auth = true;
	private String[] excludeUrls;

	private String loginUrl;
	private String processUrl;
	private String returnUrl;

	@Autowired
	public void setSession(SessionProvider session) {
		this.session = session;
	}

	

	

	@Autowired
	public void setAuthMng(AuthenticationInterface authMng) {
		this.authMng = authMng;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}