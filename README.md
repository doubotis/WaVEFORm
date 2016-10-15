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

Please see the [Quick Install Guide](https://github.com/doubotis/java-website-mvc/wiki/Quick%20Install)

### First Steps
Please see the [First Steps Guide](https://github.com/doubotis/java-website-mvc/wiki/First%20Steps)

## License

Licensed with GNU GPL.
[See the license](https://github.com/doubotis/WaVEFORm/blob/master/LICENSE.md).
