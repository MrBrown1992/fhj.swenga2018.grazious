package at.fh.swenga.grazious.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.grazious.dao.UserAllgemeinDao;
import at.fh.swenga.grazious.model.UserAllgemeinModel;

@Controller
public class UserAllgemeinController {

	@Autowired
	UserAllgemeinDao userAllgemeinDao;
	
	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
 
		List<UserAllgemeinModel> userAllgemeins = userAllgemeinDao.getUserAllgemeins();
		model.addAttribute("userAllgemeins", userAllgemeins);
		return "index";
	}
 
	@RequestMapping("/fillUserAllgemeinList")
	@Transactional
	public String fillData(Model model) {
 
 
		Date now = new Date();
 
		UserAllgemeinModel p1 = new UserAllgemeinModel("Adalbert", "Bergmann", now, "adalbert.bergmann@graz.at", 175, "athletisch", "blau", "braun", "m�nnlich", true, false);
		userAllgemeinDao.persist(p1);
 
 
		return "forward:list";
	}
 
	@RequestMapping("/searchUserAllgemeins")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("userAllgemeins", userAllgemeinDao.searchUserAllgemein(searchString));
		return "index";
	}
 
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		userAllgemeinDao.delete(id);
 
		return "forward:list";
	}
 
	// @ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
 
		return "error";
 
	}
}