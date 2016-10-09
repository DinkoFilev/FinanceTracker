package com.budgetbeat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.budgetbeat.manager.TagManager;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.User;

@Controller
public class TagController {

	@Autowired
	TagManager tagManager;// will inject dao from xml file

	/*
	 * It displays a form to input data, here "command" is a reserved request
	 * attribute which is used to display object data into form
	 */
	@RequestMapping("/tagform")
	public ModelAndView showTagForm() {
		return new ModelAndView("tagform", "command", new Tag());
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because
	 * default request is GET
	 */
	@RequestMapping(value = "/savetag", method = RequestMethod.POST)
	public ModelAndView saveTag(@ModelAttribute("tag") Tag tag, HttpSession session) {

		Integer userId = ((User) session.getAttribute("user")).getUserID();
		tagManager.create(tag.getName(), userId, tag.getParentId());
		return new ModelAndView("redirect:/viewtag");// will redirect to viewemp
														// request mapping
	}

	/* It provides list of employees in model object */
	@RequestMapping("/viewtag")
	public ModelAndView viewTags(HttpSession session) {
		Integer userId = ((User) session.getAttribute("user")).getUserID();
		List<Tag> list = tagManager.listTgs(userId);
		return new ModelAndView("viewtag", "list", list);
	}

	/*
	 * It displays object data into form for the given id. The @PathVariable
	 * puts URL data into variable.
	 */
	@RequestMapping(value = "/edittag/{id}")
	public ModelAndView editTag(@PathVariable int id) {
		Tag tag = tagManager.getTag(id);
		return new ModelAndView("tageditform", "command", tag);
	}

	/* It updates model object. */
	@RequestMapping(value = "/editsavetag", method = RequestMethod.POST)
	public ModelAndView editSaveTag(@ModelAttribute("tag") Tag tag) {
		tagManager.update(tag.getTagId(), tag.getName());
		return new ModelAndView("redirect:/viewtag");
	}

	/* It deletes record for the given id in URL and redirects to /viewemp */
	@RequestMapping(value = "/deletetag/{id}", method = RequestMethod.GET)
	public ModelAndView deleteTag(@PathVariable int id) {
		tagManager.delete(id);
		return new ModelAndView("redirect:/viewtag");
	}

}