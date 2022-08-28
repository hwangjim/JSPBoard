package ctrl;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EncFilter
 */
// .do .jsp 모두에게 이 필터가 적용되게끔 
@WebFilter({"*.do", "*.jsp"})
public class EncFilter extends HttpFilter implements Filter {
       
	private String encoding;
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    public EncFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// request든 response든 init메소드에 있는 encoding정보를 가져온다.
		request.setCharacterEncoding(this.encoding);
		response.setCharacterEncoding(this.encoding);
		System.out.println("필터 실행");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// init 메소드는 최초 1회만 실행
		// web.xml 파일에서 context를 get하는데 이때 xml에선 paramname엔 encoding
		// paramvalue에선 UTF-8을 가지고 있기 떄문에 이를 끌어온다.!
		this.encoding=fConfig.getServletContext().getInitParameter("encoding");
		System.out.println("필터 생성");
	}

}