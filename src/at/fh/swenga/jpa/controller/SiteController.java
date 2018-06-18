package at.fh.swenga.jpa.controller;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.fh.swenga.jpa.dao.GenderRepository;
import at.fh.swenga.jpa.dao.PersonalCharacterRepository;
import at.fh.swenga.jpa.dao.RegionRepository;
import at.fh.swenga.jpa.dao.UserRepository;
import at.fh.swenga.jpa.dao.UserRoleRepository;
import at.fh.swenga.jpa.model.GenderModel;
import at.fh.swenga.jpa.model.PersonalCharacterModel;
import at.fh.swenga.jpa.model.PhotoModel;
import at.fh.swenga.jpa.model.RegionModel;
import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.UserRoleModel;

@Controller
@RequestMapping(value = { "/" })
public class SiteController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	GenderRepository genderRepository;
	
	@Autowired
	RegionRepository regionRepository;

	@Autowired
	PersonalCharacterRepository personalCharacterRepository;
	
	@RequestMapping(value = { "/" })
	public String index(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
		return "index";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = { "/register" })
	public String register(Model model) {
		List<GenderModel> genders = genderRepository.findAll();
		List<RegionModel> regions = regionRepository.findAll();
		model.addAttribute("genders", genders);
		model.addAttribute("regions", regions);
		return "register";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null)
	    	new SecurityContextLogoutHandler().logout(request, response, auth);
	    
	    return "redirect:/login?logout";
	}
	
	@RequestMapping("/fill")
	@Transactional
	public String fill(Model model) {
		
		// personal characters
		PersonalCharacterModel personalCharacterModel1 = new PersonalCharacterModel("Nett");
		PersonalCharacterModel personalCharacterModel2 = new PersonalCharacterModel("�ngstlich");
		PersonalCharacterModel personalCharacterModel3 = new PersonalCharacterModel("Liebevoll");
		personalCharacterRepository.save(personalCharacterModel1);
		personalCharacterRepository.save(personalCharacterModel2);
		personalCharacterRepository.save(personalCharacterModel3);
		
		Set<PersonalCharacterModel> set1PersonalCharacter = new HashSet<>();
		set1PersonalCharacter.add(personalCharacterModel1);
		set1PersonalCharacter.add(personalCharacterModel2);
		
		Set<PersonalCharacterModel> set2PersonalCharacter = new HashSet<>();
		set2PersonalCharacter.add(personalCharacterModel1);
		set2PersonalCharacter.add(personalCharacterModel3);
		
		// genders
		GenderModel genderMaleModel = new GenderModel("m", "M�nnlich");
		GenderModel genderFemaleModel = new GenderModel("f", "Weiblich");
		
		// regions
		RegionModel regionAndritzModel = new RegionModel("Andritz");
		RegionModel regionPuntigamModel = new RegionModel("Puntigam");
		
		// photos
		Set<PhotoModel> set1PhotoModel = new HashSet<>();
		set1PhotoModel.add(new PhotoModel("1.jpg"));
		set1PhotoModel.add(new PhotoModel("2.jpg"));
		set1PhotoModel.add(new PhotoModel("3.jpg"));
		
		Set<PhotoModel> set2PhotoModel = new HashSet<>();
		set2PhotoModel.add(new PhotoModel("4.jpg"));
		set2PhotoModel.add(new PhotoModel("5.jpg"));
		set2PhotoModel.add(new PhotoModel("6.jpg"));
		set2PhotoModel.add(new PhotoModel("7.jpg"));
		
		// user roles
		UserRoleModel userRoleAdmin = new UserRoleModel("ROLE_ADMIN");
		UserRoleModel userRoleUser = new UserRoleModel("ROLE_USER");
		UserRoleModel userRolePremium= new UserRoleModel("ROLE_PREMIUM");
		userRoleRepository.save(userRoleAdmin);
		userRoleRepository.save(userRoleUser);
		userRoleRepository.save(userRolePremium);
		
		// users
		Calendar birthday1 = Calendar.getInstance();
		birthday1.set(1990, 1, 15);
		
		Calendar birthday2 = Calendar.getInstance();
		birthday2.set(1950, 10, 20);
		
		UserModel userModel1 = new UserModel(genderMaleModel, regionPuntigamModel, "mustermax", "pa$$w0rd", "Max", "Mustermann", "max@mustermann.at", birthday1, "This is a short text about my life.", "Braun", "Kurz", "Braun", "Schlank", 180, false, true, true, false);
		userModel1.encryptPassword();
		userModel1.setPhotos(set1PhotoModel);
		userModel1.setPersonalCharacters(set1PersonalCharacter);
		userModel1.addUserRole(userRoleAdmin);
		userModel1.addUserRole(userRoleUser);
		userModel1.addUserRole(userRolePremium);
		
		UserModel userModel2 = new UserModel(genderFemaleModel, regionAndritzModel, "janedoe", "pa$$w0rd", "Jane", "Doe", "jane@doe.com", birthday2, "I don't know what to write here.", "Hell", "Lang", "Gr�n", "Schlank", 165, true, true, true, true);
		userModel2.encryptPassword();
		userModel2.setPhotos(set2PhotoModel);
		userModel2.setPersonalCharacters(set2PersonalCharacter);
		userModel2.addUserRole(userRoleUser);
	
		userRepository.save(userModel1);
		userRepository.save(userModel2);
		
		return "index";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "error";
	}
	
}
