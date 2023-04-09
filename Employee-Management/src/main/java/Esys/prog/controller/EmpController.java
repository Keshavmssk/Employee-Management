package Esys.prog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Esys.prog.entity.Employee;
import Esys.prog.service.EmpService;

@Controller
public class EmpController {

	@Autowired
	private EmpService service;

	@GetMapping("/")
    public String viewHomePage(Model m) {
        return findPaginated(1, "name", "asc", m);
	}

	@GetMapping("/addemp")
	public String addEmpForm() {
		return "add_emp";
	}

	@PostMapping("/register")
	public String empRegister(@ModelAttribute Employee e) {
		service.addEmp(e);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model m) {
		Employee e = service.getEMpById(id);
		m.addAttribute("emp", e);
		return "edit";
	}

	@PostMapping("/update")
	public String updateEmp(@ModelAttribute Employee e) {
		service.addEmp(e);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteEMp(@PathVariable int id) {

		service.deleteEMp(id);
		return "redirect:/";
	}

	 @GetMapping("/page/{pageNo}")
	    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
	        @RequestParam("sortField") String sortField,
	        @RequestParam("sortDir") String sortDir,
	        Model m) {
	        int pageSize = 5;

	        Page < Employee > page = service.findPaginated(pageNo, pageSize, sortField, sortDir);
	        List < Employee > listOfEmployees = page.getContent();

	        m.addAttribute("currentPage", pageNo);
	        m.addAttribute("totalPages", page.getTotalPages());
	        m.addAttribute("totalItems", page.getTotalElements());

	        m.addAttribute("sortField", sortField);
	        m.addAttribute("sortDir", sortDir);
	        m.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

	        m.addAttribute("emp", listOfEmployees);
	        return "index";
	}

}
