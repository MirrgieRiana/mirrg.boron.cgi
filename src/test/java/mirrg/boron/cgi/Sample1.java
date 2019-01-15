package mirrg.boron.cgi;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class Sample1
{

	public static void main(String[] args) throws Exception
	{
		ContextHandlerCollection handlers = new ContextHandlerCollection();

		// カスタムサーブレット
		{
			ServletContextHandler handler = new ServletContextHandler();
			handler.setContextPath("/sample"); // setContextPathの引数は末尾に/や/*を含めない。ただし/は可能
			handler.addServlet(SampleServlet.class, "/"); // contextPathからの相対パスを絶対パスの形式で記述
			handlers.addHandler(handler);
		}

		// CGIサーブレット
		// リスエストが来た時に、その場所にCGIスクリプトが存在すればハンドルする
		// 複数のCGI言語に対応するにはこのブロックを複数配置すればよい
		{
			CgiContextHandler handler = new CgiContextHandler("/*");
			handler.setContextPath("/");
			handler.getServletHolder().setInitParameter("cgibinResourceBase", "http_home");
			handler.getServletHolder().setInitParameter("commandPrefix", "perl");
			handler.getServletHolder().setInitParameter("cgiBinSuffix", ".pl");
			handlers.addHandler(handler);
		}

		// ファイル転送サーブレット
		// 単純にファイルを転送する
		{
			WebAppContext handler = new WebAppContext();
			handler.setContextPath("/");
			handler.setResourceBase("http_home");
			handlers.addHandler(handler);
		}

		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}

	public static class SampleServlet extends HttpServlet // staticでなければならない
	{

		@Override
		public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
		{
			res.setContentType("text/html");
			res.getWriter().println("Servlet: <b>" + req.getRequestURI() + "</b>");
		}

	}

}
