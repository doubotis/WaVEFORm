/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import be.doubotis.http.exceptions.HTTPException;
import be.doubotis.http.exceptions.InternalErrorException;
import be.doubotis.http.exceptions.ResourceNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Christophe
 */
public class ServletController extends HttpServlet implements ServletControllerContext {
    
    private HTTPException mException = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, 
            IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        // Get the Controller Scanner.
        ControllerScanner cs = ControllerScanner.fromContext(
                request.getServletContext());
        
        try
        {
            // Get the page to display.
            String contextPath = request.getContextPath();
            String pathInfo = request.getRequestURI()
                    .replaceFirst(contextPath, "");
            String page = pathInfo.substring(1);
            if (page.equals("")) page = "index.html";

            // Build the web page.
            Controller c = cs.buildControllerForURI(page);
            if (c == null)
            {
                // Unable to get a controller class.
                // We assume this because the page doesn't exists, so let's 
                // display a 404 error page.
                throw new ResourceNotFoundException();
            }
            
            c.setParams(this, request, response);
            
            // Build with template.
            Template tpl = new Template();
            c.build(tpl);
            
            // Run the JSP pages.
            runJSPExecution(request, response, c, tpl);
            
        }
        catch (HTTPException httpException)
        {
            response.setStatus(httpException.getHTTPStatus());
            mException = httpException;
            Controller c = cs.buildControllerForException(httpException);
            if (c == null)
            {
                // No controllers found for this exception.
                // We send a default 500 error page.
                response.sendError(httpException.getHTTPStatus());
                return;
            }
            
            c.setParams(this, request, response);
            Template tpl = new Template();
            c.build(tpl);
            runJSPExecutionSafe(request, response, c, tpl);
            
        } catch (Exception e)
        {
            HTTPException httpException = new InternalErrorException(e);
            mException = httpException;
            Controller c = cs.buildControllerForException(httpException);
            if (c == null)
            {
                // No controllers found for this exception.
                // We send a default 500 error page.
                response.sendError(httpException.getHTTPStatus());
                return;
            }
            
            c.setParams(this, request, response);
            Template tpl = new Template();
            c.build(tpl);
            runJSPExecutionSafe(request, response, c, tpl);
        }
        
    }
    
    protected void runJSPExecution(HttpServletRequest request, 
            HttpServletResponse response, Controller c, Template tpl)
            throws Exception
    {
        
        HashMap<String, Object> hm = tpl.getAssignations();
        Iterator<String> it = hm.keySet().iterator();
        while (it.hasNext())
        {
            String key = it.next();
            Object value = hm.get(key);
            request.setAttribute(key, value);
        }
        
        if (tpl.getLocale() != null)
            request.getServletContext().setAttribute("lang", 
                    tpl.getLocale());
        
        if (tpl.getResourceBundle() != null)
            request.getServletContext().setAttribute("resourceBundle", 
                    tpl.getResourceBundle());

        request.getRequestDispatcher(tpl.getDisplayPage())
                .include(request, response);
    }
    
    private void runJSPExecutionSafe(HttpServletRequest request, 
            HttpServletResponse response, Controller c, Template tpl)
    {
        try
        {
            runJSPExecution(request, response, c, tpl);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try {
                response.sendError(500);
                
            } catch (Exception fatalException)
            {
                fatalException.printStackTrace();
                response.setStatus(500);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public HTTPException getException() {
        return mException;
    }

    

}
