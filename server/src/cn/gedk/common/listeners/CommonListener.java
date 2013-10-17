package cn.gedk.common.listeners;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @className:LoginListener
 * @author ：gedk
 * @descripthion:系统过滤限制
 * @创建时间：2013-9-23
 */
public class CommonListener  implements Filter {
	@Override
	public void destroy() {}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException {
		arg2.doFilter(arg0, arg1);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
