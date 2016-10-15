# WaVEFORm
Model-View-Controller Wrapper for Java Websites, using Web Java EE

## What is this?

Java EE is very strong, but missed some major features for efficient website development. In fact, modeling could be done with powerful tools like JPA. JSP and JSTL are a sufficient way to handle the "view" concept, but we missing an easy-to-use controller system. *Like Spring or Struts, this is a library that handles the concept of MVC (Model-View-Controller) for Web Development.*

But, this one is waaay lighter and easier to use than Spring or Struts (or at least this was the main goal while developing this library). Because sometimes, we doesn't need to build a tower when a house is completely sufficient.

Basicly, for each web page of your website, you create a page class with the implementation of the page. You also tell the JSP to use for the page inside this class.

Another goal is to make a library that works like Smarty for PHP. Ported fpr Java. And of course with some tweaks.

Go to the [Wiki](https://github.com/doubotis/java-website-mvc/wiki) Page to get how it works and how to use.

## Main Features
* Easy-to-use, with a minimum of config files and annotations
* Regex pattern to control the display of each webpage
* Internationalization
* Custom pages for HTTP status codes

## Installation Guide

### Quick Install

Please see the [Quick Install Guide](Quick Install)
Download the [all-inclusive library](https://github.com/doubotis/java-website-mvc/blob/master/store/java-website-mvc.jar).
Then add it to the list of librairies of a Web Java EE Project.

This .jar includes:
* The library itself
* Web Java EE6 library
* Reflections-0.9 - from Google, based on https://github.com/ronmamo/reflections
* Java HTTP Extensions - from doubotis. Add some classes for better Web Development.

### Setup the web.xml
Now the library is installed, you must configure the `web.xml` file, to add the ServletController:

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
You can use a custom filter and a different url-pattern to match your needs.
This servlet will serve all the webpages you need to display to the user.

Now you can begin to use the library. To get how to use it, please see [here](https://github.com/doubotis/java-website-mvc/wiki)

## License

Licensed with GNU GPL.
[See the license](https://github.com/doubotis/WaVEFORm/blob/master/LICENSE.md).
