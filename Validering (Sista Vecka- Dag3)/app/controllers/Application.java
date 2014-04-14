package controllers;

import java.util.List;

import models.Student;

import com.avaje.ebean.Ebean;

import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result insertStudent() {
    	Student student = new Student();
		Form<Student> form = Form.form(Student.class).bindFromRequest();
		boolean check = false;
		
		if(form.hasErrors()) {
			flash().put("error", "yes");
			check = true;
			
			for(String propertyName : form.errors().keySet()) {
				List<ValidationError> errorsForThisProperty = form.errors().get(propertyName);
    			
				if(errorsForThisProperty.size() > 0) {
					String test = "";
					for(ValidationError error : errorsForThisProperty) {
						if(error.message().equals("error.required")) {
							test += "No value here yet, ";
						}
						
						if(error.message().equals("error.minLength")) {
							test += "Please, put more characters, ";
						}
						
						if(error.message().equals("error.maxLength")) {
							test += "Please, put less characters, ";
						}
						
						if(error.message().equals("error.email")) {
							test += "Not valid email address, ";
						}
					}
					
					test = test.substring(0, test.length() - 2);
					flash().put(propertyName, test);
				}
			}
		}
		
		if(check) {
			return redirect(routes.Application.createStudents());
		}
		
		student.name = form.get().name;
		student.lastname = form.get().lastname;
		student.email = form.get().email;
		student.phoneNumber = form.get().phoneNumber;
		Ebean.save(student);
		
		return redirect(routes.Application.createStudents());
    }
    
    public static Result createStudents() {
    	List<Student> students = Ebean.find(Student.class).findList();
    	return ok(index.render(students));
    }

}
