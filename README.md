# java-website-mvc
MVC Wrapper for Java Websites

## What is this?

Java EE is very strong, but missing some major features for website development. In fact, modeling could be done with powerful tools like JPA. JSP and JSTL are a sufficient way to handle the "view" concept, but we missing a easy-to-use controller system. *Like Spring or Struts, this is a library that handles the concept of MVC (Model-View-Controller) for Web Development.*

But, this one is waaay lighter and easier to use than Spring or Struts (or at least this was the main goal while developing this tool). Because sometimes, we doesn't need to build a tower when a house is completely sufficient.

Another goal is to make a library that works like Smarty for PHP. For Java. And of course with some tweaks.

Go to the Wiki Page to get how it works.

## Installation Guide

### Step 1 : Prerequisites
Only works inside a Web Java EE Project.
Include the `store/java-websitee-mvc.jar` from the Github project in the librairies of your Web Java EE Project.
This .jar includes:
* The library itself
* Web Java EE6 library
* Reflections-0.9 - from Google, based on https://github.com/ronmamo/reflections
* Java HTTP Extensions - from doubotis. Add some classes for better Web Development.

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

### Step 3 : Create a controller
In this MVC system, each webpage is an unique controller.
For instance if you want to manage the webpage index.html, you must create a new controller.
```
@CPath(path = "index.html")
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

### Step 4 : Manages the template
The `Template` variable serves as a messenger between the controller and the view.
For instance, to set the current view (JSP file) for the `WelcomePage` controller, just call `tpl.display(String jspPage)`.
If you want to add a variable to be available in the view (JSP file), just call `tpl.assign(String key, Object o)`.

Here's a very basic example :
```
@Override
public void build(Template tpl)
{
    tpl.assign("myVariable", "Hello World!");
    tpl.display("/WEB-INF/pagelets/index.jsp");
}
```
```
<!-- In my JSP file, i can wrap variables defined within the Template object. -->
maVariable contient <strong>${myVariable}</strong>.
```
Basically, the methods the Template class have are similar to Smarty (PHP Template Engine).

### Step 5 : Make the view (JSP file)

You can use all the mechanics that are available for use in JSP files. To access the "myVariable" variable in the view, here's an example :
```
<html>
  <head><title>${myVariable}</title></head>
</html>
```
Remember you can use JSTL or scriptlet as others ways. If you want to use scriptlets (even this is discouraged, because it is against the MVC mechanic), and access "myVariable", just use :
```
<%= request.getAttribute("myVariable") %>
```
