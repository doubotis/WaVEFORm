# java-website-mvc
MVC Wrapper for Java Websites

## Installation Guide

### Step 1 : Prerequisites
Only works inside a Web Java EE Project.

### Step 2 : Setup the web.xml
The `web.xml` configuration file must be completed as this :

```
    <servlet>
        <servlet-name>ServletController</servlet-name>
        <servlet-class>be.doubotis.mvc.core.ServletController</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ServletController</servlet-name>
        <url-pattern>*.html</url-pattern>
    </servlet-mapping>
```

This means all .HTML files on the site will be managed by the `ServletController`.
This servlet will serve all the webpages you need to display to the user.

### Step 3 : Add a setup file
The mechanism uses a setup file, named `setup.xml` that must be located in `/WEB-INF/setup.xml`.
Here's an example of setup file :
```
<setup>
    <controllers location="org.mdl.web.pages" />
    <errors>
        <error code="404" location="org.mdl.web.pages.ErrorPage" />
        <error code="500" location="org.mdl.web.pages.ErrorPage" />
        <error code="*" location="org.mdl.web.pages.ErrorPage" />
    </errors>
</setup>
```
the `controllers` tag, with the `location` attribute defines where all controllers are set.
That means all controllers must be in the same package.

The `errors` tag is used to display error web pages.
For each kind of error, a `<error>` tag is created, with a `code` attribute (the HTTP error code) and a `location` attribute to set the controller class the system must use.

### Step 4 : Create a controller
In this MVC system, each webpage is an unique controller.
For instance if you want to manage the webpage index.html, you must create a new controller.
```
@ControllerPath(path = "index.html")
public class WelcomePage extends Controller
{
  @Override
  public void build(Template tpl)
  {
      // What you want to do.
  }
}
```

This example creates a `WelcomePage` controller that is mapped to `index.html`.
Each time the `index.html` page is asked, the system will use this controller to serve the result.
Each controller subclass must implement the `build(Template)` method.

### Step 5 : Manages the template
The `Template` variable serves as an interface between the controller and the view.
For instance, to set the current view (JSP file) for the `WelcomePage` controller, just call `tpl.display(String jspPage)`.
If you want to add a variable to be available in the view (JSP file), just call `tpl.assign(String key, Object o)`.

Here's a complete example :
```
@Override
public void build(Template tpl)
{
    tpl.assign("maVariable", "Hello World!");
    tpl.display("/WEB-INF/pagelets/index.jsp");
}
```
Basically, the methods the Template class have are similar to Smarty (PHP Template Engine).

### Step 6 : Make the view (JSP file)

You can use all the mechanics that are available for use in JSP files. To access the "maVariable" variable in the view, here's an example :
```
<html>
  <head><title>${maVariable}</title></head>
</html>
```
Remember you can use JSTL or scriptlet as others ways. If you want to use scriptlets and access "maVariable", just use :
```
<%= request.getAttribute("maVariable") %>
```
