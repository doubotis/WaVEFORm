# Java Website Model-View-Controller Framework
Model-View-Controller Wrapper for Java Websites, using Web Java EE

## What is this?

Java EE is very strong, but missed some major features for efficient website development. In fact, modeling could be done with powerful tools like JPA. JSP and JSTL are a sufficient way to handle the "view" concept, but we missing an easy-to-use controller system. *Like Spring or Struts, this is a library that handles the concept of MVC (Model-View-Controller) for Web Development.*

But, this one is waaay lighter and easier to use than Spring or Struts (or at least this was the main goal while developing this library). Because sometimes, we doesn't need to build a tower when a house is completely sufficient.

Another goal is to make a library that works like Smarty for PHP. Ported fpr Java. And of course with some tweaks.

Go to the [Wiki](https://github.com/doubotis/java-website-mvc/wiki) Page to get how it works and how to use.

## Main Features
* Easy-to-use, with a minimum of config files and annotations
* Regex pattern to control the display of each webpage
* Internationalization
* Custom pages for HTTP status codes

## Installation Guide

### Quick Install

Download the [all-inclusive library](https://github.com/doubotis/java-website-mvc/blob/master/store/java-website-mvc.jar).
Then add it to the list of librairies of a Web Java EE Project.

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
