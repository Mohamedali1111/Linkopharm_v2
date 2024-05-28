package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.category;
import com.example.aswe.linkopharm.repositories.categoryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class categoryController {

    @Autowired
    private categoryRepository categoryRepository;

    @GetMapping("")
    public ModelAndView getAllcategories() {
        List<category> categories = categoryRepository.findAll();
        ModelAndView mav = new ModelAndView("categoryDash");
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addCategory() {
        ModelAndView mav = new ModelAndView("addcategory");
        mav.addObject("category", new category());
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveCategory(@ModelAttribute("category") category category, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("addcategory");
            mav.addObject("order", category);
            return mav;
        }
        categoryRepository.save(category);
        return new ModelAndView("redirect:/categories");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCategoryForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("editcategory");
        Optional<category> optionalCategory = categoryRepository.findById(id);
        optionalCategory.ifPresent(c -> mav.addObject("category", c));
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCategory(@PathVariable("id") int id, @ModelAttribute("category") category updatedCategory, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("editCategory");
            mav.addObject("category", updatedCategory);
            return mav;
        }
        updatedCategory.setId(id);
        categoryRepository.save(updatedCategory);
        return new ModelAndView("redirect:/categories");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") int id) {
        categoryRepository.deleteById(id);
        return new ModelAndView("redirect:/categories");
    }
}