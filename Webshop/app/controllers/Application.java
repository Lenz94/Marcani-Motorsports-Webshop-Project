package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result startsida() {
        return ok(startsida.render("This is the startsida!"));
    }
    public static Result categorysida() {
        return ok(categorysida.render("This is the categorysida!"));
    }
    public static Result produktsida() {
        return ok(produktsida.render("This is the produktsida!"));
    }
    public static Result shoppingvagn() {
        return ok(shoppingvagn.render("This is the shoppingvagn!"));
    }
    public static Result checkout() {
        return ok(checkout.render("This is the checkout!"));
    }
    
}