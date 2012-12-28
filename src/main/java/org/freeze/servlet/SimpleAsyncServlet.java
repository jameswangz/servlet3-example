package org.freeze.servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(name = "simpleasync", urlPatterns = {"/simpleasync"}, asyncSupported = true)
public class SimpleAsyncServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        final AsyncContext ctx = req.startAsync();
        ctx.setTimeout(30000L);
        ctx.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent event) throws IOException {
                log("completed");
            }

            public void onTimeout(AsyncEvent event) throws IOException {
                log("timeout");
            }

            public void onError(AsyncEvent event) throws IOException {
                log("error");
            }

            public void onStartAsync(AsyncEvent event) throws IOException {
                log("async started");
            }
        });
        
        ctx.start(new Runnable() {
            public void run() {
                try {
                    ctx.getResponse().getWriter().write(String.format("<h1>Processing task in back ground thread:[%s]</h1>", Thread.currentThread().getId()));
                } catch (IOException e) {
                    log("Problem processing task", e);
                }
                ctx.complete();
            }
        });

    }




}
