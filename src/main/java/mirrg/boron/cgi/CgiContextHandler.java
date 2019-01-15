package mirrg.boron.cgi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * CGIスクリプトが存在しない場合はハンドルしません。
 */
public class CgiContextHandler extends ServletContextHandler
{

	private CgiServlet cgiServlet;
	private ServletHolder servletHolder;

	public CgiContextHandler(String pathSpec)
	{
		cgiServlet = new CgiServlet();
		servletHolder = new ServletHolder(cgiServlet);
		addServlet(servletHolder, pathSpec);
	}

	public CgiServlet getCgiServlet()
	{
		return cgiServlet;
	}

	public ServletHolder getServletHolder()
	{
		return servletHolder;
	}

	@Override
	public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		if (!cgiServlet.canRun(request, response)) return;
		super.doScope(target, baseRequest, request, response);
	}

	@Override
	public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		if (!cgiServlet.canRun(request, response)) return;
		super.doHandle(target, baseRequest, request, response);
	}

}
